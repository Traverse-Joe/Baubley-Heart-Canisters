package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class OakTree extends Tree {
   @Nullable
   protected ConfiguredFeature<TreeFeatureConfig, ?> func_225546_b_(Random p_225546_1_, boolean p_225546_2_) {
      return p_225546_1_.nextInt(10) == 0 ? Feature.FANCY_TREE.func_225566_b_(p_225546_2_ ? DefaultBiomeFeatures.field_226817_l_ : DefaultBiomeFeatures.field_226815_j_) : Feature.NORMAL_TREE.func_225566_b_(p_225546_2_ ? DefaultBiomeFeatures.field_226816_k_ : DefaultBiomeFeatures.field_226739_a_);
   }
}