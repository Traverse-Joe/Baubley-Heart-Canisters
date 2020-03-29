package net.minecraft.client.renderer.entity.model;

import java.util.Arrays;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MinecartModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer[] field_78154_a = new ModelRenderer[6];

   public MinecartModel() {
      this.field_78154_a[0] = new ModelRenderer(this, 0, 10);
      this.field_78154_a[1] = new ModelRenderer(this, 0, 0);
      this.field_78154_a[2] = new ModelRenderer(this, 0, 0);
      this.field_78154_a[3] = new ModelRenderer(this, 0, 0);
      this.field_78154_a[4] = new ModelRenderer(this, 0, 0);
      this.field_78154_a[5] = new ModelRenderer(this, 44, 10);
      int i = 20;
      int j = 8;
      int k = 16;
      int l = 4;
      this.field_78154_a[0].func_228301_a_(-10.0F, -8.0F, -1.0F, 20.0F, 16.0F, 2.0F, 0.0F);
      this.field_78154_a[0].setRotationPoint(0.0F, 4.0F, 0.0F);
      this.field_78154_a[5].func_228301_a_(-9.0F, -7.0F, -1.0F, 18.0F, 14.0F, 1.0F, 0.0F);
      this.field_78154_a[5].setRotationPoint(0.0F, 4.0F, 0.0F);
      this.field_78154_a[1].func_228301_a_(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F, 0.0F);
      this.field_78154_a[1].setRotationPoint(-9.0F, 4.0F, 0.0F);
      this.field_78154_a[2].func_228301_a_(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F, 0.0F);
      this.field_78154_a[2].setRotationPoint(9.0F, 4.0F, 0.0F);
      this.field_78154_a[3].func_228301_a_(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F, 0.0F);
      this.field_78154_a[3].setRotationPoint(0.0F, 4.0F, -7.0F);
      this.field_78154_a[4].func_228301_a_(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F, 0.0F);
      this.field_78154_a[4].setRotationPoint(0.0F, 4.0F, 7.0F);
      this.field_78154_a[0].rotateAngleX = ((float)Math.PI / 2F);
      this.field_78154_a[1].rotateAngleY = ((float)Math.PI * 1.5F);
      this.field_78154_a[2].rotateAngleY = ((float)Math.PI / 2F);
      this.field_78154_a[3].rotateAngleY = (float)Math.PI;
      this.field_78154_a[5].rotateAngleX = (-(float)Math.PI / 2F);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.field_78154_a[5].rotationPointY = 4.0F - p_225597_4_;
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return Arrays.asList(this.field_78154_a);
   }
}