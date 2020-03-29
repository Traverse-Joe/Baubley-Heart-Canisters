package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IWorldReader extends ILightReader, ICollisionReader, BiomeManager.IBiomeReader {
   @Nullable
   IChunk getChunk(int x, int z, ChunkStatus requiredStatus, boolean nonnull);

   @Deprecated
   boolean chunkExists(int chunkX, int chunkZ);

   int getHeight(Heightmap.Type heightmapType, int x, int z);

   int getSkylightSubtracted();

   BiomeManager func_225523_d_();

   default Biome func_226691_t_(BlockPos p_226691_1_) {
      return this.func_225523_d_().func_226836_a_(p_226691_1_);
   }

   @OnlyIn(Dist.CLIENT)
   default int func_225525_a_(BlockPos p_225525_1_, ColorResolver p_225525_2_) {
      return p_225525_2_.getColor(this.func_226691_t_(p_225525_1_), (double)p_225525_1_.getX(), (double)p_225525_1_.getZ());
   }

   default Biome func_225526_b_(int p_225526_1_, int p_225526_2_, int p_225526_3_) {
      IChunk ichunk = this.getChunk(p_225526_1_ >> 2, p_225526_3_ >> 2, ChunkStatus.BIOMES, false);
      return ichunk != null && ichunk.func_225549_i_() != null ? ichunk.func_225549_i_().func_225526_b_(p_225526_1_, p_225526_2_, p_225526_3_) : this.func_225604_a_(p_225526_1_, p_225526_2_, p_225526_3_);
   }

   Biome func_225604_a_(int p_225604_1_, int p_225604_2_, int p_225604_3_);

   boolean isRemote();

   int getSeaLevel();

   Dimension getDimension();

   default BlockPos getHeight(Heightmap.Type heightmapType, BlockPos pos) {
      return new BlockPos(pos.getX(), this.getHeight(heightmapType, pos.getX(), pos.getZ()), pos.getZ());
   }

   /**
    * Checks to see if an air block exists at the provided location. Note that this only checks to see if the blocks
    * material is set to air, meaning it is possible for non-vanilla blocks to still pass this check.
    */
   default boolean isAirBlock(BlockPos pos) {
      return this.getBlockState(pos).isAir(this, pos);
   }

   default boolean canBlockSeeSky(BlockPos pos) {
      if (pos.getY() >= this.getSeaLevel()) {
         return this.func_226660_f_(pos);
      } else {
         BlockPos blockpos = new BlockPos(pos.getX(), this.getSeaLevel(), pos.getZ());
         if (!this.func_226660_f_(blockpos)) {
            return false;
         } else {
            for(BlockPos blockpos1 = blockpos.down(); blockpos1.getY() > pos.getY(); blockpos1 = blockpos1.down()) {
               BlockState blockstate = this.getBlockState(blockpos1);
               if (blockstate.getOpacity(this, blockpos1) > 0 && !blockstate.getMaterial().isLiquid()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Deprecated
   default float getBrightness(BlockPos pos) {
      return this.getDimension().func_227174_a_(this.getLight(pos));
   }

   default int getStrongPower(BlockPos pos, Direction direction) {
      return this.getBlockState(pos).getStrongPower(this, pos, direction);
   }

   default IChunk getChunk(BlockPos pos) {
      return this.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
   }

   default IChunk getChunk(int chunkX, int chunkZ) {
      return this.getChunk(chunkX, chunkZ, ChunkStatus.FULL, true);
   }

   default IChunk getChunk(int chunkX, int chunkZ, ChunkStatus requiredStatus) {
      return this.getChunk(chunkX, chunkZ, requiredStatus, true);
   }

   @Nullable
   default IBlockReader func_225522_c_(int p_225522_1_, int p_225522_2_) {
      return this.getChunk(p_225522_1_, p_225522_2_, ChunkStatus.EMPTY, false);
   }

   default boolean hasWater(BlockPos pos) {
      return this.getFluidState(pos).isTagged(FluidTags.WATER);
   }

   /**
    * Checks if any of the blocks within the aabb are liquids.
    */
   default boolean containsAnyLiquid(AxisAlignedBB bb) {
      int i = MathHelper.floor(bb.minX);
      int j = MathHelper.ceil(bb.maxX);
      int k = MathHelper.floor(bb.minY);
      int l = MathHelper.ceil(bb.maxY);
      int i1 = MathHelper.floor(bb.minZ);
      int j1 = MathHelper.ceil(bb.maxZ);

      try (BlockPos.PooledMutable blockpos$pooledmutable = BlockPos.PooledMutable.retain()) {
         for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
               for(int i2 = i1; i2 < j1; ++i2) {
                  BlockState blockstate = this.getBlockState(blockpos$pooledmutable.setPos(k1, l1, i2));
                  if (!blockstate.getFluidState().isEmpty()) {
                     boolean flag = true;
                     return flag;
                  }
               }
            }
         }

         return false;
      }
   }

   default int getLight(BlockPos pos) {
      return this.getNeighborAwareLightSubtracted(pos, this.getSkylightSubtracted());
   }

   default int getNeighborAwareLightSubtracted(BlockPos pos, int amount) {
      return pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000 ? this.func_226659_b_(pos, amount) : 15;
   }

   @Deprecated
   default boolean isBlockLoaded(BlockPos pos) {
      return this.chunkExists(pos.getX() >> 4, pos.getZ() >> 4);
   }

   default boolean isAreaLoaded(BlockPos center, int range) {
      return this.isAreaLoaded(center.add(-range, -range, -range), center.add(range, range, range));
   }

   @Deprecated
   default boolean isAreaLoaded(BlockPos from, BlockPos to) {
      return this.isAreaLoaded(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
   }

   @Deprecated
   default boolean isAreaLoaded(int p_217344_1_, int p_217344_2_, int p_217344_3_, int p_217344_4_, int p_217344_5_, int p_217344_6_) {
      if (p_217344_5_ >= 0 && p_217344_2_ < 256) {
         p_217344_1_ = p_217344_1_ >> 4;
         p_217344_3_ = p_217344_3_ >> 4;
         p_217344_4_ = p_217344_4_ >> 4;
         p_217344_6_ = p_217344_6_ >> 4;

         for(int i = p_217344_1_; i <= p_217344_4_; ++i) {
            for(int j = p_217344_3_; j <= p_217344_6_; ++j) {
               if (!this.chunkExists(i, j)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}