package com.traverse.bhc.common.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.InventoryUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.List;

public class HeartAmuletRecipe extends ShapelessRecipe {

    final String group;
    final ItemStack result;
    final List<Ingredient> ingredients;


    public HeartAmuletRecipe(String group, ItemStack stack, List<Ingredient> list) {
        super(group, CraftingBookCategory.EQUIPMENT, stack, list);
        this.group = group;
        this.result = stack;
        this.ingredients = list;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingContainer, HolderLookup.Provider registries) {
        ItemStack stack = super.assemble(craftingContainer, registries);

        ItemStack oldCanister = ItemStack.EMPTY;
        for (int i = 0; i < craftingContainer.size(); i++) {
            ItemStack input = craftingContainer.getItem(i);
            if (input.getItem() == RegistryHandler.HEART_AMULET.get()) {
                oldCanister = input;
                break;
            }
        }

        // expand the virtual inventory
        var oldInv = InventoryUtil.createVirtualInventory(4, oldCanister);
        var newInv = InventoryUtil.createVirtualInventory(5, stack);
        for (int i = 0; i < oldInv.getSlots(); i++) {
            newInv.setStackInSlot(i, oldInv.getStackInSlot(i));
        }

        return stack;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public RecipeSerializer<ShapelessRecipe> getSerializer() {
        return (RecipeSerializer)RegistryHandler.HEART_AMULET_RECIPE_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<HeartAmuletRecipe> {
        private static final MapCodec<HeartAmuletRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(1, ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth())).fieldOf("ingredients").forGetter(p_360071_ -> p_360071_.ingredients)
                        )
                        .apply(instance, HeartAmuletRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, HeartAmuletRecipe> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                recipe -> recipe.group,
                ItemStack.STREAM_CODEC,
                recipe -> recipe.result,
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                recipe -> recipe.ingredients,
                HeartAmuletRecipe::new
        );

        @Override
        public MapCodec<HeartAmuletRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, HeartAmuletRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
