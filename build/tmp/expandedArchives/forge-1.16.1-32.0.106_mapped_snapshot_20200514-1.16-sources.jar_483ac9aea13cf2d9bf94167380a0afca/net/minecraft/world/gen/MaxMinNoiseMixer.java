package net.minecraft.world.gen;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.util.SharedSeedRandom;

public class MaxMinNoiseMixer {
   private final double field_237208_a_;
   private final OctavesNoiseGenerator field_237209_b_;
   private final OctavesNoiseGenerator field_237210_c_;

   public MaxMinNoiseMixer(SharedSeedRandom p_i232140_1_, IntStream p_i232140_2_) {
      this(p_i232140_1_, p_i232140_2_.boxed().collect(ImmutableList.toImmutableList()));
   }

   public MaxMinNoiseMixer(SharedSeedRandom p_i232139_1_, List<Integer> p_i232139_2_) {
      this.field_237209_b_ = new OctavesNoiseGenerator(p_i232139_1_, p_i232139_2_);
      this.field_237210_c_ = new OctavesNoiseGenerator(p_i232139_1_, p_i232139_2_);
      int i = p_i232139_2_.stream().min(Integer::compareTo).orElse(0);
      int j = p_i232139_2_.stream().max(Integer::compareTo).orElse(0);
      this.field_237208_a_ = 0.16666666666666666D / func_237212_a_(j - i);
   }

   private static double func_237212_a_(int p_237212_0_) {
      return 0.1D * (1.0D + 1.0D / (double)(p_237212_0_ + 1));
   }

   public double func_237211_a_(double p_237211_1_, double p_237211_3_, double p_237211_5_) {
      double d0 = p_237211_1_ * 1.0181268882175227D;
      double d1 = p_237211_3_ * 1.0181268882175227D;
      double d2 = p_237211_5_ * 1.0181268882175227D;
      return (this.field_237209_b_.func_205563_a(p_237211_1_, p_237211_3_, p_237211_5_) + this.field_237210_c_.func_205563_a(d0, d1, d2)) * this.field_237208_a_;
   }
}