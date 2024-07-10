package net.pqteras.superenchants.item.custom;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class UpgradeTemplateShard extends Item {

    public UpgradeTemplateShard(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.superenchants.upgrade_template_shard.desc").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
