package com.traverse.bhc.common.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.InventoryUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.List;

public class HeartAmuletRecipe extends ShapelessRecipe {
    public static final MapCodec<HeartAmuletRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Codec.STRING.fieldOf("group").forGetter(o -> o.group),
                            ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result),
                            com.mojang.serialization.Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(1, ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth())).fieldOf("ingredients").forGetter(o -> o.ingredients)
                    )
                    .apply(i, HeartAmuletRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, HeartAmuletRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            o -> o.group,
            ItemStackTemplate.STREAM_CODEC,
            o -> o.result,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
            o -> o.ingredients,
            HeartAmuletRecipe::new
    );
    public static final RecipeSerializer<HeartAmuletRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    final String group;
    final ItemStackTemplate result;
    final List<Ingredient> ingredients;


    public HeartAmuletRecipe(String group, ItemStackTemplate stack, List<Ingredient> list) {
        super(new CommonInfo(true), new CraftingBookInfo(CraftingBookCategory.EQUIPMENT, group), stack, list);
        this.group = group;
        this.result = stack;
        this.ingredients = list;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingContainer) {
        ItemStack stack = super.assemble(craftingContainer);

        ItemStack oldCanister = ItemStack.EMPTY;
        for (int i = 0; i < craftingContainer.size(); i++) {
            ItemStack input = craftingContainer.getItem(i);
            if (input.getItem() == RegistryHandler.HEART_AMULET.get()) {
                oldCanister = input;
                break;
            }
        }
        if (oldCanister.isEmpty()) {
            // this should never happen, but if it does, just return the result without the inventory data
            return stack;
        }

        // expand the virtual inventory
        try (var tx = Transaction.openRoot()) {
            var oldInv = InventoryUtil.createVirtualInventory(4, oldCanister);
            var newInv = InventoryUtil.createVirtualInventory(5, stack);
            for (int i = 0; i < oldInv.size(); i++) {
                ItemResource resource = oldInv.getResource(i);
                if (resource.isEmpty()) continue;
                newInv.insert(i, resource, oldInv.getAmountAsInt(i), tx);
            }
            tx.commit();
        }
        return stack;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public RecipeSerializer<ShapelessRecipe> getSerializer() {
        return (RecipeSerializer)RegistryHandler.HEART_AMULET_RECIPE_SERIALIZER.get();
    }
}
