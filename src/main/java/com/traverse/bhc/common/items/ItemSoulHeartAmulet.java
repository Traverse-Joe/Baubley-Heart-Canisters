package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.SoulHeartAmuletContainer;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.HealthModifier;
import com.traverse.bhc.common.util.SoulContainerProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static com.traverse.bhc.common.util.HealthModifier.updatePlayerHealth;

public class ItemSoulHeartAmulet extends BaseItem implements SoulContainerProvider, ICurioItem {

    public ItemSoulHeartAmulet(Properties properties) {
        super(properties, 1);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(!player.isShiftKeyDown()) {
            var stack = player.getItemInHand(hand);
            if (!level.isClientSide()) {
                this.openMenu(player, hand, SoulHeartAmuletContainer::new);
            }

            return InteractionResult.SUCCESS;
        }

        return super.use(level, player, hand);

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
        tooltipComponents.accept(Component.translatable(Util.makeDescriptionId("tooltip", RegistryHandler.HEART_AMULET.getId())).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        if(Screen.hasShiftDown()) {
            int[] heartCount = new int[]{HealthModifier.getHeartCount(stack)};
            int heartTotal = IntStream.of(heartCount).sum();
            tooltipComponents.accept(Component.translatable(Util.makeDescriptionId("tooltip", BaubleyHeartCanisters.id("heart_amount")), heartTotal).setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)));
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if(livingEntity instanceof Player player) {
            updatePlayerHealth(player, stack, true);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            updatePlayerHealth(player, ItemStack.EMPTY, false);
        }
    }

    @Override
    public Component getContainerName(ItemStack stack) {
        return Component.translatable(Util.makeDescriptionId("container", RegistryHandler.SOUL_HEART_AMUlET_CONTAINER.getId()));
    }
}
