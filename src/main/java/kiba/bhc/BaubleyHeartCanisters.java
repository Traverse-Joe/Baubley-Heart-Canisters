package kiba.bhc;

import kiba.bhc.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = Reference.MODID,
        version = Reference.VERSION,
        name = Reference.MODNAME,
        dependencies = "required-after:baubles;required-after:craftdevcore@[2.2.8,)",
        acceptedMinecraftVersions = "[1.12,1.13)",
        certificateFingerprint = Reference.FINGERPRINT_KEY
)
public class BaubleyHeartCanisters {

    @Mod.Instance
    public static BaubleyHeartCanisters INSTANCE;

    @SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public static void Init(FMLInitializationEvent event){
        proxy.init(event);

    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }
}
