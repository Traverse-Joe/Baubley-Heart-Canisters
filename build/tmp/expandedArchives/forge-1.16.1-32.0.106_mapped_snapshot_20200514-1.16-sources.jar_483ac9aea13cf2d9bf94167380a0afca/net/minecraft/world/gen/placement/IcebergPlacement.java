package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class IcebergPlacement extends Placement<ChanceConfig> {
   public IcebergPlacement(Codec<ChanceConfig> p_i232088_1_) {
      super(p_i232088_1_);
   }

   public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator generatorIn, Random random, ChanceConfig configIn, BlockPos pos) {
      if (random.nextFloat() < 1.0F / (float)configIn.chance) {
         int i = random.nextInt(8) + 4 + pos.getX();
         int j = random.nextInt(8) + 4 + pos.getZ();
         int k = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, i, j);
         return Stream.of(new BlockPos(i, k, j));
      } else {
         return Stream.empty();
      }
   }
}