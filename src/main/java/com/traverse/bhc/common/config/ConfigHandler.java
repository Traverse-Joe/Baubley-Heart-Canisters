package com.traverse.bhc.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Integer> heartStackSize;
        public final ForgeConfigSpec.ConfigValue<Double> boneDropRate;
        public final ForgeConfigSpec.ConfigValue<Double> echoShardDropRate;
        public final ForgeConfigSpec.ConfigValue<Double> soulHeartReturnChance;

        // Heart Patch - Heal Amounts
        public final ForgeConfigSpec.ConfigValue<Integer> redPatchHealAmount;
        public final ForgeConfigSpec.ConfigValue<Integer> yellowPatchHealAmount;
        public final ForgeConfigSpec.ConfigValue<Integer> greenPatchHealAmount;
        public final ForgeConfigSpec.ConfigValue<Integer> bluePatchHealAmount;

        // Heart Patch - Cooldowns (in seconds)
        public final ForgeConfigSpec.ConfigValue<Integer> redPatchCooldown;
        public final ForgeConfigSpec.ConfigValue<Integer> yellowPatchCooldown;
        public final ForgeConfigSpec.ConfigValue<Integer> greenPatchCooldown;
        public final ForgeConfigSpec.ConfigValue<Integer> bluePatchCooldown;

        General(ForgeConfigSpec.Builder builder) {
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

            builder.push("Heart Patches");
            builder.comment("Heal amounts are in half-hearts (e.g. 2 = 1 full heart)");
            redPatchHealAmount = builder
                    .comment("Amount of health restored by the Red Heart Patch")
                    .define("redPatchHealAmount", 2);
            yellowPatchHealAmount = builder
                    .comment("Amount of health restored by the Yellow Heart Patch")
                    .define("yellowPatchHealAmount", 6);
            greenPatchHealAmount = builder
                    .comment("Amount of health restored by the Green Heart Patch")
                    .define("greenPatchHealAmount", 10);
            bluePatchHealAmount = builder
                    .comment("Amount of health restored by the Blue Heart Patch")
                    .define("bluePatchHealAmount", 20);

            builder.comment("Cooldowns are in seconds");
            redPatchCooldown = builder
                    .comment("Cooldown in seconds for the Red Heart Patch")
                    .define("redPatchCooldown", 5);
            yellowPatchCooldown = builder
                    .comment("Cooldown in seconds for the Yellow Heart Patch")
                    .define("yellowPatchCooldown", 10);
            greenPatchCooldown = builder
                    .comment("Cooldown in seconds for the Green Heart Patch")
                    .define("greenPatchCooldown", 20);
            bluePatchCooldown = builder
                    .comment("Cooldown in seconds for the Blue Heart Patch")
                    .define("bluePatchCooldown", 30);
            builder.pop();
        }
    }

    public static class BHCServer {
        public final ForgeConfigSpec.ConfigValue<Boolean> allowStartingHeathTweaks;
        public final ForgeConfigSpec.ConfigValue<Integer> startingHealth;

        BHCServer(ForgeConfigSpec.Builder builder) {
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

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final General general = new General(BUILDER);
    public static final BHCServer server = new BHCServer(SERVER_BUILDER);
    public static final ForgeConfigSpec configSpec = BUILDER.build();
    public static final ForgeConfigSpec serverConfigSpec = SERVER_BUILDER.build();
}
