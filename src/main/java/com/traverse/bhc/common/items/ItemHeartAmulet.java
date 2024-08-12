package com.traverse.bhc.common.items;

import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.init.BHCDataComponents;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.HeartType;
import com.traverse.bhc.common.util.SoulContainerProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Arrays;
import java.util.List;

import static com.traverse.bhc.common.util.HealthModifier.updatePlayerHealth;

public class ItemHeartAmulet extends BaseItem implements SoulContainerProvider, ICurioItem {

    public ItemHeartAmulet() {
        super(1);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(player.isShiftKeyDown()) {
            var stack = player.getItemInHand(hand);
            if (!level.isClientSide()) {
                openMenu(player, hand, HeartAmuletContainer::new);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        return super.use(level, player, hand);
    }

    public static int[] getHeartCount(ItemStack stack) {
        int valuesLength = HeartType.values().length;
        if(!stack.has(BHCDataComponents.STORED_HEARTS)) {
            return new int[valuesLength];
        }

        //noinspection DataFlowIssue -- list cannot be null here
        var values = stack.get(BHCDataComponents.STORED_HEARTS).stream().mapToInt(it -> it).toArray();
        if(values.length != valuesLength) {
            return Arrays.copyOf(values, valuesLength);
        }

        return values;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", RegistryHandler.HEART_AMULET.getId())).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if(livingEntity instanceof Player player) {
            ICuriosItemHandler handler = CuriosApi.getCuriosInventory(livingEntity).orElse(null);
            if (handler == null) return;
            SlotResult equipped = handler.findFirstCurio(RegistryHandler.HEART_AMULET.get()).orElse(null);
            if (equipped != null) {
                updatePlayerHealth(player, equipped.stack(), true);
            }
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
        return Component.translatable("container.bhc.heart_amulet");
    }
}
