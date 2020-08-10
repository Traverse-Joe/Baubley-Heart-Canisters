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

public class BastionRemnantsMobsPools {
   public static final RuleEntry field_236260_a_ = new RuleEntry(new RandomBlockMatchRuleTest(Blocks.field_235406_np_, 0.01F), AlwaysTrueRuleTest.INSTANCE, Blocks.field_235387_nA_.getDefaultState());

   public static void func_236261_a_() {
   }

   static {
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/mobs/piglin"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/mobs/melee_piglin"), 1), Pair.of(new SingleJigsawPiece("bastion/mobs/sword_piglin"), 4), Pair.of(new SingleJigsawPiece("bastion/mobs/crossbow_piglin"), 4), Pair.of(new SingleJigsawPiece("bastion/mobs/empty"), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/mobs/hoglin"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/mobs/hoglin"), 2), Pair.of(new SingleJigsawPiece("bastion/mobs/empty"), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/blocks/gold"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/blocks/air"), 3), Pair.of(new SingleJigsawPiece("bastion/blocks/gold"), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("bastion/mobs/piglin_melee"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("bastion/mobs/melee_piglin_always"), 1), Pair.of(new SingleJigsawPiece("bastion/mobs/melee_piglin"), 5), Pair.of(new SingleJigsawPiece("bastion/mobs/sword_piglin"), 1)), JigsawPattern.PlacementBehaviour.RIGID));
   }
}