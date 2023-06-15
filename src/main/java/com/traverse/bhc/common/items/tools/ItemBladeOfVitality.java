package com.traverse.bhc.common.items.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.HeartType;
import com.traverse.bhc.common.util.InventoryUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class ItemBladeOfVitality extends SwordItem implements MenuProvider {

    public static final UUID DAMAGE_MODIFIER_ID = UUID.fromString("432ba3b0-c3bd-4f1c-b14c-76a0b32a386c");


    //ToDo: make an actual Tier for Blade of Vitality Easier to Customize
    public ItemBladeOfVitality() {
        super(Tiers.NETHERITE, 3, -2.4F , new Item.Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND)
            return InteractionResultHolder.fail(player.getItemInHand(hand));

        if (!level.isClientSide() && player.isShiftKeyDown()) {
            NetworkHooks.openScreen((ServerPlayer) player, this, friendlyByteBuf -> friendlyByteBuf.writeItem(player.getItemInHand(hand)));
        }

        return super.use(level, player, hand);
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
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
        return this.getMaxDamage(RegistryHandler.BLADE_OF_VITALITIY.get().getDefaultInstance()) + HeartAmuletContainer.HEART_AMOUNT.length();
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.bhc.blade_of_vitality");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BladeOfVitalityContainer(id, inventory, player.getMainHandItem());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable(Util.makeDescriptionId("tooltip", new ResourceLocation(BaubleyHeartCanisters.MODID, "vitality_blade"))).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
    }
}
