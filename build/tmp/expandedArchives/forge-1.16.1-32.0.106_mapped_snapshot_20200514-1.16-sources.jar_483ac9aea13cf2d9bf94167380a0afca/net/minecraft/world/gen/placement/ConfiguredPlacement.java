package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class ConfiguredPlacement<DC extends IPlacementConfig> {
   public static final Codec<ConfiguredPlacement<?>> field_236952_a_ = Registry.DECORATOR.dispatch("name", (p_236954_0_) -> {
      return p_236954_0_.decorator;
   }, Placement::func_236962_a_);
   public final Placement<DC> decorator;
   public final DC config;

   public ConfiguredPlacement(Placement<DC> decorator, DC config) {
      this.decorator = decorator;
      this.config = config;
   }

   public <FC extends IFeatureConfig, F extends Feature<FC>> boolean func_236953_a_(ISeedReader p_236953_1_, StructureManager p_236953_2_, ChunkGenerator p_236953_3_, Random p_236953_4_, BlockPos p_236953_5_, ConfiguredFeature<FC, F> p_236953_6_) {
      return this.decorator.func_236963_a_(p_236953_1_, p_236953_2_, p_236953_3_, p_236953_4_, p_236953_5_, this.config, p_236953_6_);
   }
}