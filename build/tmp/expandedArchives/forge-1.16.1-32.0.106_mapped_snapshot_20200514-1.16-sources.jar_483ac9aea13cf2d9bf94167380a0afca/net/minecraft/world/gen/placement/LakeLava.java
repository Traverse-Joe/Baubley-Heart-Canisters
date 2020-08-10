package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

public class LakeLava extends Placement<ChanceConfig> {
   public LakeLava(Codec<ChanceConfig> p_i232089_1_) {
      super(p_i232089_1_);
   }

   public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator generatorIn, Random random, ChanceConfig configIn, BlockPos pos) {
      if (random.nextInt(configIn.chance / 10) == 0) {
         int i = random.nextInt(16) + pos.getX();
         int j = random.nextInt(16) + pos.getZ();
         int k = random.nextInt(random.nextInt(generatorIn.func_230355_e_() - 8) + 8);
         if (k < worldIn.getSeaLevel() || random.nextInt(configIn.chance / 8) == 0) {
            return Stream.of(new BlockPos(i, k, j));
         }
      }

      return Stream.empty();
   }
}