package net.minecraft.block;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class PumpkinBlock extends StemGrownBlock {
   protected PumpkinBlock(Block.Properties builder) {
      super(builder);
   }

   public ActionResultType func_225533_a_(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
      ItemStack itemstack = p_225533_4_.getHeldItem(p_225533_5_);
      if (itemstack.getItem() == Items.SHEARS) {
         if (!p_225533_2_.isRemote) {
            Direction direction = p_225533_6_.getFace();
            Direction direction1 = direction.getAxis() == Direction.Axis.Y ? p_225533_4_.getHorizontalFacing().getOpposite() : direction;
            p_225533_2_.playSound((PlayerEntity)null, p_225533_3_, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            p_225533_2_.setBlockState(p_225533_3_, Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, direction1), 11);
            ItemEntity itementity = new ItemEntity(p_225533_2_, (double)p_225533_3_.getX() + 0.5D + (double)direction1.getXOffset() * 0.65D, (double)p_225533_3_.getY() + 0.1D, (double)p_225533_3_.getZ() + 0.5D + (double)direction1.getZOffset() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
            itementity.setMotion(0.05D * (double)direction1.getXOffset() + p_225533_2_.rand.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction1.getZOffset() + p_225533_2_.rand.nextDouble() * 0.02D);
            p_225533_2_.addEntity(itementity);
            itemstack.damageItem(1, p_225533_4_, (p_220282_1_) -> {
               p_220282_1_.sendBreakAnimation(p_225533_5_);
            });
         }

         return ActionResultType.SUCCESS;
      } else {
         return super.func_225533_a_(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
      }
   }

   public StemBlock getStem() {
      return (StemBlock)Blocks.PUMPKIN_STEM;
   }

   public AttachedStemBlock getAttachedStem() {
      return (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM;
   }
}