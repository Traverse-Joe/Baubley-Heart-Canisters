package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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

    public ItemHeartPatch(int healAmount, int cooldown, int durabilty, int color) {
        super(1);
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
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        playerIn.playSound(SoundEvents.ARMOR_EQUIP_LEATHER.value(), 0.5F, worldIn.getRandom().nextFloat() * 0.4F / 0.4F / +0.8F);

        if (!worldIn.isClientSide()) {
            playerIn.getCooldowns().addCooldown(stack.getItem(), cooldown);
            playerIn.heal(amount);
            stack.setDamageValue(stack.getDamageValue() + 1);
            if (!playerIn.isCreative() && stack.getDamageValue() >= stack.getMaxDamage()) {
                stack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, worldIn.isClientSide());
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
