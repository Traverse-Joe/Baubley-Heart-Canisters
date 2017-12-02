package kiba.bhc.handler;

import core.upcraftlp.craftdev.api.util.RegistryUtils;
import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.Reference;
import kiba.bhc.init.ModItems;
import kiba.bhc.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        RegistryUtils.createRegistryEntries(Item.class, event, ModItems.class, Reference.MODID, BaubleyHeartCanisters.CREATIVE_TAB);
        BaubleyHeartCanisters.CREATIVE_TAB.setIconStack(new ItemStack(ModItems.RED_HEART_CANISTER));
    }
}
