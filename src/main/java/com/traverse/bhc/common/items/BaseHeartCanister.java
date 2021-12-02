package com.traverse.bhc.common.items;

import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.world.item.ItemStack;

public class BaseHeartCanister extends BaseItem {

    public HeartType type;
    public BaseHeartCanister(HeartType type){
        this.type = type;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return ConfigHandler.general.heartStackSize.get();
    }
}
