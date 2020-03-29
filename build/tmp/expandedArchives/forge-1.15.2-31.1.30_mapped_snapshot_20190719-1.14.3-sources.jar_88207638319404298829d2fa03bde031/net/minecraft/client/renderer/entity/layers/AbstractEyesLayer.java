package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractEyesLayer<T extends Entity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
   public AbstractEyesLayer(IEntityRenderer<T, M> p_i226039_1_) {
      super(p_i226039_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(this.func_225636_a_());
      this.getEntityModel().func_225598_a_(p_225628_1_, ivertexbuilder, 15728640, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   public abstract RenderType func_225636_a_();
}