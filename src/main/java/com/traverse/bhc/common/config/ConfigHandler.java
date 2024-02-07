package com.traverse.bhc.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigHandler {

    public static class General {
        public final ModConfigSpec.ConfigValue<Integer> heartStackSize;
        public final ModConfigSpec.ConfigValue<Double> boneDropRate;
        public final ModConfigSpec.ConfigValue<Double> echoShardDropRate;
        public final ModConfigSpec.ConfigValue<Double> soulHeartReturnChance;

        General(ModConfigSpec.Builder builder) {
            builder.push("General");
            heartStackSize = builder
                    .comment("The maximum stacksize for heart canisters, also the maximum amount of full hearts you can get per heart container type")
                    .define("heartStackSize", 10);
            boneDropRate = builder
                    .comment("How often do wither bones Drop? (1.0 = 100 % and 0.0 means 0%)")
                    .define("boneDropRate", 0.15);
            echoShardDropRate = builder
                    .comment("How often do echo shards drop from Warden? (1.0 = 100 % and 0.0 means 0%)")
                    .define("echoShardDropRate", 0.5);
            soulHeartReturnChance = builder
                    .comment("Chance for the Soul Heart to return a Blue Heart Canister after being broken")
                    .define("soulHeartReturnChance", 1.0);
            builder.pop();
        }
    }

    public static class BHCServer {
        public final ModConfigSpec.ConfigValue<Boolean> allowStartingHeathTweaks;
        public final ModConfigSpec.ConfigValue<Integer> startingHealth;

        BHCServer(ModConfigSpec.Builder builder) {
            builder.push("Server");
            allowStartingHeathTweaks = builder
                    .comment("Allow Starting Health Tweaks")
                    .define("allowStartingHealthTweaks", false);
            startingHealth = builder
                    .comment("Starting Health of Player (Default:20)")
                    .define("startingHealth", 20);
            builder.pop();
        }
    }

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    public static final General general = new General(BUILDER);
    public static final BHCServer server = new BHCServer(SERVER_BUILDER);
    public static final ModConfigSpec configSpec = BUILDER.build();
    public static final ModConfigSpec serverConfigSpec = SERVER_BUILDER.build();
}
