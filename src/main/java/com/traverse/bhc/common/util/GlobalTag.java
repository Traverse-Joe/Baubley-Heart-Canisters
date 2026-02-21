package com.traverse.bhc.common.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;

public class GlobalTag {

    public static <T> TagKey<T> of(ResourceKey<? extends Registry<T>> registry, String name) {
        return TagKey.create(registry, Identifier.fromNamespaceAndPath("c", name));
    }
}
