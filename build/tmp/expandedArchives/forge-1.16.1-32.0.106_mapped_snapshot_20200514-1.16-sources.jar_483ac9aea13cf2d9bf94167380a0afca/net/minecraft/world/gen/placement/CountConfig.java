package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CountConfig implements IFeatureConfig {
   public static final Codec<CountConfig> field_236483_a_ = Codec.INT.fieldOf("count").xmap(CountConfig::new, (p_236484_0_) -> {
      return p_236484_0_.count;
   }).codec();
   public final int count;

   public CountConfig(int count) {
      this.count = count;
   }
}