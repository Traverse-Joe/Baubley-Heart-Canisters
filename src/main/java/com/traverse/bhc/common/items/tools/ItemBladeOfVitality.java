package com.traverse.bhc.common.items.tools;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.ItemHeartAmulet;
import com.traverse.bhc.common.util.InventoryUtil;
import com.traverse.bhc.common.util.SoulContainerProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.List;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ItemBladeOfVitality extends SwordItem implements SoulContainerProvider {

    private static final double EXTRA_DAMAGE_PER_HEART = 1.0F;

    public static final ResourceLocation DAMAGE_MODIFIER_ID = BaubleyHeartCanisters.id("blade_of_vitality");

    // TODO: make an actual Tier for Blade of Vitality Easier to Customize
    public ItemBladeOfVitality() {
        super(Tiers.NETHERITE, new Item.Properties().attributes(createAttributes(Tiers.NETHERITE, 3, -2.4F)).component(DataComponents.UNBREAKABLE, new Unbreakable(false)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                this.openMenu(player, hand, BladeOfVitalityContainer::new);
            }

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
        }

        return super.use(level, player, hand);
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

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

    @SubscribeEvent
    public static void onAttributeModifiers(ItemAttributeModifierEvent event) {
        if (event.getItemStack().is(RegistryHandler.BLADE_OF_VITALITY)) {
            // need to remove previous modifier first
            event.removeModifier(Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_ID);

            int heartCount = ItemHeartAmulet.getHeartCount(event.getItemStack());
            if (heartCount > 0) {
                event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_ID, heartCount * EXTRA_DAMAGE_PER_HEART, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable(Util.makeDescriptionId("tooltip", RegistryHandler.BLADE_OF_VITALITY.getId())).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
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
