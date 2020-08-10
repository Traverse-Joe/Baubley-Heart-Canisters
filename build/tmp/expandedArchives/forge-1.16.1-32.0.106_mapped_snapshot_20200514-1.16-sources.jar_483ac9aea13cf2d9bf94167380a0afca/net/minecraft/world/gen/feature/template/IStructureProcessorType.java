package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;

public interface IStructureProcessorType<P extends StructureProcessor> {
   IStructureProcessorType<BlockIgnoreStructureProcessor> BLOCK_IGNORE = func_237139_a_("block_ignore", BlockIgnoreStructureProcessor.field_237073_a_);
   IStructureProcessorType<IntegrityProcessor> BLOCK_ROT = func_237139_a_("block_rot", IntegrityProcessor.field_237077_a_);
   IStructureProcessorType<GravityStructureProcessor> GRAVITY = func_237139_a_("gravity", GravityStructureProcessor.field_237081_a_);
   IStructureProcessorType<JigsawReplacementStructureProcessor> JIGSAW_REPLACEMENT = func_237139_a_("jigsaw_replacement", JigsawReplacementStructureProcessor.field_237085_a_);
   IStructureProcessorType<RuleStructureProcessor> RULE = func_237139_a_("rule", RuleStructureProcessor.field_237125_a_);
   IStructureProcessorType<NopProcessor> NOP = func_237139_a_("nop", NopProcessor.field_237097_a_);
   IStructureProcessorType<BlockMosinessProcessor> field_237135_g_ = func_237139_a_("block_age", BlockMosinessProcessor.field_237062_a_);
   IStructureProcessorType<BlackStoneReplacementProcessor> field_237136_h_ = func_237139_a_("blackstone_replace", BlackStoneReplacementProcessor.field_237057_a_);
   IStructureProcessorType<LavaSubmergingProcessor> field_241534_i_ = func_237139_a_("lava_submerged_block", LavaSubmergingProcessor.field_241531_a_);
   Codec<StructureProcessor> field_237137_i_ = Registry.STRUCTURE_PROCESSOR.dispatch("processor_type", StructureProcessor::getType, IStructureProcessorType::codec);

   Codec<P> codec();

   static <P extends StructureProcessor> IStructureProcessorType<P> func_237139_a_(String p_237139_0_, Codec<P> p_237139_1_) {
      return Registry.register(Registry.STRUCTURE_PROCESSOR, p_237139_0_, () -> {
         return p_237139_1_;
      });
   }
}