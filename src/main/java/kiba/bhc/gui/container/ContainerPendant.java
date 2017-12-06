package kiba.bhc.gui.container;

import com.google.common.base.Preconditions;
import kiba.bhc.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * @author UpcraftLP
 */
public class ContainerPendant extends Container {

    private final ItemStackHandler itemHandler;
    private final ItemStack pendant;

    public ContainerPendant(@Nonnull ItemStack pendant, @Nonnull InventoryPlayer playerInventory, @Nonnull EnumHand hand) {
        Preconditions.checkNotNull(pendant, "pendant cannot be null");
        Preconditions.checkNotNull(playerInventory, "playerInventory cannot be null");
        Preconditions.checkNotNull(hand, "hand cannot be null");
        this.itemHandler = InventoryUtil.createVirtualInventory(4, pendant);
        this.pendant = pendant;

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 161));
        }

        //heart container slots
        this.addSlotToContainer(new SlotItemHandler(this.itemHandler, 0, 80, 9)); //red
        this.addSlotToContainer(new SlotItemHandler(this.itemHandler, 0, 53, 33)); //orange
        this.addSlotToContainer(new SlotItemHandler(this.itemHandler, 0, 107, 33)); //green
        this.addSlotToContainer(new SlotItemHandler(this.itemHandler, 0, 80, 57)); //blue
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        InventoryUtil.serializeInventory(this.itemHandler, this.pendant);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
