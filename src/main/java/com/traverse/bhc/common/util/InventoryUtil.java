package com.traverse.bhc.common.util;

import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.transfer.IndexModifier;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemAccessItemHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class InventoryUtil {

    public static VirtualInventory createVirtualInventory(int slots, ItemStack stack) {
        return new VirtualInventory(slots, stack);
    }

    @Deprecated(forRemoval = true)
    public static boolean hasAmulet(Player player, int slot) {
        return player.getInventory().hasAnyMatching(stack -> stack.is(RegistryHandler.HEART_AMULET.get()));
    }


    public static class VirtualInventory extends ItemAccessItemHandler {
        private ItemStack stack;

        public VirtualInventory(int slots, ItemStack stack) {
            super(ItemAccess.forStack(stack), RegistryHandler.STORED_HEARTS_COMPONENT.get(), slots);
                this.stack = stack;
        }

        public IndexModifier<ItemResource> indexModifier() {
            return (index, resource, amount) -> {
                ItemContainerContents result = update(ItemResource.of(stack), index, resource, amount).get(this.component);

                stack.set(this.component, result);
            };
        }
    }
}
