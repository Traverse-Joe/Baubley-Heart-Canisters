package net.minecraft.entity.merchant.villager;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtCustomerGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.entity.ai.goal.UseItemGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WanderingTraderEntity extends AbstractVillagerEntity {
   @Nullable
   private BlockPos wanderTarget;
   private int despawnDelay;

   public WanderingTraderEntity(EntityType<? extends WanderingTraderEntity> p_i50178_1_, World p_i50178_2_) {
      super(p_i50178_1_, p_i50178_2_);
      this.forceSpawn = true;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new SwimGoal(this));
      this.goalSelector.addGoal(0, new UseItemGoal<>(this, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.INVISIBILITY), SoundEvents.ENTITY_WANDERING_TRADER_DISAPPEARED, (p_213733_1_) -> {
         return !this.world.isDaytime() && !p_213733_1_.isInvisible();
      }));
      this.goalSelector.addGoal(0, new UseItemGoal<>(this, new ItemStack(Items.MILK_BUCKET), SoundEvents.ENTITY_WANDERING_TRADER_REAPPEARED, (p_213736_1_) -> {
         return this.world.isDaytime() && p_213736_1_.isInvisible();
      }));
      this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
      this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ZombieEntity.class, 8.0F, 0.5D, 0.5D));
      this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, EvokerEntity.class, 12.0F, 0.5D, 0.5D));
      this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, VindicatorEntity.class, 8.0F, 0.5D, 0.5D));
      this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, VexEntity.class, 8.0F, 0.5D, 0.5D));
      this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PillagerEntity.class, 15.0F, 0.5D, 0.5D));
      this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, IllusionerEntity.class, 12.0F, 0.5D, 0.5D));
      this.goalSelector.addGoal(1, new PanicGoal(this, 0.5D));
      this.goalSelector.addGoal(1, new LookAtCustomerGoal(this));
      this.goalSelector.addGoal(2, new WanderingTraderEntity.MoveToGoal(this, 2.0D, 0.35D));
      this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35D));
      this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.35D));
      this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
   }

   @Nullable
   public AgeableEntity createChild(AgeableEntity ageable) {
      return null;
   }

   public boolean func_213705_dZ() {
      return false;
   }

   public boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      boolean flag = itemstack.getItem() == Items.NAME_TAG;
      if (flag) {
         itemstack.interactWithEntity(player, this, hand);
         return true;
      } else if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.func_213716_dX() && !this.isChild()) {
         if (hand == Hand.MAIN_HAND) {
            player.addStat(Stats.TALKED_TO_VILLAGER);
         }

         if (this.getOffers().isEmpty()) {
            return super.processInteract(player, hand);
         } else {
            if (!this.world.isRemote) {
               this.setCustomer(player);
               this.func_213707_a(player, this.getDisplayName(), 1);
            }

            return true;
         }
      } else {
         return super.processInteract(player, hand);
      }
   }

   protected void populateTradeData() {
      VillagerTrades.ITrade[] avillagertrades$itrade = VillagerTrades.field_221240_b.get(1);
      VillagerTrades.ITrade[] avillagertrades$itrade1 = VillagerTrades.field_221240_b.get(2);
      if (avillagertrades$itrade != null && avillagertrades$itrade1 != null) {
         MerchantOffers merchantoffers = this.getOffers();
         this.addTrades(merchantoffers, avillagertrades$itrade, 5);
         int i = this.rand.nextInt(avillagertrades$itrade1.length);
         VillagerTrades.ITrade villagertrades$itrade = avillagertrades$itrade1[i];
         MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.rand);
         if (merchantoffer != null) {
            merchantoffers.add(merchantoffer);
         }

      }
   }

   public void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      compound.putInt("DespawnDelay", this.despawnDelay);
      if (this.wanderTarget != null) {
         compound.put("WanderTarget", NBTUtil.writeBlockPos(this.wanderTarget));
      }

   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      if (compound.contains("DespawnDelay", 99)) {
         this.despawnDelay = compound.getInt("DespawnDelay");
      }

      if (compound.contains("WanderTarget")) {
         this.wanderTarget = NBTUtil.readBlockPos(compound.getCompound("WanderTarget"));
      }

      this.setGrowingAge(Math.max(0, this.getGrowingAge()));
   }

   public boolean canDespawn(double distanceToClosestPlayer) {
      return false;
   }

   protected void func_213713_b(MerchantOffer p_213713_1_) {
      if (p_213713_1_.func_222221_q()) {
         int i = 3 + this.rand.nextInt(4);
         this.world.addEntity(new ExperienceOrbEntity(this.world, this.func_226277_ct_(), this.func_226278_cu_() + 0.5D, this.func_226281_cx_(), i));
      }

   }

   protected SoundEvent getAmbientSound() {
      return this.func_213716_dX() ? SoundEvents.ENTITY_WANDERING_TRADER_TRADE : SoundEvents.ENTITY_WANDERING_TRADER_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_WANDERING_TRADER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_WANDERING_TRADER_DEATH;
   }

   protected SoundEvent getDrinkSound(ItemStack p_213351_1_) {
      Item item = p_213351_1_.getItem();
      return item == Items.MILK_BUCKET ? SoundEvents.ENTITY_WANDERING_TRADER_DRINK_MILK : SoundEvents.ENTITY_WANDERING_TRADER_DRINK_POTION;
   }

   protected SoundEvent func_213721_r(boolean p_213721_1_) {
      return p_213721_1_ ? SoundEvents.ENTITY_WANDERING_TRADER_YES : SoundEvents.ENTITY_WANDERING_TRADER_NO;
   }

   public SoundEvent func_213714_ea() {
      return SoundEvents.ENTITY_WANDERING_TRADER_YES;
   }

   public void func_213728_s(int p_213728_1_) {
      this.despawnDelay = p_213728_1_;
   }

   public int func_213735_eg() {
      return this.despawnDelay;
   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      super.livingTick();
      if (!this.world.isRemote) {
         this.func_222821_eh();
      }

   }

   private void func_222821_eh() {
      if (this.despawnDelay > 0 && !this.func_213716_dX() && --this.despawnDelay == 0) {
         this.remove();
      }

   }

   public void func_213726_g(@Nullable BlockPos p_213726_1_) {
      this.wanderTarget = p_213726_1_;
   }

   @Nullable
   private BlockPos func_213727_eh() {
      return this.wanderTarget;
   }

   class MoveToGoal extends Goal {
      final WanderingTraderEntity field_220847_a;
      final double field_220848_b;
      final double field_220849_c;

      MoveToGoal(WanderingTraderEntity p_i50459_2_, double p_i50459_3_, double p_i50459_5_) {
         this.field_220847_a = p_i50459_2_;
         this.field_220848_b = p_i50459_3_;
         this.field_220849_c = p_i50459_5_;
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         this.field_220847_a.func_213726_g((BlockPos)null);
         WanderingTraderEntity.this.navigator.clearPath();
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         BlockPos blockpos = this.field_220847_a.func_213727_eh();
         return blockpos != null && this.func_220846_a(blockpos, this.field_220848_b);
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         BlockPos blockpos = this.field_220847_a.func_213727_eh();
         if (blockpos != null && WanderingTraderEntity.this.navigator.noPath()) {
            if (this.func_220846_a(blockpos, 10.0D)) {
               Vec3d vec3d = (new Vec3d((double)blockpos.getX() - this.field_220847_a.func_226277_ct_(), (double)blockpos.getY() - this.field_220847_a.func_226278_cu_(), (double)blockpos.getZ() - this.field_220847_a.func_226281_cx_())).normalize();
               Vec3d vec3d1 = vec3d.scale(10.0D).add(this.field_220847_a.func_226277_ct_(), this.field_220847_a.func_226278_cu_(), this.field_220847_a.func_226281_cx_());
               WanderingTraderEntity.this.navigator.tryMoveToXYZ(vec3d1.x, vec3d1.y, vec3d1.z, this.field_220849_c);
            } else {
               WanderingTraderEntity.this.navigator.tryMoveToXYZ((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), this.field_220849_c);
            }
         }

      }

      private boolean func_220846_a(BlockPos p_220846_1_, double p_220846_2_) {
         return !p_220846_1_.withinDistance(this.field_220847_a.getPositionVec(), p_220846_2_);
      }
   }
}