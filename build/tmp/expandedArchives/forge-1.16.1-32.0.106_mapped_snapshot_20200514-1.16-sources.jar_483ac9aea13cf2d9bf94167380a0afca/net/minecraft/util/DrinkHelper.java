package net.minecraft.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DrinkHelper {
   public static ActionResult<ItemStack> func_234707_a_(World p_234707_0_, PlayerEntity p_234707_1_, Hand p_234707_2_) {
      p_234707_1_.setActiveHand(p_234707_2_);
      return ActionResult.resultConsume(p_234707_1_.getHeldItem(p_234707_2_));
   }

   public static ItemStack func_241445_a_(ItemStack p_241445_0_, PlayerEntity p_241445_1_, ItemStack p_241445_2_) {
      if (p_241445_1_.abilities.isCreativeMode) {
         if (!p_241445_1_.inventory.hasItemStack(p_241445_2_)) {
            p_241445_1_.inventory.addItemStackToInventory(p_241445_2_);
         }

         return p_241445_0_;
      } else {
         p_241445_0_.shrink(1);
         if (p_241445_0_.isEmpty()) {
            return p_241445_2_;
         } else {
            if (!p_241445_1_.inventory.addItemStackToInventory(p_241445_2_)) {
               p_241445_1_.dropItem(p_241445_2_, false);
            }

            return p_241445_0_;
         }
      }
   }
}