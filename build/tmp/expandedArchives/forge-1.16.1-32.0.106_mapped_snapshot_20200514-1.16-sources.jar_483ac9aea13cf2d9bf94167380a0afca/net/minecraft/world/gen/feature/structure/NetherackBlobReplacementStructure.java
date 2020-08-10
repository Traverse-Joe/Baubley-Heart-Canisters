package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NetherackBlobReplacementFeature;

public class NetherackBlobReplacementStructure extends Feature<NetherackBlobReplacementFeature> {
   public NetherackBlobReplacementStructure(Codec<NetherackBlobReplacementFeature> p_i231982_1_) {
      super(p_i231982_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NetherackBlobReplacementFeature p_230362_6_) {
      Block block = p_230362_6_.field_236609_b_.getBlock();
      BlockPos blockpos = func_236329_a_(p_230362_1_, p_230362_5_.func_239590_i_().func_239620_a_(Direction.Axis.Y, 1, p_230362_1_.getHeight() - 1), block);
      if (blockpos == null) {
         return false;
      } else {
         Vector3i vector3i = func_236330_a_(p_230362_4_, p_230362_6_);
         int i = Math.max(vector3i.getX(), Math.max(vector3i.getY(), vector3i.getZ()));
         boolean flag = false;

         for(BlockPos blockpos1 : BlockPos.func_239583_a_(blockpos, vector3i.getX(), vector3i.getY(), vector3i.getZ())) {
            if (blockpos1.manhattanDistance(blockpos) > i) {
               break;
            }

            BlockState blockstate = p_230362_1_.getBlockState(blockpos1);
            if (blockstate.isIn(block)) {
               this.func_230367_a_(p_230362_1_, blockpos1, p_230362_6_.field_236610_c_);
               flag = true;
            }
         }

         return flag;
      }
   }

   @Nullable
   private static BlockPos func_236329_a_(IWorld p_236329_0_, BlockPos.Mutable p_236329_1_, Block p_236329_2_) {
      while(p_236329_1_.getY() > 1) {
         BlockState blockstate = p_236329_0_.getBlockState(p_236329_1_);
         if (blockstate.isIn(p_236329_2_)) {
            return p_236329_1_;
         }

         p_236329_1_.move(Direction.DOWN);
      }

      return null;
   }

   private static Vector3i func_236330_a_(Random p_236330_0_, NetherackBlobReplacementFeature p_236330_1_) {
      return new Vector3i(p_236330_1_.field_236611_d_.getX() + p_236330_0_.nextInt(p_236330_1_.field_236612_e_.getX() - p_236330_1_.field_236611_d_.getX() + 1), p_236330_1_.field_236611_d_.getY() + p_236330_0_.nextInt(p_236330_1_.field_236612_e_.getY() - p_236330_1_.field_236611_d_.getY() + 1), p_236330_1_.field_236611_d_.getZ() + p_236330_0_.nextInt(p_236330_1_.field_236612_e_.getZ() - p_236330_1_.field_236611_d_.getZ() + 1));
   }
}