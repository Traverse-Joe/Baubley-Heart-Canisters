package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopSolidWithNoiseConfig implements IPlacementConfig {
   public static final Codec<TopSolidWithNoiseConfig> field_236978_a_ = RecordCodecBuilder.create((p_236980_0_) -> {
      return p_236980_0_.group(Codec.INT.fieldOf("noise_to_count_ratio").forGetter((p_236984_0_) -> {
         return p_236984_0_.noiseToCountRatio;
      }), Codec.DOUBLE.fieldOf("noise_factor").forGetter((p_236983_0_) -> {
         return p_236983_0_.noiseFactor;
      }), Codec.DOUBLE.fieldOf("noise_offset").withDefault(0.0D).forGetter((p_236982_0_) -> {
         return p_236982_0_.noiseOffset;
      }), Heightmap.Type.field_236078_g_.fieldOf("heightmap").forGetter((p_236981_0_) -> {
         return p_236981_0_.heightmap;
      })).apply(p_236980_0_, TopSolidWithNoiseConfig::new);
   });
   private static final Logger field_236979_g_ = LogManager.getLogger();
   public final int noiseToCountRatio;
   public final double noiseFactor;
   public final double noiseOffset;
   public final Heightmap.Type heightmap;

   public TopSolidWithNoiseConfig(int noiseToCountRatio, double noiseFactor, double noiseOffset, Heightmap.Type heightmap) {
      this.noiseToCountRatio = noiseToCountRatio;
      this.noiseFactor = noiseFactor;
      this.noiseOffset = noiseOffset;
      this.heightmap = heightmap;
   }
}