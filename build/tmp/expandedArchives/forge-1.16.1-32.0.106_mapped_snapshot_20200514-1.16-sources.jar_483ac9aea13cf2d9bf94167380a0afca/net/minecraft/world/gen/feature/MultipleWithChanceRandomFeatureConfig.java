package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class MultipleWithChanceRandomFeatureConfig implements IFeatureConfig {
   public static final Codec<MultipleWithChanceRandomFeatureConfig> field_236600_a_ = RecordCodecBuilder.create((p_236602_0_) -> {
      return p_236602_0_.group(ConfiguredFeature.field_236264_b_.listOf().fieldOf("features").forGetter((p_236603_0_) -> {
         return p_236603_0_.features;
      }), Codec.INT.fieldOf("count").withDefault(0).forGetter((p_236601_0_) -> {
         return p_236601_0_.count;
      })).apply(p_236602_0_, MultipleWithChanceRandomFeatureConfig::new);
   });
   public final List<ConfiguredFeature<?, ?>> features;
   public final int count;

   public MultipleWithChanceRandomFeatureConfig(List<ConfiguredFeature<?, ?>> features, int count) {
      this.features = features;
      this.count = count;
   }
}