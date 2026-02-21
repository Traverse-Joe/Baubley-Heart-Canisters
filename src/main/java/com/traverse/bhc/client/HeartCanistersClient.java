package com.traverse.bhc.client;

import com.traverse.bhc.client.easter.CustomNameProperty;
import com.traverse.bhc.client.screens.BladeOfVitalityScreen;
import com.traverse.bhc.client.screens.HeartAmuletScreen;
import com.traverse.bhc.client.screens.SoulHeartAmuletScreen;
import com.traverse.bhc.client.screens.VigorBowScreen;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, value = Dist.CLIENT)
public class HeartCanistersClient {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(RegistryHandler.HEART_AMUlET_CONTAINER.get(), HeartAmuletScreen::new);
        event.register(RegistryHandler.SOUL_HEART_AMUlET_CONTAINER.get(), SoulHeartAmuletScreen::new);
        event.register(RegistryHandler.BLADE_OF_VITALITY_CONTAINER.get(), BladeOfVitalityScreen::new);
        event.register(RegistryHandler.VIGOR_BOW_CONTAINER.get(), VigorBowScreen::new);
    }

    @SubscribeEvent
    public static void registerItemProperty(RegisterSelectItemModelPropertyEvent event) {
        event.register(Identifier.fromNamespaceAndPath(BaubleyHeartCanisters.MODID, "custom_name"), CustomNameProperty.TYPE);
    }
}
