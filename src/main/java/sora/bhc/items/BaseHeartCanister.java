package sora.bhc.items;

import net.minecraft.item.ItemStack;
import sora.bhc.handler.ConfigHandler;
import sora.bhc.util.HeartType;


public class BaseHeartCanister extends BaseItem {

    public final HeartType type;

    public BaseHeartCanister(String name,HeartType type) {
        super(name,new Properties());
        this.type = type;
    }

    /*@Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        //wanna make it customizable by default in the super like i did with my heart
        return bauble;
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        if(OLDCONFIG.canEquipHeartCanisterWithoutAmulet){
            return true;
        }
        return false;
    }
*/
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return ConfigHandler.general.heartStackSize.get();
    }
}

