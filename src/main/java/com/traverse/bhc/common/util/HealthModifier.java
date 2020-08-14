package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class HealthModifier {



    @SubscribeEvent
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.END && !event.player.world.isRemote){
            PlayerEntity player = event.player;
            ModifiableAttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH); //Max Health
        }
    }
}
