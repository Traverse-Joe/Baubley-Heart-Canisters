package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeGelLayer<T extends LivingEntity> extends LayerRenderer<T, SlimeModel<T>> {
   private final EntityModel<T> slimeModel = new SlimeModel<>(0);

   public SlimeGelLayer(IEntityRenderer<T, SlimeModel<T>> p_i50923_1_) {
      super(p_i50923_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      if (!p_225628_4_.isInvisible()) {
         this.getEntityModel().setModelAttributes(this.slimeModel);
         this.slimeModel.setLivingAnimations(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_);
         this.slimeModel.func_225597_a_(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
         IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.func_228644_e_(this.func_229139_a_(p_225628_4_)));
         this.slimeModel.func_225598_a_(p_225628_1_, ivertexbuilder, p_225628_3_, LivingRenderer.func_229117_c_(p_225628_4_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}