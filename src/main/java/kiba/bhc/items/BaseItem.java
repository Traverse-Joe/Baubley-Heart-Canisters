package kiba.bhc.items;

import kiba.bhc.BaubleyHeartCanisters;
import kiba.bhc.proxy.ClientProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItem extends Item {
    public BaseItem (String name){
        super();
        this.setRegistryName(name);
        this.setUnlocalizedName(BaubleyHeartCanisters.MODID + "." + name);
        this.setCreativeTab(ClientProxy.CREATIVE_TAB);
        ForgeRegistries.ITEMS.register(this);
    }
    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this,0,new ModelResourceLocation(getRegistryName(), "inventory" ));
    }
}
