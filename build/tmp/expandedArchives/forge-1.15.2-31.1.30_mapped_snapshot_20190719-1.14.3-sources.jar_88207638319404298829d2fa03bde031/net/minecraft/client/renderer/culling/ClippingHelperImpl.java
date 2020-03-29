package net.minecraft.client.renderer.culling;

import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClippingHelperImpl {
   private final Vector4f[] field_228948_a_ = new Vector4f[6];
   private double field_228949_b_;
   private double field_228950_c_;
   private double field_228951_d_;

   public ClippingHelperImpl(Matrix4f p_i226026_1_, Matrix4f p_i226026_2_) {
      this.func_228956_a_(p_i226026_1_, p_i226026_2_);
   }

   public void func_228952_a_(double p_228952_1_, double p_228952_3_, double p_228952_5_) {
      this.field_228949_b_ = p_228952_1_;
      this.field_228950_c_ = p_228952_3_;
      this.field_228951_d_ = p_228952_5_;
   }

   private void func_228956_a_(Matrix4f p_228956_1_, Matrix4f p_228956_2_) {
      Matrix4f matrix4f = p_228956_2_.func_226601_d_();
      matrix4f.func_226595_a_(p_228956_1_);
      matrix4f.func_226602_e_();
      this.func_228955_a_(matrix4f, -1, 0, 0, 0);
      this.func_228955_a_(matrix4f, 1, 0, 0, 1);
      this.func_228955_a_(matrix4f, 0, -1, 0, 2);
      this.func_228955_a_(matrix4f, 0, 1, 0, 3);
      this.func_228955_a_(matrix4f, 0, 0, -1, 4);
      this.func_228955_a_(matrix4f, 0, 0, 1, 5);
   }

   private void func_228955_a_(Matrix4f p_228955_1_, int p_228955_2_, int p_228955_3_, int p_228955_4_, int p_228955_5_) {
      Vector4f vector4f = new Vector4f((float)p_228955_2_, (float)p_228955_3_, (float)p_228955_4_, 1.0F);
      vector4f.func_229372_a_(p_228955_1_);
      vector4f.func_229374_e_();
      this.field_228948_a_[p_228955_5_] = vector4f;
   }

   public boolean func_228957_a_(AxisAlignedBB p_228957_1_) {
      return this.func_228953_a_(p_228957_1_.minX, p_228957_1_.minY, p_228957_1_.minZ, p_228957_1_.maxX, p_228957_1_.maxY, p_228957_1_.maxZ);
   }

   private boolean func_228953_a_(double p_228953_1_, double p_228953_3_, double p_228953_5_, double p_228953_7_, double p_228953_9_, double p_228953_11_) {
      float f = (float)(p_228953_1_ - this.field_228949_b_);
      float f1 = (float)(p_228953_3_ - this.field_228950_c_);
      float f2 = (float)(p_228953_5_ - this.field_228951_d_);
      float f3 = (float)(p_228953_7_ - this.field_228949_b_);
      float f4 = (float)(p_228953_9_ - this.field_228950_c_);
      float f5 = (float)(p_228953_11_ - this.field_228951_d_);
      return this.func_228954_a_(f, f1, f2, f3, f4, f5);
   }

   private boolean func_228954_a_(float p_228954_1_, float p_228954_2_, float p_228954_3_, float p_228954_4_, float p_228954_5_, float p_228954_6_) {
      for(int i = 0; i < 6; ++i) {
         Vector4f vector4f = this.field_228948_a_[i];
         if (!(vector4f.func_229373_a_(new Vector4f(p_228954_1_, p_228954_2_, p_228954_3_, 1.0F)) > 0.0F) && !(vector4f.func_229373_a_(new Vector4f(p_228954_4_, p_228954_2_, p_228954_3_, 1.0F)) > 0.0F) && !(vector4f.func_229373_a_(new Vector4f(p_228954_1_, p_228954_5_, p_228954_3_, 1.0F)) > 0.0F) && !(vector4f.func_229373_a_(new Vector4f(p_228954_4_, p_228954_5_, p_228954_3_, 1.0F)) > 0.0F) && !(vector4f.func_229373_a_(new Vector4f(p_228954_1_, p_228954_2_, p_228954_6_, 1.0F)) > 0.0F) && !(vector4f.func_229373_a_(new Vector4f(p_228954_4_, p_228954_2_, p_228954_6_, 1.0F)) > 0.0F) && !(vector4f.func_229373_a_(new Vector4f(p_228954_1_, p_228954_5_, p_228954_6_, 1.0F)) > 0.0F) && !(vector4f.func_229373_a_(new Vector4f(p_228954_4_, p_228954_5_, p_228954_6_, 1.0F)) > 0.0F)) {
            return false;
         }
      }

      return true;
   }
}