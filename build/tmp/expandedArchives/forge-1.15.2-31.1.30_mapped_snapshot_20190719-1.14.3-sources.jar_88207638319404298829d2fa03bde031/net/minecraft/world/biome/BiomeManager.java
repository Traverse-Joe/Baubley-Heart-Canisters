package net.minecraft.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.provider.BiomeProvider;

public class BiomeManager {
   private final BiomeManager.IBiomeReader field_226832_a_;
   private final long field_226833_b_;
   private final IBiomeMagnifier field_226834_c_;

   public BiomeManager(BiomeManager.IBiomeReader p_i225744_1_, long p_i225744_2_, IBiomeMagnifier p_i225744_4_) {
      this.field_226832_a_ = p_i225744_1_;
      this.field_226833_b_ = p_i225744_2_;
      this.field_226834_c_ = p_i225744_4_;
   }

   public BiomeManager func_226835_a_(BiomeProvider p_226835_1_) {
      return new BiomeManager(p_226835_1_, this.field_226833_b_, this.field_226834_c_);
   }

   public Biome func_226836_a_(BlockPos p_226836_1_) {
      return this.field_226834_c_.func_225532_a_(this.field_226833_b_, p_226836_1_.getX(), p_226836_1_.getY(), p_226836_1_.getZ(), this.field_226832_a_);
   }

   public interface IBiomeReader {
      Biome func_225526_b_(int p_225526_1_, int p_225526_2_, int p_225526_3_);
   }
}