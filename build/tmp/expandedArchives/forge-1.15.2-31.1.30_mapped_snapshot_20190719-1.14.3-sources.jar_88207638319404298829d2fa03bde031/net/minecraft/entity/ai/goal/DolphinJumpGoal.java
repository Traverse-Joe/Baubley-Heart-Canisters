package net.minecraft.entity.ai.goal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DolphinJumpGoal extends JumpGoal {
   private static final int[] field_220710_a = new int[]{0, 1, 4, 5, 6, 7};
   private final DolphinEntity field_220711_b;
   private final int field_220712_c;
   private boolean field_220713_d;

   public DolphinJumpGoal(DolphinEntity p_i50329_1_, int p_i50329_2_) {
      this.field_220711_b = p_i50329_1_;
      this.field_220712_c = p_i50329_2_;
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (this.field_220711_b.getRNG().nextInt(this.field_220712_c) != 0) {
         return false;
      } else {
         Direction direction = this.field_220711_b.getAdjustedHorizontalFacing();
         int i = direction.getXOffset();
         int j = direction.getZOffset();
         BlockPos blockpos = new BlockPos(this.field_220711_b);

         for(int k : field_220710_a) {
            if (!this.func_220709_a(blockpos, i, j, k) || !this.func_220708_b(blockpos, i, j, k)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean func_220709_a(BlockPos p_220709_1_, int p_220709_2_, int p_220709_3_, int p_220709_4_) {
      BlockPos blockpos = p_220709_1_.add(p_220709_2_ * p_220709_4_, 0, p_220709_3_ * p_220709_4_);
      return this.field_220711_b.world.getFluidState(blockpos).isTagged(FluidTags.WATER) && !this.field_220711_b.world.getBlockState(blockpos).getMaterial().blocksMovement();
   }

   private boolean func_220708_b(BlockPos p_220708_1_, int p_220708_2_, int p_220708_3_, int p_220708_4_) {
      return this.field_220711_b.world.getBlockState(p_220708_1_.add(p_220708_2_ * p_220708_4_, 1, p_220708_3_ * p_220708_4_)).isAir() && this.field_220711_b.world.getBlockState(p_220708_1_.add(p_220708_2_ * p_220708_4_, 2, p_220708_3_ * p_220708_4_)).isAir();
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      double d0 = this.field_220711_b.getMotion().y;
      return (!(d0 * d0 < (double)0.03F) || this.field_220711_b.rotationPitch == 0.0F || !(Math.abs(this.field_220711_b.rotationPitch) < 10.0F) || !this.field_220711_b.isInWater()) && !this.field_220711_b.onGround;
   }

   public boolean isPreemptible() {
      return false;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      Direction direction = this.field_220711_b.getAdjustedHorizontalFacing();
      this.field_220711_b.setMotion(this.field_220711_b.getMotion().add((double)direction.getXOffset() * 0.6D, 0.7D, (double)direction.getZOffset() * 0.6D));
      this.field_220711_b.getNavigator().clearPath();
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_220711_b.rotationPitch = 0.0F;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      boolean flag = this.field_220713_d;
      if (!flag) {
         IFluidState ifluidstate = this.field_220711_b.world.getFluidState(new BlockPos(this.field_220711_b));
         this.field_220713_d = ifluidstate.isTagged(FluidTags.WATER);
      }

      if (this.field_220713_d && !flag) {
         this.field_220711_b.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
      }

      Vec3d vec3d = this.field_220711_b.getMotion();
      if (vec3d.y * vec3d.y < (double)0.03F && this.field_220711_b.rotationPitch != 0.0F) {
         this.field_220711_b.rotationPitch = MathHelper.func_226167_j_(this.field_220711_b.rotationPitch, 0.0F, 0.2F);
      } else {
         double d0 = Math.sqrt(Entity.func_213296_b(vec3d));
         double d1 = Math.signum(-vec3d.y) * Math.acos(d0 / vec3d.length()) * (double)(180F / (float)Math.PI);
         this.field_220711_b.rotationPitch = (float)d1;
      }

   }
}