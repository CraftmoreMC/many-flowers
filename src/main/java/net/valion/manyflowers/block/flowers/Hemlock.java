package net.valion.manyflowers.block.flowers;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.valion.manyflowers.ManyFlowers;
import net.valion.manyflowers.block.ManyFlowersBiomeTexturedBlockInterface;
import org.jetbrains.annotations.Nullable;

import static net.valion.manyflowers.ManyFlowers.CONFIG;

public class Hemlock extends FlowerBlock implements ManyFlowersBiomeTexturedBlockInterface, FactoryBlock {
    private static Item item;

    public Hemlock(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, Settings settings) {
        super(stewEffect, effectLengthInSeconds, settings);

        item = new ModeledItem(Items.STONE, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/hemlock"), item);
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
        if (!world.isClient && world.getDifficulty() != Difficulty.PEACEFUL && CONFIG.damage_hemlock) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.isInvulnerableTo(world.getDamageSources().magic())) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 50, 3));
                }
            }

        }
    }
}
