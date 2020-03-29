package net.minecraft.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;

public class TreeFeature extends AbstractSmallTreeFeature<TreeFeatureConfig> {
   public TreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> p_i225820_1_) {
      super(p_i225820_1_);
   }

   public boolean func_225557_a_(IWorldGenerationReader p_225557_1_, Random p_225557_2_, BlockPos p_225557_3_, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox p_225557_6_, TreeFeatureConfig p_225557_7_) {
      int i = p_225557_7_.field_227371_p_ + p_225557_2_.nextInt(p_225557_7_.field_227328_b_ + 1) + p_225557_2_.nextInt(p_225557_7_.field_227329_c_ + 1);
      int j = p_225557_7_.field_227330_d_ >= 0 ? p_225557_7_.field_227330_d_ + p_225557_2_.nextInt(p_225557_7_.field_227331_f_ + 1) : i - (p_225557_7_.field_227334_i_ + p_225557_2_.nextInt(p_225557_7_.field_227335_j_ + 1));
      int k = p_225557_7_.field_227327_a_.func_225573_a_(p_225557_2_, j, i, p_225557_7_);
      Optional<BlockPos> optional = this.func_227212_a_(p_225557_1_, i, j, k, p_225557_3_, p_225557_7_);
      if (!optional.isPresent()) {
         return false;
      } else {
         BlockPos blockpos = optional.get();
         this.setDirtAt(p_225557_1_, blockpos.down(), blockpos);
         p_225557_7_.field_227327_a_.func_225571_a_(p_225557_1_, p_225557_2_, p_225557_7_, i, j, k, blockpos, p_225557_5_);
         this.func_227213_a_(p_225557_1_, p_225557_2_, i, blockpos, p_225557_7_.field_227332_g_ + p_225557_2_.nextInt(p_225557_7_.field_227333_h_ + 1), p_225557_4_, p_225557_6_, p_225557_7_);
         return true;
      }
   }
}