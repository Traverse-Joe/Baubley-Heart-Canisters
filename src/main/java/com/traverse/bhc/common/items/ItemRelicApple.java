package com.traverse.bhc.common.items;

import com.traverse.bhc.common.config.ConfigHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ItemRelicApple extends BaseItem {


    public ItemRelicApple(){
        super(20, 5.0F);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {

        if (!worldIn.isClientSide() && entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            player.eat(worldIn,stack);
            player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 20 * 60, 1));
            player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 20 * 60, 1));
            player.addEffect(new EffectInstance(Effects.DIG_SPEED, 20 * 60, 1));
            player.heal(20);
            //stack.shrink(1);
        }


        return stack;
    }
}
