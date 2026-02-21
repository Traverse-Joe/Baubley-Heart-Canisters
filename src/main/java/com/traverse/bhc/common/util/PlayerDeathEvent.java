package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.ItemSoulHeartAmulet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class PlayerDeathEvent {

    @SubscribeEvent
    public static void onPlayerDeathEvent(LivingDeathEvent evt) {
        if (!evt.getEntity().level().isClientSide()) {
            if (evt.getEntity() instanceof Player player) {
                ICuriosItemHandler handler = CuriosApi.getCuriosInventory(evt.getEntity()).orElse(null);
                if (handler == null) return;
                SlotResult equipped = handler.findFirstCurio(itemStack -> itemStack.getItem() instanceof ItemSoulHeartAmulet).orElse(null);
                if (equipped != null) {
                    var soulInventory = InventoryUtil.createVirtualInventory(5, equipped.stack());

                    try (var tx = Transaction.openRoot()) {
                        ItemResource resource = soulInventory.getResource(4);
                        if (!resource.isEmpty()) {
                            if (soulInventory.extract(4, resource, 1, tx) != 1) return;
                            tx.commit();;
                            player.displayClientMessage(Component.translatable(Util.makeDescriptionId("message", BaubleyHeartCanisters.id("soul_heart_used"))).setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_PURPLE)), true);
                            player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, player.getSoundSource(), 1.0F, 1.0F, false);
                            //15% chance
                            if (player.getRandom().nextDouble() <= ConfigHandler.general.soulHeartReturnChance.get()) {
                                ItemStack canisterStack = new ItemStack(RegistryHandler.BLUE_CANISTER.get());
                                if (!player.addItem(canisterStack)) {
                                    player.spawnAtLocation((ServerLevel) player.level(), canisterStack);
                                }
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
}
