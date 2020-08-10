package sora.bhc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sora.bhc.handler.BHCConfig;
import sora.bhc.handler.ConfigHandler;
import sora.bhc.proxy.ClientProxy;
import sora.bhc.proxy.CommonProxy;
import sora.bhc.proxy.IProxy;

import java.io.*;

@Mod(BaubleyHeartCanisters.MODID)
public class BaubleyHeartCanisters {

  private static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new CommonProxy());
  public static final String MODID = "bhc";
  public static final Logger LOGGER = LogManager.getLogger();  public static BHCConfig config;

  public BaubleyHeartCanisters() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.configSpec);
    MinecraftForge.EVENT_BUS.register(this);
  }

  public static IProxy getProxy() {
    return proxy;
  }

  private void setup(FMLCommonSetupEvent event) {
    proxy.CommonSetup();
    jsonSetup();
  }

  private void jsonSetup() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    File folder = FMLPaths.CONFIGDIR.get().resolve("bhc").toFile();
    folder.mkdirs();
    File file = folder.toPath().resolve("drops.json").toFile();
    try{
      if (file.exists()){
          config = gson.fromJson(new FileReader(file),BHCConfig.class);
        return;
      }
      config = new BHCConfig();
      config.addEntrytoMap("red", "hostile", 0.05);
      config.addEntrytoMap("yellow", "boss", 1.0);
      config.addEntrytoMap("green", "dragon", 1.0);
      config.addEntrytoMap("blue", "minecraft:evocation_illager", 1.0);
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
