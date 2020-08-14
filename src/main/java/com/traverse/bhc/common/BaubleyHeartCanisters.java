package com.traverse.bhc.common;

import com.traverse.bhc.client.screens.HeartAmuletScreen;
import com.traverse.bhc.common.util.ConfigHandler;
import com.traverse.bhc.common.util.RegistryHandler;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(BaubleyHeartCanisters.MODID)
public class BaubleyHeartCanisters
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "bhc";

    public BaubleyHeartCanisters() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.configSpec);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(RegistryHandler.HEART_AMUlET_CONTAINER.get(), HeartAmuletScreen::new);
    }

    public static final ItemGroup TAB = new ItemGroup("bhcTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.RED_HEART.get());
        }
    };

}
