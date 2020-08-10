package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.placement.ConfiguredPlacement;

public class DecoratedFeatureConfig implements IFeatureConfig {
   public static final Codec<DecoratedFeatureConfig> field_236491_a_ = RecordCodecBuilder.create((p_236493_0_) -> {
      return p_236493_0_.group(ConfiguredFeature.field_236264_b_.fieldOf("feature").forGetter((p_236494_0_) -> {
         return p_236494_0_.feature;
      }), ConfiguredPlacement.field_236952_a_.fieldOf("decorator").forGetter((p_236492_0_) -> {
         return p_236492_0_.decorator;
      })).apply(p_236493_0_, DecoratedFeatureConfig::new);
   });
   public final ConfiguredFeature<?, ?> feature;
   public final ConfiguredPlacement<?> decorator;

   public DecoratedFeatureConfig(ConfiguredFeature<?, ?> feature, ConfiguredPlacement<?> decorator) {
      this.feature = feature;
      this.decorator = decorator;
   }

   public String toString() {
      return String.format("< %s [%s | %s] >", this.getClass().getSimpleName(), Registry.FEATURE.getKey(this.feature.feature), Registry.DECORATOR.getKey(this.decorator.decorator));
   }
}