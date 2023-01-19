package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//Creative Tab Help From
//https://github.com/VsnGamer/ElevatorMod/blob/1.19/src/main/java/xyz/vsngamer/elevatorid/ElevatorModTab.java

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BaubleyHeartCanistersTab {
    public static CreativeModeTab TAB;

    @SubscribeEvent
    public static void registerTab(CreativeModeTabEvent.Register evt){
        TAB = evt.registerCreativeModeTab(
                new ResourceLocation("bhc_tab", BaubleyHeartCanisters.MODID), builder -> builder
                        .icon(() -> new ItemStack(RegistryHandler.HEART_AMULET.get()))
                        .displayItems((flags, output, hasOP) -> RegistryHandler.ITEMS.getEntries().forEach(o -> output.accept(o.get())))
                        .title(Component.translatable("itemGroup.bhc"))
        );
    }
}
