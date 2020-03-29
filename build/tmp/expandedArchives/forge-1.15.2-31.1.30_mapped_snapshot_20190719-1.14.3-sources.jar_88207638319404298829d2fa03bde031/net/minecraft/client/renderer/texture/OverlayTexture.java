package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OverlayTexture implements AutoCloseable {
   public static final int field_229196_a_ = func_229201_a_(0, 10);
   private final DynamicTexture field_229197_b_ = new DynamicTexture(16, 16, false);

   public OverlayTexture() {
      NativeImage nativeimage = this.field_229197_b_.getTextureData();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            if (i < 8) {
               nativeimage.setPixelRGBA(j, i, -1308622593);
            } else {
               int k = (int)((1.0F - (float)j / 15.0F * 0.75F) * 255.0F);
               nativeimage.setPixelRGBA(j, i, k << 24 | 16777215);
            }
         }
      }

      RenderSystem.activeTexture(33985);
      this.field_229197_b_.func_229148_d_();
      RenderSystem.matrixMode(5890);
      RenderSystem.loadIdentity();
      float f = 0.06666667F;
      RenderSystem.scalef(0.06666667F, 0.06666667F, 0.06666667F);
      RenderSystem.matrixMode(5888);
      this.field_229197_b_.func_229148_d_();
      nativeimage.func_227789_a_(0, 0, 0, 0, 0, nativeimage.getWidth(), nativeimage.getHeight(), false, true, false, false);
      RenderSystem.activeTexture(33984);
   }

   public void close() {
      this.field_229197_b_.close();
   }

   public void func_229198_a_() {
      RenderSystem.setupOverlayColor(this.field_229197_b_::getGlTextureId, 16);
   }

   public static int func_229199_a_(float p_229199_0_) {
      return (int)(p_229199_0_ * 15.0F);
   }

   public static int func_229202_a_(boolean p_229202_0_) {
      return p_229202_0_ ? 3 : 10;
   }

   public static int func_229201_a_(int p_229201_0_, int p_229201_1_) {
      return p_229201_0_ | p_229201_1_ << 16;
   }

   public static int func_229200_a_(float p_229200_0_, boolean p_229200_1_) {
      return func_229201_a_(func_229199_a_(p_229200_0_), func_229202_a_(p_229200_1_));
   }

   public void func_229203_b_() {
      RenderSystem.teardownOverlayColor();
   }
}