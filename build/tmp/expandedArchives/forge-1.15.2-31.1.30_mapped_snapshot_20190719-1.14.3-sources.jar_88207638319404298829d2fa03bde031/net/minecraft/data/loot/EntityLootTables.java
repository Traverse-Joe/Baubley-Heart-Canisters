package net.minecraft.data.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.EmptyLootEntry;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraft.world.storage.loot.TagLootEntry;
import net.minecraft.world.storage.loot.conditions.DamageSourceProperties;
import net.minecraft.world.storage.loot.conditions.EntityHasProperty;
import net.minecraft.world.storage.loot.conditions.KilledByPlayer;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.conditions.RandomChanceWithLooting;
import net.minecraft.world.storage.loot.functions.LootingEnchantBonus;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetNBT;
import net.minecraft.world.storage.loot.functions.Smelt;

public class EntityLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   protected static final EntityPredicate.Builder field_218586_a = EntityPredicate.Builder.create().func_217987_a(EntityFlagsPredicate.Builder.create().onFire(true).build());
   private static final Set<EntityType<?>> field_222944_b = ImmutableSet.of(EntityType.PLAYER, EntityType.ARMOR_STAND, EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER);
   private final Map<ResourceLocation, LootTable.Builder> field_218587_b = Maps.newHashMap();

   private static LootTable.Builder func_218583_a(IItemProvider p_218583_0_) {
      return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(p_218583_0_))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(TableLootEntry.builder(EntityType.SHEEP.getLootTable())));
   }

   protected void addTables() {
      this.func_218582_a(EntityType.ARMOR_STAND, LootTable.builder());
      this.func_218582_a(EntityType.BAT, LootTable.builder());
      this.func_218582_a(EntityType.field_226289_e_, LootTable.builder());
      this.func_218582_a(EntityType.BLAZE, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BLAZE_ROD).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).acceptCondition(KilledByPlayer.builder())));
      this.func_218582_a(EntityType.CAT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.STRING).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))))));
      this.func_218582_a(EntityType.CAVE_SPIDER, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.STRING).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.SPIDER_EYE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(-1.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).acceptCondition(KilledByPlayer.builder())));
      this.func_218582_a(EntityType.CHICKEN, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.FEATHER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.CHICKEN).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.COD, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.COD).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BONE_MEAL)).acceptCondition(RandomChance.builder(0.05F))));
      this.func_218582_a(EntityType.COW, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.LEATHER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BEEF).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 3.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.CREEPER, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.GUNPOWDER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().addEntry(TagLootEntry.func_216176_b(ItemTags.MUSIC_DISCS)).acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.create().func_217989_a(EntityTypeTags.SKELETONS)))));
      this.func_218582_a(EntityType.DOLPHIN, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.COD).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a))))));
      this.func_218582_a(EntityType.DONKEY, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.LEATHER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.DROWNED, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.GOLD_INGOT)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.05F, 0.01F))));
      this.func_218582_a(EntityType.ELDER_GUARDIAN, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.PRISMARINE_SHARD).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.COD).weight(3).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a)))).addEntry(ItemLootEntry.builder(Items.PRISMARINE_CRYSTALS).weight(2).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).addEntry(EmptyLootEntry.func_216167_a())).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.WET_SPONGE)).acceptCondition(KilledByPlayer.builder())).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_FISH)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.025F, 0.01F))));
      this.func_218582_a(EntityType.ENDER_DRAGON, LootTable.builder());
      this.func_218582_a(EntityType.ENDERMAN, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ENDER_PEARL).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.ENDERMITE, LootTable.builder());
      this.func_218582_a(EntityType.EVOKER, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.TOTEM_OF_UNDYING))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.EMERALD).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).acceptCondition(KilledByPlayer.builder())));
      this.func_218582_a(EntityType.FOX, LootTable.builder());
      this.func_218582_a(EntityType.GHAST, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.GHAST_TEAR).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.GUNPOWDER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.GIANT, LootTable.builder());
      this.func_218582_a(EntityType.GUARDIAN, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.PRISMARINE_SHARD).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.COD).weight(2).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a)))).addEntry(ItemLootEntry.builder(Items.PRISMARINE_CRYSTALS).weight(2).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).addEntry(EmptyLootEntry.func_216167_a())).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_FISH)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.025F, 0.01F))));
      this.func_218582_a(EntityType.HORSE, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.LEATHER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.HUSK, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.IRON_INGOT)).addEntry(ItemLootEntry.builder(Items.CARROT)).addEntry(ItemLootEntry.builder(Items.POTATO)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.025F, 0.01F))));
      this.func_218582_a(EntityType.RAVAGER, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.SADDLE).acceptFunction(SetCount.func_215932_a(ConstantRange.of(1))))));
      this.func_218582_a(EntityType.ILLUSIONER, LootTable.builder());
      this.func_218582_a(EntityType.IRON_GOLEM, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.POPPY).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.IRON_INGOT).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(3.0F, 5.0F))))));
      this.func_218582_a(EntityType.LLAMA, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.LEATHER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.MAGMA_CUBE, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.MAGMA_CREAM).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(-2.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.MULE, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.LEATHER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.MOOSHROOM, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.LEATHER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BEEF).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 3.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.OCELOT, LootTable.builder());
      this.func_218582_a(EntityType.PANDA, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.BAMBOO).acceptFunction(SetCount.func_215932_a(ConstantRange.of(1))))));
      this.func_218582_a(EntityType.PARROT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.FEATHER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.PHANTOM, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.PHANTOM_MEMBRANE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).acceptCondition(KilledByPlayer.builder())));
      this.func_218582_a(EntityType.PIG, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.PORKCHOP).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 3.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.PILLAGER, LootTable.builder());
      this.func_218582_a(EntityType.PLAYER, LootTable.builder());
      this.func_218582_a(EntityType.POLAR_BEAR, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.COD).weight(3).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).addEntry(ItemLootEntry.builder(Items.SALMON).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.PUFFERFISH, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.PUFFERFISH).acceptFunction(SetCount.func_215932_a(ConstantRange.of(1))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BONE_MEAL)).acceptCondition(RandomChance.builder(0.05F))));
      this.func_218582_a(EntityType.RABBIT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.RABBIT_HIDE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.RABBIT).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.RABBIT_FOOT)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.1F, 0.03F))));
      this.func_218582_a(EntityType.SALMON, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.SALMON).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BONE_MEAL)).acceptCondition(RandomChance.builder(0.05F))));
      this.func_218582_a(EntityType.SHEEP, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.MUTTON).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 2.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.func_215999_a(LootContext.EntityTarget.THIS, field_218586_a))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_BLACK, func_218583_a(Blocks.BLACK_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_BLUE, func_218583_a(Blocks.BLUE_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_BROWN, func_218583_a(Blocks.BROWN_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_CYAN, func_218583_a(Blocks.CYAN_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_GRAY, func_218583_a(Blocks.GRAY_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_GREEN, func_218583_a(Blocks.GREEN_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_LIGHT_BLUE, func_218583_a(Blocks.LIGHT_BLUE_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_LIGHT_GRAY, func_218583_a(Blocks.LIGHT_GRAY_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_LIME, func_218583_a(Blocks.LIME_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_MAGENTA, func_218583_a(Blocks.MAGENTA_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_ORANGE, func_218583_a(Blocks.ORANGE_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_PINK, func_218583_a(Blocks.PINK_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_PURPLE, func_218583_a(Blocks.PURPLE_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_RED, func_218583_a(Blocks.RED_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_WHITE, func_218583_a(Blocks.WHITE_WOOL));
      this.func_218585_a(LootTables.ENTITIES_SHEEP_YELLOW, func_218583_a(Blocks.YELLOW_WOOL));
      this.func_218582_a(EntityType.SHULKER, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.SHULKER_SHELL)).acceptCondition(RandomChanceWithLooting.builder(0.5F, 0.0625F))));
      this.func_218582_a(EntityType.SILVERFISH, LootTable.builder());
      this.func_218582_a(EntityType.SKELETON, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ARROW).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BONE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.SKELETON_HORSE, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BONE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.SLIME, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.SLIME_BALL).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.SNOW_GOLEM, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.SNOWBALL).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 15.0F))))));
      this.func_218582_a(EntityType.SPIDER, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.STRING).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.SPIDER_EYE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(-1.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).acceptCondition(KilledByPlayer.builder())));
      this.func_218582_a(EntityType.SQUID, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.INK_SAC).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 3.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.STRAY, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ARROW).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BONE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)).func_216072_a(1)).acceptFunction(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218584_0_) -> {
         p_218584_0_.putString("Potion", "minecraft:slowness");
      })))).acceptCondition(KilledByPlayer.builder())));
      this.func_218582_a(EntityType.TRADER_LLAMA, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.LEATHER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.TROPICAL_FISH, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.TROPICAL_FISH).acceptFunction(SetCount.func_215932_a(ConstantRange.of(1))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BONE_MEAL)).acceptCondition(RandomChance.builder(0.05F))));
      this.func_218582_a(EntityType.TURTLE, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.SEAGRASS).weight(3).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BOWL)).acceptCondition(DamageSourceProperties.builder(DamageSourcePredicate.Builder.damageType().func_217950_h(true)))));
      this.func_218582_a(EntityType.VEX, LootTable.builder());
      this.func_218582_a(EntityType.VILLAGER, LootTable.builder());
      this.func_218582_a(EntityType.WANDERING_TRADER, LootTable.builder());
      this.func_218582_a(EntityType.VINDICATOR, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.EMERALD).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).acceptCondition(KilledByPlayer.builder())));
      this.func_218582_a(EntityType.WITCH, LootTable.builder().addLootPool(LootPool.builder().rolls(RandomValueRange.func_215837_a(1.0F, 3.0F)).addEntry(ItemLootEntry.builder(Items.GLOWSTONE_DUST).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).addEntry(ItemLootEntry.builder(Items.SUGAR).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).addEntry(ItemLootEntry.builder(Items.REDSTONE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).addEntry(ItemLootEntry.builder(Items.SPIDER_EYE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).addEntry(ItemLootEntry.builder(Items.GLASS_BOTTLE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).addEntry(ItemLootEntry.builder(Items.GUNPOWDER).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F)))).addEntry(ItemLootEntry.builder(Items.STICK).weight(2).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.WITHER, LootTable.builder());
      this.func_218582_a(EntityType.WITHER_SKELETON, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.COAL).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(-1.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BONE).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.WITHER_SKELETON_SKULL)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.025F, 0.01F))));
      this.func_218582_a(EntityType.WOLF, LootTable.builder());
      this.func_218582_a(EntityType.ZOMBIE, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.IRON_INGOT)).addEntry(ItemLootEntry.builder(Items.CARROT)).addEntry(ItemLootEntry.builder(Items.POTATO)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.025F, 0.01F))));
      this.func_218582_a(EntityType.ZOMBIE_HORSE, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))));
      this.func_218582_a(EntityType.ZOMBIE_PIGMAN, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.GOLD_NUGGET).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.GOLD_INGOT)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.025F, 0.01F))));
      this.func_218582_a(EntityType.ZOMBIE_VILLAGER, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.func_215915_a(RandomValueRange.func_215837_a(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.IRON_INGOT)).addEntry(ItemLootEntry.builder(Items.CARROT)).addEntry(ItemLootEntry.builder(Items.POTATO)).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.025F, 0.01F))));
   }

   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
      this.addTables();
      Set<ResourceLocation> set = Sets.newHashSet();
      Iterator iterator = getKnownEntities().iterator();

      EntityType<?> entitytype;
      ResourceLocation resourcelocation;
      while(true) {
         if (!iterator.hasNext()) {
            this.field_218587_b.forEach(p_accept_1_::accept);
            return;
         }

         entitytype = (EntityType)iterator.next();
         resourcelocation = entitytype.getLootTable();
         if (isNonLiving(entitytype)) {
            if (resourcelocation != LootTables.EMPTY && this.field_218587_b.remove(resourcelocation) != null) {
               break;
            }
         } else if (resourcelocation != LootTables.EMPTY && set.add(resourcelocation)) {
            LootTable.Builder loottable$builder = this.field_218587_b.remove(resourcelocation);
            if (loottable$builder == null) {
               throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation, Registry.ENTITY_TYPE.getKey(entitytype)));
            }

            p_accept_1_.accept(resourcelocation, loottable$builder);
         }
      }

      throw new IllegalStateException(String.format("Weird loottable '%s' for '%s', not a LivingEntity so should not have loot", resourcelocation, Registry.ENTITY_TYPE.getKey(entitytype)));
   }

   protected Iterable<EntityType<?>> getKnownEntities() {
      return Registry.ENTITY_TYPE;
   }

   protected boolean isNonLiving(EntityType<?> entitytype) {
      return !field_222944_b.contains(entitytype) && entitytype.getClassification() == EntityClassification.MISC; 
   }

   protected void func_218582_a(EntityType<?> p_218582_1_, LootTable.Builder p_218582_2_) {
      this.func_218585_a(p_218582_1_.getLootTable(), p_218582_2_);
   }

   protected void func_218585_a(ResourceLocation p_218585_1_, LootTable.Builder p_218585_2_) {
      this.field_218587_b.put(p_218585_1_, p_218585_2_);
   }
}