package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArmorStandArmorModel extends BipedModel<ArmorStandEntity> {
   public ArmorStandArmorModel(float modelSize) {
      this(modelSize, 64, 32);
   }

   protected ArmorStandArmorModel(float modelSize, int textureWidthIn, int textureHeightIn) {
      super(modelSize, 0.0F, textureWidthIn, textureHeightIn);
   }

   public void func_225597_a_(ArmorStandEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.bipedHead.rotateAngleX = ((float)Math.PI / 180F) * p_225597_1_.getHeadRotation().getX();
      this.bipedHead.rotateAngleY = ((float)Math.PI / 180F) * p_225597_1_.getHeadRotation().getY();
      this.bipedHead.rotateAngleZ = ((float)Math.PI / 180F) * p_225597_1_.getHeadRotation().getZ();
      this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
      this.bipedBody.rotateAngleX = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getX();
      this.bipedBody.rotateAngleY = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getY();
      this.bipedBody.rotateAngleZ = ((float)Math.PI / 180F) * p_225597_1_.getBodyRotation().getZ();
      this.bipedLeftArm.rotateAngleX = ((float)Math.PI / 180F) * p_225597_1_.getLeftArmRotation().getX();
      this.bipedLeftArm.rotateAngleY = ((float)Math.PI / 180F) * p_225597_1_.getLeftArmRotation().getY();
      this.bipedLeftArm.rotateAngleZ = ((float)Math.PI / 180F) * p_225597_1_.getLeftArmRotation().getZ();
      this.bipedRightArm.rotateAngleX = ((float)Math.PI / 180F) * p_225597_1_.getRightArmRotation().getX();
      this.bipedRightArm.rotateAngleY = ((float)Math.PI / 180F) * p_225597_1_.getRightArmRotation().getY();
      this.bipedRightArm.rotateAngleZ = ((float)Math.PI / 180F) * p_225597_1_.getRightArmRotation().getZ();
      this.bipedLeftLeg.rotateAngleX = ((float)Math.PI / 180F) * p_225597_1_.getLeftLegRotation().getX();
      this.bipedLeftLeg.rotateAngleY = ((float)Math.PI / 180F) * p_225597_1_.getLeftLegRotation().getY();
      this.bipedLeftLeg.rotateAngleZ = ((float)Math.PI / 180F) * p_225597_1_.getLeftLegRotation().getZ();
      this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
      this.bipedRightLeg.rotateAngleX = ((float)Math.PI / 180F) * p_225597_1_.getRightLegRotation().getX();
      this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 180F) * p_225597_1_.getRightLegRotation().getY();
      this.bipedRightLeg.rotateAngleZ = ((float)Math.PI / 180F) * p_225597_1_.getRightLegRotation().getZ();
      this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
      this.bipedHeadwear.copyModelAngles(this.bipedHead);
   }
}