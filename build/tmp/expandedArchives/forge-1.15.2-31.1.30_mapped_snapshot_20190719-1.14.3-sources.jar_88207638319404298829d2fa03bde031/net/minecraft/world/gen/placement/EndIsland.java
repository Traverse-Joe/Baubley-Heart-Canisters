package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class EndIsland extends SimplePlacement<NoPlacementConfig> {
   public EndIsland(Function<Dynamic<?>, ? extends NoPlacementConfig> p_i51372_1_) {
      super(p_i51372_1_);
   }

   public Stream<BlockPos> getPositions(Random p_212852_1_, NoPlacementConfig p_212852_2_, BlockPos p_212852_3_) {
      Stream<BlockPos> stream = Stream.empty();
      if (p_212852_1_.nextInt(14) == 0) {
         stream = Stream.concat(stream, Stream.of(p_212852_3_.add(p_212852_1_.nextInt(16), 55 + p_212852_1_.nextInt(16), p_212852_1_.nextInt(16))));
         if (p_212852_1_.nextInt(4) == 0) {
            stream = Stream.concat(stream, Stream.of(p_212852_3_.add(p_212852_1_.nextInt(16), 55 + p_212852_1_.nextInt(16), p_212852_1_.nextInt(16))));
         }

         return stream;
      } else {
         return Stream.empty();
      }
   }
}