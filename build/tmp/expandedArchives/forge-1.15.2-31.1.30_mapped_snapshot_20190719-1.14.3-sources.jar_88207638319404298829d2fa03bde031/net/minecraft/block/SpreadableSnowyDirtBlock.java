package net.minecraft.block;

import java.util.Random;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public abstract class SpreadableSnowyDirtBlock extends SnowyDirtBlock {
   protected SpreadableSnowyDirtBlock(Block.Properties builder) {
      super(builder);
   }

   private static boolean func_220257_b(BlockState p_220257_0_, IWorldReader p_220257_1_, BlockPos p_220257_2_) {
      BlockPos blockpos = p_220257_2_.up();
      BlockState blockstate = p_220257_1_.getBlockState(blockpos);
      if (blockstate.getBlock() == Blocks.SNOW && blockstate.get(SnowBlock.LAYERS) == 1) {
         return true;
      } else {
         int i = LightEngine.func_215613_a(p_220257_1_, p_220257_0_, p_220257_2_, blockstate, blockpos, Direction.UP, blockstate.getOpacity(p_220257_1_, blockpos));
         return i < p_220257_1_.getMaxLightLevel();
      }
   }

   private static boolean func_220256_c(BlockState p_220256_0_, IWorldReader p_220256_1_, BlockPos p_220256_2_) {
      BlockPos blockpos = p_220256_2_.up();
      return func_220257_b(p_220256_0_, p_220256_1_, p_220256_2_) && !p_220256_1_.getFluidState(blockpos).isTagged(FluidTags.WATER);
   }

   public void func_225534_a_(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
      if (!func_220257_b(p_225534_1_, p_225534_2_, p_225534_3_)) {
         if (!p_225534_2_.isAreaLoaded(p_225534_3_, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
         p_225534_2_.setBlockState(p_225534_3_, Blocks.DIRT.getDefaultState());
      } else {
         if (p_225534_2_.getLight(p_225534_3_.up()) >= 9) {
            BlockState blockstate = this.getDefaultState();

            for(int i = 0; i < 4; ++i) {
               BlockPos blockpos = p_225534_3_.add(p_225534_4_.nextInt(3) - 1, p_225534_4_.nextInt(5) - 3, p_225534_4_.nextInt(3) - 1);
               if (p_225534_2_.getBlockState(blockpos).getBlock() == Blocks.DIRT && func_220256_c(blockstate, p_225534_2_, blockpos)) {
                  p_225534_2_.setBlockState(blockpos, blockstate.with(SNOWY, Boolean.valueOf(p_225534_2_.getBlockState(blockpos.up()).getBlock() == Blocks.SNOW)));
               }
            }
         }

      }
   }
}