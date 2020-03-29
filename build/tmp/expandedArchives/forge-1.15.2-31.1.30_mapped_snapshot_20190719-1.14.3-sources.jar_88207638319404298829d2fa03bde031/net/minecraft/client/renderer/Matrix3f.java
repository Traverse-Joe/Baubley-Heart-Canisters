package net.minecraft.client.renderer;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Triple;

@OnlyIn(Dist.CLIENT)
public final class Matrix3f {
   private static final float field_226106_j_ = 3.0F + 2.0F * (float)Math.sqrt(2.0D);
   private static final float field_226107_k_ = (float)Math.cos((Math.PI / 8D));
   private static final float field_226108_l_ = (float)Math.sin((Math.PI / 8D));
   private static final float field_226109_m_ = 1.0F / (float)Math.sqrt(2.0D);
   protected float field_226097_a_;
   protected float field_226098_b_;
   protected float field_226099_c_;
   protected float field_226100_d_;
   protected float field_226101_e_;
   protected float field_226102_f_;
   protected float field_226103_g_;
   protected float field_226104_h_;
   protected float field_226105_i_;

   public Matrix3f() {
   }

   public Matrix3f(Quaternion p_i225696_1_) {
      float f = p_i225696_1_.getX();
      float f1 = p_i225696_1_.getY();
      float f2 = p_i225696_1_.getZ();
      float f3 = p_i225696_1_.getW();
      float f4 = 2.0F * f * f;
      float f5 = 2.0F * f1 * f1;
      float f6 = 2.0F * f2 * f2;
      this.field_226097_a_ = 1.0F - f5 - f6;
      this.field_226101_e_ = 1.0F - f6 - f4;
      this.field_226105_i_ = 1.0F - f4 - f5;
      float f7 = f * f1;
      float f8 = f1 * f2;
      float f9 = f2 * f;
      float f10 = f * f3;
      float f11 = f1 * f3;
      float f12 = f2 * f3;
      this.field_226100_d_ = 2.0F * (f7 + f12);
      this.field_226098_b_ = 2.0F * (f7 - f12);
      this.field_226103_g_ = 2.0F * (f9 - f11);
      this.field_226099_c_ = 2.0F * (f9 + f11);
      this.field_226104_h_ = 2.0F * (f8 + f10);
      this.field_226102_f_ = 2.0F * (f8 - f10);
   }

   public static Matrix3f func_226117_b_(float p_226117_0_, float p_226117_1_, float p_226117_2_) {
      Matrix3f matrix3f = new Matrix3f();
      matrix3f.field_226097_a_ = p_226117_0_;
      matrix3f.field_226101_e_ = p_226117_1_;
      matrix3f.field_226105_i_ = p_226117_2_;
      return matrix3f;
   }

   public Matrix3f(Matrix4f p_i225695_1_) {
      this.field_226097_a_ = p_i225695_1_.field_226575_a_;
      this.field_226098_b_ = p_i225695_1_.field_226576_b_;
      this.field_226099_c_ = p_i225695_1_.field_226577_c_;
      this.field_226100_d_ = p_i225695_1_.field_226579_e_;
      this.field_226101_e_ = p_i225695_1_.field_226580_f_;
      this.field_226102_f_ = p_i225695_1_.field_226581_g_;
      this.field_226103_g_ = p_i225695_1_.field_226583_i_;
      this.field_226104_h_ = p_i225695_1_.field_226584_j_;
      this.field_226105_i_ = p_i225695_1_.field_226585_k_;
   }

   public Matrix3f(Matrix3f p_i225694_1_) {
      this.field_226097_a_ = p_i225694_1_.field_226097_a_;
      this.field_226098_b_ = p_i225694_1_.field_226098_b_;
      this.field_226099_c_ = p_i225694_1_.field_226099_c_;
      this.field_226100_d_ = p_i225694_1_.field_226100_d_;
      this.field_226101_e_ = p_i225694_1_.field_226101_e_;
      this.field_226102_f_ = p_i225694_1_.field_226102_f_;
      this.field_226103_g_ = p_i225694_1_.field_226103_g_;
      this.field_226104_h_ = p_i225694_1_.field_226104_h_;
      this.field_226105_i_ = p_i225694_1_.field_226105_i_;
   }

