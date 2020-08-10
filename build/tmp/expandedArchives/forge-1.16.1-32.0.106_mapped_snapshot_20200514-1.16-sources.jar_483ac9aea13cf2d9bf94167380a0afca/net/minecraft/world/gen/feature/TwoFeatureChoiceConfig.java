package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class TwoFeatureChoiceConfig implements IFeatureConfig {
   public static final Codec<TwoFeatureChoiceConfig> field_236579_a_ = RecordCodecBuilder.create((p_236581_0_) -> {
      return p_236581_0_.group(ConfiguredFeature.field_236264_b_.fieldOf("feature_true").forGetter((p_236582_0_) -> {
         return p_236582_0_.field_227285_a_;
      }), ConfiguredFeature.field_236264_b_.fieldOf("feature_false").forGetter((p_236580_0_) -> {
         return p_236580_0_.field_227286_b_;
      })).apply(p_236581_0_, TwoFeatureChoiceConfig::new);
   });
   public final ConfiguredFeature<?, ?> field_227285_a_;
   public final ConfiguredFeature<?, ?> field_227286_b_;

   public TwoFeatureChoiceConfig(ConfiguredFeature<?, ?> p_i225835_1_, ConfiguredFeature<?, ?> p_i225835_2_) {
      this.field_227285_a_ = p_i225835_1_;
      this.field_227286_b_ = p_i225835_2_;
   }
}