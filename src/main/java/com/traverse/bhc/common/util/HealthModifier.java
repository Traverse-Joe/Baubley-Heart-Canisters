package com.traverse.bhc.common.util;

import com.google.common.base.Preconditions;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.ItemHeartAmulet;
import com.traverse.bhc.common.items.ItemSoulHeartAmulet;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class HealthModifier {

    public static final UUID HEALTH_MODIFIER_ID = UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3");

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == RegistryHandler.HEART_AMULET.get()) {
            ICurio curio = new ICurio() {

                @Override
                public ItemStack getStack() {
                    return new ItemStack(RegistryHandler.HEART_AMULET.get());
                }

                @Override
                public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                    LivingEntity livingEntity = slotContext.getWearer();
                    Optional<ImmutableTriple<String, Integer, ItemStack>> stackOptional = CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.HEART_AMULET.get(), livingEntity);

                    stackOptional.ifPresent(triple -> {
                        if(livingEntity instanceof Player) {
                            ItemStack stack = triple.getRight();
                            updatePlayerHealth((Player) livingEntity, stack, true);
                        }
                    });
                }

                @Override
                public boolean canRightClickEquip() {
                    return false;
                }

                @Override
                public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                    if(slotContext.getWearer() instanceof Player)
                        updatePlayerHealth((Player) slotContext.getWearer(), ItemStack.EMPTY, false);
                }

            };

            ICapabilityProvider provider = new ICapabilityProvider() {
                private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
                    return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
                }
            };

            event.addCapability(CuriosCapability.ID_ITEM, provider);
        }
        else if(event.getObject().getItem() == RegistryHandler.SOUL_HEART_AMULET.get()) {
            ICurio curio = new ICurio() {

                @Override
                public ItemStack getStack() {
                    return new ItemStack(RegistryHandler.SOUL_HEART_AMULET.get());
                }

                @Override
                public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                    LivingEntity livingEntity = slotContext.getWearer();
                    Optional<ImmutableTriple<String, Integer, ItemStack>> stackOptional = CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.SOUL_HEART_AMULET.get(), livingEntity);

                    stackOptional.ifPresent(triple -> {
                        if(livingEntity instanceof Player) {
                            ItemStack stack = triple.getRight();
                            updatePlayerHealth((Player) livingEntity, stack, true);
                        }
                    });
                }

                @Override
                public boolean canRightClickEquip() {
                    return false;
                }

                @Override
                public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                    if(slotContext.getWearer() instanceof Player)
                        updatePlayerHealth((Player) slotContext.getWearer(), ItemStack.EMPTY, false);
                }

            };

            ICapabilityProvider provider = new ICapabilityProvider() {
                private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
                    return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
                }
            };

            event.addCapability(CuriosCapability.ID_ITEM, provider);
        }


    }

    public static void updatePlayerHealth(Player player, ItemStack stack, boolean addHealth) {
        AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
        float diff = player.getMaxHealth() - player.getHealth();

        int[] hearts = new int[4];

        if(addHealth && !stack.isEmpty()) {
            int[] amuletHearts = new int[0];
            if(stack.getItem() instanceof ItemHeartAmulet amulet) {
                amuletHearts = amulet.getHeartCount(stack);
            }
            else if(stack.getItem() instanceof ItemSoulHeartAmulet amulet) {
                amuletHearts = amulet.getHeartCount(stack);
            }
            Preconditions.checkArgument(amuletHearts.length == HeartType.values().length, "Array must be same length as enum length!");
            for (int i = 0; i < hearts.length; i++) {
                hearts[i] += amuletHearts[i];
            }
        }

        int extraHearts = 0;
        for (int i = 0; i < hearts.length; i++) {
            extraHearts += Mth.clamp(hearts[i], 0, ConfigHandler.general.heartStackSize.get() * 2);
        }

        AttributeModifier modifier = health.getModifier(HEALTH_MODIFIER_ID);
        if (modifier != null) {
            if (modifier.getAmount() == extraHearts) return;

            health.removeModifier(modifier);
        }

        health.addPermanentModifier(new AttributeModifier(HEALTH_MODIFIER_ID, BaubleyHeartCanisters.MODID + ":extra_hearts", extraHearts, AttributeModifier.Operation.ADDITION));
        float amount = Mth.clamp(player.getMaxHealth() - diff, 0.0f, player.getMaxHealth());
        if (amount > 0.0F) {
            player.setHealth(amount);
        } else {
            player.closeContainer();
            player.kill();
        }
    }
}
