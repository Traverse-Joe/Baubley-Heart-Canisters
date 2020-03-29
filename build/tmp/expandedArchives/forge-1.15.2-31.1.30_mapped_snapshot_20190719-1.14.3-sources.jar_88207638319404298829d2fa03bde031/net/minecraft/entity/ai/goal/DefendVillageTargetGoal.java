package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class DefendVillageTargetGoal extends TargetGoal {
   private final IronGolemEntity field_75305_a;
   private LivingEntity field_75304_b;
   private final EntityPredicate field_223190_c = (new EntityPredicate()).setDistance(64.0D);

   public DefendVillageTargetGoal(IronGolemEntity ironGolemIn) {
      super(ironGolemIn, false, true);
      this.field_75305_a = ironGolemIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      AxisAlignedBB axisalignedbb = this.field_75305_a.getBoundingBox().grow(10.0D, 8.0D, 10.0D);
      List<LivingEntity> list = this.field_75305_a.world.getTargettableEntitiesWithinAABB(VillagerEntity.class, this.field_223190_c, this.field_75305_a, axisalignedbb);
      List<PlayerEntity> list1 = this.field_75305_a.world.getTargettablePlayersWithinAABB(this.field_223190_c, this.field_75305_a, axisalignedbb);

      for(LivingEntity livingentity : list) {
         VillagerEntity villagerentity = (VillagerEntity)livingentity;

         for(PlayerEntity playerentity : list1) {
            int i = villagerentity.func_223107_f(playerentity);
            if (i <= -100) {
               this.field_75304_b = playerentity;
            }
         }
      }

      if (this.field_75304_b == null) {
         return false;
      } else {
         return !(this.field_75304_b instanceof PlayerEntity) || !this.field_75304_b.isSpectator() && !((PlayerEntity)this.field_75304_b).isCreative();
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.field_75305_a.setAttackTarget(this.field_75304_b);
      super.startExecuting();
   }
}