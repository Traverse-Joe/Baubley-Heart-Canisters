package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class StartingHealthHandler {

    @SubscribeEvent
    public static void setStartingHealth(final EntityJoinWorldEvent evt) {
        if(ConfigHandler.server.allowStartingHeathTweaks.get() && evt.getEntity() instanceof Player && !(evt.getEntity()instanceof FakePlayer)) {
            final Player player = (Player) evt.getEntity();
            if(ConfigHandler.server.startingHealth.get() > 0) {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(ConfigHandler.server.startingHealth.get());
            }
        }
    }
}
