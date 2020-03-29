package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerModel<T extends LivingEntity> extends BipedModel<T> {
   private List<ModelRenderer> field_228286_w_ = Lists.newArrayList();
   public final ModelRenderer bipedLeftArmwear;
   public final ModelRenderer bipedRightArmwear;
   public final ModelRenderer bipedLeftLegwear;
   public final ModelRenderer bipedRightLegwear;
   public final ModelRenderer bipedBodyWear;
   private final ModelRenderer bipedCape;
   private final ModelRenderer bipedDeadmau5Head;
   private final boolean smallArms;

   public PlayerModel(float modelSize, boolean smallArmsIn) {
      super(RenderType::func_228644_e_, modelSize, 0.0F, 64, 64);
      this.smallArms = smallArmsIn;
      this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
      this.bipedDeadmau5Head.func_228301_a_(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 1.0F, modelSize);
      this.bipedCape = new ModelRenderer(this, 0, 0);
      this.bipedCape.setTextureSize(64, 32);
      this.bipedCape.func_228301_a_(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, modelSize);
      if (smallArmsIn) {
         this.bipedLeftArm = new ModelRenderer(this, 32, 48);
         this.bipedLeftArm.func_228301_a_(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 40, 16);
         this.bipedRightArm.func_228301_a_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize);
         this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
         this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
         this.bipedLeftArmwear.func_228301_a_(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize + 0.25F);
         this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
         this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
         this.bipedRightArmwear.func_228301_a_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize + 0.25F);
         this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
      } else {
         this.bipedLeftArm = new ModelRenderer(this, 32, 48);
         this.bipedLeftArm.func_228301_a_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
         this.bipedLeftArmwear.func_228301_a_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
         this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
         this.bipedRightArmwear.func_228301_a_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
         this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
      }

      this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
      this.bipedLeftLeg.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
      this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
      this.bipedLeftLegwear.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
      this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
      this.bipedRightLegwear.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
      this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.bipedBodyWear = new ModelRenderer(this, 16, 32);
      this.bipedBodyWear.func_228301_a_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize + 0.25F);
      this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   protected Iterable<ModelRenderer> func_225600_b_() {
      return Iterables.concat(super.func_225600_b_(), ImmutableList.of(this.bipedLeftLegwear, this.bipedRightLegwear, this.bipedLeftArmwear, this.bipedRightArmwear, this.bipedBodyWear));
   }

   public void func_228287_a_(MatrixStack p_228287_1_, IVertexBuilder p_228287_2_, int p_228287_3_, int p_228287_4_) {
      this.bipedDeadmau5Head.copyModelAngles(this.bipedHead);
      this.bipedDeadmau5Head.rotationPointX = 0.0F;
      this.bipedDeadmau5Head.rotationPointY = 0.0F;
      this.bipedDeadmau5Head.func_228308_a_(p_228287_1_, p_228287_2_, p_228287_3_, p_228287_4_);
   }

   public void func_228289_b_(MatrixStack p_228289_1_, IVertexBuilder p_228289_2_, int p_228289_3_, int p_228289_4_) {
      this.bipedCape.func_228308_a_(p_228289_1_, p_228289_2_, p_228289_3_, p_228289_4_);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      super.func_225597_a_(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
      this.bipedLeftLegwear.copyModelAngles(this.bipedLeftLeg);
      this.bipedRightLegwear.copyModelAngles(this.bipedRightLeg);
      this.bipedLeftArmwear.copyModelAngles(this.bipedLeftArm);
      this.bipedRightArmwear.copyModelAngles(this.bipedRightArm);
      this.bipedBodyWear.copyModelAngles(this.bipedBody);
      if (p_225597_1_.isCrouching()) {
         this.bipedCape.rotationPointY = 2.0F;
      } else {
         this.bipedCape.rotationPointY = 0.0F;
      }

   }

   public void setVisible(boolean visible) {
      super.setVisible(visible);
      this.bipedLeftArmwear.showModel = visible;
      this.bipedRightArmwear.showModel = visible;
      this.bipedLeftLegwear.showModel = visible;
      this.bipedRightLegwear.showModel = visible;
      this.bipedBodyWear.showModel = visible;
      this.bipedCape.showModel = visible;
      this.bipedDeadmau5Head.showModel = visible;
   }

   public void func_225599_a_(HandSide p_225599_1_, MatrixStack p_225599_2_) {
      ModelRenderer modelrenderer = this.getArmForSide(p_225599_1_);
      if (this.smallArms) {
         float f = 0.5F * (float)(p_225599_1_ == HandSide.RIGHT ? 1 : -1);
         modelrenderer.rotationPointX += f;
         modelrenderer.func_228307_a_(p_225599_2_);
         modelrenderer.rotationPointX -= f;
      } else {
         modelrenderer.func_228307_a_(p_225599_2_);
      }

   }

   public ModelRenderer func_228288_a_(Random p_228288_1_) {
      return this.field_228286_w_.get(p_228288_1_.nextInt(this.field_228286_w_.size()));
   }

   public void accept(ModelRenderer p_accept_1_) {
      if (this.field_228286_w_ == null) {
         this.field_228286_w_ = Lists.newArrayList();
      }

      this.field_228286_w_.add(p_accept_1_);
   }
}