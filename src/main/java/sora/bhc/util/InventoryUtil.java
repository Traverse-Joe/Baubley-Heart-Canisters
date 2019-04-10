package sora.bhc.util;

import sora.bhc.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author UpcraftLP
 */
public class InventoryUtil {

    private static final String ITEMLIST = Reference.MODID + "_itemlist";

    public static ItemStackHandler createVirtualInventory(int slots, ItemStack stack) {
        ItemStackHandler handler = new ItemStackHandler(slots);
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        handler.deserializeNBT(nbt.getCompoundTag(ITEMLIST));
        return handler;
    }

    public static void serializeInventory(ItemStackHandler itemHandler, ItemStack stack) {
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        nbt.setTag(ITEMLIST, itemHandler.serializeNBT());
        stack.setTagCompound(nbt);
    }
}
