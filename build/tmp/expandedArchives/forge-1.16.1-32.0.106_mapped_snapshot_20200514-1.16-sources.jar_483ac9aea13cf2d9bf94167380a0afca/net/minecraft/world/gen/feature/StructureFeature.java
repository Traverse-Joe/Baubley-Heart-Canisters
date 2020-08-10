package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class StructureFeature<FC extends IFeatureConfig, F extends Structure<FC>> {
   public static final Codec<StructureFeature<?, ?>> field_236267_a_ = Registry.STRUCTURE_FEATURE.dispatch("name", (p_236271_0_) -> {
      return p_236271_0_.field_236268_b_;
   }, Structure::func_236398_h_);
   public final F field_236268_b_;
   public final FC field_236269_c_;

   public StructureFeature(F p_i231937_1_, FC p_i231937_2_) {
      this.field_236268_b_ = p_i231937_1_;
      this.field_236269_c_ = p_i231937_2_;
   }

   public StructureStart<?> func_236270_a_(ChunkGenerator p_236270_1_, BiomeProvider p_236270_2_, TemplateManager p_236270_3_, long p_236270_4_, ChunkPos p_236270_6_, Biome p_236270_7_, int p_236270_8_, StructureSeparationSettings p_236270_9_) {
      return this.field_236268_b_.func_236389_a_(p_236270_1_, p_236270_2_, p_236270_3_, p_236270_4_, p_236270_6_, p_236270_7_, p_236270_8_, new SharedSeedRandom(), p_236270_9_, this.field_236269_c_);
   }
}