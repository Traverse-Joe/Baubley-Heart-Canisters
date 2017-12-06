package kiba.bhc.handler;

import kiba.bhc.Reference;
import net.minecraftforge.common.config.Config;

@Config(modid = Reference.MODID, category = "options")
@Config.LangKey(Reference.MODID + ".config.title")
public class ConfigHandler {

    @Config.Comment("How often do red hearts Drop? (1.0 = 100 % and 0.0 means 0%)[Default: 0.05]")
    public static double redDropRate = 0.05;
    @Config.Comment("How often do orange hearts Drop? (1.0 = 100 % and 0.0 means 0%)[Default: 1.00]")
    public static double orangeDropRate = 1.00;
    @Config.Comment("How often do green hearts Drop? (1.0 = 100 % and 0.0 means 0%)[Default: 1.00]")
    public static double greenDropRate = 1.00;
    @Config.Comment("How often do blue hearts Drop? (1.0 = 100 % and 0.0 means 0%)[Default: 1.00]")
    public static double blueDropRate = 1.00;
    @Config.Comment("How often do wither bones Drop? (1.0 = 100 % and 0.0 means 0%)[Default:0.15]")
    public static double boneDropRate = 0.15;
}
