package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BastionRemnantConfig implements IFeatureConfig {
   public static final Codec<BastionRemnantConfig> field_236545_a_ = VillageConfig.field_236533_a_.listOf().xmap(BastionRemnantConfig::new, (p_236547_0_) -> {
      return p_236547_0_.field_236546_b_;
   });
   private final List<VillageConfig> field_236546_b_;

   public BastionRemnantConfig(Map<String, Integer> p_i232012_1_) {
      this(p_i232012_1_.entrySet().stream().map((p_236548_0_) -> {
         return new VillageConfig(new ResourceLocation(p_236548_0_.getKey()), p_236548_0_.getValue());
      }).collect(Collectors.toList()));
   }

   private BastionRemnantConfig(List<VillageConfig> p_i232011_1_) {
      this.field_236546_b_ = p_i232011_1_;
   }

   public VillageConfig func_236549_a_(Random p_236549_1_) {
      return this.field_236546_b_.get(p_236549_1_.nextInt(this.field_236546_b_.size()));
   }
}