package net.pqteras.superenchants.block.entity;

import java.util.*;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pqteras.superenchants.item.ModItems;
import net.pqteras.superenchants.screen.UpgradeBenchScreenHandler;

public class UpgradeBenchBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);
    private static final int[] SHARD_SLOT = {0, 1, 2, 3, 5, 6, 7, 8};
    private static final int BOOK_SLOT = 4;
    private static final int OUTPUT_SLOT = 9;

    public UpgradeBenchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.UPGRADE_BENCH_BLOCK_ENTITY, pos, state);
    }

    public static final List<Enchantment> EXCLUDED_ENCHANTMENTS = Arrays.asList(
        Enchantments.MENDING,
        Enchantments.VANISHING_CURSE,
        Enchantments.BINDING_CURSE,
        Enchantments.SILK_TOUCH,
        Enchantments.CHANNELING,
        Enchantments.AQUA_AFFINITY,
        Enchantments.MULTISHOT,
        Enchantments.FLAME,
        Enchantments.INFINITY
    );

    @Override
    public Text getDisplayName() {
        return Text.literal("Upgrade Bench").formatted(Formatting.BLACK);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    public static void tick(World world, BlockPos pos, BlockState state, UpgradeBenchBlockEntity blockEntity) {
        if(world.isClient) return;

        blockEntity.updateOutputSlot();
    }

    public void updateOutputSlot() {
        if (canCraft()) {
            setOutputStack();
        } else {
            clearOutputSlot();
        }
    }

    private void setOutputStack() {
        ItemStack bookStack = this.getStack(BOOK_SLOT);

        if (bookStack.isOf(Items.ENCHANTED_BOOK)) {
            ItemStack upgradedBook = new ItemStack(Items.ENCHANTED_BOOK);
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(bookStack);

            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment enchantment = entry.getKey();
                int level = entry.getValue();
                if (!EXCLUDED_ENCHANTMENTS.contains(enchantment) && level == enchantment.getMaxLevel()) {
                    int newLevel = level + 1; // Increase the max level by 1
                    enchantments.put(enchantment, newLevel);
                }
            }

            EnchantmentHelper.set(enchantments, upgradedBook);
            this.setStack(OUTPUT_SLOT, upgradedBook);
        }
    }

    public void craftItem() {
        for (int slot : SHARD_SLOT) this.removeStack(slot, 1);
        this.removeStack(BOOK_SLOT, 1);

        if(world != null) world.playSound(null, pos, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public void clearOutputSlot() {
        this.setStack(OUTPUT_SLOT, ItemStack.EMPTY);
    }

    private boolean canCraft() {
        boolean hasShards = true;
        for (int slot : SHARD_SLOT) {
            if (this.getStack(slot).getItem() != ModItems.SUPERENCHANT_UPGRADE_TEMPLATE_SHARD || this.getStack(slot).getCount() < 1) {
                hasShards = false;
                break;
            }
        }
        ItemStack bookStack = this.getStack(BOOK_SLOT);
        boolean hasBook = bookStack.getItem() == Items.ENCHANTED_BOOK && isMaxLevelBook(bookStack);

        return hasShards && hasBook && canInsertItemIntoOutputSlot(new ItemStack(Items.ENCHANTED_BOOK));
    }

    private boolean isMaxLevelBook(ItemStack bookStack) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(bookStack);
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();
            if (level < enchantment.getMaxLevel() || EXCLUDED_ENCHANTMENTS.contains(enchantment)) {
                return false;
            }
        }
        return true;
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == result.getItem();
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new UpgradeBenchScreenHandler(syncId, playerInventory, this, ScreenHandlerContext.create(world, pos));
    }

}
