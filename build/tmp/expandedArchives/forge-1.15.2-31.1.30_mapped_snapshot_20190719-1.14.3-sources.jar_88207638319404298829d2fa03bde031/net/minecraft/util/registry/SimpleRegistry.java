package net.minecraft.util.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleRegistry<T> extends MutableRegistry<T> {
   protected static final Logger LOGGER0 = LogManager.getLogger();
   protected final IntIdentityHashBiMap<T> underlyingIntegerMap = new IntIdentityHashBiMap<>(256);
   protected final BiMap<ResourceLocation, T> registryObjects = HashBiMap.create();
   protected Object[] values;
   private int nextFreeId;

   public <V extends T> V register(int p_218382_1_, ResourceLocation p_218382_2_, V p_218382_3_) {
      this.underlyingIntegerMap.put((T)p_218382_3_, p_218382_1_);
      Validate.notNull(p_218382_2_);
      Validate.notNull(p_218382_3_);
      this.values = null;
      if (this.registryObjects.containsKey(p_218382_2_)) {
         LOGGER0.debug("Adding duplicate key '{}' to registry", (Object)p_218382_2_);
      }

      this.registryObjects.put(p_218382_2_, (T)p_218382_3_);
      if (this.nextFreeId <= p_218382_1_) {
         this.nextFreeId = p_218382_1_ + 1;
      }

      return p_218382_3_;
   }

   public <V extends T> V register(ResourceLocation p_218381_1_, V p_218381_2_) {
      return this.register(this.nextFreeId, p_218381_1_, p_218381_2_);
   }

   /**
    * Gets the name we use to identify the given object.
    */
   @Nullable
   public ResourceLocation getKey(T value) {
      return this.registryObjects.inverse().get(value);
   }

   /**
    * Gets the integer ID we use to identify the given object.
    */
   public int getId(@Nullable T value) {
      return this.underlyingIntegerMap.getId(value);
   }

   @Nullable
   public T getByValue(int value) {
      return this.underlyingIntegerMap.getByValue(value);
   }

   public Iterator<T> iterator() {
      return this.underlyingIntegerMap.iterator();
   }

   @Nullable
   public T getOrDefault(@Nullable ResourceLocation name) {
      return this.registryObjects.get(name);
   }

   /**
    * Gets the value assosiated by the key. Returns an optional and never throw exceptions.
    */
   public Optional<T> getValue(@Nullable ResourceLocation key) {
      return Optional.ofNullable(this.registryObjects.get(key));
   }

   /**
    * Gets all the keys recognized by this registry.
    */
   public Set<ResourceLocation> keySet() {
      return Collections.unmodifiableSet(this.registryObjects.keySet());
   }

   public boolean isEmpty() {
      return this.registryObjects.isEmpty();
   }

   @Nullable
   public T getRandom(Random random) {
      if (this.values == null) {
         Collection<?> collection = this.registryObjects.values();
         if (collection.isEmpty()) {
            return (T)null;
         }

         this.values = collection.toArray(new Object[collection.size()]);
      }

      return (T)this.values[random.nextInt(this.values.length)];
   }

   @OnlyIn(Dist.CLIENT)
   public boolean containsKey(ResourceLocation name) {
      return this.registryObjects.containsKey(name);
   }
}