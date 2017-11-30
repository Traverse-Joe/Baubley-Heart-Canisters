package kiba.bhc.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import kiba.bhc.BaubleyHeartCanisters;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BaseHeartCanister extends Item implements IBauble {
    public BaseHeartCanister(String name , Integer healthScale){
        super();
        this.setRegistryName(name);
        this.setUnlocalizedName(BaubleyHeartCanisters.MODID + "." + name);
        ForgeRegistries.ITEMS.register(this);

    }
    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this,0,new ModelResourceLocation(getRegistryName(), "inventory" ));
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return null;
    }
}

