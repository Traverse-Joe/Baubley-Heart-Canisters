package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowManModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer body;
   private final ModelRenderer bottomBody;
   private final ModelRenderer head;
   private final ModelRenderer rightHand;
   private final ModelRenderer leftHand;

   public SnowManModel() {
      float f = 4.0F;
      float f1 = 0.0F;
      this.head = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
      this.head.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, -0.5F);
      this.head.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.rightHand = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
      this.rightHand.func_228301_a_(-1.0F, 0.0F, -1.0F, 12.0F, 2.0F, 2.0F, -0.5F);
      this.rightHand.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.leftHand = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
      this.leftHand.func_228301_a_(-1.0F, 0.0F, -1.0F, 12.0F, 2.0F, 2.0F, -0.5F);
      this.leftHand.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.body = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 64);
      this.body.func_228301_a_(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, -0.5F);
      this.body.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.bottomBody = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 64);
      this.bottomBody.func_228301_a_(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, -0.5F);
      this.bottomBody.setRotationPoint(0.0F, 24.0F, 0.0F);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.head.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.head.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
      this.body.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F) * 0.25F;
      float f = MathHelper.sin(this.body.rotateAngleY);
      float f1 = MathHelper.cos(this.body.rotateAngleY);
      this.rightHand.rotateAngleZ = 1.0F;
      this.leftHand.rotateAngleZ = -1.0F;
      this.rightHand.rotateAngleY = 0.0F + this.body.rotateAngleY;
      this.leftHand.rotateAngleY = (float)Math.PI + this.body.rotateAngleY;
      this.rightHand.rotationPointX = f1 * 5.0F;
      this.rightHand.rotationPointZ = -f * 5.0F;
      this.leftHand.rotationPointX = -f1 * 5.0F;
      this.leftHand.rotationPointZ = f * 5.0F;
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.body, this.bottomBody, this.head, this.rightHand, this.leftHand);
   }

   public ModelRenderer func_205070_a() {
      return this.head;
   }
}