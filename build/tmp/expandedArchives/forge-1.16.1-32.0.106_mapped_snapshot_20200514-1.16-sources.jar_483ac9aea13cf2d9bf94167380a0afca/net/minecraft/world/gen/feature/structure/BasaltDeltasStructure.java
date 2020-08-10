package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BasaltDeltasFeature;
import net.minecraft.world.gen.feature.Feature;

public class BasaltDeltasStructure extends Feature<BasaltDeltasFeature> {
   private static final ImmutableList<Block> field_236274_a_ = ImmutableList.of(Blocks.BEDROCK, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);
   private static final Direction[] field_236275_ac_ = Direction.values();

   private static int func_236278_a_(Random p_236278_0_, BasaltDeltasFeature p_236278_1_) {
      return p_236278_1_.field_236498_d_ + p_236278_0_.nextInt(p_236278_1_.field_236499_e_ - p_236278_1_.field_236498_d_ + 1);
   }

   private static int func_236279_b_(Random p_236279_0_, BasaltDeltasFeature p_236279_1_) {
      return p_236279_0_.nextInt(p_236279_1_.field_236500_f_ + 1);
   }

   public BasaltDeltasStructure(Codec<BasaltDeltasFeature> p_i231946_1_) {
      super(p_i231946_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, BasaltDeltasFeature p_230362_6_) {
      BlockPos blockpos = func_236276_a_(p_230362_1_, p_230362_5_.func_239590_i_().func_239620_a_(Direction.Axis.Y, 1, p_230362_1_.getHeight() - 1));
      if (blockpos == null) {
         return false;
      } else {
         boolean flag = false;
         boolean flag1 = p_230362_4_.nextDouble() < 0.9D;
         int i = flag1 ? func_236279_b_(p_230362_4_, p_230362_6_) : 0;
         int j = flag1 ? func_236279_b_(p_230362_4_, p_230362_6_) : 0;
         boolean flag2 = flag1 && i != 0 && j != 0;
         int k = func_236278_a_(p_230362_4_, p_230362_6_);
         int l = func_236278_a_(p_230362_4_, p_230362_6_);
         int i1 = Math.max(k, l);

         for(BlockPos blockpos1 : BlockPos.func_239583_a_(blockpos, k, 0, l)) {
            if (blockpos1.manhattanDistance(blockpos) > i1) {
               break;
            }

            if (func_236277_a_(p_230362_1_, blockpos1, p_230362_6_)) {
               if (flag2) {
                  flag = true;
                  this.func_230367_a_(p_230362_1_, blockpos1, p_230362_6_.field_236497_c_);
               }

               BlockPos blockpos2 = blockpos1.add(i, 0, j);
               if (func_236277_a_(p_230362_1_, blockpos2, p_230362_6_)) {
                  flag = true;
                  this.func_230367_a_(p_230362_1_, blockpos2, p_230362_6_.field_236496_b_);
               }
            }
         }

         return flag;
      }
   }

   private static boolean func_236277_a_(IWorld p_236277_0_, BlockPos p_236277_1_, BasaltDeltasFeature p_236277_2_) {
      BlockState blockstate = p_236277_0_.getBlockState(p_236277_1_);
      if (blockstate.isIn(p_236277_2_.field_236496_b_.getBlock())) {
         return false;
      } else if (field_236274_a_.contains(blockstate.getBlock())) {
         return false;
      } else {
         for(Direction direction : field_236275_ac_) {
            boolean flag = p_236277_0_.getBlockState(p_236277_1_.offset(direction)).isAir();
            if (flag && direction != Direction.UP || !flag && direction == Direction.UP) {
               return false;
            }
         }

         return true;
      }
   }

   @Nullable
   private static BlockPos func_236276_a_(IWorld p_236276_0_, BlockPos.Mutable p_236276_1_) {
      for(; p_236276_1_.getY() > 1; p_236276_1_.move(Direction.DOWN)) {
         if (p_236276_0_.getBlockState(p_236276_1_).isAir()) {
            BlockState blockstate = p_236276_0_.getBlockState(p_236276_1_.move(Direction.DOWN));
            p_236276_1_.move(Direction.UP);
            if (!blockstate.isIn(Blocks.LAVA) && !blockstate.isIn(Blocks.BEDROCK) && !blockstate.isAir()) {
               return p_236276_1_;
            }
         }
      }

      return null;
   }
}