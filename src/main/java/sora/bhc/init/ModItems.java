package sora.bhc.init;

import sora.bhc.items.BaseHeartCanister;
import sora.bhc.items.BaseItem;
import sora.bhc.items.ItemHeartAmulet;
import sora.bhc.items.consumables.BaseHeartItem;
import sora.bhc.items.consumables.ItemFoodRelicApple;
import sora.bhc.util.HeartType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;


public class ModItems {

    public static  Item RED_HEART_CANISTER = new BaseHeartCanister(HeartType.RED);
    public static  Item YELLOW_HEART_CANISTER = new BaseHeartCanister(HeartType.YELLOW);
    public static  Item GREEN_HEART_CANISTER = new BaseHeartCanister(HeartType.GREEN);
    public static  Item BLUE_HEART_CANISTER = new BaseHeartCanister(HeartType.BLUE);
    public static  Item WITHER_BONE = new BaseItem("wither_bone");
    public static  Item CANISTER = new BaseItem("canister");
    public static  ItemFood RELIC_APPLE = new ItemFoodRelicApple();
    public static BaseItem HEART_AMULET = new ItemHeartAmulet();

    public static  Item RED_HEART = new BaseHeartItem(HeartType.RED);
    public static  Item YELLOW_HEART = new BaseHeartItem(HeartType.YELLOW);
    public static  Item GREEN_HEART = new BaseHeartItem(HeartType.GREEN);
    public static  Item BLUE_HEART = new BaseHeartItem(HeartType.BLUE);

}
