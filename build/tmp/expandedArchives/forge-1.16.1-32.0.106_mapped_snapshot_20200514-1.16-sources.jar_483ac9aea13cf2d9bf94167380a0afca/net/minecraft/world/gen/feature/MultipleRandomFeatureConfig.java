package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class MultipleRandomFeatureConfig implements IFeatureConfig {
   public static final Codec<MultipleRandomFeatureConfig> field_236583_a_ = RecordCodecBuilder.create((p_236585_0_) -> {
      return p_236585_0_.apply2(MultipleRandomFeatureConfig::new, ConfiguredRandomFeatureList.field_236430_a_.listOf().fieldOf("features").forGetter((p_236586_0_) -> {
         return p_236586_0_.features;
      }), ConfiguredFeature.field_236264_b_.fieldOf("default").forGetter((p_236584_0_) -> {
         return p_236584_0_.defaultFeature;
      }));
   });
   public final List<ConfiguredRandomFeatureList<?>> features;
   public final ConfiguredFeature<?, ?> defaultFeature;

   public MultipleRandomFeatureConfig(List<ConfiguredRandomFeatureList<?>> features, ConfiguredFeature<?, ?> defaultFeature) {
      this.features = features;
      this.defaultFeature = defaultFeature;
   }
}