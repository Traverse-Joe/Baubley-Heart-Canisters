package sora.bhc.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IProxy {
  default World getWorld(){
    return null;
  }

  default PlayerEntity getPlayer(){
    return null;
  }

  public void CommonSetup();
}
