package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ItemHeartPatch extends BaseItem {

    protected final Supplier<Integer> amountSupplier;
    protected final Supplier<Integer> cooldownSupplier;
    protected final int durabilty;
    protected final int color;

    public ItemHeartPatch(Properties properties, Supplier<Integer> healAmount, Supplier<Integer> cooldownSeconds, int durabilty, int color) {
        super(properties);
        this.amountSupplier = healAmount;
        this.cooldownSupplier = cooldownSeconds;
        this.durabilty = durabilty;
        this.color = color;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return durabilty;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER.value(), 0.5F, level.getRandom().nextFloat() * 0.4F / 0.4F / +0.8F);

        if (!level.isClientSide()) {
            player.getCooldowns().addCooldown(stack, cooldownSupplier.get() * 20);
            player.heal(amountSupplier.get());
            stack.setDamageValue(stack.getDamageValue() + 1);
            if (!player.isCreative() && stack.getDamageValue() >= stack.getMaxDamage()) {
                stack.shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        // TODO represent the heart colors
        return color;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
        tooltipComponents.accept(Component.translatable(Util.makeDescriptionId("tooltip", BaubleyHeartCanisters.id("patch_amount")), amountSupplier.get()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)));
        tooltipComponents.accept(Component.translatable(Util.makeDescriptionId("tooltip", BaubleyHeartCanisters.id("patch_durability")), durabilty - stack.getDamageValue()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE)));
    }
}
