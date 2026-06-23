package com.traverse.bhc.common.blocks.entity;

import com.traverse.bhc.common.blocks.BuddingCrystalBlock;
import com.traverse.bhc.common.blocks.VitalicBudBlock;
import com.traverse.bhc.common.blocks.VitalicBudSize;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.VitalicSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class BuddingCrystalBlockEntity extends BlockEntity {

    private int charge;

    public BuddingCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(RegistryHandler.BUDDING_CRYSTAL_BLOCK_ENTITY.get(), pos, state);
    }

    public int getCharge() {
        return charge;
    }

    public VitalicSource getSource() {
        return getBlockState().getBlock() instanceof BuddingCrystalBlock b ? b.getSource() : null;
    }

    public boolean canAccept() {
        return charge < ConfigHandler.general.vitalicBuddingCrystalMaxCharge.get();
    }

    public int addCharge(int amount) {
        if (amount <= 0 || level == null) return 0;
        int max = ConfigHandler.general.vitalicBuddingCrystalMaxCharge.get();
        int accepted = Math.min(amount, max - charge);
        if (accepted <= 0) return 0;
        charge += accepted;
        syncStage();
        setChanged();
        return accepted;
    }

    private void syncStage() {
        if (level == null) return;
        int max = ConfigHandler.general.vitalicBuddingCrystalMaxCharge.get();
        int target = BuddingCrystalBlock.stageForCharge(charge, max);
        BlockState state = getBlockState();
        int current = state.getValue(BuddingCrystalBlock.STAGE);
        if (current == target) return;
        level.setBlock(worldPosition, state.setValue(BuddingCrystalBlock.STAGE, target), Block.UPDATE_ALL);
        updateBuds(target);
    }

    private void updateBuds(int stage) {
        VitalicSource source = getSource();
        if (source == null) return;
        VitalicBudSize desiredSize = sizeForStage(stage);
        Block desired = desiredSize == null ? null : RegistryHandler.getBud(source, desiredSize).get();
        for (Direction dir : Direction.values()) {
            BlockPos adjPos = worldPosition.relative(dir);
            BlockState adjState = level.getBlockState(adjPos);
            boolean isOurBud = adjState.getBlock() instanceof VitalicBudBlock bud && bud.getSource() == source;
            if (!adjState.isAir() && !isOurBud) continue;
            if (desired == null) {
                if (isOurBud) level.setBlock(adjPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            } else {
                level.setBlock(adjPos, desired.defaultBlockState().setValue(VitalicBudBlock.FACING, dir), Block.UPDATE_ALL);
            }
        }
    }

    @Nullable
    private static VitalicBudSize sizeForStage(int stage) {
        return switch (stage) {
            case 1 -> VitalicBudSize.SMALL;
            case 2 -> VitalicBudSize.MEDIUM;
            case 3 -> VitalicBudSize.LARGE;
            case 4 -> VitalicBudSize.CLUSTER;
            default -> null;
        };
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        charge = input.getIntOr("Charge", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("Charge", charge);
    }
}
