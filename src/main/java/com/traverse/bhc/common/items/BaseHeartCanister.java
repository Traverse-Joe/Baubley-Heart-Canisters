package com.traverse.bhc.common.items;

import com.traverse.bhc.common.util.ConfigHandler;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.UUID;

public class BaseHeartCanister extends BaseItem{

    private static final UUID HEALTH_MODIFIER = UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3");
    public static final String HEALTH_MODIFIER_NAME = "BHC Health Modifier";

    protected HeartType type;
    public BaseHeartCanister(HeartType type){
        this.type = type;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return ConfigHandler.general.heartStackSize.get();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if(!worldIn.isRemote && entityLiving instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entityLiving;
                stack.shrink(1);
        }
        return stack;
    }
}
