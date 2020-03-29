package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.DolphinModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DolphinCarriedItemLayer extends LayerRenderer<DolphinEntity, DolphinModel<DolphinEntity>> {
   public DolphinCarriedItemLayer(IEntityRenderer<DolphinEntity, DolphinModel<DolphinEntity>> p_i50944_1_) {
      super(p_i50944_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, DolphinEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      boolean flag = p_225628_4_.getPrimaryHand() == HandSide.RIGHT;
      p_225628_1_.func_227860_a_();
      float f = 1.0F;
      float f1 = -1.0F;
      float f2 = MathHelper.abs(p_225628_4_.rotationPitch) / 60.0F;
      if (p_225628_4_.rotationPitch < 0.0F) {
         p_225628_1_.func_227861_a_(0.0D, (double)(1.0F - f2 * 0.5F), (double)(-1.0F + f2 * 0.5F));
      } else {
         p_225628_1_.func_227861_a_(0.0D, (double)(1.0F + f2 * 0.8F), (double)(-1.0F + f2 * 0.2F));
      }

      ItemStack itemstack = flag ? p_225628_4_.getHeldItemMainhand() : p_225628_4_.getHeldItemOffhand();
      Minecraft.getInstance().getFirstPersonRenderer().func_228397_a_(p_225628_4_, itemstack, ItemCameraTransforms.TransformType.GROUND, false, p_225628_1_, p_225628_2_, p_225628_3_);
      p_225628_1_.func_227865_b_();
   }
}