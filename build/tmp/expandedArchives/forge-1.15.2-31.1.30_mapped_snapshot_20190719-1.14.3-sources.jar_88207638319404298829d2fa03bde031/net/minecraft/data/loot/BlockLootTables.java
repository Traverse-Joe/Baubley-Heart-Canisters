package net.minecraft.data.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarrotBlock;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.PotatoBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.storage.loot.AlternativesLootEntry;
import net.minecraft.world.storage.loot.BinomialRange;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.DynamicLootEntry;
import net.minecraft.world.storage.loot.ILootConditionConsumer;
import net.minecraft.world.storage.loot.ILootFunctionConsumer;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.IntClamper;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.StandaloneLootEntry;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.EntityHasProperty;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.conditions.TableBonus;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.CopyBlockState;
import net.minecraft.world.storage.loot.functions.CopyName;
import net.minecraft.world.storage.loot.functions.CopyNbt;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.LimitCount;
import net.minecraft.world.storage.loot.functions.SetContents;
import net.minecraft.world.storage.loot.functions.SetCount;

public class BlockLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   private static final ILootCondition.IBuilder field_218573_a = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
   private static final ILootCondition.IBuilder field_218574_b = field_218573_a.inverted();
   private static final ILootCondition.IBuilder field_218575_c = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
   private static final ILootCondition.IBuilder field_218576_d = field_218575_c.alternative(field_218573_a);
   private static final ILootCondition.IBuilder field_218577_e = field_218576_d.inverted();
   private static final Set<Item> field_218578_f = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(IItemProvider::asItem).collect(ImmutableSet.toImmutableSet());
   private static final float[] field_218579_g = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
   private static final float[] field_218580_h = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
   private final Map<ResourceLocation, LootTable.Builder> field_218581_i = Maps.newHashMap();

   protected static <T> T func_218552_a(IItemProvider p_218552_0_, ILootFunctionConsumer<T> p_218552_1_) {
      return (T)(!field_218578_f.contains(p_218552_0_.asItem()) ? p_218552_1_.acceptFunction(ExplosionDecay.func_215863_b()) : p_218552_1_.cast());
   }

   protected static <T> T func_218560_a(IItemProvider p_218560_0_, ILootConditionConsumer<T> p_218560_1_) {
      return (T)(!field_218578_f.contains(p_218560_0_.asItem()) ? p_218560_1_.acceptCondition(SurvivesExplosion.builder()) : p_218560_1_.cast());
   }

   protected static LootTable.Builder func_218546_a(IItemProvider p_218546_0_) {
      return LootTable.builder().addLootPool(func_218560_a(p_218546_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218546_0_))));
   }

   protected static LootTable.Builder func_218494_a(Block p_218494_0_, ILootCondition.IBuilder p_218494_1_, LootEntry.Builder<?> p_218494_2_) {
      return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(((StandaloneLootEntry.Builder)ItemLootEntry.builder(p_218494_0_).acceptCondition(p_218494_1_)).func_216080_a(p_218494_2_)));
   }

   protected static LootTable.Builder func_218519_a(Block p_218519_0_, LootEntry.Builder<?> p_218519_1_) {
      return func_218494_a(p_218519_0_, field_218573_a, p_218519_1_);
   }

   protected static LootTable.Builder func_218511_b(Block p_218511_0_, LootEntry.Builder<?> p_218511_1_) {
      return func_218494_a(p_218511_0_, field_218575_c, p_218511_1_);
   }

   protected static LootTable.Builder func_218535_c(Block p_218535_0_, LootEntry.Builder<?> p_218535_1_) {
      return func_218494_a(p_218535_0_, field_218576_d, p_218535_1_);
   }

   protected static LootTable.Builder func_218515_b(Block p_218515_0_, IItemProvider p_218515_1_) {
      return func_218519_a(p_218515_0_, func_218560_a(p_218515_0_, ItemLootEntry.builder(p_218515_1_)));
   }

   protected static LootTable.Builder func_218463_a(IItemProvider p_218463_0_, IRandomRange p_218463_1_) {
      return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(func_218552_a(p_218463_0_, ItemLootEntry.builder(p_218463_0_).acceptFunction(SetCount.func_215932_a(p_218463_1_)))));
   }

   protected static LootTable.Builder func_218530_a(Block p_218530_0_, IItemProvider p_218530_1_, IRandomRange p_218530_2_) {
      return func_218519_a(p_218530_0_, func_218552_a(p_218530_0_, ItemLootEntry.builder(p_218530_1_).acceptFunction(SetCount.func_215932_a(p_218530_2_))));
   }

   protected static LootTable.Builder func_218561_b(IItemProvider p_218561_0_) {
      return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(field_218573_a).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218561_0_)));
   }

   protected static LootTable.Builder func_218523_c(IItemProvider p_218523_0_) {
      return LootTable.builder().addLootPool(func_218560_a(Blocks.FLOWER_POT, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.FLOWER_POT)))).addLootPool(func_218560_a(p_218523_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218523_0_))));
   }

   protected static LootTable.Builder func_218513_d(Block p_218513_0_) {
      return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(func_218552_a(p_218513_0_, ItemLootEntry.builder(p_218513_0_).acceptFunction(SetCount.func_215932_a(ConstantRange.of(2)).acceptCondition(BlockStateProperty.builder(p_218513_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227193_a_(SlabBlock.TYPE, SlabType.DOUBLE)))))));
   }

   protected static <T extends Comparable<T> & IStringSerializable> LootTable.Builder func_218562_a(Block p_218562_0_, IProperty<T> p_218562_1_, T p_218562_2_) {
      return LootTable.builder().addLootPool(func_218560_a(p_218562_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218562_0_).acceptCondition(BlockStateProperty.builder(p_218562_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227193_a_(p_218562_1_, p_218562_2_))))));
   }

   protected static LootTable.Builder func_218481_e(Block p_218481_0_) {
      return LootTable.builder().addLootPool(func_218560_a(p_218481_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218481_0_).acceptFunction(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)))));
   }

   protected static LootTable.Builder func_218544_f(Block p_218544_0_) {
      return LootTable.builder().addLootPool(func_218560_a(p_218544_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218544_0_).acceptFunction(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)).acceptFunction(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Lock", "BlockEntityTag.Lock").func_216056_a("LootTable", "BlockEntityTag.LootTable").func_216056_a("LootTableSeed", "BlockEntityTag.LootTableSeed")).acceptFunction(SetContents.func_215920_b().func_216075_a(DynamicLootEntry.func_216162_a(ShulkerBoxBlock.field_220169_b))))));
   }

   protected static LootTable.Builder func_218559_g(Block p_218559_0_) {
      return LootTable.builder().addLootPool(func_218560_a(p_218559_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218559_0_).acceptFunction(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)).acceptFunction(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Patterns", "BlockEntityTag.Patterns")))));
   }

   private static LootTable.Builder func_229436_h_(Block p_229436_0_) {
      return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(field_218573_a).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_229436_0_).acceptFunction(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Bees", "BlockEntityTag.Bees")).acceptFunction(CopyBlockState.func_227545_a_(p_229436_0_).func_227552_a_(BeehiveBlock.field_226873_c_))));
   }

   private static LootTable.Builder func_229437_i_(Block p_229437_0_) {
      return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(((StandaloneLootEntry.Builder)ItemLootEntry.builder(p_229437_0_).acceptCondition(field_218573_a)).acceptFunction(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Bees", "BlockEntityTag.Bees")).acceptFunction(CopyBlockState.func_227545_a_(p_229437_0_).func_227552_a_(BeehiveBlock.field_226873_c_)).func_216080_a(ItemLootEntry.builder(p_229437_0_))));
   }

   protected static LootTable.Builder func_218476_a(Block p_218476_0_, Item p_218476_1_) {
      return func_218519_a(p_218476_0_, func_218552_a(p_218476_0_, ItemLootEntry.builder(p_218476_1_).acceptFunction(ApplyBonus.func_215869_a(Enchantments.FORTUNE))));
   }

   protected static LootTable.Builder func_218491_c(Block p_218491_0_, IItemProvider p_218491_1_) {
      return func_218519_a(p_218491_0_, func_218552_a(p_218491_0_, ItemLootEntry.builder(p_218491_1_).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(-6.0F, 2.0F))).acceptFunction(LimitCount.func_215911_a(IntClamper.func_215848_a(0)))));
   }

   protected static LootTable.Builder func_218570_h(Block p_218570_0_) {
      return func_218511_b(p_218570_0_, func_218552_a(p_218570_0_, (ItemLootEntry.builder(Items.WHEAT_SEEDS).acceptCondition(RandomChance.builder(0.125F))).acceptFunction(ApplyBonus.func_215865_a(Enchantments.FORTUNE, 2))));
   }

   protected static LootTable.Builder func_218475_b(Block p_218475_0_, Item p_218475_1_) {
      return LootTable.builder().addLootPool(func_218552_a(p_218475_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218475_1_).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.06666667F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(StemBlock.AGE, 0)))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.13333334F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(StemBlock.AGE, 1)))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.2F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(StemBlock.AGE, 2)))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.26666668F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(StemBlock.AGE, 3)))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.33333334F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(StemBlock.AGE, 4)))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.4F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(StemBlock.AGE, 5)))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.46666667F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(StemBlock.AGE, 6)))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.53333336F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(StemBlock.AGE, 7)))))));
   }

   private static LootTable.Builder func_229435_c_(Block p_229435_0_, Item p_229435_1_) {
      return LootTable.builder().addLootPool(func_218552_a(p_229435_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_229435_1_).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.53333336F))))));
   }

   protected static LootTable.Builder func_218486_d(IItemProvider p_218486_0_) {
      return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(field_218575_c).addEntry(ItemLootEntry.builder(p_218486_0_)));
   }

   protected static LootTable.Builder func_218540_a(Block p_218540_0_, Block p_218540_1_, float... p_218540_2_) {
      return func_218535_c(p_218540_0_, func_218560_a(p_218540_0_, ItemLootEntry.builder(p_218540_1_)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, p_218540_2_))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(field_218577_e).addEntry(func_218552_a(p_218540_0_, ItemLootEntry.builder(Items.STICK).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 2.0F)))).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
   }

   protected static LootTable.Builder func_218526_b(Block p_218526_0_, Block p_218526_1_, float... p_218526_2_) {
      return func_218540_a(p_218526_0_, p_218526_1_, p_218526_2_).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(field_218577_e).addEntry(func_218560_a(p_218526_0_, ItemLootEntry.builder(Items.APPLE)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
   }

   protected static LootTable.Builder func_218541_a(Block p_218541_0_, Item p_218541_1_, Item p_218541_2_, ILootCondition.IBuilder p_218541_3_) {
      return func_218552_a(p_218541_0_, LootTable.builder().addLootPool(LootPool.builder().addEntry(((StandaloneLootEntry.Builder)ItemLootEntry.builder(p_218541_1_).acceptCondition(p_218541_3_)).func_216080_a(ItemLootEntry.builder(p_218541_2_)))).addLootPool(LootPool.builder().acceptCondition(p_218541_3_).addEntry(ItemLootEntry.builder(p_218541_2_).acceptFunction(ApplyBonus.func_215870_a(Enchantments.FORTUNE, 0.5714286F, 3)))));
   }

   public static LootTable.Builder func_218482_a() {
      return LootTable.builder();
   }

   protected void addTables() {
      this.func_218492_c(Blocks.GRANITE);
      this.func_218492_c(Blocks.POLISHED_GRANITE);
      this.func_218492_c(Blocks.DIORITE);
      this.func_218492_c(Blocks.POLISHED_DIORITE);
      this.func_218492_c(Blocks.ANDESITE);
      this.func_218492_c(Blocks.POLISHED_ANDESITE);
      this.func_218492_c(Blocks.DIRT);
      this.func_218492_c(Blocks.COARSE_DIRT);
      this.func_218492_c(Blocks.COBBLESTONE);
      this.func_218492_c(Blocks.OAK_PLANKS);
      this.func_218492_c(Blocks.SPRUCE_PLANKS);
      this.func_218492_c(Blocks.BIRCH_PLANKS);
      this.func_218492_c(Blocks.JUNGLE_PLANKS);
      this.func_218492_c(Blocks.ACACIA_PLANKS);
      this.func_218492_c(Blocks.DARK_OAK_PLANKS);
      this.func_218492_c(Blocks.OAK_SAPLING);
      this.func_218492_c(Blocks.SPRUCE_SAPLING);
      this.func_218492_c(Blocks.BIRCH_SAPLING);
      this.func_218492_c(Blocks.JUNGLE_SAPLING);
      this.func_218492_c(Blocks.ACACIA_SAPLING);
      this.func_218492_c(Blocks.DARK_OAK_SAPLING);
      this.func_218492_c(Blocks.SAND);
      this.func_218492_c(Blocks.RED_SAND);
      this.func_218492_c(Blocks.GOLD_ORE);
      this.func_218492_c(Blocks.IRON_ORE);
      this.func_218492_c(Blocks.OAK_LOG);
      this.func_218492_c(Blocks.SPRUCE_LOG);
      this.func_218492_c(Blocks.BIRCH_LOG);
      this.func_218492_c(Blocks.JUNGLE_LOG);
      this.func_218492_c(Blocks.ACACIA_LOG);
      this.func_218492_c(Blocks.DARK_OAK_LOG);
      this.func_218492_c(Blocks.STRIPPED_SPRUCE_LOG);
      this.func_218492_c(Blocks.STRIPPED_BIRCH_LOG);
      this.func_218492_c(Blocks.STRIPPED_JUNGLE_LOG);
      this.func_218492_c(Blocks.STRIPPED_ACACIA_LOG);
      this.func_218492_c(Blocks.STRIPPED_DARK_OAK_LOG);
      this.func_218492_c(Blocks.STRIPPED_OAK_LOG);
      this.func_218492_c(Blocks.OAK_WOOD);
      this.func_218492_c(Blocks.SPRUCE_WOOD);
      this.func_218492_c(Blocks.BIRCH_WOOD);
      this.func_218492_c(Blocks.JUNGLE_WOOD);
      this.func_218492_c(Blocks.ACACIA_WOOD);
      this.func_218492_c(Blocks.DARK_OAK_WOOD);
      this.func_218492_c(Blocks.STRIPPED_OAK_WOOD);
      this.func_218492_c(Blocks.STRIPPED_SPRUCE_WOOD);
      this.func_218492_c(Blocks.STRIPPED_BIRCH_WOOD);
      this.func_218492_c(Blocks.STRIPPED_JUNGLE_WOOD);
      this.func_218492_c(Blocks.STRIPPED_ACACIA_WOOD);
      this.func_218492_c(Blocks.STRIPPED_DARK_OAK_WOOD);
      this.func_218492_c(Blocks.SPONGE);
      this.func_218492_c(Blocks.WET_SPONGE);
      this.func_218492_c(Blocks.LAPIS_BLOCK);
      this.func_218492_c(Blocks.SANDSTONE);
      this.func_218492_c(Blocks.CHISELED_SANDSTONE);
      this.func_218492_c(Blocks.CUT_SANDSTONE);
      this.func_218492_c(Blocks.NOTE_BLOCK);
      this.func_218492_c(Blocks.POWERED_RAIL);
      this.func_218492_c(Blocks.DETECTOR_RAIL);
      this.func_218492_c(Blocks.STICKY_PISTON);
      this.func_218492_c(Blocks.PISTON);
      this.func_218492_c(Blocks.WHITE_WOOL);
      this.func_218492_c(Blocks.ORANGE_WOOL);
      this.func_218492_c(Blocks.MAGENTA_WOOL);
      this.func_218492_c(Blocks.LIGHT_BLUE_WOOL);
      this.func_218492_c(Blocks.YELLOW_WOOL);
      this.func_218492_c(Blocks.LIME_WOOL);
      this.func_218492_c(Blocks.PINK_WOOL);
      this.func_218492_c(Blocks.GRAY_WOOL);
      this.func_218492_c(Blocks.LIGHT_GRAY_WOOL);
      this.func_218492_c(Blocks.CYAN_WOOL);
      this.func_218492_c(Blocks.PURPLE_WOOL);
      this.func_218492_c(Blocks.BLUE_WOOL);
      this.func_218492_c(Blocks.BROWN_WOOL);
      this.func_218492_c(Blocks.GREEN_WOOL);
      this.func_218492_c(Blocks.RED_WOOL);
      this.func_218492_c(Blocks.BLACK_WOOL);
      this.func_218492_c(Blocks.DANDELION);
      this.func_218492_c(Blocks.POPPY);
      this.func_218492_c(Blocks.BLUE_ORCHID);
      this.func_218492_c(Blocks.ALLIUM);
      this.func_218492_c(Blocks.AZURE_BLUET);
      this.func_218492_c(Blocks.RED_TULIP);
      this.func_218492_c(Blocks.ORANGE_TULIP);
      this.func_218492_c(Blocks.WHITE_TULIP);
      this.func_218492_c(Blocks.PINK_TULIP);
      this.func_218492_c(Blocks.OXEYE_DAISY);
      this.func_218492_c(Blocks.CORNFLOWER);
      this.func_218492_c(Blocks.WITHER_ROSE);
      this.func_218492_c(Blocks.LILY_OF_THE_VALLEY);
      this.func_218492_c(Blocks.BROWN_MUSHROOM);
      this.func_218492_c(Blocks.RED_MUSHROOM);
      this.func_218492_c(Blocks.GOLD_BLOCK);
      this.func_218492_c(Blocks.IRON_BLOCK);
      this.func_218492_c(Blocks.BRICKS);
      this.func_218492_c(Blocks.MOSSY_COBBLESTONE);
      this.func_218492_c(Blocks.OBSIDIAN);
      this.func_218492_c(Blocks.TORCH);
      this.func_218492_c(Blocks.OAK_STAIRS);
      this.func_218492_c(Blocks.REDSTONE_WIRE);
      this.func_218492_c(Blocks.DIAMOND_BLOCK);
      this.func_218492_c(Blocks.CRAFTING_TABLE);
      this.func_218492_c(Blocks.OAK_SIGN);
      this.func_218492_c(Blocks.SPRUCE_SIGN);
      this.func_218492_c(Blocks.BIRCH_SIGN);
      this.func_218492_c(Blocks.ACACIA_SIGN);
      this.func_218492_c(Blocks.JUNGLE_SIGN);
      this.func_218492_c(Blocks.DARK_OAK_SIGN);
      this.func_218492_c(Blocks.LADDER);
      this.func_218492_c(Blocks.RAIL);
      this.func_218492_c(Blocks.COBBLESTONE_STAIRS);
      this.func_218492_c(Blocks.LEVER);
      this.func_218492_c(Blocks.STONE_PRESSURE_PLATE);
      this.func_218492_c(Blocks.OAK_PRESSURE_PLATE);
      this.func_218492_c(Blocks.SPRUCE_PRESSURE_PLATE);
      this.func_218492_c(Blocks.BIRCH_PRESSURE_PLATE);
      this.func_218492_c(Blocks.JUNGLE_PRESSURE_PLATE);
      this.func_218492_c(Blocks.ACACIA_PRESSURE_PLATE);
      this.func_218492_c(Blocks.DARK_OAK_PRESSURE_PLATE);
      this.func_218492_c(Blocks.REDSTONE_TORCH);
      this.func_218492_c(Blocks.STONE_BUTTON);
      this.func_218492_c(Blocks.CACTUS);
      this.func_218492_c(Blocks.SUGAR_CANE);
      this.func_218492_c(Blocks.JUKEBOX);
      this.func_218492_c(Blocks.OAK_FENCE);
      this.func_218492_c(Blocks.PUMPKIN);
      this.func_218492_c(Blocks.NETHERRACK);
      this.func_218492_c(Blocks.SOUL_SAND);
      this.func_218492_c(Blocks.CARVED_PUMPKIN);
      this.func_218492_c(Blocks.JACK_O_LANTERN);
      this.func_218492_c(Blocks.REPEATER);
      this.func_218492_c(Blocks.OAK_TRAPDOOR);
      this.func_218492_c(Blocks.SPRUCE_TRAPDOOR);
      this.func_218492_c(Blocks.BIRCH_TRAPDOOR);
      this.func_218492_c(Blocks.JUNGLE_TRAPDOOR);
      this.func_218492_c(Blocks.ACACIA_TRAPDOOR);
      this.func_218492_c(Blocks.DARK_OAK_TRAPDOOR);
      this.func_218492_c(Blocks.STONE_BRICKS);
      this.func_218492_c(Blocks.MOSSY_STONE_BRICKS);
      this.func_218492_c(Blocks.CRACKED_STONE_BRICKS);
      this.func_218492_c(Blocks.CHISELED_STONE_BRICKS);
      this.func_218492_c(Blocks.IRON_BARS);
      this.func_218492_c(Blocks.OAK_FENCE_GATE);
      this.func_218492_c(Blocks.BRICK_STAIRS);
      this.func_218492_c(Blocks.STONE_BRICK_STAIRS);
      this.func_218492_c(Blocks.LILY_PAD);
      this.func_218492_c(Blocks.NETHER_BRICKS);
      this.func_218492_c(Blocks.NETHER_BRICK_FENCE);
      this.func_218492_c(Blocks.NETHER_BRICK_STAIRS);
      this.func_218492_c(Blocks.CAULDRON);
      this.func_218492_c(Blocks.END_STONE);
      this.func_218492_c(Blocks.REDSTONE_LAMP);
      this.func_218492_c(Blocks.SANDSTONE_STAIRS);
      this.func_218492_c(Blocks.TRIPWIRE_HOOK);
      this.func_218492_c(Blocks.EMERALD_BLOCK);
      this.func_218492_c(Blocks.SPRUCE_STAIRS);
      this.func_218492_c(Blocks.BIRCH_STAIRS);
      this.func_218492_c(Blocks.JUNGLE_STAIRS);
      this.func_218492_c(Blocks.COBBLESTONE_WALL);
      this.func_218492_c(Blocks.MOSSY_COBBLESTONE_WALL);
      this.func_218492_c(Blocks.FLOWER_POT);
      this.func_218492_c(Blocks.OAK_BUTTON);
      this.func_218492_c(Blocks.SPRUCE_BUTTON);
      this.func_218492_c(Blocks.BIRCH_BUTTON);
      this.func_218492_c(Blocks.JUNGLE_BUTTON);
      this.func_218492_c(Blocks.ACACIA_BUTTON);
      this.func_218492_c(Blocks.DARK_OAK_BUTTON);
      this.func_218492_c(Blocks.SKELETON_SKULL);
      this.func_218492_c(Blocks.WITHER_SKELETON_SKULL);
      this.func_218492_c(Blocks.ZOMBIE_HEAD);
      this.func_218492_c(Blocks.CREEPER_HEAD);
      this.func_218492_c(Blocks.DRAGON_HEAD);
      this.func_218492_c(Blocks.ANVIL);
      this.func_218492_c(Blocks.CHIPPED_ANVIL);
      this.func_218492_c(Blocks.DAMAGED_ANVIL);
      this.func_218492_c(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
      this.func_218492_c(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
      this.func_218492_c(Blocks.COMPARATOR);
      this.func_218492_c(Blocks.DAYLIGHT_DETECTOR);
      this.func_218492_c(Blocks.REDSTONE_BLOCK);
      this.func_218492_c(Blocks.QUARTZ_BLOCK);
      this.func_218492_c(Blocks.CHISELED_QUARTZ_BLOCK);
      this.func_218492_c(Blocks.QUARTZ_PILLAR);
      this.func_218492_c(Blocks.QUARTZ_STAIRS);
      this.func_218492_c(Blocks.ACTIVATOR_RAIL);
      this.func_218492_c(Blocks.WHITE_TERRACOTTA);
      this.func_218492_c(Blocks.ORANGE_TERRACOTTA);
      this.func_218492_c(Blocks.MAGENTA_TERRACOTTA);
      this.func_218492_c(Blocks.LIGHT_BLUE_TERRACOTTA);
      this.func_218492_c(Blocks.YELLOW_TERRACOTTA);
      this.func_218492_c(Blocks.LIME_TERRACOTTA);
      this.func_218492_c(Blocks.PINK_TERRACOTTA);
      this.func_218492_c(Blocks.GRAY_TERRACOTTA);
      this.func_218492_c(Blocks.LIGHT_GRAY_TERRACOTTA);
      this.func_218492_c(Blocks.CYAN_TERRACOTTA);
      this.func_218492_c(Blocks.PURPLE_TERRACOTTA);
      this.func_218492_c(Blocks.BLUE_TERRACOTTA);
      this.func_218492_c(Blocks.BROWN_TERRACOTTA);
      this.func_218492_c(Blocks.GREEN_TERRACOTTA);
      this.func_218492_c(Blocks.RED_TERRACOTTA);
      this.func_218492_c(Blocks.BLACK_TERRACOTTA);
      this.func_218492_c(Blocks.ACACIA_STAIRS);
      this.func_218492_c(Blocks.DARK_OAK_STAIRS);
      this.func_218492_c(Blocks.SLIME_BLOCK);
      this.func_218492_c(Blocks.IRON_TRAPDOOR);
      this.func_218492_c(Blocks.PRISMARINE);
      this.func_218492_c(Blocks.PRISMARINE_BRICKS);
      this.func_218492_c(Blocks.DARK_PRISMARINE);
      this.func_218492_c(Blocks.PRISMARINE_STAIRS);
      this.func_218492_c(Blocks.PRISMARINE_BRICK_STAIRS);
      this.func_218492_c(Blocks.DARK_PRISMARINE_STAIRS);
      this.func_218492_c(Blocks.HAY_BLOCK);
      this.func_218492_c(Blocks.WHITE_CARPET);
      this.func_218492_c(Blocks.ORANGE_CARPET);
      this.func_218492_c(Blocks.MAGENTA_CARPET);
      this.func_218492_c(Blocks.LIGHT_BLUE_CARPET);
      this.func_218492_c(Blocks.YELLOW_CARPET);
      this.func_218492_c(Blocks.LIME_CARPET);
      this.func_218492_c(Blocks.PINK_CARPET);
      this.func_218492_c(Blocks.GRAY_CARPET);
      this.func_218492_c(Blocks.LIGHT_GRAY_CARPET);
      this.func_218492_c(Blocks.CYAN_CARPET);
      this.func_218492_c(Blocks.PURPLE_CARPET);
      this.func_218492_c(Blocks.BLUE_CARPET);
      this.func_218492_c(Blocks.BROWN_CARPET);
      this.func_218492_c(Blocks.GREEN_CARPET);
      this.func_218492_c(Blocks.RED_CARPET);
      this.func_218492_c(Blocks.BLACK_CARPET);
      this.func_218492_c(Blocks.TERRACOTTA);
      this.func_218492_c(Blocks.COAL_BLOCK);
      this.func_218492_c(Blocks.RED_SANDSTONE);
      this.func_218492_c(Blocks.CHISELED_RED_SANDSTONE);
      this.func_218492_c(Blocks.CUT_RED_SANDSTONE);
      this.func_218492_c(Blocks.RED_SANDSTONE_STAIRS);
      this.func_218492_c(Blocks.SMOOTH_STONE);
      this.func_218492_c(Blocks.SMOOTH_SANDSTONE);
      this.func_218492_c(Blocks.SMOOTH_QUARTZ);
      this.func_218492_c(Blocks.SMOOTH_RED_SANDSTONE);
      this.func_218492_c(Blocks.SPRUCE_FENCE_GATE);
      this.func_218492_c(Blocks.BIRCH_FENCE_GATE);
      this.func_218492_c(Blocks.JUNGLE_FENCE_GATE);
      this.func_218492_c(Blocks.ACACIA_FENCE_GATE);
      this.func_218492_c(Blocks.DARK_OAK_FENCE_GATE);
      this.func_218492_c(Blocks.SPRUCE_FENCE);
      this.func_218492_c(Blocks.BIRCH_FENCE);
      this.func_218492_c(Blocks.JUNGLE_FENCE);
      this.func_218492_c(Blocks.ACACIA_FENCE);
      this.func_218492_c(Blocks.DARK_OAK_FENCE);
      this.func_218492_c(Blocks.END_ROD);
      this.func_218492_c(Blocks.PURPUR_BLOCK);
      this.func_218492_c(Blocks.PURPUR_PILLAR);
      this.func_218492_c(Blocks.PURPUR_STAIRS);
      this.func_218492_c(Blocks.END_STONE_BRICKS);
      this.func_218492_c(Blocks.MAGMA_BLOCK);
      this.func_218492_c(Blocks.NETHER_WART_BLOCK);
      this.func_218492_c(Blocks.RED_NETHER_BRICKS);
      this.func_218492_c(Blocks.BONE_BLOCK);
      this.func_218492_c(Blocks.OBSERVER);
      this.func_218492_c(Blocks.WHITE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.ORANGE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.MAGENTA_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.YELLOW_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.LIME_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.PINK_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.GRAY_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.CYAN_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.PURPLE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.BLUE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.BROWN_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.GREEN_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.RED_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.BLACK_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.WHITE_CONCRETE);
      this.func_218492_c(Blocks.ORANGE_CONCRETE);
      this.func_218492_c(Blocks.MAGENTA_CONCRETE);
      this.func_218492_c(Blocks.LIGHT_BLUE_CONCRETE);
      this.func_218492_c(Blocks.YELLOW_CONCRETE);
      this.func_218492_c(Blocks.LIME_CONCRETE);
      this.func_218492_c(Blocks.PINK_CONCRETE);
      this.func_218492_c(Blocks.GRAY_CONCRETE);
      this.func_218492_c(Blocks.LIGHT_GRAY_CONCRETE);
      this.func_218492_c(Blocks.CYAN_CONCRETE);
      this.func_218492_c(Blocks.PURPLE_CONCRETE);
      this.func_218492_c(Blocks.BLUE_CONCRETE);
      this.func_218492_c(Blocks.BROWN_CONCRETE);
      this.func_218492_c(Blocks.GREEN_CONCRETE);
      this.func_218492_c(Blocks.RED_CONCRETE);
      this.func_218492_c(Blocks.BLACK_CONCRETE);
      this.func_218492_c(Blocks.WHITE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.ORANGE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.MAGENTA_CONCRETE_POWDER);
      this.func_218492_c(Blocks.LIGHT_BLUE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.YELLOW_CONCRETE_POWDER);
      this.func_218492_c(Blocks.LIME_CONCRETE_POWDER);
      this.func_218492_c(Blocks.PINK_CONCRETE_POWDER);
      this.func_218492_c(Blocks.GRAY_CONCRETE_POWDER);
      this.func_218492_c(Blocks.LIGHT_GRAY_CONCRETE_POWDER);
      this.func_218492_c(Blocks.CYAN_CONCRETE_POWDER);
      this.func_218492_c(Blocks.PURPLE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.BLUE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.BROWN_CONCRETE_POWDER);
      this.func_218492_c(Blocks.GREEN_CONCRETE_POWDER);
      this.func_218492_c(Blocks.RED_CONCRETE_POWDER);
      this.func_218492_c(Blocks.BLACK_CONCRETE_POWDER);
      this.func_218492_c(Blocks.KELP);
      this.func_218492_c(Blocks.DRIED_KELP_BLOCK);
      this.func_218492_c(Blocks.DEAD_TUBE_CORAL_BLOCK);
      this.func_218492_c(Blocks.DEAD_BRAIN_CORAL_BLOCK);
      this.func_218492_c(Blocks.DEAD_BUBBLE_CORAL_BLOCK);
      this.func_218492_c(Blocks.DEAD_FIRE_CORAL_BLOCK);
      this.func_218492_c(Blocks.DEAD_HORN_CORAL_BLOCK);
      this.func_218492_c(Blocks.CONDUIT);
      this.func_218492_c(Blocks.DRAGON_EGG);
      this.func_218492_c(Blocks.BAMBOO);
      this.func_218492_c(Blocks.POLISHED_GRANITE_STAIRS);
      this.func_218492_c(Blocks.SMOOTH_RED_SANDSTONE_STAIRS);
      this.func_218492_c(Blocks.MOSSY_STONE_BRICK_STAIRS);
      this.func_218492_c(Blocks.POLISHED_DIORITE_STAIRS);
      this.func_218492_c(Blocks.MOSSY_COBBLESTONE_STAIRS);
      this.func_218492_c(Blocks.END_STONE_BRICK_STAIRS);
      this.func_218492_c(Blocks.STONE_STAIRS);
      this.func_218492_c(Blocks.SMOOTH_SANDSTONE_STAIRS);
      this.func_218492_c(Blocks.SMOOTH_QUARTZ_STAIRS);
      this.func_218492_c(Blocks.GRANITE_STAIRS);
      this.func_218492_c(Blocks.ANDESITE_STAIRS);
      this.func_218492_c(Blocks.RED_NETHER_BRICK_STAIRS);
      this.func_218492_c(Blocks.POLISHED_ANDESITE_STAIRS);
      this.func_218492_c(Blocks.DIORITE_STAIRS);
      this.func_218492_c(Blocks.BRICK_WALL);
      this.func_218492_c(Blocks.PRISMARINE_WALL);
      this.func_218492_c(Blocks.RED_SANDSTONE_WALL);
      this.func_218492_c(Blocks.MOSSY_STONE_BRICK_WALL);
      this.func_218492_c(Blocks.GRANITE_WALL);
      this.func_218492_c(Blocks.STONE_BRICK_WALL);
      this.func_218492_c(Blocks.NETHER_BRICK_WALL);
      this.func_218492_c(Blocks.ANDESITE_WALL);
      this.func_218492_c(Blocks.RED_NETHER_BRICK_WALL);
      this.func_218492_c(Blocks.SANDSTONE_WALL);
      this.func_218492_c(Blocks.END_STONE_BRICK_WALL);
      this.func_218492_c(Blocks.DIORITE_WALL);
      this.func_218492_c(Blocks.LOOM);
      this.func_218492_c(Blocks.SCAFFOLDING);
      this.func_218492_c(Blocks.field_226907_mc_);
      this.func_218492_c(Blocks.field_226908_md_);
      this.func_218493_a(Blocks.FARMLAND, Blocks.DIRT);
      this.func_218493_a(Blocks.TRIPWIRE, Items.STRING);
      this.func_218493_a(Blocks.GRASS_PATH, Blocks.DIRT);
      this.func_218493_a(Blocks.KELP_PLANT, Blocks.KELP);
      this.func_218493_a(Blocks.BAMBOO_SAPLING, Blocks.BAMBOO);
      this.registerLootTable(Blocks.STONE, (p_218490_0_) -> {
         return func_218515_b(p_218490_0_, Blocks.COBBLESTONE);
      });
      this.registerLootTable(Blocks.GRASS_BLOCK, (p_218529_0_) -> {
         return func_218515_b(p_218529_0_, Blocks.DIRT);
      });
      this.registerLootTable(Blocks.PODZOL, (p_218514_0_) -> {
         return func_218515_b(p_218514_0_, Blocks.DIRT);
      });
      this.registerLootTable(Blocks.MYCELIUM, (p_218501_0_) -> {
         return func_218515_b(p_218501_0_, Blocks.DIRT);
      });
      this.registerLootTable(Blocks.TUBE_CORAL_BLOCK, (p_218539_0_) -> {
         return func_218515_b(p_218539_0_, Blocks.DEAD_TUBE_CORAL_BLOCK);
      });
      this.registerLootTable(Blocks.BRAIN_CORAL_BLOCK, (p_218462_0_) -> {
         return func_218515_b(p_218462_0_, Blocks.DEAD_BRAIN_CORAL_BLOCK);
      });
      this.registerLootTable(Blocks.BUBBLE_CORAL_BLOCK, (p_218505_0_) -> {
         return func_218515_b(p_218505_0_, Blocks.DEAD_BUBBLE_CORAL_BLOCK);
      });
      this.registerLootTable(Blocks.FIRE_CORAL_BLOCK, (p_218499_0_) -> {
         return func_218515_b(p_218499_0_, Blocks.DEAD_FIRE_CORAL_BLOCK);
      });
      this.registerLootTable(Blocks.HORN_CORAL_BLOCK, (p_218502_0_) -> {
         return func_218515_b(p_218502_0_, Blocks.DEAD_HORN_CORAL_BLOCK);
      });
      this.registerLootTable(Blocks.BOOKSHELF, (p_218534_0_) -> {
         return func_218530_a(p_218534_0_, Items.BOOK, ConstantRange.of(3));
      });
      this.registerLootTable(Blocks.CLAY, (p_218465_0_) -> {
         return func_218530_a(p_218465_0_, Items.CLAY_BALL, ConstantRange.of(4));
      });
      this.registerLootTable(Blocks.ENDER_CHEST, (p_218558_0_) -> {
         return func_218530_a(p_218558_0_, Blocks.OBSIDIAN, ConstantRange.of(8));
      });
      this.registerLootTable(Blocks.SNOW_BLOCK, (p_218556_0_) -> {
         return func_218530_a(p_218556_0_, Items.SNOWBALL, ConstantRange.of(4));
      });
      this.registerLootTable(Blocks.CHORUS_PLANT, func_218463_a(Items.CHORUS_FRUIT, RandomValueRange.func_215837_a(0.0F, 1.0F)));
      this.func_218547_a(Blocks.POTTED_OAK_SAPLING);
      this.func_218547_a(Blocks.POTTED_SPRUCE_SAPLING);
      this.func_218547_a(Blocks.POTTED_BIRCH_SAPLING);
      this.func_218547_a(Blocks.POTTED_JUNGLE_SAPLING);
      this.func_218547_a(Blocks.POTTED_ACACIA_SAPLING);
      this.func_218547_a(Blocks.POTTED_DARK_OAK_SAPLING);
      this.func_218547_a(Blocks.POTTED_FERN);
      this.func_218547_a(Blocks.POTTED_DANDELION);
      this.func_218547_a(Blocks.POTTED_POPPY);
      this.func_218547_a(Blocks.POTTED_BLUE_ORCHID);
      this.func_218547_a(Blocks.POTTED_ALLIUM);
      this.func_218547_a(Blocks.POTTED_AZURE_BLUET);
      this.func_218547_a(Blocks.POTTED_RED_TULIP);
      this.func_218547_a(Blocks.POTTED_ORANGE_TULIP);
      this.func_218547_a(Blocks.POTTED_WHITE_TULIP);
      this.func_218547_a(Blocks.POTTED_PINK_TULIP);
      this.func_218547_a(Blocks.POTTED_OXEYE_DAISY);
      this.func_218547_a(Blocks.POTTED_CORNFLOWER);
      this.func_218547_a(Blocks.POTTED_LILY_OF_THE_VALLEY);
      this.func_218547_a(Blocks.POTTED_WITHER_ROSE);
      this.func_218547_a(Blocks.POTTED_RED_MUSHROOM);
      this.func_218547_a(Blocks.POTTED_BROWN_MUSHROOM);
      this.func_218547_a(Blocks.POTTED_DEAD_BUSH);
      this.func_218547_a(Blocks.POTTED_CACTUS);
      this.func_218547_a(Blocks.POTTED_BAMBOO);
      this.registerLootTable(Blocks.ACACIA_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.BIRCH_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.BRICK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.COBBLESTONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.DARK_OAK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.DARK_PRISMARINE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.JUNGLE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.NETHER_BRICK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.OAK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.PETRIFIED_OAK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.PRISMARINE_BRICK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.PRISMARINE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.PURPUR_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.QUARTZ_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.RED_SANDSTONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.SANDSTONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.CUT_RED_SANDSTONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.CUT_SANDSTONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.SPRUCE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.STONE_BRICK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.STONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.SMOOTH_STONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.POLISHED_GRANITE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.SMOOTH_RED_SANDSTONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.MOSSY_STONE_BRICK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.POLISHED_DIORITE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.MOSSY_COBBLESTONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.END_STONE_BRICK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.SMOOTH_SANDSTONE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.SMOOTH_QUARTZ_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.GRANITE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.ANDESITE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.RED_NETHER_BRICK_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.POLISHED_ANDESITE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.DIORITE_SLAB, BlockLootTables::func_218513_d);
      this.registerLootTable(Blocks.ACACIA_DOOR, (p_218483_0_) -> {
         return func_218562_a(p_218483_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.BIRCH_DOOR, (p_218528_0_) -> {
         return func_218562_a(p_218528_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.DARK_OAK_DOOR, (p_218468_0_) -> {
         return func_218562_a(p_218468_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.IRON_DOOR, (p_218510_0_) -> {
         return func_218562_a(p_218510_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.JUNGLE_DOOR, (p_218498_0_) -> {
         return func_218562_a(p_218498_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.OAK_DOOR, (p_218480_0_) -> {
         return func_218562_a(p_218480_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.SPRUCE_DOOR, (p_218527_0_) -> {
         return func_218562_a(p_218527_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.BLACK_BED, (p_218567_0_) -> {
         return func_218562_a(p_218567_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.BLUE_BED, (p_218555_0_) -> {
         return func_218562_a(p_218555_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.BROWN_BED, (p_218543_0_) -> {
         return func_218562_a(p_218543_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.CYAN_BED, (p_218479_0_) -> {
         return func_218562_a(p_218479_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.GRAY_BED, (p_218521_0_) -> {
         return func_218562_a(p_218521_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.GREEN_BED, (p_218470_0_) -> {
         return func_218562_a(p_218470_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.LIGHT_BLUE_BED, (p_218536_0_) -> {
         return func_218562_a(p_218536_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.LIGHT_GRAY_BED, (p_218545_0_) -> {
         return func_218562_a(p_218545_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.LIME_BED, (p_218557_0_) -> {
         return func_218562_a(p_218557_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.MAGENTA_BED, (p_218566_0_) -> {
         return func_218562_a(p_218566_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.PURPLE_BED, (p_218520_0_) -> {
         return func_218562_a(p_218520_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.ORANGE_BED, (p_218472_0_) -> {
         return func_218562_a(p_218472_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.PINK_BED, (p_218537_0_) -> {
         return func_218562_a(p_218537_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.RED_BED, (p_218549_0_) -> {
         return func_218562_a(p_218549_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.WHITE_BED, (p_218569_0_) -> {
         return func_218562_a(p_218569_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.YELLOW_BED, (p_218517_0_) -> {
         return func_218562_a(p_218517_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.registerLootTable(Blocks.LILAC, (p_218488_0_) -> {
         return func_218562_a(p_218488_0_, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.SUNFLOWER, (p_218503_0_) -> {
         return func_218562_a(p_218503_0_, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.PEONY, (p_218497_0_) -> {
         return func_218562_a(p_218497_0_, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.ROSE_BUSH, (p_218504_0_) -> {
         return func_218562_a(p_218504_0_, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.registerLootTable(Blocks.TNT, LootTable.builder().addLootPool(func_218560_a(Blocks.TNT, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.TNT).acceptCondition(BlockStateProperty.builder(Blocks.TNT).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227195_a_(TNTBlock.UNSTABLE, false)))))));
      this.registerLootTable(Blocks.COCOA, (p_218516_0_) -> {
         return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(func_218552_a(p_218516_0_, ItemLootEntry.builder(Items.COCOA_BEANS).acceptFunction(SetCount.func_215932_a(ConstantRange.of(3)).acceptCondition(BlockStateProperty.builder(p_218516_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(CocoaBlock.AGE, 2)))))));
      });
      this.registerLootTable(Blocks.SEA_PICKLE, (p_218478_0_) -> {
         return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(func_218552_a(Blocks.SEA_PICKLE, ItemLootEntry.builder(p_218478_0_).acceptFunction(SetCount.func_215932_a(ConstantRange.of(2)).acceptCondition(BlockStateProperty.builder(p_218478_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SeaPickleBlock.PICKLES, 2)))).acceptFunction(SetCount.func_215932_a(ConstantRange.of(3)).acceptCondition(BlockStateProperty.builder(p_218478_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SeaPickleBlock.PICKLES, 3)))).acceptFunction(SetCount.func_215932_a(ConstantRange.of(4)).acceptCondition(BlockStateProperty.builder(p_218478_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SeaPickleBlock.PICKLES, 4)))))));
      });
      this.registerLootTable(Blocks.COMPOSTER, (p_218551_0_) -> {
         return LootTable.builder().addLootPool(LootPool.builder().addEntry(func_218552_a(p_218551_0_, ItemLootEntry.builder(Items.COMPOSTER)))).addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(Items.BONE_MEAL)).acceptCondition(BlockStateProperty.builder(p_218551_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(ComposterBlock.field_220298_a, 8))));
      });
      this.registerLootTable(Blocks.BEACON, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.BREWING_STAND, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.CHEST, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.DISPENSER, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.DROPPER, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.ENCHANTING_TABLE, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.FURNACE, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.HOPPER, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.TRAPPED_CHEST, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.SMOKER, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.BLAST_FURNACE, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.BARREL, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.CARTOGRAPHY_TABLE, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.FLETCHING_TABLE, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.GRINDSTONE, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.LECTERN, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.SMITHING_TABLE, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.STONECUTTER, BlockLootTables::func_218481_e);
      this.registerLootTable(Blocks.BELL, BlockLootTables::func_218546_a);
      this.registerLootTable(Blocks.LANTERN, BlockLootTables::func_218546_a);
      this.registerLootTable(Blocks.SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.BLACK_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.BLUE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.BROWN_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.CYAN_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.GRAY_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.GREEN_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.LIGHT_BLUE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.LIGHT_GRAY_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.LIME_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.MAGENTA_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.ORANGE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.PINK_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.PURPLE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.RED_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.WHITE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.YELLOW_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.registerLootTable(Blocks.BLACK_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.BLUE_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.BROWN_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.CYAN_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.GRAY_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.GREEN_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.LIGHT_BLUE_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.LIGHT_GRAY_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.LIME_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.MAGENTA_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.ORANGE_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.PINK_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.PURPLE_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.RED_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.WHITE_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.YELLOW_BANNER, BlockLootTables::func_218559_g);
      this.registerLootTable(Blocks.PLAYER_HEAD, (p_218565_0_) -> {
         return LootTable.builder().addLootPool(func_218560_a(p_218565_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218565_0_).acceptFunction(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Owner", "SkullOwner")))));
      });
      this.registerLootTable(Blocks.field_226905_ma_, BlockLootTables::func_229436_h_);
      this.registerLootTable(Blocks.field_226906_mb_, BlockLootTables::func_229437_i_);
      this.registerLootTable(Blocks.BIRCH_LEAVES, (p_218473_0_) -> {
         return func_218540_a(p_218473_0_, Blocks.BIRCH_SAPLING, field_218579_g);
      });
      this.registerLootTable(Blocks.ACACIA_LEAVES, (p_218518_0_) -> {
         return func_218540_a(p_218518_0_, Blocks.ACACIA_SAPLING, field_218579_g);
      });
      this.registerLootTable(Blocks.JUNGLE_LEAVES, (p_218477_0_) -> {
         return func_218540_a(p_218477_0_, Blocks.JUNGLE_SAPLING, field_218580_h);
      });
      this.registerLootTable(Blocks.SPRUCE_LEAVES, (p_218500_0_) -> {
         return func_218540_a(p_218500_0_, Blocks.SPRUCE_SAPLING, field_218579_g);
      });
      this.registerLootTable(Blocks.OAK_LEAVES, (p_218506_0_) -> {
         return func_218526_b(p_218506_0_, Blocks.OAK_SAPLING, field_218579_g);
      });
      this.registerLootTable(Blocks.DARK_OAK_LEAVES, (p_218471_0_) -> {
         return func_218526_b(p_218471_0_, Blocks.DARK_OAK_SAPLING, field_218579_g);
      });
      ILootCondition.IBuilder ilootcondition$ibuilder = BlockStateProperty.builder(Blocks.BEETROOTS).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(BeetrootBlock.BEETROOT_AGE, 3));
      this.registerLootTable(Blocks.BEETROOTS, func_218541_a(Blocks.BEETROOTS, Items.BEETROOT, Items.BEETROOT_SEEDS, ilootcondition$ibuilder));
      ILootCondition.IBuilder ilootcondition$ibuilder1 = BlockStateProperty.builder(Blocks.WHEAT).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(CropsBlock.AGE, 7));
      this.registerLootTable(Blocks.WHEAT, func_218541_a(Blocks.WHEAT, Items.WHEAT, Items.WHEAT_SEEDS, ilootcondition$ibuilder1));
      ILootCondition.IBuilder ilootcondition$ibuilder2 = BlockStateProperty.builder(Blocks.CARROTS).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(CarrotBlock.AGE, 7));
      this.registerLootTable(Blocks.CARROTS, func_218552_a(Blocks.CARROTS, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(Items.CARROT))).addLootPool(LootPool.builder().acceptCondition(ilootcondition$ibuilder2).addEntry(ItemLootEntry.builder(Items.CARROT).acceptFunction(ApplyBonus.func_215870_a(Enchantments.FORTUNE, 0.5714286F, 3))))));
      ILootCondition.IBuilder ilootcondition$ibuilder3 = BlockStateProperty.builder(Blocks.POTATOES).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(PotatoBlock.AGE, 7));
      this.registerLootTable(Blocks.POTATOES, func_218552_a(Blocks.POTATOES, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(Items.POTATO))).addLootPool(LootPool.builder().acceptCondition(ilootcondition$ibuilder3).addEntry(ItemLootEntry.builder(Items.POTATO).acceptFunction(ApplyBonus.func_215870_a(Enchantments.FORTUNE, 0.5714286F, 3)))).addLootPool(LootPool.builder().acceptCondition(ilootcondition$ibuilder3).addEntry(ItemLootEntry.builder(Items.POISONOUS_POTATO).acceptCondition(RandomChance.builder(0.02F))))));
      this.registerLootTable(Blocks.SWEET_BERRY_BUSH, (p_218538_0_) -> {
         return func_218552_a(p_218538_0_, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(Blocks.SWEET_BERRY_BUSH).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(Items.SWEET_BERRIES)).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(2.0F, 3.0F))).acceptFunction(ApplyBonus.func_215871_b(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(Blocks.SWEET_BERRY_BUSH).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(Items.SWEET_BERRIES)).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 2.0F))).acceptFunction(ApplyBonus.func_215871_b(Enchantments.FORTUNE))));
      });
      this.registerLootTable(Blocks.BROWN_MUSHROOM_BLOCK, (p_229434_0_) -> {
         return func_218491_c(p_229434_0_, Blocks.BROWN_MUSHROOM);
      });
      this.registerLootTable(Blocks.RED_MUSHROOM_BLOCK, (p_229433_0_) -> {
         return func_218491_c(p_229433_0_, Blocks.RED_MUSHROOM);
      });
      this.registerLootTable(Blocks.COAL_ORE, (p_229432_0_) -> {
         return func_218476_a(p_229432_0_, Items.COAL);
      });
      this.registerLootTable(Blocks.EMERALD_ORE, (p_229431_0_) -> {
         return func_218476_a(p_229431_0_, Items.EMERALD);
      });
      this.registerLootTable(Blocks.NETHER_QUARTZ_ORE, (p_218554_0_) -> {
         return func_218476_a(p_218554_0_, Items.QUARTZ);
      });
      this.registerLootTable(Blocks.DIAMOND_ORE, (p_218568_0_) -> {
         return func_218476_a(p_218568_0_, Items.DIAMOND);
      });
      this.registerLootTable(Blocks.LAPIS_ORE, (p_218548_0_) -> {
         return func_218519_a(p_218548_0_, func_218552_a(p_218548_0_, ItemLootEntry.builder(Items.LAPIS_LAZULI).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(4.0F, 9.0F))).acceptFunction(ApplyBonus.func_215869_a(Enchantments.FORTUNE))));
      });
      this.registerLootTable(Blocks.COBWEB, (p_218487_0_) -> {
         return func_218535_c(p_218487_0_, func_218560_a(p_218487_0_, ItemLootEntry.builder(Items.STRING)));
      });
      this.registerLootTable(Blocks.DEAD_BUSH, (p_218525_0_) -> {
         return func_218511_b(p_218525_0_, func_218552_a(p_218525_0_, ItemLootEntry.builder(Items.STICK).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F)))));
      });
      this.registerLootTable(Blocks.SEAGRASS, BlockLootTables::func_218486_d);
      this.registerLootTable(Blocks.VINE, BlockLootTables::func_218486_d);
      this.registerLootTable(Blocks.TALL_SEAGRASS, func_218486_d(Blocks.SEAGRASS));
      this.registerLootTable(Blocks.LARGE_FERN, (p_218572_0_) -> {
         return func_218511_b(Blocks.FERN, ((StandaloneLootEntry.Builder)func_218560_a(p_218572_0_, ItemLootEntry.builder(Items.WHEAT_SEEDS)).acceptCondition(BlockStateProperty.builder(p_218572_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227193_a_(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))).acceptCondition(RandomChance.builder(0.125F)));
      });
      this.registerLootTable(Blocks.TALL_GRASS, func_218511_b(Blocks.GRASS, ((StandaloneLootEntry.Builder)func_218560_a(Blocks.TALL_GRASS, ItemLootEntry.builder(Items.WHEAT_SEEDS)).acceptCondition(BlockStateProperty.builder(Blocks.TALL_GRASS).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227193_a_(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))).acceptCondition(RandomChance.builder(0.125F))));
      this.registerLootTable(Blocks.MELON_STEM, (p_218550_0_) -> {
         return func_218475_b(p_218550_0_, Items.MELON_SEEDS);
      });
      this.registerLootTable(Blocks.ATTACHED_MELON_STEM, (p_218531_0_) -> {
         return func_229435_c_(p_218531_0_, Items.MELON_SEEDS);
      });
      this.registerLootTable(Blocks.PUMPKIN_STEM, (p_218467_0_) -> {
         return func_218475_b(p_218467_0_, Items.PUMPKIN_SEEDS);
      });
      this.registerLootTable(Blocks.ATTACHED_PUMPKIN_STEM, (p_218509_0_) -> {
         return func_229435_c_(p_218509_0_, Items.PUMPKIN_SEEDS);
      });
      this.registerLootTable(Blocks.CHORUS_FLOWER, (p_218512_0_) -> {
         return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(func_218560_a(p_218512_0_, ItemLootEntry.builder(p_218512_0_)).acceptCondition(EntityHasProperty.func_215998_a(LootContext.EntityTarget.THIS))));
      });
      this.registerLootTable(Blocks.FERN, BlockLootTables::func_218570_h);
      this.registerLootTable(Blocks.GRASS, BlockLootTables::func_218570_h);
      this.registerLootTable(Blocks.GLOWSTONE, (p_218496_0_) -> {
         return func_218519_a(p_218496_0_, func_218552_a(p_218496_0_, ItemLootEntry.builder(Items.GLOWSTONE_DUST).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(2.0F, 4.0F))).acceptFunction(ApplyBonus.func_215871_b(Enchantments.FORTUNE)).acceptFunction(LimitCount.func_215911_a(IntClamper.func_215843_a(1, 4)))));
      });
      this.registerLootTable(Blocks.MELON, (p_218532_0_) -> {
         return func_218519_a(p_218532_0_, func_218552_a(p_218532_0_, ItemLootEntry.builder(Items.MELON_SLICE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(3.0F, 7.0F))).acceptFunction(ApplyBonus.func_215871_b(Enchantments.FORTUNE)).acceptFunction(LimitCount.func_215911_a(IntClamper.func_215851_b(9)))));
      });
      this.registerLootTable(Blocks.REDSTONE_ORE, (p_218464_0_) -> {
         return func_218519_a(p_218464_0_, func_218552_a(p_218464_0_, ItemLootEntry.builder(Items.REDSTONE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(4.0F, 5.0F))).acceptFunction(ApplyBonus.func_215871_b(Enchantments.FORTUNE))));
      });
      this.registerLootTable(Blocks.SEA_LANTERN, (p_218571_0_) -> {
         return func_218519_a(p_218571_0_, func_218552_a(p_218571_0_, ItemLootEntry.builder(Items.PRISMARINE_CRYSTALS).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(2.0F, 3.0F))).acceptFunction(ApplyBonus.func_215871_b(Enchantments.FORTUNE)).acceptFunction(LimitCount.func_215911_a(IntClamper.func_215843_a(1, 5)))));
      });
      this.registerLootTable(Blocks.NETHER_WART, (p_218553_0_) -> {
         return LootTable.builder().addLootPool(func_218552_a(p_218553_0_, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.NETHER_WART).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(2.0F, 4.0F)).acceptCondition(BlockStateProperty.builder(p_218553_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(NetherWartBlock.AGE, 3)))).acceptFunction(ApplyBonus.func_215871_b(Enchantments.FORTUNE).acceptCondition(BlockStateProperty.builder(p_218553_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(NetherWartBlock.AGE, 3)))))));
      });
      this.registerLootTable(Blocks.SNOW, (p_218485_0_) -> {
         return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(EntityHasProperty.func_215998_a(LootContext.EntityTarget.THIS)).addEntry(AlternativesLootEntry.func_216149_a(AlternativesLootEntry.func_216149_a(ItemLootEntry.builder(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 1))), ((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 2)))).acceptFunction(SetCount.func_215932_a(ConstantRange.of(2))), ((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 3)))).acceptFunction(SetCount.func_215932_a(ConstantRange.of(3))), ((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 4)))).acceptFunction(SetCount.func_215932_a(ConstantRange.of(4))), ((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 5)))).acceptFunction(SetCount.func_215932_a(ConstantRange.of(5))), ((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 6)))).acceptFunction(SetCount.func_215932_a(ConstantRange.of(6))), ((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 7)))).acceptFunction(SetCount.func_215932_a(ConstantRange.of(7))), ItemLootEntry.builder(Items.SNOWBALL).acceptFunction(SetCount.func_215932_a(ConstantRange.of(8)))).acceptCondition(field_218574_b), AlternativesLootEntry.func_216149_a(ItemLootEntry.builder(Blocks.SNOW).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 1))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.func_215932_a(ConstantRange.of(2))).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 2))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.func_215932_a(ConstantRange.of(3))).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 3))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.func_215932_a(ConstantRange.of(4))).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 4))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.func_215932_a(ConstantRange.of(5))).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 5))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.func_215932_a(ConstantRange.of(6))).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 6))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.func_215932_a(ConstantRange.of(7))).acceptCondition(BlockStateProperty.builder(p_218485_0_).func_227567_a_(StatePropertiesPredicate.Builder.func_227191_a_().func_227192_a_(SnowBlock.LAYERS, 7))), ItemLootEntry.builder(Blocks.SNOW_BLOCK)))));
      });
      this.registerLootTable(Blocks.GRAVEL, (p_218533_0_) -> {
         return func_218519_a(p_218533_0_, func_218560_a(p_218533_0_, ((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.FLINT).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F))).func_216080_a(ItemLootEntry.builder(p_218533_0_))));
      });
      this.registerLootTable(Blocks.CAMPFIRE, (p_218469_0_) -> {
         return func_218519_a(p_218469_0_, func_218560_a(p_218469_0_, ItemLootEntry.builder(Items.CHARCOAL).acceptFunction(SetCount.func_215932_a(ConstantRange.of(2)))));
      });
      this.func_218466_b(Blocks.GLASS);
      this.func_218466_b(Blocks.WHITE_STAINED_GLASS);
      this.func_218466_b(Blocks.ORANGE_STAINED_GLASS);
      this.func_218466_b(Blocks.MAGENTA_STAINED_GLASS);
      this.func_218466_b(Blocks.LIGHT_BLUE_STAINED_GLASS);
      this.func_218466_b(Blocks.YELLOW_STAINED_GLASS);
      this.func_218466_b(Blocks.LIME_STAINED_GLASS);
      this.func_218466_b(Blocks.PINK_STAINED_GLASS);
      this.func_218466_b(Blocks.GRAY_STAINED_GLASS);
      this.func_218466_b(Blocks.LIGHT_GRAY_STAINED_GLASS);
      this.func_218466_b(Blocks.CYAN_STAINED_GLASS);
      this.func_218466_b(Blocks.PURPLE_STAINED_GLASS);
      this.func_218466_b(Blocks.BLUE_STAINED_GLASS);
      this.func_218466_b(Blocks.BROWN_STAINED_GLASS);
      this.func_218466_b(Blocks.GREEN_STAINED_GLASS);
      this.func_218466_b(Blocks.RED_STAINED_GLASS);
      this.func_218466_b(Blocks.BLACK_STAINED_GLASS);
      this.func_218466_b(Blocks.GLASS_PANE);
      this.func_218466_b(Blocks.WHITE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.ORANGE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.MAGENTA_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.YELLOW_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.LIME_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.PINK_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.GRAY_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.CYAN_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.PURPLE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.BLUE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.BROWN_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.GREEN_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.RED_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.BLACK_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.ICE);
      this.func_218466_b(Blocks.PACKED_ICE);
      this.func_218466_b(Blocks.BLUE_ICE);
      this.func_218466_b(Blocks.TURTLE_EGG);
      this.func_218466_b(Blocks.MUSHROOM_STEM);
      this.func_218466_b(Blocks.DEAD_TUBE_CORAL);
      this.func_218466_b(Blocks.DEAD_BRAIN_CORAL);
      this.func_218466_b(Blocks.DEAD_BUBBLE_CORAL);
      this.func_218466_b(Blocks.DEAD_FIRE_CORAL);
      this.func_218466_b(Blocks.DEAD_HORN_CORAL);
      this.func_218466_b(Blocks.TUBE_CORAL);
      this.func_218466_b(Blocks.BRAIN_CORAL);
      this.func_218466_b(Blocks.BUBBLE_CORAL);
      this.func_218466_b(Blocks.FIRE_CORAL);
      this.func_218466_b(Blocks.HORN_CORAL);
      this.func_218466_b(Blocks.DEAD_TUBE_CORAL_FAN);
      this.func_218466_b(Blocks.DEAD_BRAIN_CORAL_FAN);
      this.func_218466_b(Blocks.DEAD_BUBBLE_CORAL_FAN);
      this.func_218466_b(Blocks.DEAD_FIRE_CORAL_FAN);
      this.func_218466_b(Blocks.DEAD_HORN_CORAL_FAN);
      this.func_218466_b(Blocks.TUBE_CORAL_FAN);
      this.func_218466_b(Blocks.BRAIN_CORAL_FAN);
      this.func_218466_b(Blocks.BUBBLE_CORAL_FAN);
      this.func_218466_b(Blocks.FIRE_CORAL_FAN);
      this.func_218466_b(Blocks.HORN_CORAL_FAN);
      this.func_218564_a(Blocks.INFESTED_STONE, Blocks.STONE);
      this.func_218564_a(Blocks.INFESTED_COBBLESTONE, Blocks.COBBLESTONE);
      this.func_218564_a(Blocks.INFESTED_STONE_BRICKS, Blocks.STONE_BRICKS);
      this.func_218564_a(Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS);
      this.func_218564_a(Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
      this.func_218564_a(Blocks.INFESTED_CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
      this.registerLootTable(Blocks.CAKE, func_218482_a());
      this.registerLootTable(Blocks.FROSTED_ICE, func_218482_a());
      this.registerLootTable(Blocks.SPAWNER, func_218482_a());
   }

   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
      this.addTables();
      Set<ResourceLocation> set = Sets.newHashSet();

      for(Block block : getKnownBlocks()) {
         ResourceLocation resourcelocation = block.getLootTable();
         if (resourcelocation != LootTables.EMPTY && set.add(resourcelocation)) {
            LootTable.Builder loottable$builder = this.field_218581_i.remove(resourcelocation);
            if (loottable$builder == null) {
               throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation, Registry.BLOCK.getKey(block)));
            }

            p_accept_1_.accept(resourcelocation, loottable$builder);
         }
      }

      if (!this.field_218581_i.isEmpty()) {
         throw new IllegalStateException("Created block loot tables for non-blocks: " + this.field_218581_i.keySet());
      }
   }

   protected Iterable<Block> getKnownBlocks() {
       return Registry.BLOCK;
   }

   public void func_218547_a(Block p_218547_1_) {
      this.registerLootTable(p_218547_1_, (p_229438_0_) -> {
         return func_218523_c(((FlowerPotBlock)p_229438_0_).func_220276_d());
      });
   }

   public void func_218564_a(Block p_218564_1_, Block p_218564_2_) {
      this.registerLootTable(p_218564_1_, func_218561_b(p_218564_2_));
   }

   public void func_218493_a(Block p_218493_1_, IItemProvider p_218493_2_) {
      this.registerLootTable(p_218493_1_, func_218546_a(p_218493_2_));
   }

   public void func_218466_b(Block p_218466_1_) {
      this.func_218564_a(p_218466_1_, p_218466_1_);
   }

   public void func_218492_c(Block p_218492_1_) {
      this.func_218493_a(p_218492_1_, p_218492_1_);
   }

   protected void registerLootTable(Block p_218522_1_, Function<Block, LootTable.Builder> p_218522_2_) {
      this.registerLootTable(p_218522_1_, p_218522_2_.apply(p_218522_1_));
   }

   protected void registerLootTable(Block p_218507_1_, LootTable.Builder p_218507_2_) {
      this.field_218581_i.put(p_218507_1_.getLootTable(), p_218507_2_);
   }
}