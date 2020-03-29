package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class SimpleTexture extends Texture {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final ResourceLocation textureLocation;

   public SimpleTexture(ResourceLocation textureResourceLocation) {
      this.textureLocation = textureResourceLocation;
   }

   public void loadTexture(IResourceManager manager) throws IOException {
      SimpleTexture.TextureData simpletexture$texturedata = this.func_215246_b(manager);
      simpletexture$texturedata.func_217801_c();
      TextureMetadataSection texturemetadatasection = simpletexture$texturedata.func_217798_a();
      boolean flag;
      boolean flag1;
      if (texturemetadatasection != null) {
         flag = texturemetadatasection.getTextureBlur();
         flag1 = texturemetadatasection.getTextureClamp();
      } else {
         flag = false;
         flag1 = false;
      }

      NativeImage nativeimage = simpletexture$texturedata.func_217800_b();
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> {
            this.func_229207_a_(nativeimage, flag, flag1);
         });
      } else {
         this.func_229207_a_(nativeimage, flag, flag1);
      }

   }

   private void func_229207_a_(NativeImage p_229207_1_, boolean p_229207_2_, boolean p_229207_3_) {
      TextureUtil.func_225681_a_(this.getGlTextureId(), 0, p_229207_1_.getWidth(), p_229207_1_.getHeight());
      p_229207_1_.func_227789_a_(0, 0, 0, 0, 0, p_229207_1_.getWidth(), p_229207_1_.getHeight(), p_229207_2_, p_229207_3_, false, true);
   }

   protected SimpleTexture.TextureData func_215246_b(IResourceManager resourceManager) {
      return SimpleTexture.TextureData.func_217799_a(resourceManager, this.textureLocation);
   }

   @OnlyIn(Dist.CLIENT)
   public static class TextureData implements Closeable {
      @Nullable
      private final TextureMetadataSection field_217802_a;
      @Nullable
      private final NativeImage field_217803_b;
      @Nullable
      private final IOException field_217804_c;

      public TextureData(IOException p_i50473_1_) {
         this.field_217804_c = p_i50473_1_;
         this.field_217802_a = null;
         this.field_217803_b = null;
      }

      public TextureData(@Nullable TextureMetadataSection p_i50474_1_, NativeImage p_i50474_2_) {
         this.field_217804_c = null;
         this.field_217802_a = p_i50474_1_;
         this.field_217803_b = p_i50474_2_;
      }

      public static SimpleTexture.TextureData func_217799_a(IResourceManager p_217799_0_, ResourceLocation p_217799_1_) {
         try (IResource iresource = p_217799_0_.getResource(p_217799_1_)) {
            NativeImage nativeimage = NativeImage.read(iresource.getInputStream());
            TextureMetadataSection texturemetadatasection = null;

            try {
               texturemetadatasection = iresource.getMetadata(TextureMetadataSection.SERIALIZER);
            } catch (RuntimeException runtimeexception) {
               SimpleTexture.LOGGER.warn("Failed reading metadata of: {}", p_217799_1_, runtimeexception);
            }

            SimpleTexture.TextureData lvt_6_1_ = new SimpleTexture.TextureData(texturemetadatasection, nativeimage);
            return lvt_6_1_;
         } catch (IOException ioexception) {
            return new SimpleTexture.TextureData(ioexception);
         }
      }

      @Nullable
      public TextureMetadataSection func_217798_a() {
         return this.field_217802_a;
      }

      public NativeImage func_217800_b() throws IOException {
         if (this.field_217804_c != null) {
            throw this.field_217804_c;
         } else {
            return this.field_217803_b;
         }
      }

      public void close() {
         if (this.field_217803_b != null) {
            this.field_217803_b.close();
         }

      }

      public void func_217801_c() throws IOException {
         if (this.field_217804_c != null) {
            throw this.field_217804_c;
         }
      }
   }
}