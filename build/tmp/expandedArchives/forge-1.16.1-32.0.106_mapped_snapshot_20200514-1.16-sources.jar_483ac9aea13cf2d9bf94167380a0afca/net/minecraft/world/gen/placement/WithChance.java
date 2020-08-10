package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class WithChance extends SimplePlacement<ChanceConfig> {
   public WithChance(Codec<ChanceConfig> p_i232068_1_) {
      super(p_i232068_1_);
   }

   public Stream<BlockPos> getPositions(Random random, ChanceConfig p_212852_2_, BlockPos pos) {
      return random.nextFloat() < 1.0F / (float)p_212852_2_.chance ? Stream.of(pos) : Stream.empty();
   }
}