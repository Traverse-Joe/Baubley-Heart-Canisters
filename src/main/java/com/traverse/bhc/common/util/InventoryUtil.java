package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryUtil {

    private static final String ITEMLIST = BaubleyHeartCanisters.MODID + "_itemlist";

    public static ItemStackHandler createVirtualInventory(int slots, ItemStack stack) {
        ItemStackHandler handler = new ItemStackHandler(slots);
        CompoundTag nbt = stack.hasTag() ? stack.getTag() : new CompoundTag();
        handler.deserializeNBT(nbt.getCompound(ITEMLIST));

        return handler;
    }

    public static void serializeInventory(ItemStackHandler itemHandler, ItemStack stack) {
        CompoundTag nbt = stack.hasTag() ? stack.getTag() : new CompoundTag();
        nbt.put(ITEMLIST, itemHandler.serializeNBT());
        stack.setTag(nbt);
    }
}
