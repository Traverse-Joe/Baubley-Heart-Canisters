package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;

public class RangedAttackGoal extends Goal {
   private final MobEntity field_75322_b;
   private final IRangedAttackMob rangedAttackEntityHost;
   private LivingEntity field_75323_c;
   private int rangedAttackTime = -1;
   private final double entityMoveSpeed;
   private int seeTime;
   private final int attackIntervalMin;
   private final int maxRangedAttackTime;
   private final float attackRadius;
   private final float maxAttackDistance;

   public RangedAttackGoal(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
      this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
   }

   public RangedAttackGoal(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
      if (!(attacker instanceof LivingEntity)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      } else {
         this.rangedAttackEntityHost = attacker;
         this.field_75322_b = (MobEntity)attacker;
         this.entityMoveSpeed = movespeed;
         this.attackIntervalMin = p_i1650_4_;
         this.maxRangedAttackTime = maxAttackTime;
         this.attackRadius = maxAttackDistanceIn;
         this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      LivingEntity livingentity = this.field_75322_b.getAttackTarget();
      if (livingentity != null && livingentity.isAlive()) {
         this.field_75323_c = livingentity;
         return true;
      } else {
         return false;
      }
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return this.shouldExecute() || !this.field_75322_b.getNavigator().noPath();
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75323_c = null;
      this.seeTime = 0;
      this.rangedAttackTime = -1;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      double d0 = this.field_75322_b.getDistanceSq(this.field_75323_c.func_226277_ct_(), this.field_75323_c.func_226278_cu_(), this.field_75323_c.func_226281_cx_());
      boolean flag = this.field_75322_b.getEntitySenses().canSee(this.field_75323_c);
      if (flag) {
         ++this.seeTime;
      } else {
         this.seeTime = 0;
      }

      if (!(d0 > (double)this.maxAttackDistance) && this.seeTime >= 5) {
         this.field_75322_b.getNavigator().clearPath();
      } else {
         this.field_75322_b.getNavigator().tryMoveToEntityLiving(this.field_75323_c, this.entityMoveSpeed);
      }

      this.field_75322_b.getLookController().setLookPositionWithEntity(this.field_75323_c, 30.0F, 30.0F);
      if (--this.rangedAttackTime == 0) {
         if (!flag) {
            return;
         }

         float f = MathHelper.sqrt(d0) / this.attackRadius;
         float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
         this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.field_75323_c, lvt_5_1_);
         this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
      } else if (this.rangedAttackTime < 0) {
         float f2 = MathHelper.sqrt(d0) / this.attackRadius;
         this.rangedAttackTime = MathHelper.floor(f2 * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
      }

   }
}