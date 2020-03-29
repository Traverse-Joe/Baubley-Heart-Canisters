package net.minecraft.world.storage;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import net.minecraft.nbt.CompoundNBT;

public class MapIdTracker extends WorldSavedData {
   private final Object2IntMap<String> field_215163_a = new Object2IntOpenHashMap<>();

   public MapIdTracker() {
      super("idcounts");
      this.field_215163_a.defaultReturnValue(-1);
   }

   /**
    * reads in data from the NBTTagCompound into this MapDataBase
    */
   public void read(CompoundNBT nbt) {
      this.field_215163_a.clear();

      for(String s : nbt.keySet()) {
         if (nbt.contains(s, 99)) {
            this.field_215163_a.put(s, nbt.getInt(s));
         }
      }

   }

   public CompoundNBT write(CompoundNBT compound) {
      for(Entry<String> entry : this.field_215163_a.object2IntEntrySet()) {
         compound.putInt(entry.getKey(), entry.getIntValue());
      }

      return compound;
   }

   public int func_215162_a() {
      int i = this.field_215163_a.getInt("map") + 1;
      this.field_215163_a.put("map", i);
      this.markDirty();
      return i;
   }
}