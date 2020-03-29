package net.minecraft.entity.ai.brain;

import java.util.Comparator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;

public class BrainUtil {
   public static void func_220618_a(LivingEntity p_220618_0_, LivingEntity p_220618_1_) {
      func_220616_b(p_220618_0_, p_220618_1_);
      func_220626_d(p_220618_0_, p_220618_1_);
   }

   public static boolean canSee(Brain<?> p_220619_0_, LivingEntity p_220619_1_) {
      return p_220619_0_.getMemory(MemoryModuleType.VISIBLE_MOBS).filter((p_220614_1_) -> {
         return p_220614_1_.contains(p_220619_1_);
      }).isPresent();
   }

   public static boolean isCorrectVisibleType(Brain<?> p_220623_0_, MemoryModuleType<? extends LivingEntity> p_220623_1_, EntityType<?> p_220623_2_) {
      return p_220623_0_.getMemory(p_220623_1_).filter((p_220622_1_) -> {
         return p_220622_1_.getType() == p_220623_2_;
      }).filter(LivingEntity::isAlive).filter((p_220615_1_) -> {
         return canSee(p_220623_0_, p_220615_1_);
      }).isPresent();
   }

   public static void func_220616_b(LivingEntity p_220616_0_, LivingEntity p_220616_1_) {
      lookAt(p_220616_0_, p_220616_1_);
      lookAt(p_220616_1_, p_220616_0_);
   }

   public static void lookAt(LivingEntity p_220625_0_, LivingEntity p_220625_1_) {
      p_220625_0_.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(p_220625_1_));
   }

   public static void func_220626_d(LivingEntity p_220626_0_, LivingEntity p_220626_1_) {
      int i = 2;
      approach(p_220626_0_, p_220626_1_, 2);
      approach(p_220626_1_, p_220626_0_, 2);
   }

   public static void approach(LivingEntity living, LivingEntity target, int targetDistance) {
      float f = (float)living.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
      EntityPosWrapper entityposwrapper = new EntityPosWrapper(target);
      WalkTarget walktarget = new WalkTarget(entityposwrapper, f, targetDistance);
      living.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, entityposwrapper);
      living.getBrain().setMemory(MemoryModuleType.WALK_TARGET, walktarget);
   }

   public static void throwItemAt(LivingEntity from, ItemStack stack, LivingEntity to) {
      double d0 = from.func_226280_cw_() - (double)0.3F;
      ItemEntity itementity = new ItemEntity(from.world, from.func_226277_ct_(), d0, from.func_226281_cx_(), stack);
      BlockPos blockpos = new BlockPos(to);
      BlockPos blockpos1 = new BlockPos(from);
      float f = 0.3F;
      Vec3d vec3d = new Vec3d(blockpos.subtract(blockpos1));
      vec3d = vec3d.normalize().scale((double)0.3F);
      itementity.setMotion(vec3d);
      itementity.setDefaultPickupDelay();
      from.world.addEntity(itementity);
   }

   public static SectionPos func_220617_a(ServerWorld p_220617_0_, SectionPos p_220617_1_, int p_220617_2_) {
      int i = p_220617_0_.func_217486_a(p_220617_1_);
      return SectionPos.getAllInBox(p_220617_1_, p_220617_2_).filter((p_220620_2_) -> {
         return p_220617_0_.func_217486_a(p_220620_2_) < i;
      }).min(Comparator.comparingInt(p_220617_0_::func_217486_a)).orElse(p_220617_1_);
   }
}