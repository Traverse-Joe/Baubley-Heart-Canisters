package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class WithChance extends SimplePlacement<ChanceConfig> {
   public WithChance(Function<Dynamic<?>, ? extends ChanceConfig> p_i51393_1_) {
      super(p_i51393_1_);
   }

   public Stream<BlockPos> getPositions(Random p_212852_1_, ChanceConfig p_212852_2_, BlockPos p_212852_3_) {
      return p_212852_1_.nextFloat() < 1.0F / (float)p_212852_2_.chance ? Stream.of(p_212852_3_) : Stream.empty();
   }
}