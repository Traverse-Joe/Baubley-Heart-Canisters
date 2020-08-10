package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.List;

public class SingleRandomFeature implements IFeatureConfig {
   public static final Codec<SingleRandomFeature> field_236642_a_ = ConfiguredFeature.field_236264_b_.listOf().fieldOf("features").xmap(SingleRandomFeature::new, (p_236643_0_) -> {
      return p_236643_0_.features;
   }).codec();
   public final List<ConfiguredFeature<?, ?>> features;

   public SingleRandomFeature(List<ConfiguredFeature<?, ?>> features) {
      this.features = features;
   }
}