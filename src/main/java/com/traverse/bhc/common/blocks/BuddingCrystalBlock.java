package com.traverse.bhc.common.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.traverse.bhc.common.blocks.entity.BuddingCrystalBlockEntity;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.items.ItemSoulHeartCrystal;
import com.traverse.bhc.common.util.VitalicSource;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BuddingCrystalBlock extends Block implements EntityBlock {

    public static final int MAX_STAGE = 4;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, MAX_STAGE);

    public static final MapCodec<BuddingCrystalBlock> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            VitalicSource.CODEC.fieldOf("source").forGetter(b -> b.source),
            propertiesCodec()
    ).apply(inst, BuddingCrystalBlock::new));

    private final VitalicSource source;

    public BuddingCrystalBlock(VitalicSource source, BlockBehaviour.Properties properties) {
        super(properties);
        this.source = source;
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }

    @Override
    protected MapCodec<? extends BuddingCrystalBlock> codec() {
        return CODEC;
    }

    public VitalicSource getSource() {
        return source;
    }

    public static int stageForCharge(int charge, int max) {
        if (charge <= 0) return 0;
        return 1 + Math.min(3, charge * MAX_STAGE / max);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BuddingCrystalBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(stack.getItem() instanceof ItemSoulHeartCrystal)) return InteractionResult.TRY_WITH_EMPTY_HAND;
        if (ItemSoulHeartCrystal.getSource(stack) != source) return InteractionResult.TRY_WITH_EMPTY_HAND;
        int avail = ItemSoulHeartCrystal.getCharge(stack);
        if (avail <= 0) return InteractionResult.TRY_WITH_EMPTY_HAND;
        if (!(level.getBlockEntity(pos) instanceof BuddingCrystalBlockEntity be)) return InteractionResult.TRY_WITH_EMPTY_HAND;
        int space = ConfigHandler.general.vitalicBuddingCrystalMaxCharge.get() - be.getCharge();
        if (space <= 0) return InteractionResult.TRY_WITH_EMPTY_HAND;
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        int transfer = Math.min(space, avail);
        int drained = ItemSoulHeartCrystal.drainCharge(stack, transfer);
        if (drained <= 0) return InteractionResult.TRY_WITH_EMPTY_HAND;
        be.addCharge(drained);
        level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 0.6F, 1.0F + level.getRandom().nextFloat() * 0.2F);
        return InteractionResult.SUCCESS;
    }
}
