package com.traverse.bhc.common.util;

import com.google.common.base.Preconditions;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.ConfigHandler;
import com.traverse.bhc.common.items.BaseHeartCanister;
import com.traverse.bhc.common.items.ItemHeartAmulet;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class HealthModifier {

    public static final UUID HEALTH_MODIFIER_ID = UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3");

    @SubscribeEvent
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.END && !event.player.world.isRemote){
            PlayerEntity player = event.player;
            ModifiableAttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
            float diff = player.getMaxHealth() - player.getHealth();
            int[] hearts = new int[HeartType.values().length];
            ICuriosItemHandler curiosInventory = (ICuriosItemHandler) player.getCapability(CuriosCapability.INVENTORY, null); // Use LazyOptional and check ifPresent()
            for(int slot = 0; slot< curiosInventory.getSlots(); slot++){
                ItemStack slotStack = curiosInventory.getWearer().getActiveItemStack();
                if(!slotStack.isEmpty()){
                    if(slotStack.getItem() instanceof BaseHeartCanister){
                        HeartType type = ((BaseHeartCanister) slotStack.getItem()).type;
                        hearts[type.ordinal()] += slotStack.getCount() *2;
                    }
                    else if(slotStack.getItem() instanceof ItemHeartAmulet){
                        int[] amuletHearts = ((ItemHeartAmulet) slotStack.getItem()).getHeartCount(slotStack);
                        Preconditions.checkArgument(amuletHearts.length == HeartType.values().length, "Array must be same length as enum length!");
                        for(int i = 0; i < hearts.length; i++){
                            hearts[i] += amuletHearts[i];
                        }
                    }
                }
            }
            int extraHearts = 0;
            for(int i = 0; i <hearts.length; i++){
                extraHearts += MathHelper.clamp(hearts[i], 0, ConfigHandler.general.heartStackSize.get() * 2);
            }
            AttributeModifier modifier = health.getModifier(HEALTH_MODIFIER_ID);
            if(modifier != null){
                if(modifier.getAmount() == extraHearts) return;
                else health.removeModifier(modifier);
            }
            health.applyPersistentModifier(new AttributeModifier(HEALTH_MODIFIER_ID, BaubleyHeartCanisters.MODID + ":extra_hearts", extraHearts, AttributeModifier.Operation.ADDITION));
            float amount = MathHelper.clamp(player.getMaxHealth() - diff, 0.0f, player.getMaxHealth());
            if(amount > 0.0F)player.setHealth(amount);
            else{
                player.closeScreen();
                player.onKillCommand();
            }
        }
    }
}
