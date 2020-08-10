package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class TwoFeatureChoiceFeature extends Feature<TwoFeatureChoiceConfig> {
   public TwoFeatureChoiceFeature(Codec<TwoFeatureChoiceConfig> p_i231978_1_) {
      super(p_i231978_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, TwoFeatureChoiceConfig p_230362_6_) {
      boolean flag = p_230362_4_.nextBoolean();
      return flag ? p_230362_6_.field_227285_a_.func_236265_a_(p_230362_1_, p_230362_2_, p_230362_3_, p_230362_4_, p_230362_5_) : p_230362_6_.field_227286_b_.func_236265_a_(p_230362_1_, p_230362_2_, p_230362_3_, p_230362_4_, p_230362_5_);
   }
}