//package sora.bhc.container;
//
//import com.google.common.base.Preconditions;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.container.Container;
//import net.minecraft.inventory.container.Slot;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.util.Hand;
//import net.minecraftforge.items.IItemHandler;
//import net.minecraftforge.items.ItemStackHandler;
//import net.minecraftforge.items.SlotItemHandler;
//import sora.bhc.handler.ConfigHandler;
//import sora.bhc.init.ModItems;
//import sora.bhc.items.BaseHeartCanister;
//import sora.bhc.util.InventoryUtil;
//
//import javax.annotation.Nonnull;
//
//
//public class HeartPendantContainer extends Container {
//
//    public static final String HEART_AMOUNT = "heart_amount";
//
//    private final ItemStackHandler itemHandler;
//    private final ItemStack pendant;
//
//    public HeartPendantContainer(int windowId, ItemStack pendant, @Nonnull PlayerInventory playerInventory, @Nonnull Enum<Hand> hand) {
//      super(ModItems.HEART_PENANT_CONTAINER,windowId);
//      Preconditions.checkNotNull(pendant, "pendant cannot be null");
//        Preconditions.checkNotNull(playerInventory, "playerInventory cannot be null");
//        Preconditions.checkNotNull(hand, "hand cannot be null");
//        this.itemHandler = InventoryUtil.createVirtualInventory(4, pendant);
//        this.pendant = pendant;
//
//
//
//        //heart container slots
//        this.addSlot(new SlotPendant(this.itemHandler, 0, 80, 9)); //red
//        this.addSlot(new SlotPendant(this.itemHandler, 1, 53, 33)); //orange
//        this.addSlot(new SlotPendant(this.itemHandler, 2, 107, 33)); //green
//        this.addSlot(new SlotPendant(this.itemHandler, 3, 80, 57)); //blue
//
//        //player inventory
//        for (int l = 0; l < 3; ++l)
//        {
//            for (int j1 = 0; j1 < 9; ++j1)
//            {
//                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 84 + l * 18));
//            }
//        }
//
//        //hotbar
//        for (int i1 = 0; i1 < 9; ++i1)
//        {
//            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
//        }
//    }
//
//    public HeartPendantContainer(int windowId){
//      super(ModItems.HEART_PENANT_CONTAINER,windowId);
//    }
//
//
//
//  @Override
//    public void onContainerClosed(PlayerEntity playerIn) {
//        super.onContainerClosed(playerIn);
//        InventoryUtil.serializeInventory(this.itemHandler, this.pendant);
//        CompoundNBT nbt = this.pendant.getTag();
//        int[] hearts = new int [this.itemHandler.getSlots()];
//        for(int i = 0; i < hearts.length; i++) { //save hearts to NBT for easy access
//            ItemStack stack = this.itemHandler.getStackInSlot(i);
//            if(!stack.isEmpty()) hearts[i] = stack.getCount() * 2;
//        }
//        nbt.putIntArray(HEART_AMOUNT, hearts);
//    }
//
//  @Override
//  public boolean canInteractWith(PlayerEntity playerIn) {
//    return true;
//  }
//
//    @Override
//    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
//        ItemStack stack = ItemStack.EMPTY;
//        Slot slot = inventorySlots.get(index);
//        if(slot != null && slot.getHasStack()) {
//            ItemStack slotStack = slot.getStack();
//            stack = slotStack.copy();
//            if(index < this.itemHandler.getSlots()) { //player --> pendant or pendant --> player inventory
//                if(!this.mergeItemStack(slotStack, this.itemHandler.getSlots(), this.inventorySlots.size(), true)) {
//                    return ItemStack.EMPTY;
//                }
//            }
//            else if(!this.mergeItemStack(slotStack, 0, this.itemHandler.getSlots(), false)) {
//                return ItemStack.EMPTY;
//            }
//
//            if(slotStack.isEmpty()) slot.putStack(ItemStack.EMPTY);
//            else slot.onSlotChanged();
//        }
//        return stack;
//    }
//
//    private static class SlotPendant extends SlotItemHandler {
//
//        public SlotPendant(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
//            super(itemHandler, index, xPosition, yPosition);
//        }
//
//        @Override
//        public boolean isItemValid(@Nonnull ItemStack stack) {
//            //only store heart canisters matching the color.
//            return super.isItemValid(stack) && stack.getItem() instanceof BaseHeartCanister && ((BaseHeartCanister) stack.getItem()).type.ordinal() == this.getSlotIndex();
//        }
//
//        @Override
//        public int getSlotStackLimit() {
//            return ConfigHandler.general.heartStackSize.get();
//        }
//
//    }
//}
