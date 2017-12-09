package kiba.bhc.handler;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import kiba.bhc.Reference;
import kiba.bhc.items.BaseHeartCanister;
import kiba.bhc.items.ItemHeartAmulet;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
        if (event.phase == TickEvent.Phase.END && event.player.openContainer == event.player.inventoryContainer && event.player instanceof EntityPlayerMP && ((EntityPlayerMP) event.player).world.getTotalWorldTime() % 10 == 0) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            IAttributeInstance health = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if(player.hasCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null)) { //TODO is this check needed??
                IBaublesItemHandler baublesInventory = player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
                float diff = player.getMaxHealth() - player.getHealth();
                int hearts = 0;
                for(int slot = 0; slot < baublesInventory.getSlots(); slot++) {
                    ItemStack slotStack = baublesInventory.getStackInSlot(slot);
                    if(!slotStack.isEmpty()) {
                        if(slotStack.getItem() instanceof BaseHeartCanister) {
                            hearts += slotStack.getCount() * 2;
                        }
                        else if(slotStack.getItem() instanceof ItemHeartAmulet) {
                            hearts += ((ItemHeartAmulet) slotStack.getItem()).getHeartCount(slotStack);
                        }
                    }
                }
                AttributeModifier modifier = health.getModifier(HEALTH_MODIFIER);
                if(modifier != null) {
                    if(modifier.getAmount() == hearts) return;
                    else health.removeModifier(modifier);
                }
                health.applyModifier(new AttributeModifier(HEALTH_MODIFIER, Reference.MODID + ":extra_hearts", hearts, 0)); //0 = addition
                float amount = MathHelper.clamp(player.getMaxHealth() - diff, 0.0F, player.getMaxHealth()); //bugfix: death by removing heart canisters could cause loss of items!
                if(amount > 0.0F) player.setHealth(amount); //no healing glitch by adding and removing heart canisters!
                else {
                    player.closeContainer();
                    player.onKillCommand();
                }
            }
        }
    }
}
