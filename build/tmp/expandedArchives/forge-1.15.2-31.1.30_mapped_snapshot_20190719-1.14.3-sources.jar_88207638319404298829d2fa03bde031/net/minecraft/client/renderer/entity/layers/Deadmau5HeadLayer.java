package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Deadmau5HeadLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
   public Deadmau5HeadLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> p_i50945_1_) {
      super(p_i50945_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, AbstractClientPlayerEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      if ("deadmau5".equals(p_225628_4_.getName().getString()) && p_225628_4_.hasSkin() && !p_225628_4_.isInvisible()) {
         IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.func_228634_a_(p_225628_4_.getLocationSkin()));
         int i = LivingRenderer.func_229117_c_(p_225628_4_, 0.0F);

         for(int j = 0; j < 2; ++j) {
            float f = MathHelper.lerp(p_225628_7_, p_225628_4_.prevRotationYaw, p_225628_4_.rotationYaw) - MathHelper.lerp(p_225628_7_, p_225628_4_.prevRenderYawOffset, p_225628_4_.renderYawOffset);
            float f1 = MathHelper.lerp(p_225628_7_, p_225628_4_.prevRotationPitch, p_225628_4_.rotationPitch);
            p_225628_1_.func_227860_a_();
            p_225628_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f));
            p_225628_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f1));
            p_225628_1_.func_227861_a_((double)(0.375F * (float)(j * 2 - 1)), 0.0D, 0.0D);
            p_225628_1_.func_227861_a_(0.0D, -0.375D, 0.0D);
            p_225628_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-f1));
            p_225628_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-f));
            float f2 = 1.3333334F;
            p_225628_1_.func_227862_a_(1.3333334F, 1.3333334F, 1.3333334F);
            this.getEntityModel().func_228287_a_(p_225628_1_, ivertexbuilder, p_225628_3_, i);
            p_225628_1_.func_227865_b_();
         }

      }
   }
}