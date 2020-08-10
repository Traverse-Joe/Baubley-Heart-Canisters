package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class EndPodiumFeature extends Feature<NoFeatureConfig> {
   public static final BlockPos END_PODIUM_LOCATION = BlockPos.ZERO;
   private final boolean activePortal;

   public EndPodiumFeature(boolean activePortalIn) {
      super(NoFeatureConfig.field_236558_a_);
      this.activePortal = activePortalIn;
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      for(BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(p_230362_5_.getX() - 4, p_230362_5_.getY() - 1, p_230362_5_.getZ() - 4), new BlockPos(p_230362_5_.getX() + 4, p_230362_5_.getY() + 32, p_230362_5_.getZ() + 4))) {
         boolean flag = blockpos.withinDistance(p_230362_5_, 2.5D);
         if (flag || blockpos.withinDistance(p_230362_5_, 3.5D)) {
            if (blockpos.getY() < p_230362_5_.getY()) {
               if (flag) {
                  this.func_230367_a_(p_230362_1_, blockpos, Blocks.BEDROCK.getDefaultState());
               } else if (blockpos.getY() < p_230362_5_.getY()) {
                  this.func_230367_a_(p_230362_1_, blockpos, Blocks.END_STONE.getDefaultState());
               }
            } else if (blockpos.getY() > p_230362_5_.getY()) {
               this.func_230367_a_(p_230362_1_, blockpos, Blocks.AIR.getDefaultState());
            } else if (!flag) {
               this.func_230367_a_(p_230362_1_, blockpos, Blocks.BEDROCK.getDefaultState());
            } else if (this.activePortal) {
               this.func_230367_a_(p_230362_1_, new BlockPos(blockpos), Blocks.END_PORTAL.getDefaultState());
            } else {
               this.func_230367_a_(p_230362_1_, new BlockPos(blockpos), Blocks.AIR.getDefaultState());
            }
         }
      }

      for(int i = 0; i < 4; ++i) {
         this.func_230367_a_(p_230362_1_, p_230362_5_.up(i), Blocks.BEDROCK.getDefaultState());
      }

      BlockPos blockpos1 = p_230362_5_.up(2);

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         this.func_230367_a_(p_230362_1_, blockpos1.offset(direction), Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, direction));
      }

      return true;
   }
}