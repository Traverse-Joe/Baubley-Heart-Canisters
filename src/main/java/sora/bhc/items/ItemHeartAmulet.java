package sora.bhc.items;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import sora.bhc.BaubleyHeartCanisters;
import sora.bhc.container.HeartPendantContainer;
import sora.bhc.handler.OLDCONFIG;
import sora.bhc.util.BHCGuiHandler;
import sora.bhc.util.HeartType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class ItemHeartAmulet extends BaseItem implements IBauble {
    public ItemHeartAmulet() {
        super("heart_amulet");
        this.setMaxStackSize(1);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return OLDCONFIG.heartCanisterBaubleType;
    }
/*
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(playerIn.isSneaking()) { //sneak to open inventory, (regular right click to equip?)
            ItemStack stack = playerIn.getHeldItem(handIn);
            if(!worldIn.isRemote) playerIn.openGui(BaubleyHeartCanisters.INSTANCE, BHCGuiHandler.PENDANT_GUI, worldIn, handIn.ordinal(), 0, 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        else return super.onItemRightClick(worldIn, playerIn, handIn);
    } */

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        ItemStack toEquip = stack.copy();
        toEquip.setCount(1);

        if(player.isSneaking()) { //sneak to open inventory, (regular right click to equip?)
            if(!world.isRemote) player.openGui(BaubleyHeartCanisters.INSTANCE, BHCGuiHandler.PENDANT_GUI, world, hand.ordinal(), 0, 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
//Code from Botania Thank you Vazkii

      /* if(canEquip(toEquip, player)) {
            if(world.isRemote)
                return ActionResult.newResult(EnumActionResult.SUCCESS, stack);

            IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
            for(int i = 0; i < baubles.getSlots(); i++) {
                if(baubles.isItemValidForSlot(i, toEquip, player)) {
                    ItemStack stackInSlot = baubles.getStackInSlot(i);
                    if(stackInSlot.isEmpty() || ((IBauble) stackInSlot.getItem()).canUnequip(stackInSlot, player)) {
                        baubles.setStackInSlot(i, ItemStack.EMPTY);

                        baubles.setStackInSlot(i, toEquip);
                        ((IBauble) toEquip.getItem()).onEquipped(toEquip, player);

                        stack.shrink(1);
                        player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundCategory.PLAYERS, 0.5F, 1.0F );

                        if(!stackInSlot.isEmpty()) {
                            ((IBauble) stackInSlot.getItem()).onUnequipped(stackInSlot, player);

                            if(stack.isEmpty()) {
                                return ActionResult.newResult(EnumActionResult.SUCCESS, stackInSlot);
                            } else {
                                ItemHandlerHelper.giveItemToPlayer(player, stackInSlot);
                            }
                        }

                        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                    }
                }
            }
        }*/

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }


    public int[] getHeartCount(ItemStack stack) {
        if(stack.hasTagCompound()) {
            NBTTagCompound nbt = stack.getTagCompound();
            if(nbt.hasKey(HeartPendantContainer.HEART_AMOUNT, Constants.NBT.TAG_INT_ARRAY)) return nbt.getIntArray(HeartPendantContainer.HEART_AMOUNT);
        }
        return new int[HeartType.values().length];
    }

}
