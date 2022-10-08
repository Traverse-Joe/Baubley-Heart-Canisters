package com.traverse.bhc.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.traverse.bhc.client.ClientBaubleyHeartCanisters;
import com.traverse.bhc.client.proxy.ClientProxy;
import com.traverse.bhc.common.config.BHCConfig;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.proxy.CommonProxy;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Mod(BaubleyHeartCanisters.MODID)
public class BaubleyHeartCanisters {

    public static final String MODID = "bhc";
    public static final CreativeModeTab TAB = new CreativeModeTab("bhcTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RegistryHandler.RED_HEART.get());
        }
    };

    public static BHCConfig config;
    public static final CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public BaubleyHeartCanisters() {
        RegistryHandler.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RegistryHandler.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.configSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.serverConfigSpec);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueue);

    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.doClientStuff();
        jsonSetup();
    }

    private void enqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("heartamulet").icon(ClientBaubleyHeartCanisters.SLOT_TEXTURE).build());
    }

    /*private void doClientStuff(final FMLClientSetupEvent event) {
        MenuScreens.register(RegistryHandler.HEART_AMUlET_CONTAINER.get(), HeartAmuletScreen::new);
        MenuScreens.register(RegistryHandler.SOUL_HEART_AMUlET_CONTAINER.get(), SoulHeartAmuletScreen::new);
    }*/

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
