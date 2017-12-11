package kiba.bhc.handler;

import kiba.bhc.Reference;
import kiba.bhc.init.ModItems;
import kiba.bhc.proxy.CommonProxy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class DropHandler {

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return; //no duplicate glitch on client!
        if (entity instanceof IMob) {
            Random random = entity.getRNG();
            if (!entity.isNonBoss()) { // = is boss
                if (entity instanceof EntityDragon) {
                    if (random.nextDouble() < ConfigHandler.greenDropRate) {
                        entity.dropItem(ModItems.GREEN_HEART, 1);
                    }
                }
                else if (random.nextDouble() < ConfigHandler.orangeDropRate){
                    entity.dropItem(ModItems.ORANGE_HEART, 1);
                }
            } else { //no boss
                if (entity instanceof EntityEvoker) {
                    if (random.nextDouble() < ConfigHandler.blueDropRate) {
                        entity.dropItem(ModItems.BLUE_HEART, 1);
                    }
                }
                else if(!CommonProxy.TINKERS_CONSTRUCT_INSTALLED && entity instanceof EntityWitherSkeleton) {
                    if (random.nextDouble() < ConfigHandler.boneDropRate) {
                        entity.dropItem(ModItems.WITHER_BONE, 1);
                    }
                }
                else { // default/fallback
                    if (random.nextDouble() < ConfigHandler.redDropRate) {
                        entity.dropItem(ModItems.RED_HEART, 1);
                    }
                }
            }
        }
    }
}
