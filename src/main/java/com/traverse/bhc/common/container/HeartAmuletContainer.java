package com.traverse.bhc.common.container;

import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.BaseHeartCanister;
import com.traverse.bhc.common.items.ItemHeartAmulet;
import com.traverse.bhc.common.util.InventoryUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class HeartAmuletContainer extends Container {

    public static final String HEART_AMOUNT = "heart_amount";

    public ItemStackHandler itemStackHandler;

    public HeartAmuletContainer(int windowId, PlayerInventory playerInventory, ItemStack stack) {
        super(RegistryHandler.HEART_AMUlET_CONTAINER.get(), windowId);
        this.itemStackHandler = InventoryUtil.createVirtualInventory(4, stack);

        //Heart Container Slots
        this.addSlot(new SlotPendant(this.itemStackHandler, 0, 80, 9));//RED
        this.addSlot(new SlotPendant(this.itemStackHandler, 1, 53, 33));//YELLOW
        this.addSlot(new SlotPendant(this.itemStackHandler, 2, 107, 33));//GREEN
        this.addSlot(new SlotPendant(this.itemStackHandler, 3, 80, 57));//BLUE

        //Add player inventory slots
        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = 56 + 86;
            if (row == getSlotFor(playerInventory, stack)) {
                addSlot(new LockedSlot(playerInventory, row, x, y));
                continue;
            }

            addSlot(new Slot(playerInventory, row, x, y));
        }

        for (int row = 1; row < 4; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 8 + col * 18;
                int y = row * 18 + (56 + 10);
                addSlot(new Slot(playerInventory, col + row * 9, x, y));
            }
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        Hand hand = ItemHeartAmulet.getHandForAmulet(playerIn);
        if (hand != null)
            InventoryUtil.serializeInventory(this.itemStackHandler, playerIn.getHeldItem(hand));

        CompoundNBT nbt = playerIn.getHeldItem(hand).getTag();
        int[] hearts = new int[this.itemStackHandler.getSlots()];
        for (int i = 0; i < hearts.length; i++) {
            ItemStack stack = this.itemStackHandler.getStackInSlot(i);
            if (!stack.isEmpty()) hearts[i] = stack.getCount() * 2;
        }
        nbt.putIntArray(HEART_AMOUNT, hearts);
        playerIn.getHeldItem(hand).setTag(nbt);

        super.onContainerClosed(playerIn);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();
            if (index < this.itemStackHandler.getSlots()) {
                if (!this.mergeItemStack(slotStack, this.itemStackHandler.getSlots(), this.inventorySlots.size(), true))
                    ;
                return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(slotStack, 0, this.itemStackHandler.getSlots(), false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }
        return stack;
    }

    private static class SlotPendant extends SlotItemHandler {
        public SlotPendant(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return super.isItemValid(stack) && stack.getItem() instanceof BaseHeartCanister && ((BaseHeartCanister) stack.getItem()).type.ordinal() == this.getSlotIndex();
        }

        @Override
        public int getSlotStackLimit() {
            return ConfigHandler.general.heartStackSize.get();
        }
    }

    private static class LockedSlot extends Slot {

        public LockedSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }

        @Override
        public boolean canTakeStack(PlayerEntity playerIn) {
            return false;
        }
    }

    public int getSlotFor(PlayerInventory inventory, ItemStack stack) {
        for (int i = 0; i < inventory.mainInventory.size(); ++i) {
            if (!inventory.mainInventory.get(i).isEmpty() && stackEqualExact(stack, inventory.mainInventory.get(i))) {
                return i;
            }
        }

        return -1;
    }

    private boolean stackEqualExact(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
}