   private static Pair<Float, Float> func_226113_a_(float p_226113_0_, float p_226113_1_, float p_226113_2_) {
      float f = 2.0F * (p_226113_0_ - p_226113_2_);
      if (field_226106_j_ * p_226113_1_ * p_226113_1_ < f * f) {
         float f1 = MathHelper.func_226165_i_(p_226113_1_ * p_226113_1_ + f * f);
         return Pair.of(f1 * p_226113_1_, f1 * f);
      } else {
         return Pair.of(field_226108_l_, field_226107_k_);
      }
   }

   private static Pair<Float, Float> func_226112_a_(float p_226112_0_, float p_226112_1_) {
      float f = (float)Math.hypot((double)p_226112_0_, (double)p_226112_1_);
      float f1 = f > 1.0E-6F ? p_226112_1_ : 0.0F;
      float f2 = Math.abs(p_226112_0_) + Math.max(f, 1.0E-6F);
      if (p_226112_0_ < 0.0F) {
         float f3 = f1;
         f1 = f2;
         f2 = f3;
      }

      float f4 = MathHelper.func_226165_i_(f2 * f2 + f1 * f1);
      f2 = f2 * f4;
      f1 = f1 * f4;
      return Pair.of(f1, f2);
   }

   private static Quaternion func_226120_c_(Matrix3f p_226120_0_) {
      Matrix3f matrix3f = new Matrix3f();
      Quaternion quaternion = Quaternion.field_227060_a_.func_227068_g_();
      if (p_226120_0_.field_226098_b_ * p_226120_0_.field_226098_b_ + p_226120_0_.field_226100_d_ * p_226120_0_.field_226100_d_ > 1.0E-6F) {
         Pair<Float, Float> pair = func_226113_a_(p_226120_0_.field_226097_a_, 0.5F * (p_226120_0_.field_226098_b_ + p_226120_0_.field_226100_d_), p_226120_0_.field_226101_e_);
         Float f = pair.getFirst();
         Float f1 = pair.getSecond();
         Quaternion quaternion1 = new Quaternion(0.0F, 0.0F, f, f1);
         float f2 = f1 * f1 - f * f;
         float f3 = -2.0F * f * f1;
         float f4 = f1 * f1 + f * f;
         quaternion.multiply(quaternion1);
         matrix3f.func_226119_c_();
         matrix3f.field_226097_a_ = f2;
         matrix3f.field_226101_e_ = f2;
         matrix3f.field_226100_d_ = -f3;
         matrix3f.field_226098_b_ = f3;
         matrix3f.field_226105_i_ = f4;
         p_226120_0_.func_226118_b_(matrix3f);
         matrix3f.func_226110_a_();
         matrix3f.func_226118_b_(p_226120_0_);
         p_226120_0_.func_226114_a_(matrix3f);
      }

      if (p_226120_0_.field_226099_c_ * p_226120_0_.field_226099_c_ + p_226120_0_.field_226103_g_ * p_226120_0_.field_226103_g_ > 1.0E-6F) {
         Pair<Float, Float> pair1 = func_226113_a_(p_226120_0_.field_226097_a_, 0.5F * (p_226120_0_.field_226099_c_ + p_226120_0_.field_226103_g_), p_226120_0_.field_226105_i_);
         float f5 = -pair1.getFirst();
         Float f7 = pair1.getSecond();
         Quaternion quaternion2 = new Quaternion(0.0F, f5, 0.0F, f7);
         float f9 = f7 * f7 - f5 * f5;
         float f11 = -2.0F * f5 * f7;
         float f13 = f7 * f7 + f5 * f5;
         quaternion.multiply(quaternion2);
         matrix3f.func_226119_c_();
         matrix3f.field_226097_a_ = f9;
         matrix3f.field_226105_i_ = f9;
         matrix3f.field_226103_g_ = f11;
         matrix3f.field_226099_c_ = -f11;
         matrix3f.field_226101_e_ = f13;
         p_226120_0_.func_226118_b_(matrix3f);
         matrix3f.func_226110_a_();
         matrix3f.func_226118_b_(p_226120_0_);
         p_226120_0_.func_226114_a_(matrix3f);
      }

      if (p_226120_0_.field_226102_f_ * p_226120_0_.field_226102_f_ + p_226120_0_.field_226104_h_ * p_226120_0_.field_226104_h_ > 1.0E-6F) {
         Pair<Float, Float> pair2 = func_226113_a_(p_226120_0_.field_226101_e_, 0.5F * (p_226120_0_.field_226102_f_ + p_226120_0_.field_226104_h_), p_226120_0_.field_226105_i_);
         Float f6 = pair2.getFirst();
         Float f8 = pair2.getSecond();
         Quaternion quaternion3 = new Quaternion(f6, 0.0F, 0.0F, f8);
         float f10 = f8 * f8 - f6 * f6;
         float f12 = -2.0F * f6 * f8;
         float f14 = f8 * f8 + f6 * f6;
         quaternion.multiply(quaternion3);
         matrix3f.func_226119_c_();
         matrix3f.field_226101_e_ = f10;
         matrix3f.field_226105_i_ = f10;
         matrix3f.field_226104_h_ = -f12;
         matrix3f.field_226102_f_ = f12;
         matrix3f.field_226097_a_ = f14;
         p_226120_0_.func_226118_b_(matrix3f);
         matrix3f.func_226110_a_();
         matrix3f.func_226118_b_(p_226120_0_);
         p_226120_0_.func_226114_a_(matrix3f);
      }

      return quaternion;
   }

