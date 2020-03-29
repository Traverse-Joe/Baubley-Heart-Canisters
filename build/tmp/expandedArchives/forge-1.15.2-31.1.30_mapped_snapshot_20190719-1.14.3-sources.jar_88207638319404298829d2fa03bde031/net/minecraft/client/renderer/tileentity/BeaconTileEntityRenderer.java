package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeaconTileEntityRenderer extends TileEntityRenderer<BeaconTileEntity> {
   public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("textures/entity/beacon_beam.png");

   public BeaconTileEntityRenderer(TileEntityRendererDispatcher p_i226003_1_) {
      super(p_i226003_1_);
   }

   public void func_225616_a_(BeaconTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      long i = p_225616_1_.getWorld().getGameTime();
      List<BeaconTileEntity.BeamSegment> list = p_225616_1_.getBeamSegments();
      int j = 0;

      for(int k = 0; k < list.size(); ++k) {
         BeaconTileEntity.BeamSegment beacontileentity$beamsegment = list.get(k);
         func_228841_a_(p_225616_3_, p_225616_4_, p_225616_2_, i, j, k == list.size() - 1 ? 1024 : beacontileentity$beamsegment.getHeight(), beacontileentity$beamsegment.getColors());
         j += beacontileentity$beamsegment.getHeight();
      }

   }

   private static void func_228841_a_(MatrixStack p_228841_0_, IRenderTypeBuffer p_228841_1_, float p_228841_2_, long p_228841_3_, int p_228841_5_, int p_228841_6_, float[] p_228841_7_) {
      func_228842_a_(p_228841_0_, p_228841_1_, TEXTURE_BEACON_BEAM, p_228841_2_, 1.0F, p_228841_3_, p_228841_5_, p_228841_6_, p_228841_7_, 0.2F, 0.25F);
   }

   public static void func_228842_a_(MatrixStack p_228842_0_, IRenderTypeBuffer p_228842_1_, ResourceLocation p_228842_2_, float p_228842_3_, float p_228842_4_, long p_228842_5_, int p_228842_7_, int p_228842_8_, float[] p_228842_9_, float p_228842_10_, float p_228842_11_) {
      int i = p_228842_7_ + p_228842_8_;
      p_228842_0_.func_227860_a_();
      p_228842_0_.func_227861_a_(0.5D, 0.0D, 0.5D);
      float f = (float)Math.floorMod(p_228842_5_, 40L) + p_228842_3_;
      float f1 = p_228842_8_ < 0 ? f : -f;
      float f2 = MathHelper.func_226164_h_(f1 * 0.2F - (float)MathHelper.floor(f1 * 0.1F));
      float f3 = p_228842_9_[0];
      float f4 = p_228842_9_[1];
      float f5 = p_228842_9_[2];
      p_228842_0_.func_227860_a_();
      p_228842_0_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f * 2.25F - 45.0F));
      float f6 = 0.0F;
      float f8 = 0.0F;
      float f9 = -p_228842_10_;
      float f10 = 0.0F;
      float f11 = 0.0F;
      float f12 = -p_228842_10_;
      float f13 = 0.0F;
      float f14 = 1.0F;
      float f15 = -1.0F + f2;
      float f16 = (float)p_228842_8_ * p_228842_4_ * (0.5F / p_228842_10_) + f15;
      func_228840_a_(p_228842_0_, p_228842_1_.getBuffer(RenderType.func_228637_a_(p_228842_2_, false)), f3, f4, f5, 1.0F, p_228842_7_, i, 0.0F, p_228842_10_, p_228842_10_, 0.0F, f9, 0.0F, 0.0F, f12, 0.0F, 1.0F, f16, f15);
      p_228842_0_.func_227865_b_();
      f6 = -p_228842_11_;
      float f7 = -p_228842_11_;
      f8 = -p_228842_11_;
      f9 = -p_228842_11_;
      f13 = 0.0F;
      f14 = 1.0F;
      f15 = -1.0F + f2;
      f16 = (float)p_228842_8_ * p_228842_4_ + f15;
      func_228840_a_(p_228842_0_, p_228842_1_.getBuffer(RenderType.func_228637_a_(p_228842_2_, true)), f3, f4, f5, 0.125F, p_228842_7_, i, f6, f7, p_228842_11_, f8, f9, p_228842_11_, p_228842_11_, p_228842_11_, 0.0F, 1.0F, f16, f15);
      p_228842_0_.func_227865_b_();
   }

   private static void func_228840_a_(MatrixStack p_228840_0_, IVertexBuilder p_228840_1_, float p_228840_2_, float p_228840_3_, float p_228840_4_, float p_228840_5_, int p_228840_6_, int p_228840_7_, float p_228840_8_, float p_228840_9_, float p_228840_10_, float p_228840_11_, float p_228840_12_, float p_228840_13_, float p_228840_14_, float p_228840_15_, float p_228840_16_, float p_228840_17_, float p_228840_18_, float p_228840_19_) {
      MatrixStack.Entry matrixstack$entry = p_228840_0_.func_227866_c_();
      Matrix4f matrix4f = matrixstack$entry.func_227870_a_();
      Matrix3f matrix3f = matrixstack$entry.func_227872_b_();
      func_228839_a_(matrix4f, matrix3f, p_228840_1_, p_228840_2_, p_228840_3_, p_228840_4_, p_228840_5_, p_228840_6_, p_228840_7_, p_228840_8_, p_228840_9_, p_228840_10_, p_228840_11_, p_228840_16_, p_228840_17_, p_228840_18_, p_228840_19_);
      func_228839_a_(matrix4f, matrix3f, p_228840_1_, p_228840_2_, p_228840_3_, p_228840_4_, p_228840_5_, p_228840_6_, p_228840_7_, p_228840_14_, p_228840_15_, p_228840_12_, p_228840_13_, p_228840_16_, p_228840_17_, p_228840_18_, p_228840_19_);
      func_228839_a_(matrix4f, matrix3f, p_228840_1_, p_228840_2_, p_228840_3_, p_228840_4_, p_228840_5_, p_228840_6_, p_228840_7_, p_228840_10_, p_228840_11_, p_228840_14_, p_228840_15_, p_228840_16_, p_228840_17_, p_228840_18_, p_228840_19_);
      func_228839_a_(matrix4f, matrix3f, p_228840_1_, p_228840_2_, p_228840_3_, p_228840_4_, p_228840_5_, p_228840_6_, p_228840_7_, p_228840_12_, p_228840_13_, p_228840_8_, p_228840_9_, p_228840_16_, p_228840_17_, p_228840_18_, p_228840_19_);
   }

   private static void func_228839_a_(Matrix4f p_228839_0_, Matrix3f p_228839_1_, IVertexBuilder p_228839_2_, float p_228839_3_, float p_228839_4_, float p_228839_5_, float p_228839_6_, int p_228839_7_, int p_228839_8_, float p_228839_9_, float p_228839_10_, float p_228839_11_, float p_228839_12_, float p_228839_13_, float p_228839_14_, float p_228839_15_, float p_228839_16_) {
      func_228838_a_(p_228839_0_, p_228839_1_, p_228839_2_, p_228839_3_, p_228839_4_, p_228839_5_, p_228839_6_, p_228839_8_, p_228839_9_, p_228839_10_, p_228839_14_, p_228839_15_);
      func_228838_a_(p_228839_0_, p_228839_1_, p_228839_2_, p_228839_3_, p_228839_4_, p_228839_5_, p_228839_6_, p_228839_7_, p_228839_9_, p_228839_10_, p_228839_14_, p_228839_16_);
      func_228838_a_(p_228839_0_, p_228839_1_, p_228839_2_, p_228839_3_, p_228839_4_, p_228839_5_, p_228839_6_, p_228839_7_, p_228839_11_, p_228839_12_, p_228839_13_, p_228839_16_);
      func_228838_a_(p_228839_0_, p_228839_1_, p_228839_2_, p_228839_3_, p_228839_4_, p_228839_5_, p_228839_6_, p_228839_8_, p_228839_11_, p_228839_12_, p_228839_13_, p_228839_15_);
   }

   private static void func_228838_a_(Matrix4f p_228838_0_, Matrix3f p_228838_1_, IVertexBuilder p_228838_2_, float p_228838_3_, float p_228838_4_, float p_228838_5_, float p_228838_6_, int p_228838_7_, float p_228838_8_, float p_228838_9_, float p_228838_10_, float p_228838_11_) {
      p_228838_2_.func_227888_a_(p_228838_0_, p_228838_8_, (float)p_228838_7_, p_228838_9_).func_227885_a_(p_228838_3_, p_228838_4_, p_228838_5_, p_228838_6_).func_225583_a_(p_228838_10_, p_228838_11_).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(15728880).func_227887_a_(p_228838_1_, 0.0F, 1.0F, 0.0F).endVertex();
   }

   public boolean isGlobalRenderer(BeaconTileEntity te) {
      return true;
   }
}