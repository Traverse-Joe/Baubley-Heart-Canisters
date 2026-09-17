package com.traverse.bhc.datagen.builder;

import com.traverse.bhc.common.recipes.HeartAmuletRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class HeartAmuletRecipeBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private final ItemStack resultStack;
	private final NonNullList<Ingredient> ingredients = NonNullList.create();
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;

	public HeartAmuletRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
		this(category, new ItemStack(result, count));
	}

	public HeartAmuletRecipeBuilder(RecipeCategory category, ItemStack result) {
		this.category = category;
		this.resultStack = result;
	}

	/**
	 * Creates a new builder for a shapeless recipe.
	 */
	public static HeartAmuletRecipeBuilder shapeless(RecipeCategory category, ItemLike result) {
		return new HeartAmuletRecipeBuilder(category, result, 1);
	}

	/**
	 * Creates a new builder for a shapeless recipe.
	 */
	public static HeartAmuletRecipeBuilder shapeless(RecipeCategory category, ItemLike result, int count) {
		return new HeartAmuletRecipeBuilder(category, result, count);
	}

	public static HeartAmuletRecipeBuilder shapeless(RecipeCategory p_252339_, ItemStack result) {
		return new HeartAmuletRecipeBuilder(p_252339_, result);
	}

	/**
	 * Adds an ingredient that can be any item in the given tag.
	 */
	public HeartAmuletRecipeBuilder requires(TagKey<Item> tag) {
		return this.requires(Ingredient.of(tag));
	}

	/**
	 * Adds an ingredient of the given item.
	 */
	public HeartAmuletRecipeBuilder requires(ItemLike item) {
		return this.requires(item, 1);
	}

	/**
	 * Adds the given ingredient multiple times.
	 */
	public HeartAmuletRecipeBuilder requires(ItemLike item, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.requires(Ingredient.of(item));
		}

		return this;
	}

	/**
	 * Adds an ingredient.
	 */
	public HeartAmuletRecipeBuilder requires(Ingredient ingredient) {
		return this.requires(ingredient, 1);
	}

	/**
	 * Adds an ingredient multiple times.
	 */
	public HeartAmuletRecipeBuilder requires(Ingredient ingredient, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.ingredients.add(ingredient);
		}

		return this;
	}

	public HeartAmuletRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		this.criteria.put(name, criterion);
		return this;
	}

	public HeartAmuletRecipeBuilder group(@Nullable String groupName) {
		this.group = groupName;
		return this;
	}

	@Override
	public Item getResult() {
		return this.resultStack.getItem();
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
		this.ensureValid(id);
		Advancement.Builder advancement$builder = recipeOutput.advancement()
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id))
				.requirements(AdvancementRequirements.Strategy.OR);
		this.criteria.forEach(advancement$builder::addCriterion);
		HeartAmuletRecipe amuletRecipe = new HeartAmuletRecipe(
				Objects.requireNonNullElse(this.group, ""),
				this.resultStack,
				this.ingredients
		);
		recipeOutput.accept(id, amuletRecipe, advancement$builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
	}

	/**
	 * Makes sure that this recipe is valid and obtainable.
	 */
	private void ensureValid(ResourceLocation id) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}
}