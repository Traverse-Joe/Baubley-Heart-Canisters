package kiba.bhc.init;

import kiba.bhc.items.BaseHeartCanister;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static BaseHeartCanister itemRedHeartCanister;

    public static void init(){
        itemRedHeartCanister = new BaseHeartCanister("red_heart_canister", 2);
    }
    @SideOnly(Side.CLIENT)
    public static void initModels(){
        itemRedHeartCanister.initModel();
    }
}
