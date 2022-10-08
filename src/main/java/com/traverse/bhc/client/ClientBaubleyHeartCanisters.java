package com.traverse.bhc.client;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ClientBaubleyHeartCanisters {

    public static final ResourceLocation SLOT_TEXTURE = new ResourceLocation(BaubleyHeartCanisters.MODID, "slots/empty_heartamulet");

    @SubscribeEvent
    public void textureStitch(final TextureStitchEvent.Pre evt) {
        evt.addSprite(ClientBaubleyHeartCanisters.SLOT_TEXTURE);
    }
}
