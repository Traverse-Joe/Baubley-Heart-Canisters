package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookModel extends Model {
   private final ModelRenderer field_78102_a = (new ModelRenderer(64, 32, 0, 0)).func_228300_a_(-6.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F);
   private final ModelRenderer field_78100_b = (new ModelRenderer(64, 32, 16, 0)).func_228300_a_(0.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F);
   private final ModelRenderer field_78101_c;
   private final ModelRenderer field_78098_d;
   private final ModelRenderer field_78099_e;
   private final ModelRenderer field_78096_f;
   private final ModelRenderer field_78097_g = (new ModelRenderer(64, 32, 12, 0)).func_228300_a_(-1.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.005F);
   private final List<ModelRenderer> field_228246_h_;

   public BookModel() {
      super(RenderType::func_228634_a_);
      this.field_78101_c = (new ModelRenderer(64, 32, 0, 10)).func_228300_a_(0.0F, -4.0F, -0.99F, 5.0F, 8.0F, 1.0F);
      this.field_78098_d = (new ModelRenderer(64, 32, 12, 10)).func_228300_a_(0.0F, -4.0F, -0.01F, 5.0F, 8.0F, 1.0F);
      this.field_78099_e = (new ModelRenderer(64, 32, 24, 10)).func_228300_a_(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F);
      this.field_78096_f = (new ModelRenderer(64, 32, 24, 10)).func_228300_a_(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F);
      this.field_228246_h_ = ImmutableList.of(this.field_78102_a, this.field_78100_b, this.field_78097_g, this.field_78101_c, this.field_78098_d, this.field_78099_e, this.field_78096_f);
      this.field_78102_a.setRotationPoint(0.0F, 0.0F, -1.0F);
      this.field_78100_b.setRotationPoint(0.0F, 0.0F, 1.0F);
      this.field_78097_g.rotateAngleY = ((float)Math.PI / 2F);
   }

   public void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
      this.func_228249_b_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
   }

   public void func_228249_b_(MatrixStack p_228249_1_, IVertexBuilder p_228249_2_, int p_228249_3_, int p_228249_4_, float p_228249_5_, float p_228249_6_, float p_228249_7_, float p_228249_8_) {
      this.field_228246_h_.forEach((p_228248_8_) -> {
         p_228248_8_.func_228309_a_(p_228249_1_, p_228249_2_, p_228249_3_, p_228249_4_, p_228249_5_, p_228249_6_, p_228249_7_, p_228249_8_);
      });
   }

   public void func_228247_a_(float p_228247_1_, float p_228247_2_, float p_228247_3_, float p_228247_4_) {
      float f = (MathHelper.sin(p_228247_1_ * 0.02F) * 0.1F + 1.25F) * p_228247_4_;
      this.field_78102_a.rotateAngleY = (float)Math.PI + f;
      this.field_78100_b.rotateAngleY = -f;
      this.field_78101_c.rotateAngleY = f;
      this.field_78098_d.rotateAngleY = -f;
      this.field_78099_e.rotateAngleY = f - f * 2.0F * p_228247_2_;
      this.field_78096_f.rotateAngleY = f - f * 2.0F * p_228247_3_;
      this.field_78101_c.rotationPointX = MathHelper.sin(f);
      this.field_78098_d.rotationPointX = MathHelper.sin(f);
      this.field_78099_e.rotationPointX = MathHelper.sin(f);
      this.field_78096_f.rotationPointX = MathHelper.sin(f);
   }
}