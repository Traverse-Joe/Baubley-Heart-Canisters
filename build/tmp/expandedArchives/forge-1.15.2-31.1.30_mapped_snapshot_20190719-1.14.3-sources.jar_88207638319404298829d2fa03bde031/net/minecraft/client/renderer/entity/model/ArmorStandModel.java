package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArmorStandModel extends ArmorStandArmorModel {
   private final ModelRenderer standRightSide;
   private final ModelRenderer standLeftSide;
   private final ModelRenderer standWaist;
   private final ModelRenderer standBase;

   public ArmorStandModel() {
      this(0.0F);
   }

   public ArmorStandModel(float modelSize) {
      super(modelSize, 64, 64);
      this.bipedHead = new ModelRenderer(this, 0, 0);
      this.bipedHead.func_228301_a_(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, modelSize);
      this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bipedBody = new ModelRenderer(this, 0, 26);
      this.bipedBody.func_228301_a_(-6.0F, 0.0F, -1.5F, 12.0F, 3.0F, 3.0F, modelSize);
      this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bipedRightArm = new ModelRenderer(this, 24, 0);
      this.bipedRightArm.func_228301_a_(-2.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelSize);
      this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
      this.bipedLeftArm = new ModelRenderer(this, 32, 16);
      this.bipedLeftArm.mirror = true;
      this.bipedLeftArm.func_228301_a_(0.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelSize);
      this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
      this.bipedRightLeg = new ModelRenderer(this, 8, 0);
      this.bipedRightLeg.func_228301_a_(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, modelSize);
      this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 40, 16);
      this.bipedLeftLeg.mirror = true;
      this.bipedLeftLeg.func_228301_a_(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, modelSize);
      this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.standRightSide = new ModelRenderer(this, 16, 0);
      this.standRightSide.func_228301_a_(-3.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F, modelSize);
      this.standRightSide.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.standRightSide.showModel = true;
      this.standLeftSide = new ModelRenderer(this, 48, 16);
      this.standLeftSide.func_228301_a_(1.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F, modelSize);
      this.standLeftSide.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.standWaist = new ModelRenderer(this, 0, 48);
      this.standWaist.func_228301_a_(-4.0F, 10.0F, -1.0F, 8.0F, 2.0F, 2.0F, modelSize);
      this.standWaist.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.standBase = new ModelRenderer(this, 0, 32);
      this.standBase.func_228301_a_(-6.0F, 11.0F, -6.0F, 12.0F, 1.0F, 12.0F, modelSize);
      this.standBase.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.bipedHeadwear.showModel = false;
   }

   public void setLivingAnimations(ArmorStandEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
      this.standBase.rotateAngleX = 0.0F;
      this.standBase.rotateAngleY = ((float)Math.PI / 180F) * -MathHelper.func_219805_h(partialTick, entityIn.prevRotationYaw, entityIn.rotationYaw);
      this.standBase.rotateAngleZ = 0.0F;
   }

   public void func_225597_a_(ArmorStandEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      super.func_225597_a_(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
      this.bipedLeftArm.showModel = p_225597_1_.getShowArms();
      this.bipedRightArm.showModel = p_225597_1_.getShowArms();
      this.standBase.showModel = !p_225597_1_.hasNoBasePlate();
      this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.standRightSide.rotateAngleX = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getX();
      this.standRightSide.rotateAngleY = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getY();
      this.standRightSide.rotateAngleZ = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getZ();
      this.standLeftSide.rotateAngleX = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getX();
      this.standLeftSide.rotateAngleY = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getY();
      this.standLeftSide.rotateAngleZ = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getZ();
      this.standWaist.rotateAngleX = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getX();
      this.standWaist.rotateAngleY = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getY();
      this.standWaist.rotateAngleZ = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getZ();
   }

   protected Iterable<ModelRenderer> func_225600_b_() {
      return Iterables.concat(super.func_225600_b_(), ImmutableList.of(this.standRightSide, this.standLeftSide, this.standWaist, this.standBase));
   }

   public void func_225599_a_(HandSide p_225599_1_, MatrixStack p_225599_2_) {
      ModelRenderer modelrenderer = this.getArmForSide(p_225599_1_);
      boolean flag = modelrenderer.showModel;
      modelrenderer.showModel = true;
      super.func_225599_a_(p_225599_1_, p_225599_2_);
      modelrenderer.showModel = flag;
   }
}