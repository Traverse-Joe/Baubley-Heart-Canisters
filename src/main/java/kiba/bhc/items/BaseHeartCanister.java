package kiba.bhc.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import kiba.bhc.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.UUID;


public class BaseHeartCanister extends core.upcraftlp.craftdev.api.item.Item implements IBauble {

    protected final UUID id;

    public BaseHeartCanister(String name, final UUID type){
        super(name + "_heart_canister");
        this.setMaxStackSize(10);
        this.id = type;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        //wanna make it customizable by default in the super like i did with my heart
        return BaubleType.TRINKET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if(player.world.isRemote) return;
        IAttributeInstance health = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier health_modifier = health.getModifier(this.id);
        int current = 0;
        if(health_modifier != null) {
            if(health_modifier.getAmount() == itemstack.getCount() * 2) return;
            current = (int) health_modifier.getAmount();
            health.removeModifier(this.id);
        }
        health.applyModifier(new AttributeModifier(this.id, Reference.MODID + ":hearts", itemstack.getCount() * 2, 0));
        int diff = (int) health.getModifier(this.id).getAmount() - current;
        player.setHealth(player.getHealth() + diff); //no healing glitch by adding and removing heart canisters!
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        IAttributeInstance health = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier health_modifier = health.getModifier(this.id);
        if(health_modifier != null) {
            int extraHealth = (int) health_modifier.getAmount();
            player.setHealth(player.getHealth() - extraHealth); //no healing glitch by adding and removing heart canisters!
            health.removeModifier(this.id); //ensure that the health is reset when removing the stack.
        }

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    //FIXME is this needed?
    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }


}

