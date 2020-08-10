package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class SpringFeature extends Feature<LiquidsConfig> {
   public SpringFeature(Codec<LiquidsConfig> p_i231995_1_) {
      super(p_i231995_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, LiquidsConfig p_230362_6_) {
      if (!p_230362_6_.acceptedBlocks.contains(p_230362_1_.getBlockState(p_230362_5_.up()).getBlock())) {
         return false;
      } else if (p_230362_6_.needsBlockBelow && !p_230362_6_.acceptedBlocks.contains(p_230362_1_.getBlockState(p_230362_5_.down()).getBlock())) {
         return false;
      } else {
         BlockState blockstate = p_230362_1_.getBlockState(p_230362_5_);
         if (!blockstate.isAir(p_230362_1_, p_230362_5_) && !p_230362_6_.acceptedBlocks.contains(blockstate.getBlock())) {
            return false;
         } else {
            int i = 0;
            int j = 0;
            if (p_230362_6_.acceptedBlocks.contains(p_230362_1_.getBlockState(p_230362_5_.west()).getBlock())) {
               ++j;
            }

            if (p_230362_6_.acceptedBlocks.contains(p_230362_1_.getBlockState(p_230362_5_.east()).getBlock())) {
               ++j;
            }

            if (p_230362_6_.acceptedBlocks.contains(p_230362_1_.getBlockState(p_230362_5_.north()).getBlock())) {
               ++j;
            }

            if (p_230362_6_.acceptedBlocks.contains(p_230362_1_.getBlockState(p_230362_5_.south()).getBlock())) {
               ++j;
            }

            if (p_230362_6_.acceptedBlocks.contains(p_230362_1_.getBlockState(p_230362_5_.down()).getBlock())) {
               ++j;
            }

            int k = 0;
            if (p_230362_1_.isAirBlock(p_230362_5_.west())) {
               ++k;
            }

            if (p_230362_1_.isAirBlock(p_230362_5_.east())) {
               ++k;
            }

            if (p_230362_1_.isAirBlock(p_230362_5_.north())) {
               ++k;
            }

            if (p_230362_1_.isAirBlock(p_230362_5_.south())) {
               ++k;
            }

            if (p_230362_1_.isAirBlock(p_230362_5_.down())) {
               ++k;
            }

            if (j == p_230362_6_.rockAmount && k == p_230362_6_.holeAmount) {
               p_230362_1_.setBlockState(p_230362_5_, p_230362_6_.state.getBlockState(), 2);
               p_230362_1_.getPendingFluidTicks().scheduleTick(p_230362_5_, p_230362_6_.state.getFluid(), 0);
               ++i;
            }

            return i > 0;
         }
      }
   }
}