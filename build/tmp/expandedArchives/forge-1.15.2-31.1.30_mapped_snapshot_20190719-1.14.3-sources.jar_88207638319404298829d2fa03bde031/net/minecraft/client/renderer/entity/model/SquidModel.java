package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SquidModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer field_78202_a;
   private final ModelRenderer[] field_78201_b = new ModelRenderer[8];
   private final ImmutableList<ModelRenderer> field_228296_f_;

   public SquidModel() {
      int i = -16;
      this.field_78202_a = new ModelRenderer(this, 0, 0);
      this.field_78202_a.func_228300_a_(-6.0F, -8.0F, -6.0F, 12.0F, 16.0F, 12.0F);
      this.field_78202_a.rotationPointY += 8.0F;

      for(int j = 0; j < this.field_78201_b.length; ++j) {
         this.field_78201_b[j] = new ModelRenderer(this, 48, 0);
         double d0 = (double)j * Math.PI * 2.0D / (double)this.field_78201_b.length;
         float f = (float)Math.cos(d0) * 5.0F;
         float f1 = (float)Math.sin(d0) * 5.0F;
         this.field_78201_b[j].func_228300_a_(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F);
         this.field_78201_b[j].rotationPointX = f;
         this.field_78201_b[j].rotationPointZ = f1;
         this.field_78201_b[j].rotationPointY = 15.0F;
         d0 = (double)j * Math.PI * -2.0D / (double)this.field_78201_b.length + (Math.PI / 2D);
         this.field_78201_b[j].rotateAngleY = (float)d0;
      }

      Builder<ModelRenderer> builder = ImmutableList.builder();
      builder.add(this.field_78202_a);
      builder.addAll(Arrays.asList(this.field_78201_b));
      this.field_228296_f_ = builder.build();
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      for(ModelRenderer modelrenderer : this.field_78201_b) {
         modelrenderer.rotateAngleX = p_225597_4_;
      }

   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return this.field_228296_f_;
   }
}