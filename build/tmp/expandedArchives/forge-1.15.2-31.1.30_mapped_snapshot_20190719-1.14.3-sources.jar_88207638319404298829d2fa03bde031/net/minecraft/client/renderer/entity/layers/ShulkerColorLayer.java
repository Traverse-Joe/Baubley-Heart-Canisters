package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerColorLayer extends LayerRenderer<ShulkerEntity, ShulkerModel<ShulkerEntity>> {
   public ShulkerColorLayer(IEntityRenderer<ShulkerEntity, ShulkerModel<ShulkerEntity>> p_i50924_1_) {
      super(p_i50924_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, ShulkerEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      p_225628_1_.func_227860_a_();
      p_225628_1_.func_227861_a_(0.0D, 1.0D, 0.0D);
      p_225628_1_.func_227862_a_(-1.0F, -1.0F, 1.0F);
      Quaternion quaternion = p_225628_4_.getAttachmentFacing().getOpposite().func_229384_a_();
      quaternion.conjugate();
      p_225628_1_.func_227863_a_(quaternion);
      p_225628_1_.func_227862_a_(-1.0F, -1.0F, 1.0F);
      p_225628_1_.func_227861_a_(0.0D, -1.0D, 0.0D);
      ModelRenderer modelrenderer = this.getEntityModel().getHead();
      modelrenderer.rotateAngleY = p_225628_9_ * ((float)Math.PI / 180F);
      modelrenderer.rotateAngleX = p_225628_10_ * ((float)Math.PI / 180F);
      DyeColor dyecolor = p_225628_4_.getColor();
      ResourceLocation resourcelocation;
      if (dyecolor == null) {
         resourcelocation = ShulkerRenderer.field_204402_a;
      } else {
         resourcelocation = ShulkerRenderer.SHULKER_ENDERGOLEM_TEXTURE[dyecolor.getId()];
      }

      IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.func_228634_a_(resourcelocation));
      modelrenderer.func_228308_a_(p_225628_1_, ivertexbuilder, p_225628_3_, LivingRenderer.func_229117_c_(p_225628_4_, 0.0F));
      p_225628_1_.func_227865_b_();
   }
}