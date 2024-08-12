package com.traverse.bhc.common.container;

import com.traverse.bhc.common.container.base.SoulContainerMenu;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class BladeOfVitalityContainer extends SoulContainerMenu {

    private static final int SLOT_COUNT = 4;

    public BladeOfVitalityContainer(int containerId, Inventory playerInventory, int containerSlotId) {
        super(RegistryHandler.BLADE_OF_VITALITY_CONTAINER.get(), containerId, playerInventory, SLOT_COUNT, containerSlotId);

        //Heart Container Slots
        for (int i = 0; i < this.slotCount; i++) {
            int y = i * 20 + 5;
            this.addSlot(new BladeOfVitalityContainer.SlotPendant(this.itemStackHandler, i, 80,  y));
        }

        addPlayerInventory();
    }

    @Override
    protected Holder<Item> getContainerItem() {
        return RegistryHandler.BLADE_OF_VITALITY;
    }
}
