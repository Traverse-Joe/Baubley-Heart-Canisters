package kiba.bhc.init;

import kiba.bhc.items.BaseHeartCanister;
import kiba.bhc.items.consumables.BaseHeartItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static BaseHeartCanister itemRedHeartCanister = new BaseHeartCanister("red_heart_canister", 2);
    public static BaseHeartItem itemRedHeart = new BaseHeartItem("red_heart", 10);
    public static BaseHeartItem itemOrangeHeart = new BaseHeartItem("orange_heart",20);
    public static BaseHeartItem itemGreenHeart = new BaseHeartItem("green_heart",30);

}
