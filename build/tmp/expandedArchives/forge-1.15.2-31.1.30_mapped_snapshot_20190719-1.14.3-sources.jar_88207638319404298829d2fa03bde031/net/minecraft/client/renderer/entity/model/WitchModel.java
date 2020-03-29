package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitchModel<T extends Entity> extends VillagerModel<T> {
   private boolean holdingItem;
   private final ModelRenderer mole = (new ModelRenderer(this)).setTextureSize(64, 128);

   public WitchModel(float scale) {
      super(scale, 64, 128);
      this.mole.setRotationPoint(0.0F, -2.0F, 0.0F);
      this.mole.setTextureOffset(0, 0).func_228301_a_(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, -0.25F);
      this.villagerNose.addChild(this.mole);
      this.villagerHead = (new ModelRenderer(this)).setTextureSize(64, 128);
      this.villagerHead.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.villagerHead.setTextureOffset(0, 0).func_228301_a_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, scale);
      this.field_217151_b = (new ModelRenderer(this)).setTextureSize(64, 128);
      this.field_217151_b.setRotationPoint(-5.0F, -10.03125F, -5.0F);
      this.field_217151_b.setTextureOffset(0, 64).func_228300_a_(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F);
      this.villagerHead.addChild(this.field_217151_b);
      this.villagerHead.addChild(this.villagerNose);
      ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(64, 128);
      modelrenderer.setRotationPoint(1.75F, -4.0F, 2.0F);
      modelrenderer.setTextureOffset(0, 76).func_228300_a_(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F);
      modelrenderer.rotateAngleX = -0.05235988F;
      modelrenderer.rotateAngleZ = 0.02617994F;
      this.field_217151_b.addChild(modelrenderer);
      ModelRenderer modelrenderer1 = (new ModelRenderer(this)).setTextureSize(64, 128);
      modelrenderer1.setRotationPoint(1.75F, -4.0F, 2.0F);
      modelrenderer1.setTextureOffset(0, 87).func_228300_a_(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F);
      modelrenderer1.rotateAngleX = -0.10471976F;
      modelrenderer1.rotateAngleZ = 0.05235988F;
      modelrenderer.addChild(modelrenderer1);
      ModelRenderer modelrenderer2 = (new ModelRenderer(this)).setTextureSize(64, 128);
      modelrenderer2.setRotationPoint(1.75F, -2.0F, 2.0F);
      modelrenderer2.setTextureOffset(0, 95).func_228301_a_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.25F);
      modelrenderer2.rotateAngleX = -0.20943952F;
      modelrenderer2.rotateAngleZ = 0.10471976F;
      modelrenderer1.addChild(modelrenderer2);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      super.func_225597_a_(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
      this.villagerNose.setRotationPoint(0.0F, -2.0F, 0.0F);
      float f = 0.01F * (float)(p_225597_1_.getEntityId() % 10);
      this.villagerNose.rotateAngleX = MathHelper.sin((float)p_225597_1_.ticksExisted * f) * 4.5F * ((float)Math.PI / 180F);
      this.villagerNose.rotateAngleY = 0.0F;
      this.villagerNose.rotateAngleZ = MathHelper.cos((float)p_225597_1_.ticksExisted * f) * 2.5F * ((float)Math.PI / 180F);
      if (this.holdingItem) {
         this.villagerNose.setRotationPoint(0.0F, 1.0F, -1.5F);
         this.villagerNose.rotateAngleX = -0.9F;
      }

   }

   public ModelRenderer func_205073_b() {
      return this.villagerNose;
   }

   public void func_205074_a(boolean p_205074_1_) {
      this.holdingItem = p_205074_1_;
   }
}