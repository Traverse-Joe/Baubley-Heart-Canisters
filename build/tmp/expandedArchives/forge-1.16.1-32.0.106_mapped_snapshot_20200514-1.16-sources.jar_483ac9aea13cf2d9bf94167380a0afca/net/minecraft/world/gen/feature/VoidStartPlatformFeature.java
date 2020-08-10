package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class VoidStartPlatformFeature extends Feature<NoFeatureConfig> {
   private static final BlockPos VOID_SPAWN_POS = new BlockPos(8, 3, 8);
   private static final ChunkPos VOID_SPAWN_CHUNK_POS = new ChunkPos(VOID_SPAWN_POS);

   public VoidStartPlatformFeature(Codec<NoFeatureConfig> p_i232003_1_) {
      super(p_i232003_1_);
   }

   /**
    * Returns the Manhattan distance between the two points.
    */
   private static int distance(int firstX, int firstZ, int secondX, int secondZ) {
      return Math.max(Math.abs(firstX - secondX), Math.abs(firstZ - secondZ));
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      ChunkPos chunkpos = new ChunkPos(p_230362_5_);
      if (distance(chunkpos.x, chunkpos.z, VOID_SPAWN_CHUNK_POS.x, VOID_SPAWN_CHUNK_POS.z) > 1) {
         return true;
      } else {
         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

         for(int i = chunkpos.getZStart(); i <= chunkpos.getZEnd(); ++i) {
            for(int j = chunkpos.getXStart(); j <= chunkpos.getXEnd(); ++j) {
               if (distance(VOID_SPAWN_POS.getX(), VOID_SPAWN_POS.getZ(), j, i) <= 16) {
                  blockpos$mutable.setPos(j, VOID_SPAWN_POS.getY(), i);
                  if (blockpos$mutable.equals(VOID_SPAWN_POS)) {
                     p_230362_1_.setBlockState(blockpos$mutable, Blocks.COBBLESTONE.getDefaultState(), 2);
                  } else {
                     p_230362_1_.setBlockState(blockpos$mutable, Blocks.STONE.getDefaultState(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}