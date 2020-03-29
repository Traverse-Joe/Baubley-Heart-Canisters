package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.state.IProperty;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemParser {
   public static final SimpleCommandExceptionType ITEM_TAGS_NOT_ALLOWED = new SimpleCommandExceptionType(new TranslationTextComponent("argument.item.tag.disallowed"));
   public static final DynamicCommandExceptionType ITEM_BAD_ID = new DynamicCommandExceptionType((p_208696_0_) -> {
      return new TranslationTextComponent("argument.item.id.invalid", p_208696_0_);
   });
   private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> field_197334_b = SuggestionsBuilder::buildFuture;
   private final StringReader reader;
   private final boolean allowTags;
   private final Map<IProperty<?>, Comparable<?>> field_197336_d = Maps.newHashMap();
   private Item item;
   @Nullable
   private CompoundNBT nbt;
   private ResourceLocation tag = new ResourceLocation("");
   private int field_201956_j;
   private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> field_197339_g = field_197334_b;

   public ItemParser(StringReader readerIn, boolean allowTags) {
      this.reader = readerIn;
      this.allowTags = allowTags;
   }

   public Item getItem() {
      return this.item;
   }

   @Nullable
   public CompoundNBT getNbt() {
      return this.nbt;
   }

   public ResourceLocation getTag() {
      return this.tag;
   }

   public void readItem() throws CommandSyntaxException {
      int i = this.reader.getCursor();
      ResourceLocation resourcelocation = ResourceLocation.read(this.reader);
      this.item = Registry.ITEM.getValue(resourcelocation).orElseThrow(() -> {
         this.reader.setCursor(i);
         return ITEM_BAD_ID.createWithContext(this.reader, resourcelocation.toString());
      });
   }

   public void readTag() throws CommandSyntaxException {
      if (!this.allowTags) {
         throw ITEM_TAGS_NOT_ALLOWED.create();
      } else {
         this.field_197339_g = this::func_201955_c;
         this.reader.expect('#');
         this.field_201956_j = this.reader.getCursor();
         this.tag = ResourceLocation.read(this.reader);
      }
   }

   public void readNBT() throws CommandSyntaxException {
      this.nbt = (new JsonToNBT(this.reader)).readStruct();
   }

   public ItemParser parse() throws CommandSyntaxException {
      this.field_197339_g = this::func_197331_c;
      if (this.reader.canRead() && this.reader.peek() == '#') {
         this.readTag();
      } else {
         this.readItem();
         this.field_197339_g = this::func_197328_b;
      }

      if (this.reader.canRead() && this.reader.peek() == '{') {
         this.field_197339_g = field_197334_b;
         this.readNBT();
      }

      return this;
   }

   private CompletableFuture<Suggestions> func_197328_b(SuggestionsBuilder p_197328_1_) {
      if (p_197328_1_.getRemaining().isEmpty()) {
         p_197328_1_.suggest(String.valueOf('{'));
      }

      return p_197328_1_.buildFuture();
   }

   private CompletableFuture<Suggestions> func_201955_c(SuggestionsBuilder p_201955_1_) {
      return ISuggestionProvider.suggestIterable(ItemTags.getCollection().getRegisteredTags(), p_201955_1_.createOffset(this.field_201956_j));
   }

   private CompletableFuture<Suggestions> func_197331_c(SuggestionsBuilder p_197331_1_) {
      if (this.allowTags) {
         ISuggestionProvider.suggestIterable(ItemTags.getCollection().getRegisteredTags(), p_197331_1_, String.valueOf('#'));
      }

      return ISuggestionProvider.suggestIterable(Registry.ITEM.keySet(), p_197331_1_);
   }

   public CompletableFuture<Suggestions> func_197329_a(SuggestionsBuilder p_197329_1_) {
      return this.field_197339_g.apply(p_197329_1_.createOffset(this.reader.getCursor()));
   }
}