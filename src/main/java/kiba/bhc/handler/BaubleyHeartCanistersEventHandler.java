package kiba.bhc.handler;

import kiba.bhc.init.ModItems;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityMob;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BaubleyHeartCanistersEventHandler {

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        if (event.getEntity() instanceof EntityMob) {
            if (((EntityMob) event.getEntity()).getRNG().nextInt(20) == 0) event.getEntityLiving().dropItem(ModItems.itemRedHeart, 1);
        }
        if (event.getEntity() instanceof EntityMob) {
            if (!(event.getEntity() instanceof EntityDragon)) {
                if (event.getEntity().isNonBoss() == false)
                    event.getEntityLiving().dropItem(ModItems.itemOrangeHeart, 1);

            }
        }
            if(event.getEntity() instanceof EntityDragon){
                event.getEntityLiving().dropItem(ModItems.itemGreenHeart, 1);
            }
        }
    }
