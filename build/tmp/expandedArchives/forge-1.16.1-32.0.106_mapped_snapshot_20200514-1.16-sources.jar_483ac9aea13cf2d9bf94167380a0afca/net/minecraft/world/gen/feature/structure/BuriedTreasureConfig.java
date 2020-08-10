package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BuriedTreasureConfig implements IFeatureConfig {
   public static final Codec<BuriedTreasureConfig> field_236457_a_ = Codec.FLOAT.xmap(BuriedTreasureConfig::new, (p_236458_0_) -> {
      return p_236458_0_.probability;
   });
   public final float probability;

   public BuriedTreasureConfig(float probability) {
      this.probability = probability;
   }
}