package net.pqteras.superenchants.block.custom;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.pqteras.superenchants.block.entity.ModBlockEntities;
import net.pqteras.superenchants.block.entity.UpgradeBenchBlockEntity;

@SuppressWarnings("unused")
public class UpgradeBenchBlock extends BlockWithEntity implements BlockEntityProvider {
    public UpgradeBenchBlock(Settings settings) {
        super(settings);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("block.superenchants.upgrade_bench.desc").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new UpgradeBenchBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof UpgradeBenchBlockEntity) {
                ((UpgradeBenchBlockEntity) blockEntity).clearOutputSlot();
                ItemScatterer.spawn(world, pos, (UpgradeBenchBlockEntity)blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((UpgradeBenchBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.UPGRADE_BENCH_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> UpgradeBenchBlockEntity.tick(world1, pos, state1, (UpgradeBenchBlockEntity) blockEntity));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof UpgradeBenchBlockEntity) {
            ((UpgradeBenchBlockEntity) blockEntity).clearOutputSlot();
        }
        super.onBreak(world, pos, state, player);
    }
}

