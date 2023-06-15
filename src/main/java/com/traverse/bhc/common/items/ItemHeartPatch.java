package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemHeartPatch extends BaseItem {

    protected final int amount;
    protected final int cooldown;
    protected final int durabilty;

    public ItemHeartPatch(int healAmount, int cooldown, int durabilty) {
        super();
        this.amount = healAmount;
        this.cooldown = cooldown;
        this.durabilty = durabilty;
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
        if(!worldIn.isClientSide()) {
            ItemStack stack = playerIn.getItemInHand(handIn);
            worldIn.playSound((Player) null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.NEUTRAL, 0.5F, 0.4F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
            playerIn.getCooldowns().addCooldown(stack.getItem(), cooldown);
            playerIn.heal(amount);
            if(!playerIn.isCreative()) stack.hurtAndBreak(1, playerIn, (p) -> {
                p.broadcastBreakEvent(InteractionHand.MAIN_HAND);
            });
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
        }
       return new InteractionResultHolder<>(InteractionResult.FAIL, playerIn.getItemInHand(handIn));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable(Util.makeDescriptionId("tooltip", new ResourceLocation(BaubleyHeartCanisters.MODID, "patch_amount")), amount).setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)));
        tooltip.add(Component.translatable(Util.makeDescriptionId("tooltip", new ResourceLocation(BaubleyHeartCanisters.MODID, "patch_durability")), durabilty - stack.getDamageValue()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE)));
    }

}
