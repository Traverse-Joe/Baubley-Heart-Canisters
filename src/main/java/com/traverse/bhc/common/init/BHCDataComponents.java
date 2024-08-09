package com.traverse.bhc.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

import java.util.List;

public class BHCDataComponents {

    public static final Codec<List<Integer>> STORED_HEARTS_CODEC = ExtraCodecs.NON_NEGATIVE_INT.listOf();

    public static final DataComponentType<List<Integer>> STORED_HEARTS = DataComponentType.<List<Integer>>builder()
            .persistent(STORED_HEARTS_CODEC)
            .networkSynchronized(ByteBufCodecs.fromCodec(STORED_HEARTS_CODEC))
            .build();
}
