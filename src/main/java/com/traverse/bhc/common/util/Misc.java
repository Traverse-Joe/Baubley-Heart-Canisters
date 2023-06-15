package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Locale;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = MOD)
public class Misc {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void addItemProperties(FMLClientSetupEvent event) {
        ItemProperties.register(RegistryHandler.BLADE_OF_VITALITIY.get(), new ResourceLocation(BaubleyHeartCanisters.MODID, "darkness"), (stack, level, entity, value) -> {
            return stack.hasCustomHoverName() && stack.getHoverName().getString().toLowerCase(Locale.ROOT).contains("beautiful eyes")? 1.0F :0.0F;
        });
    }
}
