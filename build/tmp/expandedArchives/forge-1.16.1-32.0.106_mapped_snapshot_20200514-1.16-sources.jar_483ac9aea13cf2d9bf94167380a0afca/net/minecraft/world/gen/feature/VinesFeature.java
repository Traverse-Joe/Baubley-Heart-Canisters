package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class VinesFeature extends Feature<NoFeatureConfig> {
   private static final Direction[] DIRECTIONS = Direction.values();

   public VinesFeature(Codec<NoFeatureConfig> p_i232002_1_) {
      super(p_i232002_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      BlockPos.Mutable blockpos$mutable = p_230362_5_.func_239590_i_();

      for(int i = p_230362_5_.getY(); i < 256; ++i) {
         blockpos$mutable.setPos(p_230362_5_);
         blockpos$mutable.move(p_230362_4_.nextInt(4) - p_230362_4_.nextInt(4), 0, p_230362_4_.nextInt(4) - p_230362_4_.nextInt(4));
         blockpos$mutable.setY(i);
         if (p_230362_1_.isAirBlock(blockpos$mutable)) {
            for(Direction direction : DIRECTIONS) {
               if (direction != Direction.DOWN && VineBlock.canAttachTo(p_230362_1_, blockpos$mutable, direction)) {
                  p_230362_1_.setBlockState(blockpos$mutable, Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(direction), Boolean.valueOf(true)), 2);
                  break;
               }
            }
         }
      }

      return true;
   }
}