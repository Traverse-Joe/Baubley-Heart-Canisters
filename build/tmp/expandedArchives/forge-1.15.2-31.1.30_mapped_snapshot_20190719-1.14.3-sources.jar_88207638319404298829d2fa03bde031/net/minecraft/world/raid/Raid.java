package net.minecraft.world.raid;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

public class Raid {
   private static final TranslationTextComponent RAID = new TranslationTextComponent("event.minecraft.raid");
   private static final TranslationTextComponent VICTORY = new TranslationTextComponent("event.minecraft.raid.victory");
   private static final TranslationTextComponent DEFEAT = new TranslationTextComponent("event.minecraft.raid.defeat");
   private static final ITextComponent RAID_VICTORY = RAID.shallowCopy().appendText(" - ").appendSibling(VICTORY);
   private static final ITextComponent RAID_DEFEAT = RAID.shallowCopy().appendText(" - ").appendSibling(DEFEAT);
   private final Map<Integer, AbstractRaiderEntity> leaders = Maps.newHashMap();
   private final Map<Integer, Set<AbstractRaiderEntity>> raiders = Maps.newHashMap();
   private final Set<UUID> heroes = Sets.newHashSet();
   private long ticksActive;
   private BlockPos center;
   private final ServerWorld world;
   private boolean started;
   private final int id;
   private float totalHealth;
   private int badOmenLevel;
   private boolean active;
   private int groupsSpawned;
   private final ServerBossInfo bossInfo = new ServerBossInfo(RAID, BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_10);
   private int postRaidTicks;
   private int preRaidTicks;
   private final Random random = new Random();
   private final int numGroups;
   private Raid.Status status;
   private int field_221361_y;
   private Optional<BlockPos> field_221362_z = Optional.empty();

   public Raid(int p_i50144_1_, ServerWorld p_i50144_2_, BlockPos p_i50144_3_) {
      this.id = p_i50144_1_;
      this.world = p_i50144_2_;
      this.active = true;
      this.preRaidTicks = 300;
      this.bossInfo.setPercent(0.0F);
      this.center = p_i50144_3_;
      this.numGroups = this.getWaves(p_i50144_2_.getDifficulty());
      this.status = Raid.Status.ONGOING;
   }

   public Raid(ServerWorld p_i50145_1_, CompoundNBT p_i50145_2_) {
      this.world = p_i50145_1_;
      this.id = p_i50145_2_.getInt("Id");
      this.started = p_i50145_2_.getBoolean("Started");
      this.active = p_i50145_2_.getBoolean("Active");
      this.ticksActive = p_i50145_2_.getLong("TicksActive");
      this.badOmenLevel = p_i50145_2_.getInt("BadOmenLevel");
      this.groupsSpawned = p_i50145_2_.getInt("GroupsSpawned");
      this.preRaidTicks = p_i50145_2_.getInt("PreRaidTicks");
      this.postRaidTicks = p_i50145_2_.getInt("PostRaidTicks");
      this.totalHealth = p_i50145_2_.getFloat("TotalHealth");
      this.center = new BlockPos(p_i50145_2_.getInt("CX"), p_i50145_2_.getInt("CY"), p_i50145_2_.getInt("CZ"));
      this.numGroups = p_i50145_2_.getInt("NumGroups");
      this.status = Raid.Status.func_221275_b(p_i50145_2_.getString("Status"));
      this.heroes.clear();
      if (p_i50145_2_.contains("HeroesOfTheVillage", 9)) {
         ListNBT listnbt = p_i50145_2_.getList("HeroesOfTheVillage", 10);

         for(int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            UUID uuid = compoundnbt.getUniqueId("UUID");
            this.heroes.add(uuid);
         }
      }

   }

   public boolean func_221319_a() {
      return this.isVictory() || this.isLoss();
   }

   public boolean func_221334_b() {
      return this.func_221297_c() && this.getRaiderCount() == 0 && this.preRaidTicks > 0;
   }

   public boolean func_221297_c() {
      return this.groupsSpawned > 0;
   }

