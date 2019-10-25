package sora.bhc.handler;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import com.google.common.base.Preconditions;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sora.bhc.BaubleyHeartCanisters;
import sora.bhc.Reference;
import sora.bhc.items.BaseHeartCanister;
import sora.bhc.items.ItemHeartAmulet;
import sora.bhc.util.HeartType;
import top.theillusivec4.curios.api.capability.CuriosCapability;

import java.util.UUID;

/**
 * @author UpcraftLP
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class HealthHandler {

    private static final UUID HEALTH_MODIFIER = UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3");

    @SubscribeEvent
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        //only sync every 10 ticks
        if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote && event.player.openContainer == event.player.container && event.player instanceof ServerPlayerEntity && ((ServerPlayerEntity) event.player).world.getGameTime() % 10 == 0) {
            PlayerEntity player = (PlayerEntity) event.player;
            IAttributeInstance health = player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if(player.hasCapability(CuriosCapability.CAPABILITY_BAUBLES, null)) {
                IBaublesItemHandler baublesInventory = player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
                float diff = player.getMaxHealth() - player.getHealth();
                int[] hearts = new int[HeartType.values().length];
                for(int slot = 0; slot < baublesInventory.getSlots(); slot++) {
                    ItemStack slotStack = baublesInventory.getStackInSlot(slot);
                    if(!slotStack.isEmpty()) {
                        if(slotStack.getItem() instanceof BaseHeartCanister) {
                            HeartType type = ((BaseHeartCanister) slotStack.getItem()).type;
                            hearts[type.ordinal()] += slotStack.getCount() * 2;
                        }
                        else if(slotStack.getItem() instanceof ItemHeartAmulet) {
                            int[] pendantHearts = ((ItemHeartAmulet) slotStack.getItem()).getHeartCount(slotStack);
                            Preconditions.checkArgument(pendantHearts.length == HeartType.values().length, "Array must be same length as enum length!");
                            for(int i = 0; i < hearts.length; i++) {
                                hearts[i] += pendantHearts[i];
                            }
                        }
                    }
                }
                int extraHearts = 0;
                for(int i = 0; i < hearts.length; i++) {
                    extraHearts += MathHelper.clamp(hearts[i], 0, ConfigHandler.general.heartStackSize.get() * 2); //make sure to not bypass the limit; bugfix: this is half hearts, so we need to double the limit...
                }
                AttributeModifier modifier = health.getModifier(HEALTH_MODIFIER);
                if(modifier != null) {
                    if(modifier.getAmount() == extraHearts) return;
                    else health.removeModifier(modifier);
                }
                health.applyModifier(new AttributeModifier(HEALTH_MODIFIER, BaubleyHeartCanisters.MODID + ":extra_hearts", extraHearts, 0)); //0 = addition
                float amount = MathHelper.clamp(player.getMaxHealth() - diff, 0.0F, player.getMaxHealth()); //bugfix: death by removing heart canisters could cause loss of items!
                if(amount > 0.0F) player.setHealth(amount); //no healing glitch by adding and removing heart canisters!
                else {
                    player.closeScreen();
                    player.onKillCommand();
                }
            }
        }
    }
}
