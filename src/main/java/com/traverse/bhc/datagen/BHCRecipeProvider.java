package com.traverse.bhc.datagen;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.data.BHCItemTags;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class BHCRecipeProvider extends RecipeProvider {

    public BHCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // hearts
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegistryHandler.RED_HEART.get()).define('H', BHCItemTags.HEARTS).define('#', RegistryHandler.RED_HEART_MELTED.get()).pattern("###").pattern("#H#").pattern("###").unlockedBy("has_heart", has(BHCItemTags.HEARTS)).group(BaubleyHeartCanisters.id("red_heart").toString()).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegistryHandler.YELLOW_HEART.get()).define('H', BHCItemTags.HEARTS).define('#', RegistryHandler.YELLOW_HEART_MELTED.get()).pattern("###").pattern("#H#").pattern("###").unlockedBy("has_heart", has(BHCItemTags.HEARTS)).group(BaubleyHeartCanisters.id("yellow_heart").toString()).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegistryHandler.GREEN_HEART.get()).define('H', BHCItemTags.HEARTS).define('#', RegistryHandler.GREEN_HEART_MELTED.get()).pattern("###").pattern("#H#").pattern("###").unlockedBy("has_heart", has(BHCItemTags.HEARTS)).group(BaubleyHeartCanisters.id("green_heart").toString()).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegistryHandler.BLUE_HEART.get()).define('H', BHCItemTags.HEARTS).define('#', RegistryHandler.BLUE_HEART_MELTED.get()).pattern("###").pattern("#H#").pattern("###").unlockedBy("has_heart", has(BHCItemTags.HEARTS)).group(BaubleyHeartCanisters.id("blue_heart").toString()).save(recipeOutput);

        // heart canisters
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegistryHandler.RED_CANISTER.get()).requires(RegistryHandler.CANISTER.get()).requires(RegistryHandler.RED_HEART.get()).requires(RegistryHandler.RELIC_APPLE.get()).requires(BHCItemTags.WITHER_BONES).unlockedBy("has_heart", has(RegistryHandler.RED_HEART.get())).group(BaubleyHeartCanisters.id("red_heart_canister").toString()).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegistryHandler.YELLOW_CANISTER.get()).requires(RegistryHandler.RED_CANISTER.get()).requires(RegistryHandler.YELLOW_HEART.get()).requires(Items.ENCHANTED_GOLDEN_APPLE).unlockedBy("has_heart", has(RegistryHandler.YELLOW_HEART.get())).group(BaubleyHeartCanisters.id("yellow_heart_canister").toString()).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegistryHandler.GREEN_CANISTER.get()).requires(RegistryHandler.YELLOW_CANISTER.get()).requires(RegistryHandler.GREEN_HEART.get()).requires(Tags.Items.NETHER_STARS).requires(Items.SHULKER_SHELL).unlockedBy("has_heart", has(RegistryHandler.GREEN_HEART.get())).group(BaubleyHeartCanisters.id("green_heart_canister").toString()).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegistryHandler.BLUE_CANISTER.get()).requires(RegistryHandler.GREEN_CANISTER.get()).requires(RegistryHandler.BLUE_HEART.get()).requires(Tags.Items.STORAGE_BLOCKS_NETHERITE).requires(Tags.Items.GEMS_AMETHYST).unlockedBy("has_heart", has(RegistryHandler.BLUE_HEART.get())).group(BaubleyHeartCanisters.id("blue_heart_canister").toString()).save(recipeOutput);

        // melted hearts from blast furnace
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RegistryHandler.RED_HEART.get()), RecipeCategory.MISC, RegistryHandler.RED_HEART_MELTED.get(), 0.1F, 100).unlockedBy("has_heart", has(RegistryHandler.RED_HEART.get())).group(RegistryHandler.RED_HEART_MELTED.getId().toString()).save(recipeOutput, RegistryHandler.RED_HEART_MELTED.getId().withSuffix("_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RegistryHandler.YELLOW_HEART.get()), RecipeCategory.MISC, RegistryHandler.YELLOW_HEART_MELTED.get(), 0.3F, 200).unlockedBy("has_heart", has(RegistryHandler.YELLOW_HEART.get())).group(RegistryHandler.YELLOW_HEART_MELTED.getId().toString()).save(recipeOutput, RegistryHandler.YELLOW_HEART_MELTED.getId().withSuffix("_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RegistryHandler.GREEN_HEART.get()), RecipeCategory.MISC, RegistryHandler.GREEN_HEART_MELTED.get(), 0.7F, 300).unlockedBy("has_heart", has(RegistryHandler.GREEN_HEART.get())).group(RegistryHandler.GREEN_HEART_MELTED.getId().toString()).save(recipeOutput, RegistryHandler.GREEN_HEART_MELTED.getId().withSuffix("_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RegistryHandler.BLUE_HEART.get()), RecipeCategory.MISC, RegistryHandler.BLUE_HEART_MELTED.get(), 1.0F, 400).unlockedBy("has_heart", has(RegistryHandler.BLUE_HEART.get())).group(RegistryHandler.BLUE_HEART_MELTED.getId().toString()).save(recipeOutput, RegistryHandler.BLUE_HEART_MELTED.getId().withSuffix("_from_smelting"));

        // melted hearts conversion
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, RegistryHandler.RED_HEART_MELTED.get(), RecipeCategory.MISC, RegistryHandler.YELLOW_HEART_MELTED.get(), BaubleyHeartCanisters.id(getConversionRecipeName(RegistryHandler.YELLOW_HEART_MELTED.get(), RegistryHandler.RED_HEART_MELTED.get())).toString(), RegistryHandler.YELLOW_HEART_MELTED.getId().toString(), BaubleyHeartCanisters.id(getConversionRecipeName(RegistryHandler.RED_HEART_MELTED.get(), RegistryHandler.YELLOW_HEART_MELTED.get())).toString(), RegistryHandler.RED_HEART_MELTED.getId().toString());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, RegistryHandler.YELLOW_HEART_MELTED.get(), RecipeCategory.MISC, RegistryHandler.GREEN_HEART_MELTED.get(), BaubleyHeartCanisters.id(getConversionRecipeName(RegistryHandler.GREEN_HEART_MELTED.get(), RegistryHandler.YELLOW_HEART_MELTED.get())).toString(), RegistryHandler.GREEN_HEART_MELTED.getId().toString(), BaubleyHeartCanisters.id(getConversionRecipeName(RegistryHandler.YELLOW_HEART_MELTED.get(), RegistryHandler.GREEN_HEART_MELTED.get())).toString(), RegistryHandler.YELLOW_HEART_MELTED.getId().toString());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, RegistryHandler.GREEN_HEART_MELTED.get(), RecipeCategory.MISC, RegistryHandler.BLUE_HEART_MELTED.get(), BaubleyHeartCanisters.id(getConversionRecipeName(RegistryHandler.BLUE_HEART_MELTED.get(), RegistryHandler.GREEN_HEART_MELTED.get())).toString(), RegistryHandler.BLUE_HEART_MELTED.getId().toString(), BaubleyHeartCanisters.id(getConversionRecipeName(RegistryHandler.GREEN_HEART_MELTED.get(), RegistryHandler.BLUE_HEART_MELTED.get())).toString(), RegistryHandler.GREEN_HEART_MELTED.getId().toString());

        // heart patches
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegistryHandler.RED_HEART_PATCH.get()).define('W', ItemTags.WOOL).define('S', Tags.Items.STRINGS).define('H', RegistryHandler.RED_HEART.get()).pattern(" W ").pattern("SHS").pattern(" W ").unlockedBy("has_heart", has(RegistryHandler.RED_HEART.get())).group(RegistryHandler.RED_HEART_PATCH.getId().toString()).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegistryHandler.YELLOW_HEART_PATCH.get()).define('W', ItemTags.WOOL).define('S', Tags.Items.STRINGS).define('H', RegistryHandler.YELLOW_HEART.get()).pattern(" W ").pattern("SHS").pattern(" W ").unlockedBy("has_heart", has(RegistryHandler.YELLOW_HEART.get())).group(RegistryHandler.YELLOW_HEART_PATCH.getId().toString()).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegistryHandler.GREEN_HEART_PATCH.get()).define('W', ItemTags.WOOL).define('S', Tags.Items.STRINGS).define('H', RegistryHandler.GREEN_HEART.get()).pattern(" W ").pattern("SHS").pattern(" W ").unlockedBy("has_heart", has(RegistryHandler.GREEN_HEART.get())).group(RegistryHandler.GREEN_HEART_PATCH.getId().toString()).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegistryHandler.BLUE_HEART_PATCH.get()).define('W', ItemTags.WOOL).define('S', Tags.Items.STRINGS).define('H', RegistryHandler.BLUE_HEART.get()).pattern(" W ").pattern("SHS").pattern(" W ").unlockedBy("has_heart", has(RegistryHandler.BLUE_HEART.get())).group(RegistryHandler.BLUE_HEART_PATCH.getId().toString()).save(recipeOutput);

        // heartpulse belts
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegistryHandler.RED_HEARTPULSE_BELT.get()).define('L', Tags.Items.LEATHERS).define('S', Tags.Items.STRINGS).define('R', RegistryHandler.RED_CANISTER.get()).pattern("LLL").pattern("SRS").pattern("LLL").unlockedBy("has_heart", has(RegistryHandler.RED_HEART.get())).group(RegistryHandler.RED_HEARTPULSE_BELT.getId().toString()).save(recipeOutput);
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(RegistryHandler.RED_HEARTPULSE_BELT.get()), Ingredient.of(RegistryHandler.RED_CANISTER.get()), Ingredient.of(RegistryHandler.YELLOW_CANISTER.get()),RecipeCategory.COMBAT, RegistryHandler.YELLOW_HEARTPULSE_BELT.get()).unlocks("has_heart", has(BHCItemTags.HEARTS)).save(recipeOutput, BaubleyHeartCanisters.id(getItemName(RegistryHandler.YELLOW_HEARTPULSE_BELT.get()) + "_smithing"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(RegistryHandler.YELLOW_HEARTPULSE_BELT.get()), Ingredient.of(RegistryHandler.YELLOW_CANISTER.get()), Ingredient.of(RegistryHandler.GREEN_CANISTER.get()),RecipeCategory.COMBAT, RegistryHandler.GREEN_HEARTPULSE_BELT.get()).unlocks("has_heart", has(BHCItemTags.HEARTS)).save(recipeOutput, BaubleyHeartCanisters.id(getItemName(RegistryHandler.GREEN_HEARTPULSE_BELT.get()) + "_smithing"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(RegistryHandler.GREEN_HEARTPULSE_BELT.get()), Ingredient.of(RegistryHandler.GREEN_CANISTER.get()), Ingredient.of(RegistryHandler.BLUE_CANISTER.get()),RecipeCategory.COMBAT, RegistryHandler.BLUE_HEARTPULSE_BELT.get()).unlocks("has_heart", has(BHCItemTags.HEARTS)).save(recipeOutput, BaubleyHeartCanisters.id(getItemName(RegistryHandler.BLUE_HEARTPULSE_BELT.get()) + "_smithing"));

        // canister
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegistryHandler.CANISTER.get()).define('#', Tags.Items.BONES).define('I', Tags.Items.INGOTS_IRON).pattern(" I ").pattern("I#I").pattern(" I ").unlockedBy("has_bone", has(Tags.Items.BONES)).group(RegistryHandler.CANISTER.getId().toString()).save(recipeOutput);

        // soul canister
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(BHCItemTags.HEARTS), Ingredient.of(RegistryHandler.BLUE_CANISTER.get()), Ingredient.of(Items.TOTEM_OF_UNDYING), RecipeCategory.MISC, RegistryHandler.SOUL_CANISTER.get()).unlocks("has_totem", has(Items.TOTEM_OF_UNDYING)).save(recipeOutput, BaubleyHeartCanisters.id(getItemName(RegistryHandler.SOUL_CANISTER.get()) + "_smithing"));

        // heart amulet
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegistryHandler.HEART_AMULET.get()).define('S', Tags.Items.STRINGS).define('H', BHCItemTags.HEARTS).define('#', Tags.Items.GLASS_BLOCKS_COLORLESS).pattern("S S").pattern("#H#").pattern(" # ").unlockedBy("has_heart", has(BHCItemTags.HEARTS)).group(RegistryHandler.SOUL_HEART_AMULET.getId().toString()).save(recipeOutput);

        //soul heart amulet
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegistryHandler.SOUL_HEART_AMULET.get()).requires(RegistryHandler.HEART_AMULET.get()).requires(RegistryHandler.SOUL_HEART_CRYSTAL.get()).unlockedBy("has_crystal", has(RegistryHandler.SOUL_HEART_CRYSTAL.get())).group(RegistryHandler.SOUL_HEART_AMULET.getId().toString()).save(recipeOutput);

        // blade of vitality
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegistryHandler.BLADE_OF_VITALITY.get()).define('C', RegistryHandler.SOUL_HEART_CRYSTAL.get()).define('A', RegistryHandler.SOUL_HEART_AMULET.get()).define('S', Tags.Items.RODS_WOODEN).pattern("C").pattern("A").pattern("S").unlockedBy("has_crystal", has(RegistryHandler.SOUL_HEART_CRYSTAL.get())).save(recipeOutput);

        // vigor bow
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegistryHandler.VIGOR_BOW.get()).define('C', RegistryHandler.SOUL_HEART_CRYSTAL.get()).define('A', RegistryHandler.SOUL_HEART_AMULET.get()).define('S', Tags.Items.STRINGS).pattern(" CS").pattern("CAS").pattern(" CS").unlockedBy("has_crystal", has(RegistryHandler.SOUL_HEART_CRYSTAL.get())).save(recipeOutput);

        // soul heart crystal
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegistryHandler.SOUL_HEART_CRYSTAL.get()).define('#', Items.ECHO_SHARD).define('R', RegistryHandler.RED_HEART.get()).define('Y', RegistryHandler.YELLOW_HEART.get()).define('G', RegistryHandler.GREEN_HEART.get()).define('B', RegistryHandler.BLUE_HEART.get()).define('A', Items.AMETHYST_SHARD).pattern("#R#").pattern("YAG").pattern("#B#").unlockedBy("has_heart", has(BHCItemTags.HEARTS)).group(RegistryHandler.SOUL_HEART_CRYSTAL.getId().toString()).save(recipeOutput);

        // relic apple
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, RegistryHandler.RELIC_APPLE.get()).define('D', Tags.Items.GEMS_DIAMOND).define('E', Tags.Items.GEMS_EMERALD).define('#', Items.APPLE).pattern(" D ").pattern("E#E").pattern(" D ").unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND)).unlockedBy("has_emerald", has(Tags.Items.GEMS_EMERALD)).group(RegistryHandler.RELIC_APPLE.getId().toString()).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, RegistryHandler.RELIC_APPLE.get()).define('D', Tags.Items.GEMS_DIAMOND).define('E', Tags.Items.GEMS_EMERALD).define('#', Items.APPLE).pattern(" E ").pattern("D#D").pattern(" E ").unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND)).unlockedBy("has_emerald", has(Tags.Items.GEMS_EMERALD)).group(RegistryHandler.RELIC_APPLE.getId().toString()).save(recipeOutput, RecipeBuilder.getDefaultRecipeId(RegistryHandler.RELIC_APPLE.get()).withSuffix("_rotated"));

        //Wither Bones
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegistryHandler.WITHER_BONE.get(),6).requires(Tags.Items.BONES).requires(Tags.Items.BONES).requires(Tags.Items.BONES).requires(Items.WITHER_SKELETON_SKULL).unlockedBy("has_heart", has(RegistryHandler.RED_HEART.get())).group(BaubleyHeartCanisters.id("wither_bone").toString()).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 6).requires(RegistryHandler.WITHER_BONE.get()).unlockedBy("has_wither_bone", has(RegistryHandler.WITHER_BONE.get())).group(BaubleyHeartCanisters.id("wither_bone_to_bonemeal").toString()).save(recipeOutput);;

        // vanilla enchanted golden apple
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.ENCHANTED_GOLDEN_APPLE).define('#', Tags.Items.STORAGE_BLOCKS_GOLD).define('A', Items.APPLE).pattern("###").pattern("#A#").pattern("###").unlockedBy("has_gold_block", has(Tags.Items.STORAGE_BLOCKS_GOLD)).group("enchanted_golden_apple").save(recipeOutput, BaubleyHeartCanisters.id("enchanted_golden_apple"));
    }
}
