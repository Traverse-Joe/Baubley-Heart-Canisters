package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.AlwaysTrueRuleTest;
import net.minecraft.world.gen.feature.template.RandomBlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleEntry;
import net.minecraft.world.gen.feature.template.RuleStructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessor;

public class BastionRemnantsMainPools {
   public static void func_236256_a_() {
   }

   static {
      ImmutableList<StructureProcessor> immutablelist = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235411_nu_, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.field_235412_nv_.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235406_np_, 1.0E-4F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()), BastionRemnantsMobsPools.field_236260_a_)));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/base"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/air_base", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/center_pieces"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/center_pieces/center_0", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/center_pieces/center_1", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/center_pieces/center_2", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/pathways"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/pathways/pathway_0", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/pathways/pathway_wall_0", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/walls/wall_bases"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/walls/wall_base", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/walls/connected_wall", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/stages/stage_0"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_0_0", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_0_1", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_0_2", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_0_3", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/stages/stage_1"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_1_0", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_1_1", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_1_2", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_1_3", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/stages/rot/stage_1"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/stages/rot/stage_1_0", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/stages/stage_2"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_2_0", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_2_1", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/stages/stage_3"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_3_0", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_3_1", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_3_2", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/stages/stage_3_3", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/fillers/stage_0"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/fillers/stage_0", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/edges"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/edges/edge_0", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/wall_units"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/wall_units/unit_0", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/edge_wall_units"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/wall_units/edge_0_large", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/ramparts"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/ramparts/ramparts_0", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/ramparts/ramparts_1", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/units/ramparts/ramparts_2", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/large_ramparts"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/ramparts/ramparts_0", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/units/rampart_plates"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/units/rampart_plates/plate_0", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
   }
}