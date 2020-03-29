package net.minecraft.world.gen.feature.template;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class RuleStructureProcessor extends StructureProcessor {
   private final ImmutableList<RuleEntry> rules;

   public RuleStructureProcessor(List<RuleEntry> rules) {
      this.rules = ImmutableList.copyOf(rules);
   }

   public RuleStructureProcessor(Dynamic<?> p_i51321_1_) {
      this(p_i51321_1_.get("rules").asList(RuleEntry::deserialize));
   }

   @Nullable
   public Template.BlockInfo process(IWorldReader p_215194_1_, BlockPos p_215194_2_, Template.BlockInfo p_215194_3_, Template.BlockInfo p_215194_4_, PlacementSettings p_215194_5_) {
      Random random = new Random(MathHelper.getPositionRandom(p_215194_4_.pos));
      BlockState blockstate = p_215194_1_.getBlockState(p_215194_4_.pos);

      for(RuleEntry ruleentry : this.rules) {
         if (ruleentry.test(p_215194_4_.state, blockstate, random)) {
            return new Template.BlockInfo(p_215194_4_.pos, ruleentry.getOutputState(), ruleentry.getOutputNbt());
         }
      }

      return p_215194_4_;
   }

   protected IStructureProcessorType getType() {
      return IStructureProcessorType.RULE;
   }

   protected <T> Dynamic<T> serialize0(DynamicOps<T> ops) {
      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("rules"), ops.createList(this.rules.stream().map((p_215200_1_) -> {
         return p_215200_1_.serialize(ops).getValue();
      })))));
   }
}