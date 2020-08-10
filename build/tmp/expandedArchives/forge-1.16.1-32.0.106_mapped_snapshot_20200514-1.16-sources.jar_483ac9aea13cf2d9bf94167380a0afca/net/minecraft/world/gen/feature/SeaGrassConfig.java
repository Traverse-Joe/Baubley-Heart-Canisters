package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class SeaGrassConfig implements IFeatureConfig {
   public static final Codec<SeaGrassConfig> field_236630_a_ = RecordCodecBuilder.create((p_236632_0_) -> {
      return p_236632_0_.group(Codec.INT.fieldOf("count").forGetter((p_236633_0_) -> {
         return p_236633_0_.count;
      }), Codec.DOUBLE.fieldOf("probability").forGetter((p_236631_0_) -> {
         return p_236631_0_.tallProbability;
      })).apply(p_236632_0_, SeaGrassConfig::new);
   });
   public final int count;
   public final double tallProbability;

   public SeaGrassConfig(int count, double tallProbability) {
      this.count = count;
      this.tallProbability = tallProbability;
   }
}