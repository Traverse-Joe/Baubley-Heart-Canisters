package com.traverse.bhc.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Integer> heartStackSize;
        public final ForgeConfigSpec.ConfigValue<Double> boneDropRate;
        public final ForgeConfigSpec.ConfigValue<Boolean> allowStartingHeathTweaks;
        //public final ForgeConfigSpec.ConfigValue<Integer> startingHealth;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            heartStackSize = builder
                    .comment("The maximum stacksize for heart canisters, also the maximum amount of full hearts you can get per heart container type")
                    .define("heartStackSize", 10);
            boneDropRate = builder
                    .comment("How often do wither bones Drop? (1.0 = 100 % and 0.0 means 0%)")
                    .define("boneDropRate", 0.15);
            allowStartingHeathTweaks = builder
                    .comment("Allow Starting Health Tweaks")
                    .define("allowStartingHealthTweaks", false);
/*            startingHealth = builder
                    .comment("Starting Health of Player (Default:20)")
                    .define("startingHealth", 20);*/
            builder.pop();
        }
    }

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General general = new General(BUILDER);
    public static final ForgeConfigSpec configSpec = BUILDER.build();
}
