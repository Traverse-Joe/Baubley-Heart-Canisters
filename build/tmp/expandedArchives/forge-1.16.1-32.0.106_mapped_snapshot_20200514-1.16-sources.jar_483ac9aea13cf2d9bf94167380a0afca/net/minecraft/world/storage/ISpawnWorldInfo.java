package net.minecraft.world.storage;

import net.minecraft.util.math.BlockPos;

public interface ISpawnWorldInfo extends IWorldInfo {
   /**
    * Set the x spawn position to the passed in value
    */
   void setSpawnX(int x);

   /**
    * Sets the y spawn position
    */
   void setSpawnY(int y);

   /**
    * Set the z spawn position to the passed in value
    */
   void setSpawnZ(int z);

   default void setSpawn(BlockPos spawnPoint) {
      this.setSpawnX(spawnPoint.getX());
      this.setSpawnY(spawnPoint.getY());
      this.setSpawnZ(spawnPoint.getZ());
   }
}