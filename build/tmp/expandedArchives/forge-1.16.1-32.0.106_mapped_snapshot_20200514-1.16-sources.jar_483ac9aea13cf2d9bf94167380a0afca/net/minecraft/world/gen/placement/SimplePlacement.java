package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

public abstract class SimplePlacement<DC extends IPlacementConfig> extends Placement<DC> {
   public SimplePlacement(Codec<DC> p_i232095_1_) {
      super(p_i232095_1_);
   }

   public final Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator generatorIn, Random random, DC configIn, BlockPos pos) {
      return this.getPositions(random, configIn, pos);
   }

   protected abstract Stream<BlockPos> getPositions(Random random, DC p_212852_2_, BlockPos pos);
}