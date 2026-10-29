package com.traverse.bhc.common.container;

import com.traverse.bhc.common.config.ConfigHandler;
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
        this.addSlot(new SlotPendant(this.itemStackHandler, 0, 71, 33, ConfigHandler.general.vigorBowSlotCapacity::get));//RED
        this.addSlot(new SlotPendant(this.itemStackHandler, 1, 98, 9, ConfigHandler.general.vigorBowSlotCapacity::get));//YELLOW
        this.addSlot(new SlotPendant(this.itemStackHandler, 2, 98, 33, ConfigHandler.general.vigorBowSlotCapacity::get));//GREEN
        this.addSlot(new SlotPendant(this.itemStackHandler, 3, 98, 57, ConfigHandler.general.vigorBowSlotCapacity::get));//BLUE
        addPlayerInventory();
    }

    @Override
    protected Holder<Item> getContainerItem() {
        return RegistryHandler.VIGOR_BOW;
    }
}
