package kiba.bhc.init;

import kiba.bhc.Reference;
import kiba.bhc.items.BaseHeartCanister;
import kiba.bhc.items.consumables.BaseHeartItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.UUID;

@GameRegistry.ObjectHolder(Reference.MODID)
public class ModItems {

    public static final Item RED_HEART_CANISTER = new BaseHeartCanister("red", UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3"));
    public static final Item ORANGE_HEART_CANISTER = new BaseHeartCanister("orange", UUID.fromString("147d8bca-a33c-440e-8985-22207747ffd8"));
    public static final Item GREEN_HEART_CANISTER = new BaseHeartCanister("green", UUID.fromString("85084e13-daa0-4340-aebc-5d1e1dedc3d9"));

    public static final Item RED_HEART = new BaseHeartItem("red", 10);
    public static final Item ORANGE_HEART = new BaseHeartItem("orange",20);
    public static final Item GREEN_HEART = new BaseHeartItem("green",30);
}
