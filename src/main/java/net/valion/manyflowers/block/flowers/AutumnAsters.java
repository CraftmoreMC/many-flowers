package net.valion.manyflowers.block.flowers;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.valion.manyflowers.ManyFlowers;
import net.valion.manyflowers.block.ManyFlowersBiomeTexturedBlockInterface;
import net.valion.manyflowers.block.flowers.entity.AutumnAstersEntity;
import net.valion.manyflowers.setup.BlockEntitiesReg;
import org.jetbrains.annotations.Nullable;

import static net.valion.manyflowers.block.flowers.entity.AutumnAstersEntity.ids;

public class AutumnAsters extends ExtendedFlower implements ManyFlowersBiomeTexturedBlockInterface, FactoryBlock {
    private static Item item;
    private static Item item2;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static boolean canStill = true;
    public AutumnAsters(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(HALF, DoubleBlockHalf.LOWER));

        item = new ModeledItem(Items.STONE, new Item.Settings());
        item2 = new ModeledItem(Items.STONE, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/asters_bottom"), item);
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/asters_top"), item2);
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState state) {
        if (state.get(HALF).equals(DoubleBlockHalf.LOWER))
            return new FlowerModel();
        else
            return new FlowerModel2();
    }

    public static class FlowerModel extends BlockModel {
        private FlowerModel(){
            ItemDisplayElement itemDisplayElement = ItemDisplayElementUtil.createSimple(item);
            this.addElement(itemDisplayElement);
        }
    }

    public static class FlowerModel2 extends BlockModel {
        private FlowerModel2(){
            ItemDisplayElement itemDisplayElement = ItemDisplayElementUtil.createSimple(item2);
            this.addElement(itemDisplayElement);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!ManyFlowers.CONFIG.still_asters) return;
        if (entity instanceof PlayerEntity) {
            if (ids.size() < 10) {
                if (canStill) {
                    var stack = new ItemStack(((PlayerEntity) entity).getStackInHand(((PlayerEntity) entity).getActiveHand()).getItem(), 1);
                    ids.put(Registries.ITEM.getId(stack.getItem()).toString(), stack.getCount());
                    ((PlayerEntity) entity).getInventory().removeStack(((PlayerEntity) entity).getInventory().selectedSlot, stack.getCount());
                    canStill = false;
                }
            }
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && !ids.isEmpty()) {
            for (int i = 0; i < ids.size(); i++) {
                if (player.getMainHandStack().isIn(ItemTags.SHOVELS)) {
                    var id = ids.keySet().stream().toList().get(i);
                    var count = ids.values().stream().toList().get(i);

                    var stack = new ItemStack(Registries.ITEM.get(Identifier.of(id)), count);
                    var entity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                    world.spawnEntity(entity);
                }
            }
            ids.clear();
            AutumnAstersEntity.counter = 0;
            canStill = false;
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (!(direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)) {
            return super.getPlacementState(ctx);
        }
        return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPos = pos.up();
        world.setBlockState(blockPos, TallPlantBlock.withWaterloggedState(world, blockPos, this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER)), Block.NOTIFY_ALL);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            ids.clear();
            if (player.isCreative()) {
                onBreakInCreative(world, pos, state, player);
            } else {
                dropStacks(state, world, pos, null, player, player.getMainHandStack());
            }
        }
        return state;
    }

    protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos blockPos;
        BlockState blockState;
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER && (blockState = world.getBlockState(blockPos = pos.down())).isOf(state.getBlock()) && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
            BlockState blockState2 = blockState.getFluidState().isOf(Fluids.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
            world.setBlockState(blockPos, blockState2, Block.NOTIFY_ALL | Block.SKIP_DROPS);
            world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, tool);
    }

    @Override
    public long getRenderingSeed(BlockState state, BlockPos pos) {
        return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    @Override
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT);
    }

    //Block entity
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AutumnAstersEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, BlockEntitiesReg.AUTUMN_ASTERS_ENTITY, AutumnAstersEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
