package sora.bhc.items;

import net.minecraft.util.ResourceLocation;
import sora.bhc.BaubleyHeartCanisters;
import net.minecraft.item.Item;
import sora.bhc.util.BaubleyHeartCanistersCreativeTab;

public class BaseItem extends Item {
    public BaseItem(String name, Properties properties){
        super(new Properties().group(BaubleyHeartCanistersCreativeTab.getInstance()));
        this.setRegistryName(new ResourceLocation(BaubleyHeartCanisters.MODID, name));
    }
}
