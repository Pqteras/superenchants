package net.pqteras.superenchants.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.pqteras.superenchants.block.entity.UpgradeBenchBlockEntity;

public class CraftingOutputSlot extends Slot {

    private final UpgradeBenchBlockEntity blockEntity;

    public CraftingOutputSlot(UpgradeBenchBlockEntity blockEntity, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.blockEntity = blockEntity;
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        super.onTakeItem(player, stack);
        blockEntity.craftItem();
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }
}
