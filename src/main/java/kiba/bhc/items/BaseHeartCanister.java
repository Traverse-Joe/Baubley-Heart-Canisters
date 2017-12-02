package kiba.bhc.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import kiba.bhc.Reference;
import kiba.bhc.proxy.CommonProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BaseHeartCanister extends Item implements IBauble {
public final int heartIn;
    public BaseHeartCanister(String name , Integer amount){
        super();
        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MODID + "." + name);
        this.setCreativeTab(CommonProxy.CREATIVE_TAB);
        this.setMaxStackSize(10); //UPCRAFT BAUBLE STACK (NO NEED FOR NBT) 
        this.heartIn = amount;
        ForgeRegistries.ITEMS.register(this);

    }
    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this,0,new ModelResourceLocation(getRegistryName(), "inventory" ));
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        //wanna make it customizable by default in the super like i did with my heart
        return BaubleType.TRINKET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        //HOW THE HELL DOES ONE.....MAKE HEARTS EXPAND THE PLAYERS MAX HEALTH

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }


}

