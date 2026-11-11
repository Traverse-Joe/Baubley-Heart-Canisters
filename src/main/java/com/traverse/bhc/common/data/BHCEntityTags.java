package com.traverse.bhc.common.data;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class BHCEntityTags {

    public static final TagKey<EntityType<?>> NO_HEARTS = TagKey.create(Registries.ENTITY_TYPE, BaubleyHeartCanisters.id("no_hearts"));
}
