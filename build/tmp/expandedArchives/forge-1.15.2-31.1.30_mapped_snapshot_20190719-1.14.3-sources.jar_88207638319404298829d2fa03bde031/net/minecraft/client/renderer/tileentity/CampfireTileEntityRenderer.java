package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CampfireTileEntityRenderer extends TileEntityRenderer<CampfireTileEntity> {
   public CampfireTileEntityRenderer(TileEntityRendererDispatcher p_i226007_1_) {
      super(p_i226007_1_);
   }

   public void func_225616_a_(CampfireTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      Direction direction = p_225616_1_.getBlockState().get(CampfireBlock.FACING);
      NonNullList<ItemStack> nonnulllist = p_225616_1_.getInventory();

      for(int i = 0; i < nonnulllist.size(); ++i) {
         ItemStack itemstack = nonnulllist.get(i);
         if (itemstack != ItemStack.EMPTY) {
            p_225616_3_.func_227860_a_();
            p_225616_3_.func_227861_a_(0.5D, 0.44921875D, 0.5D);
            Direction direction1 = Direction.byHorizontalIndex((i + direction.getHorizontalIndex()) % 4);
            float f = -direction1.getHorizontalAngle();
            p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f));
            p_225616_3_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
            p_225616_3_.func_227861_a_(-0.3125D, -0.3125D, 0.0D);
            p_225616_3_.func_227862_a_(0.375F, 0.375F, 0.375F);
            Minecraft.getInstance().getItemRenderer().func_229110_a_(itemstack, ItemCameraTransforms.TransformType.FIXED, p_225616_5_, p_225616_6_, p_225616_3_, p_225616_4_);
            p_225616_3_.func_227865_b_();
         }
      }

   }
}