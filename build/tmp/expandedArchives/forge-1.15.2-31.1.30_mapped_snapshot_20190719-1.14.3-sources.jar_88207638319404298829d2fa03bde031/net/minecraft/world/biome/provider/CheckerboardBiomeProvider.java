package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.biome.Biome;

public class CheckerboardBiomeProvider extends BiomeProvider {
   private final Biome[] field_205320_b;
   private final int field_205321_c;

   public CheckerboardBiomeProvider(CheckerboardBiomeProviderSettings p_i48973_1_) {
      super(ImmutableSet.copyOf(p_i48973_1_.getBiomes()));
      this.field_205320_b = p_i48973_1_.getBiomes();
      this.field_205321_c = p_i48973_1_.getSize() + 2;
   }

   public Biome func_225526_b_(int p_225526_1_, int p_225526_2_, int p_225526_3_) {
      return this.field_205320_b[Math.abs(((p_225526_1_ >> this.field_205321_c) + (p_225526_3_ >> this.field_205321_c)) % this.field_205320_b.length)];
   }
}