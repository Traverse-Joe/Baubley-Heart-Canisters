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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class PlayerDeathEvent {
    private static final Random random = new Random();

    @SubscribeEvent
    public static void onPlayerDeathEvent(LivingDeathEvent evt) {
        if (!evt.getEntity().level().isClientSide()) {
            if (evt.getEntity() instanceof Player player) {
                ICuriosItemHandler handler = CuriosApi.getCuriosInventory(evt.getEntity()).orElse(null);
                if (handler == null) return;
                SlotResult equipped = handler.findFirstCurio(itemStack -> itemStack.getItem() instanceof ItemSoulHeartAmulet).orElse(null);
                if (equipped != null) {
                    ItemStackHandler soulInventory = InventoryUtil.createVirtualInventory(5, equipped.stack());

                    if (!soulInventory.getStackInSlot(4).isEmpty()) {
                        soulInventory.getStackInSlot(4).shrink(1);
                        InventoryUtil.serializeInventory(soulInventory, equipped.stack());
                        player.displayClientMessage(Component.translatable("soulheartused.bhc.message").setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_PURPLE)), true);
                        player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, player.getSoundSource(), 1.0F, 1.0F, false);
                        //15% chance
                        if (random.nextDouble() <= ConfigHandler.general.soulHeartReturnChance.get()) {
                            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(RegistryHandler.BLUE_CANISTER.get()));
                        }
                        evt.setCanceled(true);
                        player.setHealth(player.getMaxHealth());
                        player.removeAllEffects();
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                    }

                }
            }
        }
    }
}
