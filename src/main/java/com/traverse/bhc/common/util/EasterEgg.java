package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class EasterEgg {

    public static void secretCode() {
        ItemProperties.register(RegistryHandler.BLADE_OF_VITALITIY.get(), new ResourceLocation(BaubleyHeartCanisters.MODID, "easter_egg"), (stack, level, entity, value) -> {
            String hoverName = stack.hasCustomHoverName() ? stack.getHoverName().getString().toLowerCase(Locale.ROOT) : "";
            float result = 0.0F;

            if (hoverName.contains("beautiful eyes")) {
                result = 1.0F;
            } else if (hoverName.contains("traverse")) {
                result = 2.0F;
            } else if (hoverName.contains("jamiscus")) {
                result = 3.0F;
            }

            return result;
        });
    }
}