   public boolean isStopped() {
      return this.status == Raid.Status.STOPPED;
   }

   public boolean isVictory() {
      return this.status == Raid.Status.VICTORY;
   }

   public boolean isLoss() {
      return this.status == Raid.Status.LOSS;
   }

   public World getWorld() {
      return this.world;
   }

   public boolean func_221301_k() {
      return this.started;
   }

   public int func_221315_l() {
      return this.groupsSpawned;
   }

   private Predicate<ServerPlayerEntity> getParticipantsPredicate() {
      return (p_221302_1_) -> {
         BlockPos blockpos = new BlockPos(p_221302_1_);
         return p_221302_1_.isAlive() && this.world.findRaid(blockpos) == this;
      };
   }

   private void updateBossInfoVisibility() {
      Set<ServerPlayerEntity> set = Sets.newHashSet(this.bossInfo.getPlayers());
      List<ServerPlayerEntity> list = this.world.getPlayers(this.getParticipantsPredicate());

      for(ServerPlayerEntity serverplayerentity : list) {
         if (!set.contains(serverplayerentity)) {
            this.bossInfo.addPlayer(serverplayerentity);
         }
      }

      for(ServerPlayerEntity serverplayerentity1 : set) {
         if (!list.contains(serverplayerentity1)) {
            this.bossInfo.removePlayer(serverplayerentity1);
         }
      }

   }

   public int getMaxLevel() {
      return 5;
   }

   public int func_221291_n() {
      return this.badOmenLevel;
   }

   public void increaseLevel(PlayerEntity p_221309_1_) {
      if (p_221309_1_.isPotionActive(Effects.BAD_OMEN)) {
         this.badOmenLevel += p_221309_1_.getActivePotionEffect(Effects.BAD_OMEN).getAmplifier() + 1;
         this.badOmenLevel = MathHelper.clamp(this.badOmenLevel, 0, this.getMaxLevel());
      }

      p_221309_1_.removePotionEffect(Effects.BAD_OMEN);
   }

   public void stop() {
      this.active = false;
      this.bossInfo.removeAllPlayers();
      this.status = Raid.Status.STOPPED;
   }

