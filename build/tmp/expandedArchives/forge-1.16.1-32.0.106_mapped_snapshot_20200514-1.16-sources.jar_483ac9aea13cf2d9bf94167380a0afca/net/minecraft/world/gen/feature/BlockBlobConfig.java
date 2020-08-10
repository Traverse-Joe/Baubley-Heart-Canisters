package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;

public class BlockBlobConfig implements IFeatureConfig {
   public static final Codec<BlockBlobConfig> field_236449_a_ = RecordCodecBuilder.create((p_236451_0_) -> {
      return p_236451_0_.group(BlockState.field_235877_b_.fieldOf("state").forGetter((p_236452_0_) -> {
         return p_236452_0_.state;
      }), Codec.INT.fieldOf("start_radius").withDefault(0).forGetter((p_236450_0_) -> {
         return p_236450_0_.startRadius;
      })).apply(p_236451_0_, BlockBlobConfig::new);
   });
   public final BlockState state;
   public final int startRadius;

   public BlockBlobConfig(BlockState state, int startRadius) {
      this.state = state;
      this.startRadius = startRadius;
   }
}