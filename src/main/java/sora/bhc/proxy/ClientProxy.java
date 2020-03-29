package sora.bhc.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public PlayerEntity getPlayer(){
        return Minecraft.getInstance().player;
    }

    @Override
    public World getWorld(){
        return Minecraft.getInstance().world;
    }

    @Override
    public void CommonSetup(){

    }


}
