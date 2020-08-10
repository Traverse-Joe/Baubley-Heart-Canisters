package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class SphereReplaceFeature extends Feature<SphereReplaceConfig> {
   public SphereReplaceFeature(Codec<SphereReplaceConfig> p_i231949_1_) {
      super(p_i231949_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, SphereReplaceConfig p_230362_6_) {
      if (!p_230362_1_.getFluidState(p_230362_5_).isTagged(FluidTags.WATER)) {
         return false;
      } else {
         int i = 0;
         int j = p_230362_4_.nextInt(p_230362_6_.radius - 2) + 2;

         for(int k = p_230362_5_.getX() - j; k <= p_230362_5_.getX() + j; ++k) {
            for(int l = p_230362_5_.getZ() - j; l <= p_230362_5_.getZ() + j; ++l) {
               int i1 = k - p_230362_5_.getX();
               int j1 = l - p_230362_5_.getZ();
               if (i1 * i1 + j1 * j1 <= j * j) {
                  for(int k1 = p_230362_5_.getY() - p_230362_6_.ySize; k1 <= p_230362_5_.getY() + p_230362_6_.ySize; ++k1) {
                     BlockPos blockpos = new BlockPos(k, k1, l);
                     BlockState blockstate = p_230362_1_.getBlockState(blockpos);

                     for(BlockState blockstate1 : p_230362_6_.targets) {
                        if (blockstate1.isIn(blockstate.getBlock())) {
                           p_230362_1_.setBlockState(blockpos, p_230362_6_.state, 2);
                           ++i;
                           break;
                        }
                     }
                  }
               }
            }
         }

         return i > 0;
      }
   }
}