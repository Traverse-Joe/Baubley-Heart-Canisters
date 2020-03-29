package net.minecraft.client.renderer;

import java.util.OptionalInt;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenSize {
   public final int width;
   public final int height;
   public final OptionalInt fullscreenWidth;
   public final OptionalInt fullscreenHeight;
   public final boolean fullscreen;

   public ScreenSize(int p_i51796_1_, int p_i51796_2_, OptionalInt p_i51796_3_, OptionalInt p_i51796_4_, boolean p_i51796_5_) {
      this.width = p_i51796_1_;
      this.height = p_i51796_2_;
      this.fullscreenWidth = p_i51796_3_;
      this.fullscreenHeight = p_i51796_4_;
      this.fullscreen = p_i51796_5_;
   }
}