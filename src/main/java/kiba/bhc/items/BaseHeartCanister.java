package kiba.bhc.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.UUID;


public class BaseHeartCanister extends Item implements IBauble {

    protected final UUID id;

    public BaseHeartCanister(String name, final UUID type){
        super();
        this.setRegistryName(name + "_heart_canister");
        this.setUnlocalizedName(name + "_heart_canister");
        this.setCreativeTab(BaubleyHeartCanisters.CREATIVE_TAB);
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
        float diff = player.getMaxHealth() - player.getHealth();
        if(health_modifier != null) {
            if(health_modifier.getAmount() == itemstack.getCount() * 2) return;
            health.removeModifier(this.id);
        }
        health.applyModifier(new AttributeModifier(this.id, Reference.MODID + ":hearts", itemstack.getCount() * 2, 0));
        setHealthDiff(diff, player);
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        IAttributeInstance health = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier health_modifier = health.getModifier(this.id);
        if(health_modifier != null) {
            float diff = player.getMaxHealth() - player.getHealth();
            health.removeModifier(this.id); //ensure that the health is reset when removing the stack.
            setHealthDiff(diff, player);
        }
    }

    private static void setHealthDiff(float diff, EntityLivingBase player) {
        float amount = MathHelper.clamp(player.getMaxHealth() - diff, 0.5F, player.getMaxHealth()); //bugfix: death by removing heart canisters could cause lost items!
        player.setHealth(amount); //no healing glitch by adding and removing heart canisters!
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

