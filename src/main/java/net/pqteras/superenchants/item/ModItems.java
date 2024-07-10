package net.pqteras.superenchants.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.pqteras.superenchants.block.ModBlocks;
import net.pqteras.superenchants.item.custom.UpgradeTemplateShard;

public class ModItems {
    public static final Item SUPERENCHANT_UPGRADE_TEMPLATE_SHARD = registerItem("upgrade_template_shard",
        new UpgradeTemplateShard(
            new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.EPIC)
        ));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(SUPERENCHANT_UPGRADE_TEMPLATE_SHARD);
        entries.add(ModBlocks.SUPERENCHANT_UPGRADE_BENCH);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("superenchants", name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToIngredientItemGroup);
    }
}