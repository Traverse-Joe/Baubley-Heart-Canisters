package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class SingleRandomFeatureConfig extends Feature<SingleRandomFeature> {
   public SingleRandomFeatureConfig(Codec<SingleRandomFeature> p_i231992_1_) {
      super(p_i231992_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, SingleRandomFeature p_230362_6_) {
      int i = p_230362_4_.nextInt(p_230362_6_.features.size());
      ConfiguredFeature<?, ?> configuredfeature = p_230362_6_.features.get(i);
      return configuredfeature.func_236265_a_(p_230362_1_, p_230362_2_, p_230362_3_, p_230362_4_, p_230362_5_);
   }
}