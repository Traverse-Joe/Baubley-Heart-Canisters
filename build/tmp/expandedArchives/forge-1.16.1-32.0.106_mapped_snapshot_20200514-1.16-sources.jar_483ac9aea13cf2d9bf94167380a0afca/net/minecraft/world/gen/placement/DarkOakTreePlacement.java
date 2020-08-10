package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class DarkOakTreePlacement extends Placement<NoPlacementConfig> {
   public DarkOakTreePlacement(Codec<NoPlacementConfig> p_i232082_1_) {
      super(p_i232082_1_);
   }

   public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator generatorIn, Random random, NoPlacementConfig configIn, BlockPos pos) {
      return IntStream.range(0, 16).mapToObj((p_227445_3_) -> {
         int i = p_227445_3_ / 4;
         int j = p_227445_3_ % 4;
         int k = i * 4 + 1 + random.nextInt(3) + pos.getX();
         int l = j * 4 + 1 + random.nextInt(3) + pos.getZ();
         int i1 = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, k, l);
         return new BlockPos(k, i1, l);
      });
   }
}