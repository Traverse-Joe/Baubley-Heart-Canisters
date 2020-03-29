package net.minecraft.world.gen.feature.structure;

import java.util.List;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class VillagePieces {
   public static void func_214838_a(ChunkGenerator<?> p_214838_0_, TemplateManager p_214838_1_, BlockPos p_214838_2_, List<StructurePiece> p_214838_3_, SharedSeedRandom p_214838_4_, VillageConfig p_214838_5_) {
      PlainsVillagePools.init();
      SnowyVillagePools.init();
      SavannaVillagePools.init();
      DesertVillagePools.init();
      TaigaVillagePools.init();
      JigsawManager.func_214889_a(p_214838_5_.startPool, p_214838_5_.size, VillagePieces.Village::new, p_214838_0_, p_214838_1_, p_214838_2_, p_214838_3_, p_214838_4_);
   }

   public static class Village extends AbstractVillagePiece {
      public Village(TemplateManager p_i50890_1_, JigsawPiece p_i50890_2_, BlockPos p_i50890_3_, int p_i50890_4_, Rotation p_i50890_5_, MutableBoundingBox p_i50890_6_) {
         super(IStructurePieceType.NVI, p_i50890_1_, p_i50890_2_, p_i50890_3_, p_i50890_4_, p_i50890_5_, p_i50890_6_);
      }

      public Village(TemplateManager p_i50891_1_, CompoundNBT p_i50891_2_) {
         super(p_i50891_1_, p_i50891_2_, IStructurePieceType.NVI);
      }
   }
}