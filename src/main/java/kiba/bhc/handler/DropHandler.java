package kiba.bhc.handler;

import kiba.bhc.Reference;
import kiba.bhc.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class DropHandler {

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity.world.isRemote)  return; //no duplicate glitch on client!
        if (entity instanceof IMob) {
            if (!entity.isNonBoss()) { // = is boss
                if (entity instanceof EntityDragon) entity.dropItem(ModItems.GREEN_HEART, 1);
                else entity.dropItem(ModItems.ORANGE_HEART, 1);
            } else { //no boss
                if(entity.getRNG().nextDouble() < 0.05D) {
                    entity.dropItem(ModItems.RED_HEART, 1);
                }
            }
            if(entity instanceof EntityWitherSkeleton){
                if(entity.getRNG().nextDouble() < 0.15D){
                    entity.dropItem(ModItems.WITHER_BONE,1);
                }
            }
        }
    }
}
