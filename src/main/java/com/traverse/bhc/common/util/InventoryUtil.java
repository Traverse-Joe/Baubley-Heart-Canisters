package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.stream.Stream;

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

    public static boolean hasAmulet(Player player) {
        for (int i = 0; player.getInventory().getContainerSize() > i; ++i) {
            ItemStack stack = player.getInventory().getItem(i);
            if( stack.getItem() != RegistryHandler.HEART_AMULET.get()) continue;

        }
        return true;
    }
}
