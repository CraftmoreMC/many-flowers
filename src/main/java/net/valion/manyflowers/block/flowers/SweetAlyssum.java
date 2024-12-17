package net.valion.manyflowers.block.flowers;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.valion.manyflowers.ManyFlowers;
import net.valion.manyflowers.block.ManyFlowersBiomeTexturedBlockInterface;
import net.valion.manyflowers.particle.FlowerParticles;
import org.jetbrains.annotations.Nullable;

public class SweetAlyssum extends FlowerBlock implements ManyFlowersBiomeTexturedBlockInterface, FactoryBlock {
    private static Item item;

    public SweetAlyssum(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, Settings settings) {
        super(stewEffect, effectLengthInSeconds, settings);

        item = new ModeledItem(Items.STONE, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/sweet_alyssum"), item);
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
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        float chance = 0.35f;
        if(chance < random.nextFloat()) {
            world.addParticle(FlowerParticles.SWEET_ALYSSUM_PARTICLES, pos.getX() + 0.5D,
                    pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    0.2d,0.1d,0.3d);
        }
        super.randomDisplayTick(state, world, pos, random);
    }
}
