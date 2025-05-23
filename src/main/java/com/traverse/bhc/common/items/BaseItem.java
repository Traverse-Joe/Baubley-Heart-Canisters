package com.traverse.bhc.common.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class BaseItem extends Item {

    public BaseItem(Properties properties) {
        super(properties);
    }

    public BaseItem(Properties properties, int maxCount) {
        super(properties.stacksTo(maxCount));
    }

    public BaseItem(Properties properties, int hunger, float saturation) {
        super(properties.food(new FoodProperties.Builder().saturationModifier(saturation).alwaysEdible().nutrition(hunger).build()));
    }
}
