package kiba.bhc.handler;

import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.Reference;
import kiba.bhc.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class RegistryHandler {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register <Item> event){
        event.getRegistry().register(ModItems.RED_HEART);
        event.getRegistry().register(ModItems.YELLOW_HEART);
        event.getRegistry().register(ModItems.GREEN_HEART);
        event.getRegistry().register(ModItems.BLUE_HEART);

        event.getRegistry().register(ModItems.CANISTER);
        event.getRegistry().register(ModItems.RED_HEART_CANISTER);
        event.getRegistry().register(ModItems.YELLOW_HEART_CANISTER);
        event.getRegistry().register(ModItems.GREEN_HEART_CANISTER);
        event.getRegistry().register(ModItems.BLUE_HEART_CANISTER);

        event.getRegistry().register(ModItems.RELIC_APPLE);
        event.getRegistry().register(ModItems.WITHER_BONE);
        event.getRegistry().register(ModItems.HEART_AMULET);

        BaubleyHeartCanisters.log.info("Registering Items to the OreDictionary...");
        //moved here because the item is still null in preInit.
        OreDictionary.registerOre("boneWithered", new ItemStack(ModItems.WITHER_BONE));
        OreDictionary.registerOre("itemHeart", new ItemStack(ModItems.RED_HEART));
        OreDictionary.registerOre("itemHeart", new ItemStack(ModItems.YELLOW_HEART));
        OreDictionary.registerOre("itemHeart", new ItemStack(ModItems.GREEN_HEART));
        OreDictionary.registerOre("itemHeart", new ItemStack(ModItems.BLUE_HEART));
    }
}
