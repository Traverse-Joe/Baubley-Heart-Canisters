package com.traverse.bhc.common.util;

import com.traverse.bhc.common.container.base.SoulContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public interface SoulContainerProvider {

    default void openMenu(Player player, InteractionHand hand, SoulMenuConstructor menuConstructor, Consumer<FriendlyByteBuf> extraDataWriter) {

        var stack = player.getItemInHand(hand);
        var title = getContainerName(stack);
        var slotId = SoulContainerMenu.getSlotIdForHand(player, hand);

        player.openMenu(new SimpleMenuProvider((containerId, playerInventory, _unused) -> menuConstructor.createMenu(containerId, playerInventory, slotId), title), byteBuf -> {
            SoulContainerMenu.writeSlotId(byteBuf, slotId);
            extraDataWriter.accept(byteBuf);
        });
    }

    default void openMenu(Player player, InteractionHand hand, SoulMenuConstructor menuConstructor) {
        openMenu(player, hand, menuConstructor, buf -> {
            // NO-OP
        });
    }

    Component getContainerName(ItemStack stack);

    interface SoulMenuConstructor {

        @Nullable
        AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, int containerSlotId);
    }
}
