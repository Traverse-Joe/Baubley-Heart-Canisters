package kiba.bhc.proxy;

import kiba.bhc.handler.BaubleyHeartCanistersEventHandler;
import kiba.bhc.init.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event){
        ModItems.init();

    }

    public void Init(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new BaubleyHeartCanistersEventHandler());

    }

    public void registerRenderers() {

    }
}
