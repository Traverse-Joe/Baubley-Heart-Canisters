package net.minecraft.world.gen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.codec.RangeCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FillLayerConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlatGenerationSettings {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Codec<FlatGenerationSettings> field_236932_a_ = RecordCodecBuilder.<FlatGenerationSettings>create((p_236938_0_) -> {
      return p_236938_0_.group(DimensionStructuresSettings.field_236190_a_.fieldOf("structures").forGetter(FlatGenerationSettings::func_236943_d_), FlatLayerInfo.field_236929_a_.listOf().fieldOf("layers").forGetter(FlatGenerationSettings::getFlatLayers), Codec.BOOL.fieldOf("lakes").withDefault(false).forGetter((p_241529_0_) -> {
         return p_241529_0_.field_236935_l_;
      }), Codec.BOOL.fieldOf("features").withDefault(false).forGetter((p_241528_0_) -> {
         return p_241528_0_.field_236934_k_;
      }), RangeCodec.func_241291_a_(Registry.BIOME.fieldOf("biome"), Util.func_240982_a_("Unknown biome, defaulting to plains", LOGGER::error), () -> {
         return Biomes.PLAINS;
      }).forGetter((p_236939_0_) -> {
         return p_236939_0_.biomeToUse;
      })).apply(p_236938_0_, FlatGenerationSettings::new);
   }).stable();
   private static final ConfiguredFeature<?, ?> LAKE_WATER = Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4)));
   private static final ConfiguredFeature<?, ?> LAKE_LAVA = Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80)));
   private static final Map<Structure<?>, StructureFeature<?, ?>> STRUCTURES = Util.make(Maps.newHashMap(), (p_236940_0_) -> {
      p_236940_0_.put(Structure.field_236367_c_, DefaultBiomeFeatures.field_235150_b_);
      p_236940_0_.put(Structure.field_236381_q_, DefaultBiomeFeatures.field_235182_t_);
      p_236940_0_.put(Structure.field_236375_k_, DefaultBiomeFeatures.field_235173_k_);
      p_236940_0_.put(Structure.field_236374_j_, DefaultBiomeFeatures.field_235172_j_);
      p_236940_0_.put(Structure.field_236370_f_, DefaultBiomeFeatures.field_235168_f_);
      p_236940_0_.put(Structure.field_236369_e_, DefaultBiomeFeatures.field_235167_e_);
      p_236940_0_.put(Structure.field_236371_g_, DefaultBiomeFeatures.field_235169_g_);
      p_236940_0_.put(Structure.field_236377_m_, DefaultBiomeFeatures.field_235175_m_);
      p_236940_0_.put(Structure.field_236373_i_, DefaultBiomeFeatures.field_235170_h_);
      p_236940_0_.put(Structure.field_236376_l_, DefaultBiomeFeatures.field_235174_l_);
      p_236940_0_.put(Structure.field_236379_o_, DefaultBiomeFeatures.field_235179_q_);
      p_236940_0_.put(Structure.field_236368_d_, DefaultBiomeFeatures.field_235166_d_);
      p_236940_0_.put(Structure.field_236378_n_, DefaultBiomeFeatures.field_235177_o_);
      p_236940_0_.put(Structure.field_236366_b_, DefaultBiomeFeatures.field_235134_a_);
      p_236940_0_.put(Structure.field_236372_h_, DefaultBiomeFeatures.field_235187_y_);
      p_236940_0_.put(Structure.field_236383_s_, DefaultBiomeFeatures.field_235181_s_);
   });
   private final DimensionStructuresSettings field_236933_f_;
   private final List<FlatLayerInfo> flatLayers = Lists.newArrayList();
   private Biome biomeToUse;
   private final BlockState[] states = new BlockState[256];
   private boolean allAir;
   private boolean field_236934_k_ = false;
   private boolean field_236935_l_ = false;

   public FlatGenerationSettings(DimensionStructuresSettings p_i241244_1_, List<FlatLayerInfo> p_i241244_2_, boolean p_i241244_3_, boolean p_i241244_4_, Biome p_i241244_5_) {
      this(p_i241244_1_);
      if (p_i241244_3_) {
         this.func_236941_b_();
      }

      if (p_i241244_4_) {
         this.func_236936_a_();
      }

      this.flatLayers.addAll(p_i241244_2_);
      this.updateLayers();
      this.biomeToUse = p_i241244_5_;
   }

   public FlatGenerationSettings(DimensionStructuresSettings p_i232062_1_) {
      this.field_236933_f_ = p_i232062_1_;
   }

   @OnlyIn(Dist.CLIENT)
   public FlatGenerationSettings func_236937_a_(DimensionStructuresSettings p_236937_1_) {
      return this.func_241527_a_(this.flatLayers, p_236937_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public FlatGenerationSettings func_241527_a_(List<FlatLayerInfo> p_241527_1_, DimensionStructuresSettings p_241527_2_) {
      FlatGenerationSettings flatgenerationsettings = new FlatGenerationSettings(p_241527_2_);

      for(FlatLayerInfo flatlayerinfo : p_241527_1_) {
         flatgenerationsettings.flatLayers.add(new FlatLayerInfo(flatlayerinfo.getLayerCount(), flatlayerinfo.getLayerMaterial().getBlock()));
         flatgenerationsettings.updateLayers();
      }

      flatgenerationsettings.setBiome(this.biomeToUse);
      if (this.field_236934_k_) {
         flatgenerationsettings.func_236936_a_();
      }

      if (this.field_236935_l_) {
         flatgenerationsettings.func_236941_b_();
      }

      return flatgenerationsettings;
   }

   public void func_236936_a_() {
      this.field_236934_k_ = true;
   }

   public void func_236941_b_() {
      this.field_236935_l_ = true;
   }

   public Biome func_236942_c_() {
      Biome biome = this.getBiome();
      Biome biome1 = new Biome((new Biome.Builder()).surfaceBuilder(biome.getSurfaceBuilder()).precipitation(biome.getPrecipitation()).category(biome.getCategory()).depth(biome.getDepth()).scale(biome.getScale()).temperature(biome.getDefaultTemperature()).downfall(biome.getDownfall()).func_235097_a_(biome.func_235089_q_()).parent(biome.getParent())) {
      };
      if (this.field_236935_l_) {
         biome1.addFeature(GenerationStage.Decoration.LAKES, LAKE_WATER);
         biome1.addFeature(GenerationStage.Decoration.LAKES, LAKE_LAVA);
      }

      for(Entry<Structure<?>, StructureSeparationSettings> entry : this.field_236933_f_.func_236195_a_().entrySet()) {
         biome1.func_235063_a_(biome.func_235068_b_(STRUCTURES.get(entry.getKey())));
      }

      boolean flag = (!this.allAir || biome == Biomes.THE_VOID) && this.field_236934_k_;
      if (flag) {
         List<GenerationStage.Decoration> list = Lists.newArrayList();
         list.add(GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
         list.add(GenerationStage.Decoration.SURFACE_STRUCTURES);

         for(GenerationStage.Decoration generationstage$decoration : GenerationStage.Decoration.values()) {
            if (!list.contains(generationstage$decoration)) {
               for(ConfiguredFeature<?, ?> configuredfeature : biome.getFeatures(generationstage$decoration)) {
                  biome1.addFeature(generationstage$decoration, configuredfeature);
               }
            }
         }
      }

      BlockState[] ablockstate = this.getStates();

      for(int i = 0; i < ablockstate.length; ++i) {
         BlockState blockstate = ablockstate[i];
         if (blockstate != null && !Heightmap.Type.MOTION_BLOCKING.getHeightLimitPredicate().test(blockstate)) {
            this.states[i] = null;
            biome1.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Feature.FILL_LAYER.withConfiguration(new FillLayerConfig(i, blockstate)));
         }
      }

      return biome1;
   }

   public DimensionStructuresSettings func_236943_d_() {
      return this.field_236933_f_;
   }

   /**
    * Return the biome used on this preset.
    */
   public Biome getBiome() {
      return this.biomeToUse;
   }

   /**
    * Set the biome used on this preset.
    */
   public void setBiome(Biome biome) {
      this.biomeToUse = biome;
   }

   /**
    * Return the list of layers on this preset.
    */
   public List<FlatLayerInfo> getFlatLayers() {
      return this.flatLayers;
   }

   public BlockState[] getStates() {
      return this.states;
   }

   public void updateLayers() {
      Arrays.fill(this.states, 0, this.states.length, (Object)null);
      int i = 0;

      for(FlatLayerInfo flatlayerinfo : this.flatLayers) {
         flatlayerinfo.setMinY(i);
         i += flatlayerinfo.getLayerCount();
      }

      this.allAir = true;

      for(FlatLayerInfo flatlayerinfo1 : this.flatLayers) {
         for(int j = flatlayerinfo1.getMinY(); j < flatlayerinfo1.getMinY() + flatlayerinfo1.getLayerCount(); ++j) {
            BlockState blockstate = flatlayerinfo1.getLayerMaterial();
            if (!blockstate.isIn(Blocks.AIR)) {
               this.allAir = false;
               this.states[j] = blockstate;
            }
         }
      }

   }

   public static FlatGenerationSettings getDefaultFlatGenerator() {
      DimensionStructuresSettings dimensionstructuressettings = new DimensionStructuresSettings(Optional.of(DimensionStructuresSettings.field_236192_c_), Maps.newHashMap(ImmutableMap.of(Structure.field_236381_q_, DimensionStructuresSettings.field_236191_b_.get(Structure.field_236381_q_))));
      FlatGenerationSettings flatgenerationsettings = new FlatGenerationSettings(dimensionstructuressettings);
      flatgenerationsettings.setBiome(Biomes.PLAINS);
      flatgenerationsettings.getFlatLayers().add(new FlatLayerInfo(1, Blocks.BEDROCK));
      flatgenerationsettings.getFlatLayers().add(new FlatLayerInfo(2, Blocks.DIRT));
      flatgenerationsettings.getFlatLayers().add(new FlatLayerInfo(1, Blocks.GRASS_BLOCK));
      flatgenerationsettings.updateLayers();
      return flatgenerationsettings;
   }
}