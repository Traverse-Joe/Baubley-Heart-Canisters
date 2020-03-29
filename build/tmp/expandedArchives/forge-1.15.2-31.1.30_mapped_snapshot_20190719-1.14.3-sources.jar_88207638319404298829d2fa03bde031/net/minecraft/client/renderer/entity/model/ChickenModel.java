package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChickenModel<T extends Entity> extends AgeableModel<T> {
   private final ModelRenderer head;
   private final ModelRenderer bill;
   private final ModelRenderer chin;
   private final ModelRenderer body;
   private final ModelRenderer rightWing;
   private final ModelRenderer leftWing;
   private final ModelRenderer field_78137_g;
   private final ModelRenderer field_78143_h;

   public ChickenModel() {
      int i = 16;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_228301_a_(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F);
      this.head.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.field_78137_g = new ModelRenderer(this, 14, 0);
      this.field_78137_g.func_228301_a_(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F);
      this.field_78137_g.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.field_78143_h = new ModelRenderer(this, 14, 4);
      this.field_78143_h.func_228301_a_(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F);
      this.field_78143_h.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.bill = new ModelRenderer(this, 0, 9);
      this.bill.func_228301_a_(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F);
      this.bill.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.chin = new ModelRenderer(this, 26, 0);
      this.chin.func_228300_a_(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
      this.chin.setRotationPoint(-2.0F, 19.0F, 1.0F);
      this.body = new ModelRenderer(this, 26, 0);
      this.body.func_228300_a_(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
      this.body.setRotationPoint(1.0F, 19.0F, 1.0F);
      this.rightWing = new ModelRenderer(this, 24, 13);
      this.rightWing.func_228300_a_(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
      this.rightWing.setRotationPoint(-4.0F, 13.0F, 0.0F);
      this.leftWing = new ModelRenderer(this, 24, 13);
      this.leftWing.func_228300_a_(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
      this.leftWing.setRotationPoint(4.0F, 13.0F, 0.0F);
   }

   protected Iterable<ModelRenderer> func_225602_a_() {
      return ImmutableList.of(this.head, this.field_78137_g, this.field_78143_h);
   }

   protected Iterable<ModelRenderer> func_225600_b_() {
      return ImmutableList.of(this.bill, this.chin, this.body, this.rightWing, this.leftWing);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.head.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
      this.head.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.field_78137_g.rotateAngleX = this.head.rotateAngleX;
      this.field_78137_g.rotateAngleY = this.head.rotateAngleY;
      this.field_78143_h.rotateAngleX = this.head.rotateAngleX;
      this.field_78143_h.rotateAngleY = this.head.rotateAngleY;
      this.bill.rotateAngleX = ((float)Math.PI / 2F);
      this.chin.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
      this.body.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
      this.rightWing.rotateAngleZ = p_225597_4_;
      this.leftWing.rotateAngleZ = -p_225597_4_;
   }
}