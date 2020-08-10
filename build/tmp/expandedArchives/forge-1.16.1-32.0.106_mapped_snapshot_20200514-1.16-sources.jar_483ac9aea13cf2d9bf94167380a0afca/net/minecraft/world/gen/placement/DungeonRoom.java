package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

public class DungeonRoom extends Placement<ChanceConfig> {
   public DungeonRoom(Codec<ChanceConfig> p_i232091_1_) {
      super(p_i232091_1_);
   }

   public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator generatorIn, Random random, ChanceConfig configIn, BlockPos pos) {
      int i = configIn.chance;
      return IntStream.range(0, i).mapToObj((p_227448_3_) -> {
         int j = random.nextInt(16) + pos.getX();
         int k = random.nextInt(16) + pos.getZ();
         int l = random.nextInt(generatorIn.func_230355_e_());
         return new BlockPos(j, l, k);
      });
   }
}