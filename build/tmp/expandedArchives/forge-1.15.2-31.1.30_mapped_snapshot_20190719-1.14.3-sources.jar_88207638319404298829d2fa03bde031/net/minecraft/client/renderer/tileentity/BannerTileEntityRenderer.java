package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BannerTileEntityRenderer extends TileEntityRenderer<BannerTileEntity> {
   private final ModelRenderer field_228833_a_ = func_228836_a_();
   private final ModelRenderer field_228834_c_ = new ModelRenderer(64, 64, 44, 0);
   private final ModelRenderer field_228835_d_;

   public BannerTileEntityRenderer(TileEntityRendererDispatcher p_i226002_1_) {
      super(p_i226002_1_);
      this.field_228834_c_.func_228301_a_(-1.0F, -30.0F, -1.0F, 2.0F, 42.0F, 2.0F, 0.0F);
      this.field_228835_d_ = new ModelRenderer(64, 64, 0, 42);
      this.field_228835_d_.func_228301_a_(-10.0F, -32.0F, -1.0F, 20.0F, 2.0F, 2.0F, 0.0F);
   }

   public static ModelRenderer func_228836_a_() {
      ModelRenderer modelrenderer = new ModelRenderer(64, 64, 0, 0);
      modelrenderer.func_228301_a_(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F, 0.0F);
      return modelrenderer;
   }

   public void func_225616_a_(BannerTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      List<Pair<BannerPattern, DyeColor>> list = p_225616_1_.getPatternList();
      if (list != null) {
         float f = 0.6666667F;
         boolean flag = p_225616_1_.getWorld() == null;
         p_225616_3_.func_227860_a_();
         long i;
         if (flag) {
            i = 0L;
            p_225616_3_.func_227861_a_(0.5D, 0.5D, 0.5D);
            this.field_228834_c_.showModel = true;
         } else {
            i = p_225616_1_.getWorld().getGameTime();
            BlockState blockstate = p_225616_1_.getBlockState();
            if (blockstate.getBlock() instanceof BannerBlock) {
               p_225616_3_.func_227861_a_(0.5D, 0.5D, 0.5D);
               float f1 = (float)(-blockstate.get(BannerBlock.ROTATION) * 360) / 16.0F;
               p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f1));
               this.field_228834_c_.showModel = true;
            } else {
               p_225616_3_.func_227861_a_(0.5D, (double)-0.16666667F, 0.5D);
               float f3 = -blockstate.get(WallBannerBlock.HORIZONTAL_FACING).getHorizontalAngle();
               p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f3));
               p_225616_3_.func_227861_a_(0.0D, -0.3125D, -0.4375D);
               this.field_228834_c_.showModel = false;
            }
         }

         p_225616_3_.func_227860_a_();
         p_225616_3_.func_227862_a_(0.6666667F, -0.6666667F, -0.6666667F);
         IVertexBuilder ivertexbuilder = ModelBakery.field_229315_f_.func_229311_a_(p_225616_4_, RenderType::func_228634_a_);
         this.field_228834_c_.func_228308_a_(p_225616_3_, ivertexbuilder, p_225616_5_, p_225616_6_);
         this.field_228835_d_.func_228308_a_(p_225616_3_, ivertexbuilder, p_225616_5_, p_225616_6_);
         BlockPos blockpos = p_225616_1_.getPos();
         float f2 = ((float)Math.floorMod((long)(blockpos.getX() * 7 + blockpos.getY() * 9 + blockpos.getZ() * 13) + i, 100L) + p_225616_2_) / 100.0F;
         this.field_228833_a_.rotateAngleX = (-0.0125F + 0.01F * MathHelper.cos(((float)Math.PI * 2F) * f2)) * (float)Math.PI;
         this.field_228833_a_.rotationPointY = -32.0F;
         func_230180_a_(p_225616_3_, p_225616_4_, p_225616_5_, p_225616_6_, this.field_228833_a_, ModelBakery.field_229315_f_, true, list);
         p_225616_3_.func_227865_b_();
         p_225616_3_.func_227865_b_();
      }
   }

   public static void func_230180_a_(MatrixStack p_230180_0_, IRenderTypeBuffer p_230180_1_, int p_230180_2_, int p_230180_3_, ModelRenderer p_230180_4_, Material p_230180_5_, boolean p_230180_6_, List<Pair<BannerPattern, DyeColor>> p_230180_7_) {
      p_230180_4_.func_228308_a_(p_230180_0_, p_230180_5_.func_229311_a_(p_230180_1_, RenderType::func_228634_a_), p_230180_2_, p_230180_3_);

      for(int i = 0; i < 17 && i < p_230180_7_.size(); ++i) {
         Pair<BannerPattern, DyeColor> pair = p_230180_7_.get(i);
         float[] afloat = pair.getSecond().getColorComponentValues();
         Material material = new Material(p_230180_6_ ? Atlases.field_228744_c_ : Atlases.field_228745_d_, pair.getFirst().func_226957_a_(p_230180_6_));
         p_230180_4_.func_228309_a_(p_230180_0_, material.func_229311_a_(p_230180_1_, RenderType::func_228650_h_), p_230180_2_, p_230180_3_, afloat[0], afloat[1], afloat[2], 1.0F);
      }

   }
}