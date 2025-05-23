package com.traverse.bhc.common.items;

import com.traverse.bhc.common.util.HeartType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class ItemHeart extends BaseItem {

    protected final HeartType type;

    public ItemHeart(Properties properties, HeartType type) {
        super(properties);
        this.type = type;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 30;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.SUCCESS;
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
