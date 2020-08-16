package com.traverse.bhc.common.util;


import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryUtil {

    private  static final String ITEMLIST = BaubleyHeartCanisters.MODID + "_itemlist";

    public static ItemStackHandler createVirtualInventory(int slots, ItemStack stack){
        ItemStackHandler handler = new ItemStackHandler(slots);
        CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();
        handler.deserializeNBT(nbt.getCompound(ITEMLIST));
        return handler;
    }

    public static void serializeInventory(ItemStackHandler itemHandler, ItemStack stack){
        CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();
        nbt.put(ITEMLIST, itemHandler.serializeNBT());
        stack.setTag(nbt);
    }
}
