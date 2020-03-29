package com.mojang.blaze3d.matrix;

import com.google.common.collect.Queues;
import java.util.Deque;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MatrixStack {
   private final Deque<MatrixStack.Entry> field_227859_a_ = Util.make(Queues.newArrayDeque(), (p_227864_0_) -> {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.func_226591_a_();
      Matrix3f matrix3f = new Matrix3f();
      matrix3f.func_226119_c_();
      p_227864_0_.add(new MatrixStack.Entry(matrix4f, matrix3f));
   });

   public void func_227861_a_(double p_227861_1_, double p_227861_3_, double p_227861_5_) {
      MatrixStack.Entry matrixstack$entry = this.field_227859_a_.getLast();
      matrixstack$entry.field_227868_a_.func_226595_a_(Matrix4f.func_226599_b_((float)p_227861_1_, (float)p_227861_3_, (float)p_227861_5_));
   }

   public void func_227862_a_(float p_227862_1_, float p_227862_2_, float p_227862_3_) {
      MatrixStack.Entry matrixstack$entry = this.field_227859_a_.getLast();
      matrixstack$entry.field_227868_a_.func_226595_a_(Matrix4f.func_226593_a_(p_227862_1_, p_227862_2_, p_227862_3_));
      if (p_227862_1_ == p_227862_2_ && p_227862_2_ == p_227862_3_) {
         if (p_227862_1_ > 0.0F) {
            return;
         }

         matrixstack$entry.field_227869_b_.func_226111_a_(-1.0F);
      }

      float f = 1.0F / p_227862_1_;
      float f1 = 1.0F / p_227862_2_;
      float f2 = 1.0F / p_227862_3_;
      float f3 = MathHelper.func_226166_j_(f * f1 * f2);
      matrixstack$entry.field_227869_b_.func_226118_b_(Matrix3f.func_226117_b_(f3 * f, f3 * f1, f3 * f2));
   }

   public void func_227863_a_(Quaternion p_227863_1_) {
      MatrixStack.Entry matrixstack$entry = this.field_227859_a_.getLast();
      matrixstack$entry.field_227868_a_.func_226596_a_(p_227863_1_);
      matrixstack$entry.field_227869_b_.func_226115_a_(p_227863_1_);
   }

   public void func_227860_a_() {
      MatrixStack.Entry matrixstack$entry = this.field_227859_a_.getLast();
      this.field_227859_a_.addLast(new MatrixStack.Entry(matrixstack$entry.field_227868_a_.func_226601_d_(), matrixstack$entry.field_227869_b_.func_226121_d_()));
   }

   public void func_227865_b_() {
      this.field_227859_a_.removeLast();
   }

   public MatrixStack.Entry func_227866_c_() {
      return this.field_227859_a_.getLast();
   }

   public boolean func_227867_d_() {
      return this.field_227859_a_.size() == 1;
   }

   @OnlyIn(Dist.CLIENT)
   public static final class Entry {
      private final Matrix4f field_227868_a_;
      private final Matrix3f field_227869_b_;

      private Entry(Matrix4f p_i225909_1_, Matrix3f p_i225909_2_) {
         this.field_227868_a_ = p_i225909_1_;
         this.field_227869_b_ = p_i225909_2_;
      }

      public Matrix4f func_227870_a_() {
         return this.field_227868_a_;
      }

      public Matrix3f func_227872_b_() {
         return this.field_227869_b_;
      }
   }
}