package sora.bhc.items;

import sora.bhc.BaubleyHeartCanisters;
import sora.bhc.handler.ConfigHandler;
import sora.bhc.util.HeartType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Locale;


public class BaseHeartCanister extends Item {

    public final HeartType type;


    public BaseHeartCanister(HeartType type) {
        super();
        String name = type.name().toLowerCase(Locale.ROOT) + "_heart_canister";
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(BaubleyHeartCanisters.CREATIVE_TAB);
        this.type = type;
    }

    /*@Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        //wanna make it customizable by default in the super like i did with my heart
        return bauble;
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        if(ConfigHandler.canEquipHeartCanisterWithoutAmulet){
            return true;
        }
        return false;
    }
*/
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

