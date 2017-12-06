package kiba.bhc.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.util.BHCGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHeartAmulet extends BaseItem implements IBauble {
    public ItemHeartAmulet() {
        super("heart_amulet");

    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.AMULET;
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

    //TODO track health upgrades
}
