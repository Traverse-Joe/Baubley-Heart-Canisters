package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class StartingHealthHandler {

    @SubscribeEvent
    public static void setStartingHealth(EntityJoinWorldEvent evt) {
        if(ConfigHandler.server.allowStartingHeathTweaks.get()) {
            if(evt.getEntity() instanceof PlayerEntity && !(evt.getEntity() instanceof FakePlayer)) {
                PlayerEntity player = (PlayerEntity) evt.getEntity();
                if(ConfigHandler.server.startingHealth.get() > 0) {
                    player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(ConfigHandler.server.startingHealth.get());
                }
            }
        }
    }
}
