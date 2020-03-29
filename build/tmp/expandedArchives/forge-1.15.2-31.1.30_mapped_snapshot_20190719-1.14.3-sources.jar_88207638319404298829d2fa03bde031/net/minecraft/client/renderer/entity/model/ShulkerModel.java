package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerModel<T extends ShulkerEntity> extends SegmentedModel<T> {
   private final ModelRenderer base;
   private final ModelRenderer lid = new ModelRenderer(64, 64, 0, 0);
   private final ModelRenderer head;

   public ShulkerModel() {
      this.base = new ModelRenderer(64, 64, 0, 28);
      this.head = new ModelRenderer(64, 64, 0, 52);
      this.lid.func_228300_a_(-8.0F, -16.0F, -8.0F, 16.0F, 12.0F, 16.0F);
      this.lid.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.base.func_228300_a_(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F);
      this.base.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.head.func_228300_a_(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F);
      this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      float f = p_225597_4_ - (float)p_225597_1_.ticksExisted;
      float f1 = (0.5F + p_225597_1_.getClientPeekAmount(f)) * (float)Math.PI;
      float f2 = -1.0F + MathHelper.sin(f1);
      float f3 = 0.0F;
      if (f1 > (float)Math.PI) {
         f3 = MathHelper.sin(p_225597_4_ * 0.1F) * 0.7F;
      }

      this.lid.setRotationPoint(0.0F, 16.0F + MathHelper.sin(f1) * 8.0F + f3, 0.0F);
      if (p_225597_1_.getClientPeekAmount(f) > 0.3F) {
         this.lid.rotateAngleY = f2 * f2 * f2 * f2 * (float)Math.PI * 0.125F;
      } else {
         this.lid.rotateAngleY = 0.0F;
      }

      this.head.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
      this.head.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.base, this.lid);
   }

   public ModelRenderer getBase() {
      return this.base;
   }

   public ModelRenderer getLid() {
      return this.lid;
   }

   public ModelRenderer getHead() {
      return this.head;
   }
}