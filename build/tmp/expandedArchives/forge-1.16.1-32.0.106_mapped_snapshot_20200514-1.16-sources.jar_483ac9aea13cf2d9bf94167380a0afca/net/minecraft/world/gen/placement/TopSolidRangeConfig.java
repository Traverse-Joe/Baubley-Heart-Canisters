package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class TopSolidRangeConfig implements IPlacementConfig {
   public static final Codec<TopSolidRangeConfig> field_236985_a_ = RecordCodecBuilder.create((p_236986_0_) -> {
      return p_236986_0_.group(Codec.INT.fieldOf("min").forGetter((p_236988_0_) -> {
         return p_236988_0_.min;
      }), Codec.INT.fieldOf("max").forGetter((p_236987_0_) -> {
         return p_236987_0_.max;
      })).apply(p_236986_0_, TopSolidRangeConfig::new);
   });
   public final int min;
   public final int max;

   public TopSolidRangeConfig(int min, int max) {
      this.min = min;
      this.max = max;
   }
}