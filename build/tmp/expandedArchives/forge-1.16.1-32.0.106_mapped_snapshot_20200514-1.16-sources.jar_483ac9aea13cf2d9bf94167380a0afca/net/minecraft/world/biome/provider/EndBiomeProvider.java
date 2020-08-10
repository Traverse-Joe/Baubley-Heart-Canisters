package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndBiomeProvider extends BiomeProvider {
   public static final Codec<EndBiomeProvider> field_235314_e_ = Codec.LONG.fieldOf("seed").xmap(EndBiomeProvider::new, (p_235316_0_) -> {
      return p_235316_0_.field_235315_h_;
   }).stable().codec();
   private final SimplexNoiseGenerator generator;
   private static final List<Biome> END_BIOMES = ImmutableList.of(Biomes.THE_END, Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS, Biomes.SMALL_END_ISLANDS, Biomes.END_BARRENS);
   private final long field_235315_h_;

   public EndBiomeProvider(long p_i231645_1_) {
      super(END_BIOMES);
      this.field_235315_h_ = p_i231645_1_;
      SharedSeedRandom sharedseedrandom = new SharedSeedRandom(p_i231645_1_);
      sharedseedrandom.skip(17292);
      this.generator = new SimplexNoiseGenerator(sharedseedrandom);
   }

   protected Codec<? extends BiomeProvider> func_230319_a_() {
      return field_235314_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeProvider func_230320_a_(long p_230320_1_) {
      return new EndBiomeProvider(p_230320_1_);
   }

   public Biome getNoiseBiome(int x, int y, int z) {
      int i = x >> 2;
      int j = z >> 2;
      if ((long)i * (long)i + (long)j * (long)j <= 4096L) {
         return Biomes.THE_END;
      } else {
         float f = func_235317_a_(this.generator, i * 2 + 1, j * 2 + 1);
         if (f > 40.0F) {
            return Biomes.END_HIGHLANDS;
         } else if (f >= 0.0F) {
            return Biomes.END_MIDLANDS;
         } else {
            return f < -20.0F ? Biomes.SMALL_END_ISLANDS : Biomes.END_BARRENS;
         }
      }
   }

   public boolean func_235318_b_(long p_235318_1_) {
      return this.field_235315_h_ == p_235318_1_;
   }

   public static float func_235317_a_(SimplexNoiseGenerator p_235317_0_, int p_235317_1_, int p_235317_2_) {
      int i = p_235317_1_ / 2;
      int j = p_235317_2_ / 2;
      int k = p_235317_1_ % 2;
      int l = p_235317_2_ % 2;
      float f = 100.0F - MathHelper.sqrt((float)(p_235317_1_ * p_235317_1_ + p_235317_2_ * p_235317_2_)) * 8.0F;
      f = MathHelper.clamp(f, -100.0F, 80.0F);

      for(int i1 = -12; i1 <= 12; ++i1) {
         for(int j1 = -12; j1 <= 12; ++j1) {
            long k1 = (long)(i + i1);
            long l1 = (long)(j + j1);
            if (k1 * k1 + l1 * l1 > 4096L && p_235317_0_.getValue((double)k1, (double)l1) < (double)-0.9F) {
               float f1 = (MathHelper.abs((float)k1) * 3439.0F + MathHelper.abs((float)l1) * 147.0F) % 13.0F + 9.0F;
               float f2 = (float)(k - i1 * 2);
               float f3 = (float)(l - j1 * 2);
               float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
               f4 = MathHelper.clamp(f4, -100.0F, 80.0F);
               f = Math.max(f, f4);
            }
         }
      }

      return f;
   }
}