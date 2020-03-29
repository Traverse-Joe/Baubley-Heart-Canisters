package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Random;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhastModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer[] field_78127_b = new ModelRenderer[9];
   private final ImmutableList<ModelRenderer> field_228260_b_;

   public GhastModel() {
      Builder<ModelRenderer> builder = ImmutableList.builder();
      ModelRenderer modelrenderer = new ModelRenderer(this, 0, 0);
      modelrenderer.func_228300_a_(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
      modelrenderer.rotationPointY = 17.6F;
      builder.add(modelrenderer);
      Random random = new Random(1660L);

      for(int i = 0; i < this.field_78127_b.length; ++i) {
         this.field_78127_b[i] = new ModelRenderer(this, 0, 0);
         float f = (((float)(i % 3) - (float)(i / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
         float f1 = ((float)(i / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
         int j = random.nextInt(7) + 8;
         this.field_78127_b[i].func_228300_a_(-1.0F, 0.0F, -1.0F, 2.0F, (float)j, 2.0F);
         this.field_78127_b[i].rotationPointX = f;
         this.field_78127_b[i].rotationPointZ = f1;
         this.field_78127_b[i].rotationPointY = 24.6F;
         builder.add(this.field_78127_b[i]);
      }

      this.field_228260_b_ = builder.build();
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      for(int i = 0; i < this.field_78127_b.length; ++i) {
         this.field_78127_b[i].rotateAngleX = 0.2F * MathHelper.sin(p_225597_4_ * 0.3F + (float)i) + 0.4F;
      }

   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return this.field_228260_b_;
   }
}