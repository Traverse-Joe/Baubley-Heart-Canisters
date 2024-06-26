package com.traverse.bhc.client.proxy;

import com.traverse.bhc.client.screens.BladeOfVitalityScreen;
import com.traverse.bhc.client.screens.HeartAmuletScreen;
import com.traverse.bhc.client.screens.SoulHeartAmuletScreen;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.proxy.CommonProxy;
import com.traverse.bhc.common.util.EasterEgg;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void doClientStuff() {
        MenuScreens.register(RegistryHandler.HEART_AMUlET_CONTAINER.get(), HeartAmuletScreen::new);
        MenuScreens.register(RegistryHandler.SOUL_HEART_AMUlET_CONTAINER.get(), SoulHeartAmuletScreen::new);
        MenuScreens.register(RegistryHandler.BLADE_OF_VITALITY_CONTAINER.get(), BladeOfVitalityScreen::new);
       EasterEgg.secretCode();
        super.doClientStuff();
    }
}