   public void func_226110_a_() {
      float f = this.field_226098_b_;
      this.field_226098_b_ = this.field_226100_d_;
      this.field_226100_d_ = f;
      f = this.field_226099_c_;
      this.field_226099_c_ = this.field_226103_g_;
      this.field_226103_g_ = f;
      f = this.field_226102_f_;
      this.field_226102_f_ = this.field_226104_h_;
      this.field_226104_h_ = f;
   }

   public Triple<Quaternion, Vector3f, Quaternion> func_226116_b_() {
      Quaternion quaternion = Quaternion.field_227060_a_.func_227068_g_();
      Quaternion quaternion1 = Quaternion.field_227060_a_.func_227068_g_();
      Matrix3f matrix3f = this.func_226121_d_();
      matrix3f.func_226110_a_();
      matrix3f.func_226118_b_(this);

      for(int i = 0; i < 5; ++i) {
         quaternion1.multiply(func_226120_c_(matrix3f));
      }

      quaternion1.func_227067_f_();
      Matrix3f matrix3f4 = new Matrix3f(this);
      matrix3f4.func_226118_b_(new Matrix3f(quaternion1));
      float f = 1.0F;
      Pair<Float, Float> pair = func_226112_a_(matrix3f4.field_226097_a_, matrix3f4.field_226100_d_);
      Float f1 = pair.getFirst();
      Float f2 = pair.getSecond();
      float f3 = f2 * f2 - f1 * f1;
      float f4 = -2.0F * f1 * f2;
      float f5 = f2 * f2 + f1 * f1;
      Quaternion quaternion2 = new Quaternion(0.0F, 0.0F, f1, f2);
      quaternion.multiply(quaternion2);
      Matrix3f matrix3f1 = new Matrix3f();
      matrix3f1.func_226119_c_();
      matrix3f1.field_226097_a_ = f3;
      matrix3f1.field_226101_e_ = f3;
      matrix3f1.field_226100_d_ = f4;
      matrix3f1.field_226098_b_ = -f4;
      matrix3f1.field_226105_i_ = f5;
      f = f * f5;
      matrix3f1.func_226118_b_(matrix3f4);
      pair = func_226112_a_(matrix3f1.field_226097_a_, matrix3f1.field_226103_g_);
      float f6 = -pair.getFirst();
      Float f7 = pair.getSecond();
      float f8 = f7 * f7 - f6 * f6;
      float f9 = -2.0F * f6 * f7;
      float f10 = f7 * f7 + f6 * f6;
      Quaternion quaternion3 = new Quaternion(0.0F, f6, 0.0F, f7);
      quaternion.multiply(quaternion3);
      Matrix3f matrix3f2 = new Matrix3f();
      matrix3f2.func_226119_c_();
      matrix3f2.field_226097_a_ = f8;
      matrix3f2.field_226105_i_ = f8;
      matrix3f2.field_226103_g_ = -f9;
      matrix3f2.field_226099_c_ = f9;
      matrix3f2.field_226101_e_ = f10;
      f = f * f10;
      matrix3f2.func_226118_b_(matrix3f1);
      pair = func_226112_a_(matrix3f2.field_226101_e_, matrix3f2.field_226104_h_);
      Float f11 = pair.getFirst();
      Float f12 = pair.getSecond();
      float f13 = f12 * f12 - f11 * f11;
      float f14 = -2.0F * f11 * f12;
      float f15 = f12 * f12 + f11 * f11;
      Quaternion quaternion4 = new Quaternion(f11, 0.0F, 0.0F, f12);
      quaternion.multiply(quaternion4);
      Matrix3f matrix3f3 = new Matrix3f();
      matrix3f3.func_226119_c_();
      matrix3f3.field_226101_e_ = f13;
      matrix3f3.field_226105_i_ = f13;
      matrix3f3.field_226104_h_ = f14;
      matrix3f3.field_226102_f_ = -f14;
      matrix3f3.field_226097_a_ = f15;
      f = f * f15;
      matrix3f3.func_226118_b_(matrix3f2);
      f = 1.0F / f;
      quaternion.func_227065_a_((float)Math.sqrt((double)f));
      Vector3f vector3f = new Vector3f(matrix3f3.field_226097_a_ * f, matrix3f3.field_226101_e_ * f, matrix3f3.field_226105_i_ * f);
      return Triple.of(quaternion, vector3f, quaternion1);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         Matrix3f matrix3f = (Matrix3f)p_equals_1_;
         return Float.compare(matrix3f.field_226097_a_, this.field_226097_a_) == 0 && Float.compare(matrix3f.field_226098_b_, this.field_226098_b_) == 0 && Float.compare(matrix3f.field_226099_c_, this.field_226099_c_) == 0 && Float.compare(matrix3f.field_226100_d_, this.field_226100_d_) == 0 && Float.compare(matrix3f.field_226101_e_, this.field_226101_e_) == 0 && Float.compare(matrix3f.field_226102_f_, this.field_226102_f_) == 0 && Float.compare(matrix3f.field_226103_g_, this.field_226103_g_) == 0 && Float.compare(matrix3f.field_226104_h_, this.field_226104_h_) == 0 && Float.compare(matrix3f.field_226105_i_, this.field_226105_i_) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = this.field_226097_a_ != 0.0F ? Float.floatToIntBits(this.field_226097_a_) : 0;
      i = 31 * i + (this.field_226098_b_ != 0.0F ? Float.floatToIntBits(this.field_226098_b_) : 0);
      i = 31 * i + (this.field_226099_c_ != 0.0F ? Float.floatToIntBits(this.field_226099_c_) : 0);
      i = 31 * i + (this.field_226100_d_ != 0.0F ? Float.floatToIntBits(this.field_226100_d_) : 0);
      i = 31 * i + (this.field_226101_e_ != 0.0F ? Float.floatToIntBits(this.field_226101_e_) : 0);
      i = 31 * i + (this.field_226102_f_ != 0.0F ? Float.floatToIntBits(this.field_226102_f_) : 0);
      i = 31 * i + (this.field_226103_g_ != 0.0F ? Float.floatToIntBits(this.field_226103_g_) : 0);
      i = 31 * i + (this.field_226104_h_ != 0.0F ? Float.floatToIntBits(this.field_226104_h_) : 0);
      i = 31 * i + (this.field_226105_i_ != 0.0F ? Float.floatToIntBits(this.field_226105_i_) : 0);
      return i;
   }

