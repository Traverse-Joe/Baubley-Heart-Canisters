package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.HeartAmuletContainer;
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
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.stream.IntStream;

import static com.traverse.bhc.common.util.HealthModifier.updatePlayerHealth;

public class ItemHeartAmulet extends BaseItem implements SoulContainerProvider, ICurioItem {

    public ItemHeartAmulet(Properties properties) {
        super(properties, 1);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                openMenu(player, hand, HeartAmuletContainer::new);
            }
            return InteractionResult.SUCCESS;
        }

        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", RegistryHandler.HEART_AMULET.getId())).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if(Screen.hasShiftDown()) {
            int[] heartCount = new int[]{HealthModifier.getHeartCount(stack)};
            int heartTotal = IntStream.of(heartCount).sum();
            tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", BaubleyHeartCanisters.id("heart_amount")), heartTotal).setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)));
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity instanceof Player player) {
            ICuriosItemHandler handler = CuriosApi.getCuriosInventory(livingEntity).orElse(null);
            if (handler == null)
                return;
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
        return Component.translatable(Util.makeDescriptionId("container", RegistryHandler.HEART_AMUlET_CONTAINER.getId()));
    }
}
