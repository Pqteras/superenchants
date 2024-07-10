package net.pqteras.superenchants;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.pqteras.superenchants.block.ModBlocks;
import net.pqteras.superenchants.block.entity.ModBlockEntities;
import net.pqteras.superenchants.item.ModItems;
import net.pqteras.superenchants.screen.ModScreenHandlers;

import java.util.logging.Logger;

public class SuperEnchants implements ModInitializer {

    public static Logger LOGGER = Logger.getGlobal();

	@Override
	public void onInitialize() {

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && LootTables.ANCIENT_CITY_CHEST.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.SUPERENCHANT_UPGRADE_TEMPLATE_SHARD).weight(1))
                        .conditionally(RandomChanceLootCondition.builder(0.015f).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });

        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModBlockEntities.registerModBlockEntities();
        ModScreenHandlers.registerAllScreenHandlers();
	}
}