package net.valion.manyflowers.block.flowers;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.valion.manyflowers.ManyFlowers;
import net.valion.manyflowers.block.ManyFlowersBiomeTexturedBlockInterface;
import org.jetbrains.annotations.Nullable;

import static net.valion.manyflowers.ManyFlowers.CONFIG;

public class Oenothera extends TallPlantBlock implements Fertilizable, ManyFlowersBiomeTexturedBlockInterface, FactoryBlock {
    private static Item item;
    private static Item item2;
    public Oenothera(Settings settings) {
        super(settings);

        item = new ModeledItem(Items.STONE, new Item.Settings());
        item2 = new ModeledItem(Items.STONE, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/oenothera_bottom"), item);
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/oenothera_top"), item2);
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
        if (!world.isClient && world.getDifficulty() != Difficulty.PEACEFUL && CONFIG.damage_oenothera) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.isInvulnerableTo(world.getDamageSources().magic())) {
                    entity.damage(world.getDamageSources().magic(), 1.0f);
                }
            }

        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean canGrow(World world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public void grow(ServerWorld world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {

    }
}
