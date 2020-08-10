package net.minecraft.util.registry;

import com.mojang.serialization.Lifecycle;
import net.minecraft.util.RegistryKey;

public abstract class MutableRegistry<T> extends Registry<T> {
   public MutableRegistry(RegistryKey<Registry<T>> p_i232512_1_, Lifecycle p_i232512_2_) {
      super(p_i232512_1_, p_i232512_2_);
   }

   public abstract <V extends T> V register(int id, RegistryKey<T> name, V instance);

   public abstract <V extends T> V register(RegistryKey<T> name, V instance);

   public abstract void func_239662_d_(RegistryKey<T> p_239662_1_);
}