   public void func_226114_a_(Matrix3f p_226114_1_) {
      this.field_226097_a_ = p_226114_1_.field_226097_a_;
      this.field_226098_b_ = p_226114_1_.field_226098_b_;
      this.field_226099_c_ = p_226114_1_.field_226099_c_;
      this.field_226100_d_ = p_226114_1_.field_226100_d_;
      this.field_226101_e_ = p_226114_1_.field_226101_e_;
      this.field_226102_f_ = p_226114_1_.field_226102_f_;
      this.field_226103_g_ = p_226114_1_.field_226103_g_;
      this.field_226104_h_ = p_226114_1_.field_226104_h_;
      this.field_226105_i_ = p_226114_1_.field_226105_i_;
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("Matrix3f:\n");
      stringbuilder.append(this.field_226097_a_);
      stringbuilder.append(" ");
      stringbuilder.append(this.field_226098_b_);
      stringbuilder.append(" ");
      stringbuilder.append(this.field_226099_c_);
      stringbuilder.append("\n");
      stringbuilder.append(this.field_226100_d_);
      stringbuilder.append(" ");
      stringbuilder.append(this.field_226101_e_);
      stringbuilder.append(" ");
      stringbuilder.append(this.field_226102_f_);
      stringbuilder.append("\n");
      stringbuilder.append(this.field_226103_g_);
      stringbuilder.append(" ");
      stringbuilder.append(this.field_226104_h_);
      stringbuilder.append(" ");
      stringbuilder.append(this.field_226105_i_);
      stringbuilder.append("\n");
      return stringbuilder.toString();
   }

