package kiba.bhc.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.gui.container.ContainerPendant;
import kiba.bhc.handler.ConfigHandler;
import kiba.bhc.util.BHCGuiHandler;
import kiba.bhc.util.HeartType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemHeartAmulet extends BaseItem implements IBauble {
    public ItemHeartAmulet() {
        super("heart_amulet");
        this.setMaxStackSize(1);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return ConfigHandler.heartCanisterBaubleType;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(playerIn.isSneaking()) { //sneak to open inventory, (regular right click to equip?)
            ItemStack stack = playerIn.getHeldItem(handIn);
            if(!worldIn.isRemote) playerIn.openGui(BaubleyHeartCanisters.INSTANCE, BHCGuiHandler.PENDANT_GUI, worldIn, handIn.ordinal(), 0, 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        else return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public int[] getHeartCount(ItemStack stack) {
        if(stack.hasTagCompound()) {
            NBTTagCompound nbt = stack.getTagCompound();
            if(nbt.hasKey(ContainerPendant.HEART_AMOUNT, Constants.NBT.TAG_INT_ARRAY)) return nbt.getIntArray(ContainerPendant.HEART_AMOUNT);
        }
        return new int[HeartType.values().length];
    }

}
