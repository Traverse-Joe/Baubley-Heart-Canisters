package com.traverse.bhc.common.items;

import com.traverse.bhc.common.util.HeartType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemHeart extends BaseItem {

    protected final HeartType type;

    public ItemHeart(HeartType type) {
        super();
        this.type = type;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 30;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        playerIn.startUsingItem(handIn);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide()) {
            entityLiving.heal(this.type.healAmount);

            if (!(entityLiving instanceof Player player) || !player.isCreative()) {
                stack.shrink(1);
            }

            if(entityLiving instanceof ServerPlayer player) {
                player.resetSentInfo();
            }
        }

        return stack;
    }


}
