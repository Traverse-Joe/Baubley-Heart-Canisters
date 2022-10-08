package com.traverse.bhc.client.proxy;

import com.traverse.bhc.client.ClientBaubleyHeartCanisters;
import com.traverse.bhc.client.screens.HeartAmuletScreen;
import com.traverse.bhc.client.screens.SoulHeartAmuletScreen;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.proxy.CommonProxy;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void doClientStuff() {
        MenuScreens.register(RegistryHandler.HEART_AMUlET_CONTAINER.get(), HeartAmuletScreen::new);
        MenuScreens.register(RegistryHandler.SOUL_HEART_AMUlET_CONTAINER.get(), SoulHeartAmuletScreen::new);
        MinecraftForge.EVENT_BUS.register(new ClientBaubleyHeartCanisters());
        super.doClientStuff();
    }
}
