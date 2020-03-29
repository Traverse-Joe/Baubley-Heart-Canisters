package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurtleModel<T extends TurtleEntity> extends QuadrupedModel<T> {
   private final ModelRenderer field_203078_i;

   public TurtleModel(float p_i48834_1_) {
      super(12, p_i48834_1_, true, 120.0F, 0.0F, 9.0F, 6.0F, 120);
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.headModel = new ModelRenderer(this, 3, 0);
      this.headModel.func_228301_a_(-3.0F, -1.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F);
      this.headModel.setRotationPoint(0.0F, 19.0F, -10.0F);
      this.field_78148_b = new ModelRenderer(this);
      this.field_78148_b.setTextureOffset(7, 37).func_228301_a_(-9.5F, 3.0F, -10.0F, 19.0F, 20.0F, 6.0F, 0.0F);
      this.field_78148_b.setTextureOffset(31, 1).func_228301_a_(-5.5F, 3.0F, -13.0F, 11.0F, 18.0F, 3.0F, 0.0F);
      this.field_78148_b.setRotationPoint(0.0F, 11.0F, -10.0F);
      this.field_203078_i = new ModelRenderer(this);
      this.field_203078_i.setTextureOffset(70, 33).func_228301_a_(-4.5F, 3.0F, -14.0F, 9.0F, 18.0F, 1.0F, 0.0F);
      this.field_203078_i.setRotationPoint(0.0F, 11.0F, -10.0F);
      int i = 1;
      this.field_78149_c = new ModelRenderer(this, 1, 23);
      this.field_78149_c.func_228301_a_(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
      this.field_78149_c.setRotationPoint(-3.5F, 22.0F, 11.0F);
      this.field_78146_d = new ModelRenderer(this, 1, 12);
      this.field_78146_d.func_228301_a_(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
      this.field_78146_d.setRotationPoint(3.5F, 22.0F, 11.0F);
      this.field_78147_e = new ModelRenderer(this, 27, 30);
      this.field_78147_e.func_228301_a_(-13.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
      this.field_78147_e.setRotationPoint(-5.0F, 21.0F, -4.0F);
      this.field_78144_f = new ModelRenderer(this, 27, 24);
      this.field_78144_f.func_228301_a_(0.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
      this.field_78144_f.setRotationPoint(5.0F, 21.0F, -4.0F);
   }

   protected Iterable<ModelRenderer> func_225600_b_() {
      return Iterables.concat(super.func_225600_b_(), ImmutableList.of(this.field_203078_i));
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      super.func_225597_a_(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
      this.field_78149_c.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F * 0.6F) * 0.5F * p_225597_3_;
      this.field_78146_d.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * p_225597_3_;
      this.field_78147_e.rotateAngleZ = MathHelper.cos(p_225597_2_ * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * p_225597_3_;
      this.field_78144_f.rotateAngleZ = MathHelper.cos(p_225597_2_ * 0.6662F * 0.6F) * 0.5F * p_225597_3_;
      this.field_78147_e.rotateAngleX = 0.0F;
      this.field_78144_f.rotateAngleX = 0.0F;
      this.field_78147_e.rotateAngleY = 0.0F;
      this.field_78144_f.rotateAngleY = 0.0F;
      this.field_78149_c.rotateAngleY = 0.0F;
      this.field_78146_d.rotateAngleY = 0.0F;
      this.field_203078_i.rotateAngleX = ((float)Math.PI / 2F);
      if (!p_225597_1_.isInWater() && p_225597_1_.onGround) {
         float f = p_225597_1_.isDigging() ? 4.0F : 1.0F;
         float f1 = p_225597_1_.isDigging() ? 2.0F : 1.0F;
         float f2 = 5.0F;
         this.field_78147_e.rotateAngleY = MathHelper.cos(f * p_225597_2_ * 5.0F + (float)Math.PI) * 8.0F * p_225597_3_ * f1;
         this.field_78147_e.rotateAngleZ = 0.0F;
         this.field_78144_f.rotateAngleY = MathHelper.cos(f * p_225597_2_ * 5.0F) * 8.0F * p_225597_3_ * f1;
         this.field_78144_f.rotateAngleZ = 0.0F;
         this.field_78149_c.rotateAngleY = MathHelper.cos(p_225597_2_ * 5.0F + (float)Math.PI) * 3.0F * p_225597_3_;
         this.field_78149_c.rotateAngleX = 0.0F;
         this.field_78146_d.rotateAngleY = MathHelper.cos(p_225597_2_ * 5.0F) * 3.0F * p_225597_3_;
         this.field_78146_d.rotateAngleX = 0.0F;
      }

      this.field_203078_i.showModel = !this.isChild && p_225597_1_.hasEgg();
   }

   public void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
      boolean flag = this.field_203078_i.showModel;
      if (flag) {
         p_225598_1_.func_227860_a_();
         p_225598_1_.func_227861_a_(0.0D, (double)-0.08F, 0.0D);
      }

      super.func_225598_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
      if (flag) {
         p_225598_1_.func_227865_b_();
      }

   }
}