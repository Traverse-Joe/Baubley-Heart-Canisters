package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CodModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer body;
   private final ModelRenderer finTop;
   private final ModelRenderer head;
   private final ModelRenderer headFront;
   private final ModelRenderer finRight;
   private final ModelRenderer finLeft;
   private final ModelRenderer tail;

   public CodModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      int i = 22;
      this.body = new ModelRenderer(this, 0, 0);
      this.body.func_228300_a_(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 7.0F);
      this.body.setRotationPoint(0.0F, 22.0F, 0.0F);
      this.head = new ModelRenderer(this, 11, 0);
      this.head.func_228300_a_(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F);
      this.head.setRotationPoint(0.0F, 22.0F, 0.0F);
      this.headFront = new ModelRenderer(this, 0, 0);
      this.headFront.func_228300_a_(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F);
      this.headFront.setRotationPoint(0.0F, 22.0F, -3.0F);
      this.finRight = new ModelRenderer(this, 22, 1);
      this.finRight.func_228300_a_(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F);
      this.finRight.setRotationPoint(-1.0F, 23.0F, 0.0F);
      this.finRight.rotateAngleZ = (-(float)Math.PI / 4F);
      this.finLeft = new ModelRenderer(this, 22, 4);
      this.finLeft.func_228300_a_(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F);
      this.finLeft.setRotationPoint(1.0F, 23.0F, 0.0F);
      this.finLeft.rotateAngleZ = ((float)Math.PI / 4F);
      this.tail = new ModelRenderer(this, 22, 3);
      this.tail.func_228300_a_(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F);
      this.tail.setRotationPoint(0.0F, 22.0F, 7.0F);
      this.finTop = new ModelRenderer(this, 20, -6);
      this.finTop.func_228300_a_(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 6.0F);
      this.finTop.setRotationPoint(0.0F, 20.0F, 0.0F);
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.body, this.head, this.headFront, this.finRight, this.finLeft, this.tail, this.finTop);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      float f = 1.0F;
      if (!p_225597_1_.isInWater()) {
         f = 1.5F;
      }

      this.tail.rotateAngleY = -f * 0.45F * MathHelper.sin(0.6F * p_225597_4_);
   }
}