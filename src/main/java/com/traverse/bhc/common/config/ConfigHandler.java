package com.traverse.bhc.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigHandler {

    public static class General {
        public final ModConfigSpec.IntValue heartStackSize;
        public final ModConfigSpec.DoubleValue boneDropRate;
        public final ModConfigSpec.DoubleValue echoShardDropRate;
        public final ModConfigSpec.DoubleValue soulHeartReturnChance;

        // Soul Heart Crystal
        public final ModConfigSpec.IntValue vitalicCrystalMaxCharge;
        public final ModConfigSpec.IntValue vitalicChargePerKill;
        public final ModConfigSpec.IntValue vitalicBuddingCrystalMaxCharge;
        public final ModConfigSpec.IntValue vitalicOrbBuddingCrystalRadius;

        // Heart Patch - Heal Amounts
        public final ModConfigSpec.IntValue redPatchHealAmount;
        public final ModConfigSpec.IntValue yellowPatchHealAmount;
        public final ModConfigSpec.IntValue greenPatchHealAmount;
        public final ModConfigSpec.IntValue bluePatchHealAmount;

        // Heart Patch - Cooldowns (in seconds)
        public final ModConfigSpec.IntValue redPatchCooldown;
        public final ModConfigSpec.IntValue yellowPatchCooldown;
        public final ModConfigSpec.IntValue greenPatchCooldown;
        public final ModConfigSpec.IntValue bluePatchCooldown;

        General(ModConfigSpec.Builder builder) {
            builder.push("General");
            heartStackSize = builder
                    .comment("The maximum stacksize for heart canisters, also the maximum amount of full hearts you can get per heart container type")
                    .defineInRange("heartStackSize", 10, 0, 99);
            boneDropRate = builder
                    .comment("How often do wither bones Drop? (1.0 = 100 % and 0.0 means 0%)")
                    .defineInRange("boneDropRate", 0.18, 0, 1);
            echoShardDropRate = builder
                    .comment("How often do echo shards drop from Warden? (1.0 = 100 % and 0.0 means 0%)")
                    .defineInRange("echoShardDropRate", 0.5, 0, 1);
            soulHeartReturnChance = builder
                    .comment("Chance for the Soul Heart to return a Blue Heart Canister after being broken")
                    .defineInRange("soulHeartReturnChance", 1.0, 0, 1);
            vitalicCrystalMaxCharge = builder
                    .comment("Maximum Vitalic Source charge a Soul Heart Crystal can hold")
                    .defineInRange("vitalicCrystalMaxCharge", 100, 1, Integer.MAX_VALUE);
            vitalicChargePerKill = builder
                    .comment("Amount of Vitalic Source charge granted per matching mob kill")
                    .defineInRange("vitalicChargePerKill", 1, 1, Integer.MAX_VALUE);
            vitalicBuddingCrystalMaxCharge = builder
                    .comment("Maximum Vitalic Source charge a Budding Crystal block can hold")
                    .defineInRange("vitalicBuddingCrystalMaxCharge", 300, 1, Integer.MAX_VALUE);
            vitalicOrbBuddingCrystalRadius = builder
                    .comment("Block radius around a mob kill that a Vitalic Orb will look for a matching Budding Crystal before falling back to the killer player")
                    .defineInRange("vitalicOrbBuddingCrystalRadius", 8, 0, 64);
            builder.pop();

            builder.push("Heart Patches");
            builder.comment("Heal amounts are in half-hearts (e.g. 2 = 1 full heart)");
            redPatchHealAmount = builder
                    .comment("Amount of health restored by the Red Heart Patch")
                    .defineInRange("redPatchHealAmount", 2, 0, Integer.MAX_VALUE);
            yellowPatchHealAmount = builder
                    .comment("Amount of health restored by the Yellow Heart Patch")
                    .defineInRange("yellowPatchHealAmount", 6, 0, Integer.MAX_VALUE);
            greenPatchHealAmount = builder
                    .comment("Amount of health restored by the Green Heart Patch")
                    .defineInRange("greenPatchHealAmount", 10, 0, Integer.MAX_VALUE);
            bluePatchHealAmount = builder
                    .comment("Amount of health restored by the Blue Heart Patch")
                    .defineInRange("bluePatchHealAmount", 20, 0, Integer.MAX_VALUE);

            builder.comment("Cooldowns are in seconds");
            redPatchCooldown = builder
                    .comment("Cooldown in seconds for the Red Heart Patch")
                    .defineInRange("redPatchCooldown", 5, 0, Integer.MAX_VALUE);
            yellowPatchCooldown = builder
                    .comment("Cooldown in seconds for the Yellow Heart Patch")
                    .defineInRange("yellowPatchCooldown", 10, 0, Integer.MAX_VALUE);
            greenPatchCooldown = builder
                    .comment("Cooldown in seconds for the Green Heart Patch")
                    .defineInRange("greenPatchCooldown", 20, 0, Integer.MAX_VALUE);
            bluePatchCooldown = builder
                    .comment("Cooldown in seconds for the Blue Heart Patch")
                    .defineInRange("bluePatchCooldown", 30, 0, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static class BHCServer {
        public final ModConfigSpec.ConfigValue<Boolean> allowStartingHealthTweaks;
        public final ModConfigSpec.ConfigValue<Integer> startingHealth;

        BHCServer(ModConfigSpec.Builder builder) {
            builder.push("Server");
            allowStartingHealthTweaks = builder
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
