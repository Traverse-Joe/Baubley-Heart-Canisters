package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
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

        event.enqueueWork(() ->
        {
            ItemProperties.register(RegistryHandler.VIGOR_BOW.get(),
                    ResourceLocation.withDefaultNamespace("pull"), (itemStack, world, livingEntity, num) -> {
                        if (livingEntity == null) {
                            return 0.0F;
                        } else {
                            return livingEntity.getUseItem() != itemStack ? 0.0F : (float) (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
                        }
                    });
            ItemProperties.register(RegistryHandler.VIGOR_BOW.get(), ResourceLocation.withDefaultNamespace("pulling"), (itemStack, world, livingEntity, num)
                    -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
        });
    }

}
