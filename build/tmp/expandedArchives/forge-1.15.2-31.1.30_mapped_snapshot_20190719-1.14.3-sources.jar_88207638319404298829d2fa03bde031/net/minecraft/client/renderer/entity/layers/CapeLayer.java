package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CapeLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
   public CapeLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> p_i50950_1_) {
      super(p_i50950_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, AbstractClientPlayerEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      if (p_225628_4_.hasPlayerInfo() && !p_225628_4_.isInvisible() && p_225628_4_.isWearing(PlayerModelPart.CAPE) && p_225628_4_.getLocationCape() != null) {
         ItemStack itemstack = p_225628_4_.getItemStackFromSlot(EquipmentSlotType.CHEST);
         if (itemstack.getItem() != Items.ELYTRA) {
            p_225628_1_.func_227860_a_();
            p_225628_1_.func_227861_a_(0.0D, 0.0D, 0.125D);
            double d0 = MathHelper.lerp((double)p_225628_7_, p_225628_4_.prevChasingPosX, p_225628_4_.chasingPosX) - MathHelper.lerp((double)p_225628_7_, p_225628_4_.prevPosX, p_225628_4_.func_226277_ct_());
            double d1 = MathHelper.lerp((double)p_225628_7_, p_225628_4_.prevChasingPosY, p_225628_4_.chasingPosY) - MathHelper.lerp((double)p_225628_7_, p_225628_4_.prevPosY, p_225628_4_.func_226278_cu_());
            double d2 = MathHelper.lerp((double)p_225628_7_, p_225628_4_.prevChasingPosZ, p_225628_4_.chasingPosZ) - MathHelper.lerp((double)p_225628_7_, p_225628_4_.prevPosZ, p_225628_4_.func_226281_cx_());
            float f = p_225628_4_.prevRenderYawOffset + (p_225628_4_.renderYawOffset - p_225628_4_.prevRenderYawOffset);
            double d3 = (double)MathHelper.sin(f * ((float)Math.PI / 180F));
            double d4 = (double)(-MathHelper.cos(f * ((float)Math.PI / 180F)));
            float f1 = (float)d1 * 10.0F;
            f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
            f2 = MathHelper.clamp(f2, 0.0F, 150.0F);
            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
            f3 = MathHelper.clamp(f3, -20.0F, 20.0F);
            if (f2 < 0.0F) {
               f2 = 0.0F;
            }

            float f4 = MathHelper.lerp(p_225628_7_, p_225628_4_.prevCameraYaw, p_225628_4_.cameraYaw);
            f1 = f1 + MathHelper.sin(MathHelper.lerp(p_225628_7_, p_225628_4_.prevDistanceWalkedModified, p_225628_4_.distanceWalkedModified) * 6.0F) * 32.0F * f4;
            if (p_225628_4_.isCrouching()) {
               f1 += 25.0F;
            }

            p_225628_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(6.0F + f2 / 2.0F + f1));
            p_225628_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(f3 / 2.0F));
            p_225628_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F - f3 / 2.0F));
            IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.func_228634_a_(p_225628_4_.getLocationCape()));
            this.getEntityModel().func_228289_b_(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.field_229196_a_);
            p_225628_1_.func_227865_b_();
         }
      }
   }
}