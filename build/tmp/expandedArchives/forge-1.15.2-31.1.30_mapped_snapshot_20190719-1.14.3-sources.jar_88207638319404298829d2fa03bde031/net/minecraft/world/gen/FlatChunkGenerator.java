package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FillLayerConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.CatSpawner;
import net.minecraft.world.spawner.PhantomSpawner;

public class FlatChunkGenerator extends ChunkGenerator<FlatGenerationSettings> {
   private final Biome biome;
   private final PhantomSpawner phantomSpawner = new PhantomSpawner();
   private final CatSpawner field_222544_g = new CatSpawner();

   public FlatChunkGenerator(IWorld worldIn, BiomeProvider biomeProviderIn, FlatGenerationSettings settingsIn) {
      super(worldIn, biomeProviderIn, settingsIn);
      this.biome = this.func_202099_e();
   }

   private Biome func_202099_e() {
      Biome biome = this.settings.getBiome();
      FlatChunkGenerator.WrapperBiome flatchunkgenerator$wrapperbiome = new FlatChunkGenerator.WrapperBiome(biome.getSurfaceBuilder(), biome.getPrecipitation(), biome.getCategory(), biome.getDepth(), biome.getScale(), biome.getDefaultTemperature(), biome.getDownfall(), biome.getWaterColor(), biome.getWaterFogColor(), biome.getParent());
      Map<String, Map<String, String>> map = this.settings.getWorldFeatures();

      for(String s : map.keySet()) {
         ConfiguredFeature<?, ?>[] configuredfeature = FlatGenerationSettings.STRUCTURES.get(s);
         if (configuredfeature != null) {
            for(ConfiguredFeature<?, ?> configuredfeature1 : configuredfeature) {
               flatchunkgenerator$wrapperbiome.addFeature(FlatGenerationSettings.FEATURE_STAGES.get(configuredfeature1), configuredfeature1);
               ConfiguredFeature<?, ?> configuredfeature2 = ((DecoratedFeatureConfig)configuredfeature1.config).feature;
               if (configuredfeature2.feature instanceof Structure) {
                  Structure<IFeatureConfig> structure = (Structure)configuredfeature2.feature;
                  IFeatureConfig ifeatureconfig = biome.getStructureConfig(structure);
                  IFeatureConfig ifeatureconfig1 = ifeatureconfig != null ? ifeatureconfig : FlatGenerationSettings.FEATURE_CONFIGS.get(configuredfeature1);
                  flatchunkgenerator$wrapperbiome.func_226711_a_(structure.func_225566_b_(ifeatureconfig1));
               }
            }
         }
      }

      boolean flag = (!this.settings.isAllAir() || biome == Biomes.THE_VOID) && map.containsKey("decoration");
      if (flag) {
         List<GenerationStage.Decoration> list = Lists.newArrayList();
         list.add(GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
         list.add(GenerationStage.Decoration.SURFACE_STRUCTURES);

         for(GenerationStage.Decoration generationstage$decoration : GenerationStage.Decoration.values()) {
            if (!list.contains(generationstage$decoration)) {
               for(ConfiguredFeature<?, ?> configuredfeature3 : biome.getFeatures(generationstage$decoration)) {
                  flatchunkgenerator$wrapperbiome.addFeature(generationstage$decoration, configuredfeature3);
               }
            }
         }
      }

      BlockState[] ablockstate = this.settings.getStates();

      for(int i = 0; i < ablockstate.length; ++i) {
         BlockState blockstate = ablockstate[i];
         if (blockstate != null && !Heightmap.Type.MOTION_BLOCKING.func_222684_d().test(blockstate)) {
            this.settings.func_214990_a(i);
            flatchunkgenerator$wrapperbiome.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Feature.FILL_LAYER.func_225566_b_(new FillLayerConfig(i, blockstate)).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
         }
      }

      return flatchunkgenerator$wrapperbiome;
   }

   public void func_225551_a_(WorldGenRegion p_225551_1_, IChunk p_225551_2_) {
   }

   public int getGroundHeight() {
      IChunk ichunk = this.world.getChunk(0, 0);
      return ichunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, 8, 8);
   }

   protected Biome func_225552_a_(BiomeManager p_225552_1_, BlockPos p_225552_2_) {
      return this.biome;
   }

   public void makeBase(IWorld worldIn, IChunk chunkIn) {
      BlockState[] ablockstate = this.settings.getStates();
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
      Heightmap heightmap = chunkIn.func_217303_b(Heightmap.Type.OCEAN_FLOOR_WG);
      Heightmap heightmap1 = chunkIn.func_217303_b(Heightmap.Type.WORLD_SURFACE_WG);

      for(int i = 0; i < ablockstate.length; ++i) {
         BlockState blockstate = ablockstate[i];
         if (blockstate != null) {
            for(int j = 0; j < 16; ++j) {
               for(int k = 0; k < 16; ++k) {
                  chunkIn.setBlockState(blockpos$mutable.setPos(j, i, k), blockstate, false);
                  heightmap.update(j, i, k, blockstate);
                  heightmap1.update(j, i, k, blockstate);
               }
            }
         }
      }

   }

   public int func_222529_a(int p_222529_1_, int p_222529_2_, Heightmap.Type p_222529_3_) {
      BlockState[] ablockstate = this.settings.getStates();

      for(int i = ablockstate.length - 1; i >= 0; --i) {
         BlockState blockstate = ablockstate[i];
         if (blockstate != null && p_222529_3_.func_222684_d().test(blockstate)) {
            return i + 1;
         }
      }

      return 0;
   }

   public void spawnMobs(ServerWorld worldIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs) {
      this.phantomSpawner.tick(worldIn, spawnHostileMobs, spawnPeacefulMobs);
      this.field_222544_g.tick(worldIn, spawnHostileMobs, spawnPeacefulMobs);
   }

   public boolean hasStructure(Biome biomeIn, Structure<? extends IFeatureConfig> structureIn) {
      return this.biome.hasStructure(structureIn);
   }

   @Nullable
   public <C extends IFeatureConfig> C getStructureConfig(Biome biomeIn, Structure<C> structureIn) {
      return this.biome.getStructureConfig(structureIn);
   }

   @Nullable
   public BlockPos findNearestStructure(World worldIn, String name, BlockPos pos, int radius, boolean p_211403_5_) {
      return !this.settings.getWorldFeatures().keySet().contains(name.toLowerCase(Locale.ROOT)) ? null : super.findNearestStructure(worldIn, name, pos, radius, p_211403_5_);
   }

   class WrapperBiome extends Biome {
      protected WrapperBiome(ConfiguredSurfaceBuilder<?> p_i51092_2_, Biome.RainType p_i51092_3_, Biome.Category p_i51092_4_, float p_i51092_5_, float p_i51092_6_, float p_i51092_7_, float p_i51092_8_, int p_i51092_9_, int p_i51092_10_, @Nullable String p_i51092_11_) {
         super((new Biome.Builder()).surfaceBuilder(p_i51092_2_).precipitation(p_i51092_3_).category(p_i51092_4_).depth(p_i51092_5_).scale(p_i51092_6_).temperature(p_i51092_7_).downfall(p_i51092_8_).waterColor(p_i51092_9_).waterFogColor(p_i51092_10_).parent(p_i51092_11_));
      }
   }
}