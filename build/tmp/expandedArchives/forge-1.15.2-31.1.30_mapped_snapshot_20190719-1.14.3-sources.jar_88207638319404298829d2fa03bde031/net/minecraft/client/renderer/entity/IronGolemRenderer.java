package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.layers.IronGolemCracksLayer;
import net.minecraft.client.renderer.entity.layers.IronGolenFlowerLayer;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronGolemRenderer extends MobRenderer<IronGolemEntity, IronGolemModel<IronGolemEntity>> {
   private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("textures/entity/iron_golem/iron_golem.png");

   public IronGolemRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new IronGolemModel<>(), 0.7F);
      this.addLayer(new IronGolemCracksLayer(this));
      this.addLayer(new IronGolenFlowerLayer(this));
   }

   public ResourceLocation getEntityTexture(IronGolemEntity entity) {
      return IRON_GOLEM_TEXTURES;
   }

   protected void func_225621_a_(IronGolemEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
      if (!((double)p_225621_1_.limbSwingAmount < 0.01D)) {
         float f = 13.0F;
         float f1 = p_225621_1_.limbSwing - p_225621_1_.limbSwingAmount * (1.0F - p_225621_5_) + 6.0F;
         float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
         p_225621_2_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(6.5F * f2));
      }
   }
}