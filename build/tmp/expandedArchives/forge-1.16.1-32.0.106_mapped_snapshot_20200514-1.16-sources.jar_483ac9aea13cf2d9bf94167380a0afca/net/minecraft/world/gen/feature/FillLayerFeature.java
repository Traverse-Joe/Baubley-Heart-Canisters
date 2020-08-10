package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class FillLayerFeature extends Feature<FillLayerConfig> {
   public FillLayerFeature(Codec<FillLayerConfig> p_i231954_1_) {
      super(p_i231954_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, FillLayerConfig p_230362_6_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = p_230362_5_.getX() + i;
            int l = p_230362_5_.getZ() + j;
            int i1 = p_230362_6_.height;
            blockpos$mutable.setPos(k, i1, l);
            if (p_230362_1_.getBlockState(blockpos$mutable).isAir()) {
               p_230362_1_.setBlockState(blockpos$mutable, p_230362_6_.state, 2);
            }
         }
      }

      return true;
   }
}