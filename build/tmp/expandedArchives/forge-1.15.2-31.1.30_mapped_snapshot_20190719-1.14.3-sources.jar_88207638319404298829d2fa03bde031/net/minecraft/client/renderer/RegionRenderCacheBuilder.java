package net.minecraft.client.renderer;

import java.util.Map;
import java.util.stream.Collectors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RegionRenderCacheBuilder {
   private final Map<RenderType, BufferBuilder> builders = RenderType.func_228661_n_().stream().collect(Collectors.toMap((p_228369_0_) -> {
      return p_228369_0_;
   }, (p_228368_0_) -> {
      return new BufferBuilder(p_228368_0_.func_228662_o_());
   }));

   public BufferBuilder func_228366_a_(RenderType p_228366_1_) {
      return this.builders.get(p_228366_1_);
   }

   public void func_228365_a_() {
      this.builders.values().forEach(BufferBuilder::reset);
   }

   public void func_228367_b_() {
      this.builders.values().forEach(BufferBuilder::func_227833_h_);
   }
}