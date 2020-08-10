package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class ChorusPlantFeature extends Feature<NoFeatureConfig> {
   public ChorusPlantFeature(Codec<NoFeatureConfig> p_i231936_1_) {
      super(p_i231936_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      if (p_230362_1_.isAirBlock(p_230362_5_.up()) && p_230362_1_.getBlockState(p_230362_5_).isIn(Blocks.END_STONE)) {
         ChorusFlowerBlock.generatePlant(p_230362_1_, p_230362_5_.up(), p_230362_4_, 8);
         return true;
      } else {
         return false;
      }
   }
}