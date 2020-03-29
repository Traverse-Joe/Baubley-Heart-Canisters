package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;

public abstract class ScatteredStructurePiece extends StructurePiece {
   protected final int width;
   protected final int height;
   protected final int depth;
   protected int hPos = -1;

   protected ScatteredStructurePiece(IStructurePieceType p_i51344_1_, Random p_i51344_2_, int p_i51344_3_, int p_i51344_4_, int p_i51344_5_, int p_i51344_6_, int p_i51344_7_, int p_i51344_8_) {
      super(p_i51344_1_, 0);
      this.width = p_i51344_6_;
      this.height = p_i51344_7_;
      this.depth = p_i51344_8_;
      this.setCoordBaseMode(Direction.Plane.HORIZONTAL.random(p_i51344_2_));
      if (this.getCoordBaseMode().getAxis() == Direction.Axis.Z) {
         this.boundingBox = new MutableBoundingBox(p_i51344_3_, p_i51344_4_, p_i51344_5_, p_i51344_3_ + p_i51344_6_ - 1, p_i51344_4_ + p_i51344_7_ - 1, p_i51344_5_ + p_i51344_8_ - 1);
      } else {
         this.boundingBox = new MutableBoundingBox(p_i51344_3_, p_i51344_4_, p_i51344_5_, p_i51344_3_ + p_i51344_8_ - 1, p_i51344_4_ + p_i51344_7_ - 1, p_i51344_5_ + p_i51344_6_ - 1);
      }

   }

   protected ScatteredStructurePiece(IStructurePieceType p_i51345_1_, CompoundNBT p_i51345_2_) {
      super(p_i51345_1_, p_i51345_2_);
      this.width = p_i51345_2_.getInt("Width");
      this.height = p_i51345_2_.getInt("Height");
      this.depth = p_i51345_2_.getInt("Depth");
      this.hPos = p_i51345_2_.getInt("HPos");
   }

   /**
    * (abstract) Helper method to read subclass data from NBT
    */
   protected void readAdditional(CompoundNBT tagCompound) {
      tagCompound.putInt("Width", this.width);
      tagCompound.putInt("Height", this.height);
      tagCompound.putInt("Depth", this.depth);
      tagCompound.putInt("HPos", this.hPos);
   }

   protected boolean func_202580_a(IWorld p_202580_1_, MutableBoundingBox p_202580_2_, int p_202580_3_) {
      if (this.hPos >= 0) {
         return true;
      } else {
         int i = 0;
         int j = 0;
         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

         for(int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k) {
            for(int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l) {
               blockpos$mutable.setPos(l, 64, k);
               if (p_202580_2_.isVecInside(blockpos$mutable)) {
                  i += p_202580_1_.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable).getY();
                  ++j;
               }
            }
         }

         if (j == 0) {
            return false;
         } else {
            this.hPos = i / j;
            this.boundingBox.offset(0, this.hPos - this.boundingBox.minY + p_202580_3_, 0);
            return true;
         }
      }
   }
}