package net.pqteras.superenchants.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.pqteras.superenchants.block.entity.UpgradeBenchBlockEntity;
import net.pqteras.superenchants.screen.slot.BookSlot;
import net.pqteras.superenchants.screen.slot.CraftingOutputSlot;
import net.pqteras.superenchants.screen.slot.ShardSlot;

public class UpgradeBenchScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    public final UpgradeBenchBlockEntity blockEntity;
    protected final ScreenHandlerContext context;

    public UpgradeBenchScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()), ScreenHandlerContext.EMPTY);
    }

    public UpgradeBenchScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, ScreenHandlerContext context) {
        super(ModScreenHandlers.UPGRADE_BENCH, syncId);
        this.context = context;
        checkSize((Inventory) blockEntity, 10);
        this.inventory = (Inventory) blockEntity;
        inventory.onOpen(playerInventory.player);
        this.blockEntity = (UpgradeBenchBlockEntity) blockEntity;

        // Crafting Grid
        // Shard Slot
        this.addSlot(new ShardSlot(inventory, 0, 22, 9));
        this.addSlot(new ShardSlot(inventory, 1, 40, 9));
        this.addSlot(new ShardSlot(inventory, 2, 58, 9));
        this.addSlot(new ShardSlot(inventory, 3, 16, 33));
        // Book Slot
        this.addSlot(new BookSlot(inventory, 4, 40, 33));
        // Shard Slot
        this.addSlot(new ShardSlot(inventory, 5, 64, 33));
        this.addSlot(new ShardSlot(inventory, 6, 22, 57));
        this.addSlot(new ShardSlot(inventory, 7, 40, 57));
        this.addSlot(new ShardSlot(inventory, 8, 58, 57));
        // Output slot
        this.addSlot(new CraftingOutputSlot(this.blockEntity, inventory, 9, 133, 33));

        // Player Inventory
        addPlayerInventory(playerInventory);
        // Hotbar
        addPlayerHotbar(playerInventory);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.blockEntity.clearOutputSlot();
        this.context.run((world, pos) -> {
           this.dropInventory(player, this.inventory);
        });
    }
}