package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class PillagerOutpostStructure extends Structure<NoFeatureConfig> {
   /** List of enemies that can spawn in the Pillage Outpost. */
   private static final List<Biome.SpawnListEntry> PILLAGE_OUTPOST_ENEMIES = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.PILLAGER, 1, 1, 1));

   public PillagerOutpostStructure(Codec<NoFeatureConfig> p_i231977_1_) {
      super(p_i231977_1_);
   }

   public List<Biome.SpawnListEntry> getSpawnList() {
      return PILLAGE_OUTPOST_ENEMIES;
   }

   protected boolean func_230363_a_(ChunkGenerator p_230363_1_, BiomeProvider p_230363_2_, long p_230363_3_, SharedSeedRandom p_230363_5_, int p_230363_6_, int p_230363_7_, Biome p_230363_8_, ChunkPos p_230363_9_, NoFeatureConfig p_230363_10_) {
      int i = p_230363_6_ >> 4;
      int j = p_230363_7_ >> 4;
      p_230363_5_.setSeed((long)(i ^ j << 4) ^ p_230363_3_);
      p_230363_5_.nextInt();
      if (p_230363_5_.nextInt(5) != 0) {
         return false;
      } else {
         for(int k = p_230363_6_ - 10; k <= p_230363_6_ + 10; ++k) {
            for(int l = p_230363_7_ - 10; l <= p_230363_7_ + 10; ++l) {
               ChunkPos chunkpos = Structure.field_236381_q_.func_236392_a_(p_230363_1_.func_235957_b_().func_236197_a_(Structure.field_236381_q_), p_230363_3_, p_230363_5_, k, l);
               if (k == chunkpos.x && l == chunkpos.z) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
      return PillagerOutpostStructure.Start::new;
   }

   public static class Start extends MarginedStructureStart<NoFeatureConfig> {
      public Start(Structure<NoFeatureConfig> p_i225815_1_, int p_i225815_2_, int p_i225815_3_, MutableBoundingBox p_i225815_4_, int p_i225815_5_, long p_i225815_6_) {
         super(p_i225815_1_, p_i225815_2_, p_i225815_3_, p_i225815_4_, p_i225815_5_, p_i225815_6_);
      }

      public void func_230364_a_(ChunkGenerator p_230364_1_, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, NoFeatureConfig p_230364_6_) {
         BlockPos blockpos = new BlockPos(p_230364_3_ * 16, 0, p_230364_4_ * 16);
         PillagerOutpostPieces.func_215139_a(p_230364_1_, p_230364_2_, blockpos, this.components, this.rand);
         this.recalculateStructureSize();
      }
   }
}