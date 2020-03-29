package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class Vector3f {
   public static Vector3f field_229178_a_ = new Vector3f(-1.0F, 0.0F, 0.0F);
   public static Vector3f field_229179_b_ = new Vector3f(1.0F, 0.0F, 0.0F);
   public static Vector3f field_229180_c_ = new Vector3f(0.0F, -1.0F, 0.0F);
   public static Vector3f field_229181_d_ = new Vector3f(0.0F, 1.0F, 0.0F);
   public static Vector3f field_229182_e_ = new Vector3f(0.0F, 0.0F, -1.0F);
   public static Vector3f field_229183_f_ = new Vector3f(0.0F, 0.0F, 1.0F);
   private float field_229184_g_;
   private float field_229185_h_;
   private float field_229186_i_;

   public Vector3f() {
   }

   public Vector3f(float x, float y, float z) {
      this.field_229184_g_ = x;
      this.field_229185_h_ = y;
      this.field_229186_i_ = z;
   }

   public Vector3f(Vec3d p_i51412_1_) {
      this((float)p_i51412_1_.x, (float)p_i51412_1_.y, (float)p_i51412_1_.z);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         Vector3f vector3f = (Vector3f)p_equals_1_;
         if (Float.compare(vector3f.field_229184_g_, this.field_229184_g_) != 0) {
            return false;
         } else if (Float.compare(vector3f.field_229185_h_, this.field_229185_h_) != 0) {
            return false;
         } else {
            return Float.compare(vector3f.field_229186_i_, this.field_229186_i_) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = Float.floatToIntBits(this.field_229184_g_);
      i = 31 * i + Float.floatToIntBits(this.field_229185_h_);
      i = 31 * i + Float.floatToIntBits(this.field_229186_i_);
      return i;
   }

   public float getX() {
      return this.field_229184_g_;
   }

   public float getY() {
      return this.field_229185_h_;
   }

   public float getZ() {
      return this.field_229186_i_;
   }

   @OnlyIn(Dist.CLIENT)
   public void mul(float multiplier) {
      this.field_229184_g_ *= multiplier;
      this.field_229185_h_ *= multiplier;
      this.field_229186_i_ *= multiplier;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_229192_b_(float p_229192_1_, float p_229192_2_, float p_229192_3_) {
      this.field_229184_g_ *= p_229192_1_;
      this.field_229185_h_ *= p_229192_2_;
      this.field_229186_i_ *= p_229192_3_;
   }

   @OnlyIn(Dist.CLIENT)
   public void clamp(float min, float max) {
      this.field_229184_g_ = MathHelper.clamp(this.field_229184_g_, min, max);
      this.field_229185_h_ = MathHelper.clamp(this.field_229185_h_, min, max);
      this.field_229186_i_ = MathHelper.clamp(this.field_229186_i_, min, max);
   }

   public void set(float x, float y, float z) {
      this.field_229184_g_ = x;
      this.field_229185_h_ = y;
      this.field_229186_i_ = z;
   }

   @OnlyIn(Dist.CLIENT)
   public void add(float x, float y, float z) {
      this.field_229184_g_ += x;
      this.field_229185_h_ += y;
      this.field_229186_i_ += z;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_229189_a_(Vector3f p_229189_1_) {
      this.field_229184_g_ += p_229189_1_.field_229184_g_;
      this.field_229185_h_ += p_229189_1_.field_229185_h_;
      this.field_229186_i_ += p_229189_1_.field_229186_i_;
   }

   @OnlyIn(Dist.CLIENT)
   public void sub(Vector3f vec) {
      this.field_229184_g_ -= vec.field_229184_g_;
      this.field_229185_h_ -= vec.field_229185_h_;
      this.field_229186_i_ -= vec.field_229186_i_;
   }

   @OnlyIn(Dist.CLIENT)
   public float dot(Vector3f vec) {
      return this.field_229184_g_ * vec.field_229184_g_ + this.field_229185_h_ * vec.field_229185_h_ + this.field_229186_i_ * vec.field_229186_i_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_229194_d_() {
      float f = this.field_229184_g_ * this.field_229184_g_ + this.field_229185_h_ * this.field_229185_h_ + this.field_229186_i_ * this.field_229186_i_;
      if ((double)f < 1.0E-5D) {
         return false;
      } else {
         float f1 = MathHelper.func_226165_i_(f);
         this.field_229184_g_ *= f1;
         this.field_229185_h_ *= f1;
         this.field_229186_i_ *= f1;
         return true;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void cross(Vector3f vec) {
      float f = this.field_229184_g_;
      float f1 = this.field_229185_h_;
      float f2 = this.field_229186_i_;
      float f3 = vec.getX();
      float f4 = vec.getY();
      float f5 = vec.getZ();
      this.field_229184_g_ = f1 * f5 - f2 * f4;
      this.field_229185_h_ = f2 * f3 - f * f5;
      this.field_229186_i_ = f * f4 - f1 * f3;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_229188_a_(Matrix3f p_229188_1_) {
      float f = this.field_229184_g_;
      float f1 = this.field_229185_h_;
      float f2 = this.field_229186_i_;
      this.field_229184_g_ = p_229188_1_.field_226097_a_ * f + p_229188_1_.field_226098_b_ * f1 + p_229188_1_.field_226099_c_ * f2;
      this.field_229185_h_ = p_229188_1_.field_226100_d_ * f + p_229188_1_.field_226101_e_ * f1 + p_229188_1_.field_226102_f_ * f2;
      this.field_229186_i_ = p_229188_1_.field_226103_g_ * f + p_229188_1_.field_226104_h_ * f1 + p_229188_1_.field_226105_i_ * f2;
   }

   public void func_214905_a(Quaternion p_214905_1_) {
      Quaternion quaternion = new Quaternion(p_214905_1_);
      quaternion.multiply(new Quaternion(this.getX(), this.getY(), this.getZ(), 0.0F));
      Quaternion quaternion1 = new Quaternion(p_214905_1_);
      quaternion1.conjugate();
      quaternion.multiply(quaternion1);
      this.set(quaternion.getX(), quaternion.getY(), quaternion.getZ());
   }

   @OnlyIn(Dist.CLIENT)
   public void func_229190_a_(Vector3f p_229190_1_, float p_229190_2_) {
      float f = 1.0F - p_229190_2_;
      this.field_229184_g_ = this.field_229184_g_ * f + p_229190_1_.field_229184_g_ * p_229190_2_;
      this.field_229185_h_ = this.field_229185_h_ * f + p_229190_1_.field_229185_h_ * p_229190_2_;
      this.field_229186_i_ = this.field_229186_i_ * f + p_229190_1_.field_229186_i_ * p_229190_2_;
   }

   @OnlyIn(Dist.CLIENT)
   public Quaternion func_229193_c_(float p_229193_1_) {
      return new Quaternion(this, p_229193_1_, false);
   }

   @OnlyIn(Dist.CLIENT)
   public Quaternion func_229187_a_(float p_229187_1_) {
      return new Quaternion(this, p_229187_1_, true);
   }

   @OnlyIn(Dist.CLIENT)
   public Vector3f func_229195_e_() {
      return new Vector3f(this.field_229184_g_, this.field_229185_h_, this.field_229186_i_);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_229191_a_(Float2FloatFunction p_229191_1_) {
      this.field_229184_g_ = p_229191_1_.get(this.field_229184_g_);
      this.field_229185_h_ = p_229191_1_.get(this.field_229185_h_);
      this.field_229186_i_ = p_229191_1_.get(this.field_229186_i_);
   }

   public String toString() {
      return "[" + this.field_229184_g_ + ", " + this.field_229185_h_ + ", " + this.field_229186_i_ + "]";
   }

   // Forge start
   public Vector3f(float[] values) {
      set(values);
   }
   public void set(float[] values) {
      this.field_229184_g_ = values[0];
      this.field_229185_h_ = values[1];
      this.field_229186_i_ = values[2];
   }
   public void setX(float x) { this.field_229184_g_ = x; }
   public void setY(float y) { this.field_229185_h_ = y; }
   public void setZ(float z) { this.field_229186_i_ = z; }
}