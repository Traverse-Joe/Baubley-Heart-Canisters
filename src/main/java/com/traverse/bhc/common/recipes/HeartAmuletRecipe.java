package com.traverse.bhc.common.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.InventoryUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.neoforged.neoforge.items.ItemStackHandler;

public class HeartAmuletRecipe extends ShapelessRecipe {

    final String group;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;



    public HeartAmuletRecipe(String group,ItemStack stack, NonNullList<Ingredient> list) {
        super(group, CraftingBookCategory.EQUIPMENT, stack, list);
        this.group = group;
        this.result = stack;
        this.ingredients = list;
    }


    @Override
    public ItemStack assemble(CraftingContainer craftingContainer, RegistryAccess registryAccess) {
        ItemStack oldCanister = ItemStack.EMPTY;
        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            ItemStack input = craftingContainer.getItem(i);
            if(input.getItem() == RegistryHandler.HEART_AMULET.get()) {
                oldCanister = input;
                break;
            }
        }
        ItemStack stack = super.assemble(craftingContainer, registryAccess);
        ItemStackHandler oldInv = InventoryUtil.createVirtualInventory(4, oldCanister);
        ItemStackHandler newInv = InventoryUtil.createVirtualInventory(5, stack);
        for (int i = 0; i < oldInv.getSlots(); i++) {
            newInv.setStackInSlot(i, oldInv.getStackInSlot(i));
        }
        InventoryUtil.serializeInventory(newInv, stack);
        return stack;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RegistryHandler.HEART_AMULET_RECIPE_SERIALIZER.get();
    }

    public static class BHCSerializer implements RecipeSerializer<HeartAmuletRecipe> {

        private static final Codec<HeartAmuletRecipe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
                                ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Ingredient.CODEC_NONEMPTY
                                        .listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(
                                                array -> {
                                                    Ingredient[] aingredient = array
                                                            .toArray(Ingredient[]::new); //Forge skip the empty check and immediatly create the array.
                                                    if (aingredient.length == 0) {
                                                        return DataResult.error(() -> "No ingredients for shapeless recipe");
                                                    } else {
                                                        return aingredient.length > 3 * 3
                                                                ? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(3 * 3))
                                                                : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                                                    }
                                                },
                                                DataResult::success
                                        )
                                        .forGetter(recipe -> recipe.ingredients)
                        )
                        .apply(instance, HeartAmuletRecipe::new)
        );

        @Override
        public HeartAmuletRecipe fromNetwork(FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack itemstack = pBuffer.readItem();
            return new HeartAmuletRecipe(s, itemstack, nonnulllist);
        }

        @Override
        public Codec<HeartAmuletRecipe> codec() {
            return CODEC;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, HeartAmuletRecipe heartAmuletRecipe) {
            buf.writeUtf(heartAmuletRecipe.getGroup());
            buf.writeVarInt(heartAmuletRecipe.getIngredients().size());

            for(Ingredient ingredient : heartAmuletRecipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }
            buf.writeItem(heartAmuletRecipe.getResultItem(RegistryAccess.EMPTY));
        }

    }
}
