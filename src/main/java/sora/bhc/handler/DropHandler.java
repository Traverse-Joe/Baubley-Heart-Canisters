package sora.bhc.handler;

import com.google.gson.JsonElement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import sora.bhc.BaubleyHeartCanisters;
import sora.bhc.Reference;
import sora.bhc.init.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class DropHandler {

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.world.isRemote || entity instanceof PlayerEntity) return; //no duplicate glitch on client!
        if (!ModList.get().isLoaded("tinkersconstruct") && entity instanceof WitherSkeletonEntity) {
            if (entity.world.rand.nextDouble() < ConfigHandler.general.boneDropRate.get()) {
                entity.entityDropItem(ModItems.WITHER_BONE, 1);
            }
        }
        for (ItemStack stack : getEntityDrops(entity)) {
            entity.entityDropItem(stack.getItem(), 0);
        }
    }


    public static List<ItemStack> getEntityDrops(LivingEntity entity) {
        List<ItemStack> list = new ArrayList<>();
        handleEntry("red",entity,list);
        handleEntry("yellow",entity,list);
        handleEntry("green",entity,list);
        handleEntry("blue",entity,list);
        return list;
    }

    public static void handleEntry(String category, LivingEntity entity, List<ItemStack> items) {
        for (Map.Entry<String, Double> entry : BaubleyHeartCanisters.config.getHeartTypeEntries(category).entrySet()) {
            ItemStack stack = ItemStack.EMPTY;
            switch (category) {
                case "red":
                    stack = new ItemStack(ModItems.RED_HEART);
                    break;
                case "yellow":
                    stack = new ItemStack(ModItems.YELLOW_HEART);
                    break;
                case "green":
                    stack = new ItemStack(ModItems.GREEN_HEART);
                    break;
                case "blue":
                    stack = new ItemStack(ModItems.BLUE_HEART);
                    break;
            }
            if (entry.getKey().equals(entity.getEntityString())) {
                addWithPercent(items, stack, entry.getValue());
            } else {
                switch (entry.getKey()) {
                    case "hostile":
                        if (entity instanceof IMob && entity.isNonBoss()) {
                            addWithPercent(items, stack, entry.getValue());
                        }
                        break;
                    case "boss":
                        if (!entity.isNonBoss() && !(entity instanceof EnderDragonEntity)) {
                            addWithPercent(items, stack, entry.getValue());
                        }
                        break;
                    case "dragon":
                        if (entity instanceof EnderDragonEntity) {
                            addWithPercent(items, stack, entry.getValue());
                        }
                        break;
                }
            }

        }

    }

    public static void addWithPercent(List<ItemStack> list, ItemStack stack, double percentage) {
        Random random = new Random();
        int percent = (int) (percentage * 100);
        if (random.nextInt(100) <= percent) {
            list.add(stack);
        }
    }
}
