package com.traverse.bhc.common.container;

import com.traverse.bhc.common.container.base.SoulContainerMenu;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class SoulHeartAmuletContainer extends SoulContainerMenu {

    private static final int SLOT_COUNT = 5;

    public SoulHeartAmuletContainer(int windowId, Inventory playerInventory, int containerSlotId) {
        super(RegistryHandler.SOUL_HEART_AMUlET_CONTAINER.get(), windowId, playerInventory, SLOT_COUNT, containerSlotId);

        //Heart Container Slots
        this.addSlot(new SoulHeartAmuletContainer.SlotPendant(this.itemStackHandler, 0, 80, 7));//RED
        this.addSlot(new SoulHeartAmuletContainer.SlotPendant(this.itemStackHandler, 1, 53, 33));//YELLOW
        this.addSlot(new SoulHeartAmuletContainer.SlotPendant(this.itemStackHandler, 2, 107, 33));//GREEN
        this.addSlot(new SoulHeartAmuletContainer.SlotPendant(this.itemStackHandler, 3, 80, 59));//BLUE
        this.addSlot(new SoulHeartAmuletContainer.SlotPendant(this.itemStackHandler, 4, 80, 33));//SOUL

        addPlayerInventory();
    }

    @Override
    protected Holder<Item> getContainerItem() {
        return RegistryHandler.SOUL_HEART_AMULET;
    }
}
