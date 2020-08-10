package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class BlockBlobFeature extends Feature<BlockBlobConfig> {
   public BlockBlobFeature(Codec<BlockBlobConfig> p_i231931_1_) {
      super(p_i231931_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, BlockBlobConfig p_230362_6_) {
      while(true) {
         label48: {
            if (p_230362_5_.getY() > 3) {
               if (p_230362_1_.isAirBlock(p_230362_5_.down())) {
                  break label48;
               }

               Block block = p_230362_1_.getBlockState(p_230362_5_.down()).getBlock();
               if (!isDirt(block) && !isStone(block)) {
                  break label48;
               }
            }

            if (p_230362_5_.getY() <= 3) {
               return false;
            }

            int i1 = p_230362_6_.startRadius;

            for(int i = 0; i1 >= 0 && i < 3; ++i) {
               int j = i1 + p_230362_4_.nextInt(2);
               int k = i1 + p_230362_4_.nextInt(2);
               int l = i1 + p_230362_4_.nextInt(2);
               float f = (float)(j + k + l) * 0.333F + 0.5F;

               for(BlockPos blockpos : BlockPos.getAllInBoxMutable(p_230362_5_.add(-j, -k, -l), p_230362_5_.add(j, k, l))) {
                  if (blockpos.distanceSq(p_230362_5_) <= (double)(f * f)) {
                     p_230362_1_.setBlockState(blockpos, p_230362_6_.state, 4);
                  }
               }

               p_230362_5_ = p_230362_5_.add(-(i1 + 1) + p_230362_4_.nextInt(2 + i1 * 2), 0 - p_230362_4_.nextInt(2), -(i1 + 1) + p_230362_4_.nextInt(2 + i1 * 2));
            }

            return true;
         }

         p_230362_5_ = p_230362_5_.down();
      }
   }
}