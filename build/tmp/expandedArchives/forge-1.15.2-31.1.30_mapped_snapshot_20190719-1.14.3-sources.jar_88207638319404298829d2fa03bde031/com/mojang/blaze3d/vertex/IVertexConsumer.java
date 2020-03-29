package com.mojang.blaze3d.vertex;

import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IVertexConsumer extends IVertexBuilder {
   VertexFormatElement func_225592_i_();

   void nextVertexFormatIndex();

   void func_225589_a_(int p_225589_1_, byte p_225589_2_);

   void func_225591_a_(int p_225591_1_, short p_225591_2_);

   void func_225590_a_(int p_225590_1_, float p_225590_2_);

   default IVertexBuilder func_225582_a_(double p_225582_1_, double p_225582_3_, double p_225582_5_) {
      if (this.func_225592_i_().getType() != VertexFormatElement.Type.FLOAT) {
         throw new IllegalStateException();
      } else {
         this.func_225590_a_(0, (float)p_225582_1_);
         this.func_225590_a_(4, (float)p_225582_3_);
         this.func_225590_a_(8, (float)p_225582_5_);
         this.nextVertexFormatIndex();
         return this;
      }
   }

   default IVertexBuilder func_225586_a_(int p_225586_1_, int p_225586_2_, int p_225586_3_, int p_225586_4_) {
      VertexFormatElement vertexformatelement = this.func_225592_i_();
      if (vertexformatelement.getUsage() != VertexFormatElement.Usage.COLOR) {
         return this;
      } else if (vertexformatelement.getType() != VertexFormatElement.Type.UBYTE) {
         throw new IllegalStateException();
      } else {
         this.func_225589_a_(0, (byte)p_225586_1_);
         this.func_225589_a_(1, (byte)p_225586_2_);
         this.func_225589_a_(2, (byte)p_225586_3_);
         this.func_225589_a_(3, (byte)p_225586_4_);
         this.nextVertexFormatIndex();
         return this;
      }
   }

   default IVertexBuilder func_225583_a_(float p_225583_1_, float p_225583_2_) {
      VertexFormatElement vertexformatelement = this.func_225592_i_();
      if (vertexformatelement.getUsage() == VertexFormatElement.Usage.UV && vertexformatelement.getIndex() == 0) {
         if (vertexformatelement.getType() != VertexFormatElement.Type.FLOAT) {
            throw new IllegalStateException();
         } else {
            this.func_225590_a_(0, p_225583_1_);
            this.func_225590_a_(4, p_225583_2_);
            this.nextVertexFormatIndex();
            return this;
         }
      } else {
         return this;
      }
   }

   default IVertexBuilder func_225585_a_(int p_225585_1_, int p_225585_2_) {
      return this.func_227847_a_((short)p_225585_1_, (short)p_225585_2_, 1);
   }

   default IVertexBuilder func_225587_b_(int p_225587_1_, int p_225587_2_) {
      return this.func_227847_a_((short)p_225587_1_, (short)p_225587_2_, 2);
   }

   default IVertexBuilder func_227847_a_(short p_227847_1_, short p_227847_2_, int p_227847_3_) {
      VertexFormatElement vertexformatelement = this.func_225592_i_();
      if (vertexformatelement.getUsage() == VertexFormatElement.Usage.UV && vertexformatelement.getIndex() == p_227847_3_) {
         if (vertexformatelement.getType() != VertexFormatElement.Type.SHORT) {
            throw new IllegalStateException();
         } else {
            this.func_225591_a_(0, p_227847_1_);
            this.func_225591_a_(2, p_227847_2_);
            this.nextVertexFormatIndex();
            return this;
         }
      } else {
         return this;
      }
   }

   default IVertexBuilder func_225584_a_(float p_225584_1_, float p_225584_2_, float p_225584_3_) {
      VertexFormatElement vertexformatelement = this.func_225592_i_();
      if (vertexformatelement.getUsage() != VertexFormatElement.Usage.NORMAL) {
         return this;
      } else if (vertexformatelement.getType() != VertexFormatElement.Type.BYTE) {
         throw new IllegalStateException();
      } else {
         this.func_225589_a_(0, func_227846_a_(p_225584_1_));
         this.func_225589_a_(1, func_227846_a_(p_225584_2_));
         this.func_225589_a_(2, func_227846_a_(p_225584_3_));
         this.nextVertexFormatIndex();
         return this;
      }
   }

   static byte func_227846_a_(float p_227846_0_) {
      return (byte)((int)(MathHelper.clamp(p_227846_0_, -1.0F, 1.0F) * 127.0F) & 255);
   }
}