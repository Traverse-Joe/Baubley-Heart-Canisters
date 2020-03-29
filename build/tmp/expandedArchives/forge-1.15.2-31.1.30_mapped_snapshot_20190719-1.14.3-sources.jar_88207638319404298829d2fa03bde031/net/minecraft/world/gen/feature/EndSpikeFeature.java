package net.minecraft.world.gen.feature;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;

public class EndSpikeFeature extends Feature<EndSpikeFeatureConfig> {
   private static final LoadingCache<Long, List<EndSpikeFeature.EndSpike>> field_214555_a = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build(new EndSpikeFeature.EndSpikeCacheLoader());

   public EndSpikeFeature(Function<Dynamic<?>, ? extends EndSpikeFeatureConfig> p_i51432_1_) {
      super(p_i51432_1_);
   }

   public static List<EndSpikeFeature.EndSpike> func_214554_a(IWorld p_214554_0_) {
      Random random = new Random(p_214554_0_.getSeed());
      long i = random.nextLong() & 65535L;
      return field_214555_a.getUnchecked(i);
   }

   public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, EndSpikeFeatureConfig config) {
      List<EndSpikeFeature.EndSpike> list = config.func_214671_b();
      if (list.isEmpty()) {
         list = func_214554_a(worldIn);
      }

      for(EndSpikeFeature.EndSpike endspikefeature$endspike : list) {
         if (endspikefeature$endspike.doesStartInChunk(pos)) {
            this.func_214553_a(worldIn, rand, config, endspikefeature$endspike);
         }
      }

      return true;
   }

   private void func_214553_a(IWorld p_214553_1_, Random p_214553_2_, EndSpikeFeatureConfig p_214553_3_, EndSpikeFeature.EndSpike p_214553_4_) {
      int i = p_214553_4_.getRadius();

      for(BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(p_214553_4_.getCenterX() - i, 0, p_214553_4_.getCenterZ() - i), new BlockPos(p_214553_4_.getCenterX() + i, p_214553_4_.getHeight() + 10, p_214553_4_.getCenterZ() + i))) {
         if (blockpos.distanceSq((double)p_214553_4_.getCenterX(), (double)blockpos.getY(), (double)p_214553_4_.getCenterZ(), false) <= (double)(i * i + 1) && blockpos.getY() < p_214553_4_.getHeight()) {
            this.setBlockState(p_214553_1_, blockpos, Blocks.OBSIDIAN.getDefaultState());
         } else if (blockpos.getY() > 65) {
            this.setBlockState(p_214553_1_, blockpos, Blocks.AIR.getDefaultState());
         }
      }

      if (p_214553_4_.isGuarded()) {
         int j1 = -2;
         int k1 = 2;
         int j = 3;
         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

         for(int k = -2; k <= 2; ++k) {
            for(int l = -2; l <= 2; ++l) {
               for(int i1 = 0; i1 <= 3; ++i1) {
                  boolean flag = MathHelper.abs(k) == 2;
                  boolean flag1 = MathHelper.abs(l) == 2;
                  boolean flag2 = i1 == 3;
                  if (flag || flag1 || flag2) {
                     boolean flag3 = k == -2 || k == 2 || flag2;
                     boolean flag4 = l == -2 || l == 2 || flag2;
                     BlockState blockstate = Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, Boolean.valueOf(flag3 && l != -2)).with(PaneBlock.SOUTH, Boolean.valueOf(flag3 && l != 2)).with(PaneBlock.WEST, Boolean.valueOf(flag4 && k != -2)).with(PaneBlock.EAST, Boolean.valueOf(flag4 && k != 2));
                     this.setBlockState(p_214553_1_, blockpos$mutable.setPos(p_214553_4_.getCenterX() + k, p_214553_4_.getHeight() + i1, p_214553_4_.getCenterZ() + l), blockstate);
                  }
               }
            }
         }
      }

      EnderCrystalEntity endercrystalentity = EntityType.END_CRYSTAL.create(p_214553_1_.getWorld());
      endercrystalentity.setBeamTarget(p_214553_3_.func_214668_c());
      endercrystalentity.setInvulnerable(p_214553_3_.func_214669_a());
      endercrystalentity.setLocationAndAngles((double)((float)p_214553_4_.getCenterX() + 0.5F), (double)(p_214553_4_.getHeight() + 1), (double)((float)p_214553_4_.getCenterZ() + 0.5F), p_214553_2_.nextFloat() * 360.0F, 0.0F);
      p_214553_1_.addEntity(endercrystalentity);
      this.setBlockState(p_214553_1_, new BlockPos(p_214553_4_.getCenterX(), p_214553_4_.getHeight(), p_214553_4_.getCenterZ()), Blocks.BEDROCK.getDefaultState());
   }

   public static class EndSpike {
      private final int centerX;
      private final int centerZ;
      private final int radius;
      private final int height;
      private final boolean guarded;
      private final AxisAlignedBB topBoundingBox;

      public EndSpike(int p_i47020_1_, int p_i47020_2_, int p_i47020_3_, int p_i47020_4_, boolean p_i47020_5_) {
         this.centerX = p_i47020_1_;
         this.centerZ = p_i47020_2_;
         this.radius = p_i47020_3_;
         this.height = p_i47020_4_;
         this.guarded = p_i47020_5_;
         this.topBoundingBox = new AxisAlignedBB((double)(p_i47020_1_ - p_i47020_3_), 0.0D, (double)(p_i47020_2_ - p_i47020_3_), (double)(p_i47020_1_ + p_i47020_3_), 256.0D, (double)(p_i47020_2_ + p_i47020_3_));
      }

      public boolean doesStartInChunk(BlockPos p_186154_1_) {
         return p_186154_1_.getX() >> 4 == this.centerX >> 4 && p_186154_1_.getZ() >> 4 == this.centerZ >> 4;
      }

      public int getCenterX() {
         return this.centerX;
      }

      public int getCenterZ() {
         return this.centerZ;
      }

      public int getRadius() {
         return this.radius;
      }

      public int getHeight() {
         return this.height;
      }

      public boolean isGuarded() {
         return this.guarded;
      }

      public AxisAlignedBB getTopBoundingBox() {
         return this.topBoundingBox;
      }

      public <T> Dynamic<T> func_214749_a(DynamicOps<T> p_214749_1_) {
         Builder<T, T> builder = ImmutableMap.builder();
         builder.put(p_214749_1_.createString("centerX"), p_214749_1_.createInt(this.centerX));
         builder.put(p_214749_1_.createString("centerZ"), p_214749_1_.createInt(this.centerZ));
         builder.put(p_214749_1_.createString("radius"), p_214749_1_.createInt(this.radius));
         builder.put(p_214749_1_.createString("height"), p_214749_1_.createInt(this.height));
         builder.put(p_214749_1_.createString("guarded"), p_214749_1_.createBoolean(this.guarded));
         return new Dynamic<>(p_214749_1_, p_214749_1_.createMap(builder.build()));
      }

      public static <T> EndSpikeFeature.EndSpike func_214747_a(Dynamic<T> p_214747_0_) {
         return new EndSpikeFeature.EndSpike(p_214747_0_.get("centerX").asInt(0), p_214747_0_.get("centerZ").asInt(0), p_214747_0_.get("radius").asInt(0), p_214747_0_.get("height").asInt(0), p_214747_0_.get("guarded").asBoolean(false));
      }
   }

   static class EndSpikeCacheLoader extends CacheLoader<Long, List<EndSpikeFeature.EndSpike>> {
      private EndSpikeCacheLoader() {
      }

      public List<EndSpikeFeature.EndSpike> load(Long p_load_1_) {
         List<Integer> list = IntStream.range(0, 10).boxed().collect(Collectors.toList());
         Collections.shuffle(list, new Random(p_load_1_));
         List<EndSpikeFeature.EndSpike> list1 = Lists.newArrayList();

         for(int i = 0; i < 10; ++i) {
            int j = MathHelper.floor(42.0D * Math.cos(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
            int k = MathHelper.floor(42.0D * Math.sin(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
            int l = list.get(i);
            int i1 = 2 + l / 3;
            int j1 = 76 + l * 3;
            boolean flag = l == 1 || l == 2;
            list1.add(new EndSpikeFeature.EndSpike(j, k, i1, j1, flag));
         }

         return list1;
      }
   }
}