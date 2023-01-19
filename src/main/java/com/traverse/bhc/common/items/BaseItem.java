package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class BaseItem extends Item {

    public BaseItem() {
        super(new Item.Properties());
    }

    public BaseItem(int maxCount) {
        super(new Item.Properties().stacksTo(maxCount));
    }

    public BaseItem(int hunger, float saturation) {
        super(new Item.Properties().food(new FoodProperties.Builder().saturationMod(saturation).alwaysEat ().nutrition(hunger).build()));
    }
}
