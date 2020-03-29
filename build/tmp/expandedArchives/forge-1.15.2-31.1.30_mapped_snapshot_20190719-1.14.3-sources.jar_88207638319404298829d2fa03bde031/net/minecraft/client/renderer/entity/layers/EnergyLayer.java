package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IChargeableMob;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class EnergyLayer<T extends Entity & IChargeableMob, M extends EntityModel<T>> extends LayerRenderer<T, M> {
   public EnergyLayer(IEntityRenderer<T, M> p_i226038_1_) {
      super(p_i226038_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      if (((IChargeableMob)p_225628_4_).func_225509_J__()) {
         float f = (float)p_225628_4_.ticksExisted + p_225628_7_;
         EntityModel<T> entitymodel = this.func_225635_b_();
         entitymodel.setLivingAnimations(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_);
         this.getEntityModel().setModelAttributes(entitymodel);
         IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.func_228636_a_(this.func_225633_a_(), this.func_225634_a_(f), f * 0.01F));
         entitymodel.func_225597_a_(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
         entitymodel.func_225598_a_(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.field_229196_a_, 0.5F, 0.5F, 0.5F, 1.0F);
      }
   }

   protected abstract float func_225634_a_(float p_225634_1_);

   protected abstract ResourceLocation func_225633_a_();

   protected abstract EntityModel<T> func_225635_b_();
}