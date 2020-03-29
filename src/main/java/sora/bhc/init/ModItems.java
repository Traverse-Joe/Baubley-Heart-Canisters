package sora.bhc.init;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import sora.bhc.items.BaseHeartCanister;
import sora.bhc.items.BaseItem;
import sora.bhc.items.consumables.BaseHeartItem;
import sora.bhc.items.consumables.ItemFoodRelicApple;
import sora.bhc.util.HeartType;


public class ModItems {

    public static  Item RED_HEART_CANISTER = new BaseHeartCanister("red_heart_canister",HeartType.RED);
    public static  Item YELLOW_HEART_CANISTER = new BaseHeartCanister("yellow_heart_canister",HeartType.YELLOW);
    public static  Item GREEN_HEART_CANISTER = new BaseHeartCanister("green_heart_canister",HeartType.GREEN);
    public static  Item BLUE_HEART_CANISTER = new BaseHeartCanister("blue_heart_canister",HeartType.BLUE);
    public static  Item WITHER_BONE = new BaseItem("wither_bone", new Item.Properties());
    public static  Item CANISTER = new BaseItem("canister",new Item.Properties());
    public static  Item RELIC_APPLE = new ItemFoodRelicApple();
   // public static BaseItem HEART_AMULET = new ItemHeartAmulet();
    //public static ContainerType<HeartPendantContainer> HEART_PENANT_CONTAINER;

    public static  Item RED_HEART = new BaseHeartItem("red_heart",HeartType.RED);
    public static  Item YELLOW_HEART = new BaseHeartItem("yellow_heart",HeartType.YELLOW);
    public static  Item GREEN_HEART = new BaseHeartItem("green_heart",HeartType.GREEN);
    public static  Item BLUE_HEART = new BaseHeartItem("blue_heart",HeartType.BLUE);

}
