package kiba.bhc;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = BaubleyHeartCanisters.MODID , version = BaubleyHeartCanisters.VERSION , name = BaubleyHeartCanisters.MODNAME , dependencies = "required-after:baubles", acceptedMinecraftVersions = "(1.12,1.13)")
public class BaubleyHeartCanisters {
    public static final String MODID = "bhc";
    public static final String VERSION = "1.0";
    public static final String MODNAME = "Baubley Heart Canisters";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){

    }

    @Mod.EventHandler
    public static void Init(FMLInitializationEvent event){

    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event){

    }
}
