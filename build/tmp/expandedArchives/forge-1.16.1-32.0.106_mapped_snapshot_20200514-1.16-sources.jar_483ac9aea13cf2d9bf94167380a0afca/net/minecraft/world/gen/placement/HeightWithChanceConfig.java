package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class HeightWithChanceConfig implements IPlacementConfig {
   public static final Codec<HeightWithChanceConfig> field_236967_a_ = RecordCodecBuilder.create((p_236968_0_) -> {
      return p_236968_0_.group(Codec.INT.fieldOf("count").forGetter((p_236970_0_) -> {
         return p_236970_0_.count;
      }), Codec.FLOAT.fieldOf("chance").forGetter((p_236969_0_) -> {
         return p_236969_0_.chance;
      })).apply(p_236968_0_, HeightWithChanceConfig::new);
   });
   public final int count;
   public final float chance;

   public HeightWithChanceConfig(int count, float chance) {
      this.count = count;
      this.chance = chance;
   }
}