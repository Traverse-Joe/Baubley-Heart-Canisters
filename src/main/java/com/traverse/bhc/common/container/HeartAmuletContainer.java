package com.traverse.bhc.common.container;

import com.traverse.bhc.common.container.base.SoulContainerMenu;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class HeartAmuletContainer extends SoulContainerMenu {

    private static final int SLOT_COUNT = 4;
    public static final String HEART_AMOUNT = "heart_amount";

    public HeartAmuletContainer(int windowId, Inventory playerInventory, int containerSlotId) {
        super(RegistryHandler.HEART_AMUlET_CONTAINER.get(), windowId, playerInventory, SLOT_COUNT, containerSlotId);

        //Heart Container Slots
        this.addSlot(new SlotPendant(this.itemStackHandler, 0, 80, 9));//RED
        this.addSlot(new SlotPendant(this.itemStackHandler, 1, 53, 33));//YELLOW
        this.addSlot(new SlotPendant(this.itemStackHandler, 2, 107, 33));//GREEN
        this.addSlot(new SlotPendant(this.itemStackHandler, 3, 80, 57));//BLUE

        addPlayerInventory();
    }

    @Override
    protected Holder<Item> getContainerItem() {
        return RegistryHandler.HEART_AMULET;
    }
}
