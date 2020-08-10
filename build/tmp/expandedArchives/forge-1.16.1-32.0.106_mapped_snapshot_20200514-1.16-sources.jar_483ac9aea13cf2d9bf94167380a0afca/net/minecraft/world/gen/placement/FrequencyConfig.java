package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;

public class FrequencyConfig implements IPlacementConfig {
   public static final Codec<FrequencyConfig> field_236971_a_ = Codec.INT.fieldOf("count").xmap(FrequencyConfig::new, (p_236972_0_) -> {
      return p_236972_0_.count;
   }).codec();
   public final int count;

   public FrequencyConfig(int count) {
      this.count = count;
   }
}