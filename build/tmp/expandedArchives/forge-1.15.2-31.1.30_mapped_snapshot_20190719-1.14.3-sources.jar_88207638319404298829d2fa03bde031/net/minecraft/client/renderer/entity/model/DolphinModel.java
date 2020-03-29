package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DolphinModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer field_205082_b;
   private final ModelRenderer field_205083_c;
   private final ModelRenderer field_205084_d;

   public DolphinModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      float f = 18.0F;
      float f1 = -8.0F;
      this.field_205082_b = new ModelRenderer(this, 22, 0);
      this.field_205082_b.func_228300_a_(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 13.0F);
      this.field_205082_b.setRotationPoint(0.0F, 22.0F, -5.0F);
      ModelRenderer modelrenderer = new ModelRenderer(this, 51, 0);
      modelrenderer.func_228300_a_(-0.5F, 0.0F, 8.0F, 1.0F, 4.0F, 5.0F);
      modelrenderer.rotateAngleX = ((float)Math.PI / 3F);
      this.field_205082_b.addChild(modelrenderer);
      ModelRenderer modelrenderer1 = new ModelRenderer(this, 48, 20);
      modelrenderer1.mirror = true;
      modelrenderer1.func_228300_a_(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F);
      modelrenderer1.setRotationPoint(2.0F, -2.0F, 4.0F);
      modelrenderer1.rotateAngleX = ((float)Math.PI / 3F);
      modelrenderer1.rotateAngleZ = 2.0943952F;
      this.field_205082_b.addChild(modelrenderer1);
      ModelRenderer modelrenderer2 = new ModelRenderer(this, 48, 20);
      modelrenderer2.func_228300_a_(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F);
      modelrenderer2.setRotationPoint(-2.0F, -2.0F, 4.0F);
      modelrenderer2.rotateAngleX = ((float)Math.PI / 3F);
      modelrenderer2.rotateAngleZ = -2.0943952F;
      this.field_205082_b.addChild(modelrenderer2);
      this.field_205083_c = new ModelRenderer(this, 0, 19);
      this.field_205083_c.func_228300_a_(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 11.0F);
      this.field_205083_c.setRotationPoint(0.0F, -2.5F, 11.0F);
      this.field_205083_c.rotateAngleX = -0.10471976F;
      this.field_205082_b.addChild(this.field_205083_c);
      this.field_205084_d = new ModelRenderer(this, 19, 20);
      this.field_205084_d.func_228300_a_(-5.0F, -0.5F, 0.0F, 10.0F, 1.0F, 6.0F);
      this.field_205084_d.setRotationPoint(0.0F, 0.0F, 9.0F);
      this.field_205084_d.rotateAngleX = 0.0F;
      this.field_205083_c.addChild(this.field_205084_d);
      ModelRenderer modelrenderer3 = new ModelRenderer(this, 0, 0);
      modelrenderer3.func_228300_a_(-4.0F, -3.0F, -3.0F, 8.0F, 7.0F, 6.0F);
      modelrenderer3.setRotationPoint(0.0F, -4.0F, -3.0F);
      ModelRenderer modelrenderer4 = new ModelRenderer(this, 0, 13);
      modelrenderer4.func_228300_a_(-1.0F, 2.0F, -7.0F, 2.0F, 2.0F, 4.0F);
      modelrenderer3.addChild(modelrenderer4);
      this.field_205082_b.addChild(modelrenderer3);
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.field_205082_b);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.field_205082_b.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
      this.field_205082_b.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      if (Entity.func_213296_b(p_225597_1_.getMotion()) > 1.0E-7D) {
         this.field_205082_b.rotateAngleX += -0.05F + -0.05F * MathHelper.cos(p_225597_4_ * 0.3F);
         this.field_205083_c.rotateAngleX = -0.1F * MathHelper.cos(p_225597_4_ * 0.3F);
         this.field_205084_d.rotateAngleX = -0.2F * MathHelper.cos(p_225597_4_ * 0.3F);
      }

   }
}