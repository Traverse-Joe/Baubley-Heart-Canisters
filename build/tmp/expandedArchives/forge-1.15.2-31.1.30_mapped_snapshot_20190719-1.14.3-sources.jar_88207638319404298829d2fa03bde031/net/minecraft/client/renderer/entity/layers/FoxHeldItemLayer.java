package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FoxHeldItemLayer extends LayerRenderer<FoxEntity, FoxModel<FoxEntity>> {
   public FoxHeldItemLayer(IEntityRenderer<FoxEntity, FoxModel<FoxEntity>> p_i50938_1_) {
      super(p_i50938_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, FoxEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      boolean flag = p_225628_4_.isSleeping();
      boolean flag1 = p_225628_4_.isChild();
      p_225628_1_.func_227860_a_();
      if (flag1) {
         float f = 0.75F;
         p_225628_1_.func_227862_a_(0.75F, 0.75F, 0.75F);
         p_225628_1_.func_227861_a_(0.0D, 0.5D, (double)0.209375F);
      }

      p_225628_1_.func_227861_a_((double)((this.getEntityModel()).field_217115_a.rotationPointX / 16.0F), (double)((this.getEntityModel()).field_217115_a.rotationPointY / 16.0F), (double)((this.getEntityModel()).field_217115_a.rotationPointZ / 16.0F));
      float f1 = p_225628_4_.func_213475_v(p_225628_7_);
      p_225628_1_.func_227863_a_(Vector3f.field_229183_f_.func_229193_c_(f1));
      p_225628_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(p_225628_9_));
      p_225628_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(p_225628_10_));
      if (p_225628_4_.isChild()) {
         if (flag) {
            p_225628_1_.func_227861_a_((double)0.4F, (double)0.26F, (double)0.15F);
         } else {
            p_225628_1_.func_227861_a_((double)0.06F, (double)0.26F, -0.5D);
         }
      } else if (flag) {
         p_225628_1_.func_227861_a_((double)0.46F, (double)0.26F, (double)0.22F);
      } else {
         p_225628_1_.func_227861_a_((double)0.06F, (double)0.27F, -0.5D);
      }

      p_225628_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
      if (flag) {
         p_225628_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
      }

      ItemStack itemstack = p_225628_4_.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
      Minecraft.getInstance().getFirstPersonRenderer().func_228397_a_(p_225628_4_, itemstack, ItemCameraTransforms.TransformType.GROUND, false, p_225628_1_, p_225628_2_, p_225628_3_);
      p_225628_1_.func_227865_b_();
   }
}