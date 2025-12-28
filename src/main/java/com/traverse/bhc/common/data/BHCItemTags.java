package com.traverse.bhc.common.data;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.util.GlobalTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BHCItemTags {

    public static final TagKey<Item> HEARTS = GlobalTag.of(Registries.ITEM, "hearts");
    public static final TagKey<Item> HEART_AMULETS = TagKey.create(Registries.ITEM, BaubleyHeartCanisters.id("heart_amulets"));
    public static final TagKey<Item> HEART_BELTS = TagKey.create(Registries.ITEM, BaubleyHeartCanisters.id("heart_belts"));
    public static final TagKey<Item> WITHER_BONES = GlobalTag.of(Registries.ITEM, "wither_bones");
}
