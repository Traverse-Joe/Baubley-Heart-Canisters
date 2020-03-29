package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.GiantModel;
import net.minecraft.entity.monster.GiantEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GiantZombieRenderer extends MobRenderer<GiantEntity, BipedModel<GiantEntity>> {
   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
   private final float scale;

   public GiantZombieRenderer(EntityRendererManager p_i47206_1_, float scaleIn) {
      super(p_i47206_1_, new GiantModel(), 0.5F * scaleIn);
      this.scale = scaleIn;
      this.addLayer(new HeldItemLayer<>(this));
      this.addLayer(new BipedArmorLayer<>(this, new GiantModel(0.5F, true), new GiantModel(1.0F, true)));
   }

   protected void func_225620_a_(GiantEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      p_225620_2_.func_227862_a_(this.scale, this.scale, this.scale);
   }

   public ResourceLocation getEntityTexture(GiantEntity entity) {
      return ZOMBIE_TEXTURES;
   }
}