package kiba.bhc.items.consumables;

import core.upcraftlp.craftdev.api.item.Item;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class BaseHeartItem extends Item {

    protected final int healValue;

    public BaseHeartItem(String name, Integer healAmount) {
        super(name + "_heart");
        this.healValue = healAmount;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 30;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.EAT;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if(!worldIn.isRemote && entityLiving instanceof EntityPlayer){
            EntityPlayer  player = (EntityPlayer)entityLiving;
            player.heal(this.healValue);
            if(!player.isCreative()) stack.shrink(1);
        }
        return  stack;
    }
}
