package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class SeaGrassFeature extends Feature<SeaGrassConfig> {
   public SeaGrassFeature(Codec<SeaGrassConfig> p_i231988_1_) {
      super(p_i231988_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, SeaGrassConfig p_230362_6_) {
      int i = 0;

      for(int j = 0; j < p_230362_6_.count; ++j) {
         int k = p_230362_4_.nextInt(8) - p_230362_4_.nextInt(8);
         int l = p_230362_4_.nextInt(8) - p_230362_4_.nextInt(8);
         int i1 = p_230362_1_.getHeight(Heightmap.Type.OCEAN_FLOOR, p_230362_5_.getX() + k, p_230362_5_.getZ() + l);
         BlockPos blockpos = new BlockPos(p_230362_5_.getX() + k, i1, p_230362_5_.getZ() + l);
         if (p_230362_1_.getBlockState(blockpos).isIn(Blocks.WATER)) {
            boolean flag = p_230362_4_.nextDouble() < p_230362_6_.tallProbability;
            BlockState blockstate = flag ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();
            if (blockstate.isValidPosition(p_230362_1_, blockpos)) {
               if (flag) {
                  BlockState blockstate1 = blockstate.with(TallSeaGrassBlock.field_208065_c, DoubleBlockHalf.UPPER);
                  BlockPos blockpos1 = blockpos.up();
                  if (p_230362_1_.getBlockState(blockpos1).isIn(Blocks.WATER)) {
                     p_230362_1_.setBlockState(blockpos, blockstate, 2);
                     p_230362_1_.setBlockState(blockpos1, blockstate1, 2);
                  }
               } else {
                  p_230362_1_.setBlockState(blockpos, blockstate, 2);
               }

               ++i;
            }
         }
      }

      return i > 0;
   }
}