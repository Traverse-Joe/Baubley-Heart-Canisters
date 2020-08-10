package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;

public class FeatureRadiusConfig implements IFeatureConfig {
   public static final Codec<FeatureRadiusConfig> field_236526_a_ = Codec.INT.fieldOf("radius").xmap(FeatureRadiusConfig::new, (p_236527_0_) -> {
      return p_236527_0_.radius;
   }).codec();
   public final int radius;

   public FeatureRadiusConfig(int radius) {
      this.radius = radius;
   }
}