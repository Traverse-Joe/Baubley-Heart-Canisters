package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class DesertWellsFeature extends Feature<NoFeatureConfig> {
   private static final BlockStateMatcher IS_SAND = BlockStateMatcher.forBlock(Blocks.SAND);
   private final BlockState sandSlab = Blocks.SANDSTONE_SLAB.getDefaultState();
   private final BlockState sandstone = Blocks.SANDSTONE.getDefaultState();
   private final BlockState water = Blocks.WATER.getDefaultState();

   public DesertWellsFeature(Codec<NoFeatureConfig> p_i231948_1_) {
      super(p_i231948_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      for(p_230362_5_ = p_230362_5_.up(); p_230362_1_.isAirBlock(p_230362_5_) && p_230362_5_.getY() > 2; p_230362_5_ = p_230362_5_.down()) {
      }

      if (!IS_SAND.test(p_230362_1_.getBlockState(p_230362_5_))) {
         return false;
      } else {
         for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
               if (p_230362_1_.isAirBlock(p_230362_5_.add(i, -1, j)) && p_230362_1_.isAirBlock(p_230362_5_.add(i, -2, j))) {
                  return false;
               }
            }
         }

         for(int l = -1; l <= 0; ++l) {
            for(int l1 = -2; l1 <= 2; ++l1) {
               for(int k = -2; k <= 2; ++k) {
                  p_230362_1_.setBlockState(p_230362_5_.add(l1, l, k), this.sandstone, 2);
               }
            }
         }

         p_230362_1_.setBlockState(p_230362_5_, this.water, 2);

         for(Direction direction : Direction.Plane.HORIZONTAL) {
            p_230362_1_.setBlockState(p_230362_5_.offset(direction), this.water, 2);
         }

         for(int i1 = -2; i1 <= 2; ++i1) {
            for(int i2 = -2; i2 <= 2; ++i2) {
               if (i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2) {
                  p_230362_1_.setBlockState(p_230362_5_.add(i1, 1, i2), this.sandstone, 2);
               }
            }
         }

         p_230362_1_.setBlockState(p_230362_5_.add(2, 1, 0), this.sandSlab, 2);
         p_230362_1_.setBlockState(p_230362_5_.add(-2, 1, 0), this.sandSlab, 2);
         p_230362_1_.setBlockState(p_230362_5_.add(0, 1, 2), this.sandSlab, 2);
         p_230362_1_.setBlockState(p_230362_5_.add(0, 1, -2), this.sandSlab, 2);

         for(int j1 = -1; j1 <= 1; ++j1) {
            for(int j2 = -1; j2 <= 1; ++j2) {
               if (j1 == 0 && j2 == 0) {
                  p_230362_1_.setBlockState(p_230362_5_.add(j1, 4, j2), this.sandstone, 2);
               } else {
                  p_230362_1_.setBlockState(p_230362_5_.add(j1, 4, j2), this.sandSlab, 2);
               }
            }
         }

         for(int k1 = 1; k1 <= 3; ++k1) {
            p_230362_1_.setBlockState(p_230362_5_.add(-1, k1, -1), this.sandstone, 2);
            p_230362_1_.setBlockState(p_230362_5_.add(-1, k1, 1), this.sandstone, 2);
            p_230362_1_.setBlockState(p_230362_5_.add(1, k1, -1), this.sandstone, 2);
            p_230362_1_.setBlockState(p_230362_5_.add(1, k1, 1), this.sandstone, 2);
         }

         return true;
      }
   }
}