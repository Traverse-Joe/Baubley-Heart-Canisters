package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.model.GuardianModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuardianRenderer extends MobRenderer<GuardianEntity, GuardianModel> {
   private static final ResourceLocation GUARDIAN_TEXTURE = new ResourceLocation("textures/entity/guardian.png");
   private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
   private static final RenderType field_229107_h_ = RenderType.func_228640_c_(GUARDIAN_BEAM_TEXTURE);

   public GuardianRenderer(EntityRendererManager renderManagerIn) {
      this(renderManagerIn, 0.5F);
   }

   protected GuardianRenderer(EntityRendererManager p_i50968_1_, float p_i50968_2_) {
      super(p_i50968_1_, new GuardianModel(), p_i50968_2_);
   }

   public boolean func_225626_a_(GuardianEntity p_225626_1_, ClippingHelperImpl p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
      if (super.func_225626_a_(p_225626_1_, p_225626_2_, p_225626_3_, p_225626_5_, p_225626_7_)) {
         return true;
      } else {
         if (p_225626_1_.hasTargetedEntity()) {
            LivingEntity livingentity = p_225626_1_.getTargetedEntity();
            if (livingentity != null) {
               Vec3d vec3d = this.getPosition(livingentity, (double)livingentity.getHeight() * 0.5D, 1.0F);
               Vec3d vec3d1 = this.getPosition(p_225626_1_, (double)p_225626_1_.getEyeHeight(), 1.0F);
               return p_225626_2_.func_228957_a_(new AxisAlignedBB(vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y, vec3d.z));
            }
         }

         return false;
      }
   }

   private Vec3d getPosition(LivingEntity entityLivingBaseIn, double p_177110_2_, float p_177110_4_) {
      double d0 = MathHelper.lerp((double)p_177110_4_, entityLivingBaseIn.lastTickPosX, entityLivingBaseIn.func_226277_ct_());
      double d1 = MathHelper.lerp((double)p_177110_4_, entityLivingBaseIn.lastTickPosY, entityLivingBaseIn.func_226278_cu_()) + p_177110_2_;
      double d2 = MathHelper.lerp((double)p_177110_4_, entityLivingBaseIn.lastTickPosZ, entityLivingBaseIn.func_226281_cx_());
      return new Vec3d(d0, d1, d2);
   }

   public void func_225623_a_(GuardianEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
      LivingEntity livingentity = p_225623_1_.getTargetedEntity();
      if (livingentity != null) {
         float f = p_225623_1_.getAttackAnimationScale(p_225623_3_);
         float f1 = (float)p_225623_1_.world.getGameTime() + p_225623_3_;
         float f2 = f1 * 0.5F % 1.0F;
         float f3 = p_225623_1_.getEyeHeight();
         p_225623_4_.func_227860_a_();
         p_225623_4_.func_227861_a_(0.0D, (double)f3, 0.0D);
         Vec3d vec3d = this.getPosition(livingentity, (double)livingentity.getHeight() * 0.5D, p_225623_3_);
         Vec3d vec3d1 = this.getPosition(p_225623_1_, (double)f3, p_225623_3_);
         Vec3d vec3d2 = vec3d.subtract(vec3d1);
         float f4 = (float)(vec3d2.length() + 1.0D);
         vec3d2 = vec3d2.normalize();
         float f5 = (float)Math.acos(vec3d2.y);
         float f6 = (float)Math.atan2(vec3d2.z, vec3d2.x);
         p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((((float)Math.PI / 2F) - f6) * (180F / (float)Math.PI)));
         p_225623_4_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f5 * (180F / (float)Math.PI)));
         int i = 1;
         float f7 = f1 * 0.05F * -1.5F;
         float f8 = f * f;
         int j = 64 + (int)(f8 * 191.0F);
         int k = 32 + (int)(f8 * 191.0F);
         int l = 128 - (int)(f8 * 64.0F);
         float f9 = 0.2F;
         float f10 = 0.282F;
         float f11 = MathHelper.cos(f7 + 2.3561945F) * 0.282F;
         float f12 = MathHelper.sin(f7 + 2.3561945F) * 0.282F;
         float f13 = MathHelper.cos(f7 + ((float)Math.PI / 4F)) * 0.282F;
         float f14 = MathHelper.sin(f7 + ((float)Math.PI / 4F)) * 0.282F;
         float f15 = MathHelper.cos(f7 + 3.926991F) * 0.282F;
         float f16 = MathHelper.sin(f7 + 3.926991F) * 0.282F;
         float f17 = MathHelper.cos(f7 + 5.4977875F) * 0.282F;
         float f18 = MathHelper.sin(f7 + 5.4977875F) * 0.282F;
         float f19 = MathHelper.cos(f7 + (float)Math.PI) * 0.2F;
         float f20 = MathHelper.sin(f7 + (float)Math.PI) * 0.2F;
         float f21 = MathHelper.cos(f7 + 0.0F) * 0.2F;
         float f22 = MathHelper.sin(f7 + 0.0F) * 0.2F;
         float f23 = MathHelper.cos(f7 + ((float)Math.PI / 2F)) * 0.2F;
         float f24 = MathHelper.sin(f7 + ((float)Math.PI / 2F)) * 0.2F;
         float f25 = MathHelper.cos(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
         float f26 = MathHelper.sin(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
         float f27 = 0.0F;
         float f28 = 0.4999F;
         float f29 = -1.0F + f2;
         float f30 = f4 * 2.5F + f29;
         IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(field_229107_h_);
         MatrixStack.Entry matrixstack$entry = p_225623_4_.func_227866_c_();
         Matrix4f matrix4f = matrixstack$entry.func_227870_a_();
         Matrix3f matrix3f = matrixstack$entry.func_227872_b_();
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);
         float f31 = 0.0F;
         if (p_225623_1_.ticksExisted % 2 == 0) {
            f31 = 0.5F;
         }

         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);
         p_225623_4_.func_227865_b_();
      }

   }

   private static void func_229108_a_(IVertexBuilder p_229108_0_, Matrix4f p_229108_1_, Matrix3f p_229108_2_, float p_229108_3_, float p_229108_4_, float p_229108_5_, int p_229108_6_, int p_229108_7_, int p_229108_8_, float p_229108_9_, float p_229108_10_) {
      p_229108_0_.func_227888_a_(p_229108_1_, p_229108_3_, p_229108_4_, p_229108_5_).func_225586_a_(p_229108_6_, p_229108_7_, p_229108_8_, 255).func_225583_a_(p_229108_9_, p_229108_10_).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(15728880).func_227887_a_(p_229108_2_, 0.0F, 1.0F, 0.0F).endVertex();
   }

   public ResourceLocation getEntityTexture(GuardianEntity entity) {
      return GUARDIAN_TEXTURE;
   }
}