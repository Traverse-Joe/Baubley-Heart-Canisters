package com.traverse.bhc.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.traverse.bhc.common.config.BHCConfig;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;

@Mod(BaubleyHeartCanisters.MODID)
public class BaubleyHeartCanisters {

    public static final String MODID = "bhc";

    private static final Logger LOGGER = LoggerFactory.getLogger(BaubleyHeartCanisters.class);

    public static BHCConfig config;

    public BaubleyHeartCanisters(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        RegistryHandler.ITEMS.register(modEventBus);
        RegistryHandler.TAB.register(modEventBus);
        RegistryHandler.CONTAINERS.register(modEventBus);
        RegistryHandler.RECIPESERIALIZER.register(modEventBus);
        RegistryHandler.DATA_COMPONENT_TYPES.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.STARTUP, ConfigHandler.configSpec);
        modContainer.registerConfig(ModConfig.Type.SERVER, ConfigHandler.serverConfigSpec);

       modEventBus.addListener(this::setup);
       if (dist.isClient()) {
           modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
       }
    }

    private void setup(final FMLCommonSetupEvent event) {
        jsonSetup();
    }

    private void jsonSetup() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        var configFile = FMLPaths.CONFIGDIR.get().resolve("bhc").resolve("drops.json");

        try {
            Files.createDirectories(configFile.getParent());
        } catch (IOException e) {
            throw new RuntimeException("unable to create %s config directory".formatted(BaubleyHeartCanisters.MODID), e);
        }

        try {
            if (Files.exists(configFile)) {
                try (var reader = Files.newBufferedReader(configFile)) {
                    config = gson.fromJson(reader, BHCConfig.class);
                }
                return;
            }
        } catch (IOException e) {
            LOGGER.error("Unable to read config file", e);
        }

        config = new BHCConfig();
        try (var writer = Files.newBufferedWriter(configFile)) {
            config.addEntrytoMap("red", "hostile", 0.15);
            config.addEntrytoMap("yellow", "boss", 1.0);
            config.addEntrytoMap("green", "dragon", 1.0);
            config.addEntrytoMap("blue", "minecraft:warden", 1.0);
            gson.toJson(config, writer);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write %s config".formatted(BaubleyHeartCanisters.MODID), e);
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
