package com.traverse.bhc.common.items;

import com.traverse.bhc.common.util.HeartPulseHandler;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ItemHeartPulseBelt extends BaseItem implements ICurioItem {

    protected final HeartType type;

    public ItemHeartPulseBelt(Properties properties, HeartType type) {
        super(properties.stacksTo(1));
        this.type = type;
    }

    public int getMultiplier() {
        return type.multiplier;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity instanceof Player player) {
            HeartPulseHandler.applyPulseEffects(player, true, this.getMultiplier());
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity instanceof Player player) {
            HeartPulseHandler.applyPulseEffects(player, false, this.getMultiplier());
        }
    }

}
