package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class VillageStructure extends Structure<VillageConfig> {
   public VillageStructure(Codec<VillageConfig> p_i232001_1_) {
      super(p_i232001_1_);
   }

   public Structure.IStartFactory<VillageConfig> getStartFactory() {
      return VillageStructure.Start::new;
   }

   public static class Start extends MarginedStructureStart<VillageConfig> {
      public Start(Structure<VillageConfig> p_i225821_1_, int p_i225821_2_, int p_i225821_3_, MutableBoundingBox p_i225821_4_, int p_i225821_5_, long p_i225821_6_) {
         super(p_i225821_1_, p_i225821_2_, p_i225821_3_, p_i225821_4_, p_i225821_5_, p_i225821_6_);
      }

      public void func_230364_a_(ChunkGenerator p_230364_1_, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, VillageConfig p_230364_6_) {
         BlockPos blockpos = new BlockPos(p_230364_3_ * 16, 0, p_230364_4_ * 16);
         VillagePieces.addPieces(p_230364_1_, p_230364_2_, blockpos, this.components, this.rand, p_230364_6_);
         this.recalculateStructureSize();
      }
   }
}