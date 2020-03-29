package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LayerRenderer<T extends Entity, M extends EntityModel<T>> {
   private final IEntityRenderer<T, M> entityRenderer;

   public LayerRenderer(IEntityRenderer<T, M> entityRendererIn) {
      this.entityRenderer = entityRendererIn;
   }

   protected static <T extends LivingEntity> void func_229140_a_(EntityModel<T> p_229140_0_, EntityModel<T> p_229140_1_, ResourceLocation p_229140_2_, MatrixStack p_229140_3_, IRenderTypeBuffer p_229140_4_, int p_229140_5_, T p_229140_6_, float p_229140_7_, float p_229140_8_, float p_229140_9_, float p_229140_10_, float p_229140_11_, float p_229140_12_, float p_229140_13_, float p_229140_14_, float p_229140_15_) {
      if (!p_229140_6_.isInvisible()) {
         p_229140_0_.setModelAttributes(p_229140_1_);
         p_229140_1_.setLivingAnimations(p_229140_6_, p_229140_7_, p_229140_8_, p_229140_12_);
         p_229140_1_.func_225597_a_(p_229140_6_, p_229140_7_, p_229140_8_, p_229140_9_, p_229140_10_, p_229140_11_);
         func_229141_a_(p_229140_1_, p_229140_2_, p_229140_3_, p_229140_4_, p_229140_5_, p_229140_6_, p_229140_13_, p_229140_14_, p_229140_15_);
      }

   }

   protected static <T extends LivingEntity> void func_229141_a_(EntityModel<T> p_229141_0_, ResourceLocation p_229141_1_, MatrixStack p_229141_2_, IRenderTypeBuffer p_229141_3_, int p_229141_4_, T p_229141_5_, float p_229141_6_, float p_229141_7_, float p_229141_8_) {
      IVertexBuilder ivertexbuilder = p_229141_3_.getBuffer(RenderType.func_228640_c_(p_229141_1_));
      p_229141_0_.func_225598_a_(p_229141_2_, ivertexbuilder, p_229141_4_, LivingRenderer.func_229117_c_(p_229141_5_, 0.0F), p_229141_6_, p_229141_7_, p_229141_8_, 1.0F);
   }

   public M getEntityModel() {
      return this.entityRenderer.getEntityModel();
   }

   protected ResourceLocation func_229139_a_(T p_229139_1_) {
      return this.entityRenderer.getEntityTexture(p_229139_1_);
   }

   public abstract void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_);
}