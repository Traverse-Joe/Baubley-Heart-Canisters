package com.traverse.bhc.common.util;

import com.google.common.base.Preconditions;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.ItemHeartAmulet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class HealthModifier {

    public static final UUID HEALTH_MODIFIER_ID = UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3");

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() != RegistryHandler.HEART_AMULET.get())
            return;

        ICurio curio = new ICurio() {
            @Override
            public void onEquip(String identifier, int index, LivingEntity livingEntity) {
                Optional<ImmutableTriple<String, Integer, ItemStack>> stackOptional = CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.HEART_AMULET.get(), livingEntity);

                stackOptional.ifPresent(triple -> {
                    if(livingEntity instanceof PlayerEntity) {
                        ItemStack stack = triple.getRight();
                        updatePlayerHealth((PlayerEntity) livingEntity, stack, true);
                    }
                });
            }

            @Override
            public void onUnequip(String identifier, int index, LivingEntity livingEntity) {
                if(livingEntity instanceof PlayerEntity)
                    updatePlayerHealth((PlayerEntity) livingEntity, ItemStack.EMPTY, false);
            }

            @Override
            public boolean canRightClickEquip() {
                return false;
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

    public static void updatePlayerHealth(PlayerEntity player, ItemStack stack, boolean addHealth) {
        ModifiableAttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
        float diff = player.getMaxHealth() - player.getHealth();

        int[] hearts = new int[HeartType.values().length];

        if(addHealth && !stack.isEmpty()) {
            int[] amuletHearts = ((ItemHeartAmulet) stack.getItem()).getHeartCount(stack);
            Preconditions.checkArgument(amuletHearts.length == HeartType.values().length, "Array must be same length as enum length!");
            for (int i = 0; i < hearts.length; i++) {
                hearts[i] += amuletHearts[i];
            }
        }

        int extraHearts = 0;
        for (int i = 0; i < hearts.length; i++) {
            extraHearts += MathHelper.clamp(hearts[i], 0, ConfigHandler.general.heartStackSize.get() * 2);
        }

        AttributeModifier modifier = health.getModifier(HEALTH_MODIFIER_ID);
        if (modifier != null) {
            if (modifier.getAmount() == extraHearts) return;

            health.removeModifier(modifier);
        }

        health.addPermanentModifier(new AttributeModifier(HEALTH_MODIFIER_ID, BaubleyHeartCanisters.MODID + ":extra_hearts", extraHearts, AttributeModifier.Operation.ADDITION));
        float amount = MathHelper.clamp(player.getMaxHealth() - diff, 0.0f, player.getMaxHealth());
        if (amount > 0.0F) {
            player.setHealth(amount);
        } else {
            player.closeContainer();
            player.kill();
        }
    }
}
