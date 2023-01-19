package com.traverse.bhc.common.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemRelicApple extends BaseItem {


    public ItemRelicApple(){
        super(20, 0.8F);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {

        if (!worldIn.isClientSide() && entityLiving instanceof Player) {
            Player player = (Player) entityLiving;
            player.eat(worldIn,stack);
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 60, 1));
            player.heal(20);
        }


        return stack;
    }
}
