package com.traverse.bhc.common.container;

import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.container.base.SoulContainerMenu;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class BladeOfVitalityContainer extends SoulContainerMenu {

    public static final int SLOT_COUNT = 4;

    public BladeOfVitalityContainer(int containerId, Inventory playerInventory, int containerSlotId) {
        super(RegistryHandler.BLADE_OF_VITALITY_CONTAINER.get(), containerId, playerInventory, SLOT_COUNT, containerSlotId);

        //Heart Container Slots
        this.addSlot(new SlotPendant(this.itemStackHandler, 0, 80, 5, ConfigHandler.general.bladeOfVitalitySlotCapacity::get));//RED
        this.addSlot(new SlotPendant(this.itemStackHandler, 1, 80, 25, ConfigHandler.general.bladeOfVitalitySlotCapacity::get));//YELLOW
        this.addSlot(new SlotPendant(this.itemStackHandler, 2, 80, 45, ConfigHandler.general.bladeOfVitalitySlotCapacity::get));//GREEN
        this.addSlot(new SlotPendant(this.itemStackHandler, 3, 80, 65, ConfigHandler.general.bladeOfVitalitySlotCapacity::get));//BLUE

        addPlayerInventory();
    }

    @Override
    protected Holder<Item> getContainerItem() {
        return RegistryHandler.BLADE_OF_VITALITY;
    }
}
