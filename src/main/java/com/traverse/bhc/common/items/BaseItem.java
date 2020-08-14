package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class BaseItem extends Item {

    public BaseItem() {
        super(new Item.Properties().group(BaubleyHeartCanisters.TAB));
    }

    public BaseItem(int hunger, float saturation){
        super(new Item.Properties().group(BaubleyHeartCanisters.TAB).food(new Food.Builder().saturation(saturation).setAlwaysEdible().hunger(hunger).build()));
    }
}
