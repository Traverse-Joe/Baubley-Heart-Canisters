package net.minecraft.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TridentEntity extends AbstractArrowEntity {
   private static final DataParameter<Byte> LOYALTY_LEVEL = EntityDataManager.createKey(TridentEntity.class, DataSerializers.BYTE);
   private static final DataParameter<Boolean> field_226571_aq_ = EntityDataManager.createKey(TridentEntity.class, DataSerializers.BOOLEAN);
   private ItemStack thrownStack = new ItemStack(Items.TRIDENT);
   private boolean dealtDamage;
   public int returningTicks;

   public TridentEntity(EntityType<? extends TridentEntity> p_i50148_1_, World p_i50148_2_) {
      super(p_i50148_1_, p_i50148_2_);
   }

   public TridentEntity(World p_i48790_1_, LivingEntity p_i48790_2_, ItemStack p_i48790_3_) {
      super(EntityType.TRIDENT, p_i48790_2_, p_i48790_1_);
      this.thrownStack = p_i48790_3_.copy();
      this.dataManager.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyaltyModifier(p_i48790_3_));
      this.dataManager.set(field_226571_aq_, p_i48790_3_.hasEffect());
   }

   @OnlyIn(Dist.CLIENT)
   public TridentEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
      super(EntityType.TRIDENT, p_i48791_2_, p_i48791_4_, p_i48791_6_, p_i48791_1_);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(LOYALTY_LEVEL, (byte)0);
      this.dataManager.register(field_226571_aq_, false);
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      if (this.timeInGround > 4) {
         this.dealtDamage = true;
      }

      Entity entity = this.getShooter();
      if ((this.dealtDamage || this.func_203047_q()) && entity != null) {
         int i = this.dataManager.get(LOYALTY_LEVEL);
         if (i > 0 && !this.shouldReturnToThrower()) {
            if (!this.world.isRemote && this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
               this.entityDropItem(this.getArrowStack(), 0.1F);
            }

            this.remove();
         } else if (i > 0) {
            this.func_203045_n(true);
            Vec3d vec3d = new Vec3d(entity.func_226277_ct_() - this.func_226277_ct_(), entity.func_226280_cw_() - this.func_226278_cu_(), entity.func_226281_cx_() - this.func_226281_cx_());
            this.func_226288_n_(this.func_226277_ct_(), this.func_226278_cu_() + vec3d.y * 0.015D * (double)i, this.func_226281_cx_());
            if (this.world.isRemote) {
               this.lastTickPosY = this.func_226278_cu_();
            }

            double d0 = 0.05D * (double)i;
            this.setMotion(this.getMotion().scale(0.95D).add(vec3d.normalize().scale(d0)));
            if (this.returningTicks == 0) {
               this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
            }

            ++this.returningTicks;
         }
      }

      super.tick();
   }

   private boolean shouldReturnToThrower() {
      Entity entity = this.getShooter();
      if (entity != null && entity.isAlive()) {
         return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
      } else {
         return false;
      }
   }

   protected ItemStack getArrowStack() {
      return this.thrownStack.copy();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_226572_w_() {
      return this.dataManager.get(field_226571_aq_);
   }

   @Nullable
   protected EntityRayTraceResult func_213866_a(Vec3d p_213866_1_, Vec3d p_213866_2_) {
      return this.dealtDamage ? null : super.func_213866_a(p_213866_1_, p_213866_2_);
   }

   protected void func_213868_a(EntityRayTraceResult p_213868_1_) {
      Entity entity = p_213868_1_.getEntity();
      float f = 8.0F;
      if (entity instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)entity;
         f += EnchantmentHelper.getModifierForCreature(this.thrownStack, livingentity.getCreatureAttribute());
      }

      Entity entity1 = this.getShooter();
      DamageSource damagesource = DamageSource.causeTridentDamage(this, (Entity)(entity1 == null ? this : entity1));
      this.dealtDamage = true;
      SoundEvent soundevent = SoundEvents.ITEM_TRIDENT_HIT;
      if (entity.attackEntityFrom(damagesource, f)) {
         if (entity.getType() == EntityType.ENDERMAN) {
            return;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity1 = (LivingEntity)entity;
            if (entity1 instanceof LivingEntity) {
               EnchantmentHelper.applyThornEnchantments(livingentity1, entity1);
               EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity1);
            }

            this.arrowHit(livingentity1);
         }
      }

      this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
      float f1 = 1.0F;
      if (this.world instanceof ServerWorld && this.world.isThundering() && EnchantmentHelper.hasChanneling(this.thrownStack)) {
         BlockPos blockpos = entity.getPosition();
         if (this.world.func_226660_f_(blockpos)) {
            LightningBoltEntity lightningboltentity = new LightningBoltEntity(this.world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, false);
            lightningboltentity.setCaster(entity1 instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity1 : null);
            ((ServerWorld)this.world).addLightningBolt(lightningboltentity);
            soundevent = SoundEvents.ITEM_TRIDENT_THUNDER;
            f1 = 5.0F;
         }
      }

      this.playSound(soundevent, f1, 1.0F);
   }

   protected SoundEvent func_213867_k() {
      return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
   }

   /**
    * Called by a player entity when they collide with an entity
    */
   public void onCollideWithPlayer(PlayerEntity entityIn) {
      Entity entity = this.getShooter();
      if (entity == null || entity.getUniqueID() == entityIn.getUniqueID()) {
         super.onCollideWithPlayer(entityIn);
      }
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      if (compound.contains("Trident", 10)) {
         this.thrownStack = ItemStack.read(compound.getCompound("Trident"));
      }

      this.dealtDamage = compound.getBoolean("DealtDamage");
      this.dataManager.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyaltyModifier(this.thrownStack));
   }

   public void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      compound.put("Trident", this.thrownStack.write(new CompoundNBT()));
      compound.putBoolean("DealtDamage", this.dealtDamage);
   }

   public void func_225516_i_() {
      int i = this.dataManager.get(LOYALTY_LEVEL);
      if (this.pickupStatus != AbstractArrowEntity.PickupStatus.ALLOWED || i <= 0) {
         super.func_225516_i_();
      }

   }

   protected float getWaterDrag() {
      return 0.99F;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isInRangeToRender3d(double x, double y, double z) {
      return true;
   }
}