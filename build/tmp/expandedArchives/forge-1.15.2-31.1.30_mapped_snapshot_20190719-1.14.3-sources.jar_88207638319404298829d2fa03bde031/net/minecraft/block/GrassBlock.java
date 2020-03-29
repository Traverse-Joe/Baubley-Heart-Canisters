package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;

public class GrassBlock extends SpreadableSnowyDirtBlock implements IGrowable {
   public GrassBlock(Block.Properties properties) {
      super(properties);
   }

   /**
    * Whether this IGrowable can grow
    */
   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return worldIn.getBlockState(pos.up()).isAir();
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
      BlockPos blockpos = p_225535_3_.up();
      BlockState blockstate = Blocks.GRASS.getDefaultState();

      for(int i = 0; i < 128; ++i) {
         BlockPos blockpos1 = blockpos;
         int j = 0;

         while(true) {
            if (j >= i / 16) {
               BlockState blockstate2 = p_225535_1_.getBlockState(blockpos1);
               if (blockstate2.getBlock() == blockstate.getBlock() && p_225535_2_.nextInt(10) == 0) {
                  ((IGrowable)blockstate.getBlock()).func_225535_a_(p_225535_1_, p_225535_2_, blockpos1, blockstate2);
               }

               if (!blockstate2.isAir()) {
                  break;
               }

               BlockState blockstate1;
               if (p_225535_2_.nextInt(8) == 0) {
                  List<ConfiguredFeature<?, ?>> list = p_225535_1_.func_226691_t_(blockpos1).getFlowers();
                  if (list.isEmpty()) {
                     break;
                  }

                  ConfiguredFeature<?, ?> configuredfeature = ((DecoratedFeatureConfig)(list.get(0)).config).feature;
                  blockstate1 = ((FlowersFeature)configuredfeature.feature).func_225562_b_(p_225535_2_, blockpos1, configuredfeature.config);
               } else {
                  blockstate1 = blockstate;
               }

               if (blockstate1.isValidPosition(p_225535_1_, blockpos1)) {
                  p_225535_1_.setBlockState(blockpos1, blockstate1, 3);
               }
               break;
            }

            blockpos1 = blockpos1.add(p_225535_2_.nextInt(3) - 1, (p_225535_2_.nextInt(3) - 1) * p_225535_2_.nextInt(3) / 2, p_225535_2_.nextInt(3) - 1);
            if (p_225535_1_.getBlockState(blockpos1.down()).getBlock() != this || p_225535_1_.getBlockState(blockpos1).func_224756_o(p_225535_1_, blockpos1)) {
               break;
            }

            ++j;
         }
      }

   }
}