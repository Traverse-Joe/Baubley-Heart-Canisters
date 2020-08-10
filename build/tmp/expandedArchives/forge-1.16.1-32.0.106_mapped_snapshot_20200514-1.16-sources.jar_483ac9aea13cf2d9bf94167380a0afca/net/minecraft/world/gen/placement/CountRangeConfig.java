package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CountRangeConfig implements IPlacementConfig {
   public static final Codec<CountRangeConfig> field_236485_a_ = RecordCodecBuilder.create((p_236487_0_) -> {
      return p_236487_0_.group(Codec.INT.fieldOf("count").forGetter((p_236490_0_) -> {
         return p_236490_0_.count;
      }), Codec.INT.fieldOf("bottom_offset").withDefault(0).forGetter((p_236489_0_) -> {
         return p_236489_0_.bottomOffset;
      }), Codec.INT.fieldOf("top_offset").withDefault(0).forGetter((p_236488_0_) -> {
         return p_236488_0_.topOffset;
      }), Codec.INT.fieldOf("maximum").withDefault(0).forGetter((p_236486_0_) -> {
         return p_236486_0_.maximum;
      })).apply(p_236487_0_, CountRangeConfig::new);
   });
   public final int count;
   public final int bottomOffset;
   public final int topOffset;
   public final int maximum;

   public CountRangeConfig(int count, int bottomOffset, int topOffset, int maximum) {
      this.count = count;
      this.bottomOffset = bottomOffset;
      this.topOffset = topOffset;
      this.maximum = maximum;
   }
}