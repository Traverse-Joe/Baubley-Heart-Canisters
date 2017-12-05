package kiba.bhc.proxy;

import kiba.bhc.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event){
      super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {


        super.postInit(event);
    }
    @SubscribeEvent
    public static void rendModel(ModelRegistryEvent event){
        regItem(ModItems.RED_HEART);
        regItem(ModItems.ORANGE_HEART);
        regItem(ModItems.GREEN_HEART);
        regItem(ModItems.BLUE_HEART);

        regItem(ModItems.CANISTER);
        regItem(ModItems.RED_HEART_CANISTER);
        regItem(ModItems.ORANGE_HEART_CANISTER);
        regItem(ModItems.GREEN_HEART_CANISTER);
        regItem(ModItems.BLUE_HEART_CANISTER);

        regItem(ModItems.WITHER_BONE);
        regItem(ModItems.RELIC_APPLE);
        regItem(ModItems.HEART_AMULET);

    }
    public static void regItem(Item item){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
