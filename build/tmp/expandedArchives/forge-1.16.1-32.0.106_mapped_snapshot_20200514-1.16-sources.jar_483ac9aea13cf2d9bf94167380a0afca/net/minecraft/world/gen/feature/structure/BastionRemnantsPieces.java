package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableMap;
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

public class BastionRemnantsPieces {
   public static final ImmutableMap<String, Integer> field_236257_a_ = ImmutableMap.<String, Integer>builder().put("bastion/units/base", 60).put("bastion/hoglin_stable/origin", 60).put("bastion/treasure/starters", 60).put("bastion/bridge/start", 60).build();

   public static void func_236258_a_() {
      BastionRemnantsMainPools.func_236256_a_();
      BastionRemnantsStablesPools.func_236255_a_();
      BastionRemnantsTreasurePools.func_236262_a_();
      BastionRemnantsBridgePools.func_236254_a_();
      BastionRemnantsMobsPools.func_236261_a_();
   }

   public static void func_236259_a_(ChunkGenerator p_236259_0_, TemplateManager p_236259_1_, BlockPos p_236259_2_, List<StructurePiece> p_236259_3_, SharedSeedRandom p_236259_4_, BastionRemnantConfig p_236259_5_) {
      func_236258_a_();
      VillageConfig villageconfig = p_236259_5_.func_236549_a_(p_236259_4_);
      JigsawManager.func_236823_a_(villageconfig.startPool, villageconfig.size, BastionRemnantsPieces.Bastion::new, p_236259_0_, p_236259_1_, p_236259_2_, p_236259_3_, p_236259_4_, false, false);
   }

   public static class Bastion extends AbstractVillagePiece {
      public Bastion(TemplateManager p_i231929_1_, JigsawPiece p_i231929_2_, BlockPos p_i231929_3_, int p_i231929_4_, Rotation p_i231929_5_, MutableBoundingBox p_i231929_6_) {
         super(IStructurePieceType.field_236401_af_, p_i231929_1_, p_i231929_2_, p_i231929_3_, p_i231929_4_, p_i231929_5_, p_i231929_6_);
      }

      public Bastion(TemplateManager p_i231930_1_, CompoundNBT p_i231930_2_) {
         super(p_i231930_1_, p_i231930_2_, IStructurePieceType.field_236401_af_);
      }
   }
}