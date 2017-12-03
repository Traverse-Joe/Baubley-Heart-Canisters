package kiba.bhc.items;

import kiba.bhc.BaubleyHeartCanisters;
import net.minecraft.item.Item;

public class BaseItem extends Item {
    public BaseItem(String name){
        super();
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(BaubleyHeartCanisters.CREATIVE_TAB);

    }
}
