package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronGolemModel<T extends IronGolemEntity> extends SegmentedModel<T> {
   private final ModelRenderer field_78178_a;
   private final ModelRenderer field_78176_b;
   private final ModelRenderer field_78177_c;
   private final ModelRenderer field_78174_d;
   private final ModelRenderer ironGolemLeftLeg;
   private final ModelRenderer ironGolemRightLeg;

   public IronGolemModel() {
      int i = 128;
      int j = 128;
      this.field_78178_a = (new ModelRenderer(this)).setTextureSize(128, 128);
      this.field_78178_a.setRotationPoint(0.0F, -7.0F, -2.0F);
      this.field_78178_a.setTextureOffset(0, 0).func_228301_a_(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F, 0.0F);
      this.field_78178_a.setTextureOffset(24, 0).func_228301_a_(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F, 0.0F);
      this.field_78176_b = (new ModelRenderer(this)).setTextureSize(128, 128);
      this.field_78176_b.setRotationPoint(0.0F, -7.0F, 0.0F);
      this.field_78176_b.setTextureOffset(0, 40).func_228301_a_(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.0F);
      this.field_78176_b.setTextureOffset(0, 70).func_228301_a_(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.5F);
      this.field_78177_c = (new ModelRenderer(this)).setTextureSize(128, 128);
      this.field_78177_c.setRotationPoint(0.0F, -7.0F, 0.0F);
      this.field_78177_c.setTextureOffset(60, 21).func_228301_a_(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
      this.field_78174_d = (new ModelRenderer(this)).setTextureSize(128, 128);
      this.field_78174_d.setRotationPoint(0.0F, -7.0F, 0.0F);
      this.field_78174_d.setTextureOffset(60, 58).func_228301_a_(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
      this.ironGolemLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(128, 128);
      this.ironGolemLeftLeg.setRotationPoint(-4.0F, 11.0F, 0.0F);
      this.ironGolemLeftLeg.setTextureOffset(37, 0).func_228301_a_(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);
      this.ironGolemRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(128, 128);
      this.ironGolemRightLeg.mirror = true;
      this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 11.0F, 0.0F);
      this.ironGolemRightLeg.func_228301_a_(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.field_78178_a, this.field_78176_b, this.ironGolemLeftLeg, this.ironGolemRightLeg, this.field_78177_c, this.field_78174_d);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.field_78178_a.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.field_78178_a.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
      this.ironGolemLeftLeg.rotateAngleX = -1.5F * this.triangleWave(p_225597_2_, 13.0F) * p_225597_3_;
      this.ironGolemRightLeg.rotateAngleX = 1.5F * this.triangleWave(p_225597_2_, 13.0F) * p_225597_3_;
      this.ironGolemLeftLeg.rotateAngleY = 0.0F;
      this.ironGolemRightLeg.rotateAngleY = 0.0F;
   }

   public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
      int i = entityIn.getAttackTimer();
      if (i > 0) {
         this.field_78177_c.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float)i - partialTick, 10.0F);
         this.field_78174_d.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float)i - partialTick, 10.0F);
      } else {
         int j = entityIn.getHoldRoseTick();
         if (j > 0) {
            this.field_78177_c.rotateAngleX = -0.8F + 0.025F * this.triangleWave((float)j, 70.0F);
            this.field_78174_d.rotateAngleX = 0.0F;
         } else {
            this.field_78177_c.rotateAngleX = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
            this.field_78174_d.rotateAngleX = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
         }
      }

   }

   private float triangleWave(float p_78172_1_, float p_78172_2_) {
      return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
   }

   public ModelRenderer func_205071_a() {
      return this.field_78177_c;
   }
}