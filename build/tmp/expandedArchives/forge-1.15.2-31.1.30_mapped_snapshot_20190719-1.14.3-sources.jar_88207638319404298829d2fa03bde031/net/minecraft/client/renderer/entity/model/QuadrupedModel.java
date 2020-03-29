package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QuadrupedModel<T extends Entity> extends AgeableModel<T> {
   protected ModelRenderer headModel = new ModelRenderer(this, 0, 0);
   protected ModelRenderer field_78148_b;
   protected ModelRenderer field_78149_c;
   protected ModelRenderer field_78146_d;
   protected ModelRenderer field_78147_e;
   protected ModelRenderer field_78144_f;

   public QuadrupedModel(int p_i225948_1_, float p_i225948_2_, boolean p_i225948_3_, float p_i225948_4_, float p_i225948_5_, float p_i225948_6_, float p_i225948_7_, int p_i225948_8_) {
      super(p_i225948_3_, p_i225948_4_, p_i225948_5_, p_i225948_6_, p_i225948_7_, (float)p_i225948_8_);
      this.headModel.func_228301_a_(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, p_i225948_2_);
      this.headModel.setRotationPoint(0.0F, (float)(18 - p_i225948_1_), -6.0F);
      this.field_78148_b = new ModelRenderer(this, 28, 8);
      this.field_78148_b.func_228301_a_(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, p_i225948_2_);
      this.field_78148_b.setRotationPoint(0.0F, (float)(17 - p_i225948_1_), 2.0F);
      this.field_78149_c = new ModelRenderer(this, 0, 16);
      this.field_78149_c.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, (float)p_i225948_1_, 4.0F, p_i225948_2_);
      this.field_78149_c.setRotationPoint(-3.0F, (float)(24 - p_i225948_1_), 7.0F);
      this.field_78146_d = new ModelRenderer(this, 0, 16);
      this.field_78146_d.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, (float)p_i225948_1_, 4.0F, p_i225948_2_);
      this.field_78146_d.setRotationPoint(3.0F, (float)(24 - p_i225948_1_), 7.0F);
      this.field_78147_e = new ModelRenderer(this, 0, 16);
      this.field_78147_e.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, (float)p_i225948_1_, 4.0F, p_i225948_2_);
      this.field_78147_e.setRotationPoint(-3.0F, (float)(24 - p_i225948_1_), -5.0F);
      this.field_78144_f = new ModelRenderer(this, 0, 16);
      this.field_78144_f.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, (float)p_i225948_1_, 4.0F, p_i225948_2_);
      this.field_78144_f.setRotationPoint(3.0F, (float)(24 - p_i225948_1_), -5.0F);
   }

   protected Iterable<ModelRenderer> func_225602_a_() {
      return ImmutableList.of(this.headModel);
   }

   protected Iterable<ModelRenderer> func_225600_b_() {
      return ImmutableList.of(this.field_78148_b, this.field_78149_c, this.field_78146_d, this.field_78147_e, this.field_78144_f);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.headModel.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
      this.headModel.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.field_78148_b.rotateAngleX = ((float)Math.PI / 2F);
      this.field_78149_c.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
      this.field_78146_d.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
      this.field_78147_e.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
      this.field_78144_f.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
   }
}