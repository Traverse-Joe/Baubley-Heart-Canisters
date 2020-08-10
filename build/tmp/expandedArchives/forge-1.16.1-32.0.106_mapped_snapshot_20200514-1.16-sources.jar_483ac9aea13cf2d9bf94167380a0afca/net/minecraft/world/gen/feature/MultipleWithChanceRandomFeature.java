package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class MultipleWithChanceRandomFeature extends Feature<MultipleRandomFeatureConfig> {
   public MultipleWithChanceRandomFeature(Codec<MultipleRandomFeatureConfig> p_i231981_1_) {
      super(p_i231981_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, MultipleRandomFeatureConfig p_230362_6_) {
      for(ConfiguredRandomFeatureList<?> configuredrandomfeaturelist : p_230362_6_.features) {
         if (p_230362_4_.nextFloat() < configuredrandomfeaturelist.chance) {
            return configuredrandomfeaturelist.func_236431_a_(p_230362_1_, p_230362_2_, p_230362_3_, p_230362_4_, p_230362_5_);
         }
      }

      return p_230362_6_.defaultFeature.func_236265_a_(p_230362_1_, p_230362_2_, p_230362_3_, p_230362_4_, p_230362_5_);
   }
}