package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class TextureUtil {
   private static final Logger field_225676_a_ = LogManager.getLogger();

   public static int func_225678_a_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GlStateManager.func_227622_J_();
   }

   public static void func_225679_a_(int p_225679_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager.func_227758_s_(p_225679_0_);
   }

   public static void func_225680_a_(int p_225680_0_, int p_225680_1_, int p_225680_2_) {
      func_225683_a_(NativeImage.PixelFormatGLCode.RGBA, p_225680_0_, 0, p_225680_1_, p_225680_2_);
   }

   public static void func_225682_a_(NativeImage.PixelFormatGLCode p_225682_0_, int p_225682_1_, int p_225682_2_, int p_225682_3_) {
      func_225683_a_(p_225682_0_, p_225682_1_, 0, p_225682_2_, p_225682_3_);
   }

   public static void func_225681_a_(int p_225681_0_, int p_225681_1_, int p_225681_2_, int p_225681_3_) {
      func_225683_a_(NativeImage.PixelFormatGLCode.RGBA, p_225681_0_, p_225681_1_, p_225681_2_, p_225681_3_);
   }

   public static void func_225683_a_(NativeImage.PixelFormatGLCode p_225683_0_, int p_225683_1_, int p_225683_2_, int p_225683_3_, int p_225683_4_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      func_225686_b_(p_225683_1_);
      if (p_225683_2_ >= 0) {
         GlStateManager.func_227677_b_(3553, 33085, p_225683_2_);
         GlStateManager.func_227677_b_(3553, 33082, 0);
         GlStateManager.func_227677_b_(3553, 33083, p_225683_2_);
         GlStateManager.func_227642_a_(3553, 34049, 0.0F);
      }

      for(int i = 0; i <= p_225683_2_; ++i) {
         GlStateManager.func_227647_a_(3553, i, p_225683_0_.getGlFormat(), p_225683_3_ >> i, p_225683_4_ >> i, 0, 6408, 5121, (IntBuffer)null);
      }

   }

   private static void func_225686_b_(int p_225686_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager.func_227760_t_(p_225686_0_);
   }

   public static ByteBuffer func_225684_a_(InputStream p_225684_0_) throws IOException {
      ByteBuffer bytebuffer;
      if (p_225684_0_ instanceof FileInputStream) {
         FileInputStream fileinputstream = (FileInputStream)p_225684_0_;
         FileChannel filechannel = fileinputstream.getChannel();
         bytebuffer = MemoryUtil.memAlloc((int)filechannel.size() + 1);

         while(filechannel.read(bytebuffer) != -1) {
            ;
         }
      } else {
         bytebuffer = MemoryUtil.memAlloc(8192);
         ReadableByteChannel readablebytechannel = Channels.newChannel(p_225684_0_);

         while(readablebytechannel.read(bytebuffer) != -1) {
            if (bytebuffer.remaining() == 0) {
               bytebuffer = MemoryUtil.memRealloc(bytebuffer, bytebuffer.capacity() * 2);
            }
         }
      }

      return bytebuffer;
   }

   public static String func_225687_b_(InputStream p_225687_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      ByteBuffer bytebuffer = null;

      try {
         bytebuffer = func_225684_a_(p_225687_0_);
         int i = bytebuffer.position();
         bytebuffer.rewind();
         String s = MemoryUtil.memASCII(bytebuffer, i);
         return s;
      } catch (IOException var7) {
         ;
      } finally {
         if (bytebuffer != null) {
            MemoryUtil.memFree(bytebuffer);
         }

      }

      return null;
   }

   public static void func_225685_a_(IntBuffer p_225685_0_, int p_225685_1_, int p_225685_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPixelStorei(3312, 0);
      GL11.glPixelStorei(3313, 0);
      GL11.glPixelStorei(3314, 0);
      GL11.glPixelStorei(3315, 0);
      GL11.glPixelStorei(3316, 0);
      GL11.glPixelStorei(3317, 4);
      GL11.glTexImage2D(3553, 0, 6408, p_225685_1_, p_225685_2_, 0, 32993, 33639, p_225685_0_);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10241, 9729);
   }
}