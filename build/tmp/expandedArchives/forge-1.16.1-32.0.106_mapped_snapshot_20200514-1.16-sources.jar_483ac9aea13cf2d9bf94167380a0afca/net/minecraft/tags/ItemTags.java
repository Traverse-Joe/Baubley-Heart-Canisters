package net.minecraft.tags;

import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemTags {
   private static final TagRegistry<Item> collection = new TagRegistry<>();
   public static final ITag.INamedTag<Item> WOOL = makeWrapperTag("wool");
   public static final ITag.INamedTag<Item> PLANKS = makeWrapperTag("planks");
   public static final ITag.INamedTag<Item> STONE_BRICKS = makeWrapperTag("stone_bricks");
   public static final ITag.INamedTag<Item> WOODEN_BUTTONS = makeWrapperTag("wooden_buttons");
   public static final ITag.INamedTag<Item> BUTTONS = makeWrapperTag("buttons");
   public static final ITag.INamedTag<Item> CARPETS = makeWrapperTag("carpets");
   public static final ITag.INamedTag<Item> WOODEN_DOORS = makeWrapperTag("wooden_doors");
   public static final ITag.INamedTag<Item> WOODEN_STAIRS = makeWrapperTag("wooden_stairs");
   public static final ITag.INamedTag<Item> WOODEN_SLABS = makeWrapperTag("wooden_slabs");
   public static final ITag.INamedTag<Item> WOODEN_FENCES = makeWrapperTag("wooden_fences");
   public static final ITag.INamedTag<Item> WOODEN_PRESSURE_PLATES = makeWrapperTag("wooden_pressure_plates");
   public static final ITag.INamedTag<Item> WOODEN_TRAPDOORS = makeWrapperTag("wooden_trapdoors");
   public static final ITag.INamedTag<Item> DOORS = makeWrapperTag("doors");
   public static final ITag.INamedTag<Item> SAPLINGS = makeWrapperTag("saplings");
   public static final ITag.INamedTag<Item> field_232912_o_ = makeWrapperTag("logs_that_burn");
   public static final ITag.INamedTag<Item> LOGS = makeWrapperTag("logs");
   public static final ITag.INamedTag<Item> DARK_OAK_LOGS = makeWrapperTag("dark_oak_logs");
   public static final ITag.INamedTag<Item> OAK_LOGS = makeWrapperTag("oak_logs");
   public static final ITag.INamedTag<Item> BIRCH_LOGS = makeWrapperTag("birch_logs");
   public static final ITag.INamedTag<Item> ACACIA_LOGS = makeWrapperTag("acacia_logs");
   public static final ITag.INamedTag<Item> JUNGLE_LOGS = makeWrapperTag("jungle_logs");
   public static final ITag.INamedTag<Item> SPRUCE_LOGS = makeWrapperTag("spruce_logs");
   public static final ITag.INamedTag<Item> field_232913_w_ = makeWrapperTag("crimson_stems");
   public static final ITag.INamedTag<Item> field_232914_x_ = makeWrapperTag("warped_stems");
   public static final ITag.INamedTag<Item> BANNERS = makeWrapperTag("banners");
   public static final ITag.INamedTag<Item> SAND = makeWrapperTag("sand");
   public static final ITag.INamedTag<Item> STAIRS = makeWrapperTag("stairs");
   public static final ITag.INamedTag<Item> SLABS = makeWrapperTag("slabs");
   public static final ITag.INamedTag<Item> WALLS = makeWrapperTag("walls");
   public static final ITag.INamedTag<Item> ANVIL = makeWrapperTag("anvil");
   public static final ITag.INamedTag<Item> RAILS = makeWrapperTag("rails");
   public static final ITag.INamedTag<Item> LEAVES = makeWrapperTag("leaves");
   public static final ITag.INamedTag<Item> TRAPDOORS = makeWrapperTag("trapdoors");
   public static final ITag.INamedTag<Item> SMALL_FLOWERS = makeWrapperTag("small_flowers");
   public static final ITag.INamedTag<Item> BEDS = makeWrapperTag("beds");
   public static final ITag.INamedTag<Item> FENCES = makeWrapperTag("fences");
   public static final ITag.INamedTag<Item> TALL_FLOWERS = makeWrapperTag("tall_flowers");
   public static final ITag.INamedTag<Item> FLOWERS = makeWrapperTag("flowers");
   public static final ITag.INamedTag<Item> field_232902_M_ = makeWrapperTag("piglin_repellents");
   public static final ITag.INamedTag<Item> field_232903_N_ = makeWrapperTag("piglin_loved");
   public static final ITag.INamedTag<Item> field_232904_O_ = makeWrapperTag("gold_ores");
   public static final ITag.INamedTag<Item> field_232905_P_ = makeWrapperTag("non_flammable_wood");
   public static final ITag.INamedTag<Item> field_232906_Q_ = makeWrapperTag("soul_fire_base_blocks");
   public static final ITag.INamedTag<Item> BOATS = makeWrapperTag("boats");
   public static final ITag.INamedTag<Item> FISHES = makeWrapperTag("fishes");
   public static final ITag.INamedTag<Item> SIGNS = makeWrapperTag("signs");
   public static final ITag.INamedTag<Item> MUSIC_DISCS = makeWrapperTag("music_discs");
   public static final ITag.INamedTag<Item> field_232907_V_ = makeWrapperTag("creeper_drop_music_discs");
   public static final ITag.INamedTag<Item> COALS = makeWrapperTag("coals");
   public static final ITag.INamedTag<Item> ARROWS = makeWrapperTag("arrows");
   public static final ITag.INamedTag<Item> LECTERN_BOOKS = makeWrapperTag("lectern_books");
   public static final ITag.INamedTag<Item> field_232908_Z_ = makeWrapperTag("beacon_payment_items");
   public static final ITag.INamedTag<Item> field_232909_aa_ = makeWrapperTag("stone_tool_materials");
   public static final ITag.INamedTag<Item> field_232910_ab_ = makeWrapperTag("furnace_materials");

   public static ITag.INamedTag<Item> makeWrapperTag(String p_199901_0_) {
      return collection.func_232937_a_(p_199901_0_);
   }

   public static void setCollection(TagCollection<Item> collectionIn) {
      collection.func_232935_a_(collectionIn);
   }

   @OnlyIn(Dist.CLIENT)
   public static void func_232915_a_() {
      collection.func_232932_a_();
   }

   public static TagCollection<Item> getCollection() {
      return collection.func_232939_b_();
   }

   public static Set<ResourceLocation> func_232917_b_(TagCollection<Item> p_232917_0_) {
      return collection.func_232940_b_(p_232917_0_);
   }
}