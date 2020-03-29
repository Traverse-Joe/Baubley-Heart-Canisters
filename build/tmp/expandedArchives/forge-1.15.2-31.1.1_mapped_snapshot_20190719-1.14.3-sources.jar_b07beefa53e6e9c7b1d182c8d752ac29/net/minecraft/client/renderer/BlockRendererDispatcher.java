package net.minecraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Random;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockRendererDispatcher implements IResourceManagerReloadListener {
   private final BlockModelShapes blockModelShapes;
   private final BlockModelRenderer blockModelRenderer;
   private final FluidBlockRenderer fluidRenderer;
   private final Random random = new Random();
   private final BlockColors field_228790_e_;

   public BlockRendererDispatcher(BlockModelShapes p_i46577_1_, BlockColors p_i46577_2_) {
      this.blockModelShapes = p_i46577_1_;
      this.field_228790_e_ = p_i46577_2_;
      this.blockModelRenderer = new BlockModelRenderer(this.field_228790_e_);
      this.fluidRenderer = new FluidBlockRenderer();
   }

   public BlockModelShapes getBlockModelShapes() {
      return this.blockModelShapes;
   }

   @Deprecated //Forge: Model parameter
   public void func_228792_a_(BlockState p_228792_1_, BlockPos p_228792_2_, ILightReader p_228792_3_, MatrixStack p_228792_4_, IVertexBuilder p_228792_5_) {
      renderModel(p_228792_1_, p_228792_2_, p_228792_3_, p_228792_4_, p_228792_5_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public void renderModel(BlockState p_228792_1_, BlockPos p_228792_2_, ILightReader p_228792_3_, MatrixStack p_228792_4_, IVertexBuilder p_228792_5_, net.minecraftforge.client.model.data.IModelData modelData) {
      if (p_228792_1_.getRenderType() == BlockRenderType.MODEL) {
         IBakedModel ibakedmodel = this.blockModelShapes.getModel(p_228792_1_);
         long i = p_228792_1_.getPositionRandom(p_228792_2_);
         this.blockModelRenderer.renderModel(p_228792_3_, ibakedmodel, p_228792_1_, p_228792_2_, p_228792_4_, p_228792_5_, true, this.random, i, OverlayTexture.field_229196_a_, modelData);
      }
   }

   @Deprecated //Forge: Model parameter
   public boolean func_228793_a_(BlockState p_228793_1_, BlockPos p_228793_2_, ILightReader p_228793_3_, MatrixStack p_228793_4_, IVertexBuilder p_228793_5_, boolean p_228793_6_, Random p_228793_7_) {
      return renderModel(p_228793_1_, p_228793_2_, p_228793_3_, p_228793_4_, p_228793_5_, p_228793_6_, p_228793_7_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public boolean renderModel(BlockState p_228793_1_, BlockPos p_228793_2_, ILightReader p_228793_3_, MatrixStack p_228793_4_, IVertexBuilder p_228793_5_, boolean p_228793_6_, Random p_228793_7_, net.minecraftforge.client.model.data.IModelData modelData) {
      try {
         BlockRenderType blockrendertype = p_228793_1_.getRenderType();
         return blockrendertype != BlockRenderType.MODEL ? false : this.blockModelRenderer.renderModel(p_228793_3_, this.getModelForState(p_228793_1_), p_228793_1_, p_228793_2_, p_228793_4_, p_228793_5_, p_228793_6_, p_228793_7_, p_228793_1_.getPositionRandom(p_228793_2_), OverlayTexture.field_229196_a_, modelData);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block in world");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being tesselated");
         CrashReportCategory.addBlockInfo(crashreportcategory, p_228793_2_, p_228793_1_);
         throw new ReportedException(crashreport);
      }
   }

   public boolean func_228794_a_(BlockPos p_228794_1_, ILightReader p_228794_2_, IVertexBuilder p_228794_3_, IFluidState p_228794_4_) {
      try {
         return this.fluidRenderer.func_228796_a_(p_228794_2_, p_228794_1_, p_228794_3_, p_228794_4_);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating liquid in world");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being tesselated");
         CrashReportCategory.addBlockInfo(crashreportcategory, p_228794_1_, (BlockState)null);
         throw new ReportedException(crashreport);
      }
   }

   public BlockModelRenderer getBlockModelRenderer() {
      return this.blockModelRenderer;
   }

   public IBakedModel getModelForState(BlockState state) {
      return this.blockModelShapes.getModel(state);
   }

   @Deprecated //Forge: Model parameter
   public void func_228791_a_(BlockState p_228791_1_, MatrixStack p_228791_2_, IRenderTypeBuffer p_228791_3_, int p_228791_4_, int p_228791_5_) {
      renderBlock(p_228791_1_, p_228791_2_, p_228791_3_, p_228791_4_, p_228791_5_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public void renderBlock(BlockState p_228791_1_, MatrixStack p_228791_2_, IRenderTypeBuffer p_228791_3_, int p_228791_4_, int p_228791_5_, net.minecraftforge.client.model.data.IModelData modelData) {
      BlockRenderType blockrendertype = p_228791_1_.getRenderType();
      if (blockrendertype != BlockRenderType.INVISIBLE) {
         switch(blockrendertype) {
         case MODEL:
            IBakedModel ibakedmodel = this.getModelForState(p_228791_1_);
            int i = this.field_228790_e_.func_228054_a_(p_228791_1_, (ILightReader)null, (BlockPos)null, 0);
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            this.blockModelRenderer.renderModel(p_228791_2_.func_227866_c_(), p_228791_3_.getBuffer(RenderTypeLookup.func_228394_b_(p_228791_1_)), p_228791_1_, ibakedmodel, f, f1, f2, p_228791_4_, p_228791_5_, modelData);
            break;
         case ENTITYBLOCK_ANIMATED:
            ItemStackTileEntityRenderer.instance.func_228364_a_(new ItemStack(p_228791_1_.getBlock()), p_228791_2_, p_228791_3_, p_228791_4_, p_228791_5_);
         }

      }
   }

   public void onResourceManagerReload(IResourceManager resourceManager) {
      this.fluidRenderer.initAtlasSprites();
   }

   @Override
   public net.minecraftforge.resource.IResourceType getResourceType() {
      return net.minecraftforge.resource.VanillaResourceType.MODELS;
   }
}