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

public class BastionRemnantsBridgePools {
   public static void func_236254_a_() {
   }

   static {
      ImmutableList<StructureProcessor> immutablelist = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235411_nu_, 0.4F), AlwaysTrueRuleTest.INSTANCE, Blocks.field_235412_nv_.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235406_np_, 0.01F), AlwaysTrueRuleTest.INSTANCE, Blocks.field_235412_nv_.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235411_nu_, 1.0E-4F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235406_np_, 1.0E-4F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GOLD_BLOCK, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.field_235412_nv_.getDefaultState()), BastionRemnantsMobsPools.field_236260_a_)));
      ImmutableList<StructureProcessor> immutablelist1 = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235411_nu_, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.field_235412_nv_.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235406_np_, 1.0E-4F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GOLD_BLOCK, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.field_235412_nv_.getDefaultState()), BastionRemnantsMobsPools.field_236260_a_)));
      ImmutableList<StructureProcessor> immutablelist2 = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235413_nw_, 0.5F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GOLD_BLOCK, 0.6F), AlwaysTrueRuleTest.INSTANCE, Blocks.field_235412_nv_.getDefaultState()), BastionRemnantsMobsPools.field_236260_a_)));
      ImmutableList<StructureProcessor> immutablelist3 = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235411_nu_, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.field_235412_nv_.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235406_np_, 1.0E-4F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/bridge/start"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/bridge/starting_pieces/entrance_base", immutablelist1), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/bridge/starting_pieces"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/bridge/starting_pieces/entrance", immutablelist2), 1), Pair.of(new SingleJigsawPiece("bastion/bridge/starting_pieces/entrance_face", immutablelist1), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/bridge/bridge_pieces"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/bridge/bridge_pieces/bridge", immutablelist3), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/bridge/legs"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/bridge/legs/leg_0", immutablelist1), 1), Pair.of(new SingleJigsawPiece("bastion/bridge/legs/leg_1", immutablelist1), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/bridge/walls"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/bridge/walls/wall_base_0", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/bridge/walls/wall_base_1", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/bridge/ramparts"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/bridge/ramparts/rampart_0", immutablelist), 1), Pair.of(new SingleJigsawPiece("bastion/bridge/ramparts/rampart_1", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/bridge/rampart_plates"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/bridge/rampart_plates/plate_0", immutablelist), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/bridge/connectors"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/bridge/connectors/back_bridge_top", immutablelist1), 1), Pair.of(new SingleJigsawPiece("bastion/bridge/connectors/back_bridge_bottom", immutablelist1), 1)), JigsawPattern.PlacementBehaviour.RIGID));
   }
}