package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class EndIslandFeature extends Feature<NoFeatureConfig> {
   public EndIslandFeature(Codec<NoFeatureConfig> p_i231952_1_) {
      super(p_i231952_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      float f = (float)(p_230362_4_.nextInt(3) + 4);

      for(int i = 0; f > 0.5F; --i) {
         for(int j = MathHelper.floor(-f); j <= MathHelper.ceil(f); ++j) {
            for(int k = MathHelper.floor(-f); k <= MathHelper.ceil(f); ++k) {
               if ((float)(j * j + k * k) <= (f + 1.0F) * (f + 1.0F)) {
                  this.func_230367_a_(p_230362_1_, p_230362_5_.add(j, i, k), Blocks.END_STONE.getDefaultState());
               }
            }
         }

         f = (float)((double)f - ((double)p_230362_4_.nextInt(2) + 0.5D));
      }

      return true;
   }
}