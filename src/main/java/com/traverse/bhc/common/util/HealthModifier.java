package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

//@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class HealthModifier {

    public static final Identifier HEALTH_MODIFIER_ID = BaubleyHeartCanisters.id("extra_health");

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
        if(health == null) {
            return;
        }

        float diff = player.getMaxHealth() - player.getHealth();

        int extraHearts = 0;

        if(addHealth) {
            // no need to check item type, either the stack has our component or it doesnt
            extraHearts = HealthModifier.getHeartCount(stack) * 2;
        }

        AttributeModifier modifier = health.getModifier(HEALTH_MODIFIER_ID);
        if (modifier != null) {
            if (modifier.amount() == extraHearts) return;

            health.removeModifier(HEALTH_MODIFIER_ID);
        }

        health.addPermanentModifier(new AttributeModifier(HEALTH_MODIFIER_ID, extraHearts, AttributeModifier.Operation.ADD_VALUE));
        float newHealth = Mth.clamp(player.getMaxHealth() - diff, 0.0F, player.getMaxHealth());
        if (newHealth > 0.0F) {
            player.setHealth(newHealth);
            if(player instanceof ServerPlayer serverPlayer) {
                serverPlayer.resetSentInfo();
            }
        } else {
            player.closeContainer();
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.kill(serverPlayer.level());
            }
        }
    }

    public static int[] getHeartValues(ItemStack stack) {
        int valuesLength = HeartType.values().length;
        if (!stack.has(RegistryHandler.STORED_HEARTS_COMPONENT)) {
            return new int[valuesLength];
        }

        //noinspection DataFlowIssue -- inventory cannot be null here
        var values = stack.get(RegistryHandler.STORED_HEARTS_COMPONENT).stream().mapToInt(ItemStack::getCount).toArray();
        if (values.length != valuesLength) {
            return Arrays.copyOf(values, valuesLength);
        }

        return values;
    }


    public static int getHeartCount(ItemStack stack) {
        int sum = 0;
        for (int hearts : getHeartValues(stack)) {
            sum += hearts;
        }
        return sum;
    }
}
