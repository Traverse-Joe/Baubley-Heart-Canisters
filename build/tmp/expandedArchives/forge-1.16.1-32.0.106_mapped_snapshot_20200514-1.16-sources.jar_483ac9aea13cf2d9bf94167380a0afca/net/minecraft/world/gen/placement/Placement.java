package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class Placement<DC extends IPlacementConfig> extends net.minecraftforge.registries.ForgeRegistryEntry<Placement<?>> {
   public static final Placement<NoPlacementConfig> NOPE = register("nope", new Passthrough(NoPlacementConfig.field_236555_a_));
   public static final Placement<FrequencyConfig> COUNT_HEIGHTMAP = register("count_heightmap", new AtSurface(FrequencyConfig.field_236971_a_));
   public static final Placement<FrequencyConfig> COUNT_TOP_SOLID = register("count_top_solid", new TopSolid(FrequencyConfig.field_236971_a_));
   public static final Placement<FrequencyConfig> COUNT_HEIGHTMAP_32 = register("count_heightmap_32", new SurfacePlus32(FrequencyConfig.field_236971_a_));
   public static final Placement<FrequencyConfig> COUNT_HEIGHTMAP_DOUBLE = register("count_heightmap_double", new TwiceSurface(FrequencyConfig.field_236971_a_));
   public static final Placement<FrequencyConfig> COUNT_HEIGHT_64 = register("count_height_64", new AtHeight64(FrequencyConfig.field_236971_a_));
   public static final Placement<NoiseDependant> NOISE_HEIGHTMAP_32 = register("noise_heightmap_32", new SurfacePlus32WithNoise(NoiseDependant.field_236550_a_));
   public static final Placement<NoiseDependant> NOISE_HEIGHTMAP_DOUBLE = register("noise_heightmap_double", new TwiceSurfaceWithNoise(NoiseDependant.field_236550_a_));
   public static final Placement<ChanceConfig> CHANCE_HEIGHTMAP = register("chance_heightmap", new AtSurfaceWithChance(ChanceConfig.field_236950_a_));
   public static final Placement<ChanceConfig> CHANCE_HEIGHTMAP_DOUBLE = register("chance_heightmap_double", new TwiceSurfaceWithChance(ChanceConfig.field_236950_a_));
   public static final Placement<ChanceConfig> CHANCE_PASSTHROUGH = register("chance_passthrough", new WithChance(ChanceConfig.field_236950_a_));
   public static final Placement<ChanceConfig> CHANCE_TOP_SOLID_HEIGHTMAP = register("chance_top_solid_heightmap", new TopSolidWithChance(ChanceConfig.field_236950_a_));
   public static final Placement<AtSurfaceWithExtraConfig> COUNT_EXTRA_HEIGHTMAP = register("count_extra_heightmap", new AtSurfaceWithExtra(AtSurfaceWithExtraConfig.field_236973_a_));
   public static final Placement<CountRangeConfig> COUNT_RANGE = register("count_range", new CountRange(CountRangeConfig.field_236485_a_));
   public static final Placement<CountRangeConfig> COUNT_BIASED_RANGE = register("count_biased_range", new HeightBiasedRange(CountRangeConfig.field_236485_a_));
   public static final Placement<CountRangeConfig> COUNT_VERY_BIASED_RANGE = register("count_very_biased_range", new HeightVeryBiasedRange(CountRangeConfig.field_236485_a_));
   public static final Placement<CountRangeConfig> RANDOM_COUNT_RANGE = register("random_count_range", new RandomCountWithRange(CountRangeConfig.field_236485_a_));
   public static final Placement<ChanceRangeConfig> CHANCE_RANGE = register("chance_range", new ChanceRange(ChanceRangeConfig.field_236459_a_));
   public static final Placement<HeightWithChanceConfig> COUNT_CHANCE_HEIGHTMAP = register("count_chance_heightmap", new AtSurfaceWithChanceMultiple(HeightWithChanceConfig.field_236967_a_));
   public static final Placement<HeightWithChanceConfig> COUNT_CHANCE_HEIGHTMAP_DOUBLE = register("count_chance_heightmap_double", new TwiceSurfaceWithChanceMultiple(HeightWithChanceConfig.field_236967_a_));
   public static final Placement<DepthAverageConfig> COUNT_DEPTH_AVERAGE = register("count_depth_average", new DepthAverage(DepthAverageConfig.field_236955_a_));
   public static final Placement<NoPlacementConfig> TOP_SOLID_HEIGHTMAP = register("top_solid_heightmap", new TopSolidOnce(NoPlacementConfig.field_236555_a_));
   public static final Placement<TopSolidRangeConfig> TOP_SOLID_HEIGHTMAP_RANGE = register("top_solid_heightmap_range", new TopSolidRange(TopSolidRangeConfig.field_236985_a_));
   public static final Placement<TopSolidWithNoiseConfig> TOP_SOLID_HEIGHTMAP_NOISE_BIASED = register("top_solid_heightmap_noise_biased", new TopSolidWithNoise(TopSolidWithNoiseConfig.field_236978_a_));
   public static final Placement<CaveEdgeConfig> CARVING_MASK = register("carving_mask", new CaveEdge(CaveEdgeConfig.field_236946_a_));
   public static final Placement<FrequencyConfig> FOREST_ROCK = register("forest_rock", new AtSurfaceRandomCount(FrequencyConfig.field_236971_a_));
   public static final Placement<FrequencyConfig> field_236960_A_ = register("fire", new FirePlacement(FrequencyConfig.field_236971_a_));
   public static final Placement<FrequencyConfig> MAGMA = register("magma", new NetherMagma(FrequencyConfig.field_236971_a_));
   public static final Placement<NoPlacementConfig> EMERALD_ORE = register("emerald_ore", new Height4To32(NoPlacementConfig.field_236555_a_));
   public static final Placement<ChanceConfig> LAVA_LAKE = register("lava_lake", new LakeLava(ChanceConfig.field_236950_a_));
   public static final Placement<ChanceConfig> WATER_LAKE = register("water_lake", new LakeWater(ChanceConfig.field_236950_a_));
   public static final Placement<ChanceConfig> DUNGEONS = register("dungeons", new DungeonRoom(ChanceConfig.field_236950_a_));
   public static final Placement<NoPlacementConfig> DARK_OAK_TREE = register("dark_oak_tree", new DarkOakTreePlacement(NoPlacementConfig.field_236555_a_));
   public static final Placement<ChanceConfig> ICEBERG = register("iceberg", new IcebergPlacement(ChanceConfig.field_236950_a_));
   public static final Placement<FrequencyConfig> LIGHT_GEM_CHANCE = register("light_gem_chance", new NetherGlowstone(FrequencyConfig.field_236971_a_));
   public static final Placement<NoPlacementConfig> END_ISLAND = register("end_island", new EndIsland(NoPlacementConfig.field_236555_a_));
   public static final Placement<NoPlacementConfig> CHORUS_PLANT = register("chorus_plant", new ChorusPlant(NoPlacementConfig.field_236555_a_));
   public static final Placement<NoPlacementConfig> END_GATEWAY = register("end_gateway", new EndGateway(NoPlacementConfig.field_236555_a_));
   private final Codec<ConfiguredPlacement<DC>> field_236961_M_;

   private static <T extends IPlacementConfig, G extends Placement<T>> G register(String key, G p_214999_1_) {
      return Registry.register(Registry.DECORATOR, key, p_214999_1_);
   }

   public Placement(Codec<DC> p_i232086_1_) {
      this.field_236961_M_ = p_i232086_1_.fieldOf("config").xmap((p_236966_1_) -> {
         return new ConfiguredPlacement<>(this, p_236966_1_);
      }, (p_236965_0_) -> {
         return p_236965_0_.config;
      }).codec();
   }

   public ConfiguredPlacement<DC> configure(DC p_227446_1_) {
      return new ConfiguredPlacement<>(this, p_227446_1_);
   }

   public Codec<ConfiguredPlacement<DC>> func_236962_a_() {
      return this.field_236961_M_;
   }

   protected <FC extends IFeatureConfig, F extends Feature<FC>> boolean func_236963_a_(ISeedReader p_236963_1_, StructureManager p_236963_2_, ChunkGenerator p_236963_3_, Random p_236963_4_, BlockPos p_236963_5_, DC p_236963_6_, ConfiguredFeature<FC, F> p_236963_7_) {
      MutableBoolean mutableboolean = new MutableBoolean();
      this.getPositions(p_236963_1_, p_236963_3_, p_236963_4_, p_236963_6_, p_236963_5_).forEach((p_236964_6_) -> {
         if (p_236963_7_.func_236265_a_(p_236963_1_, p_236963_2_, p_236963_3_, p_236963_4_, p_236964_6_)) {
            mutableboolean.setTrue();
         }

      });
      return mutableboolean.isTrue();
   }

   public abstract Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator generatorIn, Random random, DC configIn, BlockPos pos);

   public String toString() {
      return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
   }
}