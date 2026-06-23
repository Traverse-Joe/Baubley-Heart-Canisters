package com.traverse.bhc.common.entity;

import com.traverse.bhc.common.blocks.entity.BuddingCrystalBlockEntity;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.items.ItemSoulHeartCrystal;
import com.traverse.bhc.common.util.VitalicSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class VitalicOrb extends Entity {

    private static final EntityDataAccessor<Integer> DATA_SOURCE = SynchedEntityData.defineId(VitalicOrb.class, EntityDataSerializers.INT);

    private static final double HOMING_SPEED = 0.10;
    private static final double PICKUP_DISTANCE_SQ = 1.2 * 1.2;
    private static final int MAX_LIFETIME = 600;
    public static final int TRAIL_LENGTH = 12;

    @Nullable private UUID targetPlayerUUID;
    @Nullable private BlockPos targetBlock;
    private int lifetime;
    private final Deque<Vec3> clientTrail = new ArrayDeque<>(TRAIL_LENGTH);

    public VitalicOrb(EntityType<? extends VitalicOrb> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public static VitalicOrb create(EntityType<VitalicOrb> type, Level level, Vec3 pos, VitalicSource source, Player target) {
        VitalicOrb orb = baseCreate(type, level, pos, source);
        orb.targetPlayerUUID = target.getUUID();
        return orb;
    }

    public static VitalicOrb createForBlock(EntityType<VitalicOrb> type, Level level, Vec3 pos, VitalicSource source, BlockPos target) {
        VitalicOrb orb = baseCreate(type, level, pos, source);
        orb.targetBlock = target.immutable();
        return orb;
    }

    private static VitalicOrb baseCreate(EntityType<VitalicOrb> type, Level level, Vec3 pos, VitalicSource source) {
        VitalicOrb orb = new VitalicOrb(type, level);
        orb.setPos(pos.x, pos.y, pos.z);
        orb.setSource(source);
        return orb;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_SOURCE, 0);
    }

    public VitalicSource getSource() {
        return VitalicSource.values()[entityData.get(DATA_SOURCE) % VitalicSource.values().length];
    }

    public void setSource(VitalicSource source) {
        entityData.set(DATA_SOURCE, source.ordinal());
    }

    public Deque<Vec3> getClientTrail() {
        return clientTrail;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            recordTrail();
            return;
        }
        lifetime++;
        if (lifetime > MAX_LIFETIME) {
            discard();
            return;
        }
        Vec3 targetPos = resolveTargetPos();
        if (targetPos == null) {
            discard();
            return;
        }
        Vec3 delta = targetPos.subtract(position());
        if (delta.lengthSqr() < PICKUP_DISTANCE_SQ) {
            if (targetBlock != null) tryDepositIntoBlock();
            else tryDepositIntoPlayer();
            return;
        }
        Vec3 velocity = delta.normalize().scale(HOMING_SPEED);
        setDeltaMovement(velocity);
        move(MoverType.SELF, getDeltaMovement());
    }

    @Nullable
    private Vec3 resolveTargetPos() {
        if (targetBlock != null) {
            if (!(level() instanceof ServerLevel serverLevel)) return null;
            if (!(serverLevel.getBlockEntity(targetBlock) instanceof BuddingCrystalBlockEntity be) || be.getSource() != getSource() || !be.canAccept()) return null;
            return Vec3.atCenterOf(targetBlock);
        }
        Player player = resolvePlayer();
        return player == null ? null : player.position().add(0.0, player.getBbHeight() * 0.5, 0.0);
    }

    @Nullable
    private Player resolvePlayer() {
        if (targetPlayerUUID == null) return null;
        if (level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(targetPlayerUUID);
            if (entity instanceof Player player && player.isAlive()) return player;
        }
        return null;
    }

    private void tryDepositIntoBlock() {
        if (!(level() instanceof ServerLevel serverLevel)) { discard(); return; }
        if (!(serverLevel.getBlockEntity(targetBlock) instanceof BuddingCrystalBlockEntity be) || be.getSource() != getSource()) { discard(); return; }
        int added = be.addCharge(ConfigHandler.general.vitalicChargePerKill.get());
        if (added > 0) {
            serverLevel.playSound(null, targetBlock, SoundEvents.AMETHYST_BLOCK_CHIME, getSoundSource(), 0.4F, 1.4F + level().getRandom().nextFloat() * 0.2F);
        }
        discard();
    }

    private void tryDepositIntoPlayer() {
        Player player = resolvePlayer();
        if (player == null) { discard(); return; }
        VitalicSource source = getSource();
        int perKill = ConfigHandler.general.vitalicChargePerKill.get();
        int slots = player.getInventory().getContainerSize();
        for (int i = 0; i < slots; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof ItemSoulHeartCrystal && ItemSoulHeartCrystal.canAccept(stack, source)) {
                int added = ItemSoulHeartCrystal.addCharge(stack, source, perKill);
                if (added > 0) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, player.getSoundSource(), 0.4F, 1.6F + level().getRandom().nextFloat() * 0.2F);
                    discard();
                    return;
                }
            }
        }
        discard();
    }

    private void recordTrail() {
        clientTrail.addFirst(position());
        while (clientTrail.size() > TRAIL_LENGTH) {
            clientTrail.removeLast();
        }
    }

    public static int colorFor(VitalicSource source) {
        return switch (source) {
            case RED -> 0xF22D2D;
            case YELLOW -> 0xFAE940;
            case GREEN -> 0x40EB59;
            case BLUE -> 0x4073FA;
        };
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        entityData.set(DATA_SOURCE, input.getIntOr("Source", 0));
        targetPlayerUUID = input.read("Target", UUIDUtil.CODEC).orElse(null);
        targetBlock = input.read("TargetBlock", BlockPos.CODEC).orElse(null);
        lifetime = input.getIntOr("Lifetime", 0);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.putInt("Source", entityData.get(DATA_SOURCE));
        output.storeNullable("Target", UUIDUtil.CODEC, targetPlayerUUID);
        output.storeNullable("TargetBlock", BlockPos.CODEC, targetBlock);
        output.putInt("Lifetime", lifetime);
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        return false;
    }
}
