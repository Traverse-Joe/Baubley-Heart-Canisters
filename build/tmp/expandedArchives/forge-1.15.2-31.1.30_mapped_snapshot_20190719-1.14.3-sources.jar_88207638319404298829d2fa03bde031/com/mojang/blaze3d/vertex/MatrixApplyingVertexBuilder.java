package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MatrixApplyingVertexBuilder extends DefaultColorVertexBuilder {
   private final IVertexBuilder field_227808_g_;
   private final Matrix4f field_227809_h_;
   private final Matrix3f field_227810_i_;
   private float field_227811_j_;
   private float field_227812_k_;
   private float field_227813_l_;
   private int field_227814_m_;
   private int field_227815_n_;
   private int field_227816_o_;
   private float field_227817_p_;
   private float field_227818_q_;
   private float field_227819_r_;

   public MatrixApplyingVertexBuilder(IVertexBuilder p_i225904_1_, MatrixStack.Entry p_i225904_2_) {
      this.field_227808_g_ = p_i225904_1_;
      this.field_227809_h_ = p_i225904_2_.func_227870_a_().func_226601_d_();
      this.field_227809_h_.func_226600_c_();
      this.field_227810_i_ = p_i225904_2_.func_227872_b_().func_226121_d_();
      this.field_227810_i_.func_226123_f_();
      this.func_227820_b_();
   }

   private void func_227820_b_() {
      this.field_227811_j_ = 0.0F;
      this.field_227812_k_ = 0.0F;
      this.field_227813_l_ = 0.0F;
      this.field_227814_m_ = 0;
      this.field_227815_n_ = 10;
      this.field_227816_o_ = 15728880;
      this.field_227817_p_ = 0.0F;
      this.field_227818_q_ = 1.0F;
      this.field_227819_r_ = 0.0F;
   }

   public void endVertex() {
      Vector3f vector3f = new Vector3f(this.field_227817_p_, this.field_227818_q_, this.field_227819_r_);
      vector3f.func_229188_a_(this.field_227810_i_);
      Direction direction = Direction.getFacingFromVector(vector3f.getX(), vector3f.getY(), vector3f.getZ());
      Vector4f vector4f = new Vector4f(this.field_227811_j_, this.field_227812_k_, this.field_227813_l_, 1.0F);
      vector4f.func_229372_a_(this.field_227809_h_);
      vector4f.func_195912_a(Vector3f.field_229181_d_.func_229187_a_(180.0F));
      vector4f.func_195912_a(Vector3f.field_229179_b_.func_229187_a_(-90.0F));
      vector4f.func_195912_a(direction.func_229384_a_());
      float f = -vector4f.getX();
      float f1 = -vector4f.getY();
      this.field_227808_g_.func_225582_a_((double)this.field_227811_j_, (double)this.field_227812_k_, (double)this.field_227813_l_).func_227885_a_(1.0F, 1.0F, 1.0F, 1.0F).func_225583_a_(f, f1).func_225585_a_(this.field_227814_m_, this.field_227815_n_).func_227886_a_(this.field_227816_o_).func_225584_a_(this.field_227817_p_, this.field_227818_q_, this.field_227819_r_).endVertex();
      this.func_227820_b_();
   }

   public IVertexBuilder func_225582_a_(double p_225582_1_, double p_225582_3_, double p_225582_5_) {
      this.field_227811_j_ = (float)p_225582_1_;
      this.field_227812_k_ = (float)p_225582_3_;
      this.field_227813_l_ = (float)p_225582_5_;
      return this;
   }

   public IVertexBuilder func_225586_a_(int p_225586_1_, int p_225586_2_, int p_225586_3_, int p_225586_4_) {
      return this;
   }

   public IVertexBuilder func_225583_a_(float p_225583_1_, float p_225583_2_) {
      return this;
   }

   public IVertexBuilder func_225585_a_(int p_225585_1_, int p_225585_2_) {
      this.field_227814_m_ = p_225585_1_;
      this.field_227815_n_ = p_225585_2_;
      return this;
   }

   public IVertexBuilder func_225587_b_(int p_225587_1_, int p_225587_2_) {
      this.field_227816_o_ = p_225587_1_ | p_225587_2_ << 16;
      return this;
   }

   public IVertexBuilder func_225584_a_(float p_225584_1_, float p_225584_2_, float p_225584_3_) {
      this.field_227817_p_ = p_225584_1_;
      this.field_227818_q_ = p_225584_2_;
      this.field_227819_r_ = p_225584_3_;
      return this;
   }
}