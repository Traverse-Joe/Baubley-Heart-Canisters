package com.traverse.bhc.client;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Locale;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = MOD)
public class ClientBaubleyHeartCanisters {

    public static final ResourceLocation SLOT_TEXTURE = new ResourceLocation(BaubleyHeartCanisters.MODID, "slots/empty_heartamulet");

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void textureStitch(final TextureStitchEvent.Pre evt) {
        evt.addSprite(ClientBaubleyHeartCanisters.SLOT_TEXTURE);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void addItemProperties(FMLClientSetupEvent event) {
        ItemProperties.register(RegistryHandler.BLADE_OF_VITALITIY.get(), new ResourceLocation(BaubleyHeartCanisters.MODID, "darkness"), (stack, level, entity, value) -> {
            System.out.println(stack.getDisplayName().);
            return stack.getDisplayName().getString().toLowerCase(Locale.ROOT).equals("beautiful eyes")? 1.0F :0.0F;
    });
}
}