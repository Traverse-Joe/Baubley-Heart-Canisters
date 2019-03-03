package kiba.bhc.handler;

import baubles.api.BaubleType;
import kiba.bhc.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MODID, category = "options", name = "BaubleyHeartCanisters")
@Config.LangKey(Reference.MODID + ".config.title")

public class ConfigHandler {

    @Config.Comment("The maximum stacksize for heart canisters, also the maximum amount of full hearts you can get per heart container type")
    public static int heartStackSize = 10;

    @Config.Comment("How often do red hearts Drop? (1.0 = 100 % and 0.0 means 0%)")
    public static double redDropRate = 0.05;

    @Config.Comment("How often do orange hearts Drop? (1.0 = 100 % and 0.0 means 0%)")
    public static double orangeDropRate = 1.00;

    @Config.Comment("How often do green hearts Drop? (1.0 = 100 % and 0.0 means 0%)")
    public static double greenDropRate = 1.00;

    @Config.Comment("How often do blue hearts Drop? (1.0 = 100 % and 0.0 means 0%)")
    public static double blueDropRate = 1.00;

    @Config.Comment("How often do wither bones Drop? (1.0 = 100 % and 0.0 means 0%)")
    public static double boneDropRate = 0.15;

    @Config.Comment("What Type of Bauble is the Heart Amulet?")
    public static BaubleType heartCanisterBaubleType = BaubleType.AMULET;

    @Config.Comment("Red Heart Canister Bauble Type (Requires Restart)")
    public static BaubleType redCanisterBaubleType = BaubleType.HEAD;

    @Config.Comment("Orange Heart Canister Bauble Type(Requires Restart)")
    public static BaubleType orangeCanisterBaubleType = BaubleType.BODY;

    @Config.Comment("Green Heart Canister Bauble Type (Requires Restart)")
    public static BaubleType greenCanisterBaubleType = BaubleType.CHARM;

    @Config.Comment("Blue Heart Canister Bauble Type (Requires Restart)")
    public static BaubleType blueCanisterBaubleType = BaubleType.BELT;

    @Config.Comment("Allow Starting Health Tweaks")
    public static boolean allowStartingHealthTweaks = false;

    @Config.Comment("Starting Health of Player (Default:20)")
    public static int startingHealth = 20;

    @Config.Comment("Can Equip Heart Canisters")
    public static boolean canEquipHeartCanisterWithoutAmulet = true;

    @Mod.EventBusSubscriber(modid = Reference.MODID)
    public static class Handler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(Reference.MODID)) {
                ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
            }
        }
    }
}
