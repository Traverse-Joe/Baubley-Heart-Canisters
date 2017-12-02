package kiba.bhc.init;

import kiba.bhc.items.BaseHeartCanister;
import kiba.bhc.items.consumables.BaseHeartItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static Item RED_HEART_CANISTER = new BaseHeartCanister("red_heart_canister", 2);
    public static Item ORANGE_HEART_CANISTER = new BaseHeartCanister("orange_heart_canister", 2);
    public static Item GREEN_HEART_CANISTER = new BaseHeartCanister("green_heart_canister", 2);

    public static Item RED_HEART = new BaseHeartItem("red_heart", 10);
    public static Item ORANGE_HEART = new BaseHeartItem("orange_heart",20);
    public static Item GREEN_HEART = new BaseHeartItem("green_heart",30);
}
