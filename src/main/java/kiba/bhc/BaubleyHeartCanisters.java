package kiba.bhc;

import kiba.bhc.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = BaubleyHeartCanisters.MODID , version = BaubleyHeartCanisters.VERSION , name = BaubleyHeartCanisters.MODNAME , dependencies = "required-after:baubles", acceptedMinecraftVersions = "(1.12,1.13)")
public class BaubleyHeartCanisters {
    public static final String MODID = "bhc";
    public static final String VERSION = "1.0";
    public static final String MODNAME = "Baubley Heart Canisters";
    public static final String CLIENTPROXY = "kiba.bhc.proxy.ClientProxy";
    public static final String COMMONPROXY = "kiba.bhc.proxy.CommonProxy";

    @Mod.Instance
    public static BaubleyHeartCanisters instance;
    @SidedProxy(clientSide = BaubleyHeartCanisters.CLIENTPROXY , serverSide = BaubleyHeartCanisters.COMMONPROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(this);
        proxy.preInit(event);

    }

    @Mod.EventHandler
    public static void Init(FMLInitializationEvent event){
        proxy.Init(event);

    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event){


    }
}
