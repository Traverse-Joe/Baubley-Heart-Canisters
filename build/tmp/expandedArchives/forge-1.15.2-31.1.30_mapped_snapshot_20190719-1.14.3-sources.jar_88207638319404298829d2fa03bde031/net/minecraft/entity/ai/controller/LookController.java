package net.minecraft.entity.ai.controller;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class LookController {
   protected final MobEntity mob;
   protected float deltaLookYaw;
   protected float deltaLookPitch;
   protected boolean isLooking;
   protected double posX;
   protected double posY;
   protected double posZ;

   public LookController(MobEntity mob) {
      this.mob = mob;
   }

   public void func_220674_a(Vec3d p_220674_1_) {
      this.func_220679_a(p_220674_1_.x, p_220674_1_.y, p_220674_1_.z);
   }

   /**
    * Sets position to look at using entity
    */
   public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
      this.setLookPosition(entityIn.func_226277_ct_(), func_220676_b(entityIn), entityIn.func_226281_cx_(), deltaYaw, deltaPitch);
   }

   public void func_220679_a(double p_220679_1_, double p_220679_3_, double p_220679_5_) {
      this.setLookPosition(p_220679_1_, p_220679_3_, p_220679_5_, (float)this.mob.func_213396_dB(), (float)this.mob.getVerticalFaceSpeed());
   }

   /**
    * Sets position to look at
    */
   public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      this.deltaLookYaw = deltaYaw;
      this.deltaLookPitch = deltaPitch;
      this.isLooking = true;
   }

   /**
    * Updates look
    */
   public void tick() {
      if (this.func_220680_b()) {
         this.mob.rotationPitch = 0.0F;
      }

      if (this.isLooking) {
         this.isLooking = false;
         this.mob.rotationYawHead = this.func_220675_a(this.mob.rotationYawHead, this.func_220678_h(), this.deltaLookYaw);
         this.mob.rotationPitch = this.func_220675_a(this.mob.rotationPitch, this.func_220677_g(), this.deltaLookPitch);
      } else {
         this.mob.rotationYawHead = this.func_220675_a(this.mob.rotationYawHead, this.mob.renderYawOffset, 10.0F);
      }

      if (!this.mob.getNavigator().noPath()) {
         this.mob.rotationYawHead = MathHelper.func_219800_b(this.mob.rotationYawHead, this.mob.renderYawOffset, (float)this.mob.getHorizontalFaceSpeed());
      }

   }

   protected boolean func_220680_b() {
      return true;
   }

   public boolean getIsLooking() {
      return this.isLooking;
   }

   public double getLookPosX() {
      return this.posX;
   }

   public double getLookPosY() {
      return this.posY;
   }

   public double getLookPosZ() {
      return this.posZ;
   }

   protected float func_220677_g() {
      double d0 = this.posX - this.mob.func_226277_ct_();
      double d1 = this.posY - this.mob.func_226280_cw_();
      double d2 = this.posZ - this.mob.func_226281_cx_();
      double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
      return (float)(-(MathHelper.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
   }

   protected float func_220678_h() {
      double d0 = this.posX - this.mob.func_226277_ct_();
      double d1 = this.posZ - this.mob.func_226281_cx_();
      return (float)(MathHelper.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
   }

   protected float func_220675_a(float p_220675_1_, float p_220675_2_, float p_220675_3_) {
      float f = MathHelper.wrapSubtractDegrees(p_220675_1_, p_220675_2_);
      float f1 = MathHelper.clamp(f, -p_220675_3_, p_220675_3_);
      return p_220675_1_ + f1;
   }

   private static double func_220676_b(Entity p_220676_0_) {
      return p_220676_0_ instanceof LivingEntity ? p_220676_0_.func_226280_cw_() : (p_220676_0_.getBoundingBox().minY + p_220676_0_.getBoundingBox().maxY) / 2.0D;
   }
}