package kiba.bhc.handler;

import com.google.gson.JsonElement;
import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.Reference;
import kiba.bhc.init.ModItems;
import kiba.bhc.proxy.CommonProxy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class DropHandler {

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return; //no duplicate glitch on client!
        if(!CommonProxy.TINKERS_CONSTRUCT_INSTALLED && entity instanceof EntityWitherSkeleton) {
            if (entity.world.rand.nextDouble() < ConfigHandler.boneDropRate) {
                entity.dropItem(ModItems.WITHER_BONE, 1);
            }
        }
        for (ItemStack stack : getEntityDrops(entity)) {
            entity.dropItem(stack.getItem(), 1);
        }
    }


    public static List<ItemStack> getEntityDrops(EntityLivingBase entity) {

        List<ItemStack> list = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : BaubleyHeartCanisters.jsonHandler.getObject().entrySet()) {
            ItemStack stack = ItemStack.EMPTY;
            switch (entry.getKey()) {
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
            for (Map.Entry<String, JsonElement> catEntry : entry.getValue().getAsJsonObject().entrySet()) {
                if (BaubleyHeartCanisters.jsonHandler.genericValues.contains(catEntry.getKey())) {
                    switch (catEntry.getKey()) {
                        case "hostile":
                            if (entity instanceof IMob && entity.isNonBoss()) {
                                addWithPercent(list, stack, catEntry.getValue().getAsDouble());
                            }
                            break;
                        case "boss":
                            if (!entity.isNonBoss() && !(entity instanceof EntityDragon)) {
                                addWithPercent(list, stack, catEntry.getValue().getAsDouble());
                            }
                            break;
                        case "dragon":
                            if (entity instanceof EntityDragon) {
                                addWithPercent(list, stack, catEntry.getValue().getAsDouble());
                            }
                            break;
                    }
                } else if (catEntry.getKey().equals(ForgeRegistries.ENTITIES.getKey(EntityRegistry.getEntry(entity.getClass())).toString())) {
                    addWithPercent(list, stack, catEntry.getValue().getAsDouble());
                }
            }
        }
        return list;
    }

    public static void addWithPercent(List<ItemStack> list, ItemStack stack, double percentage) {

        Random random = new Random();
        int percent = (int) (percentage * 100);
        if (random.nextInt(100) <= percent) {
            list.add(stack);
        }
    }
}
