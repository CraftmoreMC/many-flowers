package net.valion.manyflowers.block;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.valion.manyflowers.ManyFlowers;
import net.valion.manyflowers.block.flowers.Oenothera;
import org.jetbrains.annotations.Nullable;

public class ManyFlowersTallFlowerBlock extends TallFlowerBlock implements ManyFlowersBiomeTexturedBlockInterface, FactoryBlock {
    private static Item item;
    private static Item item2;
    public ManyFlowersTallFlowerBlock(Settings settings, String name) {
        super(settings);

        item = new ModeledItem(Items.STONE, new Item.Settings());
        item2 = new ModeledItem(Items.STONE, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/"+name+"_bottom"), item);
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/"+name+"_top"), item2);
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
}
