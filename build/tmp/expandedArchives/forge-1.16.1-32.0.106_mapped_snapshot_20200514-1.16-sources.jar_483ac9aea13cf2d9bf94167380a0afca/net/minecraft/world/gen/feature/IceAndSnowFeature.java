package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class IceAndSnowFeature extends Feature<NoFeatureConfig> {
   public IceAndSnowFeature(Codec<NoFeatureConfig> p_i231993_1_) {
      super(p_i231993_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
      BlockPos.Mutable blockpos$mutable1 = new BlockPos.Mutable();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = p_230362_5_.getX() + i;
            int l = p_230362_5_.getZ() + j;
            int i1 = p_230362_1_.getHeight(Heightmap.Type.MOTION_BLOCKING, k, l);
            blockpos$mutable.setPos(k, i1, l);
            blockpos$mutable1.setPos(blockpos$mutable).move(Direction.DOWN, 1);
            Biome biome = p_230362_1_.getBiome(blockpos$mutable);
            if (biome.doesWaterFreeze(p_230362_1_, blockpos$mutable1, false)) {
               p_230362_1_.setBlockState(blockpos$mutable1, Blocks.ICE.getDefaultState(), 2);
            }

            if (biome.doesSnowGenerate(p_230362_1_, blockpos$mutable)) {
               p_230362_1_.setBlockState(blockpos$mutable, Blocks.SNOW.getDefaultState(), 2);
               BlockState blockstate = p_230362_1_.getBlockState(blockpos$mutable1);
               if (blockstate.func_235901_b_(SnowyDirtBlock.SNOWY)) {
                  p_230362_1_.setBlockState(blockpos$mutable1, blockstate.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
               }
            }
         }
      }

      return true;
   }
}