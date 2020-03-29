package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer field_78200_a;
   private final ModelRenderer field_78198_b;
   private final ModelRenderer field_78199_c;
   private final ModelRenderer field_78197_d;

   public SlimeModel(int slimeBodyTexOffY) {
      this.field_78200_a = new ModelRenderer(this, 0, slimeBodyTexOffY);
      this.field_78198_b = new ModelRenderer(this, 32, 0);
      this.field_78199_c = new ModelRenderer(this, 32, 4);
      this.field_78197_d = new ModelRenderer(this, 32, 8);
      if (slimeBodyTexOffY > 0) {
         this.field_78200_a.func_228300_a_(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F);
         this.field_78198_b.func_228300_a_(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
         this.field_78199_c.func_228300_a_(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
         this.field_78197_d.func_228300_a_(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F);
      } else {
         this.field_78200_a.func_228300_a_(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      }

   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.field_78200_a, this.field_78198_b, this.field_78199_c, this.field_78197_d);
   }
}