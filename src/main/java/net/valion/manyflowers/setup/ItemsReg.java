package net.valion.manyflowers.setup;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.valion.manyflowers.ManyFlowers;

import static net.valion.manyflowers.ManyFlowers.MOD_ID;
import static net.valion.manyflowers.setup.OreFlowers.*;

public class ItemsReg {
    //Seeds//
    public static final Item DIAMOND_SEEDS = registerItem("diamond_seeds",
            new AliasedBlockItem(DIAMOND_PLANT, new Item.Settings()));

    public static final Item COAL_SEEDS = registerItem("coal_seeds",
            new AliasedBlockItem(COAL_PLANT, new Item.Settings()));

    public static final Item IRON_SEEDS = registerItem("iron_seeds",
            new AliasedBlockItem(IRON_PLANT, new Item.Settings()));

    public static final Item GOLD_SEEDS = registerItem("gold_seeds",
            new AliasedBlockItem(GOLD_PLANT, new Item.Settings()));

    public static final Item EMERALD_SEEDS = registerItem("emerald_seeds",
            new AliasedBlockItem(EMERALD_PLANT, new Item.Settings()));

    public static final Item COPPER_SEEDS = registerItem("copper_seeds",
            new AliasedBlockItem(COPPER_PLANT, new Item.Settings()));

    //Items//
    public static final Item DIAMOND_PETAL = registerItem("diamond_petal",
            new Item(new Item.Settings()));

    public static final Item COAL_PETAL = registerItem("coal_petal",
            new Item(new Item.Settings()));

    public static final Item IRON_PETAL = registerItem("iron_petal",
            new Item(new Item.Settings()));

    public static final Item GOLD_PETAL = registerItem("gold_petal",
            new Item(new Item.Settings()));

    public static final Item EMERALD_PETAL = registerItem("emerald_petal",
            new Item(new Item.Settings()));

    public static final Item COPPER_PETAL = registerItem("copper_petal",
            new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
    }

    public static void addItemsToItemGroup() {
        addToItemGroup(ItemGroups.INGREDIENTS, GOLD_SEEDS);
        addToItemGroup(ItemGroups.INGREDIENTS, COAL_SEEDS);
        addToItemGroup(ItemGroups.INGREDIENTS, COPPER_SEEDS);
        addToItemGroup(ItemGroups.INGREDIENTS, DIAMOND_SEEDS);
        addToItemGroup(ItemGroups.INGREDIENTS, EMERALD_SEEDS);
        addToItemGroup(ItemGroups.INGREDIENTS, IRON_SEEDS);

        addToItemGroup(ItemGroupSetup.MANY_FLOWERS, COAL_PETAL);
        addToItemGroup(ItemGroupSetup.MANY_FLOWERS, COPPER_PETAL);
        addToItemGroup(ItemGroupSetup.MANY_FLOWERS, DIAMOND_PETAL);
        addToItemGroup(ItemGroupSetup.MANY_FLOWERS, EMERALD_PETAL);
        addToItemGroup(ItemGroupSetup.MANY_FLOWERS, GOLD_PETAL);
        addToItemGroup(ItemGroupSetup.MANY_FLOWERS, IRON_PETAL);
    }

    private static void addToItemGroup(RegistryKey<ItemGroup> group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        addItemsToItemGroup();
    }
}
