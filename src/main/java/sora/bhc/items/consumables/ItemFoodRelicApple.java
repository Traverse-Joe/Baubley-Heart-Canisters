package sora.bhc.items.consumables;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import sora.bhc.items.BaseItem;

public class ItemFoodRelicApple extends BaseItem {
    public ItemFoodRelicApple() {
        super("relic_apple", new Properties().food(new Food.Builder().saturation(0.5F).hunger(2).setAlwaysEdible().build()));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(ActionResultType.SUCCESS,playerIn.getHeldItem(handIn));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        super.onItemUseFinish(stack,worldIn,entityLiving);
        if(entityLiving instanceof ServerPlayerEntity){
            PlayerEntity player = (PlayerEntity) entityLiving;
            player.addPotionEffect(new EffectInstance(Effects.RESISTANCE,20*60,1));
            player.addPotionEffect(new EffectInstance(Effects.STRENGTH,20*60,1));
            player.addPotionEffect(new EffectInstance(Effects.HASTE,20*60,1));
            player.heal(20);
        }
        return stack;
    }
}
