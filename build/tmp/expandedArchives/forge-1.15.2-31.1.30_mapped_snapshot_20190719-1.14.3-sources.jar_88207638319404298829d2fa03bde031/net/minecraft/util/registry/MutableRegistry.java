package net.minecraft.util.registry;

import net.minecraft.util.ResourceLocation;

public abstract class MutableRegistry<T> extends Registry<T> {
   public abstract <V extends T> V register(int p_218382_1_, ResourceLocation p_218382_2_, V p_218382_3_);

   public abstract <V extends T> V register(ResourceLocation p_218381_1_, V p_218381_2_);

   public abstract boolean isEmpty();
}