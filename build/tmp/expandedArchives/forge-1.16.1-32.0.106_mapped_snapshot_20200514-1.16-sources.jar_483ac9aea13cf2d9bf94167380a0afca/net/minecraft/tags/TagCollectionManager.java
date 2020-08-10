package net.minecraft.tags;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

public class TagCollectionManager {
   private static volatile TagCollectionManager field_232918_a_ = new TagCollectionManager(BlockTags.getCollection(), ItemTags.getCollection(), FluidTags.getCollection(), EntityTypeTags.getCollection());
   private final TagCollection<Block> field_232919_b_;
   private final TagCollection<Item> field_232920_c_;
   private final TagCollection<Fluid> field_232921_d_;
   private final TagCollection<EntityType<?>> field_232922_e_;

   private TagCollectionManager(TagCollection<Block> p_i231429_1_, TagCollection<Item> p_i231429_2_, TagCollection<Fluid> p_i231429_3_, TagCollection<EntityType<?>> p_i231429_4_) {
      this.field_232919_b_ = p_i231429_1_;
      this.field_232920_c_ = p_i231429_2_;
      this.field_232921_d_ = p_i231429_3_;
      this.field_232922_e_ = p_i231429_4_;
   }

   public TagCollection<Block> func_232923_a_() {
      return this.field_232919_b_;
   }

   public TagCollection<Item> func_232925_b_() {
      return this.field_232920_c_;
   }

   public TagCollection<Fluid> func_232926_c_() {
      return this.field_232921_d_;
   }

   public TagCollection<EntityType<?>> func_232927_d_() {
      return this.field_232922_e_;
   }

   public static TagCollectionManager func_232928_e_() {
      return field_232918_a_;
   }

   public static void func_232924_a_(TagCollection<Block> p_232924_0_, TagCollection<Item> p_232924_1_, TagCollection<Fluid> p_232924_2_, TagCollection<EntityType<?>> p_232924_3_) {
      field_232918_a_ = new TagCollectionManager(p_232924_0_, p_232924_1_, p_232924_2_, p_232924_3_);
   }
}