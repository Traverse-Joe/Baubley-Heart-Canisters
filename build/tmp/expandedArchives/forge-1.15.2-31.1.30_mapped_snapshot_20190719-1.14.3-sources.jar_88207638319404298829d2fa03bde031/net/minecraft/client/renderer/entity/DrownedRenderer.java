package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
import net.minecraft.client.renderer.entity.model.DrownedModel;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DrownedRenderer extends AbstractZombieRenderer<DrownedEntity, DrownedModel<DrownedEntity>> {
   private static final ResourceLocation DROWNED_LOCATION = new ResourceLocation("textures/entity/zombie/drowned.png");

   public DrownedRenderer(EntityRendererManager p_i48906_1_) {
      super(p_i48906_1_, new DrownedModel<>(0.0F, 0.0F, 64, 64), new DrownedModel<>(0.5F, true), new DrownedModel<>(1.0F, true));
      this.addLayer(new DrownedOuterLayer<>(this));
   }

   public ResourceLocation getEntityTexture(ZombieEntity entity) {
      return DROWNED_LOCATION;
   }

   protected void func_225621_a_(DrownedEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
      float f = p_225621_1_.getSwimAnimation(p_225621_5_);
      if (f > 0.0F) {
         p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(MathHelper.lerp(f, p_225621_1_.rotationPitch, -10.0F - p_225621_1_.rotationPitch)));
      }

   }
}