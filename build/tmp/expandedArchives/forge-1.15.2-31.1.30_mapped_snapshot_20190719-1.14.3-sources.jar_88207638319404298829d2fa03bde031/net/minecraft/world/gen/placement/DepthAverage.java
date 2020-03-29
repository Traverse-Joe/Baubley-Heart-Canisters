package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class DepthAverage extends SimplePlacement<DepthAverageConfig> {
   public DepthAverage(Function<Dynamic<?>, ? extends DepthAverageConfig> p_i51385_1_) {
      super(p_i51385_1_);
   }

   public Stream<BlockPos> getPositions(Random p_212852_1_, DepthAverageConfig p_212852_2_, BlockPos p_212852_3_) {
      int i = p_212852_2_.count;
      int j = p_212852_2_.baseline;
      int k = p_212852_2_.spread;
      return IntStream.range(0, i).mapToObj((p_227439_4_) -> {
         int l = p_212852_1_.nextInt(16) + p_212852_3_.getX();
         int i1 = p_212852_1_.nextInt(16) + p_212852_3_.getZ();
         int j1 = p_212852_1_.nextInt(k) + p_212852_1_.nextInt(k) - k + j;
         return new BlockPos(l, j1, i1);
      });
   }
}