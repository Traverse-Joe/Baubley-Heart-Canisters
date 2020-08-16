package com.traverse.bhc.common.container;

import com.traverse.bhc.common.init.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.BaseHeartCanister;
import com.traverse.bhc.common.util.InventoryUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class HeartAmuletContainer extends Container {

    public static final String HEART_AMOUNT = "heart_amount";

    public  ItemStackHandler itemStackHandler;
    private  ItemStack amulet;


    public HeartAmuletContainer(ContainerType<?> type, int windowId, final PlayerInventory playerInventory, ItemStack amulet){
        super(type, windowId);
        this.itemStackHandler = InventoryUtil.createVirtualInventory(4, amulet);
        this.amulet = amulet;


        //Heart Container Slots
        this.addSlot(new SlotPendant(this.itemStackHandler, 0,80,9));//RED
        this.addSlot(new SlotPendant(this.itemStackHandler, 1,53,33));//YELLOW
        this.addSlot(new SlotPendant(this.itemStackHandler, 2,107,33));//GREEN
        this.addSlot(new SlotPendant(this.itemStackHandler, 3,80,57));//BLUE

        // Slots for the hotbar
        for (int row = 0; row < 9; ++ row) {
            int x = 8 + row * 18;
            int y = 56 + 86;
            addSlot(new Slot(playerInventory, row, x, y));
        }
        // Slots for the main inventory
        for (int row = 1; row < 4; ++ row) {
            for (int col = 0; col < 9; ++ col) {
                int x = 8 + col * 18;
                int y = row * 18 + (56 + 10);
                addSlot(new Slot(playerInventory, col + row * 9, x, y));
            }
        }
    }

    public HeartAmuletContainer(int windowId, PlayerInventory playerInventory, PacketBuffer data){
        super(RegistryHandler.HEART_AMUlET_CONTAINER.get(), windowId);
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        InventoryUtil.serializeInventory(this.itemStackHandler, this.amulet);
        CompoundNBT nbt = this.amulet.getTag();
        int[] hearts = new int [this.itemStackHandler.getSlots()];
        for(int i = 0; i < hearts.length; i++){
            ItemStack stack = this.itemStackHandler.getStackInSlot(i);
            if(!stack.isEmpty()) hearts [i] = stack.getCount() *2;
        }
        nbt.putIntArray(HEART_AMOUNT, hearts);

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if(slot != null && slot.getHasStack()){
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();
            if(index < this.itemStackHandler.getSlots()){
                if(!this.mergeItemStack(slotStack, this.itemStackHandler.getSlots(), this.inventorySlots.size(), true));
                return ItemStack.EMPTY;
            }
            else if(!this.mergeItemStack(slotStack, 0, this.itemStackHandler.getSlots(), false)){
                return ItemStack.EMPTY;
            }
            if(slotStack.isEmpty()) slot.putStack(ItemStack.EMPTY);
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

}
