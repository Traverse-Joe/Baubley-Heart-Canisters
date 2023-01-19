package com.traverse.bhc.common.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.InventoryUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.items.ItemStackHandler;

public class HeartAmuletRecipe extends ShapelessRecipe {

    public HeartAmuletRecipe(ResourceLocation id, String group, ItemStack stack, NonNullList<Ingredient> list) {
        super(id, group, stack, list);
    }

    @Override
    public ItemStack assemble(CraftingContainer p_44260_) {
        ItemStack oldCanister = ItemStack.EMPTY;
        for (int i = 0; i < p_44260_.getContainerSize(); i++) {
            ItemStack input = p_44260_.getItem(i);
            if(input.getItem() == RegistryHandler.HEART_AMULET.get()) {
                oldCanister = input;
                break;
            }
        }
        ItemStack stack = super.assemble(p_44260_);
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

        public static final ResourceLocation NAME = new ResourceLocation(BaubleyHeartCanisters.MODID, "amulet_shapeless");

        @Override
        public HeartAmuletRecipe fromJson(ResourceLocation p_44290_, JsonObject p_44291_) {
            String s = GsonHelper.getAsString(p_44291_, "group", "");
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(p_44291_, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (nonnulllist.size() > 3 * 3) {
                throw new JsonParseException("Too many ingredients for shapeless recipe. The maximum is " + (3 * 3));
            } else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_44291_, "result"));
                return new HeartAmuletRecipe(p_44290_, s, itemstack, nonnulllist);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray p_44276_) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < p_44276_.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(p_44276_.get(i));
                if (true || !ingredient.isEmpty()) { // FORGE: Skip checking if an ingredient is empty during shapeless recipe deserialization to prevent complex ingredients from caching tags too early. Can not be done using a config value due to sync issues.
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Override
        public HeartAmuletRecipe fromNetwork(ResourceLocation p_44293_, FriendlyByteBuf p_44294_) {
            String s = p_44294_.readUtf();
            int i = p_44294_.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(p_44294_));
            }

            ItemStack itemstack = p_44294_.readItem();
            return new HeartAmuletRecipe(p_44293_, s, itemstack, nonnulllist);
        }

        @Override
        public void toNetwork(FriendlyByteBuf p_44281_, HeartAmuletRecipe p_44282_) {
            p_44281_.writeUtf(p_44282_.getGroup());
            p_44281_.writeVarInt(p_44282_.getIngredients().size());

            for(Ingredient ingredient : p_44282_.getIngredients()) {
                ingredient.toNetwork(p_44281_);
            }

            p_44281_.writeItem(p_44282_.getResultItem());
        }

    }
}
