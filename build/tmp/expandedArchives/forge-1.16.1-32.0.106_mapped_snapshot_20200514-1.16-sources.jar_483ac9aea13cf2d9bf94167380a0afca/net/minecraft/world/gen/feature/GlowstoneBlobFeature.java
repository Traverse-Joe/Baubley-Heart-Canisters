package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class GlowstoneBlobFeature extends Feature<NoFeatureConfig> {
   public GlowstoneBlobFeature(Codec<NoFeatureConfig> p_i231956_1_) {
      super(p_i231956_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      if (!p_230362_1_.isAirBlock(p_230362_5_)) {
         return false;
      } else {
         BlockState blockstate = p_230362_1_.getBlockState(p_230362_5_.up());
         if (!blockstate.isIn(Blocks.NETHERRACK) && !blockstate.isIn(Blocks.field_235337_cO_) && !blockstate.isIn(Blocks.field_235406_np_)) {
            return false;
         } else {
            p_230362_1_.setBlockState(p_230362_5_, Blocks.GLOWSTONE.getDefaultState(), 2);

            for(int i = 0; i < 1500; ++i) {
               BlockPos blockpos = p_230362_5_.add(p_230362_4_.nextInt(8) - p_230362_4_.nextInt(8), -p_230362_4_.nextInt(12), p_230362_4_.nextInt(8) - p_230362_4_.nextInt(8));
               if (p_230362_1_.getBlockState(blockpos).isAir(p_230362_1_, blockpos)) {
                  int j = 0;

                  for(Direction direction : Direction.values()) {
                     if (p_230362_1_.getBlockState(blockpos.offset(direction)).isIn(Blocks.GLOWSTONE)) {
                        ++j;
                     }

                     if (j > 1) {
                        break;
                     }
                  }

                  if (j == 1) {
                     p_230362_1_.setBlockState(blockpos, Blocks.GLOWSTONE.getDefaultState(), 2);
                  }
               }
            }

            return true;
         }
      }
   }
}