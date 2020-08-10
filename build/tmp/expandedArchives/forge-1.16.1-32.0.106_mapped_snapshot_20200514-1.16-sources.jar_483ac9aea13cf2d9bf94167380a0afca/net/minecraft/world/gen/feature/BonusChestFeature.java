package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class BonusChestFeature extends Feature<NoFeatureConfig> {
   public BonusChestFeature(Codec<NoFeatureConfig> p_i231934_1_) {
      super(p_i231934_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, NoFeatureConfig p_230362_6_) {
      ChunkPos chunkpos = new ChunkPos(p_230362_5_);
      List<Integer> list = IntStream.rangeClosed(chunkpos.getXStart(), chunkpos.getXEnd()).boxed().collect(Collectors.toList());
      Collections.shuffle(list, p_230362_4_);
      List<Integer> list1 = IntStream.rangeClosed(chunkpos.getZStart(), chunkpos.getZEnd()).boxed().collect(Collectors.toList());
      Collections.shuffle(list1, p_230362_4_);
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(Integer integer : list) {
         for(Integer integer1 : list1) {
            blockpos$mutable.setPos(integer, 0, integer1);
            BlockPos blockpos = p_230362_1_.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable);
            if (p_230362_1_.isAirBlock(blockpos) || p_230362_1_.getBlockState(blockpos).getCollisionShape(p_230362_1_, blockpos).isEmpty()) {
               p_230362_1_.setBlockState(blockpos, Blocks.CHEST.getDefaultState(), 2);
               LockableLootTileEntity.setLootTable(p_230362_1_, p_230362_4_, blockpos, LootTables.CHESTS_SPAWN_BONUS_CHEST);
               BlockState blockstate = Blocks.TORCH.getDefaultState();

               for(Direction direction : Direction.Plane.HORIZONTAL) {
                  BlockPos blockpos1 = blockpos.offset(direction);
                  if (blockstate.isValidPosition(p_230362_1_, blockpos1)) {
                     p_230362_1_.setBlockState(blockpos1, blockstate, 2);
                  }
               }

               return true;
            }
         }
      }

      return false;
   }
}