package kiba.bhc.items.consumables;

import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.util.HeartType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Locale;

public class BaseHeartItem extends net.minecraft.item.Item {

    protected final HeartType type;

    public BaseHeartItem(HeartType type) {
        super();
        String name = type.name().toLowerCase(Locale.ROOT) + "_heart";
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(BaubleyHeartCanisters.CREATIVE_TAB);
        this.type = type;
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
            player.heal(this.type.healAmount);
            if(!player.isCreative()) stack.shrink(1);
        }
        return  stack;
    }
}
