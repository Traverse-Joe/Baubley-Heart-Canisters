package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class Height4To32 extends SimplePlacement<NoPlacementConfig> {
   public Height4To32(Function<Dynamic<?>, ? extends NoPlacementConfig> p_i51374_1_) {
      super(p_i51374_1_);
   }

   public Stream<BlockPos> getPositions(Random p_212852_1_, NoPlacementConfig p_212852_2_, BlockPos p_212852_3_) {
      int i = 3 + p_212852_1_.nextInt(6);
      return IntStream.range(0, i).mapToObj((p_215060_2_) -> {
         int j = p_212852_1_.nextInt(16) + p_212852_3_.getX();
         int k = p_212852_1_.nextInt(16) + p_212852_3_.getZ();
         int l = p_212852_1_.nextInt(28) + 4;
         return new BlockPos(j, l, k);
      });
   }
}