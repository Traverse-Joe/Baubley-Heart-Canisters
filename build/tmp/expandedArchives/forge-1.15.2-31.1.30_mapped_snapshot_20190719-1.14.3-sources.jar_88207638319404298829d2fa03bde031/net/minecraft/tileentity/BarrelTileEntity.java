package net.minecraft.tileentity;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BarrelTileEntity extends LockableLootTileEntity {
   private NonNullList<ItemStack> field_213966_a = NonNullList.withSize(27, ItemStack.EMPTY);
   private int field_213967_b;

   private BarrelTileEntity(TileEntityType<?> p_i49963_1_) {
      super(p_i49963_1_);
   }

   public BarrelTileEntity() {
      this(TileEntityType.BARREL);
   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.field_213966_a);
      }

      return compound;
   }

   public void read(CompoundNBT compound) {
      super.read(compound);
      this.field_213966_a = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.field_213966_a);
      }

   }

   /**
    * Returns the number of slots in the inventory.
    */
   public int getSizeInventory() {
      return 27;
   }

   protected NonNullList<ItemStack> getItems() {
      return this.field_213966_a;
   }

   protected void setItems(NonNullList<ItemStack> itemsIn) {
      this.field_213966_a = itemsIn;
   }

   protected ITextComponent getDefaultName() {
      return new TranslationTextComponent("container.barrel");
   }

   protected Container createMenu(int id, PlayerInventory player) {
      return ChestContainer.createGeneric9X3(id, player, this);
   }

   public void openInventory(PlayerEntity player) {
      if (!player.isSpectator()) {
         if (this.field_213967_b < 0) {
            this.field_213967_b = 0;
         }

         ++this.field_213967_b;
         BlockState blockstate = this.getBlockState();
         boolean flag = blockstate.get(BarrelBlock.PROPERTY_OPEN);
         if (!flag) {
            this.func_213965_a(blockstate, SoundEvents.BLOCK_BARREL_OPEN);
            this.func_213963_a(blockstate, true);
         }

         this.func_213964_r();
      }

   }

   private void func_213964_r() {
      this.world.getPendingBlockTicks().scheduleTick(this.getPos(), this.getBlockState().getBlock(), 5);
   }

   public void func_213962_h() {
      int i = this.pos.getX();
      int j = this.pos.getY();
      int k = this.pos.getZ();
      this.field_213967_b = ChestTileEntity.func_213976_a(this.world, this, i, j, k);
      if (this.field_213967_b > 0) {
         this.func_213964_r();
      } else {
         BlockState blockstate = this.getBlockState();
         if (blockstate.getBlock() != Blocks.BARREL) {
            this.remove();
            return;
         }

         boolean flag = blockstate.get(BarrelBlock.PROPERTY_OPEN);
         if (flag) {
            this.func_213965_a(blockstate, SoundEvents.BLOCK_BARREL_CLOSE);
            this.func_213963_a(blockstate, false);
         }
      }

   }

   public void closeInventory(PlayerEntity player) {
      if (!player.isSpectator()) {
         --this.field_213967_b;
      }

   }

   private void func_213963_a(BlockState p_213963_1_, boolean p_213963_2_) {
      this.world.setBlockState(this.getPos(), p_213963_1_.with(BarrelBlock.PROPERTY_OPEN, Boolean.valueOf(p_213963_2_)), 3);
   }

   private void func_213965_a(BlockState p_213965_1_, SoundEvent p_213965_2_) {
      Vec3i vec3i = p_213965_1_.get(BarrelBlock.PROPERTY_FACING).getDirectionVec();
      double d0 = (double)this.pos.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
      double d1 = (double)this.pos.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
      double d2 = (double)this.pos.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
      this.world.playSound((PlayerEntity)null, d0, d1, d2, p_213965_2_, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
   }
}