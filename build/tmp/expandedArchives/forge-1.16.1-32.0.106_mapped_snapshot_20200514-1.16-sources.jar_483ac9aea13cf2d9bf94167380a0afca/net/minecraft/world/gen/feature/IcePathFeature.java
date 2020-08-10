package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class IcePathFeature extends Feature<FeatureRadiusConfig> {
   private final Block block = Blocks.PACKED_ICE;

   public IcePathFeature(Codec<FeatureRadiusConfig> p_i231961_1_) {
      super(p_i231961_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, FeatureRadiusConfig p_230362_6_) {
      while(p_230362_1_.isAirBlock(p_230362_5_) && p_230362_5_.getY() > 2) {
         p_230362_5_ = p_230362_5_.down();
      }

      if (!p_230362_1_.getBlockState(p_230362_5_).isIn(Blocks.SNOW_BLOCK)) {
         return false;
      } else {
         int i = p_230362_4_.nextInt(p_230362_6_.radius) + 2;
         int j = 1;

         for(int k = p_230362_5_.getX() - i; k <= p_230362_5_.getX() + i; ++k) {
            for(int l = p_230362_5_.getZ() - i; l <= p_230362_5_.getZ() + i; ++l) {
               int i1 = k - p_230362_5_.getX();
               int j1 = l - p_230362_5_.getZ();
               if (i1 * i1 + j1 * j1 <= i * i) {
                  for(int k1 = p_230362_5_.getY() - 1; k1 <= p_230362_5_.getY() + 1; ++k1) {
                     BlockPos blockpos = new BlockPos(k, k1, l);
                     Block block = p_230362_1_.getBlockState(blockpos).getBlock();
                     if (isDirt(block) || block == Blocks.SNOW_BLOCK || block == Blocks.ICE) {
                        p_230362_1_.setBlockState(blockpos, this.block.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}