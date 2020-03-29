package net.minecraft.entity.ai.goal;

import java.util.List;
import net.minecraft.entity.passive.AnimalEntity;

public class FollowParentGoal extends Goal {
   private final AnimalEntity field_75348_a;
   private AnimalEntity field_75346_b;
   private final double moveSpeed;
   private int delayCounter;

   public FollowParentGoal(AnimalEntity animal, double speed) {
      this.field_75348_a = animal;
      this.moveSpeed = speed;
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (this.field_75348_a.getGrowingAge() >= 0) {
         return false;
      } else {
         List<AnimalEntity> list = this.field_75348_a.world.getEntitiesWithinAABB(this.field_75348_a.getClass(), this.field_75348_a.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
         AnimalEntity animalentity = null;
         double d0 = Double.MAX_VALUE;

         for(AnimalEntity animalentity1 : list) {
            if (animalentity1.getGrowingAge() >= 0) {
               double d1 = this.field_75348_a.getDistanceSq(animalentity1);
               if (!(d1 > d0)) {
                  d0 = d1;
                  animalentity = animalentity1;
               }
            }
         }

         if (animalentity == null) {
            return false;
         } else if (d0 < 9.0D) {
            return false;
         } else {
            this.field_75346_b = animalentity;
            return true;
         }
      }
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      if (this.field_75348_a.getGrowingAge() >= 0) {
         return false;
      } else if (!this.field_75346_b.isAlive()) {
         return false;
      } else {
         double d0 = this.field_75348_a.getDistanceSq(this.field_75346_b);
         return !(d0 < 9.0D) && !(d0 > 256.0D);
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.delayCounter = 0;
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75346_b = null;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (--this.delayCounter <= 0) {
         this.delayCounter = 10;
         this.field_75348_a.getNavigator().tryMoveToEntityLiving(this.field_75346_b, this.moveSpeed);
      }
   }
}