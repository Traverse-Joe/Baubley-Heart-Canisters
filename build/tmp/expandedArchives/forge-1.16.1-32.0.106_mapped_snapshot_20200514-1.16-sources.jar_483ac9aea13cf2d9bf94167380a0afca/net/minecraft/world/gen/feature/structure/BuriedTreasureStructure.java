package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class BuriedTreasureStructure extends Structure<BuriedTreasureConfig> {
   public BuriedTreasureStructure(Codec<BuriedTreasureConfig> p_i231935_1_) {
      super(p_i231935_1_);
   }

   protected boolean func_230363_a_(ChunkGenerator p_230363_1_, BiomeProvider p_230363_2_, long p_230363_3_, SharedSeedRandom p_230363_5_, int p_230363_6_, int p_230363_7_, Biome p_230363_8_, ChunkPos p_230363_9_, BuriedTreasureConfig p_230363_10_) {
      p_230363_5_.setLargeFeatureSeedWithSalt(p_230363_3_, p_230363_6_, p_230363_7_, 10387320);
      return p_230363_5_.nextFloat() < p_230363_10_.probability;
   }

   public Structure.IStartFactory<BuriedTreasureConfig> getStartFactory() {
      return BuriedTreasureStructure.Start::new;
   }

   public static class Start extends StructureStart<BuriedTreasureConfig> {
      public Start(Structure<BuriedTreasureConfig> p_i225799_1_, int p_i225799_2_, int p_i225799_3_, MutableBoundingBox p_i225799_4_, int p_i225799_5_, long p_i225799_6_) {
         super(p_i225799_1_, p_i225799_2_, p_i225799_3_, p_i225799_4_, p_i225799_5_, p_i225799_6_);
      }

      public void func_230364_a_(ChunkGenerator p_230364_1_, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, BuriedTreasureConfig p_230364_6_) {
         int i = p_230364_3_ * 16;
         int j = p_230364_4_ * 16;
         BlockPos blockpos = new BlockPos(i + 9, 90, j + 9);
         this.components.add(new BuriedTreasure.Piece(blockpos));
         this.recalculateStructureSize();
      }

      public BlockPos getPos() {
         return new BlockPos((this.getChunkPosX() << 4) + 9, 0, (this.getChunkPosZ() << 4) + 9);
      }
   }
}