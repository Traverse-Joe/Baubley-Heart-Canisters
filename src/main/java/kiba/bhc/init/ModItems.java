package kiba.bhc.init;

import kiba.bhc.items.BaseHeartCanister;
import kiba.bhc.items.BaseItem;
import kiba.bhc.items.ItemHeartAmulet;
import kiba.bhc.items.consumables.BaseHeartItem;
import kiba.bhc.items.consumables.ItemFoodRelicApple;
import kiba.bhc.util.HeartType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;


public class ModItems {

    public static  Item RED_HEART_CANISTER = new BaseHeartCanister(HeartType.RED);
    public static  Item ORANGE_HEART_CANISTER = new BaseHeartCanister(HeartType.ORANGE);
    public static  Item GREEN_HEART_CANISTER = new BaseHeartCanister(HeartType.GREEN);
    public static  Item BLUE_HEART_CANISTER = new BaseHeartCanister(HeartType.BLUE);
    public static  Item WITHER_BONE = new BaseItem("wither_bone");
    public static  Item CANISTER = new BaseItem("canister");
    public static  ItemFood RELIC_APPLE = new ItemFoodRelicApple();
    public static BaseItem HEART_AMULET = new ItemHeartAmulet();

    public static  Item RED_HEART = new BaseHeartItem(HeartType.RED);
    public static  Item ORANGE_HEART = new BaseHeartItem(HeartType.ORANGE);
    public static  Item GREEN_HEART = new BaseHeartItem(HeartType.GREEN);
    public static  Item BLUE_HEART = new BaseHeartItem(HeartType.BLUE);

}
