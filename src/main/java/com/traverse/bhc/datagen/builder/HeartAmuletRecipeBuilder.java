package com.traverse.bhc.datagen.builder;

import com.traverse.bhc.common.recipes.HeartAmuletRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HeartAmuletRecipeBuilder implements RecipeBuilder {
	private final HolderGetter<Item> items;
	private final RecipeCategory category;
	private final ItemStackTemplate result;
	private final List<Ingredient> ingredients = new ArrayList<>();
	private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
	private @Nullable String group;

	private HeartAmuletRecipeBuilder(HolderGetter<Item> items, RecipeCategory category, ItemStackTemplate result) {
		this.items = items;
		this.category = category;
		this.result = result;
	}

	public static HeartAmuletRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, ItemLike item) {
		return shapeless(items, category, item, 1);
	}

	public static HeartAmuletRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, ItemLike item, int count) {
		return new HeartAmuletRecipeBuilder(items, category, new ItemStackTemplate(item.asItem(), count));
	}

	public HeartAmuletRecipeBuilder requires(TagKey<Item> tag) {
		return this.requires(Ingredient.of(this.items.getOrThrow(tag)));
	}

	public HeartAmuletRecipeBuilder requires(ItemLike item) {
		return this.requires((ItemLike)item, 1);
	}

	public HeartAmuletRecipeBuilder requires(ItemLike item, int count) {
		for(int i = 0; i < count; ++i) {
			this.requires(Ingredient.of(item));
		}

		return this;
	}

	public HeartAmuletRecipeBuilder requires(Ingredient ingredient) {
		return this.requires((Ingredient)ingredient, 1);
	}

	public HeartAmuletRecipeBuilder requires(Ingredient ingredient, int count) {
		for(int i = 0; i < count; ++i) {
			this.ingredients.add(ingredient);
		}

		return this;
	}

	public HeartAmuletRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		this.advancementBuilder.unlockedBy(name, criterion);
		return this;
	}

	public HeartAmuletRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(this.result);
	}

	public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
		ShapelessRecipe recipe = new HeartAmuletRecipe(this.group, this.result, this.ingredients);
		output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
	}
}
