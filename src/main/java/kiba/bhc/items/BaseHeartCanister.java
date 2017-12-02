package kiba.bhc.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import core.upcraftlp.craftdev.api.item.Item;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;


public class BaseHeartCanister extends Item implements IBauble {
public final int heartIn;
    public BaseHeartCanister(String name , Integer amount){
        super(name);
        this.setMaxStackSize(10); //UPCRAFT BAUBLE STACK (NO NEED FOR NBT) //TODO does that work?
        this.heartIn = amount;

    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        //wanna make it customizable by default in the super like i did with my heart
        return BaubleType.TRINKET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        //HOW THE HELL DOES ONE.....MAKE HEARTS EXPAND THE PLAYERS MAX HEALTH
        //TODO increase base health
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

}

