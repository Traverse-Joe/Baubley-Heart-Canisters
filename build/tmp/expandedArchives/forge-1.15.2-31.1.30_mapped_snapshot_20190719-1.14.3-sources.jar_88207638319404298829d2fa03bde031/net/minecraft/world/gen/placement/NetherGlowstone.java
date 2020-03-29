package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class NetherGlowstone extends SimplePlacement<FrequencyConfig> {
   public NetherGlowstone(Function<Dynamic<?>, ? extends FrequencyConfig> p_i51355_1_) {
      super(p_i51355_1_);
   }

   public Stream<BlockPos> getPositions(Random p_212852_1_, FrequencyConfig p_212852_2_, BlockPos p_212852_3_) {
      return IntStream.range(0, p_212852_1_.nextInt(p_212852_1_.nextInt(p_212852_2_.count) + 1)).mapToObj((p_215062_2_) -> {
         int i = p_212852_1_.nextInt(16) + p_212852_3_.getX();
         int j = p_212852_1_.nextInt(16) + p_212852_3_.getZ();
         int k = p_212852_1_.nextInt(120) + 4;
         return new BlockPos(i, k, j);
      });
   }
}