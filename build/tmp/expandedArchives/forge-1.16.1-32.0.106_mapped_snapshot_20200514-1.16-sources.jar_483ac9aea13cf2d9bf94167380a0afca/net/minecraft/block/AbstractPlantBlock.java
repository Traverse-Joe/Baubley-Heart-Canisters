package net.minecraft.block;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public abstract class AbstractPlantBlock extends Block {
   protected final Direction field_235498_a_;
   protected final boolean field_235499_b_;
   protected final VoxelShape field_235500_c_;

   protected AbstractPlantBlock(AbstractBlock.Properties p_i241178_1_, Direction p_i241178_2_, VoxelShape p_i241178_3_, boolean p_i241178_4_) {
      super(p_i241178_1_);
      this.field_235498_a_ = p_i241178_2_;
      this.field_235500_c_ = p_i241178_3_;
      this.field_235499_b_ = p_i241178_4_;
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.offset(this.field_235498_a_.getOpposite());
      BlockState blockstate = worldIn.getBlockState(blockpos);
      Block block = blockstate.getBlock();
      if (!this.func_230333_c_(block)) {
         return false;
      } else {
         return block == this.func_230331_c_() || block == this.func_230330_d_() || blockstate.isSolidSide(worldIn, blockpos, this.field_235498_a_);
      }
   }

   protected boolean func_230333_c_(Block p_230333_1_) {
      return true;
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return this.field_235500_c_;
   }

   protected abstract AbstractTopPlantBlock func_230331_c_();

   protected abstract Block func_230330_d_();
}