package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LlamaModel<T extends AbstractChestedHorseEntity> extends EntityModel<T> {
   private final ModelRenderer field_228273_a_;
   private final ModelRenderer field_228274_b_;
   private final ModelRenderer field_228275_f_;
   private final ModelRenderer field_228276_g_;
   private final ModelRenderer field_228277_h_;
   private final ModelRenderer field_228278_i_;
   private final ModelRenderer chest1;
   private final ModelRenderer chest2;

   public LlamaModel(float p_i47226_1_) {
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.field_228273_a_ = new ModelRenderer(this, 0, 0);
      this.field_228273_a_.func_228301_a_(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, p_i47226_1_);
      this.field_228273_a_.setRotationPoint(0.0F, 7.0F, -6.0F);
      this.field_228273_a_.setTextureOffset(0, 14).func_228301_a_(-4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, p_i47226_1_);
      this.field_228273_a_.setTextureOffset(17, 0).func_228301_a_(-4.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, p_i47226_1_);
      this.field_228273_a_.setTextureOffset(17, 0).func_228301_a_(1.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, p_i47226_1_);
      this.field_228274_b_ = new ModelRenderer(this, 29, 0);
      this.field_228274_b_.func_228301_a_(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, p_i47226_1_);
      this.field_228274_b_.setRotationPoint(0.0F, 5.0F, 2.0F);
      this.chest1 = new ModelRenderer(this, 45, 28);
      this.chest1.func_228301_a_(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, p_i47226_1_);
      this.chest1.setRotationPoint(-8.5F, 3.0F, 3.0F);
      this.chest1.rotateAngleY = ((float)Math.PI / 2F);
      this.chest2 = new ModelRenderer(this, 45, 41);
      this.chest2.func_228301_a_(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, p_i47226_1_);
      this.chest2.setRotationPoint(5.5F, 3.0F, 3.0F);
      this.chest2.rotateAngleY = ((float)Math.PI / 2F);
      int i = 4;
      int j = 14;
      this.field_228275_f_ = new ModelRenderer(this, 29, 29);
      this.field_228275_f_.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
      this.field_228275_f_.setRotationPoint(-2.5F, 10.0F, 6.0F);
      this.field_228276_g_ = new ModelRenderer(this, 29, 29);
      this.field_228276_g_.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
      this.field_228276_g_.setRotationPoint(2.5F, 10.0F, 6.0F);
      this.field_228277_h_ = new ModelRenderer(this, 29, 29);
      this.field_228277_h_.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
      this.field_228277_h_.setRotationPoint(-2.5F, 10.0F, -4.0F);
      this.field_228278_i_ = new ModelRenderer(this, 29, 29);
      this.field_228278_i_.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
      this.field_228278_i_.setRotationPoint(2.5F, 10.0F, -4.0F);
      --this.field_228275_f_.rotationPointX;
      ++this.field_228276_g_.rotationPointX;
      this.field_228275_f_.rotationPointZ += 0.0F;
      this.field_228276_g_.rotationPointZ += 0.0F;
      --this.field_228277_h_.rotationPointX;
      ++this.field_228278_i_.rotationPointX;
      --this.field_228277_h_.rotationPointZ;
      --this.field_228278_i_.rotationPointZ;
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.field_228273_a_.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
      this.field_228273_a_.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.field_228274_b_.rotateAngleX = ((float)Math.PI / 2F);
      this.field_228275_f_.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
      this.field_228276_g_.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
      this.field_228277_h_.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
      this.field_228278_i_.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
      boolean flag = !p_225597_1_.isChild() && p_225597_1_.hasChest();
      this.chest1.showModel = flag;
      this.chest2.showModel = flag;
   }

   public void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
      if (this.isChild) {
         float f = 2.0F;
         p_225598_1_.func_227860_a_();
         float f1 = 0.7F;
         p_225598_1_.func_227862_a_(0.71428573F, 0.64935064F, 0.7936508F);
         p_225598_1_.func_227861_a_(0.0D, 1.3125D, (double)0.22F);
         this.field_228273_a_.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
         p_225598_1_.func_227865_b_();
         p_225598_1_.func_227860_a_();
         float f2 = 1.1F;
         p_225598_1_.func_227862_a_(0.625F, 0.45454544F, 0.45454544F);
         p_225598_1_.func_227861_a_(0.0D, 2.0625D, 0.0D);
         this.field_228274_b_.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
         p_225598_1_.func_227865_b_();
         p_225598_1_.func_227860_a_();
         p_225598_1_.func_227862_a_(0.45454544F, 0.41322312F, 0.45454544F);
         p_225598_1_.func_227861_a_(0.0D, 2.0625D, 0.0D);
         ImmutableList.of(this.field_228275_f_, this.field_228276_g_, this.field_228277_h_, this.field_228278_i_, this.chest1, this.chest2).forEach((p_228280_8_) -> {
            p_228280_8_.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
         });
         p_225598_1_.func_227865_b_();
      } else {
         ImmutableList.of(this.field_228273_a_, this.field_228274_b_, this.field_228275_f_, this.field_228276_g_, this.field_228277_h_, this.field_228278_i_, this.chest1, this.chest2).forEach((p_228279_8_) -> {
            p_228279_8_.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
         });
      }

   }
}