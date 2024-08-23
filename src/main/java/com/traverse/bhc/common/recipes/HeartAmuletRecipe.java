package com.traverse.bhc.common.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.InventoryUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class HeartAmuletRecipe extends ShapelessRecipe {

    final String group;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;


    public HeartAmuletRecipe(String group, ItemStack stack, NonNullList<Ingredient> list) {
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


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RegistryHandler.HEART_AMULET_RECIPE_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<HeartAmuletRecipe> {

        private static final MapCodec<HeartAmuletRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group), ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result), Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(array -> {
            Ingredient[] aingredient = array.toArray(Ingredient[]::new); // Neo skip the empty check and immediately create the array.
            if (aingredient.length == 0) {
                return DataResult.error(() -> "No ingredients for heart amulet recipe");
            } else {
                return aingredient.length > ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth() ? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth())) : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
            }
        }, DataResult::success).forGetter(recipe -> recipe.ingredients)).apply(instance, HeartAmuletRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, HeartAmuletRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<HeartAmuletRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, HeartAmuletRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, HeartAmuletRecipe heartAmuletRecipe) {
            buf.writeUtf(heartAmuletRecipe.group);

            buf.writeVarInt(heartAmuletRecipe.ingredients.size());
            for (Ingredient ingredient : heartAmuletRecipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buf, heartAmuletRecipe.result);
        }

        private static HeartAmuletRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();

            int size = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));

            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            return new HeartAmuletRecipe(group, result, ingredients);
        }
    }
}
