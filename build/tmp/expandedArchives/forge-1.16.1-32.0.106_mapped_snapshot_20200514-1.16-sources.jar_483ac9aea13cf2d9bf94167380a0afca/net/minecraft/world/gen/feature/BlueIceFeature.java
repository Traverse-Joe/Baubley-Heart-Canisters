package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class BlueIceFeature extends Feature<NoFeatureConfig> {
   public BlueIceFeature(Codec<NoFeatureConfig> p_i231933_1_) {
      super(p_i231933_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      if (p_230362_5_.getY() > p_230362_1_.getSeaLevel() - 1) {
         return false;
      } else if (!p_230362_1_.getBlockState(p_230362_5_).isIn(Blocks.WATER) && !p_230362_1_.getBlockState(p_230362_5_.down()).isIn(Blocks.WATER)) {
         return false;
      } else {
         boolean flag = false;

         for(Direction direction : Direction.values()) {
            if (direction != Direction.DOWN && p_230362_1_.getBlockState(p_230362_5_.offset(direction)).isIn(Blocks.PACKED_ICE)) {
               flag = true;
               break;
            }
         }

         if (!flag) {
            return false;
         } else {
            p_230362_1_.setBlockState(p_230362_5_, Blocks.BLUE_ICE.getDefaultState(), 2);

            for(int i = 0; i < 200; ++i) {
               int j = p_230362_4_.nextInt(5) - p_230362_4_.nextInt(6);
               int k = 3;
               if (j < 2) {
                  k += j / 2;
               }

               if (k >= 1) {
                  BlockPos blockpos = p_230362_5_.add(p_230362_4_.nextInt(k) - p_230362_4_.nextInt(k), j, p_230362_4_.nextInt(k) - p_230362_4_.nextInt(k));
                  BlockState blockstate = p_230362_1_.getBlockState(blockpos);
                  if (blockstate.getMaterial() == Material.AIR || blockstate.isIn(Blocks.WATER) || blockstate.isIn(Blocks.PACKED_ICE) || blockstate.isIn(Blocks.ICE)) {
                     for(Direction direction1 : Direction.values()) {
                        BlockState blockstate1 = p_230362_1_.getBlockState(blockpos.offset(direction1));
                        if (blockstate1.isIn(Blocks.BLUE_ICE)) {
                           p_230362_1_.setBlockState(blockpos, Blocks.BLUE_ICE.getDefaultState(), 2);
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}