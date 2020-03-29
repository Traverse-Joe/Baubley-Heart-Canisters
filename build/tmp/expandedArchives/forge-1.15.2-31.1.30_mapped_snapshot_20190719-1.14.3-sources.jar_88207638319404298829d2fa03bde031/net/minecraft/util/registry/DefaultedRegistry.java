package net.minecraft.util.registry;

import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;

public class DefaultedRegistry<T> extends SimpleRegistry<T> {
   private final ResourceLocation defaultValueKey;
   private T defaultValue;

   public DefaultedRegistry(String p_i50797_1_) {
      this.defaultValueKey = new ResourceLocation(p_i50797_1_);
   }

   public <V extends T> V register(int p_218382_1_, ResourceLocation p_218382_2_, V p_218382_3_) {
      if (this.defaultValueKey.equals(p_218382_2_)) {
         this.defaultValue = (T)p_218382_3_;
      }

      return super.register(p_218382_1_, p_218382_2_, p_218382_3_);
   }

   /**
    * Gets the integer ID we use to identify the given object.
    */
   public int getId(@Nullable T value) {
      int i = super.getId(value);
      return i == -1 ? super.getId(this.defaultValue) : i;
   }

   /**
    * Gets the name we use to identify the given object.
    */
   @Nonnull
   public ResourceLocation getKey(T value) {
      ResourceLocation resourcelocation = super.getKey(value);
      return resourcelocation == null ? this.defaultValueKey : resourcelocation;
   }

   @Nonnull
   public T getOrDefault(@Nullable ResourceLocation name) {
      T t = (T)super.getOrDefault(name);
      return (T)(t == null ? this.defaultValue : t);
   }

   @Nonnull
   public T getByValue(int value) {
      T t = (T)super.getByValue(value);
      return (T)(t == null ? this.defaultValue : t);
   }

   @Nonnull
   public T getRandom(Random random) {
      T t = (T)super.getRandom(random);
      return (T)(t == null ? this.defaultValue : t);
   }

   public ResourceLocation getDefaultKey() {
      return this.defaultValueKey;
   }
}