package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.Vec3d;

public class LeapAtTargetGoal extends Goal {
   private final MobEntity leaper;
   private LivingEntity leapTarget;
   private final float leapMotionY;

   public LeapAtTargetGoal(MobEntity leapingEntity, float leapMotionYIn) {
      this.leaper = leapingEntity;
      this.leapMotionY = leapMotionYIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (this.leaper.isBeingRidden()) {
         return false;
      } else {
         this.leapTarget = this.leaper.getAttackTarget();
         if (this.leapTarget == null) {
            return false;
         } else {
            double d0 = this.leaper.getDistanceSq(this.leapTarget);
            if (!(d0 < 4.0D) && !(d0 > 16.0D)) {
               if (!this.leaper.onGround) {
                  return false;
               } else {
                  return this.leaper.getRNG().nextInt(5) == 0;
               }
            } else {
               return false;
            }
         }
      }
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return !this.leaper.onGround;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      Vec3d vec3d = this.leaper.getMotion();
      Vec3d vec3d1 = new Vec3d(this.leapTarget.func_226277_ct_() - this.leaper.func_226277_ct_(), 0.0D, this.leapTarget.func_226281_cx_() - this.leaper.func_226281_cx_());
      if (vec3d1.lengthSquared() > 1.0E-7D) {
         vec3d1 = vec3d1.normalize().scale(0.4D).add(vec3d.scale(0.2D));
      }

      this.leaper.setMotion(vec3d1.x, (double)this.leapMotionY, vec3d1.z);
   }
}