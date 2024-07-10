package net.pqteras.superenchants.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.pqteras.superenchants.item.ModItems;

public class ShardSlot extends Slot {
    public ShardSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() == ModItems.SUPERENCHANT_UPGRADE_TEMPLATE_SHARD;
    }
}