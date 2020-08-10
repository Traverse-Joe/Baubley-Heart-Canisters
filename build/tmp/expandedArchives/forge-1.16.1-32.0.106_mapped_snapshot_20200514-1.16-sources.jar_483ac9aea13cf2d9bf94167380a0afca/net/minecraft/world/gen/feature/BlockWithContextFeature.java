package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class BlockWithContextFeature extends Feature<BlockWithContextConfig> {
   public BlockWithContextFeature(Codec<BlockWithContextConfig> p_i231991_1_) {
      super(p_i231991_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, BlockWithContextConfig p_230362_6_) {
      if (p_230362_6_.placeOn.contains(p_230362_1_.getBlockState(p_230362_5_.down())) && p_230362_6_.placeIn.contains(p_230362_1_.getBlockState(p_230362_5_)) && p_230362_6_.placeUnder.contains(p_230362_1_.getBlockState(p_230362_5_.up()))) {
         p_230362_1_.setBlockState(p_230362_5_, p_230362_6_.toPlace, 2);
         return true;
      } else {
         return false;
      }
   }
}