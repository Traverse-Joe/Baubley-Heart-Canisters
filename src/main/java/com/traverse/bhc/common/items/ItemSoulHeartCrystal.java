package com.traverse.bhc.common.items;

import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.entity.VitalicOrb;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.VitalicCharge;
import com.traverse.bhc.common.util.VitalicSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class ItemSoulHeartCrystal extends BaseItem {

    public ItemSoulHeartCrystal(Properties properties) {
        super(properties);
    }

    public static int getMaxCharge() {
        return ConfigHandler.general.vitalicCrystalMaxCharge.get();
    }

    public static VitalicCharge getChargeData(ItemStack stack) {
        return stack.get(RegistryHandler.VITALIC_CHARGE_COMPONENT.get());
    }

    public static int getCharge(ItemStack stack) {
        VitalicCharge data = getChargeData(stack);
        return data == null ? 0 : data.charge();
    }

    public static VitalicSource getSource(ItemStack stack) {
        VitalicCharge data = getChargeData(stack);
        return data == null ? null : data.source();
    }

    public static boolean canAccept(ItemStack stack, VitalicSource source) {
        VitalicCharge data = getChargeData(stack);
        if (data == null || data.charge() <= 0) return true;
        return data.source() == source && data.charge() < getMaxCharge();
    }

    public static int addCharge(ItemStack stack, VitalicSource source, int amount) {
        if (amount <= 0) return 0;
        VitalicCharge data = getChargeData(stack);
        int max = getMaxCharge();
        int current = (data == null || data.charge() <= 0) ? 0 : data.charge();
        VitalicSource boundSource = (data == null || data.charge() <= 0) ? source : data.source();
        if (boundSource != source) return 0;
        int accepted = Math.min(amount, max - current);
        if (accepted <= 0) return 0;
        stack.set(RegistryHandler.VITALIC_CHARGE_COMPONENT.get(), new VitalicCharge(boundSource, current + accepted));
        return accepted;
    }

    public static int drainCharge(ItemStack stack, int amount) {
        if (amount <= 0) return 0;
        VitalicCharge data = getChargeData(stack);
        if (data == null || data.charge() <= 0) return 0;
        int drained = Math.min(amount, data.charge());
        int remaining = data.charge() - drained;
        if (remaining <= 0) {
            stack.remove(RegistryHandler.VITALIC_CHARGE_COMPONENT.get());
        } else {
            stack.set(RegistryHandler.VITALIC_CHARGE_COMPONENT.get(), data.withCharge(remaining));
        }
        return drained;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getCharge(stack) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Mth.clamp(Math.round(13.0F * getCharge(stack) / (float) getMaxCharge()), 0, 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        VitalicSource source = getSource(stack);
        return source == null ? 0xFFFFFF : VitalicOrb.colorFor(source);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
        VitalicCharge data = getChargeData(stack);
        if (data == null || data.charge() <= 0) {
            tooltipComponents.accept(Component.translatable("tooltip.bhc.soul_heart_crystal.empty")
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
            return;
        }
        tooltipComponents.accept(Component.translatable(
                "tooltip.bhc.soul_heart_crystal.charge",
                data.charge(),
                getMaxCharge(),
                Component.translatable("tooltip.bhc.vitalic_source." + data.source().getSerializedName())
        ).setStyle(Style.EMPTY.applyFormat(colorFor(data.source()))));
    }

    private static ChatFormatting colorFor(VitalicSource source) {
        return switch (source) {
            case RED -> ChatFormatting.RED;
            case YELLOW -> ChatFormatting.YELLOW;
            case GREEN -> ChatFormatting.GREEN;
            case BLUE -> ChatFormatting.BLUE;
        };
    }
}
