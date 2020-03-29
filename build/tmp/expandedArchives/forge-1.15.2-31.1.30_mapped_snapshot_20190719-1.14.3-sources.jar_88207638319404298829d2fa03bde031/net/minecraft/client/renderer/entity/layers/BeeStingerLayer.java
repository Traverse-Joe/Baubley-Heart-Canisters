package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeeStingerLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M> {
   private static final ResourceLocation field_229131_a_ = new ResourceLocation("textures/entity/bee/bee_stinger.png");

   public BeeStingerLayer(LivingRenderer<T, M> p_i226036_1_) {
      super(p_i226036_1_);
   }

   protected int func_225631_a_(T p_225631_1_) {
      return p_225631_1_.func_226297_df_();
   }

   protected void func_225632_a_(MatrixStack p_225632_1_, IRenderTypeBuffer p_225632_2_, int p_225632_3_, Entity p_225632_4_, float p_225632_5_, float p_225632_6_, float p_225632_7_, float p_225632_8_) {
      float f = MathHelper.sqrt(p_225632_5_ * p_225632_5_ + p_225632_7_ * p_225632_7_);
      float f1 = (float)(Math.atan2((double)p_225632_5_, (double)p_225632_7_) * (double)(180F / (float)Math.PI));
      float f2 = (float)(Math.atan2((double)p_225632_6_, (double)f) * (double)(180F / (float)Math.PI));
      p_225632_1_.func_227861_a_(0.0D, 0.0D, 0.0D);
      p_225632_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f1 - 90.0F));
      p_225632_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(f2));
      float f3 = 0.0F;
      float f4 = 0.125F;
      float f5 = 0.0F;
      float f6 = 0.0625F;
      float f7 = 0.03125F;
      p_225632_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(45.0F));
      p_225632_1_.func_227862_a_(0.03125F, 0.03125F, 0.03125F);
      p_225632_1_.func_227861_a_(2.5D, 0.0D, 0.0D);
      IVertexBuilder ivertexbuilder = p_225632_2_.getBuffer(RenderType.func_228640_c_(field_229131_a_));

      for(int i = 0; i < 4; ++i) {
         p_225632_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
         MatrixStack.Entry matrixstack$entry = p_225632_1_.func_227866_c_();
         Matrix4f matrix4f = matrixstack$entry.func_227870_a_();
         Matrix3f matrix3f = matrixstack$entry.func_227872_b_();
         func_229132_a_(ivertexbuilder, matrix4f, matrix3f, -4.5F, -1, 0.0F, 0.0F, p_225632_3_);
         func_229132_a_(ivertexbuilder, matrix4f, matrix3f, 4.5F, -1, 0.125F, 0.0F, p_225632_3_);
         func_229132_a_(ivertexbuilder, matrix4f, matrix3f, 4.5F, 1, 0.125F, 0.0625F, p_225632_3_);
         func_229132_a_(ivertexbuilder, matrix4f, matrix3f, -4.5F, 1, 0.0F, 0.0625F, p_225632_3_);
      }

   }

   private static void func_229132_a_(IVertexBuilder p_229132_0_, Matrix4f p_229132_1_, Matrix3f p_229132_2_, float p_229132_3_, int p_229132_4_, float p_229132_5_, float p_229132_6_, int p_229132_7_) {
      p_229132_0_.func_227888_a_(p_229132_1_, p_229132_3_, (float)p_229132_4_, 0.0F).func_225586_a_(255, 255, 255, 255).func_225583_a_(p_229132_5_, p_229132_6_).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(p_229132_7_).func_227887_a_(p_229132_2_, 0.0F, 1.0F, 0.0F).endVertex();
   }
}