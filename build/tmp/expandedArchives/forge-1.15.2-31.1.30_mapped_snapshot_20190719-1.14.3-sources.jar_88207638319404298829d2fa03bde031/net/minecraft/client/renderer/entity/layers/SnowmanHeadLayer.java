package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.SnowManModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowmanHeadLayer extends LayerRenderer<SnowGolemEntity, SnowManModel<SnowGolemEntity>> {
   public SnowmanHeadLayer(IEntityRenderer<SnowGolemEntity, SnowManModel<SnowGolemEntity>> p_i50922_1_) {
      super(p_i50922_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, SnowGolemEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      if (!p_225628_4_.isInvisible() && p_225628_4_.isPumpkinEquipped()) {
         p_225628_1_.func_227860_a_();
         this.getEntityModel().func_205070_a().func_228307_a_(p_225628_1_);
         float f = 0.625F;
         p_225628_1_.func_227861_a_(0.0D, -0.34375D, 0.0D);
         p_225628_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F));
         p_225628_1_.func_227862_a_(0.625F, -0.625F, -0.625F);
         ItemStack itemstack = new ItemStack(Blocks.CARVED_PUMPKIN);
         Minecraft.getInstance().getItemRenderer().func_229109_a_(p_225628_4_, itemstack, ItemCameraTransforms.TransformType.HEAD, false, p_225628_1_, p_225628_2_, p_225628_4_.world, p_225628_3_, LivingRenderer.func_229117_c_(p_225628_4_, 0.0F));
         p_225628_1_.func_227865_b_();
      }
   }
}