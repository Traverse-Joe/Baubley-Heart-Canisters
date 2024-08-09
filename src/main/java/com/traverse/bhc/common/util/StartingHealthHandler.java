package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;


@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class StartingHealthHandler {

    @SubscribeEvent
    public static void setStartingHealth(final EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player && !player.isFakePlayer() && ConfigHandler.server.allowStartingHealthTweaks.get() && ConfigHandler.server.startingHealth.get() > 0) {
            var attribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (attribute != null) {
                attribute.setBaseValue(ConfigHandler.server.startingHealth.get());
            }
        }
    }
}
