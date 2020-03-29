package sora.bhc;

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
import sora.bhc.handler.ConfigHandler;
import sora.bhc.handler.JsonHandler;
import sora.bhc.proxy.ClientProxy;
import sora.bhc.proxy.CommonProxy;
import sora.bhc.proxy.IProxy;

import java.io.File;

@Mod(BaubleyHeartCanisters.MODID)
public class BaubleyHeartCanisters {

  private static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new CommonProxy());
  public static final String MODID = "bhc";
  public static final Logger LOGGER = LogManager.getLogger();
  public static File CONFIG_DIR = new File(FMLPaths.CONFIGDIR.get().toFile().toPath() + "/bhc/", MODID);
  public static JsonHandler jsonHandler = new JsonHandler("bhc-drops.json");

  public BaubleyHeartCanisters() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.configSpec);
    jsonSetup();
    MinecraftForge.EVENT_BUS.register(this);
  }

  public static IProxy getProxy() {
    return proxy;
  }

  private void setup(FMLCommonSetupEvent event) {
    if (!CONFIG_DIR.exists() && !CONFIG_DIR.mkdir()) {
      LOGGER.warn("Impossible to create the config folder");
    }
    proxy.CommonSetup();
  }

  private void jsonSetup() {
    jsonHandler.addCategory("red", "yellow", "green", "blue");
    jsonHandler.addObject("red", "hostile", 0.05);
    jsonHandler.addObject("yellow", "boss", 1.0);
    jsonHandler.addObject("green", "dragon", 1.0);
    jsonHandler.addObject("blue", "minecraft:evocation_illager", 1.0);
    jsonHandler.saveJson();
  }
}
