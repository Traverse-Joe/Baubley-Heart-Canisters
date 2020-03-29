package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class MobRenderer<T extends MobEntity, M extends EntityModel<T>> extends LivingRenderer<T, M> {
   public MobRenderer(EntityRendererManager p_i50961_1_, M p_i50961_2_, float p_i50961_3_) {
      super(p_i50961_1_, p_i50961_2_, p_i50961_3_);
   }

   protected boolean canRenderName(T entity) {
      return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
   }

   public boolean func_225626_a_(T p_225626_1_, ClippingHelperImpl p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
      if (super.func_225626_a_(p_225626_1_, p_225626_2_, p_225626_3_, p_225626_5_, p_225626_7_)) {
         return true;
      } else {
         Entity entity = p_225626_1_.getLeashHolder();
         return entity != null ? p_225626_2_.func_228957_a_(entity.getRenderBoundingBox()) : false;
      }
   }

   public void func_225623_a_(T p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
      Entity entity = p_225623_1_.getLeashHolder();
      if (entity != null) {
         this.func_229118_a_(p_225623_1_, p_225623_3_, p_225623_4_, p_225623_5_, entity);
      }
   }

   private <E extends Entity> void func_229118_a_(T p_229118_1_, float p_229118_2_, MatrixStack p_229118_3_, IRenderTypeBuffer p_229118_4_, E p_229118_5_) {
      p_229118_3_.func_227860_a_();
      double d0 = (double)(MathHelper.lerp(p_229118_2_ * 0.5F, p_229118_5_.rotationYaw, p_229118_5_.prevRotationYaw) * ((float)Math.PI / 180F));
      double d1 = (double)(MathHelper.lerp(p_229118_2_ * 0.5F, p_229118_5_.rotationPitch, p_229118_5_.prevRotationPitch) * ((float)Math.PI / 180F));
      double d2 = Math.cos(d0);
      double d3 = Math.sin(d0);
      double d4 = Math.sin(d1);
      if (p_229118_5_ instanceof HangingEntity) {
         d2 = 0.0D;
         d3 = 0.0D;
         d4 = -1.0D;
      }

      double d5 = Math.cos(d1);
      double d6 = MathHelper.lerp((double)p_229118_2_, p_229118_5_.prevPosX, p_229118_5_.func_226277_ct_()) - d2 * 0.7D - d3 * 0.5D * d5;
      double d7 = MathHelper.lerp((double)p_229118_2_, p_229118_5_.prevPosY + (double)p_229118_5_.getEyeHeight() * 0.7D, p_229118_5_.func_226278_cu_() + (double)p_229118_5_.getEyeHeight() * 0.7D) - d4 * 0.5D - 0.25D;
      double d8 = MathHelper.lerp((double)p_229118_2_, p_229118_5_.prevPosZ, p_229118_5_.func_226281_cx_()) - d3 * 0.7D + d2 * 0.5D * d5;
      double d9 = (double)(MathHelper.lerp(p_229118_2_, p_229118_1_.renderYawOffset, p_229118_1_.prevRenderYawOffset) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
      d2 = Math.cos(d9) * (double)p_229118_1_.getWidth() * 0.4D;
      d3 = Math.sin(d9) * (double)p_229118_1_.getWidth() * 0.4D;
      double d10 = MathHelper.lerp((double)p_229118_2_, p_229118_1_.prevPosX, p_229118_1_.func_226277_ct_()) + d2;
      double d11 = MathHelper.lerp((double)p_229118_2_, p_229118_1_.prevPosY, p_229118_1_.func_226278_cu_());
      double d12 = MathHelper.lerp((double)p_229118_2_, p_229118_1_.prevPosZ, p_229118_1_.func_226281_cx_()) + d3;
      p_229118_3_.func_227861_a_(d2, -(1.6D - (double)p_229118_1_.getHeight()) * 0.5D, d3);
      float f = (float)(d6 - d10);
      float f1 = (float)(d7 - d11);
      float f2 = (float)(d8 - d12);
      float f3 = 0.025F;
      IVertexBuilder ivertexbuilder = p_229118_4_.getBuffer(RenderType.func_228649_h_());
      Matrix4f matrix4f = p_229118_3_.func_227866_c_().func_227870_a_();
      float f4 = MathHelper.func_226165_i_(f * f + f2 * f2) * 0.025F / 2.0F;
      float f5 = f2 * f4;
      float f6 = f * f4;
      int i = this.func_225624_a_(p_229118_1_, p_229118_2_);
      int j = this.renderManager.getRenderer(p_229118_5_).func_225624_a_(p_229118_5_, p_229118_2_);
      int k = p_229118_1_.world.func_226658_a_(LightType.SKY, new BlockPos(p_229118_1_.getEyePosition(p_229118_2_)));
      int l = p_229118_1_.world.func_226658_a_(LightType.SKY, new BlockPos(p_229118_5_.getEyePosition(p_229118_2_)));
      func_229119_a_(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6);
      func_229119_a_(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6);
      p_229118_3_.func_227865_b_();
   }

   public static void func_229119_a_(IVertexBuilder p_229119_0_, Matrix4f p_229119_1_, float p_229119_2_, float p_229119_3_, float p_229119_4_, int p_229119_5_, int p_229119_6_, int p_229119_7_, int p_229119_8_, float p_229119_9_, float p_229119_10_, float p_229119_11_, float p_229119_12_) {
      int i = 24;

      for(int j = 0; j < 24; ++j) {
         float f = (float)j / 23.0F;
         int k = (int)MathHelper.lerp(f, (float)p_229119_5_, (float)p_229119_6_);
         int l = (int)MathHelper.lerp(f, (float)p_229119_7_, (float)p_229119_8_);
         int i1 = LightTexture.func_228451_a_(k, l);
         func_229120_a_(p_229119_0_, p_229119_1_, i1, p_229119_2_, p_229119_3_, p_229119_4_, p_229119_9_, p_229119_10_, 24, j, false, p_229119_11_, p_229119_12_);
         func_229120_a_(p_229119_0_, p_229119_1_, i1, p_229119_2_, p_229119_3_, p_229119_4_, p_229119_9_, p_229119_10_, 24, j + 1, true, p_229119_11_, p_229119_12_);
      }

   }

   public static void func_229120_a_(IVertexBuilder p_229120_0_, Matrix4f p_229120_1_, int p_229120_2_, float p_229120_3_, float p_229120_4_, float p_229120_5_, float p_229120_6_, float p_229120_7_, int p_229120_8_, int p_229120_9_, boolean p_229120_10_, float p_229120_11_, float p_229120_12_) {
      float f = 0.5F;
      float f1 = 0.4F;
      float f2 = 0.3F;
      if (p_229120_9_ % 2 == 0) {
         f *= 0.7F;
         f1 *= 0.7F;
         f2 *= 0.7F;
      }

      float f3 = (float)p_229120_9_ / (float)p_229120_8_;
      float f4 = p_229120_3_ * f3;
      float f5 = p_229120_4_ * (f3 * f3 + f3) * 0.5F + ((float)p_229120_8_ - (float)p_229120_9_) / ((float)p_229120_8_ * 0.75F) + 0.125F;
      float f6 = p_229120_5_ * f3;
      if (!p_229120_10_) {
         p_229120_0_.func_227888_a_(p_229120_1_, f4 + p_229120_11_, f5 + p_229120_6_ - p_229120_7_, f6 - p_229120_12_).func_227885_a_(f, f1, f2, 1.0F).func_227886_a_(p_229120_2_).endVertex();
      }

      p_229120_0_.func_227888_a_(p_229120_1_, f4 - p_229120_11_, f5 + p_229120_7_, f6 + p_229120_12_).func_227885_a_(f, f1, f2, 1.0F).func_227886_a_(p_229120_2_).endVertex();
      if (p_229120_10_) {
         p_229120_0_.func_227888_a_(p_229120_1_, f4 + p_229120_11_, f5 + p_229120_6_ - p_229120_7_, f6 - p_229120_12_).func_227885_a_(f, f1, f2, 1.0F).func_227886_a_(p_229120_2_).endVertex();
      }

   }
}