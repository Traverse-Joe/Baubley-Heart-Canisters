package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ChanceRangeConfig implements IPlacementConfig {
   public static final Codec<ChanceRangeConfig> field_236459_a_ = RecordCodecBuilder.create((p_236461_0_) -> {
      return p_236461_0_.group(Codec.FLOAT.fieldOf("chance").forGetter((p_236464_0_) -> {
         return p_236464_0_.chance;
      }), Codec.INT.fieldOf("bottom_offset").withDefault(0).forGetter((p_236463_0_) -> {
         return p_236463_0_.bottomOffset;
      }), Codec.INT.fieldOf("top_offset").withDefault(0).forGetter((p_236462_0_) -> {
         return p_236462_0_.topOffset;
      }), Codec.INT.fieldOf("top").withDefault(0).forGetter((p_236460_0_) -> {
         return p_236460_0_.top;
      })).apply(p_236461_0_, ChanceRangeConfig::new);
   });
   public final float chance;
   public final int bottomOffset;
   public final int topOffset;
   public final int top;

   public ChanceRangeConfig(float chance, int bottomOffset, int topOffset, int top) {
      this.chance = chance;
      this.bottomOffset = bottomOffset;
      this.topOffset = topOffset;
      this.top = top;
   }
}