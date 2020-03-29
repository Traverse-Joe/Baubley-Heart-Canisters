package net.minecraft.client.renderer.texture;

import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MipmapGenerator {
   private static final float[] field_229169_a_ = Util.make(new float[256], (p_229174_0_) -> {
      for(int i = 0; i < p_229174_0_.length; ++i) {
         p_229174_0_[i] = (float)Math.pow((double)((float)i / 255.0F), 2.2D);
      }

   });

   public static NativeImage[] func_229173_a_(NativeImage p_229173_0_, int p_229173_1_) {
      NativeImage[] anativeimage = new NativeImage[p_229173_1_ + 1];
      anativeimage[0] = p_229173_0_;
      if (p_229173_1_ > 0) {
         boolean flag = false;

         label51:
         for(int i = 0; i < p_229173_0_.getWidth(); ++i) {
            for(int j = 0; j < p_229173_0_.getHeight(); ++j) {
               if (p_229173_0_.getPixelRGBA(i, j) >> 24 == 0) {
                  flag = true;
                  break label51;
               }
            }
         }

         for(int k1 = 1; k1 <= p_229173_1_; ++k1) {
            NativeImage nativeimage1 = anativeimage[k1 - 1];
            NativeImage nativeimage = new NativeImage(nativeimage1.getWidth() >> 1, nativeimage1.getHeight() >> 1, false);
            int k = nativeimage.getWidth();
            int l = nativeimage.getHeight();

            for(int i1 = 0; i1 < k; ++i1) {
               for(int j1 = 0; j1 < l; ++j1) {
                  nativeimage.setPixelRGBA(i1, j1, func_229172_a_(nativeimage1.getPixelRGBA(i1 * 2 + 0, j1 * 2 + 0), nativeimage1.getPixelRGBA(i1 * 2 + 1, j1 * 2 + 0), nativeimage1.getPixelRGBA(i1 * 2 + 0, j1 * 2 + 1), nativeimage1.getPixelRGBA(i1 * 2 + 1, j1 * 2 + 1), flag));
               }
            }

            anativeimage[k1] = nativeimage;
         }
      }

      return anativeimage;
   }

   private static int func_229172_a_(int p_229172_0_, int p_229172_1_, int p_229172_2_, int p_229172_3_, boolean p_229172_4_) {
      if (p_229172_4_) {
         float f = 0.0F;
         float f1 = 0.0F;
         float f2 = 0.0F;
         float f3 = 0.0F;
         if (p_229172_0_ >> 24 != 0) {
            f += func_229170_a_(p_229172_0_ >> 24);
            f1 += func_229170_a_(p_229172_0_ >> 16);
            f2 += func_229170_a_(p_229172_0_ >> 8);
            f3 += func_229170_a_(p_229172_0_ >> 0);
         }

         if (p_229172_1_ >> 24 != 0) {
            f += func_229170_a_(p_229172_1_ >> 24);
            f1 += func_229170_a_(p_229172_1_ >> 16);
            f2 += func_229170_a_(p_229172_1_ >> 8);
            f3 += func_229170_a_(p_229172_1_ >> 0);
         }

         if (p_229172_2_ >> 24 != 0) {
            f += func_229170_a_(p_229172_2_ >> 24);
            f1 += func_229170_a_(p_229172_2_ >> 16);
            f2 += func_229170_a_(p_229172_2_ >> 8);
            f3 += func_229170_a_(p_229172_2_ >> 0);
         }

         if (p_229172_3_ >> 24 != 0) {
            f += func_229170_a_(p_229172_3_ >> 24);
            f1 += func_229170_a_(p_229172_3_ >> 16);
            f2 += func_229170_a_(p_229172_3_ >> 8);
            f3 += func_229170_a_(p_229172_3_ >> 0);
         }

         f = f / 4.0F;
         f1 = f1 / 4.0F;
         f2 = f2 / 4.0F;
         f3 = f3 / 4.0F;
         int i1 = (int)(Math.pow((double)f, 0.45454545454545453D) * 255.0D);
         int j1 = (int)(Math.pow((double)f1, 0.45454545454545453D) * 255.0D);
         int k1 = (int)(Math.pow((double)f2, 0.45454545454545453D) * 255.0D);
         int l1 = (int)(Math.pow((double)f3, 0.45454545454545453D) * 255.0D);
         if (i1 < 96) {
            i1 = 0;
         }

         return i1 << 24 | j1 << 16 | k1 << 8 | l1;
      } else {
         int i = func_229171_a_(p_229172_0_, p_229172_1_, p_229172_2_, p_229172_3_, 24);
         int j = func_229171_a_(p_229172_0_, p_229172_1_, p_229172_2_, p_229172_3_, 16);
         int k = func_229171_a_(p_229172_0_, p_229172_1_, p_229172_2_, p_229172_3_, 8);
         int l = func_229171_a_(p_229172_0_, p_229172_1_, p_229172_2_, p_229172_3_, 0);
         return i << 24 | j << 16 | k << 8 | l;
      }
   }

   private static int func_229171_a_(int p_229171_0_, int p_229171_1_, int p_229171_2_, int p_229171_3_, int p_229171_4_) {
      float f = func_229170_a_(p_229171_0_ >> p_229171_4_);
      float f1 = func_229170_a_(p_229171_1_ >> p_229171_4_);
      float f2 = func_229170_a_(p_229171_2_ >> p_229171_4_);
      float f3 = func_229170_a_(p_229171_3_ >> p_229171_4_);
      float f4 = (float)((double)((float)Math.pow((double)(f + f1 + f2 + f3) * 0.25D, 0.45454545454545453D)));
      return (int)((double)f4 * 255.0D);
   }

   private static float func_229170_a_(int p_229170_0_) {
      return field_229169_a_[p_229170_0_ & 255];
   }
}