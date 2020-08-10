package net.minecraft.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

public class TransportationHelper {
   public static int[][] func_234632_a_(Direction p_234632_0_) {
      Direction direction = p_234632_0_.rotateY();
      Direction direction1 = direction.getOpposite();
      Direction direction2 = p_234632_0_.getOpposite();
      return new int[][]{{direction.getXOffset(), direction.getZOffset()}, {direction1.getXOffset(), direction1.getZOffset()}, {direction2.getXOffset() + direction.getXOffset(), direction2.getZOffset() + direction.getZOffset()}, {direction2.getXOffset() + direction1.getXOffset(), direction2.getZOffset() + direction1.getZOffset()}, {p_234632_0_.getXOffset() + direction.getXOffset(), p_234632_0_.getZOffset() + direction.getZOffset()}, {p_234632_0_.getXOffset() + direction1.getXOffset(), p_234632_0_.getZOffset() + direction1.getZOffset()}, {direction2.getXOffset(), direction2.getZOffset()}, {p_234632_0_.getXOffset(), p_234632_0_.getZOffset()}};
   }

   public static boolean func_234630_a_(double p_234630_0_) {
      return !Double.isInfinite(p_234630_0_) && p_234630_0_ < 1.0D;
   }

   public static boolean func_234631_a_(World p_234631_0_, LivingEntity p_234631_1_, AxisAlignedBB p_234631_2_) {
      return p_234631_0_.getCollisionShapes(p_234631_1_, p_234631_2_).allMatch(VoxelShape::isEmpty);
   }
}