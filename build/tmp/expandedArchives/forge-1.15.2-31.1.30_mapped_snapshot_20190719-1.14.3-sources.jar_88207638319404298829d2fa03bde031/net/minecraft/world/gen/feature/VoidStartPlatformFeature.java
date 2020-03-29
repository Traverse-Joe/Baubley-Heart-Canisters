package net.minecraft.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;

public class VoidStartPlatformFeature extends Feature<NoFeatureConfig> {
   private static final BlockPos field_214564_a = new BlockPos(8, 3, 8);
   private static final ChunkPos field_214565_aS = new ChunkPos(field_214564_a);

   public VoidStartPlatformFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51417_1_) {
      super(p_i51417_1_);
   }

   private static int func_214563_a(int p_214563_0_, int p_214563_1_, int p_214563_2_, int p_214563_3_) {
      return Math.max(Math.abs(p_214563_0_ - p_214563_2_), Math.abs(p_214563_1_ - p_214563_3_));
   }

   public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      ChunkPos chunkpos = new ChunkPos(pos);
      if (func_214563_a(chunkpos.x, chunkpos.z, field_214565_aS.x, field_214565_aS.z) > 1) {
         return true;
      } else {
         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

         for(int i = chunkpos.getZStart(); i <= chunkpos.getZEnd(); ++i) {
            for(int j = chunkpos.getXStart(); j <= chunkpos.getXEnd(); ++j) {
               if (func_214563_a(field_214564_a.getX(), field_214564_a.getZ(), j, i) <= 16) {
                  blockpos$mutable.setPos(j, field_214564_a.getY(), i);
                  if (blockpos$mutable.equals(field_214564_a)) {
                     worldIn.setBlockState(blockpos$mutable, Blocks.COBBLESTONE.getDefaultState(), 2);
                  } else {
                     worldIn.setBlockState(blockpos$mutable, Blocks.STONE.getDefaultState(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}