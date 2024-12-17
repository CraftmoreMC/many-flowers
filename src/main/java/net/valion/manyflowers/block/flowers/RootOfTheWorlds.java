package net.valion.manyflowers.block.flowers;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.valion.manyflowers.ManyFlowers;
import net.valion.manyflowers.block.ManyFlowersBiomeTexturedBlockInterface;
import org.jetbrains.annotations.Nullable;

import static net.valion.manyflowers.ManyFlowers.CONFIG;
import static net.valion.manyflowers.helpers.WorldsHelper.teleportToSafeZone;

public class RootOfTheWorlds extends FlowerBlock implements ManyFlowersBiomeTexturedBlockInterface, FactoryBlock {
    private static Item item;

    public RootOfTheWorlds(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, Settings settings) {
        super(stewEffect, effectLengthInSeconds, settings);

        item = new ModeledItem(Items.STONE, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/root_of_the_worlds"), item);
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new FlowerModel();
    }

    public static class FlowerModel extends BlockModel {
        private FlowerModel(){
            ItemDisplayElement itemDisplayElement = ItemDisplayElementUtil.createSimple(item);
            this.addElement(itemDisplayElement);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world instanceof ServerWorld && !entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals(false) && entity.isPlayer() && CONFIG.teleport_ROTW) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            teleportToSafeZone(serverPlayer);
        }
    }

    @Override
    public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        float chance = 0.35f;
        if(chance < rand.nextFloat()) {
            worldIn.addParticle(ParticleTypes.PORTAL, pos.getX() + 0.5D,
                    pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    0.2d,0.1d,0.3d);
        }
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.NYLIUM) || floor.isOf(Blocks.END_STONE) || super.canPlantOnTop(floor, world, pos);
    }
}
