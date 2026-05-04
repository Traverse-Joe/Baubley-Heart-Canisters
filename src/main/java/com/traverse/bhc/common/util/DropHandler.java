package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.*;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class DropHandler {

    private static final boolean TCONSTRUCT_LOADED = ModList.get().isLoaded("tinkersconstruct");

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide() || entity instanceof Player) return;
        if (entity.level() instanceof ServerLevel serverLevel) {
            Collection<ItemEntity> drops = event.getDrops();

            if (!TCONSTRUCT_LOADED && entity instanceof WitherSkeleton) {
                if (entity.level().getRandom().nextDouble() < ConfigHandler.general.boneDropRate.get()) {
                    drops.add(makeItemEntity(serverLevel, entity, 1, RegistryHandler.WITHER_BONE.toStack()));
                }
            }

            if(event.getEntity() instanceof Warden warden) {
                if(warden.level().getRandom().nextDouble() < ConfigHandler.general.echoShardDropRate.get()) {
                    drops.add(makeItemEntity(serverLevel, entity, 1, Items.ECHO_SHARD.getDefaultInstance()));
                }
            }

            for (ItemStack stack : getEntityDrops(entity)) {
                drops.add(makeItemEntity(serverLevel, entity, 0, stack));
            }
        }
    }

    private static ItemEntity makeItemEntity(ServerLevel level, LivingEntity sourceEntity, float yOffset, ItemStack stack) {
        return new ItemEntity(level, sourceEntity.getX(), sourceEntity.getY() + yOffset, sourceEntity.getZ(), stack);
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
                    stack = new ItemStack(RegistryHandler.RED_HEART.get());
                    break;
                case "yellow":
                    stack = new ItemStack(RegistryHandler.YELLOW_HEART.get());
                    break;
                case "green":
                    stack = new ItemStack(RegistryHandler.GREEN_HEART.get());
                    break;
                case "blue":
                    stack = new ItemStack(RegistryHandler.BLUE_HEART.get());
                    break;
            }

            if (entry.getKey().equals(entity.getEncodeId())) {
                addWithPercent(items, stack, entry.getValue());
            } else {
                switch (entry.getKey()) {
                    case "passive":
                        if((!(entity instanceof Monster) && !(entity instanceof Player))) {
                            addWithPercent(items, stack, entry.getValue());
                        }
                        break;
                    case "hostile":
                        if (entity instanceof Monster && !(entity.is(Tags.EntityTypes.BOSSES) && !(entity instanceof Warden))) {
                            addWithPercent(items, stack, entry.getValue());
                        }
                        break;
                    case "boss":
                        if (entity.is(Tags.EntityTypes.BOSSES) && !(entity instanceof EnderDragon)) {
                            addWithPercent(items, stack, entry.getValue());
                        }
                        break;
                    case "dragon":
                        if (entity instanceof EnderDragon) {
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
        if (random.nextInt(100) < percent) {
            list.add(stack);
        }
    }
}
