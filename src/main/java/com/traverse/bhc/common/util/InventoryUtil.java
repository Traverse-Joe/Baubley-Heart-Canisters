package com.traverse.bhc.common.util;

import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;

public class InventoryUtil {

    public static ComponentItemHandler createVirtualInventory(int slots, ItemStack stack) {
        return new ComponentItemHandler(stack, DataComponents.CONTAINER, slots);
    }

    @Deprecated
    public static boolean hasAmulet(Player player, int slot) {
        return player.getInventory().hasAnyMatching(stack -> stack.is(RegistryHandler.HEART_AMULET.get()));
    }
}
