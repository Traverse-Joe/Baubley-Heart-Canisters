package kiba.bhc;

import kiba.bhc.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = Reference.MODID,
        version = Reference.VERSION,
        name = Reference.MODNAME,
        dependencies = "required-after:baubles",
        acceptedMinecraftVersions = "(1.12,1.13)",
        certificateFingerprint = Reference.FINGERPRINT_KEY
)
public class BaubleyHeartCanisters {

    @Mod.Instance
    public static BaubleyHeartCanisters instance;
    @SidedProxy(clientSide = Reference.CLIENTPROXY , serverSide = Reference.COMMONPROXY)
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