   public void tick() {
      if (!this.isStopped()) {
         if (this.status == Raid.Status.ONGOING) {
            boolean flag = this.active;
            this.active = this.world.isBlockLoaded(this.center);
            if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
               this.stop();
               return;
            }

            if (flag != this.active) {
               this.bossInfo.setVisible(this.active);
            }

            if (!this.active) {
               return;
            }

            if (!this.world.func_217483_b_(this.center)) {
               this.func_223027_y();
            }

            if (!this.world.func_217483_b_(this.center)) {
               if (this.groupsSpawned > 0) {
                  this.status = Raid.Status.LOSS;
               } else {
                  this.stop();
               }
            }

            ++this.ticksActive;
            if (this.ticksActive >= 48000L) {
               this.stop();
               return;
            }

            int i = this.getRaiderCount();
            if (i == 0 && this.func_221289_z()) {
               if (this.preRaidTicks <= 0) {
                  if (this.preRaidTicks == 0 && this.groupsSpawned > 0) {
                     this.preRaidTicks = 300;
                     this.bossInfo.setName(RAID);
                     return;
                  }
               } else {
                  boolean flag1 = this.field_221362_z.isPresent();
                  boolean flag2 = !flag1 && this.preRaidTicks % 5 == 0;
                  if (flag1 && !this.world.getChunkProvider().isChunkLoaded(new ChunkPos(this.field_221362_z.get()))) {
                     flag2 = true;
                  }

                  if (flag2) {
                     int j = 0;
                     if (this.preRaidTicks < 100) {
                        j = 1;
                     } else if (this.preRaidTicks < 40) {
                        j = 2;
                     }

                     this.field_221362_z = this.func_221313_d(j);
                  }

                  if (this.preRaidTicks == 300 || this.preRaidTicks % 20 == 0) {
                     this.updateBossInfoVisibility();
                  }

                  --this.preRaidTicks;
                  this.bossInfo.setPercent(MathHelper.clamp((float)(300 - this.preRaidTicks) / 300.0F, 0.0F, 1.0F));
               }
            }

            if (this.ticksActive % 20L == 0L) {
               this.updateBossInfoVisibility();
               this.func_221292_E();
               if (i > 0) {
                  if (i <= 2) {
                     this.bossInfo.setName(RAID.shallowCopy().appendText(" - ").appendSibling(new TranslationTextComponent("event.minecraft.raid.raiders_remaining", i)));
                  } else {
                     this.bossInfo.setName(RAID);
                  }
               } else {
                  this.bossInfo.setName(RAID);
               }
            }

            boolean flag3 = false;
            int k = 0;

            while(this.func_221318_F()) {
               BlockPos blockpos = this.field_221362_z.isPresent() ? this.field_221362_z.get() : this.func_221298_a(k, 20);
               if (blockpos != null) {
                  this.started = true;
                  this.spawnNextWave(blockpos);
                  if (!flag3) {
                     this.playWaveStartSound(blockpos);
                     flag3 = true;
                  }
               } else {
                  ++k;
               }

               if (k > 3) {
                  this.stop();
                  break;
               }
            }

            if (this.func_221301_k() && !this.func_221289_z() && i == 0) {
               if (this.postRaidTicks < 40) {
                  ++this.postRaidTicks;
               } else {
                  this.status = Raid.Status.VICTORY;

                  for(UUID uuid : this.heroes) {
                     Entity entity = this.world.getEntityByUuid(uuid);
                     if (entity instanceof LivingEntity && !entity.isSpectator()) {
                        LivingEntity livingentity = (LivingEntity)entity;
                        livingentity.addPotionEffect(new EffectInstance(Effects.HERO_OF_THE_VILLAGE, 48000, this.badOmenLevel - 1, false, false, true));
                        if (livingentity instanceof ServerPlayerEntity) {
                           ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)livingentity;
                           serverplayerentity.addStat(Stats.RAID_WIN);
                           CriteriaTriggers.HERO_OF_THE_VILLAGE.trigger(serverplayerentity);
                        }
                     }
                  }
               }
            }

            this.markDirty();
         } else if (this.func_221319_a()) {
            ++this.field_221361_y;
            if (this.field_221361_y >= 600) {
               this.stop();
               return;
            }

            if (this.field_221361_y % 20 == 0) {
               this.updateBossInfoVisibility();
               this.bossInfo.setVisible(true);
               if (this.isVictory()) {
                  this.bossInfo.setPercent(0.0F);
                  this.bossInfo.setName(RAID_VICTORY);
               } else {
                  this.bossInfo.setName(RAID_DEFEAT);
               }
            }
         }

      }
   }

   private void func_223027_y() {
      Stream<SectionPos> stream = SectionPos.getAllInBox(SectionPos.from(this.center), 2);
      stream.filter(this.world::func_222887_a).map(SectionPos::getCenter).min(Comparator.comparingDouble((p_223025_1_) -> {
         return p_223025_1_.distanceSq(this.center);
      })).ifPresent(this::func_223024_c);
   }

   private Optional<BlockPos> func_221313_d(int p_221313_1_) {
      for(int i = 0; i < 3; ++i) {
         BlockPos blockpos = this.func_221298_a(p_221313_1_, 1);
         if (blockpos != null) {
            return Optional.of(blockpos);
         }
      }

      return Optional.empty();
   }

   private boolean func_221289_z() {
      if (this.func_221328_B()) {
         return !this.func_221314_C();
      } else {
         return !this.func_221288_A();
      }
   }

   private boolean func_221288_A() {
      return this.func_221315_l() == this.numGroups;
   }

   private boolean func_221328_B() {
      return this.badOmenLevel > 1;
   }

   private boolean func_221314_C() {
      return this.func_221315_l() > this.numGroups;
   }

   private boolean func_221305_D() {
      return this.func_221288_A() && this.getRaiderCount() == 0 && this.func_221328_B();
   }

   private void func_221292_E() {
      Iterator<Set<AbstractRaiderEntity>> iterator = this.raiders.values().iterator();
      Set<AbstractRaiderEntity> set = Sets.newHashSet();

      while(iterator.hasNext()) {
         for(AbstractRaiderEntity abstractraiderentity : iterator.next()) {
            BlockPos blockpos = new BlockPos(abstractraiderentity);
            if (!abstractraiderentity.removed && abstractraiderentity.dimension == this.world.getDimension().getType() && !(this.center.distanceSq(blockpos) >= 12544.0D)) {
               if (abstractraiderentity.ticksExisted > 600) {
                  if (this.world.getEntityByUuid(abstractraiderentity.getUniqueID()) == null) {
                     set.add(abstractraiderentity);
                  }

                  if (!this.world.func_217483_b_(blockpos) && abstractraiderentity.getIdleTime() > 2400) {
                     abstractraiderentity.func_213653_b(abstractraiderentity.func_213661_eo() + 1);
                  }

                  if (abstractraiderentity.func_213661_eo() >= 30) {
                     set.add(abstractraiderentity);
                  }
               }
            } else {
               set.add(abstractraiderentity);
            }
         }
      }

      for(AbstractRaiderEntity abstractraiderentity1 : set) {
         this.leaveRaid(abstractraiderentity1, true);
      }

   }

   private void playWaveStartSound(BlockPos p_221293_1_) {
      float f = 13.0F;
      int i = 64;
      Collection<ServerPlayerEntity> collection = this.bossInfo.getPlayers();

      for(ServerPlayerEntity serverplayerentity : this.world.getPlayers()) {
         Vec3d vec3d = serverplayerentity.getPositionVec();
         Vec3d vec3d1 = new Vec3d(p_221293_1_);
         float f1 = MathHelper.sqrt((vec3d1.x - vec3d.x) * (vec3d1.x - vec3d.x) + (vec3d1.z - vec3d.z) * (vec3d1.z - vec3d.z));
         double d0 = vec3d.x + (double)(13.0F / f1) * (vec3d1.x - vec3d.x);
         double d1 = vec3d.z + (double)(13.0F / f1) * (vec3d1.z - vec3d.z);
         if (f1 <= 64.0F || collection.contains(serverplayerentity)) {
            serverplayerentity.connection.sendPacket(new SPlaySoundEffectPacket(SoundEvents.EVENT_RAID_HORN, SoundCategory.NEUTRAL, d0, serverplayerentity.func_226278_cu_(), d1, 64.0F, 1.0F));
         }
      }

   }

   private void spawnNextWave(BlockPos p_221294_1_) {
      boolean flag = false;
      int i = this.groupsSpawned + 1;
      this.totalHealth = 0.0F;
      DifficultyInstance difficultyinstance = this.world.getDifficultyForLocation(p_221294_1_);
      boolean flag1 = this.func_221305_D();

      for(Raid.WaveMember raid$wavemember : Raid.WaveMember.VALUES) {
         int j = this.func_221330_a(raid$wavemember, i, flag1) + this.func_221335_a(raid$wavemember, this.random, i, difficultyinstance, flag1);
         int k = 0;

         for(int l = 0; l < j; ++l) {
            AbstractRaiderEntity abstractraiderentity = raid$wavemember.type.create(this.world);
            if (!flag && abstractraiderentity.canBeLeader()) {
               abstractraiderentity.setLeader(true);
               this.setLeader(i, abstractraiderentity);
               flag = true;
            }

            this.func_221317_a(i, abstractraiderentity, p_221294_1_, false);
            if (raid$wavemember.type == EntityType.RAVAGER) {
               AbstractRaiderEntity abstractraiderentity1 = null;
               if (i == this.getWaves(Difficulty.NORMAL)) {
                  abstractraiderentity1 = EntityType.PILLAGER.create(this.world);
               } else if (i >= this.getWaves(Difficulty.HARD)) {
                  if (k == 0) {
                     abstractraiderentity1 = EntityType.EVOKER.create(this.world);
                  } else {
                     abstractraiderentity1 = EntityType.VINDICATOR.create(this.world);
                  }
               }

               ++k;
               if (abstractraiderentity1 != null) {
                  this.func_221317_a(i, abstractraiderentity1, p_221294_1_, false);
                  abstractraiderentity1.moveToBlockPosAndAngles(p_221294_1_, 0.0F, 0.0F);
                  abstractraiderentity1.startRiding(abstractraiderentity);
               }
            }
         }
      }

      this.field_221362_z = Optional.empty();
      ++this.groupsSpawned;
      this.updateBarPercentage();
      this.markDirty();
   }

   public void func_221317_a(int wave, AbstractRaiderEntity p_221317_2_, @Nullable BlockPos p_221317_3_, boolean p_221317_4_) {
      boolean flag = this.joinRaid(wave, p_221317_2_);
      if (flag) {
         p_221317_2_.setRaid(this);
         p_221317_2_.setWave(wave);
         p_221317_2_.func_213644_t(true);
         p_221317_2_.func_213653_b(0);
         if (!p_221317_4_ && p_221317_3_ != null) {
            p_221317_2_.setPosition((double)p_221317_3_.getX() + 0.5D, (double)p_221317_3_.getY() + 1.0D, (double)p_221317_3_.getZ() + 0.5D);
            p_221317_2_.onInitialSpawn(this.world, this.world.getDifficultyForLocation(p_221317_3_), SpawnReason.EVENT, (ILivingEntityData)null, (CompoundNBT)null);
            p_221317_2_.func_213660_a(wave, false);
            p_221317_2_.onGround = true;
            this.world.addEntity(p_221317_2_);
         }
      }

   }

   public void updateBarPercentage() {
      this.bossInfo.setPercent(MathHelper.clamp(this.getCurrentHealth() / this.totalHealth, 0.0F, 1.0F));
   }

   public float getCurrentHealth() {
      float f = 0.0F;
      Iterator iterator = this.raiders.values().iterator();

      while(iterator.hasNext()) {
         for(AbstractRaiderEntity abstractraiderentity : (Set<AbstractRaiderEntity>)iterator.next()) {
            f += abstractraiderentity.getHealth();
         }
      }

      return f;
   }

   private boolean func_221318_F() {
      return this.preRaidTicks == 0 && (this.groupsSpawned < this.numGroups || this.func_221305_D()) && this.getRaiderCount() == 0;
   }

   public int getRaiderCount() {
      return this.raiders.values().stream().mapToInt(Set::size).sum();
   }

   public void leaveRaid(AbstractRaiderEntity p_221322_1_, boolean p_221322_2_) {
      Set<AbstractRaiderEntity> set = this.raiders.get(p_221322_1_.func_213642_em());
      if (set != null) {
         boolean flag = set.remove(p_221322_1_);
         if (flag) {
            if (p_221322_2_) {
               this.totalHealth -= p_221322_1_.getHealth();
            }

            p_221322_1_.setRaid((Raid)null);
            this.updateBarPercentage();
            this.markDirty();
         }
      }

   }

   private void markDirty() {
      this.world.getRaids().markDirty();
   }

   public static ItemStack createIllagerBanner() {
      ItemStack itemstack = new ItemStack(Items.WHITE_BANNER);
      CompoundNBT compoundnbt = itemstack.getOrCreateChildTag("BlockEntityTag");
      ListNBT listnbt = (new BannerPattern.Builder()).func_222477_a(BannerPattern.RHOMBUS_MIDDLE, DyeColor.CYAN).func_222477_a(BannerPattern.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY).func_222477_a(BannerPattern.STRIPE_CENTER, DyeColor.GRAY).func_222477_a(BannerPattern.BORDER, DyeColor.LIGHT_GRAY).func_222477_a(BannerPattern.STRIPE_MIDDLE, DyeColor.BLACK).func_222477_a(BannerPattern.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY).func_222477_a(BannerPattern.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY).func_222477_a(BannerPattern.BORDER, DyeColor.BLACK).func_222476_a();
      compoundnbt.put("Patterns", listnbt);
      itemstack.setDisplayName((new TranslationTextComponent("block.minecraft.ominous_banner")).applyTextStyle(TextFormatting.GOLD));
      return itemstack;
   }

   @Nullable
   public AbstractRaiderEntity getLeader(int p_221332_1_) {
      return this.leaders.get(p_221332_1_);
   }

   @Nullable
   private BlockPos func_221298_a(int p_221298_1_, int p_221298_2_) {
      int i = p_221298_1_ == 0 ? 2 : 2 - p_221298_1_;
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int i1 = 0; i1 < p_221298_2_; ++i1) {
         float f = this.world.rand.nextFloat() * ((float)Math.PI * 2F);
         int j = this.center.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0F * (float)i) + this.world.rand.nextInt(5);
         int l = this.center.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0F * (float)i) + this.world.rand.nextInt(5);
         int k = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, j, l);
         blockpos$mutable.setPos(j, k, l);
         if ((!this.world.func_217483_b_(blockpos$mutable) || p_221298_1_ >= 2) && this.world.isAreaLoaded(blockpos$mutable.getX() - 10, blockpos$mutable.getY() - 10, blockpos$mutable.getZ() - 10, blockpos$mutable.getX() + 10, blockpos$mutable.getY() + 10, blockpos$mutable.getZ() + 10) && this.world.getChunkProvider().isChunkLoaded(new ChunkPos(blockpos$mutable)) && (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, this.world, blockpos$mutable, EntityType.RAVAGER) || this.world.getBlockState(blockpos$mutable.down()).getBlock() == Blocks.SNOW && this.world.getBlockState(blockpos$mutable).isAir())) {
            return blockpos$mutable;
         }
      }

      return null;
   }

   private boolean joinRaid(int p_221287_1_, AbstractRaiderEntity p_221287_2_) {
      return this.joinRaid(p_221287_1_, p_221287_2_, true);
   }

   public boolean joinRaid(int p_221300_1_, AbstractRaiderEntity p_221300_2_, boolean p_221300_3_) {
      this.raiders.computeIfAbsent(p_221300_1_, (p_221323_0_) -> {
         return Sets.newHashSet();
      });
      Set<AbstractRaiderEntity> set = this.raiders.get(p_221300_1_);
      AbstractRaiderEntity abstractraiderentity = null;

      for(AbstractRaiderEntity abstractraiderentity1 : set) {
         if (abstractraiderentity1.getUniqueID().equals(p_221300_2_.getUniqueID())) {
            abstractraiderentity = abstractraiderentity1;
            break;
         }
      }

      if (abstractraiderentity != null) {
         set.remove(abstractraiderentity);
         set.add(p_221300_2_);
      }

      set.add(p_221300_2_);
      if (p_221300_3_) {
         this.totalHealth += p_221300_2_.getHealth();
      }

      this.updateBarPercentage();
      this.markDirty();
      return true;
   }

   public void setLeader(int raidId, AbstractRaiderEntity p_221324_2_) {
      this.leaders.put(raidId, p_221324_2_);
      p_221324_2_.setItemStackToSlot(EquipmentSlotType.HEAD, createIllagerBanner());
      p_221324_2_.setDropChance(EquipmentSlotType.HEAD, 2.0F);
   }

   public void removeLeader(int p_221296_1_) {
      this.leaders.remove(p_221296_1_);
   }

   public BlockPos func_221304_t() {
      return this.center;
   }

   private void func_223024_c(BlockPos p_223024_1_) {
      this.center = p_223024_1_;
   }

   public int getId() {
      return this.id;
   }

   private int func_221330_a(Raid.WaveMember p_221330_1_, int p_221330_2_, boolean p_221330_3_) {
      return p_221330_3_ ? p_221330_1_.waveCounts[this.numGroups] : p_221330_1_.waveCounts[p_221330_2_];
   }

   private int func_221335_a(Raid.WaveMember p_221335_1_, Random p_221335_2_, int wave, DifficultyInstance p_221335_4_, boolean p_221335_5_) {
      Difficulty difficulty = p_221335_4_.getDifficulty();
      boolean flag = difficulty == Difficulty.EASY;
      boolean flag1 = difficulty == Difficulty.NORMAL;
      int i;
      switch(p_221335_1_) {
      case WITCH:
         if (flag || wave <= 2 || wave == 4) {
            return 0;
         }

         i = 1;
         break;
      case PILLAGER:
      case VINDICATOR:
         if (flag) {
            i = p_221335_2_.nextInt(2);
         } else if (flag1) {
            i = 1;
         } else {
            i = 2;
         }
         break;
      case RAVAGER:
         i = !flag && p_221335_5_ ? 1 : 0;
         break;
      default:
         return 0;
      }

      return i > 0 ? p_221335_2_.nextInt(i + 1) : 0;
   }

   public boolean isActive() {
      return this.active;
   }

   public CompoundNBT write(CompoundNBT p_221326_1_) {
      p_221326_1_.putInt("Id", this.id);
      p_221326_1_.putBoolean("Started", this.started);
      p_221326_1_.putBoolean("Active", this.active);
      p_221326_1_.putLong("TicksActive", this.ticksActive);
      p_221326_1_.putInt("BadOmenLevel", this.badOmenLevel);
      p_221326_1_.putInt("GroupsSpawned", this.groupsSpawned);
      p_221326_1_.putInt("PreRaidTicks", this.preRaidTicks);
      p_221326_1_.putInt("PostRaidTicks", this.postRaidTicks);
      p_221326_1_.putFloat("TotalHealth", this.totalHealth);
      p_221326_1_.putInt("NumGroups", this.numGroups);
      p_221326_1_.putString("Status", this.status.func_221277_a());
      p_221326_1_.putInt("CX", this.center.getX());
      p_221326_1_.putInt("CY", this.center.getY());
      p_221326_1_.putInt("CZ", this.center.getZ());
      ListNBT listnbt = new ListNBT();

      for(UUID uuid : this.heroes) {
         CompoundNBT compoundnbt = new CompoundNBT();
         compoundnbt.putUniqueId("UUID", uuid);
         listnbt.add(compoundnbt);
      }

      p_221326_1_.put("HeroesOfTheVillage", listnbt);
      return p_221326_1_;
   }

   public int getWaves(Difficulty p_221306_1_) {
      switch(p_221306_1_) {
      case EASY:
         return 3;
      case NORMAL:
         return 5;
      case HARD:
         return 7;
      default:
         return 0;
      }
   }

   public float func_221308_w() {
      int i = this.func_221291_n();
      if (i == 2) {
         return 0.1F;
      } else if (i == 3) {
         return 0.25F;
      } else if (i == 4) {
         return 0.5F;
      } else {
         return i == 5 ? 0.75F : 0.0F;
      }
   }

   public void addHero(Entity p_221311_1_) {
      this.heroes.add(p_221311_1_.getUniqueID());
   }

   static enum Status {
      ONGOING,
      VICTORY,
      LOSS,
      STOPPED;

      private static final Raid.Status[] field_221278_e = values();

      private static Raid.Status func_221275_b(String p_221275_0_) {
         for(Raid.Status raid$status : field_221278_e) {
            if (p_221275_0_.equalsIgnoreCase(raid$status.name())) {
               return raid$status;
            }
         }

         return ONGOING;
      }

      public String func_221277_a() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   static enum WaveMember {
      VINDICATOR(EntityType.VINDICATOR, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
      EVOKER(EntityType.EVOKER, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
      PILLAGER(EntityType.PILLAGER, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
      WITCH(EntityType.WITCH, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
      RAVAGER(EntityType.RAVAGER, new int[]{0, 0, 0, 1, 0, 1, 0, 2});

      private static final Raid.WaveMember[] VALUES = values();
      private final EntityType<? extends AbstractRaiderEntity> type;
      private final int[] waveCounts;

      private WaveMember(EntityType<? extends AbstractRaiderEntity> p_i50602_3_, int[] p_i50602_4_) {
         this.type = p_i50602_3_;
         this.waveCounts = p_i50602_4_;
      }
   }
}