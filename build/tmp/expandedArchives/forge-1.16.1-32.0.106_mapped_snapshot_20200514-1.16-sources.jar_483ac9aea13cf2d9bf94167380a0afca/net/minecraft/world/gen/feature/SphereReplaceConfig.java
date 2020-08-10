package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.block.BlockState;

public class SphereReplaceConfig implements IFeatureConfig {
   public static final Codec<SphereReplaceConfig> field_236516_a_ = RecordCodecBuilder.create((p_236518_0_) -> {
      return p_236518_0_.group(BlockState.field_235877_b_.fieldOf("state").forGetter((p_236521_0_) -> {
         return p_236521_0_.state;
      }), Codec.INT.fieldOf("radius").withDefault(0).forGetter((p_236520_0_) -> {
         return p_236520_0_.radius;
      }), Codec.INT.fieldOf("y_size").withDefault(0).forGetter((p_236519_0_) -> {
         return p_236519_0_.ySize;
      }), BlockState.field_235877_b_.listOf().fieldOf("targets").forGetter((p_236517_0_) -> {
         return p_236517_0_.targets;
      })).apply(p_236518_0_, SphereReplaceConfig::new);
   });
   public final BlockState state;
   public final int radius;
   public final int ySize;
   public final List<BlockState> targets;

   public SphereReplaceConfig(BlockState state, int radiusIn, int ySizeIn, List<BlockState> targetsIn) {
      this.state = state;
      this.radius = radiusIn;
      this.ySize = ySizeIn;
      this.targets = targetsIn;
   }
}