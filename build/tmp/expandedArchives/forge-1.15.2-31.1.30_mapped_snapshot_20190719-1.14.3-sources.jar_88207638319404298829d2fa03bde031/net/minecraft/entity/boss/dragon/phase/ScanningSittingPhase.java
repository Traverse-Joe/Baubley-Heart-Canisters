package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ScanningSittingPhase extends SittingPhase {
   private static final EntityPredicate field_221115_b = (new EntityPredicate()).setDistance(150.0D);
   private final EntityPredicate field_221116_c;
   private int scanningTime;

   public ScanningSittingPhase(EnderDragonEntity dragonIn) {
      super(dragonIn);
      this.field_221116_c = (new EntityPredicate()).setDistance(20.0D).setCustomPredicate((p_221114_1_) -> {
         return Math.abs(p_221114_1_.func_226278_cu_() - dragonIn.func_226278_cu_()) <= 10.0D;
      });
   }

   /**
    * Gives the phase a chance to update its status.
    * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
    */
   public void serverTick() {
      ++this.scanningTime;
      LivingEntity livingentity = this.dragon.world.getClosestPlayer(this.field_221116_c, this.dragon, this.dragon.func_226277_ct_(), this.dragon.func_226278_cu_(), this.dragon.func_226281_cx_());
      if (livingentity != null) {
         if (this.scanningTime > 25) {
            this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_ATTACKING);
         } else {
            Vec3d vec3d = (new Vec3d(livingentity.func_226277_ct_() - this.dragon.func_226277_ct_(), 0.0D, livingentity.func_226281_cx_() - this.dragon.func_226281_cx_())).normalize();
            Vec3d vec3d1 = (new Vec3d((double)MathHelper.sin(this.dragon.rotationYaw * ((float)Math.PI / 180F)), 0.0D, (double)(-MathHelper.cos(this.dragon.rotationYaw * ((float)Math.PI / 180F))))).normalize();
            float f = (float)vec3d1.dotProduct(vec3d);
            float f1 = (float)(Math.acos((double)f) * (double)(180F / (float)Math.PI)) + 0.5F;
            if (f1 < 0.0F || f1 > 10.0F) {
               double d0 = livingentity.func_226277_ct_() - this.dragon.field_70986_h.func_226277_ct_();
               double d1 = livingentity.func_226281_cx_() - this.dragon.field_70986_h.func_226281_cx_();
               double d2 = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d0, d1) * (double)(180F / (float)Math.PI) - (double)this.dragon.rotationYaw), -100.0D, 100.0D);
               this.dragon.field_226525_bB_ *= 0.8F;
               float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) + 1.0F;
               float f3 = f2;
               if (f2 > 40.0F) {
                  f2 = 40.0F;
               }

               this.dragon.field_226525_bB_ = (float)((double)this.dragon.field_226525_bB_ + d2 * (double)(0.7F / f2 / f3));
               this.dragon.rotationYaw += this.dragon.field_226525_bB_;
            }
         }
      } else if (this.scanningTime >= 100) {
         livingentity = this.dragon.world.getClosestPlayer(field_221115_b, this.dragon, this.dragon.func_226277_ct_(), this.dragon.func_226278_cu_(), this.dragon.func_226281_cx_());
         this.dragon.getPhaseManager().setPhase(PhaseType.TAKEOFF);
         if (livingentity != null) {
            this.dragon.getPhaseManager().setPhase(PhaseType.CHARGING_PLAYER);
            this.dragon.getPhaseManager().getPhase(PhaseType.CHARGING_PLAYER).setTarget(new Vec3d(livingentity.func_226277_ct_(), livingentity.func_226278_cu_(), livingentity.func_226281_cx_()));
         }
      }

   }

   /**
    * Called when this phase is set to active
    */
   public void initPhase() {
      this.scanningTime = 0;
   }

   public PhaseType<ScanningSittingPhase> getType() {
      return PhaseType.SITTING_SCANNING;
   }
}