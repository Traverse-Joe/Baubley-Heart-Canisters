package net.minecraft.util.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.codec.RangeCodec;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleRegistry<T> extends MutableRegistry<T> {
   protected static final Logger LOGGER0 = LogManager.getLogger();
   /** The backing store that maps Integers to objects. */
   protected final IntIdentityHashBiMap<T> underlyingIntegerMap = new IntIdentityHashBiMap<>(256);
   protected final BiMap<ResourceLocation, T> registryObjects = HashBiMap.create();
   private final BiMap<RegistryKey<T>, T> field_239649_bb_ = HashBiMap.create();
   private final Set<RegistryKey<T>> field_239650_bc_ = Sets.newIdentityHashSet();
   protected Object[] values;
   private int nextFreeId;

   public SimpleRegistry(RegistryKey<Registry<T>> p_i232509_1_, Lifecycle p_i232509_2_) {
      super(p_i232509_1_, p_i232509_2_);
   }

   public <V extends T> V register(int id, RegistryKey<T> name, V instance) {
      this.underlyingIntegerMap.put((T)instance, id);
      Validate.notNull(name);
      Validate.notNull((T)instance);
      this.values = null;
      if (this.field_239649_bb_.containsKey(name)) {
         LOGGER0.debug("Adding duplicate key '{}' to registry", (Object)name);
      }

      this.registryObjects.put(name.func_240901_a_(), (T)instance);
      this.field_239649_bb_.put(name, (T)instance);
      if (this.nextFreeId <= id) {
         this.nextFreeId = id + 1;
      }

      return instance;
   }

   public <V extends T> V register(RegistryKey<T> name, V instance) {
      return this.register(this.nextFreeId, name, instance);
   }

   /**
    * Gets the name we use to identify the given object.
    */
   @Nullable
   public ResourceLocation getKey(T value) {
      return this.registryObjects.inverse().get(value);
   }

   public Optional<RegistryKey<T>> func_230519_c_(T p_230519_1_) {
      return Optional.ofNullable(this.field_239649_bb_.inverse().get(p_230519_1_));
   }

   /**
    * Gets the integer ID we use to identify the given object.
    */
   public int getId(@Nullable T value) {
      return this.underlyingIntegerMap.getId(value);
   }

   @Nullable
   public T func_230516_a_(@Nullable RegistryKey<T> p_230516_1_) {
      return this.field_239649_bb_.get(p_230516_1_);
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

   public Set<Entry<RegistryKey<T>, T>> func_239659_c_() {
      return Collections.unmodifiableMap(this.field_239649_bb_).entrySet();
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

      return Util.func_240989_a_((T[])this.values, random);
   }

   public boolean containsKey(ResourceLocation name) {
      return this.registryObjects.containsKey(name);
   }

   public boolean func_230518_b_(int p_230518_1_) {
      return this.underlyingIntegerMap.func_232993_b_(p_230518_1_);
   }

   public boolean func_239660_c_(RegistryKey<T> p_239660_1_) {
      return this.field_239650_bc_.contains(p_239660_1_);
   }

   public void func_239662_d_(RegistryKey<T> p_239662_1_) {
      this.field_239650_bc_.add(p_239662_1_);
   }

   public static <T> Codec<SimpleRegistry<T>> func_241743_a_(RegistryKey<Registry<T>> p_241743_0_, Lifecycle p_241743_1_, MapCodec<T> p_241743_2_) {
      return RangeCodec.func_241293_a_(p_241743_0_, p_241743_2_).codec().listOf().xmap((p_239655_2_) -> {
         SimpleRegistry<T> simpleregistry = new SimpleRegistry<>(p_241743_0_, p_241743_1_);

         for(Pair<RegistryKey<T>, T> pair : p_239655_2_) {
            simpleregistry.register(pair.getFirst(), pair.getSecond());
         }

         return simpleregistry;
      }, (p_239657_0_) -> {
         com.google.common.collect.ImmutableList.Builder<Pair<RegistryKey<T>, T>> builder = ImmutableList.builder();

         for(T t : p_239657_0_.underlyingIntegerMap) {
            builder.add(Pair.of(p_239657_0_.func_230519_c_(t).get(), t));
         }

         return builder.build();
      });
   }

   public static <T> Codec<SimpleRegistry<T>> func_241744_b_(RegistryKey<Registry<T>> p_241744_0_, Lifecycle p_241744_1_, MapCodec<T> p_241744_2_) {
      return SimpleRegistryCodec.func_241793_a_(p_241744_0_, p_241744_1_, p_241744_2_);
   }

   public static <T> Codec<SimpleRegistry<T>> func_241745_c_(RegistryKey<Registry<T>> p_241745_0_, Lifecycle p_241745_1_, MapCodec<T> p_241745_2_) {
      return Codec.unboundedMap(ResourceLocation.field_240908_a_.xmap(RegistryKey.func_240902_a_(p_241745_0_), RegistryKey::func_240901_a_), p_241745_2_.codec()).xmap((p_239656_2_) -> {
         SimpleRegistry<T> simpleregistry = new SimpleRegistry<>(p_241745_0_, p_241745_1_);
         p_239656_2_.forEach((p_239653_1_, p_239653_2_) -> {
            simpleregistry.register(simpleregistry.nextFreeId, p_239653_1_, p_239653_2_);
            simpleregistry.func_239662_d_(p_239653_1_);
         });
         return simpleregistry;
      }, (p_239651_0_) -> {
         Builder<RegistryKey<T>, T> builder = ImmutableMap.builder();
         p_239651_0_.field_239649_bb_.entrySet().stream().filter((p_239652_1_) -> {
            return p_239651_0_.func_239660_c_(p_239652_1_.getKey());
         }).forEach(builder::put);
         return builder.build();
      });
   }
}