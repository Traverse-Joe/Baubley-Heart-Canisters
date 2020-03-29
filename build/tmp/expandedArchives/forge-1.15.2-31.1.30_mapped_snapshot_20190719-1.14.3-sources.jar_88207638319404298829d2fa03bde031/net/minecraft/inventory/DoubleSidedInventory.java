package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class DoubleSidedInventory implements IInventory {
   private final IInventory field_70477_b;
   private final IInventory field_70478_c;

   public DoubleSidedInventory(IInventory p_i50399_1_, IInventory p_i50399_2_) {
      if (p_i50399_1_ == null) {
         p_i50399_1_ = p_i50399_2_;
      }

      if (p_i50399_2_ == null) {
         p_i50399_2_ = p_i50399_1_;
      }

      this.field_70477_b = p_i50399_1_;
      this.field_70478_c = p_i50399_2_;
   }

   /**
    * Returns the number of slots in the inventory.
    */
   public int getSizeInventory() {
      return this.field_70477_b.getSizeInventory() + this.field_70478_c.getSizeInventory();
   }

   public boolean isEmpty() {
      return this.field_70477_b.isEmpty() && this.field_70478_c.isEmpty();
   }

   /**
    * Return whether the given inventory is part of this large chest.
    */
   public boolean isPartOfLargeChest(IInventory inventoryIn) {
      return this.field_70477_b == inventoryIn || this.field_70478_c == inventoryIn;
   }

   /**
    * Returns the stack in the given slot.
    */
   public ItemStack getStackInSlot(int index) {
      return index >= this.field_70477_b.getSizeInventory() ? this.field_70478_c.getStackInSlot(index - this.field_70477_b.getSizeInventory()) : this.field_70477_b.getStackInSlot(index);
   }

   /**
    * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
    */
   public ItemStack decrStackSize(int index, int count) {
      return index >= this.field_70477_b.getSizeInventory() ? this.field_70478_c.decrStackSize(index - this.field_70477_b.getSizeInventory(), count) : this.field_70477_b.decrStackSize(index, count);
   }

   /**
    * Removes a stack from the given slot and returns it.
    */
   public ItemStack removeStackFromSlot(int index) {
      return index >= this.field_70477_b.getSizeInventory() ? this.field_70478_c.removeStackFromSlot(index - this.field_70477_b.getSizeInventory()) : this.field_70477_b.removeStackFromSlot(index);
   }

   /**
    * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
    */
   public void setInventorySlotContents(int index, ItemStack stack) {
      if (index >= this.field_70477_b.getSizeInventory()) {
         this.field_70478_c.setInventorySlotContents(index - this.field_70477_b.getSizeInventory(), stack);
      } else {
         this.field_70477_b.setInventorySlotContents(index, stack);
      }

   }

   /**
    * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
    */
   public int getInventoryStackLimit() {
      return this.field_70477_b.getInventoryStackLimit();
   }

   /**
    * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
    * hasn't changed and skip it.
    */
   public void markDirty() {
      this.field_70477_b.markDirty();
      this.field_70478_c.markDirty();
   }

   /**
    * Don't rename this method to canInteractWith due to conflicts with Container
    */
   public boolean isUsableByPlayer(PlayerEntity player) {
      return this.field_70477_b.isUsableByPlayer(player) && this.field_70478_c.isUsableByPlayer(player);
   }

   public void openInventory(PlayerEntity player) {
      this.field_70477_b.openInventory(player);
      this.field_70478_c.openInventory(player);
   }

   public void closeInventory(PlayerEntity player) {
      this.field_70477_b.closeInventory(player);
      this.field_70478_c.closeInventory(player);
   }

   /**
    * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
    * guis use Slot.isItemValid
    */
   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return index >= this.field_70477_b.getSizeInventory() ? this.field_70478_c.isItemValidForSlot(index - this.field_70477_b.getSizeInventory(), stack) : this.field_70477_b.isItemValidForSlot(index, stack);
   }

   public void clear() {
      this.field_70477_b.clear();
      this.field_70478_c.clear();
   }
}