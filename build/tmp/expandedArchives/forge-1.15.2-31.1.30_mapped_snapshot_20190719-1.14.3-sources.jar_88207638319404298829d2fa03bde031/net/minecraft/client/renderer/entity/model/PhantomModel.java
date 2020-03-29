package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhantomModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer field_203070_a;
   private final ModelRenderer field_203071_b;
   private final ModelRenderer field_203072_c;
   private final ModelRenderer field_203073_d;
   private final ModelRenderer field_203074_e;
   private final ModelRenderer field_204233_g;
   private final ModelRenderer field_204234_h;

   public PhantomModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.field_203070_a = new ModelRenderer(this, 0, 8);
      this.field_203070_a.func_228300_a_(-3.0F, -2.0F, -8.0F, 5.0F, 3.0F, 9.0F);
      this.field_204233_g = new ModelRenderer(this, 3, 20);
      this.field_204233_g.func_228300_a_(-2.0F, 0.0F, 0.0F, 3.0F, 2.0F, 6.0F);
      this.field_204233_g.setRotationPoint(0.0F, -2.0F, 1.0F);
      this.field_203070_a.addChild(this.field_204233_g);
      this.field_204234_h = new ModelRenderer(this, 4, 29);
      this.field_204234_h.func_228300_a_(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F);
      this.field_204234_h.setRotationPoint(0.0F, 0.5F, 6.0F);
      this.field_204233_g.addChild(this.field_204234_h);
      this.field_203071_b = new ModelRenderer(this, 23, 12);
      this.field_203071_b.func_228300_a_(0.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F);
      this.field_203071_b.setRotationPoint(2.0F, -2.0F, -8.0F);
      this.field_203072_c = new ModelRenderer(this, 16, 24);
      this.field_203072_c.func_228300_a_(0.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F);
      this.field_203072_c.setRotationPoint(6.0F, 0.0F, 0.0F);
      this.field_203071_b.addChild(this.field_203072_c);
      this.field_203073_d = new ModelRenderer(this, 23, 12);
      this.field_203073_d.mirror = true;
      this.field_203073_d.func_228300_a_(-6.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F);
      this.field_203073_d.setRotationPoint(-3.0F, -2.0F, -8.0F);
      this.field_203074_e = new ModelRenderer(this, 16, 24);
      this.field_203074_e.mirror = true;
      this.field_203074_e.func_228300_a_(-13.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F);
      this.field_203074_e.setRotationPoint(-6.0F, 0.0F, 0.0F);
      this.field_203073_d.addChild(this.field_203074_e);
      this.field_203071_b.rotateAngleZ = 0.1F;
      this.field_203072_c.rotateAngleZ = 0.1F;
      this.field_203073_d.rotateAngleZ = -0.1F;
      this.field_203074_e.rotateAngleZ = -0.1F;
      this.field_203070_a.rotateAngleX = -0.1F;
      ModelRenderer modelrenderer = new ModelRenderer(this, 0, 0);
      modelrenderer.func_228300_a_(-4.0F, -2.0F, -5.0F, 7.0F, 3.0F, 5.0F);
      modelrenderer.setRotationPoint(0.0F, 1.0F, -7.0F);
      modelrenderer.rotateAngleX = 0.2F;
      this.field_203070_a.addChild(modelrenderer);
      this.field_203070_a.addChild(this.field_203071_b);
      this.field_203070_a.addChild(this.field_203073_d);
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.field_203070_a);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      float f = ((float)(p_225597_1_.getEntityId() * 3) + p_225597_4_) * 0.13F;
      float f1 = 16.0F;
      this.field_203071_b.rotateAngleZ = MathHelper.cos(f) * 16.0F * ((float)Math.PI / 180F);
      this.field_203072_c.rotateAngleZ = MathHelper.cos(f) * 16.0F * ((float)Math.PI / 180F);
      this.field_203073_d.rotateAngleZ = -this.field_203071_b.rotateAngleZ;
      this.field_203074_e.rotateAngleZ = -this.field_203072_c.rotateAngleZ;
      this.field_204233_g.rotateAngleX = -(5.0F + MathHelper.cos(f * 2.0F) * 5.0F) * ((float)Math.PI / 180F);
      this.field_204234_h.rotateAngleX = -(5.0F + MathHelper.cos(f * 2.0F) * 5.0F) * ((float)Math.PI / 180F);
   }
}