package net.valion.manyflowers.setup;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.valion.manyflowers.ManyFlowers;
import net.valion.manyflowers.block.ManyFlowersFlowerBlock;
import net.valion.manyflowers.block.ManyFlowersModeledBlockItem;
import net.valion.manyflowers.block.ManyFlowersTallFlowerBlock;
import net.valion.manyflowers.block.flowers.*;

public class Flowers {

    public static final Block ALSTROEMERIA_FLOWER = registerBlock("alstroemeria_flower",
            new ManyFlowersFlowerBlock(StatusEffects.SATURATION, 0,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "alstroemeria_flower"), ItemGroupSetup.MANY_FLOWERS);

    public static final Block HYDRANGEA_FLOWER = registerBlock("hydrangea_flower",
            new ManyFlowersFlowerBlock(StatusEffects.WATER_BREATHING, 0,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "hydrangea_flower"), ItemGroupSetup.MANY_FLOWERS);

    public static final Block MARIGOLD_FLOWER = registerBlock("marigold_flower",
            new ManyFlowersFlowerBlock(StatusEffects.FIRE_RESISTANCE, 0,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "marigold_flower"), ItemGroupSetup.MANY_FLOWERS);

    public static final Block SWEET_ALYSSUM = registerBlock("sweet_alyssum",
            new SweetAlyssum(StatusEffects.ABSORPTION, 0,
                    AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).luminance((s) -> 5)), ItemGroupSetup.MANY_FLOWERS);

    public static final Block WATER_HEMLOCK = registerBlock("water_hemlock",
            new WaterHemlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)), ItemGroupSetup.MANY_FLOWERS);

    public static final Block HEMLOCK = registerBlock("hemlock",
            new Hemlock(StatusEffects.FIRE_RESISTANCE, 0,
                    (AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS))), ItemGroups.FUNCTIONAL);

    public static final Block OENOTHERA = registerBlock("oenothera",
            new Oenothera(AbstractBlock.Settings.copy(Blocks.ROSE_BUSH).nonOpaque()), ItemGroupSetup.MANY_FLOWERS);

    public static final Block GAILLARDIA = registerBlock("gaillardia",
            new Gaillardia(StatusEffects.FIRE_RESISTANCE, 0,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque()), ItemGroupSetup.MANY_FLOWERS);

    public static final Block ORIENTAL_POPPY = registerBlock("oriental_poppy",
            new OrientalPoppy(StatusEffects.FIRE_RESISTANCE, 3,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque()), ItemGroupSetup.MANY_FLOWERS);

    public static final Block ROOT_OF_THE_WORLDS = registerBlock("root_of_the_worlds",
            new RootOfTheWorlds(StatusEffects.FIRE_RESISTANCE, 0,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque()), ItemGroupSetup.MANY_FLOWERS);

    public static final Block CHRYSANTHEMUM = registerBlock("chrysanthemum",
            new Chrysanthemum(StatusEffects.REGENERATION, 0,
                    (AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS))), ItemGroupSetup.MANY_FLOWERS);

    public static final Block DAISIES = registerBlock("daisies",
            new ManyFlowersFlowerBlock(StatusEffects.ABSORPTION, 0,
                    AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque(), "daisies"), ItemGroupSetup.MANY_FLOWERS);

    public static final Block VELVETS = registerBlock("velvets",
            new Velvets(AbstractBlock.Settings.copy(Blocks.DANDELION).sounds(BlockSoundGroup.WEEPING_VINES)), ItemGroupSetup.MANY_FLOWERS);

    public static final Block AUTUMN_ASTERS = registerBlock("autumn_asters",
            new AutumnAsters(AbstractBlock.Settings.copy(Blocks.ROSE_BUSH)), ItemGroupSetup.MANY_FLOWERS);

    public static final Block ZINNIA = registerBlock("zinnia",
            new ManyFlowersTallFlowerBlock(AbstractBlock.Settings.copy(Blocks.ROSE_BUSH), "zinnia"), ItemGroupSetup.MANY_FLOWERS);

    public static final Block AUTUMN_CROCUS = registerBlock("autumn_crocus",
            new AutumnCrocus(), ItemGroupSetup.MANY_FLOWERS);

    public static final Block JACK_FLOWER = registerBlock("jack_flower",
            new JackFlower(), ItemGroupSetup.MANY_FLOWERS);

    private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup> group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, Identifier.of(ManyFlowers.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, RegistryKey<ItemGroup> group) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, name),
                new ManyFlowersModeledBlockItem(block, new Item.Settings(), Items.STICK));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }
}
