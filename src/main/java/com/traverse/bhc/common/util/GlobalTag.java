package com.traverse.bhc.common.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class GlobalTag {

    public static <T> TagKey<T> of(ResourceKey<? extends Registry<T>> registry, String name) {
        return TagKey.create(registry, ResourceLocation.fromNamespaceAndPath("c", name));
    }
}
