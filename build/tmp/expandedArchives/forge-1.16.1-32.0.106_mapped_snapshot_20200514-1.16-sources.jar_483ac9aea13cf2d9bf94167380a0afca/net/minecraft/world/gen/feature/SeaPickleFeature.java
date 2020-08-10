package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.placement.CountConfig;

public class SeaPickleFeature extends Feature<CountConfig> {
   public SeaPickleFeature(Codec<CountConfig> p_i231987_1_) {
      super(p_i231987_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, CountConfig p_230362_6_) {
      int i = 0;

      for(int j = 0; j < p_230362_6_.count; ++j) {
         int k = p_230362_4_.nextInt(8) - p_230362_4_.nextInt(8);
         int l = p_230362_4_.nextInt(8) - p_230362_4_.nextInt(8);
         int i1 = p_230362_1_.getHeight(Heightmap.Type.OCEAN_FLOOR, p_230362_5_.getX() + k, p_230362_5_.getZ() + l);
         BlockPos blockpos = new BlockPos(p_230362_5_.getX() + k, i1, p_230362_5_.getZ() + l);
         BlockState blockstate = Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, Integer.valueOf(p_230362_4_.nextInt(4) + 1));
         if (p_230362_1_.getBlockState(blockpos).isIn(Blocks.WATER) && blockstate.isValidPosition(p_230362_1_, blockpos)) {
            p_230362_1_.setBlockState(blockpos, blockstate, 2);
            ++i;
         }
      }

      return i > 0;
   }
}