package net.minecraft.world.gen.feature;

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
import net.minecraft.world.gen.feature.structure.StructureManager;

public class BasaltColumnFeature extends Feature<ColumnConfig> {
   private static final ImmutableList<Block> field_236245_a_ = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.SOUL_SAND, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);

   public BasaltColumnFeature(Codec<ColumnConfig> p_i231925_1_) {
      super(p_i231925_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, ColumnConfig p_230362_6_) {
      int i = p_230362_3_.func_230356_f_();
      BlockPos blockpos = func_236246_a_(p_230362_1_, i, p_230362_5_.func_239590_i_().func_239620_a_(Direction.Axis.Y, 1, p_230362_1_.getHeight() - 1), Integer.MAX_VALUE);
      if (blockpos == null) {
         return false;
      } else {
         int j = func_236250_a_(p_230362_4_, p_230362_6_);
         boolean flag = p_230362_4_.nextFloat() < 0.9F;
         int k = Math.min(j, flag ? 5 : 8);
         int l = flag ? 50 : 15;
         boolean flag1 = false;

         for(BlockPos blockpos1 : BlockPos.func_239585_a_(p_230362_4_, l, blockpos.getX() - k, blockpos.getY(), blockpos.getZ() - k, blockpos.getX() + k, blockpos.getY(), blockpos.getZ() + k)) {
            int i1 = j - blockpos1.manhattanDistance(blockpos);
            if (i1 >= 0) {
               flag1 |= this.func_236248_a_(p_230362_1_, i, blockpos1, i1, func_236251_b_(p_230362_4_, p_230362_6_));
            }
         }

         return flag1;
      }
   }

   private boolean func_236248_a_(IWorld p_236248_1_, int p_236248_2_, BlockPos p_236248_3_, int p_236248_4_, int p_236248_5_) {
      boolean flag = false;

      for(BlockPos blockpos : BlockPos.getAllInBoxMutable(p_236248_3_.getX() - p_236248_5_, p_236248_3_.getY(), p_236248_3_.getZ() - p_236248_5_, p_236248_3_.getX() + p_236248_5_, p_236248_3_.getY(), p_236248_3_.getZ() + p_236248_5_)) {
         int i = blockpos.manhattanDistance(p_236248_3_);
         BlockPos blockpos1 = func_236247_a_(p_236248_1_, p_236248_2_, blockpos) ? func_236246_a_(p_236248_1_, p_236248_2_, blockpos.func_239590_i_(), i) : func_236249_a_(p_236248_1_, blockpos.func_239590_i_(), i);
         if (blockpos1 != null) {
            int j = p_236248_4_ - i / 2;

            for(BlockPos.Mutable blockpos$mutable = blockpos1.func_239590_i_(); j >= 0; --j) {
               if (func_236247_a_(p_236248_1_, p_236248_2_, blockpos$mutable)) {
                  this.func_230367_a_(p_236248_1_, blockpos$mutable, Blocks.field_235337_cO_.getDefaultState());
                  blockpos$mutable.move(Direction.UP);
                  flag = true;
               } else {
                  if (!p_236248_1_.getBlockState(blockpos$mutable).isIn(Blocks.field_235337_cO_)) {
                     break;
                  }

                  blockpos$mutable.move(Direction.UP);
               }
            }
         }
      }

      return flag;
   }

   @Nullable
   private static BlockPos func_236246_a_(IWorld p_236246_0_, int p_236246_1_, BlockPos.Mutable p_236246_2_, int p_236246_3_) {
      for(; p_236246_2_.getY() > 1 && p_236246_3_ > 0; p_236246_2_.move(Direction.DOWN)) {
         --p_236246_3_;
         if (func_236247_a_(p_236246_0_, p_236246_1_, p_236246_2_)) {
            BlockState blockstate = p_236246_0_.getBlockState(p_236246_2_.move(Direction.DOWN));
            p_236246_2_.move(Direction.UP);
            if (!blockstate.isAir() && !field_236245_a_.contains(blockstate.getBlock())) {
               return p_236246_2_;
            }
         }
      }

      return null;
   }

   @Nullable
   private static BlockPos func_236249_a_(IWorld p_236249_0_, BlockPos.Mutable p_236249_1_, int p_236249_2_) {
      while(p_236249_1_.getY() < p_236249_0_.getHeight() && p_236249_2_ > 0) {
         --p_236249_2_;
         BlockState blockstate = p_236249_0_.getBlockState(p_236249_1_);
         if (field_236245_a_.contains(blockstate.getBlock())) {
            return null;
         }

         if (blockstate.isAir()) {
            return p_236249_1_;
         }

         p_236249_1_.move(Direction.UP);
      }

      return null;
   }

   private static int func_236250_a_(Random p_236250_0_, ColumnConfig p_236250_1_) {
      return p_236250_1_.field_236468_d_ + p_236250_0_.nextInt(p_236250_1_.field_236469_e_ - p_236250_1_.field_236468_d_ + 1);
   }

   private static int func_236251_b_(Random p_236251_0_, ColumnConfig p_236251_1_) {
      return p_236251_1_.field_236466_b_ + p_236251_0_.nextInt(p_236251_1_.field_236467_c_ - p_236251_1_.field_236466_b_ + 1);
   }

   private static boolean func_236247_a_(IWorld p_236247_0_, int p_236247_1_, BlockPos p_236247_2_) {
      BlockState blockstate = p_236247_0_.getBlockState(p_236247_2_);
      return blockstate.isAir() || blockstate.isIn(Blocks.LAVA) && p_236247_2_.getY() <= p_236247_1_;
   }
}