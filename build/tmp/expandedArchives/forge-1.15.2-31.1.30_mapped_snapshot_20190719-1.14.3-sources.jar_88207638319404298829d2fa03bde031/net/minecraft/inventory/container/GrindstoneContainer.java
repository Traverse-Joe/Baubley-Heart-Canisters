package net.minecraft.inventory.container;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

public class GrindstoneContainer extends Container {
   private final IInventory field_217013_c = new CraftResultInventory();
   private final IInventory field_217014_d = new Inventory(2) {
      /**
       * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
       * it hasn't changed and skip it.
       */
      public void markDirty() {
         super.markDirty();
         GrindstoneContainer.this.onCraftMatrixChanged(this);
      }
   };
   private final IWorldPosCallable field_217015_e;

   public GrindstoneContainer(int p_i50080_1_, PlayerInventory p_i50080_2_) {
      this(p_i50080_1_, p_i50080_2_, IWorldPosCallable.DUMMY);
   }

   public GrindstoneContainer(int p_i50081_1_, PlayerInventory p_i50081_2_, final IWorldPosCallable p_i50081_3_) {
      super(ContainerType.GRINDSTONE, p_i50081_1_);
      this.field_217015_e = p_i50081_3_;
      this.addSlot(new Slot(this.field_217014_d, 0, 49, 19) {
         /**
          * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
          */
         public boolean isItemValid(ItemStack stack) {
            return stack.isDamageable() || stack.getItem() == Items.ENCHANTED_BOOK || stack.isEnchanted();
         }
      });
      this.addSlot(new Slot(this.field_217014_d, 1, 49, 40) {
         /**
          * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
          */
         public boolean isItemValid(ItemStack stack) {
            return stack.isDamageable() || stack.getItem() == Items.ENCHANTED_BOOK || stack.isEnchanted();
         }
      });
      this.addSlot(new Slot(this.field_217013_c, 2, 129, 34) {
         /**
          * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
          */
         public boolean isItemValid(ItemStack stack) {
            return false;
         }

         public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
            p_i50081_3_.consume((p_216944_1_, p_216944_2_) -> {
               int l = this.func_216942_a(p_216944_1_);

               while(l > 0) {
                  int i1 = ExperienceOrbEntity.getXPSplit(l);
                  l -= i1;
                  p_216944_1_.addEntity(new ExperienceOrbEntity(p_216944_1_, (double)p_216944_2_.getX(), (double)p_216944_2_.getY() + 0.5D, (double)p_216944_2_.getZ() + 0.5D, i1));
               }

               p_216944_1_.playEvent(1042, p_216944_2_, 0);
            });
            GrindstoneContainer.this.field_217014_d.setInventorySlotContents(0, ItemStack.EMPTY);
            GrindstoneContainer.this.field_217014_d.setInventorySlotContents(1, ItemStack.EMPTY);
            return stack;
         }

         private int func_216942_a(World p_216942_1_) {
            int l = 0;
            l = l + this.func_216943_e(GrindstoneContainer.this.field_217014_d.getStackInSlot(0));
            l = l + this.func_216943_e(GrindstoneContainer.this.field_217014_d.getStackInSlot(1));
            if (l > 0) {
               int i1 = (int)Math.ceil((double)l / 2.0D);
               return i1 + p_216942_1_.rand.nextInt(i1);
            } else {
               return 0;
            }
         }

         private int func_216943_e(ItemStack p_216943_1_) {
            int l = 0;
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(p_216943_1_);

            for(Entry<Enchantment, Integer> entry : map.entrySet()) {
               Enchantment enchantment = entry.getKey();
               Integer integer = entry.getValue();
               if (!enchantment.isCurse()) {
                  l += enchantment.getMinEnchantability(integer);
               }
            }

            return l;
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(p_i50081_2_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.addSlot(new Slot(p_i50081_2_, k, 8 + k * 18, 142));
      }

   }

   /**
    * Callback for when the crafting matrix is changed.
    */
   public void onCraftMatrixChanged(IInventory inventoryIn) {
      super.onCraftMatrixChanged(inventoryIn);
      if (inventoryIn == this.field_217014_d) {
         this.func_217010_e();
      }

   }

   private void func_217010_e() {
      ItemStack itemstack = this.field_217014_d.getStackInSlot(0);
      ItemStack itemstack1 = this.field_217014_d.getStackInSlot(1);
      boolean flag = !itemstack.isEmpty() || !itemstack1.isEmpty();
      boolean flag1 = !itemstack.isEmpty() && !itemstack1.isEmpty();
      if (!flag) {
         this.field_217013_c.setInventorySlotContents(0, ItemStack.EMPTY);
      } else {
         boolean flag2 = !itemstack.isEmpty() && itemstack.getItem() != Items.ENCHANTED_BOOK && !itemstack.isEnchanted() || !itemstack1.isEmpty() && itemstack1.getItem() != Items.ENCHANTED_BOOK && !itemstack1.isEnchanted();
         if (itemstack.getCount() > 1 || itemstack1.getCount() > 1 || !flag1 && flag2) {
            this.field_217013_c.setInventorySlotContents(0, ItemStack.EMPTY);
            this.detectAndSendChanges();
            return;
         }

         int j = 1;
         int i;
         ItemStack itemstack2;
         if (flag1) {
            if (itemstack.getItem() != itemstack1.getItem()) {
               this.field_217013_c.setInventorySlotContents(0, ItemStack.EMPTY);
               this.detectAndSendChanges();
               return;
            }

            Item item = itemstack.getItem();
            int k = itemstack.getMaxDamage() - itemstack.getDamage();
            int l = itemstack.getMaxDamage() - itemstack1.getDamage();
            int i1 = k + l + itemstack.getMaxDamage() * 5 / 100;
            i = Math.max(itemstack.getMaxDamage() - i1, 0);
            itemstack2 = this.func_217011_b(itemstack, itemstack1);
            if (!itemstack2.isRepairable()) i = itemstack.getDamage();
            if (!itemstack2.isDamageable() || !itemstack2.isRepairable()) {
               if (!ItemStack.areItemStacksEqual(itemstack, itemstack1)) {
                  this.field_217013_c.setInventorySlotContents(0, ItemStack.EMPTY);
                  this.detectAndSendChanges();
                  return;
               }

               j = 2;
            }
         } else {
            boolean flag3 = !itemstack.isEmpty();
            i = flag3 ? itemstack.getDamage() : itemstack1.getDamage();
            itemstack2 = flag3 ? itemstack : itemstack1;
         }

         this.field_217013_c.setInventorySlotContents(0, this.func_217007_a(itemstack2, i, j));
      }

      this.detectAndSendChanges();
   }

   private ItemStack func_217011_b(ItemStack p_217011_1_, ItemStack p_217011_2_) {
      ItemStack itemstack = p_217011_1_.copy();
      Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(p_217011_2_);

      for(Entry<Enchantment, Integer> entry : map.entrySet()) {
         Enchantment enchantment = entry.getKey();
         if (!enchantment.isCurse() || EnchantmentHelper.getEnchantmentLevel(enchantment, itemstack) == 0) {
            itemstack.addEnchantment(enchantment, entry.getValue());
         }
      }

      return itemstack;
   }

   private ItemStack func_217007_a(ItemStack p_217007_1_, int p_217007_2_, int p_217007_3_) {
      ItemStack itemstack = p_217007_1_.copy();
      itemstack.removeChildTag("Enchantments");
      itemstack.removeChildTag("StoredEnchantments");
      if (p_217007_2_ > 0) {
         itemstack.setDamage(p_217007_2_);
      } else {
         itemstack.removeChildTag("Damage");
      }

      itemstack.setCount(p_217007_3_);
      Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(p_217007_1_).entrySet().stream().filter((p_217012_0_) -> {
         return p_217012_0_.getKey().isCurse();
      }).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      EnchantmentHelper.setEnchantments(map, itemstack);
      itemstack.setRepairCost(0);
      if (itemstack.getItem() == Items.ENCHANTED_BOOK && map.size() == 0) {
         itemstack = new ItemStack(Items.BOOK);
         if (p_217007_1_.hasDisplayName()) {
            itemstack.setDisplayName(p_217007_1_.getDisplayName());
         }
      }

      for(int i = 0; i < map.size(); ++i) {
         itemstack.setRepairCost(RepairContainer.func_216977_d(itemstack.getRepairCost()));
      }

      return itemstack;
   }

   /**
    * Called when the container is closed.
    */
   public void onContainerClosed(PlayerEntity playerIn) {
      super.onContainerClosed(playerIn);
      this.field_217015_e.consume((p_217009_2_, p_217009_3_) -> {
         this.clearContainer(playerIn, p_217009_2_, this.field_217014_d);
      });
   }

   /**
    * Determines whether supplied player can use this container
    */
   public boolean canInteractWith(PlayerEntity playerIn) {
      return isWithinUsableDistance(this.field_217015_e, playerIn, Blocks.GRINDSTONE);
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
         ItemStack itemstack2 = this.field_217014_d.getStackInSlot(0);
         ItemStack itemstack3 = this.field_217014_d.getStackInSlot(1);
         if (index == 2) {
            if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            slot.onSlotChange(itemstack1, itemstack);
         } else if (index != 0 && index != 1) {
            if (!itemstack2.isEmpty() && !itemstack3.isEmpty()) {
               if (index >= 3 && index < 30) {
                  if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
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
}