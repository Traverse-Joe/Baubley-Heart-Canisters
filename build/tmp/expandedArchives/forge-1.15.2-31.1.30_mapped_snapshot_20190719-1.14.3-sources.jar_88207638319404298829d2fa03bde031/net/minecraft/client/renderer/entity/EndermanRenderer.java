package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.EndermanEyesLayer;
import net.minecraft.client.renderer.entity.layers.HeldBlockLayer;
import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndermanRenderer extends MobRenderer<EndermanEntity, EndermanModel<EndermanEntity>> {
   private static final ResourceLocation ENDERMAN_TEXTURES = new ResourceLocation("textures/entity/enderman/enderman.png");
   private final Random rnd = new Random();

   public EndermanRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new EndermanModel<>(0.0F), 0.5F);
      this.addLayer(new EndermanEyesLayer<>(this));
      this.addLayer(new HeldBlockLayer(this));
   }

   public void func_225623_a_(EndermanEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      BlockState blockstate = p_225623_1_.getHeldBlockState();
      EndermanModel<EndermanEntity> endermanmodel = this.getEntityModel();
      endermanmodel.isCarrying = blockstate != null;
      endermanmodel.isAttacking = p_225623_1_.isScreaming();
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
   }

   public Vec3d func_225627_b_(EndermanEntity p_225627_1_, float p_225627_2_) {
      if (p_225627_1_.isScreaming()) {
         double d0 = 0.02D;
         return new Vec3d(this.rnd.nextGaussian() * 0.02D, 0.0D, this.rnd.nextGaussian() * 0.02D);
      } else {
         return super.func_225627_b_(p_225627_1_, p_225627_2_);
      }
   }

   public ResourceLocation getEntityTexture(EndermanEntity entity) {
      return ENDERMAN_TEXTURES;
   }
}