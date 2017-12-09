package kiba.bhc.proxy;

import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.handler.DropHandler;
import kiba.bhc.util.BHCGuiHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public static boolean TINKERS_CONSTRUCT_INSTALLED;

    public void preInit(FMLPreInitializationEvent event){
        NetworkRegistry.INSTANCE.registerGuiHandler(BaubleyHeartCanisters.INSTANCE, new BHCGuiHandler());
    }

    public void init(FMLInitializationEvent event){

    }

    public void postInit(FMLPostInitializationEvent event) {
        TINKERS_CONSTRUCT_INSTALLED = Loader.isModLoaded("tconstruct");
    }
}
