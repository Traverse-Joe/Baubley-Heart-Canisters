package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeldBlockLayer extends LayerRenderer<EndermanEntity, EndermanModel<EndermanEntity>> {
   public HeldBlockLayer(IEntityRenderer<EndermanEntity, EndermanModel<EndermanEntity>> p_i50949_1_) {
      super(p_i50949_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, EndermanEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      BlockState blockstate = p_225628_4_.getHeldBlockState();
      if (blockstate != null) {
         p_225628_1_.func_227860_a_();
         p_225628_1_.func_227861_a_(0.0D, 0.6875D, -0.75D);
         p_225628_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(20.0F));
         p_225628_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(45.0F));
         p_225628_1_.func_227861_a_(0.25D, 0.1875D, 0.25D);
         float f = 0.5F;
         p_225628_1_.func_227862_a_(-0.5F, -0.5F, 0.5F);
         p_225628_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(90.0F));
         Minecraft.getInstance().getBlockRendererDispatcher().func_228791_a_(blockstate, p_225628_1_, p_225628_2_, p_225628_3_, OverlayTexture.field_229196_a_);
         p_225628_1_.func_227865_b_();
      }
   }
}