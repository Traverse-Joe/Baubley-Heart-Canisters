package kiba.bhc.init;

import kiba.bhc.items.BaseHeartCanister;
import kiba.bhc.items.BaseItem;
import kiba.bhc.items.consumables.BaseHeartItem;
import kiba.bhc.items.consumables.ItemFoodRelicApple;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

import java.util.UUID;


public class ModItems {

    public static  Item RED_HEART_CANISTER = new BaseHeartCanister("red", UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3"));
    public static  Item ORANGE_HEART_CANISTER = new BaseHeartCanister("orange", UUID.fromString("147d8bca-a33c-440e-8985-22207747ffd8"));
    public static  Item GREEN_HEART_CANISTER = new BaseHeartCanister("green", UUID.fromString("85084e13-daa0-4340-aebc-5d1e1dedc3d9"));
    public static  Item BLUE_HEART_CANISTER = new BaseHeartCanister("blue", UUID.fromString("268edd4a-e364-4afe-8e1e-7212734a7025"));
    public static  Item WITHER_BONE = new BaseItem("wither_bone");
    public static  Item CANISTER = new BaseItem("canister");
    public static  ItemFood RELIC_APPLE = new ItemFoodRelicApple();

    public static  Item RED_HEART = new BaseHeartItem("red", 10);
    public static  Item ORANGE_HEART = new BaseHeartItem("orange",20);
    public static  Item GREEN_HEART = new BaseHeartItem("green",30);
    public static  Item BLUE_HEART = new BaseHeartItem("blue",40);
    //TODO add blue heart item!
}
