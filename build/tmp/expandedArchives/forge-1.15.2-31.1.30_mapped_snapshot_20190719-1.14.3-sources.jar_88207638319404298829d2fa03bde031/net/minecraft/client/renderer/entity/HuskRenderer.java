package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HuskRenderer extends ZombieRenderer {
   private static final ResourceLocation HUSK_ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/husk.png");

   public HuskRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
   }

   protected void func_225620_a_(ZombieEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      float f = 1.0625F;
      p_225620_2_.func_227862_a_(1.0625F, 1.0625F, 1.0625F);
      super.func_225620_a_(p_225620_1_, p_225620_2_, p_225620_3_);
   }

   public ResourceLocation getEntityTexture(ZombieEntity entity) {
      return HUSK_ZOMBIE_TEXTURES;
   }
}