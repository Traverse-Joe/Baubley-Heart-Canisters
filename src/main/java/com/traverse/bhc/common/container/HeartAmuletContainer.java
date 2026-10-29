package com.traverse.bhc.common.container;

import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.container.base.SoulContainerMenu;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class HeartAmuletContainer extends SoulContainerMenu {

    private static final int SLOT_COUNT = 4;

    public HeartAmuletContainer(int windowId, Inventory playerInventory, int containerSlotId) {
        super(RegistryHandler.HEART_AMUlET_CONTAINER.get(), windowId, playerInventory, SLOT_COUNT, containerSlotId);

        //Heart Container Slots
        this.addSlot(new SlotPendant(this.itemStackHandler, 0, 80, 9, ConfigHandler.general.heartAmuletSlotCapacity::get));//RED
        this.addSlot(new SlotPendant(this.itemStackHandler, 1, 53, 33, ConfigHandler.general.heartAmuletSlotCapacity::get));//YELLOW
        this.addSlot(new SlotPendant(this.itemStackHandler, 2, 107, 33, ConfigHandler.general.heartAmuletSlotCapacity::get));//GREEN
        this.addSlot(new SlotPendant(this.itemStackHandler, 3, 80, 57, ConfigHandler.general.heartAmuletSlotCapacity::get));//BLUE

        addPlayerInventory();
    }

    @Override
    protected Holder<Item> getContainerItem() {
        return RegistryHandler.HEART_AMULET;
    }
}
