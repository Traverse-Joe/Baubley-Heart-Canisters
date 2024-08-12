package com.traverse.bhc.common.items.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.HeartType;
import com.traverse.bhc.common.util.SoulContainerProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class ItemBladeOfVitality extends SwordItem implements SoulContainerProvider {

    public static final UUID DAMAGE_MODIFIER_ID = UUID.fromString("432ba3b0-c3bd-4f1c-b14c-76a0b32a386c");


    // TODO: make an actual Tier for Blade of Vitality Easier to Customize
    public ItemBladeOfVitality() {
        super(Tiers.NETHERITE, new Item.Properties().attributes(createAttributes(Tiers.NETHERITE, 3, -2.4F)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND)
            return InteractionResultHolder.fail(player.getItemInHand(hand));

        if (!level.isClientSide() && player.isShiftKeyDown()) {
            this.openMenu(player, hand, BladeOfVitalityContainer::new);
        }

        return super.use(level, player, hand);
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        var result = super.getDefaultAttributeModifiers(stack);

        return result;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> RESULT = ImmutableMultimap.builder();
        RESULT.putAll(super.getAttributeModifiers(slot, stack));
        if(slot == EquipmentSlot.MAINHAND) {
            int[] heartCount = getHeartCount(stack);
            int heartTotal = IntStream.of(heartCount).sum();
            if (heartTotal > 0) {
                RESULT.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_ID, "Weapon modifier", heartTotal, AttributeModifier.Operation.ADDITION));
            }
        }
        return RESULT.build();
    }

    public int[] getHeartCount(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag nbt = stack.getTag();
            if (nbt.contains(BladeOfVitalityContainer.HEART_AMOUNT))
                return nbt.getIntArray(BladeOfVitalityContainer.HEART_AMOUNT);
        }

        return new int[HeartType.values().length];
    }

    //ToDo Actually check the length of the Hearts on the Weapon and Add to the damage
    @Override
    public float getDamage() {
        return this.getMaxDamage(RegistryHandler.BLADE_OF_VITALITY.get().getDefaultInstance()) + HeartAmuletContainer.HEART_AMOUNT.length();
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable(Util.makeDescriptionId("tooltip", RegistryHandler.BLADE_OF_VITALITY.getId())).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return true;
    }

    @Override
    public Component getContainerName(ItemStack stack) {
        return Component.translatable("container.bhc.blade_of_vitality");
    }
}
