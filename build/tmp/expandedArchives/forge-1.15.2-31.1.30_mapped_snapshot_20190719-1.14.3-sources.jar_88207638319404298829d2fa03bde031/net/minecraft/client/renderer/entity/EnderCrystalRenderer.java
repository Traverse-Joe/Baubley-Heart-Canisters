package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnderCrystalRenderer extends EntityRenderer<EnderCrystalEntity> {
   private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
   private static final RenderType field_229046_e_ = RenderType.func_228640_c_(ENDER_CRYSTAL_TEXTURES);
   private static final float field_229047_f_ = (float)Math.sin((Math.PI / 4D));
   private final ModelRenderer field_229048_g_;
   private final ModelRenderer field_229049_h_;
   private final ModelRenderer field_229050_i_;

   public EnderCrystalRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
      this.shadowSize = 0.5F;
      this.field_229049_h_ = new ModelRenderer(64, 32, 0, 0);
      this.field_229049_h_.func_228300_a_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.field_229048_g_ = new ModelRenderer(64, 32, 32, 0);
      this.field_229048_g_.func_228300_a_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.field_229050_i_ = new ModelRenderer(64, 32, 0, 16);
      this.field_229050_i_.func_228300_a_(-6.0F, 0.0F, -6.0F, 12.0F, 4.0F, 12.0F);
   }

   public void func_225623_a_(EnderCrystalEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      p_225623_4_.func_227860_a_();
      float f = func_229051_a_(p_225623_1_, p_225623_3_);
      float f1 = ((float)p_225623_1_.innerRotation + p_225623_3_) * 3.0F;
      IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(field_229046_e_);
      p_225623_4_.func_227860_a_();
      p_225623_4_.func_227862_a_(2.0F, 2.0F, 2.0F);
      p_225623_4_.func_227861_a_(0.0D, -0.5D, 0.0D);
      int i = OverlayTexture.field_229196_a_;
      if (p_225623_1_.shouldShowBottom()) {
         this.field_229050_i_.func_228308_a_(p_225623_4_, ivertexbuilder, p_225623_6_, i);
      }

      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f1));
      p_225623_4_.func_227861_a_(0.0D, (double)(1.5F + f / 2.0F), 0.0D);
      p_225623_4_.func_227863_a_(new Quaternion(new Vector3f(field_229047_f_, 0.0F, field_229047_f_), 60.0F, true));
      this.field_229049_h_.func_228308_a_(p_225623_4_, ivertexbuilder, p_225623_6_, i);
      float f2 = 0.875F;
      p_225623_4_.func_227862_a_(0.875F, 0.875F, 0.875F);
      p_225623_4_.func_227863_a_(new Quaternion(new Vector3f(field_229047_f_, 0.0F, field_229047_f_), 60.0F, true));
      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f1));
      this.field_229049_h_.func_228308_a_(p_225623_4_, ivertexbuilder, p_225623_6_, i);
      p_225623_4_.func_227862_a_(0.875F, 0.875F, 0.875F);
      p_225623_4_.func_227863_a_(new Quaternion(new Vector3f(field_229047_f_, 0.0F, field_229047_f_), 60.0F, true));
      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f1));
      this.field_229048_g_.func_228308_a_(p_225623_4_, ivertexbuilder, p_225623_6_, i);
      p_225623_4_.func_227865_b_();
      p_225623_4_.func_227865_b_();
      BlockPos blockpos = p_225623_1_.getBeamTarget();
      if (blockpos != null) {
         float f3 = (float)blockpos.getX() + 0.5F;
         float f4 = (float)blockpos.getY() + 0.5F;
         float f5 = (float)blockpos.getZ() + 0.5F;
         float f6 = (float)((double)f3 - p_225623_1_.func_226277_ct_());
         float f7 = (float)((double)f4 - p_225623_1_.func_226278_cu_());
         float f8 = (float)((double)f5 - p_225623_1_.func_226281_cx_());
         p_225623_4_.func_227861_a_((double)f6, (double)f7, (double)f8);
         EnderDragonRenderer.func_229059_a_(-f6, -f7 + f, -f8, p_225623_3_, p_225623_1_.innerRotation, p_225623_4_, p_225623_5_, p_225623_6_);
      }

      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
   }

   public static float func_229051_a_(EnderCrystalEntity p_229051_0_, float p_229051_1_) {
      float f = (float)p_229051_0_.innerRotation + p_229051_1_;
      float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
      f1 = (f1 * f1 + f1) * 0.4F;
      return f1 - 1.4F;
   }

   public ResourceLocation getEntityTexture(EnderCrystalEntity entity) {
      return ENDER_CRYSTAL_TEXTURES;
   }

   public boolean func_225626_a_(EnderCrystalEntity p_225626_1_, ClippingHelperImpl p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
      return super.func_225626_a_(p_225626_1_, p_225626_2_, p_225626_3_, p_225626_5_, p_225626_7_) || p_225626_1_.getBeamTarget() != null;
   }
}