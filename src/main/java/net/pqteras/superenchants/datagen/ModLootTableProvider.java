package net.pqteras.superenchants.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.pqteras.superenchants.block.ModBlocks;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.SUPERENCHANT_UPGRADE_BENCH);

        /*
         * Custom Ore Example
         */

        // addDrop(ModBlocks.CUSTOM_BLOCK, oreDrops(ModBlocks.ORE_MINED_WITH_SILK, ModBlocks.ORE_ITSELF));
    }

}
