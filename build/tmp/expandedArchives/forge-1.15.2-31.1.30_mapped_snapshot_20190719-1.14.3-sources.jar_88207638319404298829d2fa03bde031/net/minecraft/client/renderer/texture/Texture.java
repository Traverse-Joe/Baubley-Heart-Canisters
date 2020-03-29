package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.concurrent.Executor;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Texture {
   protected int glTextureId = -1;
   protected boolean blur;
   protected boolean mipmap;

   public void setBlurMipmapDirect(boolean blurIn, boolean mipmapIn) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.blur = blurIn;
      this.mipmap = mipmapIn;
      int i;
      int j;
      if (blurIn) {
         i = mipmapIn ? 9987 : 9729;
         j = 9729;
      } else {
         i = mipmapIn ? 9986 : 9728;
         j = 9728;
      }

      GlStateManager.func_227677_b_(3553, 10241, i);
      GlStateManager.func_227677_b_(3553, 10240, j);
   }

   // FORGE: This seems to have been stripped out, but we need it
   private boolean lastBlur;
   private boolean lastMipmap;

   public void setBlurMipmap(boolean blur, boolean mipmap) {
      this.lastBlur = this.blur;
      this.lastMipmap = this.mipmap;
      setBlurMipmapDirect(blur, mipmap);
   }

   public void restoreLastBlurMipmap() {
      setBlurMipmapDirect(this.lastBlur, this.lastMipmap);
   }

   public int getGlTextureId() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (this.glTextureId == -1) {
         this.glTextureId = TextureUtil.func_225678_a_();
      }

      return this.glTextureId;
   }

   public void deleteGlTexture() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            if (this.glTextureId != -1) {
               TextureUtil.func_225679_a_(this.glTextureId);
               this.glTextureId = -1;
            }

         });
      } else if (this.glTextureId != -1) {
         TextureUtil.func_225679_a_(this.glTextureId);
         this.glTextureId = -1;
      }

   }

   public abstract void loadTexture(IResourceManager manager) throws IOException;

   public void func_229148_d_() {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> {
            GlStateManager.func_227760_t_(this.getGlTextureId());
         });
      } else {
         GlStateManager.func_227760_t_(this.getGlTextureId());
      }

   }

   public void func_215244_a(TextureManager p_215244_1_, IResourceManager p_215244_2_, ResourceLocation p_215244_3_, Executor p_215244_4_) {
      p_215244_1_.func_229263_a_(p_215244_3_, this);
   }
}