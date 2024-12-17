package net.valion.manyflowers.setup;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.valion.manyflowers.block.ManyFlowersFlowerBlock;
import net.valion.manyflowers.block.plant.*;

import static net.valion.manyflowers.ManyFlowers.MOD_ID;

public class OreFlowers {
    public static final Block DIAMOND_FLOWER = registerBlock("diamond_flower",
            new ManyFlowersFlowerBlock(StatusEffects.FIRE_RESISTANCE, 3,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "diamond_flower"));

    public static final Block COAL_FLOWER = registerBlock("coal_flower",
            new ManyFlowersFlowerBlock(StatusEffects.FIRE_RESISTANCE, 3,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "coal_flower"));

    public static final Block IRON_FLOWER = registerBlock("iron_flower",
            new ManyFlowersFlowerBlock(StatusEffects.FIRE_RESISTANCE, 3,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "iron_flower"));

    public static final Block GOLD_FLOWER = registerBlock("gold_flower",
            new ManyFlowersFlowerBlock(StatusEffects.FIRE_RESISTANCE, 3,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "gold_flower"));

    public static final Block EMERALD_FLOWER = registerBlock("emerald_flower",
            new ManyFlowersFlowerBlock(StatusEffects.FIRE_RESISTANCE, 3,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "emerald_flower"));

    public static final Block COPPER_FLOWER = registerBlock("copper_flower",
            new ManyFlowersFlowerBlock(StatusEffects.FIRE_RESISTANCE, 3,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "copper_flower"));

    //Plants//
    public static final Block DIAMOND_PLANT = registerBlockWithoutBlockItem("diamond_plant",
            new DiamondPlant(AbstractBlock.Settings.copy(Blocks.WHEAT).nonOpaque().noCollision()));

    public static final Block COAL_PLANT = registerBlockWithoutBlockItem("coal_plant",
            new CoalPlant(AbstractBlock.Settings.copy(Blocks.WHEAT).nonOpaque().noCollision()));

    public static final Block IRON_PLANT = registerBlockWithoutBlockItem("iron_plant",
            new IronPlant(AbstractBlock.Settings.copy(Blocks.WHEAT).nonOpaque().noCollision()));

    public static final Block GOLD_PLANT = registerBlockWithoutBlockItem("gold_plant",
            new GoldPlant(AbstractBlock.Settings.copy(Blocks.WHEAT).nonOpaque().noCollision()));

    public static final Block EMERALD_PLANT = registerBlockWithoutBlockItem("emerald_plant",
            new EmeraldPlant(AbstractBlock.Settings.copy(Blocks.WHEAT).nonOpaque().noCollision()));

    public static final Block COPPER_PLANT = registerBlockWithoutBlockItem("copper_plant",
            new CopperPlant(AbstractBlock.Settings.copy(Blocks.WHEAT).nonOpaque().noCollision()));

    //Petal Blocks//
    public static final Block DIAMOND_PETAL_BLOCK = registerBlock("diamond_petal_block",
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block COAL_PETAL_BLOCK = registerBlock("coal_petal_block",
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE)));

    public static final Block IRON_PETAL_BLOCK = registerBlock("iron_petal_block",
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL)));

    public static final Block GOLD_PETAL_BLOCK = registerBlock("gold_petal_block",
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL)));

    public static final Block EMERALD_PETAL_BLOCK = registerBlock("emerald_petal_block",
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block COPPER_PETAL_BLOCK = registerBlock("copper_petal_block",
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(ItemGroupSetup.MANY_FLOWERS).register(entries -> entries.add(item));
    }

    private static Block registerBlockWithoutBlockItem(String name, Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
    }
}
