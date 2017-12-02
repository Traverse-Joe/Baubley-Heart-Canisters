package kiba.bhc.init;

import kiba.bhc.items.BaseHeartCanister;
import kiba.bhc.items.consumables.BaseHeartItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static BaseHeartCanister itemRedHeartCanister;
    public static BaseHeartItem itemRedHeart;
    public static BaseHeartItem itemOrangeHeart;
    public static BaseHeartItem itemGreenHeart;

    public static void init(){
        itemRedHeartCanister = new BaseHeartCanister("red_heart_canister", 2);
        itemRedHeart = new BaseHeartItem("red_heart", 10);
        itemOrangeHeart = new BaseHeartItem("orange_heart",20);
        itemGreenHeart = new BaseHeartItem("green_heart",30);
    }
    @SideOnly(Side.CLIENT)
    public static void initModels(){
        itemRedHeartCanister.initModel();
        itemRedHeart.initModel();
        itemOrangeHeart.initModel();
        itemGreenHeart.initModel();
    }
}
