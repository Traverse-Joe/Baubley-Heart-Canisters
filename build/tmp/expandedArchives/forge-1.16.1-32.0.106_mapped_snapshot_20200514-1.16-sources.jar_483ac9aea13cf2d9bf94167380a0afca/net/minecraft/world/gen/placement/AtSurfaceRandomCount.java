package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class AtSurfaceRandomCount extends Placement<FrequencyConfig> {
   public AtSurfaceRandomCount(Codec<FrequencyConfig> p_i232087_1_) {
      super(p_i232087_1_);
   }

   public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator generatorIn, Random random, FrequencyConfig configIn, BlockPos pos) {
      int i = random.nextInt(configIn.count);
      return IntStream.range(0, i).mapToObj((p_227447_3_) -> {
         int j = random.nextInt(16) + pos.getX();
         int k = random.nextInt(16) + pos.getZ();
         int l = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, j, k);
         return new BlockPos(j, l, k);
      });
   }
}