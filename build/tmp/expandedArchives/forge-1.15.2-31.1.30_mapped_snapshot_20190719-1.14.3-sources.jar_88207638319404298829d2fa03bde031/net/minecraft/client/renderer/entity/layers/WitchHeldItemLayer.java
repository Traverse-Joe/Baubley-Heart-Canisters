package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.WitchModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitchHeldItemLayer<T extends LivingEntity> extends CrossedArmsItemLayer<T, WitchModel<T>> {
   public WitchHeldItemLayer(IEntityRenderer<T, WitchModel<T>> p_i50916_1_) {
      super(p_i50916_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      ItemStack itemstack = p_225628_4_.getHeldItemMainhand();
      p_225628_1_.func_227860_a_();
      if (itemstack.getItem() == Items.POTION) {
         this.getEntityModel().func_205072_a().func_228307_a_(p_225628_1_);
         this.getEntityModel().func_205073_b().func_228307_a_(p_225628_1_);
         p_225628_1_.func_227861_a_(0.0625D, 0.25D, 0.0D);
         p_225628_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
         p_225628_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(140.0F));
         p_225628_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(10.0F));
         p_225628_1_.func_227861_a_(0.0D, (double)-0.4F, (double)0.4F);
      }

      super.func_225628_a_(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
      p_225628_1_.func_227865_b_();
   }
}