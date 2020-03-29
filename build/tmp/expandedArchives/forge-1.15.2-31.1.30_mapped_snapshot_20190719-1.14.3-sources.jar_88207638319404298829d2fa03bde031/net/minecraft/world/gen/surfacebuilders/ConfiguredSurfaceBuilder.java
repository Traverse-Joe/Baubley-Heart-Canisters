package net.minecraft.world.gen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

public class ConfiguredSurfaceBuilder<SC extends ISurfaceBuilderConfig> {
   public final SurfaceBuilder<SC> builder;
   public final SC config;

   public ConfiguredSurfaceBuilder(SurfaceBuilder<SC> builder, SC config) {
      this.builder = builder;
      this.config = config;
   }

   public void buildSurface(Random random, IChunk p_215450_2_, Biome p_215450_3_, int x, int z, int p_215450_6_, double noise, BlockState p_215450_9_, BlockState p_215450_10_, int seaLevel, long seed) {
      this.builder.buildSurface(random, p_215450_2_, p_215450_3_, x, z, p_215450_6_, noise, p_215450_9_, p_215450_10_, seaLevel, seed, this.config);
   }

   public void setSeed(long seed) {
      this.builder.setSeed(seed);
   }

   public SC getConfig() {
      return this.config;
   }
}