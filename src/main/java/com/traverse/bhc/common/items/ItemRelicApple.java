package com.traverse.bhc.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ItemRelicApple extends BaseItem {

    public ItemRelicApple(){
        super(2, 0.5f);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if(!worldIn.isRemote) {
            if (entityLiving instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entityLiving;
                player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 20 * 60, 1));
                player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 20 * 60, 1));
                player.addPotionEffect(new EffectInstance(Effects.HASTE, 20 * 60, 1));
                player.heal(20);
            }
        }
        return stack;
    }
}
