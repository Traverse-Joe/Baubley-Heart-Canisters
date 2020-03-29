package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChunkBorderDebugRenderer implements DebugRenderer.IDebugRenderer {
   private final Minecraft minecraft;

   public ChunkBorderDebugRenderer(Minecraft minecraftIn) {
      this.minecraft = minecraftIn;
   }

   public void func_225619_a_(MatrixStack p_225619_1_, IRenderTypeBuffer p_225619_2_, double p_225619_3_, double p_225619_5_, double p_225619_7_) {
      RenderSystem.enableDepthTest();
      RenderSystem.shadeModel(7425);
      RenderSystem.enableAlphaTest();
      RenderSystem.defaultAlphaFunc();
      Entity entity = this.minecraft.gameRenderer.getActiveRenderInfo().getRenderViewEntity();
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      double d0 = 0.0D - p_225619_5_;
      double d1 = 256.0D - p_225619_5_;
      RenderSystem.disableTexture();
      RenderSystem.disableBlend();
      double d2 = (double)(entity.chunkCoordX << 4) - p_225619_3_;
      double d3 = (double)(entity.chunkCoordZ << 4) - p_225619_7_;
      RenderSystem.lineWidth(1.0F);
      bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);

      for(int i = -16; i <= 32; i += 16) {
         for(int j = -16; j <= 32; j += 16) {
            bufferbuilder.func_225582_a_(d2 + (double)i, d0, d3 + (double)j).func_227885_a_(1.0F, 0.0F, 0.0F, 0.0F).endVertex();
            bufferbuilder.func_225582_a_(d2 + (double)i, d0, d3 + (double)j).func_227885_a_(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
            bufferbuilder.func_225582_a_(d2 + (double)i, d1, d3 + (double)j).func_227885_a_(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
            bufferbuilder.func_225582_a_(d2 + (double)i, d1, d3 + (double)j).func_227885_a_(1.0F, 0.0F, 0.0F, 0.0F).endVertex();
         }
      }

      for(int k = 2; k < 16; k += 2) {
         bufferbuilder.func_225582_a_(d2 + (double)k, d0, d3).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + (double)k, d0, d3).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + (double)k, d1, d3).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + (double)k, d1, d3).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + (double)k, d0, d3 + 16.0D).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + (double)k, d0, d3 + 16.0D).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + (double)k, d1, d3 + 16.0D).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + (double)k, d1, d3 + 16.0D).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
      }

      for(int l = 2; l < 16; l += 2) {
         bufferbuilder.func_225582_a_(d2, d0, d3 + (double)l).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d0, d3 + (double)l).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d1, d3 + (double)l).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d1, d3 + (double)l).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + 16.0D, d0, d3 + (double)l).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + 16.0D, d0, d3 + (double)l).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + 16.0D, d1, d3 + (double)l).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + 16.0D, d1, d3 + (double)l).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
      }

      for(int i1 = 0; i1 <= 256; i1 += 2) {
         double d4 = (double)i1 - p_225619_5_;
         bufferbuilder.func_225582_a_(d2, d4, d3).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d4, d3).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d4, d3 + 16.0D).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + 16.0D, d4, d3 + 16.0D).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + 16.0D, d4, d3).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d4, d3).func_227885_a_(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d4, d3).func_227885_a_(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
      }

      tessellator.draw();
      RenderSystem.lineWidth(2.0F);
      bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);

      for(int j1 = 0; j1 <= 16; j1 += 16) {
         for(int l1 = 0; l1 <= 16; l1 += 16) {
            bufferbuilder.func_225582_a_(d2 + (double)j1, d0, d3 + (double)l1).func_227885_a_(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
            bufferbuilder.func_225582_a_(d2 + (double)j1, d0, d3 + (double)l1).func_227885_a_(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
            bufferbuilder.func_225582_a_(d2 + (double)j1, d1, d3 + (double)l1).func_227885_a_(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
            bufferbuilder.func_225582_a_(d2 + (double)j1, d1, d3 + (double)l1).func_227885_a_(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
         }
      }

      for(int k1 = 0; k1 <= 256; k1 += 16) {
         double d5 = (double)k1 - p_225619_5_;
         bufferbuilder.func_225582_a_(d2, d5, d3).func_227885_a_(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d5, d3).func_227885_a_(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d5, d3 + 16.0D).func_227885_a_(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + 16.0D, d5, d3 + 16.0D).func_227885_a_(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2 + 16.0D, d5, d3).func_227885_a_(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d5, d3).func_227885_a_(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         bufferbuilder.func_225582_a_(d2, d5, d3).func_227885_a_(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
      }

      tessellator.draw();
      RenderSystem.lineWidth(1.0F);
      RenderSystem.enableBlend();
      RenderSystem.enableTexture();
      RenderSystem.shadeModel(7424);
   }
}