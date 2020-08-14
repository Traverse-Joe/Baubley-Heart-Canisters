package com.traverse.bhc.common.container;

import com.google.common.base.Preconditions;
import com.traverse.bhc.common.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class HeartAmuletContainer extends Container {

    public  ItemStackHandler itemStackHandler;
    private  ItemStack amulet;


    public HeartAmuletContainer(ContainerType<?> type, int windowId, final PlayerInventory playerInventory, ItemStack amulet){
        super(type, windowId);
        this.itemStackHandler = new ItemStackHandler(4);
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

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    private static class SlotPendant extends SlotItemHandler {
        public SlotPendant(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }
}
