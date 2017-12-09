package kiba.bhc;

import kiba.bhc.init.ModItems;
import kiba.bhc.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Reference.MODID,
        version = Reference.VERSION,
        name = Reference.MODNAME,
        dependencies = "required-after:baubles;required-after:mantle",
        acceptedMinecraftVersions = "[1.12,1.13)",
        certificateFingerprint = Reference.FINGERPRINT_KEY,
        updateJSON = Reference.UPDATE_URL
)
public class BaubleyHeartCanisters {



    public static final Logger log = LogManager.getLogger(Reference.MODID);

    @Mod.Instance
    public static BaubleyHeartCanisters INSTANCE;

    @SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
        log.info("Pre-Initialization finished.");
    }

    @Mod.EventHandler
    public static void Init(FMLInitializationEvent event){
        proxy.init(event);
        log.info("Initialization finished.");
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
        log.info("Post-Initialization finished.");
    }
    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(Reference.MODID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.RED_HEART_CANISTER);
        }
    };
}
