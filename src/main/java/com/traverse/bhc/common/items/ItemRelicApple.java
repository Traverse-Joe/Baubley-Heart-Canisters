package com.traverse.bhc.common.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemRelicApple extends BaseItem {


    public ItemRelicApple() {
        super(20, 0.8F);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide()) {
            entityLiving.eat(worldIn, stack);
            entityLiving.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 1));
            entityLiving.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 1));
            entityLiving.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 60, 1));
            entityLiving.heal(20);
        }

        return stack;
    }
}
