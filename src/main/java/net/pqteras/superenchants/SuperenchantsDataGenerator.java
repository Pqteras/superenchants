package net.pqteras.superenchants;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.pqteras.superenchants.datagen.ModBlockTagProvider;
import net.pqteras.superenchants.datagen.ModLootTableProvider;
import net.pqteras.superenchants.datagen.ModModelProvider;
import net.pqteras.superenchants.datagen.ModRecipeProvider;

public class SuperenchantsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModLootTableProvider::new);
	}
}