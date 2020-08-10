package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class VillageConfig implements IFeatureConfig {
   public static final Codec<VillageConfig> field_236533_a_ = RecordCodecBuilder.create((p_236535_0_) -> {
      return p_236535_0_.group(ResourceLocation.field_240908_a_.fieldOf("start_pool").forGetter(VillageConfig::func_236536_b_), Codec.INT.fieldOf("size").forGetter(VillageConfig::func_236534_a_)).apply(p_236535_0_, VillageConfig::new);
   });
   public final ResourceLocation startPool;
   public final int size;

   public VillageConfig(ResourceLocation p_i232010_1_, int p_i232010_2_) {
      this.startPool = p_i232010_1_;
      this.size = p_i232010_2_;
   }

   public int func_236534_a_() {
      return this.size;
   }

   public ResourceLocation func_236536_b_() {
      return this.startPool;
   }
}