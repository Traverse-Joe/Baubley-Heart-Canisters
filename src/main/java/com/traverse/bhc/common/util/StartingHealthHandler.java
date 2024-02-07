package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;


@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class StartingHealthHandler {

    @SubscribeEvent
    public static void setStartingHealth(final EntityJoinLevelEvent evt) {
        if(ConfigHandler.server.allowStartingHeathTweaks.get() && evt.getEntity() instanceof Player && !(evt.getEntity()instanceof FakePlayer)) {
            final Player player = (Player) evt.getEntity();
            if(ConfigHandler.server.startingHealth.get() > 0) {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(ConfigHandler.server.startingHealth.get());
            }
        }
    }
}
