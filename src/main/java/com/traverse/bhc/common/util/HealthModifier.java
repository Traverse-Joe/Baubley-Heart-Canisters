package com.traverse.bhc.common.util;

import com.google.common.base.Preconditions;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.items.ItemHeartAmulet;
import com.traverse.bhc.common.items.ItemSoulHeartAmulet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.common.Mod;

import java.util.UUID;

//@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class HealthModifier {

    public static final UUID HEALTH_MODIFIER_ID = UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3");

    /*
    public static void onEquipCurio(CurioEquipEvent event) {
        LivingEntity livingEntity = event.getSlotContext().entity();
        if(livingEntity instanceof Player player) {
            ICuriosItemHandler handler = CuriosApi.getCuriosInventory(livingEntity).orElse(null);
            if (handler == null) return;
            IItemHandlerModifiable equipped = handler.getEquippedCurios();
            int slots = equipped.getSlots();
            for (int i = 0; i < slots; i++) {
                ItemStack stack = equipped.getStackInSlot(i);
                if(stack.is(RegistryHandler.HEART_AMULET)) {
                    updatePlayerHealth(player, stack, true);
                }
            }

        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        if (slotContext.getWearer() instanceof Player)
            updatePlayerHealth((Player) slotContext.getWearer(), ItemStack.EMPTY, false);
    }

};

    public static void onUnequipCurio(CurioUnequipEvent event) {
       if(event.getSlotContext().entity() instanceof Player player) {
           updatePlayerHealth(player, );
       }
    }

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
                        if (livingEntity instanceof Player) {
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
                    if (slotContext.entity() instanceof Player player)
                        updatePlayerHealth(player, ItemStack.EMPTY, false);
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
        } else if (event.getObject().getItem() == RegistryHandler.SOUL_HEART_AMULET.get()) {
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
                        if (livingEntity instanceof Player) {
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
                    if (slotContext.getWearer() instanceof Player)
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


    }  */
    public static void updatePlayerHealth(Player player, ItemStack stack, boolean addHealth) {
        AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
        float diff = player.getMaxHealth() - player.getHealth();

        int[] hearts = new int[4];

        if (addHealth && !stack.isEmpty()) {
            int[] amuletHearts = null;
            if (stack.getItem() instanceof ItemHeartAmulet amulet) {
                amuletHearts = amulet.getHeartCount(stack);
            } else if (stack.getItem() instanceof ItemSoulHeartAmulet amulet) {
                amuletHearts = amulet.getHeartCount(stack);
            }
            Preconditions.checkArgument(amuletHearts != null, "amuletHearts was never initialized - is this a soul canister?");
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

            health.removeModifier(HEALTH_MODIFIER_ID);
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


