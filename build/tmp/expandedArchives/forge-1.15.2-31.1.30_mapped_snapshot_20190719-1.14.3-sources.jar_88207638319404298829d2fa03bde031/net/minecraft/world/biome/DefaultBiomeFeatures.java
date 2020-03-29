package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.AxisRotatingBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.ForestFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.PlainFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.feature.structure.BuriedTreasureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.PineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.CocoaTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;

public class DefaultBiomeFeatures {
   private static final BlockState field_226769_ad_ = Blocks.GRASS.getDefaultState();
   private static final BlockState field_226770_ae_ = Blocks.FERN.getDefaultState();
   private static final BlockState field_226771_af_ = Blocks.PODZOL.getDefaultState();
   private static final BlockState field_226772_ag_ = Blocks.OAK_LOG.getDefaultState();
   private static final BlockState field_226773_ah_ = Blocks.OAK_LEAVES.getDefaultState();
   private static final BlockState field_226774_ai_ = Blocks.JUNGLE_LOG.getDefaultState();
   private static final BlockState field_226775_aj_ = Blocks.JUNGLE_LEAVES.getDefaultState();
   private static final BlockState field_226776_ak_ = Blocks.SPRUCE_LOG.getDefaultState();
   private static final BlockState field_226777_al_ = Blocks.SPRUCE_LEAVES.getDefaultState();
   private static final BlockState field_226778_am_ = Blocks.ACACIA_LOG.getDefaultState();
   private static final BlockState field_226779_an_ = Blocks.ACACIA_LEAVES.getDefaultState();
   private static final BlockState field_226780_ao_ = Blocks.BIRCH_LOG.getDefaultState();
   private static final BlockState field_226781_ap_ = Blocks.BIRCH_LEAVES.getDefaultState();
   private static final BlockState field_226782_aq_ = Blocks.DARK_OAK_LOG.getDefaultState();
   private static final BlockState field_226783_ar_ = Blocks.DARK_OAK_LEAVES.getDefaultState();
   private static final BlockState field_226784_as_ = Blocks.WATER.getDefaultState();
   private static final BlockState field_226785_at_ = Blocks.LAVA.getDefaultState();
   private static final BlockState field_226786_au_ = Blocks.DIRT.getDefaultState();
   private static final BlockState field_226787_av_ = Blocks.GRAVEL.getDefaultState();
   private static final BlockState field_226788_aw_ = Blocks.GRANITE.getDefaultState();
   private static final BlockState field_226789_ax_ = Blocks.DIORITE.getDefaultState();
   private static final BlockState field_226790_ay_ = Blocks.ANDESITE.getDefaultState();
   private static final BlockState field_226791_az_ = Blocks.COAL_ORE.getDefaultState();
   private static final BlockState field_226740_aA_ = Blocks.IRON_ORE.getDefaultState();
   private static final BlockState field_226741_aB_ = Blocks.GOLD_ORE.getDefaultState();
   private static final BlockState field_226742_aC_ = Blocks.REDSTONE_ORE.getDefaultState();
   private static final BlockState field_226743_aD_ = Blocks.DIAMOND_ORE.getDefaultState();
   private static final BlockState field_226744_aE_ = Blocks.LAPIS_ORE.getDefaultState();
   private static final BlockState field_226745_aF_ = Blocks.STONE.getDefaultState();
   private static final BlockState field_226746_aG_ = Blocks.EMERALD_ORE.getDefaultState();
   private static final BlockState field_226747_aH_ = Blocks.INFESTED_STONE.getDefaultState();
   private static final BlockState field_226748_aI_ = Blocks.SAND.getDefaultState();
   private static final BlockState field_226749_aJ_ = Blocks.CLAY.getDefaultState();
   private static final BlockState field_226750_aK_ = Blocks.GRASS_BLOCK.getDefaultState();
   private static final BlockState field_226751_aL_ = Blocks.MOSSY_COBBLESTONE.getDefaultState();
   private static final BlockState field_226752_aM_ = Blocks.LARGE_FERN.getDefaultState();
   private static final BlockState field_226753_aN_ = Blocks.TALL_GRASS.getDefaultState();
   private static final BlockState field_226754_aO_ = Blocks.LILAC.getDefaultState();
   private static final BlockState field_226755_aP_ = Blocks.ROSE_BUSH.getDefaultState();
   private static final BlockState field_226756_aQ_ = Blocks.PEONY.getDefaultState();
   private static final BlockState field_226757_aR_ = Blocks.BROWN_MUSHROOM.getDefaultState();
   private static final BlockState field_226758_aS_ = Blocks.RED_MUSHROOM.getDefaultState();
   private static final BlockState field_226759_aT_ = Blocks.SEAGRASS.getDefaultState();
   private static final BlockState field_226760_aU_ = Blocks.PACKED_ICE.getDefaultState();
   private static final BlockState field_226761_aV_ = Blocks.BLUE_ICE.getDefaultState();
   private static final BlockState field_226762_aW_ = Blocks.LILY_OF_THE_VALLEY.getDefaultState();
   private static final BlockState field_226763_aX_ = Blocks.BLUE_ORCHID.getDefaultState();
   private static final BlockState field_226764_aY_ = Blocks.POPPY.getDefaultState();
   private static final BlockState field_226765_aZ_ = Blocks.DANDELION.getDefaultState();
   private static final BlockState field_226793_ba_ = Blocks.DEAD_BUSH.getDefaultState();
   private static final BlockState field_226794_bb_ = Blocks.MELON.getDefaultState();
   private static final BlockState field_226795_bc_ = Blocks.PUMPKIN.getDefaultState();
   private static final BlockState field_226796_bd_ = Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, Integer.valueOf(3));
   private static final BlockState field_226797_be_ = Blocks.FIRE.getDefaultState();
   private static final BlockState field_226798_bf_ = Blocks.NETHERRACK.getDefaultState();
   private static final BlockState field_226799_bg_ = Blocks.LILY_PAD.getDefaultState();
   private static final BlockState field_226800_bh_ = Blocks.SNOW.getDefaultState();
   private static final BlockState field_226801_bi_ = Blocks.JACK_O_LANTERN.getDefaultState();
   private static final BlockState field_226802_bj_ = Blocks.SUNFLOWER.getDefaultState();
   private static final BlockState field_226803_bk_ = Blocks.CACTUS.getDefaultState();
   private static final BlockState field_226804_bl_ = Blocks.SUGAR_CANE.getDefaultState();
   private static final BlockState field_226805_bm_ = Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
   private static final BlockState field_226806_bn_ = Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.UP, Boolean.valueOf(true)).with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
   private static final BlockState field_226807_bo_ = Blocks.MUSHROOM_STEM.getDefaultState().with(HugeMushroomBlock.UP, Boolean.valueOf(false)).with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
   public static final TreeFeatureConfig field_226739_a_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(2, 0))).func_225569_d_(4).func_227354_b_(2).func_227360_i_(3).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226792_b_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226774_ai_), new SimpleBlockStateProvider(field_226775_aj_), new BlobFoliagePlacer(2, 0))).func_225569_d_(4).func_227354_b_(8).func_227360_i_(3).func_227353_a_(ImmutableList.of(new CocoaTreeDecorator(0.2F), new TrunkVineTreeDecorator(), new LeaveVineTreeDecorator())).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable)Blocks.JUNGLE_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226808_c_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226774_ai_), new SimpleBlockStateProvider(field_226775_aj_), new BlobFoliagePlacer(2, 0))).func_225569_d_(4).func_227354_b_(8).func_227360_i_(3).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable)Blocks.JUNGLE_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226809_d_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226776_ak_), new SimpleBlockStateProvider(field_226777_al_), new PineFoliagePlacer(1, 0))).func_225569_d_(7).func_227354_b_(4).func_227358_g_(1).func_227360_i_(3).func_227361_j_(1).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226810_e_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226776_ak_), new SimpleBlockStateProvider(field_226777_al_), new SpruceFoliagePlacer(2, 1))).func_225569_d_(6).func_227354_b_(3).func_227356_e_(1).func_227357_f_(1).func_227359_h_(2).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226811_f_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226778_am_), new SimpleBlockStateProvider(field_226779_an_), new AcaciaFoliagePlacer(2, 0))).func_225569_d_(5).func_227354_b_(2).func_227355_c_(2).func_227356_e_(0).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable)Blocks.ACACIA_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226812_g_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226780_ao_), new SimpleBlockStateProvider(field_226781_ap_), new BlobFoliagePlacer(2, 0))).func_225569_d_(5).func_227354_b_(2).func_227360_i_(3).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_230129_h_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226780_ao_), new SimpleBlockStateProvider(field_226781_ap_), new BlobFoliagePlacer(2, 0))).func_225569_d_(5).func_227354_b_(2).func_227360_i_(3).func_227352_a_().func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_230130_i_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226780_ao_), new SimpleBlockStateProvider(field_226781_ap_), new BlobFoliagePlacer(2, 0))).func_225569_d_(5).func_227354_b_(2).func_227355_c_(6).func_227360_i_(3).func_227352_a_().func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226814_i_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(3, 0))).func_225569_d_(5).func_227354_b_(3).func_227360_i_(3).func_227362_k_(1).func_227353_a_(ImmutableList.of(new LeaveVineTreeDecorator())).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226815_j_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(0, 0))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226816_k_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(2, 0))).func_225569_d_(4).func_227354_b_(2).func_227360_i_(3).func_227352_a_().func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_230131_m_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(0, 0))).func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_226817_l_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(0, 0))).func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_230132_o_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(2, 0))).func_225569_d_(4).func_227354_b_(2).func_227360_i_(3).func_227352_a_().func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_230133_p_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(2, 0))).func_225569_d_(4).func_227354_b_(2).func_227360_i_(3).func_227352_a_().func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.02F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_230134_q_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226772_ag_), new SimpleBlockStateProvider(field_226773_ah_), new BlobFoliagePlacer(0, 0))).func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.02F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_230135_r_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226780_ao_), new SimpleBlockStateProvider(field_226781_ap_), new BlobFoliagePlacer(2, 0))).func_225569_d_(5).func_227354_b_(2).func_227360_i_(3).func_227352_a_().func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.02F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING).func_225568_b_();
   public static final TreeFeatureConfig field_230136_s_ = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226780_ao_), new SimpleBlockStateProvider(field_226781_ap_), new BlobFoliagePlacer(2, 0))).func_225569_d_(5).func_227354_b_(2).func_227360_i_(3).func_227352_a_().func_227353_a_(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING).func_225568_b_();
   public static final BaseTreeFeatureConfig field_226821_p_ = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226774_ai_), new SimpleBlockStateProvider(field_226773_ah_))).func_225569_d_(4).setSapling((net.minecraftforge.common.IPlantable)Blocks.JUNGLE_SAPLING).func_225568_b_();
   public static final HugeTreeFeatureConfig field_226822_q_ = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226782_aq_), new SimpleBlockStateProvider(field_226783_ar_))).func_225569_d_(6).setSapling((net.minecraftforge.common.IPlantable)Blocks.DARK_OAK_SAPLING).func_225568_b_();
   public static final HugeTreeFeatureConfig field_226823_r_ = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226776_ak_), new SimpleBlockStateProvider(field_226777_al_))).func_225569_d_(13).func_227283_b_(15).func_227284_c_(13).func_227282_a_(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(field_226771_af_)))).setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING).func_225568_b_();
   public static final HugeTreeFeatureConfig field_226824_s_ = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226776_ak_), new SimpleBlockStateProvider(field_226777_al_))).func_225569_d_(13).func_227283_b_(15).func_227284_c_(3).func_227282_a_(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(field_226771_af_)))).setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING).func_225568_b_();
   public static final HugeTreeFeatureConfig field_226825_t_ = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(field_226774_ai_), new SimpleBlockStateProvider(field_226775_aj_))).func_225569_d_(10).func_227283_b_(20).func_227282_a_(ImmutableList.of(new TrunkVineTreeDecorator(), new LeaveVineTreeDecorator())).setSapling((net.minecraftforge.common.IPlantable)Blocks.JUNGLE_SAPLING).func_225568_b_();
   public static final BlockClusterFeatureConfig field_226826_u_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226769_ad_), new SimpleBlockPlacer())).func_227315_a_(32).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226827_v_ = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).func_227407_a_(field_226769_ad_, 1).func_227407_a_(field_226770_ae_, 4), new SimpleBlockPlacer())).func_227315_a_(32).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226828_w_ = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).func_227407_a_(field_226769_ad_, 3).func_227407_a_(field_226770_ae_, 1), new SimpleBlockPlacer())).func_227319_b_(ImmutableSet.of(field_226771_af_)).func_227315_a_(32).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226829_x_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226762_aW_), new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226830_y_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226763_aX_), new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226831_z_ = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).func_227407_a_(field_226764_aY_, 2).func_227407_a_(field_226765_aZ_, 1), new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226713_A_ = (new BlockClusterFeatureConfig.Builder(new PlainFlowerBlockStateProvider(), new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226714_B_ = (new BlockClusterFeatureConfig.Builder(new ForestFlowerBlockStateProvider(), new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226715_C_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226793_ba_), new SimpleBlockPlacer())).func_227315_a_(4).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226716_D_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226794_bb_), new SimpleBlockPlacer())).func_227315_a_(64).func_227316_a_(ImmutableSet.of(field_226750_aK_.getBlock())).func_227314_a_().func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226717_E_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226795_bc_), new SimpleBlockPlacer())).func_227315_a_(64).func_227316_a_(ImmutableSet.of(field_226750_aK_.getBlock())).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226718_F_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226796_bd_), new SimpleBlockPlacer())).func_227315_a_(64).func_227316_a_(ImmutableSet.of(field_226750_aK_.getBlock())).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226719_G_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226797_be_), new SimpleBlockPlacer())).func_227315_a_(64).func_227316_a_(ImmutableSet.of(field_226798_bf_.getBlock())).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226720_H_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226799_bg_), new SimpleBlockPlacer())).func_227315_a_(10).func_227322_d_();
   public static final BlockClusterFeatureConfig field_226721_I_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226758_aS_), new SimpleBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226722_J_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226757_aR_), new SimpleBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226723_K_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226754_aO_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226724_L_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226755_aP_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226725_M_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226756_aQ_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226726_N_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226802_bj_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226727_O_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226753_aN_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226728_P_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226752_aM_), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226729_Q_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226803_bk_), new ColumnBlockPlacer(1, 2))).func_227315_a_(10).func_227317_b_().func_227322_d_();
   public static final BlockClusterFeatureConfig field_226730_R_ = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(field_226804_bl_), new ColumnBlockPlacer(2, 2))).func_227315_a_(20).func_227318_b_(4).func_227321_c_(0).func_227323_d_(4).func_227317_b_().func_227320_c_().func_227322_d_();
   public static final BlockStateProvidingFeatureConfig field_226731_S_ = new BlockStateProvidingFeatureConfig(new AxisRotatingBlockStateProvider(Blocks.HAY_BLOCK));
   public static final BlockStateProvidingFeatureConfig field_226732_T_ = new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(field_226800_bh_));
   public static final BlockStateProvidingFeatureConfig field_226733_U_ = new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(field_226794_bb_));
   public static final BlockStateProvidingFeatureConfig field_226734_V_ = new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).func_227407_a_(field_226795_bc_, 19).func_227407_a_(field_226801_bi_, 1));
   public static final BlockStateProvidingFeatureConfig field_226735_W_ = new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).func_227407_a_(field_226761_aV_, 1).func_227407_a_(field_226760_aU_, 5));
   public static final LiquidsConfig field_226736_X_ = new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE));
   public static final LiquidsConfig field_226737_Y_ = new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE));
   public static final LiquidsConfig field_226738_Z_ = new LiquidsConfig(Fluids.LAVA.getDefaultState(), false, 4, 1, ImmutableSet.of(Blocks.NETHERRACK));
   public static final LiquidsConfig field_226766_aa_ = new LiquidsConfig(Fluids.LAVA.getDefaultState(), false, 5, 0, ImmutableSet.of(Blocks.NETHERRACK));
   public static final BigMushroomFeatureConfig field_226767_ab_ = new BigMushroomFeatureConfig(new SimpleBlockStateProvider(field_226805_bm_), new SimpleBlockStateProvider(field_226807_bo_), 2);
   public static final BigMushroomFeatureConfig field_226768_ac_ = new BigMushroomFeatureConfig(new SimpleBlockStateProvider(field_226806_bn_), new SimpleBlockStateProvider(field_226807_bo_), 3);

   public static void addCarvers(Biome biomeIn) {
      biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.14285715F)));
      biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.02F)));
   }

   public static void addOceanCarvers(Biome biomeIn) {
      biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.06666667F)));
      biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.02F)));
      biomeIn.addCarver(GenerationStage.Carving.LIQUID, Biome.createCarver(WorldCarver.UNDERWATER_CANYON, new ProbabilityConfig(0.02F)));
      biomeIn.addCarver(GenerationStage.Carving.LIQUID, Biome.createCarver(WorldCarver.UNDERWATER_CAVE, new ProbabilityConfig(0.06666667F)));
   }

   public static void addStructures(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.MINESHAFT.func_225566_b_(new MineshaftConfig((double)0.004F, MineshaftStructure.Type.NORMAL)).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.PILLAGER_OUTPOST.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.STRONGHOLD.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.SWAMP_HUT.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.DESERT_PYRAMID.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.JUNGLE_TEMPLE.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.IGLOO.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.SHIPWRECK.func_225566_b_(new ShipwreckConfig(false)).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.OCEAN_MONUMENT.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.WOODLAND_MANSION.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.OCEAN_RUIN.func_225566_b_(new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F)).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.BURIED_TREASURE.func_225566_b_(new BuriedTreasureConfig(0.01F)).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.VILLAGE.func_225566_b_(new VillageConfig("village/plains/town_centers", 6)).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
   }

   public static void addLakes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.func_225566_b_(new BlockStateFeatureConfig(field_226784_as_)).func_227228_a_(Placement.WATER_LAKE.func_227446_a_(new ChanceConfig(4))));
      biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.func_225566_b_(new BlockStateFeatureConfig(field_226785_at_)).func_227228_a_(Placement.LAVA_LAKE.func_227446_a_(new ChanceConfig(80))));
   }

   public static void addDesertLakes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.func_225566_b_(new BlockStateFeatureConfig(field_226785_at_)).func_227228_a_(Placement.LAVA_LAKE.func_227446_a_(new ChanceConfig(80))));
   }

   public static void addMonsterRooms(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.MONSTER_ROOM.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.DUNGEONS.func_227446_a_(new ChanceConfig(8))));
   }

   public static void addStoneVariants(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226786_au_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 256))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226787_av_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(8, 0, 0, 256))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226788_aw_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 80))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226789_ax_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 80))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226790_ay_, 33)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(10, 0, 0, 80))));
   }

   public static void addOres(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226791_az_, 17)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(20, 0, 0, 128))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226740_aA_, 9)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(20, 0, 0, 64))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226741_aB_, 9)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(2, 0, 0, 32))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226742_aC_, 8)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(8, 0, 0, 16))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226743_aD_, 8)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(1, 0, 0, 16))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226744_aE_, 7)).func_227228_a_(Placement.COUNT_DEPTH_AVERAGE.func_227446_a_(new DepthAverageConfig(1, 16, 16))));
   }

   public static void addExtraGoldOre(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226741_aB_, 9)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(20, 32, 32, 80))));
   }

   public static void addExtraEmeraldOre(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.EMERALD_ORE.func_225566_b_(new ReplaceBlockConfig(field_226745_aF_, field_226746_aG_)).func_227228_a_(Placement.EMERALD_ORE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
   }

   public static void addInfestedStone(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, field_226747_aH_, 9)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(7, 0, 0, 64))));
   }

   public static void addSedimentDisks(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(field_226748_aI_, 7, 2, Lists.newArrayList(field_226786_au_, field_226750_aK_))).func_227228_a_(Placement.COUNT_TOP_SOLID.func_227446_a_(new FrequencyConfig(3))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(field_226749_aJ_, 4, 1, Lists.newArrayList(field_226786_au_, field_226749_aJ_))).func_227228_a_(Placement.COUNT_TOP_SOLID.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(field_226787_av_, 6, 2, Lists.newArrayList(field_226786_au_, field_226750_aK_))).func_227228_a_(Placement.COUNT_TOP_SOLID.func_227446_a_(new FrequencyConfig(1))));
   }

   public static void addSwampClayDisks(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.func_225566_b_(new SphereReplaceConfig(field_226749_aJ_, 4, 1, Lists.newArrayList(field_226786_au_, field_226749_aJ_))).func_227228_a_(Placement.COUNT_TOP_SOLID.func_227446_a_(new FrequencyConfig(1))));
   }

   public static void addTaigaRocks(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.FOREST_ROCK.func_225566_b_(new BlockBlobConfig(field_226751_aL_, 0)).func_227228_a_(Placement.FOREST_ROCK.func_227446_a_(new FrequencyConfig(3))));
   }

   public static void addTaigaLargeFerns(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226728_P_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(7))));
   }

   public static void addSparseBerryBushes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226718_F_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(12))));
   }

   public static void addBerryBushes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226718_F_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
   }

   public static void addBamboo(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.BAMBOO.func_225566_b_(new ProbabilityConfig(0.0F)).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(16))));
   }

   public static void addBambooJungleVegetation(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.BAMBOO.func_225566_b_(new ProbabilityConfig(0.2F)).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.func_227446_a_(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D, Heightmap.Type.WORLD_SURFACE_WG))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.func_225566_b_(field_226815_j_).func_227227_a_(0.05F), Feature.JUNGLE_GROUND_BUSH.func_225566_b_(field_226821_p_).func_227227_a_(0.15F), Feature.MEGA_JUNGLE_TREE.func_225566_b_(field_226825_t_).func_227227_a_(0.7F)), Feature.field_227248_z_.func_225566_b_(field_226828_w_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(30, 0.1F, 1))));
   }

   public static void addTaigaConifers(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.NORMAL_TREE.func_225566_b_(field_226809_d_).func_227227_a_(0.33333334F)), Feature.NORMAL_TREE.func_225566_b_(field_226810_e_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void func_222296_u(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.func_225566_b_(field_226815_j_).func_227227_a_(0.1F)), Feature.NORMAL_TREE.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
   }

   public static void addBirchTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.func_225566_b_(field_230129_h_).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void addForestTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.NORMAL_TREE.func_225566_b_(field_230129_h_).func_227227_a_(0.2F), Feature.FANCY_TREE.func_225566_b_(field_230131_m_).func_227227_a_(0.1F)), Feature.NORMAL_TREE.func_225566_b_(field_230132_o_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void func_222336_x(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.NORMAL_TREE.func_225566_b_(field_230130_i_).func_227227_a_(0.5F)), Feature.NORMAL_TREE.func_225566_b_(field_230129_h_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void addSavannaTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_227246_s_.func_225566_b_(field_226811_f_).func_227227_a_(0.8F)), Feature.NORMAL_TREE.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));
   }

   public static void addShatteredSavannaTrees(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_227246_s_.func_225566_b_(field_226811_f_).func_227227_a_(0.8F)), Feature.NORMAL_TREE.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
   }

   public static void func_222343_A(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.NORMAL_TREE.func_225566_b_(field_226810_e_).func_227227_a_(0.666F), Feature.FANCY_TREE.func_225566_b_(field_226815_j_).func_227227_a_(0.1F)), Feature.NORMAL_TREE.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
   }

   public static void func_222304_B(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.NORMAL_TREE.func_225566_b_(field_226810_e_).func_227227_a_(0.666F), Feature.FANCY_TREE.func_225566_b_(field_226815_j_).func_227227_a_(0.1F)), Feature.NORMAL_TREE.func_225566_b_(field_226739_a_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(3, 0.1F, 1))));
   }

   public static void func_222323_C(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.func_225566_b_(field_226815_j_).func_227227_a_(0.1F), Feature.JUNGLE_GROUND_BUSH.func_225566_b_(field_226821_p_).func_227227_a_(0.5F), Feature.MEGA_JUNGLE_TREE.func_225566_b_(field_226825_t_).func_227227_a_(0.33333334F)), Feature.NORMAL_TREE.func_225566_b_(field_226792_b_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(50, 0.1F, 1))));
   }

   public static void func_222290_D(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.func_225566_b_(field_226815_j_).func_227227_a_(0.1F), Feature.JUNGLE_GROUND_BUSH.func_225566_b_(field_226821_p_).func_227227_a_(0.5F)), Feature.NORMAL_TREE.func_225566_b_(field_226792_b_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
   }

   public static void func_222327_E(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.func_225566_b_(field_226739_a_).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(5, 0.1F, 1))));
   }

   public static void func_222284_F(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.func_225566_b_(field_226810_e_).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
   }

   public static void func_222316_G(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.MEGA_SPRUCE_TREE.func_225566_b_(field_226823_r_).func_227227_a_(0.33333334F), Feature.NORMAL_TREE.func_225566_b_(field_226809_d_).func_227227_a_(0.33333334F)), Feature.NORMAL_TREE.func_225566_b_(field_226810_e_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void func_222285_H(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.MEGA_SPRUCE_TREE.func_225566_b_(field_226823_r_).func_227227_a_(0.025641026F), Feature.MEGA_SPRUCE_TREE.func_225566_b_(field_226824_s_).func_227227_a_(0.30769232F), Feature.NORMAL_TREE.func_225566_b_(field_226809_d_).func_227227_a_(0.33333334F)), Feature.NORMAL_TREE.func_225566_b_(field_226810_e_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
   }

   public static void addJungleGrass(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226828_w_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(25))));
   }

   public static void func_222344_J(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226727_O_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(7))));
   }

   public static void func_222314_K(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(5))));
   }

   public static void func_222339_L(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(20))));
   }

   public static void func_222308_M(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226715_C_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(20))));
   }

   public static void addDoubleFlowers(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.func_225566_b_(new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(Feature.field_227248_z_.func_225566_b_(field_226723_K_), Feature.field_227248_z_.func_225566_b_(field_226724_L_), Feature.field_227248_z_.func_225566_b_(field_226725_M_), Feature.field_227247_y_.func_225566_b_(field_226829_x_)), 0)).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(5))));
   }

   public static void addGrass(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(2))));
   }

   public static void addSwampVegetation(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.func_225566_b_(field_226814_i_).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(field_226830_y_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(5))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226715_C_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226720_H_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(4))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP.func_227446_a_(new HeightWithChanceConfig(8, 0.25F))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new HeightWithChanceConfig(8, 0.125F))));
   }

   public static void addHugeMushrooms(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_BOOLEAN_SELECTOR.func_225566_b_(new TwoFeatureChoiceConfig(Feature.HUGE_RED_MUSHROOM.func_225566_b_(field_226767_ab_), Feature.HUGE_BROWN_MUSHROOM.func_225566_b_(field_226768_ac_))).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP.func_227446_a_(new HeightWithChanceConfig(1, 0.25F))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new HeightWithChanceConfig(1, 0.125F))));
   }

   public static void func_222299_R(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.func_225566_b_(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.func_225566_b_(field_226817_l_).func_227227_a_(0.33333334F)), Feature.NORMAL_TREE.func_225566_b_(field_226816_k_))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(field_226713_A_).func_227228_a_(Placement.NOISE_HEIGHTMAP_32.func_227446_a_(new NoiseDependant(-0.8D, 15, 4))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.NOISE_HEIGHTMAP_DOUBLE.func_227446_a_(new NoiseDependant(-0.8D, 5, 10))));
   }

   public static void addDeadBushes(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226715_C_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(2))));
   }

   public static void func_222303_T(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226827_v_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(7))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226715_C_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP.func_227446_a_(new HeightWithChanceConfig(3, 0.25F))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new HeightWithChanceConfig(3, 0.125F))));
   }

   public static void addDefaultFlowers(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(field_226831_z_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(2))));
   }

   public static void addExtraDefaultFlowers(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(field_226831_z_).func_227228_a_(Placement.COUNT_HEIGHTMAP_32.func_227446_a_(new FrequencyConfig(4))));
   }

   public static void func_222348_W(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226826_u_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
   }

   public static void func_222319_X(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226827_v_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP.func_227446_a_(new HeightWithChanceConfig(1, 0.25F))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new HeightWithChanceConfig(1, 0.125F))));
   }

   public static void func_222283_Y(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226727_O_).func_227228_a_(Placement.NOISE_HEIGHTMAP_32.func_227446_a_(new NoiseDependant(-0.8D, 0, 7))));
   }

   public static void addMushrooms(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226722_J_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(4))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226721_I_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(8))));
   }

   public static void addReedsAndPumpkins(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226730_R_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(10))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226717_E_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(32))));
   }

   public static void addReedsPumpkinsCactus(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226730_R_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(13))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226717_E_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(32))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226729_Q_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(5))));
   }

   public static void addJunglePlants(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226716_D_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(1))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.VINES.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.COUNT_HEIGHT_64.func_227446_a_(new FrequencyConfig(50))));
   }

   public static void addExtraReedsPumpkinsCactus(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226730_R_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(60))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226717_E_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(32))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226729_Q_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(10))));
   }

   public static void func_222329_ae(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226730_R_).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(20))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(field_226717_E_).func_227228_a_(Placement.CHANCE_HEIGHTMAP_DOUBLE.func_227446_a_(new ChanceConfig(32))));
   }

   public static void addDesertFeatures(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.DESERT_WELL.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.CHANCE_HEIGHTMAP.func_227446_a_(new ChanceConfig(1000))));
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.FOSSIL.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.CHANCE_PASSTHROUGH.func_227446_a_(new ChanceConfig(64))));
   }

   public static void addFossils(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.FOSSIL.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.CHANCE_PASSTHROUGH.func_227446_a_(new ChanceConfig(64))));
   }

   public static void func_222287_ah(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.KELP.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.func_227446_a_(new TopSolidWithNoiseConfig(120, 80.0D, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG))));
   }

   public static void func_222320_ai(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SIMPLE_BLOCK.func_225566_b_(new BlockWithContextConfig(field_226759_aT_, new BlockState[]{field_226745_aF_}, new BlockState[]{field_226784_as_}, new BlockState[]{field_226784_as_})).func_227228_a_(Placement.CARVING_MASK.func_227446_a_(new CaveEdgeConfig(GenerationStage.Carving.LIQUID, 0.1F))));
   }

   public static void func_222309_aj(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.func_225566_b_(new SeaGrassConfig(80, 0.3D)).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
   }

   public static void func_222340_ak(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.func_225566_b_(new SeaGrassConfig(80, 0.8D)).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
   }

   public static void addKelp(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.KELP.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.func_227446_a_(new TopSolidWithNoiseConfig(80, 80.0D, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG))));
   }

   public static void addSprings(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(field_226736_X_).func_227228_a_(Placement.COUNT_BIASED_RANGE.func_227446_a_(new CountRangeConfig(50, 8, 8, 256))));
      biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.func_225566_b_(field_226737_Y_).func_227228_a_(Placement.COUNT_VERY_BIASED_RANGE.func_227446_a_(new CountRangeConfig(20, 8, 16, 256))));
   }

   public static void addIcebergs(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.ICEBERG.func_225566_b_(new BlockStateFeatureConfig(field_226760_aU_)).func_227228_a_(Placement.ICEBERG.func_227446_a_(new ChanceConfig(16))));
      biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.ICEBERG.func_225566_b_(new BlockStateFeatureConfig(field_226761_aV_)).func_227228_a_(Placement.ICEBERG.func_227446_a_(new ChanceConfig(200))));
   }

   public static void addBlueIce(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.BLUE_ICE.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.RANDOM_COUNT_RANGE.func_227446_a_(new CountRangeConfig(20, 30, 32, 64))));
   }

   public static void addFreezeTopLayer(Biome biomeIn) {
      biomeIn.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Feature.FREEZE_TOP_LAYER.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
   }

   public static void func_225489_aq(Biome p_225489_0_) {
      p_225489_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.END_CITY.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
   }
}