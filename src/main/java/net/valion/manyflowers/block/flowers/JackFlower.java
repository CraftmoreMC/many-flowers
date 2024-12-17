package net.valion.manyflowers.block.flowers;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.valion.manyflowers.ManyFlowers;
import net.valion.manyflowers.block.ManyFlowersBiomeTexturedBlockInterface;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class JackFlower extends FlowerBlock implements Fertilizable, ManyFlowersBiomeTexturedBlockInterface, FactoryBlock {
    private static Item item;
    private static Item item2;
    public static final BooleanProperty LIT = Properties.LIT;
    public JackFlower() {
        super(StatusEffects.FIRE_RESISTANCE, 0, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).nonOpaque()
                .noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).luminance(createLightLevelFromLitBlockState(15)));
        this.setDefaultState(this.getDefaultState().with(LIT, false));

        item = new ModeledItem(Items.STONE, new Item.Settings());
        item2 = new ModeledItem(Items.STONE, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/jack_flower"), item);
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/jack_flower_light"), item2);
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState state) {
        if (state.get(LIT).equals(false))
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
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT);
    }

    private void updateState(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.cycle(LIT), Block.NOTIFY_LISTENERS);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.getTimeOfDay() >= 10000) {
            if (!state.get(LIT)) {
                updateState(state, world, pos);
            }
        } else if (state.get(LIT)) {
            updateState(state, world, pos);
        }
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        super.appendProperties(builder);
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    }
}
