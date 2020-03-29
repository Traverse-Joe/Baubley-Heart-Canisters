package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BedTileEntityRenderer extends TileEntityRenderer<BedTileEntity> {
   private final ModelRenderer field_228843_a_;
   private final ModelRenderer field_228844_c_;
   private final ModelRenderer[] field_228845_d_ = new ModelRenderer[4];

   public BedTileEntityRenderer(TileEntityRendererDispatcher p_i226004_1_) {
      super(p_i226004_1_);
      this.field_228843_a_ = new ModelRenderer(64, 64, 0, 0);
      this.field_228843_a_.func_228301_a_(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
      this.field_228844_c_ = new ModelRenderer(64, 64, 0, 22);
      this.field_228844_c_.func_228301_a_(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
      this.field_228845_d_[0] = new ModelRenderer(64, 64, 50, 0);
      this.field_228845_d_[1] = new ModelRenderer(64, 64, 50, 6);
      this.field_228845_d_[2] = new ModelRenderer(64, 64, 50, 12);
      this.field_228845_d_[3] = new ModelRenderer(64, 64, 50, 18);
      this.field_228845_d_[0].func_228300_a_(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
      this.field_228845_d_[1].func_228300_a_(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
      this.field_228845_d_[2].func_228300_a_(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
      this.field_228845_d_[3].func_228300_a_(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
      this.field_228845_d_[0].rotateAngleX = ((float)Math.PI / 2F);
      this.field_228845_d_[1].rotateAngleX = ((float)Math.PI / 2F);
      this.field_228845_d_[2].rotateAngleX = ((float)Math.PI / 2F);
      this.field_228845_d_[3].rotateAngleX = ((float)Math.PI / 2F);
      this.field_228845_d_[0].rotateAngleZ = 0.0F;
      this.field_228845_d_[1].rotateAngleZ = ((float)Math.PI / 2F);
      this.field_228845_d_[2].rotateAngleZ = ((float)Math.PI * 1.5F);
      this.field_228845_d_[3].rotateAngleZ = (float)Math.PI;
   }

   public void func_225616_a_(BedTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      Material material = Atlases.field_228751_j_[p_225616_1_.getColor().getId()];
      World world = p_225616_1_.getWorld();
      if (world != null) {
         BlockState blockstate = p_225616_1_.getBlockState();
         TileEntityMerger.ICallbackWrapper<? extends BedTileEntity> icallbackwrapper = TileEntityMerger.func_226924_a_(TileEntityType.BED, BedBlock::func_226863_i_, BedBlock::func_226862_h_, ChestBlock.FACING, blockstate, world, p_225616_1_.getPos(), (p_228846_0_, p_228846_1_) -> {
            return false;
         });
         int i = icallbackwrapper.apply(new DualBrightnessCallback<>()).get(p_225616_5_);
         this.func_228847_a_(p_225616_3_, p_225616_4_, blockstate.get(BedBlock.PART) == BedPart.HEAD, blockstate.get(BedBlock.HORIZONTAL_FACING), material, i, p_225616_6_, false);
      } else {
         this.func_228847_a_(p_225616_3_, p_225616_4_, true, Direction.SOUTH, material, p_225616_5_, p_225616_6_, false);
         this.func_228847_a_(p_225616_3_, p_225616_4_, false, Direction.SOUTH, material, p_225616_5_, p_225616_6_, true);
      }

   }

   private void func_228847_a_(MatrixStack p_228847_1_, IRenderTypeBuffer p_228847_2_, boolean p_228847_3_, Direction p_228847_4_, Material p_228847_5_, int p_228847_6_, int p_228847_7_, boolean p_228847_8_) {
      this.field_228843_a_.showModel = p_228847_3_;
      this.field_228844_c_.showModel = !p_228847_3_;
      this.field_228845_d_[0].showModel = !p_228847_3_;
      this.field_228845_d_[1].showModel = p_228847_3_;
      this.field_228845_d_[2].showModel = !p_228847_3_;
      this.field_228845_d_[3].showModel = p_228847_3_;
      p_228847_1_.func_227860_a_();
      p_228847_1_.func_227861_a_(0.0D, 0.5625D, p_228847_8_ ? -1.0D : 0.0D);
      p_228847_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
      p_228847_1_.func_227861_a_(0.5D, 0.5D, 0.5D);
      p_228847_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F + p_228847_4_.getHorizontalAngle()));
      p_228847_1_.func_227861_a_(-0.5D, -0.5D, -0.5D);
      IVertexBuilder ivertexbuilder = p_228847_5_.func_229311_a_(p_228847_2_, RenderType::func_228634_a_);
      this.field_228843_a_.func_228308_a_(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
      this.field_228844_c_.func_228308_a_(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
      this.field_228845_d_[0].func_228308_a_(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
      this.field_228845_d_[1].func_228308_a_(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
      this.field_228845_d_[2].func_228308_a_(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
      this.field_228845_d_[3].func_228308_a_(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
      p_228847_1_.func_227865_b_();
   }
}