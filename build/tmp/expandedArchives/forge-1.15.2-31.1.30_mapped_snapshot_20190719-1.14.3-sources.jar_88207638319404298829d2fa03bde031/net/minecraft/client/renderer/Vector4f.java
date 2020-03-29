package net.minecraft.client.renderer;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Vector4f {
   private float field_229368_a_;
   private float field_229369_b_;
   private float field_229370_c_;
   private float field_229371_d_;

   public Vector4f() {
   }

   public Vector4f(float x, float y, float z, float w) {
      this.field_229368_a_ = x;
      this.field_229369_b_ = y;
      this.field_229370_c_ = z;
      this.field_229371_d_ = w;
   }

   public Vector4f(Vector3f p_i226061_1_) {
      this(p_i226061_1_.getX(), p_i226061_1_.getY(), p_i226061_1_.getZ(), 1.0F);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         Vector4f vector4f = (Vector4f)p_equals_1_;
         if (Float.compare(vector4f.field_229368_a_, this.field_229368_a_) != 0) {
            return false;
         } else if (Float.compare(vector4f.field_229369_b_, this.field_229369_b_) != 0) {
            return false;
         } else if (Float.compare(vector4f.field_229370_c_, this.field_229370_c_) != 0) {
            return false;
         } else {
            return Float.compare(vector4f.field_229371_d_, this.field_229371_d_) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = Float.floatToIntBits(this.field_229368_a_);
      i = 31 * i + Float.floatToIntBits(this.field_229369_b_);
      i = 31 * i + Float.floatToIntBits(this.field_229370_c_);
      i = 31 * i + Float.floatToIntBits(this.field_229371_d_);
      return i;
   }

   public float getX() {
      return this.field_229368_a_;
   }

   public float getY() {
      return this.field_229369_b_;
   }

   public float getZ() {
      return this.field_229370_c_;
   }

   public float getW() {
      return this.field_229371_d_;
   }

   public void scale(Vector3f vec) {
      this.field_229368_a_ *= vec.getX();
      this.field_229369_b_ *= vec.getY();
      this.field_229370_c_ *= vec.getZ();
   }

   public void set(float x, float y, float z, float w) {
      this.field_229368_a_ = x;
      this.field_229369_b_ = y;
      this.field_229370_c_ = z;
      this.field_229371_d_ = w;
   }

   public float func_229373_a_(Vector4f p_229373_1_) {
      return this.field_229368_a_ * p_229373_1_.field_229368_a_ + this.field_229369_b_ * p_229373_1_.field_229369_b_ + this.field_229370_c_ * p_229373_1_.field_229370_c_ + this.field_229371_d_ * p_229373_1_.field_229371_d_;
   }

   public boolean func_229374_e_() {
      float f = this.field_229368_a_ * this.field_229368_a_ + this.field_229369_b_ * this.field_229369_b_ + this.field_229370_c_ * this.field_229370_c_ + this.field_229371_d_ * this.field_229371_d_;
      if ((double)f < 1.0E-5D) {
         return false;
      } else {
         float f1 = MathHelper.func_226165_i_(f);
         this.field_229368_a_ *= f1;
         this.field_229369_b_ *= f1;
         this.field_229370_c_ *= f1;
         this.field_229371_d_ *= f1;
         return true;
      }
   }

   public void func_229372_a_(Matrix4f p_229372_1_) {
      float f = this.field_229368_a_;
      float f1 = this.field_229369_b_;
      float f2 = this.field_229370_c_;
      float f3 = this.field_229371_d_;
      this.field_229368_a_ = p_229372_1_.field_226575_a_ * f + p_229372_1_.field_226576_b_ * f1 + p_229372_1_.field_226577_c_ * f2 + p_229372_1_.field_226578_d_ * f3;
      this.field_229369_b_ = p_229372_1_.field_226579_e_ * f + p_229372_1_.field_226580_f_ * f1 + p_229372_1_.field_226581_g_ * f2 + p_229372_1_.field_226582_h_ * f3;
      this.field_229370_c_ = p_229372_1_.field_226583_i_ * f + p_229372_1_.field_226584_j_ * f1 + p_229372_1_.field_226585_k_ * f2 + p_229372_1_.field_226586_l_ * f3;
      this.field_229371_d_ = p_229372_1_.field_226587_m_ * f + p_229372_1_.field_226588_n_ * f1 + p_229372_1_.field_226589_o_ * f2 + p_229372_1_.field_226590_p_ * f3;
   }

   public void func_195912_a(Quaternion quaternionIn) {
      Quaternion quaternion = new Quaternion(quaternionIn);
      quaternion.multiply(new Quaternion(this.getX(), this.getY(), this.getZ(), 0.0F));
      Quaternion quaternion1 = new Quaternion(quaternionIn);
      quaternion1.conjugate();
      quaternion.multiply(quaternion1);
      this.set(quaternion.getX(), quaternion.getY(), quaternion.getZ(), this.getW());
   }

   public void func_229375_f_() {
      this.field_229368_a_ /= this.field_229371_d_;
      this.field_229369_b_ /= this.field_229371_d_;
      this.field_229370_c_ /= this.field_229371_d_;
      this.field_229371_d_ = 1.0F;
   }

   public String toString() {
      return "[" + this.field_229368_a_ + ", " + this.field_229369_b_ + ", " + this.field_229370_c_ + ", " + this.field_229371_d_ + "]";
   }

   // Forge start
   public void set(float[] values) {
      this.field_229368_a_ = values[0];
      this.field_229369_b_ = values[1];
      this.field_229370_c_ = values[2];
      this.field_229371_d_ = values[3];
   }
   public void setX(float x) { this.field_229368_a_ = x; }
   public void setY(float y) { this.field_229369_b_ = y; }
   public void setZ(float z) { this.field_229370_c_ = z; }
   public void setW(float z) { this.field_229371_d_ = z; }
}