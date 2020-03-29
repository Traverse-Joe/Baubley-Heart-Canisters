package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class DownloadingTexture extends SimpleTexture {
   private static final Logger LOGGER = LogManager.getLogger();
   @Nullable
   private final File cacheFile;
   private final String imageUrl;
   private final boolean field_229154_h_;
   @Nullable
   private final Runnable field_229155_i_;
   @Nullable
   private CompletableFuture<?> field_229156_j_;
   private boolean textureUploaded;

   public DownloadingTexture(@Nullable File p_i226043_1_, String p_i226043_2_, ResourceLocation p_i226043_3_, boolean p_i226043_4_, @Nullable Runnable p_i226043_5_) {
      super(p_i226043_3_);
      this.cacheFile = p_i226043_1_;
      this.imageUrl = p_i226043_2_;
      this.field_229154_h_ = p_i226043_4_;
      this.field_229155_i_ = p_i226043_5_;
   }

   private void setImage(NativeImage nativeImageIn) {
      if (this.field_229155_i_ != null) {
         this.field_229155_i_.run();
      }

      Minecraft.getInstance().execute(() -> {
         this.textureUploaded = true;
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> {
               this.func_229160_b_(nativeImageIn);
            });
         } else {
            this.func_229160_b_(nativeImageIn);
         }

      });
   }

   private void func_229160_b_(NativeImage p_229160_1_) {
      TextureUtil.func_225680_a_(this.getGlTextureId(), p_229160_1_.getWidth(), p_229160_1_.getHeight());
      p_229160_1_.uploadTextureSub(0, 0, 0, true);
   }

   public void loadTexture(IResourceManager manager) throws IOException {
      Minecraft.getInstance().execute(() -> {
         if (!this.textureUploaded) {
            try {
               super.loadTexture(manager);
            } catch (IOException ioexception) {
               LOGGER.warn("Failed to load texture: {}", this.textureLocation, ioexception);
            }

            this.textureUploaded = true;
         }

      });
      if (this.field_229156_j_ == null) {
         NativeImage nativeimage;
         if (this.cacheFile != null && this.cacheFile.isFile()) {
            LOGGER.debug("Loading http texture from local cache ({})", (Object)this.cacheFile);
            FileInputStream fileinputstream = new FileInputStream(this.cacheFile);
            nativeimage = this.func_229159_a_(fileinputstream);
         } else {
            nativeimage = null;
         }

         if (nativeimage != null) {
            this.setImage(nativeimage);
         } else {
            this.field_229156_j_ = CompletableFuture.runAsync(() -> {
               HttpURLConnection httpurlconnection = null;
               LOGGER.debug("Downloading http texture from {} to {}", this.imageUrl, this.cacheFile);

               try {
                  httpurlconnection = (HttpURLConnection)(new URL(this.imageUrl)).openConnection(Minecraft.getInstance().getProxy());
                  httpurlconnection.setDoInput(true);
                  httpurlconnection.setDoOutput(false);
                  httpurlconnection.connect();
                  if (httpurlconnection.getResponseCode() / 100 == 2) {
                     InputStream inputstream;
                     if (this.cacheFile != null) {
                        FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), this.cacheFile);
                        inputstream = new FileInputStream(this.cacheFile);
                     } else {
                        inputstream = httpurlconnection.getInputStream();
                     }

                     Minecraft.getInstance().execute(() -> {
                        NativeImage nativeimage1 = this.func_229159_a_(inputstream);
                        if (nativeimage1 != null) {
                           this.setImage(nativeimage1);
                        }

                     });
                     return;
                  }
               } catch (Exception exception) {
                  LOGGER.error("Couldn't download http texture", (Throwable)exception);
                  return;
               } finally {
                  if (httpurlconnection != null) {
                     httpurlconnection.disconnect();
                  }

               }

            }, Util.getServerExecutor());
         }
      }
   }

   @Nullable
   private NativeImage func_229159_a_(InputStream p_229159_1_) {
      NativeImage nativeimage = null;

      try {
         nativeimage = NativeImage.read(p_229159_1_);
         if (this.field_229154_h_) {
            nativeimage = func_229163_c_(nativeimage);
         }
      } catch (IOException ioexception) {
         LOGGER.warn("Error while loading the skin texture", (Throwable)ioexception);
      }

      return nativeimage;
   }

   private static NativeImage func_229163_c_(NativeImage p_229163_0_) {
      boolean flag = p_229163_0_.getHeight() == 32;
      if (flag) {
         NativeImage nativeimage = new NativeImage(64, 64, true);
         nativeimage.copyImageData(p_229163_0_);
         p_229163_0_.close();
         p_229163_0_ = nativeimage;
         nativeimage.fillAreaRGBA(0, 32, 64, 32, 0);
         nativeimage.copyAreaRGBA(4, 16, 16, 32, 4, 4, true, false);
         nativeimage.copyAreaRGBA(8, 16, 16, 32, 4, 4, true, false);
         nativeimage.copyAreaRGBA(0, 20, 24, 32, 4, 12, true, false);
         nativeimage.copyAreaRGBA(4, 20, 16, 32, 4, 12, true, false);
         nativeimage.copyAreaRGBA(8, 20, 8, 32, 4, 12, true, false);
         nativeimage.copyAreaRGBA(12, 20, 16, 32, 4, 12, true, false);
         nativeimage.copyAreaRGBA(44, 16, -8, 32, 4, 4, true, false);
         nativeimage.copyAreaRGBA(48, 16, -8, 32, 4, 4, true, false);
         nativeimage.copyAreaRGBA(40, 20, 0, 32, 4, 12, true, false);
         nativeimage.copyAreaRGBA(44, 20, -8, 32, 4, 12, true, false);
         nativeimage.copyAreaRGBA(48, 20, -16, 32, 4, 12, true, false);
         nativeimage.copyAreaRGBA(52, 20, -8, 32, 4, 12, true, false);
      }

      func_229161_b_(p_229163_0_, 0, 0, 32, 16);
      if (flag) {
         func_229158_a_(p_229163_0_, 32, 0, 64, 32);
      }

      func_229161_b_(p_229163_0_, 0, 16, 64, 32);
      func_229161_b_(p_229163_0_, 16, 48, 48, 64);
      return p_229163_0_;
   }

   private static void func_229158_a_(NativeImage p_229158_0_, int p_229158_1_, int p_229158_2_, int p_229158_3_, int p_229158_4_) {
      for(int i = p_229158_1_; i < p_229158_3_; ++i) {
         for(int j = p_229158_2_; j < p_229158_4_; ++j) {
            int k = p_229158_0_.getPixelRGBA(i, j);
            if ((k >> 24 & 255) < 128) {
               return;
            }
         }
      }

      for(int l = p_229158_1_; l < p_229158_3_; ++l) {
         for(int i1 = p_229158_2_; i1 < p_229158_4_; ++i1) {
            p_229158_0_.setPixelRGBA(l, i1, p_229158_0_.getPixelRGBA(l, i1) & 16777215);
         }
      }

   }

   private static void func_229161_b_(NativeImage p_229161_0_, int p_229161_1_, int p_229161_2_, int p_229161_3_, int p_229161_4_) {
      for(int i = p_229161_1_; i < p_229161_3_; ++i) {
         for(int j = p_229161_2_; j < p_229161_4_; ++j) {
            p_229161_0_.setPixelRGBA(i, j, p_229161_0_.getPixelRGBA(i, j) | -16777216);
         }
      }

   }
}