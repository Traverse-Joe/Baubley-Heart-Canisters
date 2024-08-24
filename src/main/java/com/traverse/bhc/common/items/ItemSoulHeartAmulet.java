package com.traverse.bhc.common.items;

import com.traverse.bhc.common.container.SoulHeartAmuletContainer;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.SoulContainerProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

import static com.traverse.bhc.common.util.HealthModifier.updatePlayerHealth;

public class ItemSoulHeartAmulet extends BaseItem implements SoulContainerProvider, ICurioItem {

    public ItemSoulHeartAmulet() {
        super(1);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!player.isShiftKeyDown()) {
            var stack = player.getItemInHand(hand);
            if (!level.isClientSide()) {
                this.openMenu(player, hand, SoulHeartAmuletContainer::new);
            }

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        return super.use(level, player, hand);

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", RegistryHandler.HEART_AMULET.getId())).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
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
