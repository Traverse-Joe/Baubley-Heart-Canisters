package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.blocks.entity.BuddingCrystalBlockEntity;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.entity.VitalicOrb;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.ItemSoulHeartCrystal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class VitalicOrbHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity killed = event.getEntity();
        if (!(killed.level() instanceof ServerLevel serverLevel)) return;
        if (killed instanceof Player) return;

        Optional<VitalicSource> match = DropHandler.getMatchingSource(killed);
        if (match.isEmpty()) return;
        VitalicSource source = match.get();

        Vec3 spawnPos = killed.position().add(0.0, killed.getBbHeight() * 0.5, 0.0);

        BlockPos buddingTarget = findBuddingCrystal(serverLevel, killed.blockPosition(), source);
        if (buddingTarget != null) {
            VitalicOrb orb = VitalicOrb.createForBlock(RegistryHandler.VITALIC_ORB.get(), serverLevel, spawnPos, source, buddingTarget);
            serverLevel.addFreshEntity(orb);
            return;
        }

        if (event.getSource().getEntity() instanceof Player killer && hasMatchingCrystal(killer, source)) {
            VitalicOrb orb = VitalicOrb.create(RegistryHandler.VITALIC_ORB.get(), serverLevel, spawnPos, source, killer);
            serverLevel.addFreshEntity(orb);
        }
    }

    @Nullable
    private static BlockPos findBuddingCrystal(ServerLevel level, BlockPos center, VitalicSource source) {
        int radius = ConfigHandler.general.vitalicOrbBuddingCrystalRadius.get();
        if (radius <= 0) return null;
        BlockPos best = null;
        double bestDistSq = Double.MAX_VALUE;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    mutable.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    BlockEntity be = level.getBlockEntity(mutable);
                    if (be instanceof BuddingCrystalBlockEntity bce && bce.getSource() == source && bce.canAccept()) {
                        double distSq = center.distSqr(mutable);
                        if (distSq < bestDistSq) {
                            bestDistSq = distSq;
                            best = mutable.immutable();
                        }
                    }
                }
            }
        }
        return best;
    }

    private static boolean hasMatchingCrystal(Player player, VitalicSource source) {
        int size = player.getInventory().getContainerSize();
        for (int i = 0; i < size; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof ItemSoulHeartCrystal && ItemSoulHeartCrystal.canAccept(stack, source)) return true;
        }
        return false;
    }
}
