package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryStack;

@OnlyIn(Dist.CLIENT)
public interface IVertexBuilder extends net.minecraftforge.client.extensions.IForgeVertexBuilder {
   Logger field_227884_f_ = LogManager.getLogger();

   IVertexBuilder func_225582_a_(double p_225582_1_, double p_225582_3_, double p_225582_5_);

   IVertexBuilder func_225586_a_(int p_225586_1_, int p_225586_2_, int p_225586_3_, int p_225586_4_);

   IVertexBuilder func_225583_a_(float p_225583_1_, float p_225583_2_);

   IVertexBuilder func_225585_a_(int p_225585_1_, int p_225585_2_);

   IVertexBuilder func_225587_b_(int p_225587_1_, int p_225587_2_);

   IVertexBuilder func_225584_a_(float p_225584_1_, float p_225584_2_, float p_225584_3_);

   void endVertex();

   default void func_225588_a_(float p_225588_1_, float p_225588_2_, float p_225588_3_, float p_225588_4_, float p_225588_5_, float p_225588_6_, float p_225588_7_, float p_225588_8_, float p_225588_9_, int p_225588_10_, int p_225588_11_, float p_225588_12_, float p_225588_13_, float p_225588_14_) {
      this.func_225582_a_((double)p_225588_1_, (double)p_225588_2_, (double)p_225588_3_);
      this.func_227885_a_(p_225588_4_, p_225588_5_, p_225588_6_, p_225588_7_);
      this.func_225583_a_(p_225588_8_, p_225588_9_);
      this.func_227891_b_(p_225588_10_);
      this.func_227886_a_(p_225588_11_);
      this.func_225584_a_(p_225588_12_, p_225588_13_, p_225588_14_);
      this.endVertex();
   }

   default IVertexBuilder func_227885_a_(float p_227885_1_, float p_227885_2_, float p_227885_3_, float p_227885_4_) {
      return this.func_225586_a_((int)(p_227885_1_ * 255.0F), (int)(p_227885_2_ * 255.0F), (int)(p_227885_3_ * 255.0F), (int)(p_227885_4_ * 255.0F));
   }

   default IVertexBuilder func_227886_a_(int p_227886_1_) {
      return this.func_225587_b_(p_227886_1_ & '\uffff', p_227886_1_ >> 16 & '\uffff');
   }

   default IVertexBuilder func_227891_b_(int p_227891_1_) {
      return this.func_225585_a_(p_227891_1_ & '\uffff', p_227891_1_ >> 16 & '\uffff');
   }

   default void func_227889_a_(MatrixStack.Entry p_227889_1_, BakedQuad p_227889_2_, float p_227889_3_, float p_227889_4_, float p_227889_5_, int p_227889_6_, int p_227889_7_) {
      this.func_227890_a_(p_227889_1_, p_227889_2_, new float[]{1.0F, 1.0F, 1.0F, 1.0F}, p_227889_3_, p_227889_4_, p_227889_5_, new int[]{p_227889_6_, p_227889_6_, p_227889_6_, p_227889_6_}, p_227889_7_, false);
   }

   default void func_227890_a_(MatrixStack.Entry p_227890_1_, BakedQuad p_227890_2_, float[] p_227890_3_, float p_227890_4_, float p_227890_5_, float p_227890_6_, int[] p_227890_7_, int p_227890_8_, boolean p_227890_9_) {
      int[] aint = p_227890_2_.getVertexData();
      Vec3i vec3i = p_227890_2_.getFace().getDirectionVec();
      Vector3f vector3f = new Vector3f((float)vec3i.getX(), (float)vec3i.getY(), (float)vec3i.getZ());
      Matrix4f matrix4f = p_227890_1_.func_227870_a_();
      vector3f.func_229188_a_(p_227890_1_.func_227872_b_());
      int i = 8;
      int j = aint.length / 8;

      try (MemoryStack memorystack = MemoryStack.stackPush()) {
         ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormats.BLOCK.getSize());
         IntBuffer intbuffer = bytebuffer.asIntBuffer();

         for(int k = 0; k < j; ++k) {
            intbuffer.clear();
            intbuffer.put(aint, k * 8, 8);
            float f = bytebuffer.getFloat(0);
            float f1 = bytebuffer.getFloat(4);
            float f2 = bytebuffer.getFloat(8);
            float f3;
            float f4;
            float f5;
            if (p_227890_9_) {
               float f6 = (float)(bytebuffer.get(12) & 255) / 255.0F;
               float f7 = (float)(bytebuffer.get(13) & 255) / 255.0F;
               float f8 = (float)(bytebuffer.get(14) & 255) / 255.0F;
               f3 = f6 * p_227890_3_[k] * p_227890_4_;
               f4 = f7 * p_227890_3_[k] * p_227890_5_;
               f5 = f8 * p_227890_3_[k] * p_227890_6_;
            } else {
               f3 = p_227890_3_[k] * p_227890_4_;
               f4 = p_227890_3_[k] * p_227890_5_;
               f5 = p_227890_3_[k] * p_227890_6_;
            }

            int l = applyBakedLighting(p_227890_7_[k], bytebuffer);
            float f9 = bytebuffer.getFloat(16);
            float f10 = bytebuffer.getFloat(20);
            Vector4f vector4f = new Vector4f(f, f1, f2, 1.0F);
            vector4f.func_229372_a_(matrix4f);
            applyBakedNormals(vector3f, bytebuffer, p_227890_1_.func_227872_b_());
            this.func_225588_a_(vector4f.getX(), vector4f.getY(), vector4f.getZ(), f3, f4, f5, 1.0F, f9, f10, p_227890_8_, l, vector3f.getX(), vector3f.getY(), vector3f.getZ());
         }
      }

   }

   default IVertexBuilder func_227888_a_(Matrix4f p_227888_1_, float p_227888_2_, float p_227888_3_, float p_227888_4_) {
      Vector4f vector4f = new Vector4f(p_227888_2_, p_227888_3_, p_227888_4_, 1.0F);
      vector4f.func_229372_a_(p_227888_1_);
      return this.func_225582_a_((double)vector4f.getX(), (double)vector4f.getY(), (double)vector4f.getZ());
   }

   default IVertexBuilder func_227887_a_(Matrix3f p_227887_1_, float p_227887_2_, float p_227887_3_, float p_227887_4_) {
      Vector3f vector3f = new Vector3f(p_227887_2_, p_227887_3_, p_227887_4_);
      vector3f.func_229188_a_(p_227887_1_);
      return this.func_225584_a_(vector3f.getX(), vector3f.getY(), vector3f.getZ());
   }
}