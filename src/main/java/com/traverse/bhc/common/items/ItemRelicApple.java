package com.traverse.bhc.common.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;

public class ItemRelicApple extends BaseItem {


    public ItemRelicApple(Properties properties) {
        super(properties, 20, 0.8F);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Consumable consumable = stack.get(DataComponents.CONSUMABLE);
        if (!level.isClientSide() && consumable != null) {
            consumable.onConsume(level, entityLiving, stack);
            entityLiving.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 20 * 60, 1));
            entityLiving.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 20 * 60, 1));
            entityLiving.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 20 * 60, 1));
            entityLiving.heal(20);
        }

        return stack;
    }
}
