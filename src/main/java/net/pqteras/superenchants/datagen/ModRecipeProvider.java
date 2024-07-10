package net.pqteras.superenchants.datagen;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.pqteras.superenchants.block.ModBlocks;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SUPERENCHANT_UPGRADE_BENCH, 1)
            .pattern(" N ")
            .pattern("NCN")
            .pattern(" N ")
            .input('N', Items.NETHERITE_INGOT)
            .input('C', Items.CRAFTING_TABLE)
            .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
            .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
            .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.SUPERENCHANT_UPGRADE_BENCH)));

    }
}
