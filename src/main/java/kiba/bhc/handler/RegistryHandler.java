package kiba.bhc.handler;

import core.upcraftlp.craftdev.api.util.RegistryUtils;
import kiba.bhc.Reference;
import kiba.bhc.init.ModItems;
import kiba.bhc.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author UpcraftLP
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class RegistryHandler {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        RegistryUtils.createRegistryEntries(Item.class, event, ModItems.class, Reference.MODID, CommonProxy.CREATIVE_TAB);
    }
}
