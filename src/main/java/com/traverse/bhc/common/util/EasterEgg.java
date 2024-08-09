package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Locale;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EasterEgg {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(RegistryHandler.BLADE_OF_VITALITY.get(), BaubleyHeartCanisters.id("easter_egg"), (stack, level, entity, value) -> {
            if (!stack.has(DataComponents.CUSTOM_NAME)) {
                return 0.0F;
            }

            var component = stack.get(DataComponents.CUSTOM_NAME);
            if (component != null) {
                String hoverName = component.getString().toLowerCase(Locale.ROOT);

                if (hoverName.contains("beautiful eyes")) {
                    return 1.0F;
                } else if (hoverName.contains("traverse")) {
                    return 2.0F;
                } else if (hoverName.contains("jamiscus")) {
                    return 3.0F;
                }
            }

            return 0.0F;
        }));
    }
}
