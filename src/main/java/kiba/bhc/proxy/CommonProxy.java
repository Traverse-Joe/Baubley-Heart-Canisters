package kiba.bhc.proxy;

import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.init.ModItems;
import kiba.bhc.util.BHCGuiHandler;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event){
        NetworkRegistry.INSTANCE.registerGuiHandler(BaubleyHeartCanisters.INSTANCE, new BHCGuiHandler());
    }

    public void init(FMLInitializationEvent event){

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register <Item> event){
        event.getRegistry().register(ModItems.RED_HEART);
        event.getRegistry().register(ModItems.ORANGE_HEART);
        event.getRegistry().register(ModItems.GREEN_HEART);
        event.getRegistry().register(ModItems.BLUE_HEART);

        event.getRegistry().register(ModItems.CANISTER);
        event.getRegistry().register(ModItems.RED_HEART_CANISTER);
        event.getRegistry().register(ModItems.ORANGE_HEART_CANISTER);
        event.getRegistry().register(ModItems.GREEN_HEART_CANISTER);
        event.getRegistry().register(ModItems.BLUE_HEART_CANISTER);

        event.getRegistry().register(ModItems.RELIC_APPLE);
        event.getRegistry().register(ModItems.WITHER_BONE);
        event.getRegistry().register(ModItems.HEART_AMULET);
    }
}
