package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.ItemSoulHeartAmulet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class PlayerDeathEvent {
    private static final Random random = new Random();

    @SubscribeEvent
    public static void onPlayerDeathEvent(LivingDeathEvent evt) {
        if (evt.getEntity() instanceof Player player) {
            Optional<SlotResult> firstCurio = CuriosApi.getCuriosHelper().findFirstCurio(evt.getEntity(), itemStack -> itemStack.getItem() instanceof ItemSoulHeartAmulet);
            firstCurio.ifPresent(slotResult -> {
                ItemStackHandler soulInventory = InventoryUtil.createVirtualInventory(5, slotResult.stack());

                if (!soulInventory.getStackInSlot(4).isEmpty()) {
                    soulInventory.getStackInSlot(4).shrink(1);
                    InventoryUtil.serializeInventory(soulInventory, slotResult.stack());
                    player.displayClientMessage(Component.translatable("soulheartused.bhc.message").setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_PURPLE)), true);
                    player.level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, player.getSoundSource(), 1.0F, 1.0F, false);
                    //15% chance
                    if(random.nextDouble() <= ConfigHandler.general.soulHeartReturnChance.get()) {
                       ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(RegistryHandler.BLUE_CANISTER.get()));
                    }
                    evt.setCanceled(true);
                    player.setHealth(1.0F);
                    player.removeAllEffects();
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                }
            });
        }
    }
}