   public void func_226119_c_() {
      this.field_226097_a_ = 1.0F;
      this.field_226098_b_ = 0.0F;
      this.field_226099_c_ = 0.0F;
      this.field_226100_d_ = 0.0F;
      this.field_226101_e_ = 1.0F;
      this.field_226102_f_ = 0.0F;
      this.field_226103_g_ = 0.0F;
      this.field_226104_h_ = 0.0F;
      this.field_226105_i_ = 1.0F;
   }

   public float func_226122_e_() {
      float f = this.field_226101_e_ * this.field_226105_i_ - this.field_226102_f_ * this.field_226104_h_;
      float f1 = -(this.field_226100_d_ * this.field_226105_i_ - this.field_226102_f_ * this.field_226103_g_);
      float f2 = this.field_226100_d_ * this.field_226104_h_ - this.field_226101_e_ * this.field_226103_g_;
      float f3 = -(this.field_226098_b_ * this.field_226105_i_ - this.field_226099_c_ * this.field_226104_h_);
      float f4 = this.field_226097_a_ * this.field_226105_i_ - this.field_226099_c_ * this.field_226103_g_;
      float f5 = -(this.field_226097_a_ * this.field_226104_h_ - this.field_226098_b_ * this.field_226103_g_);
      float f6 = this.field_226098_b_ * this.field_226102_f_ - this.field_226099_c_ * this.field_226101_e_;
      float f7 = -(this.field_226097_a_ * this.field_226102_f_ - this.field_226099_c_ * this.field_226100_d_);
      float f8 = this.field_226097_a_ * this.field_226101_e_ - this.field_226098_b_ * this.field_226100_d_;
      float f9 = this.field_226097_a_ * f + this.field_226098_b_ * f1 + this.field_226099_c_ * f2;
      this.field_226097_a_ = f;
      this.field_226100_d_ = f1;
      this.field_226103_g_ = f2;
      this.field_226098_b_ = f3;
      this.field_226101_e_ = f4;
      this.field_226104_h_ = f5;
      this.field_226099_c_ = f6;
      this.field_226102_f_ = f7;
      this.field_226105_i_ = f8;
      return f9;
   }

