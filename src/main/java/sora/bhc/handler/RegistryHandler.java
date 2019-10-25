package sora.bhc.handler;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sora.bhc.Reference;
import sora.bhc.container.HeartPendantContainer;
import sora.bhc.init.ModItems;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@Mod.EventBusSubscriber(bus = MOD, modid = Reference.MODID)
public class RegistryHandler {

  public static final ContainerType<HeartPendantContainer> HEART_PENDANT = null;

  @SubscribeEvent
  public static void registerItems(RegistryEvent.Register<Item> event) {
    event.getRegistry().registerAll(
        ModItems.RED_HEART,
        ModItems.YELLOW_HEART,
        ModItems.GREEN_HEART,
        ModItems.BLUE_HEART,
        ModItems.CANISTER,
        ModItems.RED_HEART_CANISTER,
        ModItems.YELLOW_HEART_CANISTER,
        ModItems.GREEN_HEART_CANISTER,
        ModItems.BLUE_HEART_CANISTER,
        ModItems.RELIC_APPLE,
        ModItems.WITHER_BONE
       // ModItems.HEART_AMULET
    );
  }


  @SubscribeEvent
  public static void registerContainer(final RegistryEvent.Register<ContainerType<?>> event){
    event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
      return new HeartPendantContainer(windowId);
    }));
  }
}
