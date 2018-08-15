package kiba.bhc.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.handler.ConfigHandler;
import kiba.bhc.util.HeartType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Locale;


public class BaseHeartCanister extends Item implements IBauble {

    public final HeartType type;
    public final BaubleType bauble;

    public BaseHeartCanister(HeartType type, BaubleType bauble) {
        super();
        String name = type.name().toLowerCase(Locale.ROOT) + "_heart_canister";
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(BaubleyHeartCanisters.CREATIVE_TAB);
        this.bauble = bauble;
        this.type = type;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        //wanna make it customizable by default in the super like i did with my heart
        return bauble;
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return ConfigHandler.heartStackSize;
    }

    @Deprecated
    @Override
    public int getItemStackLimit() {
        return ConfigHandler.heartStackSize;
    }
}

