package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemHeartPatch extends BaseItem {

    protected final int amount;
    protected final int cooldown;
    protected final int durabilty;
    protected final int color;

    public ItemHeartPatch(Properties properties, int healAmount, int cooldown, int durabilty, int color) {
        super(properties);
        this.amount = healAmount;
        this.cooldown = cooldown;
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
            player.getCooldowns().addCooldown(stack, cooldown);
            player.heal(amount);
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", BaubleyHeartCanisters.id("patch_amount")), amount).setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)));
        tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", BaubleyHeartCanisters.id("patch_durability")), durabilty - stack.getDamageValue()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE)));
    }
}
