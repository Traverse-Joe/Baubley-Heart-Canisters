package kiba.bhc.handler;

import baubles.api.BaubleType;
import kiba.bhc.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MODID, category = "options", name = "bhc/BaubleyHeartCanisters")
@Config.LangKey(Reference.MODID + ".config.title")

public class ConfigHandler {

  @Config.Comment("The maximum stacksize for heart canisters, also the maximum amount of full hearts you can get per heart container type")
    public static int heartStackSize = 10;

    @Config.Comment("How often do wither bones Drop? (1.0 = 100 % and 0.0 means 0%)")
    public static double boneDropRate = 0.15;

    @Config.Comment("What Type of Bauble is the Heart Amulet?")
    public static BaubleType heartCanisterBaubleType = BaubleType.AMULET;

    @Config.Comment("Allow Starting Health Tweaks")
    public static boolean allowStartingHealthTweaks = false;

    @Config.Comment("Starting Health of Player (Default:20)")
    public static int startingHealth = 20;

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
