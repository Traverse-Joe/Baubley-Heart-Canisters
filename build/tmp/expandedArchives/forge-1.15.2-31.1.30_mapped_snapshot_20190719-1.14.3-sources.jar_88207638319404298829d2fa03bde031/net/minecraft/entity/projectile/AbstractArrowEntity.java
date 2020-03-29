package net.minecraft.entity.projectile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractArrowEntity extends Entity implements IProjectile {
   private static final DataParameter<Byte> CRITICAL = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BYTE);
   protected static final DataParameter<Optional<UUID>> field_212362_a = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
   private static final DataParameter<Byte> field_213876_as = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BYTE);
   @Nullable
   private BlockState inBlockState;
   protected boolean inGround;
   protected int timeInGround;
   public AbstractArrowEntity.PickupStatus pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
   public int arrowShake;
   public UUID shootingEntity;
   private int ticksInGround;
   private int ticksInAir;
   private double damage = 2.0D;
   private int knockbackStrength;
   private SoundEvent field_213877_ay = this.func_213867_k();
   private IntOpenHashSet field_213878_az;
   private List<Entity> field_213875_aA;

   protected AbstractArrowEntity(EntityType<? extends AbstractArrowEntity> type, World p_i48546_2_) {
      super(type, p_i48546_2_);
   }

   protected AbstractArrowEntity(EntityType<? extends AbstractArrowEntity> p_i48547_1_, double p_i48547_2_, double p_i48547_4_, double p_i48547_6_, World p_i48547_8_) {
      this(p_i48547_1_, p_i48547_8_);
      this.setPosition(p_i48547_2_, p_i48547_4_, p_i48547_6_);
   }

   protected AbstractArrowEntity(EntityType<? extends AbstractArrowEntity> p_i48548_1_, LivingEntity p_i48548_2_, World p_i48548_3_) {
      this(p_i48548_1_, p_i48548_2_.func_226277_ct_(), p_i48548_2_.func_226280_cw_() - (double)0.1F, p_i48548_2_.func_226281_cx_(), p_i48548_3_);
      this.setShooter(p_i48548_2_);
      if (p_i48548_2_ instanceof PlayerEntity) {
         this.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
      }

   }

   public void setHitSound(SoundEvent p_213869_1_) {
      this.field_213877_ay = p_213869_1_;
   }

   /**
    * Checks if the entity is in range to render.
    */
   @OnlyIn(Dist.CLIENT)
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
      if (Double.isNaN(d0)) {
         d0 = 1.0D;
      }

      d0 = d0 * 64.0D * getRenderDistanceWeight();
      return distance < d0 * d0;
   }

   protected void registerData() {
      this.dataManager.register(CRITICAL, (byte)0);
      this.dataManager.register(field_212362_a, Optional.empty());
      this.dataManager.register(field_213876_as, (byte)0);
   }

   public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
      float f1 = -MathHelper.sin(pitch * ((float)Math.PI / 180F));
      float f2 = MathHelper.cos(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
      this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
      this.setMotion(this.getMotion().add(shooter.getMotion().x, shooter.onGround ? 0.0D : shooter.getMotion().y, shooter.getMotion().z));
   }

   /**
    * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
    */
   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy).scale((double)velocity);
      this.setMotion(vec3d);
      float f = MathHelper.sqrt(func_213296_b(vec3d));
      this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
      this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI));
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
      this.ticksInGround = 0;
   }

   /**
    * Sets a target for the client to interpolate towards over the next few ticks
    */
   @OnlyIn(Dist.CLIENT)
   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
      this.setPosition(x, y, z);
      this.setRotation(yaw, pitch);
   }

   /**
    * Updates the entity motion clientside, called by packets from the server
    */
   @OnlyIn(Dist.CLIENT)
   public void setVelocity(double x, double y, double z) {
      this.setMotion(x, y, z);
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt(x * x + z * z);
         this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (double)(180F / (float)Math.PI));
         this.rotationYaw = (float)(MathHelper.atan2(x, z) * (double)(180F / (float)Math.PI));
         this.prevRotationPitch = this.rotationPitch;
         this.prevRotationYaw = this.rotationYaw;
         this.setLocationAndAngles(this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_(), this.rotationYaw, this.rotationPitch);
         this.ticksInGround = 0;
      }

   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();
      boolean flag = this.func_203047_q();
      Vec3d vec3d = this.getMotion();
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt(func_213296_b(vec3d));
         this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
         this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI));
         this.prevRotationYaw = this.rotationYaw;
         this.prevRotationPitch = this.rotationPitch;
      }

      BlockPos blockpos = new BlockPos(this);
      BlockState blockstate = this.world.getBlockState(blockpos);
      if (!blockstate.isAir(this.world, blockpos) && !flag) {
         VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
         if (!voxelshape.isEmpty()) {
            Vec3d vec3d1 = this.getPositionVec();

            for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
               if (axisalignedbb.offset(blockpos).contains(vec3d1)) {
                  this.inGround = true;
                  break;
               }
            }
         }
      }

      if (this.arrowShake > 0) {
         --this.arrowShake;
      }

      if (this.isWet()) {
         this.extinguish();
      }

      if (this.inGround && !flag) {
         if (this.inBlockState != blockstate && this.world.func_226664_a_(this.getBoundingBox().grow(0.06D))) {
            this.inGround = false;
            this.setMotion(vec3d.mul((double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F)));
            this.ticksInGround = 0;
            this.ticksInAir = 0;
         } else if (!this.world.isRemote) {
            this.func_225516_i_();
         }

         ++this.timeInGround;
      } else {
         this.timeInGround = 0;
         ++this.ticksInAir;
         Vec3d vec3d2 = this.getPositionVec();
         Vec3d vec3d3 = vec3d2.add(vec3d);
         RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vec3d2, vec3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
         if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
            vec3d3 = raytraceresult.getHitVec();
         }

         while(!this.removed) {
            EntityRayTraceResult entityraytraceresult = this.func_213866_a(vec3d2, vec3d3);
            if (entityraytraceresult != null) {
               raytraceresult = entityraytraceresult;
            }

            if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
               Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
               Entity entity1 = this.getShooter();
               if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
                  raytraceresult = null;
                  entityraytraceresult = null;
               }
            }

            if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
               this.onHit(raytraceresult);
               this.isAirBorne = true;
            }

            if (entityraytraceresult == null || this.func_213874_s() <= 0) {
               break;
            }

            raytraceresult = null;
         }

         vec3d = this.getMotion();
         double d3 = vec3d.x;
         double d4 = vec3d.y;
         double d0 = vec3d.z;
         if (this.getIsCritical()) {
            for(int i = 0; i < 4; ++i) {
               this.world.addParticle(ParticleTypes.CRIT, this.func_226277_ct_() + d3 * (double)i / 4.0D, this.func_226278_cu_() + d4 * (double)i / 4.0D, this.func_226281_cx_() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
            }
         }

         double d5 = this.func_226277_ct_() + d3;
         double d1 = this.func_226278_cu_() + d4;
         double d2 = this.func_226281_cx_() + d0;
         float f1 = MathHelper.sqrt(func_213296_b(vec3d));
         if (flag) {
            this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * (double)(180F / (float)Math.PI));
         } else {
            this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
         }

         for(this.rotationPitch = (float)(MathHelper.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
            ;
         }

         while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }

         this.rotationPitch = MathHelper.lerp(0.2F, this.prevRotationPitch, this.rotationPitch);
         this.rotationYaw = MathHelper.lerp(0.2F, this.prevRotationYaw, this.rotationYaw);
         float f2 = 0.99F;
         float f3 = 0.05F;
         if (this.isInWater()) {
            for(int j = 0; j < 4; ++j) {
               float f4 = 0.25F;
               this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
            }

            f2 = this.getWaterDrag();
         }

         this.setMotion(vec3d.scale((double)f2));
         if (!this.hasNoGravity() && !flag) {
            Vec3d vec3d4 = this.getMotion();
            this.setMotion(vec3d4.x, vec3d4.y - (double)0.05F, vec3d4.z);
         }

         this.setPosition(d5, d1, d2);
         this.doBlockCollisions();
      }
   }

   protected void func_225516_i_() {
      ++this.ticksInGround;
      if (this.ticksInGround >= 1200) {
         this.remove();
      }

   }

   /**
    * Called when the arrow hits a block or an entity
    */
   protected void onHit(RayTraceResult raytraceResultIn) {
      RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
      if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
         this.func_213868_a((EntityRayTraceResult)raytraceResultIn);
      } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
         BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceResultIn;
         BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
         this.inBlockState = blockstate;
         Vec3d vec3d = blockraytraceresult.getHitVec().subtract(this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_());
         this.setMotion(vec3d);
         Vec3d vec3d1 = vec3d.normalize().scale((double)0.05F);
         this.func_226288_n_(this.func_226277_ct_() - vec3d1.x, this.func_226278_cu_() - vec3d1.y, this.func_226281_cx_() - vec3d1.z);
         this.playSound(this.getHitGroundSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
         this.inGround = true;
         this.arrowShake = 7;
         this.setIsCritical(false);
         this.func_213872_b((byte)0);
         this.setHitSound(SoundEvents.ENTITY_ARROW_HIT);
         this.func_213865_o(false);
         this.func_213870_w();
         blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
      }

   }

   private void func_213870_w() {
      if (this.field_213875_aA != null) {
         this.field_213875_aA.clear();
      }

      if (this.field_213878_az != null) {
         this.field_213878_az.clear();
      }

   }

   protected void func_213868_a(EntityRayTraceResult p_213868_1_) {
      Entity entity = p_213868_1_.getEntity();
      float f = (float)this.getMotion().length();
      int i = MathHelper.ceil(Math.max((double)f * this.damage, 0.0D));
      if (this.func_213874_s() > 0) {
         if (this.field_213878_az == null) {
            this.field_213878_az = new IntOpenHashSet(5);
         }

         if (this.field_213875_aA == null) {
            this.field_213875_aA = Lists.newArrayListWithCapacity(5);
         }

         if (this.field_213878_az.size() >= this.func_213874_s() + 1) {
            this.remove();
            return;
         }

         this.field_213878_az.add(entity.getEntityId());
      }

      if (this.getIsCritical()) {
         i += this.rand.nextInt(i / 2 + 2);
      }

      Entity entity1 = this.getShooter();
      DamageSource damagesource;
      if (entity1 == null) {
         damagesource = DamageSource.causeArrowDamage(this, this);
      } else {
         damagesource = DamageSource.causeArrowDamage(this, entity1);
         if (entity1 instanceof LivingEntity) {
            ((LivingEntity)entity1).setLastAttackedEntity(entity);
         }
      }

      boolean flag = entity.getType() == EntityType.ENDERMAN;
      int j = entity.func_223314_ad();
      if (this.isBurning() && !flag) {
         entity.setFire(5);
      }

      if (entity.attackEntityFrom(damagesource, (float)i)) {
         if (flag) {
            return;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            if (!this.world.isRemote && this.func_213874_s() <= 0) {
               livingentity.setArrowCountInEntity(livingentity.getArrowCountInEntity() + 1);
            }

            if (this.knockbackStrength > 0) {
               Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
               if (vec3d.lengthSquared() > 0.0D) {
                  livingentity.addVelocity(vec3d.x, 0.1D, vec3d.z);
               }
            }

            if (!this.world.isRemote && entity1 instanceof LivingEntity) {
               EnchantmentHelper.applyThornEnchantments(livingentity, entity1);
               EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity);
            }

            this.arrowHit(livingentity);
            if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity) {
               ((ServerPlayerEntity)entity1).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
            }

            if (!entity.isAlive() && this.field_213875_aA != null) {
               this.field_213875_aA.add(livingentity);
            }

            if (!this.world.isRemote && entity1 instanceof ServerPlayerEntity) {
               ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entity1;
               if (this.field_213875_aA != null && this.func_213873_r()) {
                  CriteriaTriggers.KILLED_BY_CROSSBOW.func_215105_a(serverplayerentity, this.field_213875_aA, this.field_213875_aA.size());
               } else if (!entity.isAlive() && this.func_213873_r()) {
                  CriteriaTriggers.KILLED_BY_CROSSBOW.func_215105_a(serverplayerentity, Arrays.asList(entity), 0);
               }
            }
         }

         this.playSound(this.field_213877_ay, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
         if (this.func_213874_s() <= 0) {
            this.remove();
         }
      } else {
         entity.func_223308_g(j);
         this.setMotion(this.getMotion().scale(-0.1D));
         this.rotationYaw += 180.0F;
         this.prevRotationYaw += 180.0F;
         this.ticksInAir = 0;
         if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
            if (this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
               this.entityDropItem(this.getArrowStack(), 0.1F);
            }

            this.remove();
         }
      }

   }

   protected SoundEvent func_213867_k() {
      return SoundEvents.ENTITY_ARROW_HIT;
   }

   protected final SoundEvent getHitGroundSound() {
      return this.field_213877_ay;
   }

   protected void arrowHit(LivingEntity living) {
   }

   @Nullable
   protected EntityRayTraceResult func_213866_a(Vec3d p_213866_1_, Vec3d p_213866_2_) {
      return ProjectileHelper.func_221271_a(this.world, this, p_213866_1_, p_213866_2_, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (p_213871_1_) -> {
         return !p_213871_1_.isSpectator() && p_213871_1_.isAlive() && p_213871_1_.canBeCollidedWith() && (p_213871_1_ != this.getShooter() || this.ticksInAir >= 5) && (this.field_213878_az == null || !this.field_213878_az.contains(p_213871_1_.getEntityId()));
      });
   }

   public void writeAdditional(CompoundNBT compound) {
      compound.putShort("life", (short)this.ticksInGround);
      if (this.inBlockState != null) {
         compound.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
      }

      compound.putByte("shake", (byte)this.arrowShake);
      compound.putBoolean("inGround", this.inGround);
      compound.putByte("pickup", (byte)this.pickupStatus.ordinal());
      compound.putDouble("damage", this.damage);
      compound.putBoolean("crit", this.getIsCritical());
      compound.putByte("PierceLevel", this.func_213874_s());
      if (this.shootingEntity != null) {
         compound.putUniqueId("OwnerUUID", this.shootingEntity);
      }

      compound.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.field_213877_ay).toString());
      compound.putBoolean("ShotFromCrossbow", this.func_213873_r());
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      this.ticksInGround = compound.getShort("life");
      if (compound.contains("inBlockState", 10)) {
         this.inBlockState = NBTUtil.readBlockState(compound.getCompound("inBlockState"));
      }

      this.arrowShake = compound.getByte("shake") & 255;
      this.inGround = compound.getBoolean("inGround");
      if (compound.contains("damage", 99)) {
         this.damage = compound.getDouble("damage");
      }

      if (compound.contains("pickup", 99)) {
         this.pickupStatus = AbstractArrowEntity.PickupStatus.getByOrdinal(compound.getByte("pickup"));
      } else if (compound.contains("player", 99)) {
         this.pickupStatus = compound.getBoolean("player") ? AbstractArrowEntity.PickupStatus.ALLOWED : AbstractArrowEntity.PickupStatus.DISALLOWED;
      }

      this.setIsCritical(compound.getBoolean("crit"));
      this.func_213872_b(compound.getByte("PierceLevel"));
      if (compound.hasUniqueId("OwnerUUID")) {
         this.shootingEntity = compound.getUniqueId("OwnerUUID");
      }

      if (compound.contains("SoundEvent", 8)) {
         this.field_213877_ay = Registry.SOUND_EVENT.getValue(new ResourceLocation(compound.getString("SoundEvent"))).orElse(this.func_213867_k());
      }

      this.func_213865_o(compound.getBoolean("ShotFromCrossbow"));
   }

   public void setShooter(@Nullable Entity p_212361_1_) {
      this.shootingEntity = p_212361_1_ == null ? null : p_212361_1_.getUniqueID();
      if (p_212361_1_ instanceof PlayerEntity) {
         this.pickupStatus = ((PlayerEntity)p_212361_1_).abilities.isCreativeMode ? AbstractArrowEntity.PickupStatus.CREATIVE_ONLY : AbstractArrowEntity.PickupStatus.ALLOWED;
      }

   }

   @Nullable
   public Entity getShooter() {
      return this.shootingEntity != null && this.world instanceof ServerWorld ? ((ServerWorld)this.world).getEntityByUuid(this.shootingEntity) : null;
   }

   /**
    * Called by a player entity when they collide with an entity
    */
   public void onCollideWithPlayer(PlayerEntity entityIn) {
      if (!this.world.isRemote && (this.inGround || this.func_203047_q()) && this.arrowShake <= 0) {
         boolean flag = this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED || this.pickupStatus == AbstractArrowEntity.PickupStatus.CREATIVE_ONLY && entityIn.abilities.isCreativeMode || this.func_203047_q() && this.getShooter().getUniqueID() == entityIn.getUniqueID();
         if (this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED && !entityIn.inventory.addItemStackToInventory(this.getArrowStack())) {
            flag = false;
         }

         if (flag) {
            entityIn.onItemPickup(this, 1);
            this.remove();
         }

      }
   }

   protected abstract ItemStack getArrowStack();

   protected boolean func_225502_at_() {
      return false;
   }

   public void setDamage(double damageIn) {
      this.damage = damageIn;
   }

   public double getDamage() {
      return this.damage;
   }

   /**
    * Sets the amount of knockback the arrow applies when it hits a mob.
    */
   public void setKnockbackStrength(int knockbackStrengthIn) {
      this.knockbackStrength = knockbackStrengthIn;
   }

   /**
    * Returns true if it's possible to attack this entity with an item.
    */
   public boolean canBeAttackedWithItem() {
      return false;
   }

   protected float getEyeHeight(Pose p_213316_1_, EntitySize p_213316_2_) {
      return 0.0F;
   }

   /**
    * Whether the arrow has a stream of critical hit particles flying behind it.
    */
   public void setIsCritical(boolean critical) {
      this.func_203049_a(1, critical);
   }

   public void func_213872_b(byte p_213872_1_) {
      this.dataManager.set(field_213876_as, p_213872_1_);
   }

   private void func_203049_a(int p_203049_1_, boolean p_203049_2_) {
      byte b0 = this.dataManager.get(CRITICAL);
      if (p_203049_2_) {
         this.dataManager.set(CRITICAL, (byte)(b0 | p_203049_1_));
      } else {
         this.dataManager.set(CRITICAL, (byte)(b0 & ~p_203049_1_));
      }

   }

   /**
    * Whether the arrow has a stream of critical hit particles flying behind it.
    */
   public boolean getIsCritical() {
      byte b0 = this.dataManager.get(CRITICAL);
      return (b0 & 1) != 0;
   }

   public boolean func_213873_r() {
      byte b0 = this.dataManager.get(CRITICAL);
      return (b0 & 4) != 0;
   }

   public byte func_213874_s() {
      return this.dataManager.get(field_213876_as);
   }

   public void setEnchantmentEffectsFromEntity(LivingEntity p_190547_1_, float p_190547_2_) {
      int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, p_190547_1_);
      int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, p_190547_1_);
      this.setDamage((double)(p_190547_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().getId() * 0.11F));
      if (i > 0) {
         this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
      }

      if (j > 0) {
         this.setKnockbackStrength(j);
      }

      if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, p_190547_1_) > 0) {
         this.setFire(100);
      }

   }

   protected float getWaterDrag() {
      return 0.6F;
   }

   public void func_203045_n(boolean p_203045_1_) {
      this.noClip = p_203045_1_;
      this.func_203049_a(2, p_203045_1_);
   }

   public boolean func_203047_q() {
      if (!this.world.isRemote) {
         return this.noClip;
      } else {
         return (this.dataManager.get(CRITICAL) & 2) != 0;
      }
   }

   public void func_213865_o(boolean p_213865_1_) {
      this.func_203049_a(4, p_213865_1_);
   }

   public IPacket<?> createSpawnPacket() {
      Entity entity = this.getShooter();
      return new SSpawnObjectPacket(this, entity == null ? 0 : entity.getEntityId());
   }

   public static enum PickupStatus {
      DISALLOWED,
      ALLOWED,
      CREATIVE_ONLY;

      public static AbstractArrowEntity.PickupStatus getByOrdinal(int ordinal) {
         if (ordinal < 0 || ordinal > values().length) {
            ordinal = 0;
         }

         return values()[ordinal];
      }
   }
}