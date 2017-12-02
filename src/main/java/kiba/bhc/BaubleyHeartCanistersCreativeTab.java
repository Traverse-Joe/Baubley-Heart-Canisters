package kiba.bhc;

import kiba.bhc.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BaubleyHeartCanistersCreativeTab extends CreativeTabs {
    public BaubleyHeartCanistersCreativeTab(){
        super(Reference.MODID);
    }


    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.RED_HEART_CANISTER);
    }
}
