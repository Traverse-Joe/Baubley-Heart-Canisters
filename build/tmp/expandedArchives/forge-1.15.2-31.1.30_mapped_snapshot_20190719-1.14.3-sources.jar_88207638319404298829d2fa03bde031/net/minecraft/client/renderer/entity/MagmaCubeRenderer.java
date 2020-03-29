package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.model.MagmaCubeModel;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MagmaCubeRenderer extends MobRenderer<MagmaCubeEntity, MagmaCubeModel<MagmaCubeEntity>> {
   private static final ResourceLocation MAGMA_CUBE_TEXTURES = new ResourceLocation("textures/entity/slime/magmacube.png");

   public MagmaCubeRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new MagmaCubeModel<>(), 0.25F);
   }

   protected int func_225624_a_(MagmaCubeEntity p_225624_1_, float p_225624_2_) {
      return 15;
   }

   public ResourceLocation getEntityTexture(MagmaCubeEntity entity) {
      return MAGMA_CUBE_TEXTURES;
   }

   protected void func_225620_a_(MagmaCubeEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      int i = p_225620_1_.getSlimeSize();
      float f = MathHelper.lerp(p_225620_3_, p_225620_1_.prevSquishFactor, p_225620_1_.squishFactor) / ((float)i * 0.5F + 1.0F);
      float f1 = 1.0F / (f + 1.0F);
      p_225620_2_.func_227862_a_(f1 * (float)i, 1.0F / f1 * (float)i, f1 * (float)i);
   }
}