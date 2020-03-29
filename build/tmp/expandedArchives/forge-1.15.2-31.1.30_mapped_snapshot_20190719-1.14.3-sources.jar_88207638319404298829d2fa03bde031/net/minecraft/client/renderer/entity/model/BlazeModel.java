package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlazeModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer[] blazeSticks;
   private final ModelRenderer blazeHead = new ModelRenderer(this, 0, 0);
   private final ImmutableList<ModelRenderer> field_228242_f_;

   public BlazeModel() {
      this.blazeHead.func_228300_a_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.blazeSticks = new ModelRenderer[12];

      for(int i = 0; i < this.blazeSticks.length; ++i) {
         this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
         this.blazeSticks[i].func_228300_a_(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);
      }

      Builder<ModelRenderer> builder = ImmutableList.builder();
      builder.add(this.blazeHead);
      builder.addAll(Arrays.asList(this.blazeSticks));
      this.field_228242_f_ = builder.build();
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return this.field_228242_f_;
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      float f = p_225597_4_ * (float)Math.PI * -0.1F;

      for(int i = 0; i < 4; ++i) {
         this.blazeSticks[i].rotationPointY = -2.0F + MathHelper.cos(((float)(i * 2) + p_225597_4_) * 0.25F);
         this.blazeSticks[i].rotationPointX = MathHelper.cos(f) * 9.0F;
         this.blazeSticks[i].rotationPointZ = MathHelper.sin(f) * 9.0F;
         ++f;
      }

      f = ((float)Math.PI / 4F) + p_225597_4_ * (float)Math.PI * 0.03F;

      for(int j = 4; j < 8; ++j) {
         this.blazeSticks[j].rotationPointY = 2.0F + MathHelper.cos(((float)(j * 2) + p_225597_4_) * 0.25F);
         this.blazeSticks[j].rotationPointX = MathHelper.cos(f) * 7.0F;
         this.blazeSticks[j].rotationPointZ = MathHelper.sin(f) * 7.0F;
         ++f;
      }

      f = 0.47123894F + p_225597_4_ * (float)Math.PI * -0.05F;

      for(int k = 8; k < 12; ++k) {
         this.blazeSticks[k].rotationPointY = 11.0F + MathHelper.cos(((float)k * 1.5F + p_225597_4_) * 0.5F);
         this.blazeSticks[k].rotationPointX = MathHelper.cos(f) * 5.0F;
         this.blazeSticks[k].rotationPointZ = MathHelper.sin(f) * 5.0F;
         ++f;
      }

      this.blazeHead.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.blazeHead.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
   }
}