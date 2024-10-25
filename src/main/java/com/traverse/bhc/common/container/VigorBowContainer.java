package com.traverse.bhc.common.container;

import com.traverse.bhc.common.container.base.SoulContainerMenu;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class VigorBowContainer extends SoulContainerMenu {

    public static final int SLOT_COUNT = 4;

    public VigorBowContainer(int containerId, Inventory playerInventory, int containerSlotId) {
        super(RegistryHandler.VIGOR_BOW_CONTAINER.get(), containerId, playerInventory, SLOT_COUNT, containerSlotId);

        //Heart Container Slots
        this.addSlot(new SlotPendant(this.itemStackHandler, 0, 71, 33));//RED
        this.addSlot(new SlotPendant(this.itemStackHandler, 1, 98, 9));//YELLOW
        this.addSlot(new SlotPendant(this.itemStackHandler, 2, 98, 33));//GREEN
        this.addSlot(new SlotPendant(this.itemStackHandler, 3, 98, 57));//BLUE
        addPlayerInventory();
    }

    @Override
    protected Holder<Item> getContainerItem() {
        return RegistryHandler.VIGOR_BOW;
    }
}
