package net.minecraft.util.math;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public class ChunkPos {
   /** Value representing an absent or invalid chunkpos */
   public static final long SENTINEL = asLong(1875016, 1875016);
   public final int x;
   public final int z;

   public ChunkPos(int x, int z) {
      this.x = x;
      this.z = z;
   }

   public ChunkPos(BlockPos pos) {
      this.x = pos.getX() >> 4;
      this.z = pos.getZ() >> 4;
   }

   public ChunkPos(long longIn) {
      this.x = (int)longIn;
      this.z = (int)(longIn >> 32);
   }

   public long asLong() {
      return asLong(this.x, this.z);
   }

   /**
    * Converts the chunk coordinate pair to a long
    */
   public static long asLong(int x, int z) {
      return (long)x & 4294967295L | ((long)z & 4294967295L) << 32;
   }

   public static int getX(long p_212578_0_) {
      return (int)(p_212578_0_ & 4294967295L);
   }

   public static int getZ(long p_212579_0_) {
      return (int)(p_212579_0_ >>> 32 & 4294967295L);
   }

   public int hashCode() {
      int i = 1664525 * this.x + 1013904223;
      int j = 1664525 * (this.z ^ -559038737) + 1013904223;
      return i ^ j;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof ChunkPos)) {
         return false;
      } else {
         ChunkPos chunkpos = (ChunkPos)p_equals_1_;
         return this.x == chunkpos.x && this.z == chunkpos.z;
      }
   }

   /**
    * Get the first world X coordinate that belongs to this Chunk
    */
   public int getXStart() {
      return this.x << 4;
   }

   /**
    * Get the first world Z coordinate that belongs to this Chunk
    */
   public int getZStart() {
      return this.z << 4;
   }

   /**
    * Get the last world X coordinate that belongs to this Chunk
    */
   public int getXEnd() {
      return (this.x << 4) + 15;
   }

   /**
    * Get the last world Z coordinate that belongs to this Chunk
    */
   public int getZEnd() {
      return (this.z << 4) + 15;
   }

   /**
    * Gets the x-coordinate of the region file containing this chunk.
    */
   public int getRegionCoordX() {
      return this.x >> 5;
   }

   /**
    * Gets the z-coordinate of the region file containing this chunk.
    */
   public int getRegionCoordZ() {
      return this.z >> 5;
   }

   /**
    * Gets the x-coordinate of this chunk within the region file that contains it.
    */
   public int getRegionPositionX() {
      return this.x & 31;
   }

   /**
    * Gets the z-coordinate of this chunk within the region file that contains it.
    */
   public int getRegionPositionZ() {
      return this.z & 31;
   }

   /**
    * Get the World coordinates of the Block with the given Chunk coordinates relative to this chunk
    */
   public BlockPos getBlock(int x, int y, int z) {
      return new BlockPos((this.x << 4) + x, y, (this.z << 4) + z);
   }

   public String toString() {
      return "[" + this.x + ", " + this.z + "]";
   }

   public BlockPos asBlockPos() {
      return new BlockPos(this.x << 4, 0, this.z << 4);
   }

   public int func_226661_a_(ChunkPos p_226661_1_) {
      return Math.max(Math.abs(this.x - p_226661_1_.x), Math.abs(this.z - p_226661_1_.z));
   }

   public static Stream<ChunkPos> getAllInBox(ChunkPos center, int radius) {
      return getAllInBox(new ChunkPos(center.x - radius, center.z - radius), new ChunkPos(center.x + radius, center.z + radius));
   }

   public static Stream<ChunkPos> getAllInBox(final ChunkPos p_222239_0_, final ChunkPos p_222239_1_) {
      int i = Math.abs(p_222239_0_.x - p_222239_1_.x) + 1;
      int j = Math.abs(p_222239_0_.z - p_222239_1_.z) + 1;
      final int k = p_222239_0_.x < p_222239_1_.x ? 1 : -1;
      final int l = p_222239_0_.z < p_222239_1_.z ? 1 : -1;
      return StreamSupport.stream(new AbstractSpliterator<ChunkPos>((long)(i * j), 64) {
         @Nullable
         private ChunkPos field_222237_e;

         public boolean tryAdvance(Consumer<? super ChunkPos> p_tryAdvance_1_) {
            if (this.field_222237_e == null) {
               this.field_222237_e = p_222239_0_;
            } else {
               int i1 = this.field_222237_e.x;
               int j1 = this.field_222237_e.z;
               if (i1 == p_222239_1_.x) {
                  if (j1 == p_222239_1_.z) {
                     return false;
                  }

                  this.field_222237_e = new ChunkPos(p_222239_0_.x, j1 + l);
               } else {
                  this.field_222237_e = new ChunkPos(i1 + k, j1);
               }
            }

            p_tryAdvance_1_.accept(this.field_222237_e);
            return true;
         }
      }, false);
   }
}