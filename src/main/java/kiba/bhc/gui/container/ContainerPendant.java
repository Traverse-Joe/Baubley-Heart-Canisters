package kiba.bhc.gui.container;

import com.google.common.base.Preconditions;
import kiba.bhc.init.ModItems;
import kiba.bhc.items.BaseHeartCanister;
import kiba.bhc.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandler;
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
                this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }

        //heart container slots
        this.addSlotToContainer(new SlotPendant(this.itemHandler, 0, 80, 9)); //red
        this.addSlotToContainer(new SlotPendant(this.itemHandler, 1, 53, 33)); //orange
        this.addSlotToContainer(new SlotPendant(this.itemHandler, 2, 107, 33)); //green
        this.addSlotToContainer(new SlotPendant(this.itemHandler, 3, 80, 57)); //blue
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

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();
            if(index < HeartType.values().length) { //player --> pendant or pendant --> player inventory
                if(!this.mergeItemStack(slotStack, 4, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(slotStack, 0, 4, false)) {
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
            //only store heart canisters matching the color.
            return super.isItemValid(stack) && stack.getItem() instanceof BaseHeartCanister && ((BaseHeartCanister) stack.getItem()).type.ordinal() == this.getSlotIndex();
        }

        @Override
        public int getSlotStackLimit() {
            return getMaxStackSize();
        }

        public static int getMaxStackSize() { //TODO small bypass, might need to be updated if stack sizes are made configurable individually.
            return ModItems.RED_HEART.getItemStackLimit();
        }
    }
}
