package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.tileentity.LecternTileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LecternTileEntityRenderer extends TileEntityRenderer<LecternTileEntity> {
   private final BookModel field_217656_d = new BookModel();

   public LecternTileEntityRenderer(TileEntityRendererDispatcher p_i226011_1_) {
      super(p_i226011_1_);
   }

   public void func_225616_a_(LecternTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      BlockState blockstate = p_225616_1_.getBlockState();
      if (blockstate.get(LecternBlock.HAS_BOOK)) {
         p_225616_3_.func_227860_a_();
         p_225616_3_.func_227861_a_(0.5D, 1.0625D, 0.5D);
         float f = blockstate.get(LecternBlock.FACING).rotateY().getHorizontalAngle();
         p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-f));
         p_225616_3_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(67.5F));
         p_225616_3_.func_227861_a_(0.0D, -0.125D, 0.0D);
         this.field_217656_d.func_228247_a_(0.0F, 0.1F, 0.9F, 1.2F);
         IVertexBuilder ivertexbuilder = EnchantmentTableTileEntityRenderer.TEXTURE_BOOK.func_229311_a_(p_225616_4_, RenderType::func_228634_a_);
         this.field_217656_d.func_228249_b_(p_225616_3_, ivertexbuilder, p_225616_5_, p_225616_6_, 1.0F, 1.0F, 1.0F, 1.0F);
         p_225616_3_.func_227865_b_();
      }
   }
}