package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.items.ItemHeartPulseBelt;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class HeartPulseHandler {
    public static final Identifier MOVEMENT_SPEED_MODIFIER_ID = BaubleyHeartCanisters.id("heartpulse_movement_speed");
    public static final Identifier STEP_HEIGHT_MODIFIER_ID = BaubleyHeartCanisters.id("heartpulse_step_height");
    public static final Identifier JUMP_STRENGTH_MODIFIER_ID = BaubleyHeartCanisters.id("heartpulse_jump_strength");

    private static final double BASE_MOVEMENT_SPEED_BONUS = 0.12; // 5% movement speed increase per multiplier
    private static final double STEP_HEIGHT_ADDITION = 0.4; // Adds 0.4 to base 0.6 = 1.0 block step height
    private static final double JUMP_STRENGTH_ADDITION = 0.08; // Base Creature 0.4

    public static void applyPulseEffects(Player player, boolean apply, int multiplier) {
        if (apply) {
            applyMovementSpeed(player, multiplier);
            applyStepHeight(player);
            applyJumpStrength(player, multiplier);
        } else {
            removeMovementSpeed(player);
            removeStepHeight(player);
            removeJumpStrength(player);
        }
    }


    private static void applyStepHeight(Player player) {
        Holder<Attribute> holder = Attributes.STEP_HEIGHT;

        AttributeInstance inst = player.getAttribute(holder);
        if (inst == null) return;

        // Replace existing to avoid stacking / stale values
        if (inst.getModifier(STEP_HEIGHT_MODIFIER_ID) != null) {
            inst.removeModifier(STEP_HEIGHT_MODIFIER_ID);
        }

        inst.addPermanentModifier(new AttributeModifier(
                STEP_HEIGHT_MODIFIER_ID,
                STEP_HEIGHT_ADDITION,
                AttributeModifier.Operation.ADD_VALUE
        ));
    }

    private static void removeStepHeight(Player player) {
        Holder<Attribute> holder = Attributes.STEP_HEIGHT;

        AttributeInstance inst = player.getAttribute(holder);
        if (inst != null) {
            inst.removeModifier(STEP_HEIGHT_MODIFIER_ID);
        }
    }

    private static void applyMovementSpeed(Player player, int multiplier) {
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed != null) {
            // Remove existing modifier if present
            AttributeModifier existingModifier = movementSpeed.getModifier(MOVEMENT_SPEED_MODIFIER_ID);
            if (existingModifier != null) {
                movementSpeed.removeModifier(MOVEMENT_SPEED_MODIFIER_ID);
            }

            // Calculate bonus based on multiplier
            double speedBonus = BASE_MOVEMENT_SPEED_BONUS * multiplier;

            // Add new modifier with the calculated bonus
            movementSpeed.addPermanentModifier(new AttributeModifier(
                    MOVEMENT_SPEED_MODIFIER_ID,
                    speedBonus,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ));
        }
    }

    private static void removeMovementSpeed(Player player) {
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed != null) {
            movementSpeed.removeModifier(MOVEMENT_SPEED_MODIFIER_ID);
        }
    }

    private static void applyJumpStrength(Player player, int multiplier) {
        Holder<Attribute> holder = Attributes.JUMP_STRENGTH;

        AttributeInstance inst = player.getAttribute(holder);
        if (inst == null) return;

        // Replace existing to avoid stacking / stale values
        if (inst.getModifier(JUMP_STRENGTH_MODIFIER_ID) != null) {
            inst.removeModifier(JUMP_STRENGTH_MODIFIER_ID);
        }

       var jump_height = JUMP_STRENGTH_ADDITION * multiplier;
        inst.addPermanentModifier(new AttributeModifier(
                JUMP_STRENGTH_MODIFIER_ID,
                jump_height,
                AttributeModifier.Operation.ADD_VALUE
        ));
    }

    private static void removeJumpStrength(Player player) {
        AttributeInstance movementSpeed = player.getAttribute(Attributes.JUMP_STRENGTH);
        if (movementSpeed != null) {
            movementSpeed.removeModifier(JUMP_STRENGTH_MODIFIER_ID);
        }
    }


    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (isCuriosEqupped(event.getEntity()) != null && event.getDistance() <= 6.0F) {
            // Cancel fall damage for falls <= 4 blocks (matching original Swift mod)
            event.setCanceled(true);
        }
    }


    public static SlotResult isCuriosEqupped(LivingEntity entity) {
        if (entity instanceof Player player && !player.level().isClientSide()) {
            var curiosHandler = CuriosApi.getCuriosInventory(entity);
            if (curiosHandler.isPresent()) {
                ICuriosItemHandler handler = curiosHandler.get();
                return handler.findFirstCurio(itemStack -> itemStack.getItem() instanceof ItemHeartPulseBelt)
                        .orElse(null);
            }
        }

        return null;
    }
}