   public boolean func_226123_f_() {
      float f = this.func_226122_e_();
      if (Math.abs(f) > 1.0E-6F) {
         this.func_226111_a_(f);
         return true;
      } else {
         return false;
      }
   }

   public void func_226118_b_(Matrix3f p_226118_1_) {
      float f = this.field_226097_a_ * p_226118_1_.field_226097_a_ + this.field_226098_b_ * p_226118_1_.field_226100_d_ + this.field_226099_c_ * p_226118_1_.field_226103_g_;
      float f1 = this.field_226097_a_ * p_226118_1_.field_226098_b_ + this.field_226098_b_ * p_226118_1_.field_226101_e_ + this.field_226099_c_ * p_226118_1_.field_226104_h_;
      float f2 = this.field_226097_a_ * p_226118_1_.field_226099_c_ + this.field_226098_b_ * p_226118_1_.field_226102_f_ + this.field_226099_c_ * p_226118_1_.field_226105_i_;
      float f3 = this.field_226100_d_ * p_226118_1_.field_226097_a_ + this.field_226101_e_ * p_226118_1_.field_226100_d_ + this.field_226102_f_ * p_226118_1_.field_226103_g_;
      float f4 = this.field_226100_d_ * p_226118_1_.field_226098_b_ + this.field_226101_e_ * p_226118_1_.field_226101_e_ + this.field_226102_f_ * p_226118_1_.field_226104_h_;
      float f5 = this.field_226100_d_ * p_226118_1_.field_226099_c_ + this.field_226101_e_ * p_226118_1_.field_226102_f_ + this.field_226102_f_ * p_226118_1_.field_226105_i_;
      float f6 = this.field_226103_g_ * p_226118_1_.field_226097_a_ + this.field_226104_h_ * p_226118_1_.field_226100_d_ + this.field_226105_i_ * p_226118_1_.field_226103_g_;
      float f7 = this.field_226103_g_ * p_226118_1_.field_226098_b_ + this.field_226104_h_ * p_226118_1_.field_226101_e_ + this.field_226105_i_ * p_226118_1_.field_226104_h_;
      float f8 = this.field_226103_g_ * p_226118_1_.field_226099_c_ + this.field_226104_h_ * p_226118_1_.field_226102_f_ + this.field_226105_i_ * p_226118_1_.field_226105_i_;
      this.field_226097_a_ = f;
      this.field_226098_b_ = f1;
      this.field_226099_c_ = f2;
      this.field_226100_d_ = f3;
      this.field_226101_e_ = f4;
      this.field_226102_f_ = f5;
      this.field_226103_g_ = f6;
      this.field_226104_h_ = f7;
      this.field_226105_i_ = f8;
   }

   public void func_226115_a_(Quaternion p_226115_1_) {
      this.func_226118_b_(new Matrix3f(p_226115_1_));
   }

   public void func_226111_a_(float p_226111_1_) {
      this.field_226097_a_ *= p_226111_1_;
      this.field_226098_b_ *= p_226111_1_;
      this.field_226099_c_ *= p_226111_1_;
      this.field_226100_d_ *= p_226111_1_;
      this.field_226101_e_ *= p_226111_1_;
      this.field_226102_f_ *= p_226111_1_;
      this.field_226103_g_ *= p_226111_1_;
      this.field_226104_h_ *= p_226111_1_;
      this.field_226105_i_ *= p_226111_1_;
   }

   public Matrix3f func_226121_d_() {
      return new Matrix3f(this);
   }

   // Forge start
   public void multiplyBackward(Matrix3f other) {
      Matrix3f copy = other.func_226121_d_();
      copy.func_226118_b_(this);
      this.func_226114_a_(copy);
   }
}