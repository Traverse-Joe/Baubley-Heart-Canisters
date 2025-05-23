package com.traverse.bhc.common.items.tools;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.BaseItem;
import com.traverse.bhc.common.util.HealthModifier;
import com.traverse.bhc.common.util.InventoryUtil;
import com.traverse.bhc.common.util.SoulContainerProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.function.Consumer;
import java.util.stream.IntStream;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ItemBladeOfVitality extends BaseItem implements SoulContainerProvider {

    private static final double EXTRA_DAMAGE_PER_HEART = 1.0F;

    public static final ResourceLocation DAMAGE_MODIFIER_ID = BaubleyHeartCanisters.id("blade_of_vitality");

    // TODO: make an actual Tier for Blade of Vitality Easier to Customize
    public ItemBladeOfVitality(Properties properties) {
        super(properties.sword(ToolMaterial.NETHERITE, 3, -2.4F)
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                this.openMenu(player, hand, BladeOfVitalityContainer::new);
            }

            return InteractionResult.SUCCESS;
        }

        return super.use(level, player, hand);
    }

    @Override
    public boolean isCombineRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    /* DISABLED UNTIL A BETTER BAR SYSTEM IS IMPLEMENTED
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return ItemHeartAmulet.getHeartCount(stack) < ItemBladeOfVitality.getMaxHearts(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        // TODO represent the heart colors
        return 0xE531E7;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Mth.clamp(Math.round(13.0F * (ItemHeartAmulet.getHeartCount(stack) / (float) getMaxHearts(stack))), 0, 13);
    }
*/
    @SubscribeEvent
    public static void onAttributeModifiers(ItemAttributeModifierEvent event) {
        if (event.getItemStack().is(RegistryHandler.BLADE_OF_VITALITY)) {
            // need to remove previous modifier first
            event.removeModifier(Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_ID);

            int heartCount = HealthModifier.getHeartCount(event.getItemStack());
            if (heartCount > 0) {
                event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_ID, heartCount * EXTRA_DAMAGE_PER_HEART, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
        tooltipComponents.accept(Component.translatable(Util.makeDescriptionId("tooltip", RegistryHandler.BLADE_OF_VITALITY.getId())).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        if(Screen.hasShiftDown()) {
            int[] heartCount = new int[]{HealthModifier.getHeartCount(stack)};
            int heartTotal = IntStream.of(heartCount).sum();
            tooltipComponents.accept(Component.translatable(Util.makeDescriptionId("tooltip", BaubleyHeartCanisters.id("heart_amount")), heartTotal).setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)));
        }
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return true;
    }

    @Override
    public Component getContainerName(ItemStack stack) {
        return Component.translatable("container.bhc.blade_of_vitality");
    }

    private static int getMaxHearts(ItemStack stack) {
        var inv = InventoryUtil.createVirtualInventory(BladeOfVitalityContainer.SLOT_COUNT, stack);

        int total = 0;
        for (int i = 0; i < inv.getSlots(); i++) {
            var invStack = inv.getStackInSlot(i);
            total += !invStack.isEmpty() ? invStack.getMaxStackSize() : ConfigHandler.general.heartStackSize.get();
        }

        return total;
    }

}
