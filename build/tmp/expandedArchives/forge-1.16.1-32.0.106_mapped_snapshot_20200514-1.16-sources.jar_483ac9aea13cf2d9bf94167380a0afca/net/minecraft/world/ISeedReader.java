package net.minecraft.world;

public interface ISeedReader extends IWorld {
   /**
    * gets the random world seed
    */
   long getSeed();
}