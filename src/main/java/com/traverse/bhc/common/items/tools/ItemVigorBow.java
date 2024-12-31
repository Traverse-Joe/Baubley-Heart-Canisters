package com.traverse.bhc.common.items.tools;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.VigorBowContainer;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.HealthModifier;
import com.traverse.bhc.common.util.SoulContainerProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;
import java.util.stream.IntStream;

public class ItemVigorBow extends BowItem implements SoulContainerProvider {

    public static final ResourceLocation DAMAGE_MODIFIER_ID = BaubleyHeartCanisters.id("vigor_bow");
    private static final double EXTRA_DAMAGE_PER_HEART = 1.0F;

    public ItemVigorBow() {
        super(new Item.Properties());
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
        arrow.setBaseDamage(arrow.getBaseDamage() + (HealthModifier.getHeartCount(weaponStack)/2));
        return arrow;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        Level level = entity.level();
        Player player = (Player) entity;
        if (entity.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                this.openMenu(player, hand, VigorBowContainer::new);

            }

        }
        return super.onEntitySwing(stack, entity, hand);
    }



    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            ItemStack itemstack = player.getProjectile(stack);
            int i = (int) ((this.getUseDuration(stack, entity) - timeLeft) * (Math.max(HealthModifier.getHeartCount(stack)/2, 1)));
            i = EventHooks.onArrowLoose(stack, level, player, i, !itemstack.isEmpty());
            if (i < 0) return;

            float f = getPowerForTime(i);
            if (!((double) f < 0.1)) {
                List<ItemStack> list = draw(stack, itemstack, player);
                if (level instanceof ServerLevel serverlevel && !list.isEmpty()) {
                    this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, f * 3.0F , 1.0F / (Math.max(HealthModifier.getHeartCount(stack)/2, 1)), f == 1.0F, null);
                }

                level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.ARROW_SHOOT,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
                );
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public Component getContainerName(ItemStack stack) {
        return Component.translatable("container.bhc.vigor_bow");
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", RegistryHandler.VIGOR_BOW.getId())).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        if(HealthModifier.getHeartCount(stack) > 0) {
            tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", BaubleyHeartCanisters.id("bonus")), HealthModifier.getHeartCount(stack)).setStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE)));
        }
        if(Screen.hasShiftDown()) {
            int[] heartCount = new int[]{HealthModifier.getHeartCount(stack)};
            int heartTotal = IntStream.of(heartCount).sum();
            tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", BaubleyHeartCanisters.id("heart_amount")), heartTotal).setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)));
        }

    }
}
