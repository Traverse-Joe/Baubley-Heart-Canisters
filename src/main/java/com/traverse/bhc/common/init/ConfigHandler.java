package com.traverse.bhc.common.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {

    public static class General{
        public final ForgeConfigSpec.ConfigValue<Integer> heartStackSize;

        General(ForgeConfigSpec.Builder builder){
            builder.push("General");
            heartStackSize = builder
                    .comment("The maximum stacksize for heart canisters, also the maximum amount of full hearts you can get per heart container type")
                    .define("heartStackSize", 10);
            builder.pop();
        }
    }

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General general = new General(BUILDER);
    public static final ForgeConfigSpec configSpec = BUILDER.build();
}
