package net.pqteras.superenchants.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pqteras.superenchants.SuperEnchants;
import net.pqteras.superenchants.block.ModBlocks;

public class ModBlockEntities {
    public static final BlockEntityType<UpgradeBenchBlockEntity> UPGRADE_BENCH_BLOCK_ENTITY =
        Registry.register(Registries.BLOCK_ENTITY_TYPE,
        new Identifier("superenchants", "upgrade_bench_be"),
        FabricBlockEntityTypeBuilder.create(UpgradeBenchBlockEntity::new, ModBlocks.SUPERENCHANT_UPGRADE_BENCH).build());

    public static void registerModBlockEntities() {
        SuperEnchants.LOGGER.info("Registering block entities for SuperEnchants...");
    }
}
