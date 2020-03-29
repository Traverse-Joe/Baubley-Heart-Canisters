package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.ShulkerBulletModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerBulletRenderer extends EntityRenderer<ShulkerBulletEntity> {
   private static final ResourceLocation SHULKER_SPARK_TEXTURE = new ResourceLocation("textures/entity/shulker/spark.png");
   private static final RenderType field_229123_e_ = RenderType.func_228644_e_(SHULKER_SPARK_TEXTURE);
   private final ShulkerBulletModel<ShulkerBulletEntity> model = new ShulkerBulletModel<>();

   public ShulkerBulletRenderer(EntityRendererManager manager) {
      super(manager);
   }

   protected int func_225624_a_(ShulkerBulletEntity p_225624_1_, float p_225624_2_) {
      return 15;
   }

   public void func_225623_a_(ShulkerBulletEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      p_225623_4_.func_227860_a_();
      float f = MathHelper.func_226167_j_(p_225623_1_.prevRotationYaw, p_225623_1_.rotationYaw, p_225623_3_);
      float f1 = MathHelper.lerp(p_225623_3_, p_225623_1_.prevRotationPitch, p_225623_1_.rotationPitch);
      float f2 = (float)p_225623_1_.ticksExisted + p_225623_3_;
      p_225623_4_.func_227861_a_(0.0D, (double)0.15F, 0.0D);
      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(MathHelper.sin(f2 * 0.1F) * 180.0F));
      p_225623_4_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(MathHelper.cos(f2 * 0.1F) * 180.0F));
      p_225623_4_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(MathHelper.sin(f2 * 0.15F) * 360.0F));
      p_225623_4_.func_227862_a_(-0.5F, -0.5F, 0.5F);
      this.model.func_225597_a_(p_225623_1_, 0.0F, 0.0F, 0.0F, f, f1);
      IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(this.model.func_228282_a_(SHULKER_SPARK_TEXTURE));
      this.model.func_225598_a_(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_225623_4_.func_227862_a_(1.5F, 1.5F, 1.5F);
      IVertexBuilder ivertexbuilder1 = p_225623_5_.getBuffer(field_229123_e_);
      this.model.func_225598_a_(p_225623_4_, ivertexbuilder1, p_225623_6_, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 0.15F);
      p_225623_4_.func_227865_b_();
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
   }

   public ResourceLocation getEntityTexture(ShulkerBulletEntity entity) {
      return SHULKER_SPARK_TEXTURE;
   }
}