package net.minecraft.client.renderer;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class Quaternion {
   public static final Quaternion field_227060_a_ = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
   private float field_227061_b_;
   private float field_227062_c_;
   private float field_227063_d_;
   private float field_227064_e_;

   public Quaternion(float x, float y, float z, float w) {
      this.field_227061_b_ = x;
      this.field_227062_c_ = y;
      this.field_227063_d_ = z;
      this.field_227064_e_ = w;
   }

   public Quaternion(Vector3f axis, float angle, boolean degrees) {
      if (degrees) {
         angle *= ((float)Math.PI / 180F);
      }

      float f = func_214903_b(angle / 2.0F);
      this.field_227061_b_ = axis.getX() * f;
      this.field_227062_c_ = axis.getY() * f;
      this.field_227063_d_ = axis.getZ() * f;
      this.field_227064_e_ = func_214904_a(angle / 2.0F);
   }

   @OnlyIn(Dist.CLIENT)
   public Quaternion(float xAngle, float yAngle, float zAngle, boolean degrees) {
      if (degrees) {
         xAngle *= ((float)Math.PI / 180F);
         yAngle *= ((float)Math.PI / 180F);
         zAngle *= ((float)Math.PI / 180F);
      }

      float f = func_214903_b(0.5F * xAngle);
      float f1 = func_214904_a(0.5F * xAngle);
      float f2 = func_214903_b(0.5F * yAngle);
      float f3 = func_214904_a(0.5F * yAngle);
      float f4 = func_214903_b(0.5F * zAngle);
      float f5 = func_214904_a(0.5F * zAngle);
      this.field_227061_b_ = f * f3 * f5 + f1 * f2 * f4;
      this.field_227062_c_ = f1 * f2 * f5 - f * f3 * f4;
      this.field_227063_d_ = f * f2 * f5 + f1 * f3 * f4;
      this.field_227064_e_ = f1 * f3 * f5 - f * f2 * f4;
   }

   public Quaternion(Quaternion quaternionIn) {
      this.field_227061_b_ = quaternionIn.field_227061_b_;
      this.field_227062_c_ = quaternionIn.field_227062_c_;
      this.field_227063_d_ = quaternionIn.field_227063_d_;
      this.field_227064_e_ = quaternionIn.field_227064_e_;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         Quaternion quaternion = (Quaternion)p_equals_1_;
         if (Float.compare(quaternion.field_227061_b_, this.field_227061_b_) != 0) {
            return false;
         } else if (Float.compare(quaternion.field_227062_c_, this.field_227062_c_) != 0) {
            return false;
         } else if (Float.compare(quaternion.field_227063_d_, this.field_227063_d_) != 0) {
            return false;
         } else {
            return Float.compare(quaternion.field_227064_e_, this.field_227064_e_) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = Float.floatToIntBits(this.field_227061_b_);
      i = 31 * i + Float.floatToIntBits(this.field_227062_c_);
      i = 31 * i + Float.floatToIntBits(this.field_227063_d_);
      i = 31 * i + Float.floatToIntBits(this.field_227064_e_);
      return i;
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("Quaternion[").append(this.getW()).append(" + ");
      stringbuilder.append(this.getX()).append("i + ");
      stringbuilder.append(this.getY()).append("j + ");
      stringbuilder.append(this.getZ()).append("k]");
      return stringbuilder.toString();
   }

   public float getX() {
      return this.field_227061_b_;
   }

   public float getY() {
      return this.field_227062_c_;
   }

   public float getZ() {
      return this.field_227063_d_;
   }

   public float getW() {
      return this.field_227064_e_;
   }

   public void multiply(Quaternion quaternionIn) {
      float f = this.getX();
      float f1 = this.getY();
      float f2 = this.getZ();
      float f3 = this.getW();
      float f4 = quaternionIn.getX();
      float f5 = quaternionIn.getY();
      float f6 = quaternionIn.getZ();
      float f7 = quaternionIn.getW();
      this.field_227061_b_ = f3 * f4 + f * f7 + f1 * f6 - f2 * f5;
      this.field_227062_c_ = f3 * f5 - f * f6 + f1 * f7 + f2 * f4;
      this.field_227063_d_ = f3 * f6 + f * f5 - f1 * f4 + f2 * f7;
      this.field_227064_e_ = f3 * f7 - f * f4 - f1 * f5 - f2 * f6;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_227065_a_(float p_227065_1_) {
      this.field_227061_b_ *= p_227065_1_;
      this.field_227062_c_ *= p_227065_1_;
      this.field_227063_d_ *= p_227065_1_;
      this.field_227064_e_ *= p_227065_1_;
   }

   public void conjugate() {
      this.field_227061_b_ = -this.field_227061_b_;
      this.field_227062_c_ = -this.field_227062_c_;
      this.field_227063_d_ = -this.field_227063_d_;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_227066_a_(float p_227066_1_, float p_227066_2_, float p_227066_3_, float p_227066_4_) {
      this.field_227061_b_ = p_227066_1_;
      this.field_227062_c_ = p_227066_2_;
      this.field_227063_d_ = p_227066_3_;
      this.field_227064_e_ = p_227066_4_;
   }

   private static float func_214904_a(float p_214904_0_) {
      return (float)Math.cos((double)p_214904_0_);
   }

   private static float func_214903_b(float p_214903_0_) {
      return (float)Math.sin((double)p_214903_0_);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_227067_f_() {
      float f = this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ() + this.getW() * this.getW();
      if (f > 1.0E-6F) {
         float f1 = MathHelper.func_226165_i_(f);
         this.field_227061_b_ *= f1;
         this.field_227062_c_ *= f1;
         this.field_227063_d_ *= f1;
         this.field_227064_e_ *= f1;
      } else {
         this.field_227061_b_ = 0.0F;
         this.field_227062_c_ = 0.0F;
         this.field_227063_d_ = 0.0F;
         this.field_227064_e_ = 0.0F;
      }

   }

   @OnlyIn(Dist.CLIENT)
   public Quaternion func_227068_g_() {
      return new Quaternion(this);
   }
}