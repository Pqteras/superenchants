package net.pqteras.superenchants.screen.slot;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.pqteras.superenchants.block.entity.UpgradeBenchBlockEntity;

public class BookSlot extends Slot {
    public BookSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isOf(Items.ENCHANTED_BOOK) && isMaxLevelBook(stack);
    }

    private boolean isMaxLevelBook(ItemStack stack) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment enchantment = entry.getKey();
                int level = entry.getValue();
                if ((level < enchantment.getMaxLevel() || level > enchantment.getMaxLevel()) || UpgradeBenchBlockEntity.EXCLUDED_ENCHANTMENTS.contains(enchantment)) {
                    return false;
                }
            }
        return true;
    }
}
