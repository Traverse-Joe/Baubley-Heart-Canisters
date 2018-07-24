package kiba.bhc.handler;

import kiba.bhc.Reference;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod.EventBusSubscriber(modid = Reference.MODID)
public class StartingHealthHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void setStartingHealth(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getEntity();
            if(ConfigHandler.startingHealth > 0) {
                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigHandler.startingHealth);
            }


        }

    }
}
