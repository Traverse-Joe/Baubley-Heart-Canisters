package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.SquidModel;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SquidRenderer extends MobRenderer<SquidEntity, SquidModel<SquidEntity>> {
   private static final ResourceLocation SQUID_TEXTURES = new ResourceLocation("textures/entity/squid.png");

   public SquidRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new SquidModel<>(), 0.7F);
   }

   public ResourceLocation getEntityTexture(SquidEntity entity) {
      return SQUID_TEXTURES;
   }

   protected void func_225621_a_(SquidEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      float f = MathHelper.lerp(p_225621_5_, p_225621_1_.prevSquidPitch, p_225621_1_.squidPitch);
      float f1 = MathHelper.lerp(p_225621_5_, p_225621_1_.prevSquidYaw, p_225621_1_.squidYaw);
      p_225621_2_.func_227861_a_(0.0D, 0.5D, 0.0D);
      p_225621_2_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F - p_225621_4_));
      p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f));
      p_225621_2_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f1));
      p_225621_2_.func_227861_a_(0.0D, (double)-1.2F, 0.0D);
   }

   protected float handleRotationFloat(SquidEntity livingBase, float partialTicks) {
      return MathHelper.lerp(partialTicks, livingBase.lastTentacleAngle, livingBase.tentacleAngle);
   }
}