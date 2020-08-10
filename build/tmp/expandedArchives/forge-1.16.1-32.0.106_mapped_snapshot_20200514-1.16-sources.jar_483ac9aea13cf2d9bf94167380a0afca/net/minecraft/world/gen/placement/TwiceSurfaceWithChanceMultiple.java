package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class TwiceSurfaceWithChanceMultiple extends Placement<HeightWithChanceConfig> {
   public TwiceSurfaceWithChanceMultiple(Codec<HeightWithChanceConfig> p_i232073_1_) {
      super(p_i232073_1_);
   }

   public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator generatorIn, Random random, HeightWithChanceConfig configIn, BlockPos pos) {
      return IntStream.range(0, configIn.count).filter((p_215045_2_) -> {
         return random.nextFloat() < configIn.chance;
      }).mapToObj((p_227438_3_) -> {
         int i = random.nextInt(16) + pos.getX();
         int j = random.nextInt(16) + pos.getZ();
         int k = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, i, j) * 2;
         return k <= 0 ? null : new BlockPos(i, random.nextInt(k), j);
      }).filter(Objects::nonNull);
   }
}