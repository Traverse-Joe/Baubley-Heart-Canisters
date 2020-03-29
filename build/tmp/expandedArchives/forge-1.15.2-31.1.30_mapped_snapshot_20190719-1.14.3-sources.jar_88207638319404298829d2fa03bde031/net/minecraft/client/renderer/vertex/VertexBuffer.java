package net.minecraft.client.renderer.vertex;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VertexBuffer implements AutoCloseable {
   private int glBufferId;
   private final VertexFormat vertexFormat;
   private int count;

   public VertexBuffer(VertexFormat vertexFormatIn) {
      this.vertexFormat = vertexFormatIn;
      RenderSystem.glGenBuffers((p_227876_1_) -> {
         this.glBufferId = p_227876_1_;
      });
   }

   public void bindBuffer() {
      RenderSystem.glBindBuffer(34962, () -> {
         return this.glBufferId;
      });
   }

   public void func_227875_a_(BufferBuilder p_227875_1_) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            this.func_227880_c_(p_227875_1_);
         });
      } else {
         this.func_227880_c_(p_227875_1_);
      }

   }

   public CompletableFuture<Void> func_227878_b_(BufferBuilder p_227878_1_) {
      if (!RenderSystem.isOnRenderThread()) {
         return CompletableFuture.runAsync(() -> {
            this.func_227880_c_(p_227878_1_);
         }, (p_227877_0_) -> {
            RenderSystem.recordRenderCall(p_227877_0_::run);
         });
      } else {
         this.func_227880_c_(p_227878_1_);
         return CompletableFuture.completedFuture((Void)null);
      }
   }

   private void func_227880_c_(BufferBuilder p_227880_1_) {
      Pair<BufferBuilder.DrawState, ByteBuffer> pair = p_227880_1_.func_227832_f_();
      if (this.glBufferId != -1) {
         ByteBuffer bytebuffer = pair.getSecond();
         this.count = bytebuffer.remaining() / this.vertexFormat.getSize();
         this.bindBuffer();
         RenderSystem.glBufferData(34962, bytebuffer, 35044);
         unbindBuffer();
      }
   }

   public void func_227874_a_(Matrix4f p_227874_1_, int p_227874_2_) {
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      RenderSystem.multMatrix(p_227874_1_);
      RenderSystem.drawArrays(p_227874_2_, 0, this.count);
      RenderSystem.popMatrix();
   }

   public static void unbindBuffer() {
      RenderSystem.glBindBuffer(34962, () -> {
         return 0;
      });
   }

   public void close() {
      if (this.glBufferId >= 0) {
         RenderSystem.glDeleteBuffers(this.glBufferId);
         this.glBufferId = -1;
      }

   }
}