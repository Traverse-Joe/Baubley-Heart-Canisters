package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.SpriteAwareVertexBuilder;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextureAtlasSprite implements AutoCloseable, net.minecraftforge.client.extensions.IForgeTextureAtlasSprite {
   private final AtlasTexture field_229225_b_;
   private final TextureAtlasSprite.Info field_229226_c_;
   private final AnimationMetadataSection animationMetadata;
   protected final NativeImage[] frames;
   private final int[] framesX;
   private final int[] framesY;
   @Nullable
   private final TextureAtlasSprite.InterpolationData field_229227_g_;
   private final int x;
   private final int y;
   private final float minU;
   private final float maxU;
   private final float minV;
   private final float maxV;
   private int frameCounter;
   private int tickCounter;

   protected TextureAtlasSprite(AtlasTexture p_i226049_1_, TextureAtlasSprite.Info p_i226049_2_, int p_i226049_3_, int p_i226049_4_, int p_i226049_5_, int p_i226049_6_, int p_i226049_7_, NativeImage p_i226049_8_) {
      this.field_229225_b_ = p_i226049_1_;
      AnimationMetadataSection animationmetadatasection = p_i226049_2_.field_229247_d_;
      int i = p_i226049_2_.field_229245_b_;
      int j = p_i226049_2_.field_229246_c_;
      this.x = p_i226049_6_;
      this.y = p_i226049_7_;
      this.minU = (float)p_i226049_6_ / (float)p_i226049_4_;
      this.maxU = (float)(p_i226049_6_ + i) / (float)p_i226049_4_;
      this.minV = (float)p_i226049_7_ / (float)p_i226049_5_;
      this.maxV = (float)(p_i226049_7_ + j) / (float)p_i226049_5_;
      int k = p_i226049_8_.getWidth() / animationmetadatasection.func_229302_b_(i);
      int l = p_i226049_8_.getHeight() / animationmetadatasection.func_229301_a_(j);
      if (animationmetadatasection.getFrameCount() > 0) {
         int i1 = animationmetadatasection.getFrameIndexSet().stream().max(Integer::compareTo).get() + 1;
         this.framesX = new int[i1];
         this.framesY = new int[i1];
         Arrays.fill(this.framesX, -1);
         Arrays.fill(this.framesY, -1);

         for(int j1 : animationmetadatasection.getFrameIndexSet()) {
            if (j1 >= k * l) {
               throw new RuntimeException("invalid frameindex " + j1);
            }

            int k1 = j1 / k;
            int l1 = j1 % k;
            this.framesX[j1] = l1;
            this.framesY[j1] = k1;
         }
      } else {
         List<AnimationFrame> list = Lists.newArrayList();
         int i2 = k * l;
         this.framesX = new int[i2];
         this.framesY = new int[i2];

         for(int j2 = 0; j2 < l; ++j2) {
            for(int k2 = 0; k2 < k; ++k2) {
               int l2 = j2 * k + k2;
               this.framesX[l2] = k2;
               this.framesY[l2] = j2;
               list.add(new AnimationFrame(l2, -1));
            }
         }

         animationmetadatasection = new AnimationMetadataSection(list, i, j, animationmetadatasection.getFrameTime(), animationmetadatasection.isInterpolate());
      }

      this.field_229226_c_ = new TextureAtlasSprite.Info(p_i226049_2_.field_229244_a_, i, j, animationmetadatasection);
      this.animationMetadata = animationmetadatasection;

      try {
         try {
            this.frames = MipmapGenerator.func_229173_a_(p_i226049_8_, p_i226049_3_);
         } catch (Throwable throwable) {
            CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Generating mipmaps for frame");
            CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Frame being iterated");
            crashreportcategory1.addDetail("First frame", () -> {
               StringBuilder stringbuilder = new StringBuilder();
               if (stringbuilder.length() > 0) {
                  stringbuilder.append(", ");
               }

               stringbuilder.append(p_i226049_8_.getWidth()).append("x").append(p_i226049_8_.getHeight());
               return stringbuilder.toString();
            });
            throw new ReportedException(crashreport1);
         }
      } catch (Throwable throwable1) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Applying mipmap");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Sprite being mipmapped");
         crashreportcategory.addDetail("Sprite name", () -> {
            return this.getName().toString();
         });
         crashreportcategory.addDetail("Sprite size", () -> {
            return this.getWidth() + " x " + this.getHeight();
         });
         crashreportcategory.addDetail("Sprite frames", () -> {
            return this.getFrameCount() + " frames";
         });
         crashreportcategory.addDetail("Mipmap levels", p_i226049_3_);
         throw new ReportedException(crashreport);
      }

      if (animationmetadatasection.isInterpolate()) {
         this.field_229227_g_ = new TextureAtlasSprite.InterpolationData(p_i226049_2_, p_i226049_3_);
      } else {
         this.field_229227_g_ = null;
      }

   }

   private void uploadFrames(int index) {
      int i = this.framesX[index] * this.field_229226_c_.field_229245_b_;
      int j = this.framesY[index] * this.field_229226_c_.field_229246_c_;
      this.uploadFrames(i, j, this.frames);
   }

   private void uploadFrames(int xOffsetIn, int yOffsetIn, NativeImage[] framesIn) {
      for(int i = 0; i < this.frames.length; ++i) {
         if ((this.field_229226_c_.field_229245_b_ >> i <= 0) || (this.field_229226_c_.field_229246_c_ >> i <= 0)) break;
         framesIn[i].func_227788_a_(i, this.x >> i, this.y >> i, xOffsetIn >> i, yOffsetIn >> i, this.field_229226_c_.field_229245_b_ >> i, this.field_229226_c_.field_229246_c_ >> i, this.frames.length > 1, false);
      }

   }

   /**
    * Returns the width of the icon, in pixels.
    */
   public int getWidth() {
      return this.field_229226_c_.field_229245_b_;
   }

   /**
    * Returns the height of the icon, in pixels.
    */
   public int getHeight() {
      return this.field_229226_c_.field_229246_c_;
   }

   /**
    * Returns the minimum U coordinate to use when rendering with this icon.
    */
   public float getMinU() {
      return this.minU;
   }

   /**
    * Returns the maximum U coordinate to use when rendering with this icon.
    */
   public float getMaxU() {
      return this.maxU;
   }

   /**
    * Gets a U coordinate on the icon. 0 returns uMin and 16 returns uMax. Other arguments return in-between values.
    */
   public float getInterpolatedU(double u) {
      float f = this.maxU - this.minU;
      return this.minU + f * (float)u / 16.0F;
   }

   /**
    * Returns the minimum V coordinate to use when rendering with this icon.
    */
   public float getMinV() {
      return this.minV;
   }

   /**
    * Returns the maximum V coordinate to use when rendering with this icon.
    */
   public float getMaxV() {
      return this.maxV;
   }

   /**
    * Gets a V coordinate on the icon. 0 returns vMin and 16 returns vMax. Other arguments return in-between values.
    */
   public float getInterpolatedV(double v) {
      float f = this.maxV - this.minV;
      return this.minV + f * (float)v / 16.0F;
   }

   public ResourceLocation getName() {
      return this.field_229226_c_.field_229244_a_;
   }

   public AtlasTexture func_229241_m_() {
      return this.field_229225_b_;
   }

   public int getFrameCount() {
      return this.framesX.length;
   }

   public void close() {
      for(NativeImage nativeimage : this.frames) {
         if (nativeimage != null) {
            nativeimage.close();
         }
      }

      if (this.field_229227_g_ != null) {
         this.field_229227_g_.close();
      }

   }

   public String toString() {
      int i = this.framesX.length;
      return "TextureAtlasSprite{name='" + this.field_229226_c_.field_229244_a_ + '\'' + ", frameCount=" + i + ", x=" + this.x + ", y=" + this.y + ", height=" + this.field_229226_c_.field_229246_c_ + ", width=" + this.field_229226_c_.field_229245_b_ + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
   }

   public boolean isPixelTransparent(int frameIndex, int pixelX, int pixelY) {
      return (this.frames[0].getPixelRGBA(pixelX + this.framesX[frameIndex] * this.field_229226_c_.field_229245_b_, pixelY + this.framesY[frameIndex] * this.field_229226_c_.field_229246_c_) >> 24 & 255) == 0;
   }

   public void uploadMipmaps() {
      this.uploadFrames(0);
   }

   private float func_229228_a_() {
      float f = (float)this.field_229226_c_.field_229245_b_ / (this.maxU - this.minU);
      float f1 = (float)this.field_229226_c_.field_229246_c_ / (this.maxV - this.minV);
      return Math.max(f1, f);
   }

   public float func_229242_p_() {
      return 4.0F / this.func_229228_a_();
   }

   public void updateAnimation() {
      ++this.tickCounter;
      if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
         int i = this.animationMetadata.getFrameIndex(this.frameCounter);
         int j = this.animationMetadata.getFrameCount() == 0 ? this.getFrameCount() : this.animationMetadata.getFrameCount();
         this.frameCounter = (this.frameCounter + 1) % j;
         this.tickCounter = 0;
         int k = this.animationMetadata.getFrameIndex(this.frameCounter);
         if (i != k && k >= 0 && k < this.getFrameCount()) {
            this.uploadFrames(k);
         }
      } else if (this.field_229227_g_ != null) {
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> {
               this.field_229227_g_.func_229257_a_();
            });
         } else {
            this.field_229227_g_.func_229257_a_();
         }
      }

   }

   public boolean hasAnimationMetadata() {
      return this.animationMetadata.getFrameCount() > 1;
   }

   public IVertexBuilder func_229230_a_(IVertexBuilder p_229230_1_) {
      return new SpriteAwareVertexBuilder(p_229230_1_, this);
   }

   @OnlyIn(Dist.CLIENT)
   public static final class Info {
      private final ResourceLocation field_229244_a_;
      private final int field_229245_b_;
      private final int field_229246_c_;
      private final AnimationMetadataSection field_229247_d_;

      public Info(ResourceLocation p_i226050_1_, int p_i226050_2_, int p_i226050_3_, AnimationMetadataSection p_i226050_4_) {
         this.field_229244_a_ = p_i226050_1_;
         this.field_229245_b_ = p_i226050_2_;
         this.field_229246_c_ = p_i226050_3_;
         this.field_229247_d_ = p_i226050_4_;
      }

      public ResourceLocation func_229248_a_() {
         return this.field_229244_a_;
      }

      public int func_229250_b_() {
         return this.field_229245_b_;
      }

      public int func_229252_c_() {
         return this.field_229246_c_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   final class InterpolationData implements AutoCloseable {
      private final NativeImage[] field_229256_b_;

      private InterpolationData(TextureAtlasSprite.Info p_i226051_2_, int p_i226051_3_) {
         this.field_229256_b_ = new NativeImage[p_i226051_3_ + 1];

         for(int i = 0; i < this.field_229256_b_.length; ++i) {
            int j = p_i226051_2_.field_229245_b_ >> i;
            int k = p_i226051_2_.field_229246_c_ >> i;
            if (this.field_229256_b_[i] == null) {
               this.field_229256_b_[i] = new NativeImage(j, k, false);
            }
         }

      }

      private void func_229257_a_() {
         double d0 = 1.0D - (double)TextureAtlasSprite.this.tickCounter / (double)TextureAtlasSprite.this.animationMetadata.getFrameTimeSingle(TextureAtlasSprite.this.frameCounter);
         int i = TextureAtlasSprite.this.animationMetadata.getFrameIndex(TextureAtlasSprite.this.frameCounter);
         int j = TextureAtlasSprite.this.animationMetadata.getFrameCount() == 0 ? TextureAtlasSprite.this.getFrameCount() : TextureAtlasSprite.this.animationMetadata.getFrameCount();
         int k = TextureAtlasSprite.this.animationMetadata.getFrameIndex((TextureAtlasSprite.this.frameCounter + 1) % j);
         if (i != k && k >= 0 && k < TextureAtlasSprite.this.getFrameCount()) {
            for(int l = 0; l < this.field_229256_b_.length; ++l) {
               int i1 = TextureAtlasSprite.this.field_229226_c_.field_229245_b_ >> l;
               int j1 = TextureAtlasSprite.this.field_229226_c_.field_229246_c_ >> l;

               for(int k1 = 0; k1 < j1; ++k1) {
                  for(int l1 = 0; l1 < i1; ++l1) {
                     int i2 = this.func_229259_a_(i, l, l1, k1);
                     int j2 = this.func_229259_a_(k, l, l1, k1);
                     int k2 = this.func_229258_a_(d0, i2 >> 16 & 255, j2 >> 16 & 255);
                     int l2 = this.func_229258_a_(d0, i2 >> 8 & 255, j2 >> 8 & 255);
                     int i3 = this.func_229258_a_(d0, i2 & 255, j2 & 255);
                     this.field_229256_b_[l].setPixelRGBA(l1, k1, i2 & -16777216 | k2 << 16 | l2 << 8 | i3);
                  }
               }
            }

            TextureAtlasSprite.this.uploadFrames(0, 0, this.field_229256_b_);
         }

      }

      private int func_229259_a_(int p_229259_1_, int p_229259_2_, int p_229259_3_, int p_229259_4_) {
         return TextureAtlasSprite.this.frames[p_229259_2_].getPixelRGBA(p_229259_3_ + (TextureAtlasSprite.this.framesX[p_229259_1_] * TextureAtlasSprite.this.field_229226_c_.field_229245_b_ >> p_229259_2_), p_229259_4_ + (TextureAtlasSprite.this.framesY[p_229259_1_] * TextureAtlasSprite.this.field_229226_c_.field_229246_c_ >> p_229259_2_));
      }

      private int func_229258_a_(double p_229258_1_, int p_229258_3_, int p_229258_4_) {
         return (int)(p_229258_1_ * (double)p_229258_3_ + (1.0D - p_229258_1_) * (double)p_229258_4_);
      }

      public void close() {
         for(NativeImage nativeimage : this.field_229256_b_) {
            if (nativeimage != null) {
               nativeimage.close();
            }
         }

      }
   }

   // Forge Start
   public int getPixelRGBA(int frameIndex, int x, int y) {
       return this.frames[0].getPixelRGBA(x + this.framesX[frameIndex] * getWidth(), y + this.framesY[frameIndex] * getHeight());
   }
}