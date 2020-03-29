package net.minecraft.inventory.container;

import net.minecraft.entity.Entity;
import net.minecraft.entity.NPCMerchant;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.MerchantInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MerchantContainer extends Container {
   private final IMerchant merchant;
   private final MerchantInventory merchantInventory;
   @OnlyIn(Dist.CLIENT)
   private int field_217054_e;
   @OnlyIn(Dist.CLIENT)
   private boolean field_217055_f;
   @OnlyIn(Dist.CLIENT)
   private boolean field_223433_g;

   public MerchantContainer(int p_i50068_1_, PlayerInventory p_i50068_2_) {
      this(p_i50068_1_, p_i50068_2_, new NPCMerchant(p_i50068_2_.player));
   }

   public MerchantContainer(int p_i50069_1_, PlayerInventory p_i50069_2_, IMerchant p_i50069_3_) {
      super(ContainerType.MERCHANT, p_i50069_1_);
      this.merchant = p_i50069_3_;
      this.merchantInventory = new MerchantInventory(p_i50069_3_);
      this.addSlot(new Slot(this.merchantInventory, 0, 136, 37));
      this.addSlot(new Slot(this.merchantInventory, 1, 162, 37));
      this.addSlot(new MerchantResultSlot(p_i50069_2_.player, p_i50069_3_, this.merchantInventory, 2, 220, 37));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(p_i50069_2_, j + i * 9 + 9, 108 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.addSlot(new Slot(p_i50069_2_, k, 108 + k * 18, 142));
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void func_217045_a(boolean p_217045_1_) {
      this.field_217055_f = p_217045_1_;
   }

   /**
    * Callback for when the crafting matrix is changed.
    */
   public void onCraftMatrixChanged(IInventory inventoryIn) {
      this.merchantInventory.resetRecipeAndSlots();
      super.onCraftMatrixChanged(inventoryIn);
   }

   public void setCurrentRecipeIndex(int currentRecipeIndex) {
      this.merchantInventory.setCurrentRecipeIndex(currentRecipeIndex);
   }

   /**
    * Determines whether supplied player can use this container
    */
   public boolean canInteractWith(PlayerEntity playerIn) {
      return this.merchant.getCustomer() == playerIn;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_217048_e() {
      return this.merchant.getXp();
   }

   @OnlyIn(Dist.CLIENT)
   public int func_217047_f() {
      return this.merchantInventory.func_214024_h();
   }

   @OnlyIn(Dist.CLIENT)
   public void func_217052_e(int p_217052_1_) {
      this.merchant.func_213702_q(p_217052_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public int func_217049_g() {
      return this.field_217054_e;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_217043_f(int p_217043_1_) {
      this.field_217054_e = p_217043_1_;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_223431_b(boolean p_223431_1_) {
      this.field_223433_g = p_223431_1_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_223432_h() {
      return this.field_223433_g;
   }

   /**
    * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
    * null for the initial slot that was double-clicked.
    */
   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
      return false;
   }

   /**
    * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
    * inventory and the other inventory(s).
    */
   public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if (index == 2) {
            if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            slot.onSlotChange(itemstack1, itemstack);
            this.func_223132_j();
         } else if (index != 0 && index != 1) {
            if (index >= 3 && index < 30) {
               if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
         } else {
            slot.onSlotChanged();
         }

         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(playerIn, itemstack1);
      }

      return itemstack;
   }

   private void func_223132_j() {
      if (!this.merchant.getWorld().isRemote) {
         Entity entity = (Entity)this.merchant;
         this.merchant.getWorld().playSound(entity.func_226277_ct_(), entity.func_226278_cu_(), entity.func_226281_cx_(), this.merchant.func_213714_ea(), SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
      }

   }

   /**
    * Called when the container is closed.
    */
   public void onContainerClosed(PlayerEntity playerIn) {
      super.onContainerClosed(playerIn);
      this.merchant.setCustomer((PlayerEntity)null);
      if (!this.merchant.getWorld().isRemote) {
         if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity)playerIn).hasDisconnected()) {
            ItemStack itemstack = this.merchantInventory.removeStackFromSlot(0);
            if (!itemstack.isEmpty()) {
               playerIn.dropItem(itemstack, false);
            }

            itemstack = this.merchantInventory.removeStackFromSlot(1);
            if (!itemstack.isEmpty()) {
               playerIn.dropItem(itemstack, false);
            }
         } else {
            playerIn.inventory.placeItemBackInInventory(playerIn.world, this.merchantInventory.removeStackFromSlot(0));
            playerIn.inventory.placeItemBackInInventory(playerIn.world, this.merchantInventory.removeStackFromSlot(1));
         }

      }
   }

   public void func_217046_g(int p_217046_1_) {
      if (this.func_217051_h().size() > p_217046_1_) {
         ItemStack itemstack = this.merchantInventory.getStackInSlot(0);
         if (!itemstack.isEmpty()) {
            if (!this.mergeItemStack(itemstack, 3, 39, true)) {
               return;
            }

            this.merchantInventory.setInventorySlotContents(0, itemstack);
         }

         ItemStack itemstack1 = this.merchantInventory.getStackInSlot(1);
         if (!itemstack1.isEmpty()) {
            if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
               return;
            }

            this.merchantInventory.setInventorySlotContents(1, itemstack1);
         }

         if (this.merchantInventory.getStackInSlot(0).isEmpty() && this.merchantInventory.getStackInSlot(1).isEmpty()) {
            ItemStack itemstack2 = this.func_217051_h().get(p_217046_1_).func_222205_b();
            this.func_217053_c(0, itemstack2);
            ItemStack itemstack3 = this.func_217051_h().get(p_217046_1_).func_222202_c();
            this.func_217053_c(1, itemstack3);
         }

      }
   }

   private void func_217053_c(int p_217053_1_, ItemStack p_217053_2_) {
      if (!p_217053_2_.isEmpty()) {
         for(int i = 3; i < 39; ++i) {
            ItemStack itemstack = this.inventorySlots.get(i).getStack();
            if (!itemstack.isEmpty() && this.func_217050_b(p_217053_2_, itemstack)) {
               ItemStack itemstack1 = this.merchantInventory.getStackInSlot(p_217053_1_);
               int j = itemstack1.isEmpty() ? 0 : itemstack1.getCount();
               int k = Math.min(p_217053_2_.getMaxStackSize() - j, itemstack.getCount());
               ItemStack itemstack2 = itemstack.copy();
               int l = j + k;
               itemstack.shrink(k);
               itemstack2.setCount(l);
               this.merchantInventory.setInventorySlotContents(p_217053_1_, itemstack2);
               if (l >= p_217053_2_.getMaxStackSize()) {
                  break;
               }
            }
         }
      }

   }

   private boolean func_217050_b(ItemStack p_217050_1_, ItemStack p_217050_2_) {
      return p_217050_1_.getItem() == p_217050_2_.getItem() && ItemStack.areItemStackTagsEqual(p_217050_1_, p_217050_2_);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_217044_a(MerchantOffers p_217044_1_) {
      this.merchant.func_213703_a(p_217044_1_);
   }

   public MerchantOffers func_217051_h() {
      return this.merchant.getOffers();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_217042_i() {
      return this.field_217055_f;
   }
}