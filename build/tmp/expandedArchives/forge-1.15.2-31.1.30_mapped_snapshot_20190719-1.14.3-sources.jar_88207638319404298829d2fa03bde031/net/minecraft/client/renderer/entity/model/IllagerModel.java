package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IllagerModel<T extends AbstractIllagerEntity> extends SegmentedModel<T> implements IHasArm, IHasHead {
   private final ModelRenderer head;
   private final ModelRenderer hat;
   private final ModelRenderer body;
   private final ModelRenderer arms;
   private final ModelRenderer field_217143_g;
   private final ModelRenderer field_217144_h;
   private final ModelRenderer rightArm;
   private final ModelRenderer leftArm;
   private float field_217145_m;

   public IllagerModel(float scaleFactor, float p_i47227_2_, int textureWidthIn, int textureHeightIn) {
      this.head = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
      this.head.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
      this.head.setTextureOffset(0, 0).func_228301_a_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, scaleFactor);
      this.hat = (new ModelRenderer(this, 32, 0)).setTextureSize(textureWidthIn, textureHeightIn);
      this.hat.func_228301_a_(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, scaleFactor + 0.45F);
      this.head.addChild(this.hat);
      this.hat.showModel = false;
      ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
      modelrenderer.setRotationPoint(0.0F, p_i47227_2_ - 2.0F, 0.0F);
      modelrenderer.setTextureOffset(24, 0).func_228301_a_(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, scaleFactor);
      this.head.addChild(modelrenderer);
      this.body = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
      this.body.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
      this.body.setTextureOffset(16, 20).func_228301_a_(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, scaleFactor);
      this.body.setTextureOffset(0, 38).func_228301_a_(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, scaleFactor + 0.5F);
      this.arms = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
      this.arms.setRotationPoint(0.0F, 0.0F + p_i47227_2_ + 2.0F, 0.0F);
      this.arms.setTextureOffset(44, 22).func_228301_a_(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, scaleFactor);
      ModelRenderer modelrenderer1 = (new ModelRenderer(this, 44, 22)).setTextureSize(textureWidthIn, textureHeightIn);
      modelrenderer1.mirror = true;
      modelrenderer1.func_228301_a_(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, scaleFactor);
      this.arms.addChild(modelrenderer1);
      this.arms.setTextureOffset(40, 38).func_228301_a_(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, scaleFactor);
      this.field_217143_g = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_217143_g.setRotationPoint(-2.0F, 12.0F + p_i47227_2_, 0.0F);
      this.field_217143_g.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scaleFactor);
      this.field_217144_h = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_217144_h.mirror = true;
      this.field_217144_h.setRotationPoint(2.0F, 12.0F + p_i47227_2_, 0.0F);
      this.field_217144_h.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scaleFactor);
      this.rightArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
      this.rightArm.func_228301_a_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scaleFactor);
      this.rightArm.setRotationPoint(-5.0F, 2.0F + p_i47227_2_, 0.0F);
      this.leftArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
      this.leftArm.mirror = true;
      this.leftArm.func_228301_a_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scaleFactor);
      this.leftArm.setRotationPoint(5.0F, 2.0F + p_i47227_2_, 0.0F);
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.head, this.body, this.field_217143_g, this.field_217144_h, this.arms, this.rightArm, this.leftArm);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.head.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.head.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
      this.arms.rotationPointY = 3.0F;
      this.arms.rotationPointZ = -1.0F;
      this.arms.rotateAngleX = -0.75F;
      if (this.isSitting) {
         this.rightArm.rotateAngleX = (-(float)Math.PI / 5F);
         this.rightArm.rotateAngleY = 0.0F;
         this.rightArm.rotateAngleZ = 0.0F;
         this.leftArm.rotateAngleX = (-(float)Math.PI / 5F);
         this.leftArm.rotateAngleY = 0.0F;
         this.leftArm.rotateAngleZ = 0.0F;
         this.field_217143_g.rotateAngleX = -1.4137167F;
         this.field_217143_g.rotateAngleY = ((float)Math.PI / 10F);
         this.field_217143_g.rotateAngleZ = 0.07853982F;
         this.field_217144_h.rotateAngleX = -1.4137167F;
         this.field_217144_h.rotateAngleY = (-(float)Math.PI / 10F);
         this.field_217144_h.rotateAngleZ = -0.07853982F;
      } else {
         this.rightArm.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 2.0F * p_225597_3_ * 0.5F;
         this.rightArm.rotateAngleY = 0.0F;
         this.rightArm.rotateAngleZ = 0.0F;
         this.leftArm.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 2.0F * p_225597_3_ * 0.5F;
         this.leftArm.rotateAngleY = 0.0F;
         this.leftArm.rotateAngleZ = 0.0F;
         this.field_217143_g.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_ * 0.5F;
         this.field_217143_g.rotateAngleY = 0.0F;
         this.field_217143_g.rotateAngleZ = 0.0F;
         this.field_217144_h.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_ * 0.5F;
         this.field_217144_h.rotateAngleY = 0.0F;
         this.field_217144_h.rotateAngleZ = 0.0F;
      }

      AbstractIllagerEntity.ArmPose abstractillagerentity$armpose = p_225597_1_.getArmPose();
      if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.ATTACKING) {
         float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
         float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
         this.rightArm.rotateAngleZ = 0.0F;
         this.leftArm.rotateAngleZ = 0.0F;
         this.rightArm.rotateAngleY = 0.15707964F;
         this.leftArm.rotateAngleY = -0.15707964F;
         if (p_225597_1_.getPrimaryHand() == HandSide.RIGHT) {
            this.rightArm.rotateAngleX = -1.8849558F + MathHelper.cos(p_225597_4_ * 0.09F) * 0.15F;
            this.leftArm.rotateAngleX = -0.0F + MathHelper.cos(p_225597_4_ * 0.19F) * 0.5F;
            this.rightArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
            this.leftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
         } else {
            this.rightArm.rotateAngleX = -0.0F + MathHelper.cos(p_225597_4_ * 0.19F) * 0.5F;
            this.leftArm.rotateAngleX = -1.8849558F + MathHelper.cos(p_225597_4_ * 0.09F) * 0.15F;
            this.rightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
            this.leftArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
         }

         this.rightArm.rotateAngleZ += MathHelper.cos(p_225597_4_ * 0.09F) * 0.05F + 0.05F;
         this.leftArm.rotateAngleZ -= MathHelper.cos(p_225597_4_ * 0.09F) * 0.05F + 0.05F;
         this.rightArm.rotateAngleX += MathHelper.sin(p_225597_4_ * 0.067F) * 0.05F;
         this.leftArm.rotateAngleX -= MathHelper.sin(p_225597_4_ * 0.067F) * 0.05F;
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.SPELLCASTING) {
         this.rightArm.rotationPointZ = 0.0F;
         this.rightArm.rotationPointX = -5.0F;
         this.leftArm.rotationPointZ = 0.0F;
         this.leftArm.rotationPointX = 5.0F;
         this.rightArm.rotateAngleX = MathHelper.cos(p_225597_4_ * 0.6662F) * 0.25F;
         this.leftArm.rotateAngleX = MathHelper.cos(p_225597_4_ * 0.6662F) * 0.25F;
         this.rightArm.rotateAngleZ = 2.3561945F;
         this.leftArm.rotateAngleZ = -2.3561945F;
         this.rightArm.rotateAngleY = 0.0F;
         this.leftArm.rotateAngleY = 0.0F;
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.BOW_AND_ARROW) {
         this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY;
         this.rightArm.rotateAngleX = (-(float)Math.PI / 2F) + this.head.rotateAngleX;
         this.leftArm.rotateAngleX = -0.9424779F + this.head.rotateAngleX;
         this.leftArm.rotateAngleY = this.head.rotateAngleY - 0.4F;
         this.leftArm.rotateAngleZ = ((float)Math.PI / 2F);
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD) {
         this.rightArm.rotateAngleY = -0.3F + this.head.rotateAngleY;
         this.leftArm.rotateAngleY = 0.6F + this.head.rotateAngleY;
         this.rightArm.rotateAngleX = (-(float)Math.PI / 2F) + this.head.rotateAngleX + 0.1F;
         this.leftArm.rotateAngleX = -1.5F + this.head.rotateAngleX;
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.CROSSBOW_CHARGE) {
         this.rightArm.rotateAngleY = -0.8F;
         this.rightArm.rotateAngleX = -0.97079635F;
         this.leftArm.rotateAngleX = -0.97079635F;
         float f2 = MathHelper.clamp(this.field_217145_m, 0.0F, 25.0F);
         this.leftArm.rotateAngleY = MathHelper.lerp(f2 / 25.0F, 0.4F, 0.85F);
         this.leftArm.rotateAngleX = MathHelper.lerp(f2 / 25.0F, this.leftArm.rotateAngleX, (-(float)Math.PI / 2F));
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.CELEBRATING) {
         this.rightArm.rotationPointZ = 0.0F;
         this.rightArm.rotationPointX = -5.0F;
         this.rightArm.rotateAngleX = MathHelper.cos(p_225597_4_ * 0.6662F) * 0.05F;
         this.rightArm.rotateAngleZ = 2.670354F;
         this.rightArm.rotateAngleY = 0.0F;
         this.leftArm.rotationPointZ = 0.0F;
         this.leftArm.rotationPointX = 5.0F;
         this.leftArm.rotateAngleX = MathHelper.cos(p_225597_4_ * 0.6662F) * 0.05F;
         this.leftArm.rotateAngleZ = -2.3561945F;
         this.leftArm.rotateAngleY = 0.0F;
      }

      boolean flag = abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.CROSSED;
      this.arms.showModel = flag;
      this.leftArm.showModel = !flag;
      this.rightArm.showModel = !flag;
   }

   public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
      this.field_217145_m = (float)entityIn.getItemInUseMaxCount();
      super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
   }

   private ModelRenderer getArm(HandSide p_191216_1_) {
      return p_191216_1_ == HandSide.LEFT ? this.leftArm : this.rightArm;
   }

   public ModelRenderer func_205062_a() {
      return this.hat;
   }

   public ModelRenderer func_205072_a() {
      return this.head;
   }

   public void func_225599_a_(HandSide p_225599_1_, MatrixStack p_225599_2_) {
      this.getArm(p_225599_1_).func_228307_a_(p_225599_2_);
   }
}