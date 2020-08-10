package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public final class FrozenOceanBiome extends Biome {
   protected static final PerlinNoiseGenerator field_205163_aV = new PerlinNoiseGenerator(new SharedSeedRandom(3456L), ImmutableList.of(-2, -1, 0));

   public FrozenOceanBiome() {
      super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.FROZEN_OCEAN, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG).precipitation(Biome.RainType.SNOW).category(Biome.Category.OCEAN).depth(-1.0F).scale(0.1F).temperature(0.0F).downfall(0.5F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(3750089).func_235248_c_(329011).func_235239_a_(12638463).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).parent((String)null));
      this.func_235063_a_(DefaultBiomeFeatures.field_235175_m_);
      DefaultBiomeFeatures.func_235197_c_(this);
      this.func_235063_a_(DefaultBiomeFeatures.field_235132_D_);
      DefaultBiomeFeatures.addOceanCarvers(this);
      DefaultBiomeFeatures.addLakes(this);
      DefaultBiomeFeatures.addIcebergs(this);
      DefaultBiomeFeatures.addMonsterRooms(this);
      DefaultBiomeFeatures.addBlueIce(this);
      DefaultBiomeFeatures.addStoneVariants(this);
      DefaultBiomeFeatures.addOres(this);
      DefaultBiomeFeatures.addSedimentDisks(this);
      DefaultBiomeFeatures.addScatteredOakTrees(this);
      DefaultBiomeFeatures.addDefaultFlowers(this);
      DefaultBiomeFeatures.addSparseGrass(this);
      DefaultBiomeFeatures.addMushrooms(this);
      DefaultBiomeFeatures.addReedsAndPumpkins(this);
      DefaultBiomeFeatures.addSprings(this);
      DefaultBiomeFeatures.addFreezeTopLayer(this);
      this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 1, 1, 4));
      this.addSpawn(EntityClassification.WATER_AMBIENT, new Biome.SpawnListEntry(EntityType.SALMON, 15, 1, 5));
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.POLAR_BEAR, 1, 1, 2));
      this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 1));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
   }

   /**
    * Gets the current temperature at the given location, based off of the default for this biome, the elevation of the
    * position, and {@linkplain #TEMPERATURE_NOISE} some random perlin noise.
    */
   public float getTemperatureRaw(BlockPos pos) {
      float f = this.getDefaultTemperature();
      double d0 = field_205163_aV.noiseAt((double)pos.getX() * 0.05D, (double)pos.getZ() * 0.05D, false) * 7.0D;
      double d1 = INFO_NOISE.noiseAt((double)pos.getX() * 0.2D, (double)pos.getZ() * 0.2D, false);
      double d2 = d0 + d1;
      if (d2 < 0.3D) {
         double d3 = INFO_NOISE.noiseAt((double)pos.getX() * 0.09D, (double)pos.getZ() * 0.09D, false);
         if (d3 < 0.8D) {
            f = 0.2F;
         }
      }

      if (pos.getY() > 64) {
         float f1 = (float)(TEMPERATURE_NOISE.noiseAt((double)((float)pos.getX() / 8.0F), (double)((float)pos.getZ() / 8.0F), false) * 4.0D);
         return f - (f1 + (float)pos.getY() - 64.0F) * 0.05F / 30.0F;
      } else {
         return f;
      }
   }
}