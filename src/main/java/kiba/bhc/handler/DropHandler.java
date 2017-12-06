package kiba.bhc.handler;

import kiba.bhc.Reference;
import kiba.bhc.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class DropHandler {

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return; //no duplicate glitch on client!
        if (entity instanceof IMob) {
            if (!entity.isNonBoss()) { // = is boss
                if (entity instanceof EntityDragon) {
                    if (entity.getRNG().nextDouble() < ConfigHandler.greenDropRate) {
                        entity.dropItem(ModItems.GREEN_HEART, 1);
                    }
                    else if(entity.getRNG().nextDouble()<ConfigHandler.orangeDropRate){
                        entity.dropItem(ModItems.ORANGE_HEART, 1);
                    }

                }
            } else { //no boss
                if (entity instanceof EntityEvoker) {
                    if (entity.getRNG().nextDouble() < ConfigHandler.blueDropRate) {
                        entity.dropItem(ModItems.BLUE_HEART, 1);
                    }

                } else {
                    if (entity.getRNG().nextDouble() < ConfigHandler.redDropRate) {
                        entity.dropItem(ModItems.RED_HEART, 1);
                    }
                }
                if (!(Loader.isModLoaded("tconstruct"))) {
                    if (entity instanceof EntityWitherSkeleton) {
                        if (entity.getRNG().nextDouble() < ConfigHandler.boneDropRate) {
                            entity.dropItem(ModItems.WITHER_BONE, 1);
                        }
                    }
                }
            }
        }
    }
}
//TODO setup the Config for drops
//BUGREPORT need help getting config to work properly Orange Else If isnt working