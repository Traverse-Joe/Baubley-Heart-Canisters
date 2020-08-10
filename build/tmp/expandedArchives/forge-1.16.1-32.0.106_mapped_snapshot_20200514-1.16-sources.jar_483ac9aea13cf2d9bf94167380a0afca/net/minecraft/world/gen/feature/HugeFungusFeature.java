package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class HugeFungusFeature extends Feature<HugeFungusConfig> {
   public HugeFungusFeature(Codec<HugeFungusConfig> p_i231959_1_) {
      super(p_i231959_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, HugeFungusConfig p_230362_6_) {
      Block block = p_230362_6_.field_236303_f_.getBlock();
      BlockPos blockpos = null;
      if (p_230362_6_.field_236307_j_) {
         Block block1 = p_230362_1_.getBlockState(p_230362_5_.down()).getBlock();
         if (block1 == block) {
            blockpos = p_230362_5_;
         }
      } else {
         blockpos = func_236314_a_(p_230362_1_, p_230362_5_, block);
      }

      if (blockpos == null) {
         return false;
      } else {
         int j = MathHelper.nextInt(p_230362_4_, 4, 13);
         if (p_230362_4_.nextInt(12) == 0) {
            j *= 2;
         }

         if (!p_230362_6_.field_236307_j_) {
            int i = p_230362_3_.func_230355_e_();
            if (blockpos.getY() + j + 1 >= i) {
               return false;
            }
         }

         boolean flag = !p_230362_6_.field_236307_j_ && p_230362_4_.nextFloat() < 0.06F;
         p_230362_1_.setBlockState(p_230362_5_, Blocks.AIR.getDefaultState(), 4);
         this.func_236317_a_(p_230362_1_, p_230362_4_, p_230362_6_, blockpos, j, flag);
         this.func_236321_b_(p_230362_1_, p_230362_4_, p_230362_6_, blockpos, j, flag);
         return true;
      }
   }

   private static boolean func_236315_a_(IWorld p_236315_0_, BlockPos p_236315_1_, boolean p_236315_2_) {
      return p_236315_0_.hasBlockState(p_236315_1_, (p_236320_1_) -> {
         Material material = p_236320_1_.getMaterial();
         return p_236320_1_.isAir() || p_236320_1_.isIn(Blocks.WATER) || p_236320_1_.isIn(Blocks.LAVA) || material == Material.TALL_PLANTS || p_236315_2_ && material == Material.PLANTS;
      });
   }

   private void func_236317_a_(IWorld p_236317_1_, Random p_236317_2_, HugeFungusConfig p_236317_3_, BlockPos p_236317_4_, int p_236317_5_, boolean p_236317_6_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
      BlockState blockstate = p_236317_3_.field_236304_g_;
      int i = p_236317_6_ ? 1 : 0;

      for(int j = -i; j <= i; ++j) {
         for(int k = -i; k <= i; ++k) {
            boolean flag = p_236317_6_ && MathHelper.abs(j) == i && MathHelper.abs(k) == i;

            for(int l = 0; l < p_236317_5_; ++l) {
               blockpos$mutable.func_239621_a_(p_236317_4_, j, l, k);
               if (func_236315_a_(p_236317_1_, blockpos$mutable, true)) {
                  if (p_236317_3_.field_236307_j_) {
                     if (!p_236317_1_.getBlockState(blockpos$mutable.down()).isAir()) {
                        p_236317_1_.destroyBlock(blockpos$mutable, true);
                     }

                     p_236317_1_.setBlockState(blockpos$mutable, blockstate, 3);
                  } else if (flag) {
                     if (p_236317_2_.nextFloat() < 0.1F) {
                        this.func_230367_a_(p_236317_1_, blockpos$mutable, blockstate);
                     }
                  } else {
                     this.func_230367_a_(p_236317_1_, blockpos$mutable, blockstate);
                  }
               }
            }
         }
      }

   }

   private void func_236321_b_(IWorld p_236321_1_, Random p_236321_2_, HugeFungusConfig p_236321_3_, BlockPos p_236321_4_, int p_236321_5_, boolean p_236321_6_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
      boolean flag = p_236321_3_.field_236305_h_.isIn(Blocks.NETHER_WART_BLOCK);
      int i = Math.min(p_236321_2_.nextInt(1 + p_236321_5_ / 3) + 5, p_236321_5_);
      int j = p_236321_5_ - i;

      for(int k = j; k <= p_236321_5_; ++k) {
         int l = k < p_236321_5_ - p_236321_2_.nextInt(3) ? 2 : 1;
         if (i > 8 && k < j + 4) {
            l = 3;
         }

         if (p_236321_6_) {
            ++l;
         }

         for(int i1 = -l; i1 <= l; ++i1) {
            for(int j1 = -l; j1 <= l; ++j1) {
               boolean flag1 = i1 == -l || i1 == l;
               boolean flag2 = j1 == -l || j1 == l;
               boolean flag3 = !flag1 && !flag2 && k != p_236321_5_;
               boolean flag4 = flag1 && flag2;
               boolean flag5 = k < j + 3;
               blockpos$mutable.func_239621_a_(p_236321_4_, i1, k, j1);
               if (func_236315_a_(p_236321_1_, blockpos$mutable, false)) {
                  if (p_236321_3_.field_236307_j_ && !p_236321_1_.getBlockState(blockpos$mutable.down()).isAir()) {
                     p_236321_1_.destroyBlock(blockpos$mutable, true);
                  }

                  if (flag5) {
                     if (!flag3) {
                        this.func_236318_a_(p_236321_1_, p_236321_2_, blockpos$mutable, p_236321_3_.field_236305_h_, flag);
                     }
                  } else if (flag3) {
                     this.func_236316_a_(p_236321_1_, p_236321_2_, p_236321_3_, blockpos$mutable, 0.1F, 0.2F, flag ? 0.1F : 0.0F);
                  } else if (flag4) {
                     this.func_236316_a_(p_236321_1_, p_236321_2_, p_236321_3_, blockpos$mutable, 0.01F, 0.7F, flag ? 0.083F : 0.0F);
                  } else {
                     this.func_236316_a_(p_236321_1_, p_236321_2_, p_236321_3_, blockpos$mutable, 5.0E-4F, 0.98F, flag ? 0.07F : 0.0F);
                  }
               }
            }
         }
      }

   }

   private void func_236316_a_(IWorld p_236316_1_, Random p_236316_2_, HugeFungusConfig p_236316_3_, BlockPos.Mutable p_236316_4_, float p_236316_5_, float p_236316_6_, float p_236316_7_) {
      if (p_236316_2_.nextFloat() < p_236316_5_) {
         this.func_230367_a_(p_236316_1_, p_236316_4_, p_236316_3_.field_236306_i_);
      } else if (p_236316_2_.nextFloat() < p_236316_6_) {
         this.func_230367_a_(p_236316_1_, p_236316_4_, p_236316_3_.field_236305_h_);
         if (p_236316_2_.nextFloat() < p_236316_7_) {
            func_236319_a_(p_236316_4_, p_236316_1_, p_236316_2_);
         }
      }

   }

   private void func_236318_a_(IWorld p_236318_1_, Random p_236318_2_, BlockPos p_236318_3_, BlockState p_236318_4_, boolean p_236318_5_) {
      if (p_236318_1_.getBlockState(p_236318_3_.down()).isIn(p_236318_4_.getBlock())) {
         this.func_230367_a_(p_236318_1_, p_236318_3_, p_236318_4_);
      } else if ((double)p_236318_2_.nextFloat() < 0.15D) {
         this.func_230367_a_(p_236318_1_, p_236318_3_, p_236318_4_);
         if (p_236318_5_ && p_236318_2_.nextInt(11) == 0) {
            func_236319_a_(p_236318_3_, p_236318_1_, p_236318_2_);
         }
      }

   }

   @Nullable
   private static BlockPos.Mutable func_236314_a_(IWorld p_236314_0_, BlockPos p_236314_1_, Block p_236314_2_) {
      BlockPos.Mutable blockpos$mutable = p_236314_1_.func_239590_i_();

      for(int i = p_236314_1_.getY(); i >= 1; --i) {
         blockpos$mutable.setY(i);
         Block block = p_236314_0_.getBlockState(blockpos$mutable.down()).getBlock();
         if (block == p_236314_2_) {
            return blockpos$mutable;
         }
      }

      return null;
   }

   private static void func_236319_a_(BlockPos p_236319_0_, IWorld p_236319_1_, Random p_236319_2_) {
      BlockPos.Mutable blockpos$mutable = p_236319_0_.func_239590_i_().move(Direction.DOWN);
      if (p_236319_1_.isAirBlock(blockpos$mutable)) {
         int i = MathHelper.nextInt(p_236319_2_, 1, 5);
         if (p_236319_2_.nextInt(7) == 0) {
            i *= 2;
         }

         int j = 23;
         int k = 25;
         WeepingVineFeature.func_236427_a_(p_236319_1_, p_236319_2_, blockpos$mutable, i, 23, 25);
      }
   }
}