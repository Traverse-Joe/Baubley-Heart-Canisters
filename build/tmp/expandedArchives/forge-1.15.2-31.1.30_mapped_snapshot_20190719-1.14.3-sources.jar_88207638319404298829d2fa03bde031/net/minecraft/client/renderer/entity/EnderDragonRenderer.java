package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnderDragonRenderer extends EntityRenderer<EnderDragonEntity> {
   public static final ResourceLocation ENDERCRYSTAL_BEAM_TEXTURES = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
   private static final ResourceLocation DRAGON_EXPLODING_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
   private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon.png");
   private static final ResourceLocation field_229052_g_ = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
   private static final RenderType field_229053_h_ = RenderType.func_228640_c_(DRAGON_TEXTURES);
   private static final RenderType field_229054_i_ = RenderType.func_228648_g_(DRAGON_TEXTURES);
   private static final RenderType field_229055_j_ = RenderType.func_228652_i_(field_229052_g_);
   private static final RenderType field_229056_k_ = RenderType.func_228646_f_(ENDERCRYSTAL_BEAM_TEXTURES);
   private static final float field_229057_l_ = (float)(Math.sqrt(3.0D) / 2.0D);
   private final EnderDragonRenderer.EnderDragonModel field_229058_m_ = new EnderDragonRenderer.EnderDragonModel();

   public EnderDragonRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
      this.shadowSize = 0.5F;
   }

   public void func_225623_a_(EnderDragonEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      p_225623_4_.func_227860_a_();
      float f = (float)p_225623_1_.getMovementOffsets(7, p_225623_3_)[0];
      float f1 = (float)(p_225623_1_.getMovementOffsets(5, p_225623_3_)[1] - p_225623_1_.getMovementOffsets(10, p_225623_3_)[1]);
      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-f));
      p_225623_4_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f1 * 10.0F));
      p_225623_4_.func_227861_a_(0.0D, 0.0D, 1.0D);
      p_225623_4_.func_227862_a_(-1.0F, -1.0F, 1.0F);
      p_225623_4_.func_227861_a_(0.0D, (double)-1.501F, 0.0D);
      boolean flag = p_225623_1_.hurtTime > 0;
      this.field_229058_m_.setLivingAnimations(p_225623_1_, 0.0F, 0.0F, p_225623_3_);
      if (p_225623_1_.deathTicks > 0) {
         float f2 = (float)p_225623_1_.deathTicks / 200.0F;
         IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(RenderType.func_228635_a_(DRAGON_EXPLODING_TEXTURES, f2));
         this.field_229058_m_.func_225598_a_(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
         IVertexBuilder ivertexbuilder1 = p_225623_5_.getBuffer(field_229054_i_);
         this.field_229058_m_.func_225598_a_(p_225623_4_, ivertexbuilder1, p_225623_6_, OverlayTexture.func_229200_a_(0.0F, flag), 1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         IVertexBuilder ivertexbuilder3 = p_225623_5_.getBuffer(field_229053_h_);
         this.field_229058_m_.func_225598_a_(p_225623_4_, ivertexbuilder3, p_225623_6_, OverlayTexture.func_229200_a_(0.0F, flag), 1.0F, 1.0F, 1.0F, 1.0F);
      }

      IVertexBuilder ivertexbuilder4 = p_225623_5_.getBuffer(field_229055_j_);
      this.field_229058_m_.func_225598_a_(p_225623_4_, ivertexbuilder4, p_225623_6_, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
      if (p_225623_1_.deathTicks > 0) {
         float f5 = ((float)p_225623_1_.deathTicks + p_225623_3_) / 200.0F;
         float f7 = 0.0F;
         if (f5 > 0.8F) {
            f7 = (f5 - 0.8F) / 0.2F;
         }

         Random random = new Random(432L);
         IVertexBuilder ivertexbuilder2 = p_225623_5_.getBuffer(RenderType.func_228657_l_());
         p_225623_4_.func_227860_a_();
         p_225623_4_.func_227861_a_(0.0D, -1.0D, -2.0D);

         for(int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
            p_225623_4_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(random.nextFloat() * 360.0F));
            p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(random.nextFloat() * 360.0F));
            p_225623_4_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(random.nextFloat() * 360.0F));
            p_225623_4_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(random.nextFloat() * 360.0F));
            p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(random.nextFloat() * 360.0F));
            p_225623_4_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(random.nextFloat() * 360.0F + f5 * 90.0F));
            float f3 = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
            Matrix4f matrix4f = p_225623_4_.func_227866_c_().func_227870_a_();
            int j = (int)(255.0F * (1.0F - f7));
            func_229061_a_(ivertexbuilder2, matrix4f, j);
            func_229060_a_(ivertexbuilder2, matrix4f, f3, f4);
            func_229062_b_(ivertexbuilder2, matrix4f, f3, f4);
            func_229061_a_(ivertexbuilder2, matrix4f, j);
            func_229062_b_(ivertexbuilder2, matrix4f, f3, f4);
            func_229063_c_(ivertexbuilder2, matrix4f, f3, f4);
            func_229061_a_(ivertexbuilder2, matrix4f, j);
            func_229063_c_(ivertexbuilder2, matrix4f, f3, f4);
            func_229060_a_(ivertexbuilder2, matrix4f, f3, f4);
         }

         p_225623_4_.func_227865_b_();
      }

      p_225623_4_.func_227865_b_();
      if (p_225623_1_.field_70992_bH != null) {
         p_225623_4_.func_227860_a_();
         float f6 = (float)(p_225623_1_.field_70992_bH.func_226277_ct_() - MathHelper.lerp((double)p_225623_3_, p_225623_1_.prevPosX, p_225623_1_.func_226277_ct_()));
         float f8 = (float)(p_225623_1_.field_70992_bH.func_226278_cu_() - MathHelper.lerp((double)p_225623_3_, p_225623_1_.prevPosY, p_225623_1_.func_226278_cu_()));
         float f9 = (float)(p_225623_1_.field_70992_bH.func_226281_cx_() - MathHelper.lerp((double)p_225623_3_, p_225623_1_.prevPosZ, p_225623_1_.func_226281_cx_()));
         func_229059_a_(f6, f8 + EnderCrystalRenderer.func_229051_a_(p_225623_1_.field_70992_bH, p_225623_3_), f9, p_225623_3_, p_225623_1_.ticksExisted, p_225623_4_, p_225623_5_, p_225623_6_);
         p_225623_4_.func_227865_b_();
      }

      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
   }

   private static void func_229061_a_(IVertexBuilder p_229061_0_, Matrix4f p_229061_1_, int p_229061_2_) {
      p_229061_0_.func_227888_a_(p_229061_1_, 0.0F, 0.0F, 0.0F).func_225586_a_(255, 255, 255, p_229061_2_).endVertex();
      p_229061_0_.func_227888_a_(p_229061_1_, 0.0F, 0.0F, 0.0F).func_225586_a_(255, 255, 255, p_229061_2_).endVertex();
   }

   private static void func_229060_a_(IVertexBuilder p_229060_0_, Matrix4f p_229060_1_, float p_229060_2_, float p_229060_3_) {
      p_229060_0_.func_227888_a_(p_229060_1_, -field_229057_l_ * p_229060_3_, p_229060_2_, -0.5F * p_229060_3_).func_225586_a_(255, 0, 255, 0).endVertex();
   }

   private static void func_229062_b_(IVertexBuilder p_229062_0_, Matrix4f p_229062_1_, float p_229062_2_, float p_229062_3_) {
      p_229062_0_.func_227888_a_(p_229062_1_, field_229057_l_ * p_229062_3_, p_229062_2_, -0.5F * p_229062_3_).func_225586_a_(255, 0, 255, 0).endVertex();
   }

   private static void func_229063_c_(IVertexBuilder p_229063_0_, Matrix4f p_229063_1_, float p_229063_2_, float p_229063_3_) {
      p_229063_0_.func_227888_a_(p_229063_1_, 0.0F, p_229063_2_, 1.0F * p_229063_3_).func_225586_a_(255, 0, 255, 0).endVertex();
   }

   public static void func_229059_a_(float p_229059_0_, float p_229059_1_, float p_229059_2_, float p_229059_3_, int p_229059_4_, MatrixStack p_229059_5_, IRenderTypeBuffer p_229059_6_, int p_229059_7_) {
      float f = MathHelper.sqrt(p_229059_0_ * p_229059_0_ + p_229059_2_ * p_229059_2_);
      float f1 = MathHelper.sqrt(p_229059_0_ * p_229059_0_ + p_229059_1_ * p_229059_1_ + p_229059_2_ * p_229059_2_);
      p_229059_5_.func_227860_a_();
      p_229059_5_.func_227861_a_(0.0D, 2.0D, 0.0D);
      p_229059_5_.func_227863_a_(Vector3f.field_229181_d_.func_229193_c_((float)(-Math.atan2((double)p_229059_2_, (double)p_229059_0_)) - ((float)Math.PI / 2F)));
      p_229059_5_.func_227863_a_(Vector3f.field_229179_b_.func_229193_c_((float)(-Math.atan2((double)f, (double)p_229059_1_)) - ((float)Math.PI / 2F)));
      IVertexBuilder ivertexbuilder = p_229059_6_.getBuffer(field_229056_k_);
      float f2 = 0.0F - ((float)p_229059_4_ + p_229059_3_) * 0.01F;
      float f3 = MathHelper.sqrt(p_229059_0_ * p_229059_0_ + p_229059_1_ * p_229059_1_ + p_229059_2_ * p_229059_2_) / 32.0F - ((float)p_229059_4_ + p_229059_3_) * 0.01F;
      int i = 8;
      float f4 = 0.0F;
      float f5 = 0.75F;
      float f6 = 0.0F;
      MatrixStack.Entry matrixstack$entry = p_229059_5_.func_227866_c_();
      Matrix4f matrix4f = matrixstack$entry.func_227870_a_();
      Matrix3f matrix3f = matrixstack$entry.func_227872_b_();

      for(int j = 1; j <= 8; ++j) {
         float f7 = MathHelper.sin((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
         float f8 = MathHelper.cos((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
         float f9 = (float)j / 8.0F;
         ivertexbuilder.func_227888_a_(matrix4f, f4 * 0.2F, f5 * 0.2F, 0.0F).func_225586_a_(0, 0, 0, 255).func_225583_a_(f6, f2).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(p_229059_7_).func_227887_a_(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
         ivertexbuilder.func_227888_a_(matrix4f, f4, f5, f1).func_225586_a_(255, 255, 255, 255).func_225583_a_(f6, f3).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(p_229059_7_).func_227887_a_(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
         ivertexbuilder.func_227888_a_(matrix4f, f7, f8, f1).func_225586_a_(255, 255, 255, 255).func_225583_a_(f9, f3).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(p_229059_7_).func_227887_a_(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
         ivertexbuilder.func_227888_a_(matrix4f, f7 * 0.2F, f8 * 0.2F, 0.0F).func_225586_a_(0, 0, 0, 255).func_225583_a_(f9, f2).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(p_229059_7_).func_227887_a_(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
         f4 = f7;
         f5 = f8;
         f6 = f9;
      }

      p_229059_5_.func_227865_b_();
   }

   public ResourceLocation getEntityTexture(EnderDragonEntity entity) {
      return DRAGON_TEXTURES;
   }

   @OnlyIn(Dist.CLIENT)
   public static class EnderDragonModel extends EntityModel<EnderDragonEntity> {
      private final ModelRenderer field_78221_a;
      private final ModelRenderer spine;
      private final ModelRenderer field_78220_c;
      private final ModelRenderer field_78217_d;
      private ModelRenderer field_229065_h_;
      private ModelRenderer field_229066_i_;
      private ModelRenderer field_229067_j_;
      private ModelRenderer field_229068_k_;
      private ModelRenderer field_229069_l_;
      private ModelRenderer field_229070_m_;
      private ModelRenderer field_229071_n_;
      private ModelRenderer field_229072_o_;
      private ModelRenderer field_229073_p_;
      private ModelRenderer field_229074_t_;
      private ModelRenderer field_229075_u_;
      private ModelRenderer field_229076_v_;
      private ModelRenderer field_229077_w_;
      private ModelRenderer field_229078_x_;
      private ModelRenderer field_229079_y_;
      private ModelRenderer field_229080_z_;
      @Nullable
      private EnderDragonEntity field_229064_A_;
      private float partialTicks;

      public EnderDragonModel() {
         this.textureWidth = 256;
         this.textureHeight = 256;
         float f = -16.0F;
         this.field_78221_a = new ModelRenderer(this);
         this.field_78221_a.func_217178_a("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 0.0F, 176, 44);
         this.field_78221_a.func_217178_a("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, 0.0F, 112, 30);
         this.field_78221_a.mirror = true;
         this.field_78221_a.func_217178_a("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0.0F, 0, 0);
         this.field_78221_a.func_217178_a("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 0.0F, 112, 0);
         this.field_78221_a.mirror = false;
         this.field_78221_a.func_217178_a("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0.0F, 0, 0);
         this.field_78221_a.func_217178_a("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 0.0F, 112, 0);
         this.field_78220_c = new ModelRenderer(this);
         this.field_78220_c.setRotationPoint(0.0F, 4.0F, -8.0F);
         this.field_78220_c.func_217178_a("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, 0.0F, 176, 65);
         this.field_78221_a.addChild(this.field_78220_c);
         this.spine = new ModelRenderer(this);
         this.spine.func_217178_a("box", -5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F, 192, 104);
         this.spine.func_217178_a("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6, 0.0F, 48, 0);
         this.field_78217_d = new ModelRenderer(this);
         this.field_78217_d.setRotationPoint(0.0F, 4.0F, 8.0F);
         this.field_78217_d.func_217178_a("body", -12.0F, 0.0F, -16.0F, 24, 24, 64, 0.0F, 0, 0);
         this.field_78217_d.func_217178_a("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12, 0.0F, 220, 53);
         this.field_78217_d.func_217178_a("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12, 0.0F, 220, 53);
         this.field_78217_d.func_217178_a("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12, 0.0F, 220, 53);
         this.field_229065_h_ = new ModelRenderer(this);
         this.field_229065_h_.mirror = true;
         this.field_229065_h_.setRotationPoint(12.0F, 5.0F, 2.0F);
         this.field_229065_h_.func_217178_a("bone", 0.0F, -4.0F, -4.0F, 56, 8, 8, 0.0F, 112, 88);
         this.field_229065_h_.func_217178_a("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 88);
         this.field_229066_i_ = new ModelRenderer(this);
         this.field_229066_i_.mirror = true;
         this.field_229066_i_.setRotationPoint(56.0F, 0.0F, 0.0F);
         this.field_229066_i_.func_217178_a("bone", 0.0F, -2.0F, -2.0F, 56, 4, 4, 0.0F, 112, 136);
         this.field_229066_i_.func_217178_a("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 144);
         this.field_229065_h_.addChild(this.field_229066_i_);
         this.field_229067_j_ = new ModelRenderer(this);
         this.field_229067_j_.setRotationPoint(12.0F, 20.0F, 2.0F);
         this.field_229067_j_.func_217178_a("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 0.0F, 112, 104);
         this.field_229068_k_ = new ModelRenderer(this);
         this.field_229068_k_.setRotationPoint(0.0F, 20.0F, -1.0F);
         this.field_229068_k_.func_217178_a("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 0.0F, 226, 138);
         this.field_229067_j_.addChild(this.field_229068_k_);
         this.field_229069_l_ = new ModelRenderer(this);
         this.field_229069_l_.setRotationPoint(0.0F, 23.0F, 0.0F);
         this.field_229069_l_.func_217178_a("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 0.0F, 144, 104);
         this.field_229068_k_.addChild(this.field_229069_l_);
         this.field_229070_m_ = new ModelRenderer(this);
         this.field_229070_m_.setRotationPoint(16.0F, 16.0F, 42.0F);
         this.field_229070_m_.func_217178_a("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0.0F, 0, 0);
         this.field_229071_n_ = new ModelRenderer(this);
         this.field_229071_n_.setRotationPoint(0.0F, 32.0F, -4.0F);
         this.field_229071_n_.func_217178_a("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 0.0F, 196, 0);
         this.field_229070_m_.addChild(this.field_229071_n_);
         this.field_229072_o_ = new ModelRenderer(this);
         this.field_229072_o_.setRotationPoint(0.0F, 31.0F, 4.0F);
         this.field_229072_o_.func_217178_a("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 0.0F, 112, 0);
         this.field_229071_n_.addChild(this.field_229072_o_);
         this.field_229073_p_ = new ModelRenderer(this);
         this.field_229073_p_.setRotationPoint(-12.0F, 5.0F, 2.0F);
         this.field_229073_p_.func_217178_a("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8, 0.0F, 112, 88);
         this.field_229073_p_.func_217178_a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 88);
         this.field_229074_t_ = new ModelRenderer(this);
         this.field_229074_t_.setRotationPoint(-56.0F, 0.0F, 0.0F);
         this.field_229074_t_.func_217178_a("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4, 0.0F, 112, 136);
         this.field_229074_t_.func_217178_a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 144);
         this.field_229073_p_.addChild(this.field_229074_t_);
         this.field_229075_u_ = new ModelRenderer(this);
         this.field_229075_u_.setRotationPoint(-12.0F, 20.0F, 2.0F);
         this.field_229075_u_.func_217178_a("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 0.0F, 112, 104);
         this.field_229076_v_ = new ModelRenderer(this);
         this.field_229076_v_.setRotationPoint(0.0F, 20.0F, -1.0F);
         this.field_229076_v_.func_217178_a("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 0.0F, 226, 138);
         this.field_229075_u_.addChild(this.field_229076_v_);
         this.field_229077_w_ = new ModelRenderer(this);
         this.field_229077_w_.setRotationPoint(0.0F, 23.0F, 0.0F);
         this.field_229077_w_.func_217178_a("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 0.0F, 144, 104);
         this.field_229076_v_.addChild(this.field_229077_w_);
         this.field_229078_x_ = new ModelRenderer(this);
         this.field_229078_x_.setRotationPoint(-16.0F, 16.0F, 42.0F);
         this.field_229078_x_.func_217178_a("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0.0F, 0, 0);
         this.field_229079_y_ = new ModelRenderer(this);
         this.field_229079_y_.setRotationPoint(0.0F, 32.0F, -4.0F);
         this.field_229079_y_.func_217178_a("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 0.0F, 196, 0);
         this.field_229078_x_.addChild(this.field_229079_y_);
         this.field_229080_z_ = new ModelRenderer(this);
         this.field_229080_z_.setRotationPoint(0.0F, 31.0F, 4.0F);
         this.field_229080_z_.func_217178_a("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 0.0F, 112, 0);
         this.field_229079_y_.addChild(this.field_229080_z_);
      }

      public void setLivingAnimations(EnderDragonEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
         this.field_229064_A_ = entityIn;
         this.partialTicks = partialTick;
      }

      public void func_225597_a_(EnderDragonEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      }

      public void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
         p_225598_1_.func_227860_a_();
         float f = MathHelper.lerp(this.partialTicks, this.field_229064_A_.prevAnimTime, this.field_229064_A_.animTime);
         this.field_78220_c.rotateAngleX = (float)(Math.sin((double)(f * ((float)Math.PI * 2F))) + 1.0D) * 0.2F;
         float f1 = (float)(Math.sin((double)(f * ((float)Math.PI * 2F) - 1.0F)) + 1.0D);
         f1 = (f1 * f1 + f1 * 2.0F) * 0.05F;
         p_225598_1_.func_227861_a_(0.0D, (double)(f1 - 2.0F), -3.0D);
         p_225598_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f1 * 2.0F));
         float f2 = 0.0F;
         float f3 = 20.0F;
         float f4 = -12.0F;
         float f5 = 1.5F;
         double[] adouble = this.field_229064_A_.getMovementOffsets(6, this.partialTicks);
         float f6 = MathHelper.func_226168_l_(this.field_229064_A_.getMovementOffsets(5, this.partialTicks)[0] - this.field_229064_A_.getMovementOffsets(10, this.partialTicks)[0]);
         float f7 = MathHelper.func_226168_l_(this.field_229064_A_.getMovementOffsets(5, this.partialTicks)[0] + (double)(f6 / 2.0F));
         float f8 = f * ((float)Math.PI * 2F);

         for(int i = 0; i < 5; ++i) {
            double[] adouble1 = this.field_229064_A_.getMovementOffsets(5 - i, this.partialTicks);
            float f9 = (float)Math.cos((double)((float)i * 0.45F + f8)) * 0.15F;
            this.spine.rotateAngleY = MathHelper.func_226168_l_(adouble1[0] - adouble[0]) * ((float)Math.PI / 180F) * 1.5F;
            this.spine.rotateAngleX = f9 + this.field_229064_A_.getHeadPartYOffset(i, adouble, adouble1) * ((float)Math.PI / 180F) * 1.5F * 5.0F;
            this.spine.rotateAngleZ = -MathHelper.func_226168_l_(adouble1[0] - (double)f7) * ((float)Math.PI / 180F) * 1.5F;
            this.spine.rotationPointY = f3;
            this.spine.rotationPointZ = f4;
            this.spine.rotationPointX = f2;
            f3 = (float)((double)f3 + Math.sin((double)this.spine.rotateAngleX) * 10.0D);
            f4 = (float)((double)f4 - Math.cos((double)this.spine.rotateAngleY) * Math.cos((double)this.spine.rotateAngleX) * 10.0D);
            f2 = (float)((double)f2 - Math.sin((double)this.spine.rotateAngleY) * Math.cos((double)this.spine.rotateAngleX) * 10.0D);
            this.spine.func_228308_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
         }

         this.field_78221_a.rotationPointY = f3;
         this.field_78221_a.rotationPointZ = f4;
         this.field_78221_a.rotationPointX = f2;
         double[] adouble2 = this.field_229064_A_.getMovementOffsets(0, this.partialTicks);
         this.field_78221_a.rotateAngleY = MathHelper.func_226168_l_(adouble2[0] - adouble[0]) * ((float)Math.PI / 180F);
         this.field_78221_a.rotateAngleX = MathHelper.func_226168_l_((double)this.field_229064_A_.getHeadPartYOffset(6, adouble, adouble2)) * ((float)Math.PI / 180F) * 1.5F * 5.0F;
         this.field_78221_a.rotateAngleZ = -MathHelper.func_226168_l_(adouble2[0] - (double)f7) * ((float)Math.PI / 180F);
         this.field_78221_a.func_228308_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
         p_225598_1_.func_227860_a_();
         p_225598_1_.func_227861_a_(0.0D, 1.0D, 0.0D);
         p_225598_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(-f6 * 1.5F));
         p_225598_1_.func_227861_a_(0.0D, -1.0D, 0.0D);
         this.field_78217_d.rotateAngleZ = 0.0F;
         this.field_78217_d.func_228308_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
         float f10 = f * ((float)Math.PI * 2F);
         this.field_229065_h_.rotateAngleX = 0.125F - (float)Math.cos((double)f10) * 0.2F;
         this.field_229065_h_.rotateAngleY = -0.25F;
         this.field_229065_h_.rotateAngleZ = -((float)(Math.sin((double)f10) + 0.125D)) * 0.8F;
         this.field_229066_i_.rotateAngleZ = (float)(Math.sin((double)(f10 + 2.0F)) + 0.5D) * 0.75F;
         this.field_229073_p_.rotateAngleX = this.field_229065_h_.rotateAngleX;
         this.field_229073_p_.rotateAngleY = -this.field_229065_h_.rotateAngleY;
         this.field_229073_p_.rotateAngleZ = -this.field_229065_h_.rotateAngleZ;
         this.field_229074_t_.rotateAngleZ = -this.field_229066_i_.rotateAngleZ;
         this.func_229081_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, f1, this.field_229065_h_, this.field_229067_j_, this.field_229068_k_, this.field_229069_l_, this.field_229070_m_, this.field_229071_n_, this.field_229072_o_);
         this.func_229081_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, f1, this.field_229073_p_, this.field_229075_u_, this.field_229076_v_, this.field_229077_w_, this.field_229078_x_, this.field_229079_y_, this.field_229080_z_);
         p_225598_1_.func_227865_b_();
         float f11 = -((float)Math.sin((double)(f * ((float)Math.PI * 2F)))) * 0.0F;
         f8 = f * ((float)Math.PI * 2F);
         f3 = 10.0F;
         f4 = 60.0F;
         f2 = 0.0F;
         adouble = this.field_229064_A_.getMovementOffsets(11, this.partialTicks);

         for(int j = 0; j < 12; ++j) {
            adouble2 = this.field_229064_A_.getMovementOffsets(12 + j, this.partialTicks);
            f11 = (float)((double)f11 + Math.sin((double)((float)j * 0.45F + f8)) * (double)0.05F);
            this.spine.rotateAngleY = (MathHelper.func_226168_l_(adouble2[0] - adouble[0]) * 1.5F + 180.0F) * ((float)Math.PI / 180F);
            this.spine.rotateAngleX = f11 + (float)(adouble2[1] - adouble[1]) * ((float)Math.PI / 180F) * 1.5F * 5.0F;
            this.spine.rotateAngleZ = MathHelper.func_226168_l_(adouble2[0] - (double)f7) * ((float)Math.PI / 180F) * 1.5F;
            this.spine.rotationPointY = f3;
            this.spine.rotationPointZ = f4;
            this.spine.rotationPointX = f2;
            f3 = (float)((double)f3 + Math.sin((double)this.spine.rotateAngleX) * 10.0D);
            f4 = (float)((double)f4 - Math.cos((double)this.spine.rotateAngleY) * Math.cos((double)this.spine.rotateAngleX) * 10.0D);
            f2 = (float)((double)f2 - Math.sin((double)this.spine.rotateAngleY) * Math.cos((double)this.spine.rotateAngleX) * 10.0D);
            this.spine.func_228308_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
         }

         p_225598_1_.func_227865_b_();
      }

      private void func_229081_a_(MatrixStack p_229081_1_, IVertexBuilder p_229081_2_, int p_229081_3_, int p_229081_4_, float p_229081_5_, ModelRenderer p_229081_6_, ModelRenderer p_229081_7_, ModelRenderer p_229081_8_, ModelRenderer p_229081_9_, ModelRenderer p_229081_10_, ModelRenderer p_229081_11_, ModelRenderer p_229081_12_) {
         p_229081_10_.rotateAngleX = 1.0F + p_229081_5_ * 0.1F;
         p_229081_11_.rotateAngleX = 0.5F + p_229081_5_ * 0.1F;
         p_229081_12_.rotateAngleX = 0.75F + p_229081_5_ * 0.1F;
         p_229081_7_.rotateAngleX = 1.3F + p_229081_5_ * 0.1F;
         p_229081_8_.rotateAngleX = -0.5F - p_229081_5_ * 0.1F;
         p_229081_9_.rotateAngleX = 0.75F + p_229081_5_ * 0.1F;
         p_229081_6_.func_228308_a_(p_229081_1_, p_229081_2_, p_229081_3_, p_229081_4_);
         p_229081_7_.func_228308_a_(p_229081_1_, p_229081_2_, p_229081_3_, p_229081_4_);
         p_229081_10_.func_228308_a_(p_229081_1_, p_229081_2_, p_229081_3_, p_229081_4_);
      }
   }
}