package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.BHCConfig;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.function.Consumer;

public class ItemHeart extends BaseItem {

    protected final HeartType type;

    public ItemHeart(Properties properties, HeartType type) {
        super(properties);
        this.type = type;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 30;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide()) {
            entityLiving.heal(this.type.healAmount);

            if (!(entityLiving instanceof Player player) || !player.isCreative()) {
                stack.shrink(1);
            }

            if(entityLiving instanceof ServerPlayer player) {
                player.resetSentInfo();
            }
        }

        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

        // Only show drop info if config is loaded and we're on client
        BHCConfig config = BaubleyHeartCanisters.config;
        if (config == null) return;

        String heartTypeKey = getHeartTypeKey();
        Map<String, Double> entries = config.getHeartTypeEntries(heartTypeKey);
        if (entries == null || entries.isEmpty()) return;

        if (Screen.hasShiftDown()) {
            // Show detailed drop information when Shift is held
            tooltipAdder.accept(Component.empty());
            tooltipAdder.accept(Component.translatable("tooltip.bhc.heart_drops").withStyle(ChatFormatting.GOLD));

            for (Map.Entry<String, Double> entry : entries.entrySet()) {
                String entityKey = entry.getKey();
                double chance = entry.getValue();
                int percentage = (int) (chance * 100);

                Component entityDisplayName = getEntityDisplayName(entityKey, context);
                tooltipAdder.accept(Component.translatable("tooltip.bhc.drop_info", entityDisplayName, percentage).withStyle(ChatFormatting.GRAY));
            }
        } else {
            // Show hint to press Shift for more info
            tooltipAdder.accept(Component.translatable("tooltip.bhc.hold_shift_for_drops").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }

    private String getHeartTypeKey() {
        return switch (type) {
            case RED -> "red";
            case YELLOW -> "yellow";
            case GREEN -> "green";
            case BLUE -> "blue";
            default -> "";
        };
    }

    private Component getEntityDisplayName(String entityKey, Item.TooltipContext context) {
        return switch (entityKey) {
            case "passive" -> Component.translatable("entity.bhc.passive_mobs");
            case "hostile" -> Component.translatable("entity.bhc.hostile_mobs");
            case "boss" -> Component.translatable("entity.bhc.boss_mobs");
            case "dragon" -> EntityType.ENDER_DRAGON.getDescription();
            default -> {
                // Try to get entity type name from registry, fallback to formatted key
                try {
                    ResourceLocation entityId = ResourceLocation.parse(entityKey);
                    var entityRegistry = context.registries().lookup(Registries.ENTITY_TYPE);
                    if (entityRegistry.isPresent()) {
                        ResourceKey<EntityType<?>> entityTypeKey = ResourceKey.create(Registries.ENTITY_TYPE, entityId);
                        var entityTypeHolder = entityRegistry.get().get(entityTypeKey);
                        if (entityTypeHolder.isPresent()) {
                            yield entityTypeHolder.get().value().getDescription();
                        }
                    }
                    // Format entity ID nicely (e.g., "minecraft:zombie" -> "Zombie")
                    String namePart = entityKey.contains(":") ? entityKey.split(":")[1] : entityKey;
                    String formatted = namePart.replace("_", " ");
                    // Simple capitalization: first letter uppercase, rest lowercase
                    if (!formatted.isEmpty()) {
                        formatted = formatted.substring(0, 1).toUpperCase() + formatted.substring(1).toLowerCase();
                    }
                    yield Component.literal(formatted);
                } catch (Exception e) {
                    yield Component.literal(entityKey);
                }
            }
        };
    }
}

