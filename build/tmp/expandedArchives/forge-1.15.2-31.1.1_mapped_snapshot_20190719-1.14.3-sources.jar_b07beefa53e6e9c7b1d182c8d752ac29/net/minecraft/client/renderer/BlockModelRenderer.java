package net.minecraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ILightReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockModelRenderer {
   private final BlockColors blockColors;
   private static final ThreadLocal<BlockModelRenderer.Cache> CACHE_COMBINED_LIGHT = ThreadLocal.withInitial(() -> {
      return new BlockModelRenderer.Cache();
   });

   public BlockModelRenderer(BlockColors blockColorsIn) {
      this.blockColors = blockColorsIn;
   }

   @Deprecated //Forge: Model data argument
   public boolean func_228802_a_(ILightReader p_228802_1_, IBakedModel p_228802_2_, BlockState p_228802_3_, BlockPos p_228802_4_, MatrixStack p_228802_5_, IVertexBuilder p_228802_6_, boolean p_228802_7_, Random p_228802_8_, long p_228802_9_, int p_228802_11_) {
      return renderModel(p_228802_1_, p_228802_2_, p_228802_3_, p_228802_4_, p_228802_5_, p_228802_6_, p_228802_7_, p_228802_8_, p_228802_9_, p_228802_11_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public boolean renderModel(ILightReader p_228802_1_, IBakedModel p_228802_2_, BlockState p_228802_3_, BlockPos p_228802_4_, MatrixStack p_228802_5_, IVertexBuilder p_228802_6_, boolean p_228802_7_, Random p_228802_8_, long p_228802_9_, int p_228802_11_, net.minecraftforge.client.model.data.IModelData modelData) {
      boolean flag = Minecraft.isAmbientOcclusionEnabled() && p_228802_3_.getLightValue(p_228802_1_, p_228802_4_) == 0 && p_228802_2_.isAmbientOcclusion();
      Vec3d vec3d = p_228802_3_.getOffset(p_228802_1_, p_228802_4_);
      p_228802_5_.func_227861_a_(vec3d.x, vec3d.y, vec3d.z);
      modelData = p_228802_2_.getModelData(p_228802_1_, p_228802_4_, p_228802_3_, modelData);

      try {
         return flag ? this.renderModelSmooth(p_228802_1_, p_228802_2_, p_228802_3_, p_228802_4_, p_228802_5_, p_228802_6_, p_228802_7_, p_228802_8_, p_228802_9_, p_228802_11_, modelData) : this.renderModelFlat(p_228802_1_, p_228802_2_, p_228802_3_, p_228802_4_, p_228802_5_, p_228802_6_, p_228802_7_, p_228802_8_, p_228802_9_, p_228802_11_, modelData);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block model");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block model being tesselated");
         CrashReportCategory.addBlockInfo(crashreportcategory, p_228802_4_, p_228802_3_);
         crashreportcategory.addDetail("Using AO", flag);
         throw new ReportedException(crashreport);
      }
   }

   @Deprecated //Forge: Model data argument
   public boolean func_228805_b_(ILightReader p_228805_1_, IBakedModel p_228805_2_, BlockState p_228805_3_, BlockPos p_228805_4_, MatrixStack p_228805_5_, IVertexBuilder p_228805_6_, boolean p_228805_7_, Random p_228805_8_, long p_228805_9_, int p_228805_11_) {
      return renderModelSmooth(p_228805_1_, p_228805_2_, p_228805_3_, p_228805_4_, p_228805_5_, p_228805_6_, p_228805_7_, p_228805_8_, p_228805_9_, p_228805_11_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public boolean renderModelSmooth(ILightReader p_228805_1_, IBakedModel p_228805_2_, BlockState p_228805_3_, BlockPos p_228805_4_, MatrixStack p_228805_5_, IVertexBuilder p_228805_6_, boolean p_228805_7_, Random p_228805_8_, long p_228805_9_, int p_228805_11_, net.minecraftforge.client.model.data.IModelData modelData) {
      boolean flag = false;
      float[] afloat = new float[Direction.values().length * 2];
      BitSet bitset = new BitSet(3);
      BlockModelRenderer.AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = new BlockModelRenderer.AmbientOcclusionFace();

      for(Direction direction : Direction.values()) {
         p_228805_8_.setSeed(p_228805_9_);
         List<BakedQuad> list = p_228805_2_.getQuads(p_228805_3_, direction, p_228805_8_, modelData);
         if (!list.isEmpty() && (!p_228805_7_ || Block.shouldSideBeRendered(p_228805_3_, p_228805_1_, p_228805_4_, direction))) {
            this.func_228799_a_(p_228805_1_, p_228805_3_, p_228805_4_, p_228805_5_, p_228805_6_, list, afloat, bitset, blockmodelrenderer$ambientocclusionface, p_228805_11_);
            flag = true;
         }
      }

      p_228805_8_.setSeed(p_228805_9_);
      List<BakedQuad> list1 = p_228805_2_.getQuads(p_228805_3_, (Direction)null, p_228805_8_, modelData);
      if (!list1.isEmpty()) {
         this.func_228799_a_(p_228805_1_, p_228805_3_, p_228805_4_, p_228805_5_, p_228805_6_, list1, afloat, bitset, blockmodelrenderer$ambientocclusionface, p_228805_11_);
         flag = true;
      }

      return flag;
   }

   @Deprecated //Forge: Model data argument
   public boolean func_228806_c_(ILightReader p_228806_1_, IBakedModel p_228806_2_, BlockState p_228806_3_, BlockPos p_228806_4_, MatrixStack p_228806_5_, IVertexBuilder p_228806_6_, boolean p_228806_7_, Random p_228806_8_, long p_228806_9_, int p_228806_11_) {
      return renderModelFlat(p_228806_1_, p_228806_2_, p_228806_3_, p_228806_4_, p_228806_5_, p_228806_6_, p_228806_7_, p_228806_8_, p_228806_9_, p_228806_11_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }

   public boolean renderModelFlat(ILightReader p_228806_1_, IBakedModel p_228806_2_, BlockState p_228806_3_, BlockPos p_228806_4_, MatrixStack p_228806_5_, IVertexBuilder p_228806_6_, boolean p_228806_7_, Random p_228806_8_, long p_228806_9_, int p_228806_11_, net.minecraftforge.client.model.data.IModelData modelData) {
      boolean flag = false;
      BitSet bitset = new BitSet(3);

      for(Direction direction : Direction.values()) {
         p_228806_8_.setSeed(p_228806_9_);
         List<BakedQuad> list = p_228806_2_.getQuads(p_228806_3_, direction, p_228806_8_, modelData);
         if (!list.isEmpty() && (!p_228806_7_ || Block.shouldSideBeRendered(p_228806_3_, p_228806_1_, p_228806_4_, direction))) {
            int i = WorldRenderer.func_228420_a_(p_228806_1_, p_228806_3_, p_228806_4_.offset(direction));
            this.func_228798_a_(p_228806_1_, p_228806_3_, p_228806_4_, i, p_228806_11_, false, p_228806_5_, p_228806_6_, list, bitset);
            flag = true;
         }
      }

      p_228806_8_.setSeed(p_228806_9_);
      List<BakedQuad> list1 = p_228806_2_.getQuads(p_228806_3_, (Direction)null, p_228806_8_, modelData);
      if (!list1.isEmpty()) {
         this.func_228798_a_(p_228806_1_, p_228806_3_, p_228806_4_, -1, p_228806_11_, true, p_228806_5_, p_228806_6_, list1, bitset);
         flag = true;
      }

      return flag;
   }

   private void func_228799_a_(ILightReader p_228799_1_, BlockState p_228799_2_, BlockPos p_228799_3_, MatrixStack p_228799_4_, IVertexBuilder p_228799_5_, List<BakedQuad> p_228799_6_, float[] p_228799_7_, BitSet p_228799_8_, BlockModelRenderer.AmbientOcclusionFace p_228799_9_, int p_228799_10_) {
      for(BakedQuad bakedquad : p_228799_6_) {
         this.func_228801_a_(p_228799_1_, p_228799_2_, p_228799_3_, bakedquad.getVertexData(), bakedquad.getFace(), p_228799_7_, p_228799_8_);
         p_228799_9_.func_228807_a_(p_228799_1_, p_228799_2_, p_228799_3_, bakedquad.getFace(), p_228799_7_, p_228799_8_);
         this.func_228800_a_(p_228799_1_, p_228799_2_, p_228799_3_, p_228799_5_, p_228799_4_.func_227866_c_(), bakedquad, p_228799_9_.vertexColorMultiplier[0], p_228799_9_.vertexColorMultiplier[1], p_228799_9_.vertexColorMultiplier[2], p_228799_9_.vertexColorMultiplier[3], p_228799_9_.vertexBrightness[0], p_228799_9_.vertexBrightness[1], p_228799_9_.vertexBrightness[2], p_228799_9_.vertexBrightness[3], p_228799_10_);
      }

   }

   private void func_228800_a_(ILightReader p_228800_1_, BlockState p_228800_2_, BlockPos p_228800_3_, IVertexBuilder p_228800_4_, MatrixStack.Entry p_228800_5_, BakedQuad p_228800_6_, float p_228800_7_, float p_228800_8_, float p_228800_9_, float p_228800_10_, int p_228800_11_, int p_228800_12_, int p_228800_13_, int p_228800_14_, int p_228800_15_) {
      float f;
      float f1;
      float f2;
      if (p_228800_6_.hasTintIndex()) {
         int i = this.blockColors.func_228054_a_(p_228800_2_, p_228800_1_, p_228800_3_, p_228800_6_.getTintIndex());
         f = (float)(i >> 16 & 255) / 255.0F;
         f1 = (float)(i >> 8 & 255) / 255.0F;
         f2 = (float)(i & 255) / 255.0F;
      } else {
         f = 1.0F;
         f1 = 1.0F;
         f2 = 1.0F;
      }
      // FORGE: Apply diffuse lighting at render-time instead of baking it in
      if (p_228800_6_.shouldApplyDiffuseLighting()) {
         // TODO this should be handled by the forge lighting pipeline
         float l = net.minecraftforge.client.model.pipeline.LightUtil.diffuseLight(p_228800_6_.getFace());
         f *= l;
         f1 *= l;
         f2 *= l;
      }

      p_228800_4_.func_227890_a_(p_228800_5_, p_228800_6_, new float[]{p_228800_7_, p_228800_8_, p_228800_9_, p_228800_10_}, f, f1, f2, new int[]{p_228800_11_, p_228800_12_, p_228800_13_, p_228800_14_}, p_228800_15_, true);
   }

   private void func_228801_a_(ILightReader p_228801_1_, BlockState p_228801_2_, BlockPos p_228801_3_, int[] p_228801_4_, Direction p_228801_5_, @Nullable float[] p_228801_6_, BitSet p_228801_7_) {
      float f = 32.0F;
      float f1 = 32.0F;
      float f2 = 32.0F;
      float f3 = -32.0F;
      float f4 = -32.0F;
      float f5 = -32.0F;

      for(int i = 0; i < 4; ++i) {
         float f6 = Float.intBitsToFloat(p_228801_4_[i * 8]);
         float f7 = Float.intBitsToFloat(p_228801_4_[i * 8 + 1]);
         float f8 = Float.intBitsToFloat(p_228801_4_[i * 8 + 2]);
         f = Math.min(f, f6);
         f1 = Math.min(f1, f7);
         f2 = Math.min(f2, f8);
         f3 = Math.max(f3, f6);
         f4 = Math.max(f4, f7);
         f5 = Math.max(f5, f8);
      }

      if (p_228801_6_ != null) {
         p_228801_6_[Direction.WEST.getIndex()] = f;
         p_228801_6_[Direction.EAST.getIndex()] = f3;
         p_228801_6_[Direction.DOWN.getIndex()] = f1;
         p_228801_6_[Direction.UP.getIndex()] = f4;
         p_228801_6_[Direction.NORTH.getIndex()] = f2;
         p_228801_6_[Direction.SOUTH.getIndex()] = f5;
         int j = Direction.values().length;
         p_228801_6_[Direction.WEST.getIndex() + j] = 1.0F - f;
         p_228801_6_[Direction.EAST.getIndex() + j] = 1.0F - f3;
         p_228801_6_[Direction.DOWN.getIndex() + j] = 1.0F - f1;
         p_228801_6_[Direction.UP.getIndex() + j] = 1.0F - f4;
         p_228801_6_[Direction.NORTH.getIndex() + j] = 1.0F - f2;
         p_228801_6_[Direction.SOUTH.getIndex() + j] = 1.0F - f5;
      }

      float f9 = 1.0E-4F;
      float f10 = 0.9999F;
      switch(p_228801_5_) {
      case DOWN:
         p_228801_7_.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
         p_228801_7_.set(0, f1 == f4 && (f1 < 1.0E-4F || p_228801_2_.func_224756_o(p_228801_1_, p_228801_3_)));
         break;
      case UP:
         p_228801_7_.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
         p_228801_7_.set(0, f1 == f4 && (f4 > 0.9999F || p_228801_2_.func_224756_o(p_228801_1_, p_228801_3_)));
         break;
      case NORTH:
         p_228801_7_.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
         p_228801_7_.set(0, f2 == f5 && (f2 < 1.0E-4F || p_228801_2_.func_224756_o(p_228801_1_, p_228801_3_)));
         break;
      case SOUTH:
         p_228801_7_.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
         p_228801_7_.set(0, f2 == f5 && (f5 > 0.9999F || p_228801_2_.func_224756_o(p_228801_1_, p_228801_3_)));
         break;
      case WEST:
         p_228801_7_.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
         p_228801_7_.set(0, f == f3 && (f < 1.0E-4F || p_228801_2_.func_224756_o(p_228801_1_, p_228801_3_)));
         break;
      case EAST:
         p_228801_7_.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
         p_228801_7_.set(0, f == f3 && (f3 > 0.9999F || p_228801_2_.func_224756_o(p_228801_1_, p_228801_3_)));
      }

   }

   private void func_228798_a_(ILightReader p_228798_1_, BlockState p_228798_2_, BlockPos p_228798_3_, int p_228798_4_, int p_228798_5_, boolean p_228798_6_, MatrixStack p_228798_7_, IVertexBuilder p_228798_8_, List<BakedQuad> p_228798_9_, BitSet p_228798_10_) {
      for(BakedQuad bakedquad : p_228798_9_) {
         if (p_228798_6_) {
            this.func_228801_a_(p_228798_1_, p_228798_2_, p_228798_3_, bakedquad.getVertexData(), bakedquad.getFace(), (float[])null, p_228798_10_);
            BlockPos blockpos = p_228798_10_.get(0) ? p_228798_3_.offset(bakedquad.getFace()) : p_228798_3_;
            p_228798_4_ = WorldRenderer.func_228420_a_(p_228798_1_, p_228798_2_, blockpos);
         }

         this.func_228800_a_(p_228798_1_, p_228798_2_, p_228798_3_, p_228798_8_, p_228798_7_.func_227866_c_(), bakedquad, 1.0F, 1.0F, 1.0F, 1.0F, p_228798_4_, p_228798_4_, p_228798_4_, p_228798_4_, p_228798_5_);
      }

   }

   @Deprecated //Forge: Model data argument
   public void func_228804_a_(MatrixStack.Entry p_228804_1_, IVertexBuilder p_228804_2_, @Nullable BlockState p_228804_3_, IBakedModel p_228804_4_, float p_228804_5_, float p_228804_6_, float p_228804_7_, int p_228804_8_, int p_228804_9_) {
      renderModel(p_228804_1_, p_228804_2_, p_228804_3_, p_228804_4_, p_228804_5_, p_228804_6_, p_228804_7_, p_228804_8_, p_228804_9_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public void renderModel(MatrixStack.Entry p_228804_1_, IVertexBuilder p_228804_2_, @Nullable BlockState p_228804_3_, IBakedModel p_228804_4_, float p_228804_5_, float p_228804_6_, float p_228804_7_, int p_228804_8_, int p_228804_9_, net.minecraftforge.client.model.data.IModelData modelData) {
      Random random = new Random();
      long i = 42L;

      for(Direction direction : Direction.values()) {
         random.setSeed(42L);
         func_228803_a_(p_228804_1_, p_228804_2_, p_228804_5_, p_228804_6_, p_228804_7_, p_228804_4_.getQuads(p_228804_3_, direction, random), p_228804_8_, p_228804_9_);
      }

      random.setSeed(42L);
      func_228803_a_(p_228804_1_, p_228804_2_, p_228804_5_, p_228804_6_, p_228804_7_, p_228804_4_.getQuads(p_228804_3_, (Direction)null, random), p_228804_8_, p_228804_9_);
   }

   private static void func_228803_a_(MatrixStack.Entry p_228803_0_, IVertexBuilder p_228803_1_, float p_228803_2_, float p_228803_3_, float p_228803_4_, List<BakedQuad> p_228803_5_, int p_228803_6_, int p_228803_7_) {
      for(BakedQuad bakedquad : p_228803_5_) {
         float f;
         float f1;
         float f2;
         if (bakedquad.hasTintIndex()) {
            f = MathHelper.clamp(p_228803_2_, 0.0F, 1.0F);
            f1 = MathHelper.clamp(p_228803_3_, 0.0F, 1.0F);
            f2 = MathHelper.clamp(p_228803_4_, 0.0F, 1.0F);
         } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
         }

         p_228803_1_.func_227889_a_(p_228803_0_, bakedquad, f, f1, f2, p_228803_6_, p_228803_7_);
      }

   }

   public static void enableCache() {
      CACHE_COMBINED_LIGHT.get().func_222895_a();
   }

   public static void disableCache() {
      CACHE_COMBINED_LIGHT.get().func_222897_b();
   }

   @OnlyIn(Dist.CLIENT)
   class AmbientOcclusionFace {
      private final float[] vertexColorMultiplier = new float[4];
      private final int[] vertexBrightness = new int[4];

      public AmbientOcclusionFace() {
      }

      public void func_228807_a_(ILightReader p_228807_1_, BlockState p_228807_2_, BlockPos p_228807_3_, Direction p_228807_4_, float[] p_228807_5_, BitSet p_228807_6_) {
         BlockPos blockpos = p_228807_6_.get(0) ? p_228807_3_.offset(p_228807_4_) : p_228807_3_;
         BlockModelRenderer.NeighborInfo blockmodelrenderer$neighborinfo = BlockModelRenderer.NeighborInfo.getNeighbourInfo(p_228807_4_);
         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
         BlockModelRenderer.Cache blockmodelrenderer$cache = BlockModelRenderer.CACHE_COMBINED_LIGHT.get();
         blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[0]);
         BlockState blockstate = p_228807_1_.getBlockState(blockpos$mutable);
         int i = blockmodelrenderer$cache.func_228810_a_(blockstate, p_228807_1_, blockpos$mutable);
         float f = blockmodelrenderer$cache.func_228811_b_(blockstate, p_228807_1_, blockpos$mutable);
         blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[1]);
         BlockState blockstate1 = p_228807_1_.getBlockState(blockpos$mutable);
         int j = blockmodelrenderer$cache.func_228810_a_(blockstate1, p_228807_1_, blockpos$mutable);
         float f1 = blockmodelrenderer$cache.func_228811_b_(blockstate1, p_228807_1_, blockpos$mutable);
         blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[2]);
         BlockState blockstate2 = p_228807_1_.getBlockState(blockpos$mutable);
         int k = blockmodelrenderer$cache.func_228810_a_(blockstate2, p_228807_1_, blockpos$mutable);
         float f2 = blockmodelrenderer$cache.func_228811_b_(blockstate2, p_228807_1_, blockpos$mutable);
         blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[3]);
         BlockState blockstate3 = p_228807_1_.getBlockState(blockpos$mutable);
         int l = blockmodelrenderer$cache.func_228810_a_(blockstate3, p_228807_1_, blockpos$mutable);
         float f3 = blockmodelrenderer$cache.func_228811_b_(blockstate3, p_228807_1_, blockpos$mutable);
         blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[0]).move(p_228807_4_);
         boolean flag = p_228807_1_.getBlockState(blockpos$mutable).getOpacity(p_228807_1_, blockpos$mutable) == 0;
         blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[1]).move(p_228807_4_);
         boolean flag1 = p_228807_1_.getBlockState(blockpos$mutable).getOpacity(p_228807_1_, blockpos$mutable) == 0;
         blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[2]).move(p_228807_4_);
         boolean flag2 = p_228807_1_.getBlockState(blockpos$mutable).getOpacity(p_228807_1_, blockpos$mutable) == 0;
         blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[3]).move(p_228807_4_);
         boolean flag3 = p_228807_1_.getBlockState(blockpos$mutable).getOpacity(p_228807_1_, blockpos$mutable) == 0;
         float f4;
         int i1;
         if (!flag2 && !flag) {
            f4 = f;
            i1 = i;
         } else {
            blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[0]).move(blockmodelrenderer$neighborinfo.corners[2]);
            BlockState blockstate4 = p_228807_1_.getBlockState(blockpos$mutable);
            f4 = blockmodelrenderer$cache.func_228811_b_(blockstate4, p_228807_1_, blockpos$mutable);
            i1 = blockmodelrenderer$cache.func_228810_a_(blockstate4, p_228807_1_, blockpos$mutable);
         }

         float f5;
         int j1;
         if (!flag3 && !flag) {
            f5 = f;
            j1 = i;
         } else {
            blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[0]).move(blockmodelrenderer$neighborinfo.corners[3]);
            BlockState blockstate6 = p_228807_1_.getBlockState(blockpos$mutable);
            f5 = blockmodelrenderer$cache.func_228811_b_(blockstate6, p_228807_1_, blockpos$mutable);
            j1 = blockmodelrenderer$cache.func_228810_a_(blockstate6, p_228807_1_, blockpos$mutable);
         }

         float f6;
         int k1;
         if (!flag2 && !flag1) {
            f6 = f;
            k1 = i;
         } else {
            blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[1]).move(blockmodelrenderer$neighborinfo.corners[2]);
            BlockState blockstate7 = p_228807_1_.getBlockState(blockpos$mutable);
            f6 = blockmodelrenderer$cache.func_228811_b_(blockstate7, p_228807_1_, blockpos$mutable);
            k1 = blockmodelrenderer$cache.func_228810_a_(blockstate7, p_228807_1_, blockpos$mutable);
         }

         float f7;
         int l1;
         if (!flag3 && !flag1) {
            f7 = f;
            l1 = i;
         } else {
            blockpos$mutable.setPos(blockpos).move(blockmodelrenderer$neighborinfo.corners[1]).move(blockmodelrenderer$neighborinfo.corners[3]);
            BlockState blockstate8 = p_228807_1_.getBlockState(blockpos$mutable);
            f7 = blockmodelrenderer$cache.func_228811_b_(blockstate8, p_228807_1_, blockpos$mutable);
            l1 = blockmodelrenderer$cache.func_228810_a_(blockstate8, p_228807_1_, blockpos$mutable);
         }

         int i3 = blockmodelrenderer$cache.func_228810_a_(p_228807_2_, p_228807_1_, p_228807_3_);
         blockpos$mutable.setPos(p_228807_3_).move(p_228807_4_);
         BlockState blockstate5 = p_228807_1_.getBlockState(blockpos$mutable);
         if (p_228807_6_.get(0) || !blockstate5.isOpaqueCube(p_228807_1_, blockpos$mutable)) {
            i3 = blockmodelrenderer$cache.func_228810_a_(blockstate5, p_228807_1_, blockpos$mutable);
         }

         float f8 = p_228807_6_.get(0) ? blockmodelrenderer$cache.func_228811_b_(p_228807_1_.getBlockState(blockpos), p_228807_1_, blockpos) : blockmodelrenderer$cache.func_228811_b_(p_228807_1_.getBlockState(p_228807_3_), p_228807_1_, p_228807_3_);
         BlockModelRenderer.VertexTranslations blockmodelrenderer$vertextranslations = BlockModelRenderer.VertexTranslations.getVertexTranslations(p_228807_4_);
         if (p_228807_6_.get(1) && blockmodelrenderer$neighborinfo.doNonCubicWeight) {
            float f29 = (f3 + f + f5 + f8) * 0.25F;
            float f30 = (f2 + f + f4 + f8) * 0.25F;
            float f31 = (f2 + f1 + f6 + f8) * 0.25F;
            float f32 = (f3 + f1 + f7 + f8) * 0.25F;
            float f13 = p_228807_5_[blockmodelrenderer$neighborinfo.vert0Weights[0].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert0Weights[1].shape];
            float f14 = p_228807_5_[blockmodelrenderer$neighborinfo.vert0Weights[2].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert0Weights[3].shape];
            float f15 = p_228807_5_[blockmodelrenderer$neighborinfo.vert0Weights[4].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert0Weights[5].shape];
            float f16 = p_228807_5_[blockmodelrenderer$neighborinfo.vert0Weights[6].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert0Weights[7].shape];
            float f17 = p_228807_5_[blockmodelrenderer$neighborinfo.vert1Weights[0].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert1Weights[1].shape];
            float f18 = p_228807_5_[blockmodelrenderer$neighborinfo.vert1Weights[2].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert1Weights[3].shape];
            float f19 = p_228807_5_[blockmodelrenderer$neighborinfo.vert1Weights[4].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert1Weights[5].shape];
            float f20 = p_228807_5_[blockmodelrenderer$neighborinfo.vert1Weights[6].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert1Weights[7].shape];
            float f21 = p_228807_5_[blockmodelrenderer$neighborinfo.vert2Weights[0].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert2Weights[1].shape];
            float f22 = p_228807_5_[blockmodelrenderer$neighborinfo.vert2Weights[2].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert2Weights[3].shape];
            float f23 = p_228807_5_[blockmodelrenderer$neighborinfo.vert2Weights[4].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert2Weights[5].shape];
            float f24 = p_228807_5_[blockmodelrenderer$neighborinfo.vert2Weights[6].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert2Weights[7].shape];
            float f25 = p_228807_5_[blockmodelrenderer$neighborinfo.vert3Weights[0].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert3Weights[1].shape];
            float f26 = p_228807_5_[blockmodelrenderer$neighborinfo.vert3Weights[2].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert3Weights[3].shape];
            float f27 = p_228807_5_[blockmodelrenderer$neighborinfo.vert3Weights[4].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert3Weights[5].shape];
            float f28 = p_228807_5_[blockmodelrenderer$neighborinfo.vert3Weights[6].shape] * p_228807_5_[blockmodelrenderer$neighborinfo.vert3Weights[7].shape];
            this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert0] = f29 * f13 + f30 * f14 + f31 * f15 + f32 * f16;
            this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert1] = f29 * f17 + f30 * f18 + f31 * f19 + f32 * f20;
            this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert2] = f29 * f21 + f30 * f22 + f31 * f23 + f32 * f24;
            this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert3] = f29 * f25 + f30 * f26 + f31 * f27 + f32 * f28;
            int i2 = this.getAoBrightness(l, i, j1, i3);
            int j2 = this.getAoBrightness(k, i, i1, i3);
            int k2 = this.getAoBrightness(k, j, k1, i3);
            int l2 = this.getAoBrightness(l, j, l1, i3);
            this.vertexBrightness[blockmodelrenderer$vertextranslations.vert0] = this.getVertexBrightness(i2, j2, k2, l2, f13, f14, f15, f16);
            this.vertexBrightness[blockmodelrenderer$vertextranslations.vert1] = this.getVertexBrightness(i2, j2, k2, l2, f17, f18, f19, f20);
            this.vertexBrightness[blockmodelrenderer$vertextranslations.vert2] = this.getVertexBrightness(i2, j2, k2, l2, f21, f22, f23, f24);
            this.vertexBrightness[blockmodelrenderer$vertextranslations.vert3] = this.getVertexBrightness(i2, j2, k2, l2, f25, f26, f27, f28);
         } else {
            float f9 = (f3 + f + f5 + f8) * 0.25F;
            float f10 = (f2 + f + f4 + f8) * 0.25F;
            float f11 = (f2 + f1 + f6 + f8) * 0.25F;
            float f12 = (f3 + f1 + f7 + f8) * 0.25F;
            this.vertexBrightness[blockmodelrenderer$vertextranslations.vert0] = this.getAoBrightness(l, i, j1, i3);
            this.vertexBrightness[blockmodelrenderer$vertextranslations.vert1] = this.getAoBrightness(k, i, i1, i3);
            this.vertexBrightness[blockmodelrenderer$vertextranslations.vert2] = this.getAoBrightness(k, j, k1, i3);
            this.vertexBrightness[blockmodelrenderer$vertextranslations.vert3] = this.getAoBrightness(l, j, l1, i3);
            this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert0] = f9;
            this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert1] = f10;
            this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert2] = f11;
            this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert3] = f12;
         }

      }

      /**
       * Get ambient occlusion brightness
       */
      private int getAoBrightness(int br1, int br2, int br3, int br4) {
         if (br1 == 0) {
            br1 = br4;
         }

         if (br2 == 0) {
            br2 = br4;
         }

         if (br3 == 0) {
            br3 = br4;
         }

         return br1 + br2 + br3 + br4 >> 2 & 16711935;
      }

      private int getVertexBrightness(int b1, int b2, int b3, int b4, float w1, float w2, float w3, float w4) {
         int i = (int)((float)(b1 >> 16 & 255) * w1 + (float)(b2 >> 16 & 255) * w2 + (float)(b3 >> 16 & 255) * w3 + (float)(b4 >> 16 & 255) * w4) & 255;
         int j = (int)((float)(b1 & 255) * w1 + (float)(b2 & 255) * w2 + (float)(b3 & 255) * w3 + (float)(b4 & 255) * w4) & 255;
         return i << 16 | j;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Cache {
      private boolean field_222898_a;
      private final Long2IntLinkedOpenHashMap field_222899_b = Util.make(() -> {
         Long2IntLinkedOpenHashMap long2intlinkedopenhashmap = new Long2IntLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int p_rehash_1_) {
            }
         };
         long2intlinkedopenhashmap.defaultReturnValue(Integer.MAX_VALUE);
         return long2intlinkedopenhashmap;
      });
      private final Long2FloatLinkedOpenHashMap field_222900_c = Util.make(() -> {
         Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = new Long2FloatLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int p_rehash_1_) {
            }
         };
         long2floatlinkedopenhashmap.defaultReturnValue(Float.NaN);
         return long2floatlinkedopenhashmap;
      });

      private Cache() {
      }

      public void func_222895_a() {
         this.field_222898_a = true;
      }

      public void func_222897_b() {
         this.field_222898_a = false;
         this.field_222899_b.clear();
         this.field_222900_c.clear();
      }

      public int func_228810_a_(BlockState p_228810_1_, ILightReader p_228810_2_, BlockPos p_228810_3_) {
         long i = p_228810_3_.toLong();
         if (this.field_222898_a) {
            int j = this.field_222899_b.get(i);
            if (j != Integer.MAX_VALUE) {
               return j;
            }
         }

         int k = WorldRenderer.func_228420_a_(p_228810_2_, p_228810_1_, p_228810_3_);
         if (this.field_222898_a) {
            if (this.field_222899_b.size() == 100) {
               this.field_222899_b.removeFirstInt();
            }

            this.field_222899_b.put(i, k);
         }

         return k;
      }

      public float func_228811_b_(BlockState p_228811_1_, ILightReader p_228811_2_, BlockPos p_228811_3_) {
         long i = p_228811_3_.toLong();
         if (this.field_222898_a) {
            float f = this.field_222900_c.get(i);
            if (!Float.isNaN(f)) {
               return f;
            }
         }

         float f1 = p_228811_1_.func_215703_d(p_228811_2_, p_228811_3_);
         if (this.field_222898_a) {
            if (this.field_222900_c.size() == 100) {
               this.field_222900_c.removeFirstFloat();
            }

            this.field_222900_c.put(i, f1);
         }

         return f1;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum NeighborInfo {
      DOWN(new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}, 0.5F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH}),
      UP(new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH}, 1.0F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH}),
      NORTH(new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST}, 0.8F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST}),
      SOUTH(new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP}, 0.8F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST}),
      WEST(new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH}, 0.6F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH}),
      EAST(new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH}, 0.6F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH});

      private final Direction[] corners;
      private final boolean doNonCubicWeight;
      private final BlockModelRenderer.Orientation[] vert0Weights;
      private final BlockModelRenderer.Orientation[] vert1Weights;
      private final BlockModelRenderer.Orientation[] vert2Weights;
      private final BlockModelRenderer.Orientation[] vert3Weights;
      private static final BlockModelRenderer.NeighborInfo[] VALUES = Util.make(new BlockModelRenderer.NeighborInfo[6], (p_209260_0_) -> {
         p_209260_0_[Direction.DOWN.getIndex()] = DOWN;
         p_209260_0_[Direction.UP.getIndex()] = UP;
         p_209260_0_[Direction.NORTH.getIndex()] = NORTH;
         p_209260_0_[Direction.SOUTH.getIndex()] = SOUTH;
         p_209260_0_[Direction.WEST.getIndex()] = WEST;
         p_209260_0_[Direction.EAST.getIndex()] = EAST;
      });

      private NeighborInfo(Direction[] cornersIn, float brightness, boolean doNonCubicWeightIn, BlockModelRenderer.Orientation[] vert0WeightsIn, BlockModelRenderer.Orientation[] vert1WeightsIn, BlockModelRenderer.Orientation[] vert2WeightsIn, BlockModelRenderer.Orientation[] vert3WeightsIn) {
         this.corners = cornersIn;
         this.doNonCubicWeight = doNonCubicWeightIn;
         this.vert0Weights = vert0WeightsIn;
         this.vert1Weights = vert1WeightsIn;
         this.vert2Weights = vert2WeightsIn;
         this.vert3Weights = vert3WeightsIn;
      }

      public static BlockModelRenderer.NeighborInfo getNeighbourInfo(Direction facing) {
         return VALUES[facing.getIndex()];
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Orientation {
      DOWN(Direction.DOWN, false),
      UP(Direction.UP, false),
      NORTH(Direction.NORTH, false),
      SOUTH(Direction.SOUTH, false),
      WEST(Direction.WEST, false),
      EAST(Direction.EAST, false),
      FLIP_DOWN(Direction.DOWN, true),
      FLIP_UP(Direction.UP, true),
      FLIP_NORTH(Direction.NORTH, true),
      FLIP_SOUTH(Direction.SOUTH, true),
      FLIP_WEST(Direction.WEST, true),
      FLIP_EAST(Direction.EAST, true);

      private final int shape;

      private Orientation(Direction facingIn, boolean flip) {
         this.shape = facingIn.getIndex() + (flip ? Direction.values().length : 0);
      }
   }

   @OnlyIn(Dist.CLIENT)
   static enum VertexTranslations {
      DOWN(0, 1, 2, 3),
      UP(2, 3, 0, 1),
      NORTH(3, 0, 1, 2),
      SOUTH(0, 1, 2, 3),
      WEST(3, 0, 1, 2),
      EAST(1, 2, 3, 0);

      private final int vert0;
      private final int vert1;
      private final int vert2;
      private final int vert3;
      private static final BlockModelRenderer.VertexTranslations[] VALUES = Util.make(new BlockModelRenderer.VertexTranslations[6], (p_209261_0_) -> {
         p_209261_0_[Direction.DOWN.getIndex()] = DOWN;
         p_209261_0_[Direction.UP.getIndex()] = UP;
         p_209261_0_[Direction.NORTH.getIndex()] = NORTH;
         p_209261_0_[Direction.SOUTH.getIndex()] = SOUTH;
         p_209261_0_[Direction.WEST.getIndex()] = WEST;
         p_209261_0_[Direction.EAST.getIndex()] = EAST;
      });

      private VertexTranslations(int vert0In, int vert1In, int vert2In, int vert3In) {
         this.vert0 = vert0In;
         this.vert1 = vert1In;
         this.vert2 = vert2In;
         this.vert3 = vert3In;
      }

      public static BlockModelRenderer.VertexTranslations getVertexTranslations(Direction facingIn) {
         return VALUES[facingIn.getIndex()];
      }
   }
}