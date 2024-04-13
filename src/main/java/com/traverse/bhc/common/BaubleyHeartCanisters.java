package com.traverse.bhc.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.traverse.bhc.client.proxy.ClientProxy;
import com.traverse.bhc.common.config.BHCConfig;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.proxy.CommonProxy;
import com.traverse.bhc.common.util.EasterEgg;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Mod(BaubleyHeartCanisters.MODID)
public class BaubleyHeartCanisters {

    public static final String MODID = "bhc";

    public static BHCConfig config;
    public static final CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public BaubleyHeartCanisters(IEventBus eventBus) {
        RegistryHandler.ITEMS.register(eventBus);
        RegistryHandler.TAB.register(eventBus);
        RegistryHandler.CONTAINERS.register(eventBus);
        RegistryHandler.RECIPESERIALIZER.register(eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.configSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.serverConfigSpec);

       eventBus.addListener(this::setup);
       eventBus.addListener(this::enqueue);

    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.doClientStuff();
        jsonSetup();
    }

    private void enqueue(InterModEnqueueEvent event) {
      //  InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("heartamulet").icon(ClientBaubleyHeartCanisters.SLOT_TEXTURE).build());
    }

    private void jsonSetup() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File folder = FMLPaths.CONFIGDIR.get().resolve("bhc").toFile();
        folder.mkdirs();
        File file = folder.toPath().resolve("drops.json").toFile();
        try {
            if (file.exists()) {
                config = gson.fromJson(new FileReader(file), BHCConfig.class);
                return;
            }
            config = new BHCConfig();
            config.addEntrytoMap("red", "hostile", 0.05);
            config.addEntrytoMap("yellow", "boss", 1.0);
            config.addEntrytoMap("green", "dragon", 1.0);
            config.addEntrytoMap("blue", "minecraft:warden", 1.0);
            String json = gson.toJson(config, BHCConfig.class);
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
