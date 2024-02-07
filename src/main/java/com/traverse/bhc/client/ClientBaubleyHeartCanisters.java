package com.traverse.bhc.client;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.util.EasterEgg;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static net.neoforged.fml.common.Mod.EventBusSubscriber.Bus.MOD;


@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = MOD)
public class ClientBaubleyHeartCanisters {

    public static final ResourceLocation SLOT_TEXTURE = new ResourceLocation(BaubleyHeartCanisters.MODID, "slot/empty_heartamulet");

/*   @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
   public static void textureStitch(final TextureStitchEvent.Post evt) {
     evt.getAtlas().getTextureLocations().add(SLOT_TEXTURE);
    } */

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void addItemProperty(FMLClientSetupEvent evt) {
        EasterEgg.secretCode();
    }
}