package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class Passthrough extends SimplePlacement<NoPlacementConfig> {
   public Passthrough(Function<Dynamic<?>, ? extends NoPlacementConfig> p_i51363_1_) {
      super(p_i51363_1_);
   }

   public Stream<BlockPos> getPositions(Random p_212852_1_, NoPlacementConfig p_212852_2_, BlockPos p_212852_3_) {
      return Stream.of(p_212852_3_);
   }
}