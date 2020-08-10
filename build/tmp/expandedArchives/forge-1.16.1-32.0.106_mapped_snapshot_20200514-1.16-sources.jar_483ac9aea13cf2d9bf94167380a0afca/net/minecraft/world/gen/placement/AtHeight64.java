package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

public class AtHeight64 extends Placement<FrequencyConfig> {
   public AtHeight64(Codec<FrequencyConfig> p_i232076_1_) {
      super(p_i232076_1_);
   }

   public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator generatorIn, Random random, FrequencyConfig configIn, BlockPos pos) {
      return IntStream.range(0, configIn.count).mapToObj((p_215048_2_) -> {
         int i = random.nextInt(16) + pos.getX();
         int j = random.nextInt(16) + pos.getZ();
         int k = 64;
         return new BlockPos(i, 64, j);
      });
   }
}