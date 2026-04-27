package com.traverse.bhc.common.container.base;

import com.traverse.bhc.common.items.BaseHeartCanister;
import com.traverse.bhc.common.util.InventoryUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public abstract class SoulContainerMenu extends AbstractContainerMenu {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoulContainerMenu.class);
    protected final int containerSlotId;
    protected final InventoryUtil.VirtualInventory itemStackHandler;
    protected final Inventory playerInventory;
    protected final int slotCount;

    public SoulContainerMenu(@Nullable MenuType<?> menuType, int containerId, Inventory playerInventory, int slotCount, int containerSlotId) {
        super(menuType, containerId);

        this.playerInventory = playerInventory;
        this.containerSlotId = containerSlotId;

        var stack = playerInventory.getItem(containerSlotId);

        this.itemStackHandler = InventoryUtil.createVirtualInventory(slotCount, stack);
        this.slotCount = slotCount;

        if (!stack.is(getContainerItem())) {
            LOGGER.error("Item in slot {} of player {} was not of expected type, closing menu!", containerSlotId, playerInventory.player.getGameProfile().name());
            playerInventory.player.closeContainer();
        }
    }

    public static int getSlotIdForHand(Player player, InteractionHand hand) {
        int slotId;
        if (hand == InteractionHand.MAIN_HAND) {
            slotId = player.getInventory().getSelectedSlot();
            if (!Inventory.isHotbarSlot(slotId)) {
                LOGGER.error("Unable to find main hand slot for player {}", player.getGameProfile().name());
            }
        } else {
            slotId = Inventory.SLOT_OFFHAND;
        }

        return slotId;
    }

    public static void writeSlotId(RegistryFriendlyByteBuf byteBuf, int slotId) {
        byteBuf.writeVarInt(slotId);
    }

    public static int readSlotId(RegistryFriendlyByteBuf byteBuf) {
        return byteBuf.readVarInt();
    }

    protected void addPlayerInventory() {
        //Hotbar
        for (int idx = 0; idx < 9; ++idx) {
            int x = 8 + idx * 18;
            int y = 142;
            if (idx == this.containerSlotId) {
                addSlot(new LockedSlot(playerInventory, idx, x, y));
                continue;
            }

            addSlot(new Slot(playerInventory, idx, x, y));
        }

        //Main inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int x = 8 + col * 18;
                int y = 84 + row * 18;
                addSlot(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();
            if (index < this.itemStackHandler.size()) {
                if (!this.moveItemStackTo(slotStack, this.itemStackHandler.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 0, this.itemStackHandler.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }
        }
        return stack;
    }

    public static class LockedSlot extends Slot {

        public LockedSlot(Inventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public boolean mayPickup(Player playerIn) {
            return false;
        }
    }

    public static class SlotPendant extends ResourceHandlerSlot {

        private final int slotIndex;

        public SlotPendant(InventoryUtil.VirtualInventory itemHandler, int slotIndex, int xPosition, int yPosition) {
            super(itemHandler, itemHandler.indexModifier(), slotIndex, xPosition, yPosition);
            this.slotIndex = slotIndex;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return super.mayPlace(stack) && stack.getItem() instanceof BaseHeartCanister && ((BaseHeartCanister) stack.getItem()).getType().ordinal() == slotIndex;
        }
    }

    protected abstract Holder<Item> getContainerItem();

    @Override
    public boolean stillValid(Player player) {
        return player.getInventory().getItem(this.containerSlotId).is(getContainerItem());
    }
}
