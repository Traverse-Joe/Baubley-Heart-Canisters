package com.traverse.bhc.common.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public enum VitalicSource implements StringRepresentable {
    RED("red"),
    YELLOW("yellow"),
    GREEN("green"),
    BLUE("blue");

    public static final Codec<VitalicSource> CODEC = StringRepresentable.fromEnum(VitalicSource::values);
    public static final StreamCodec<ByteBuf, VitalicSource> STREAM_CODEC = ByteBufCodecs.idMapper(i -> values()[i], VitalicSource::ordinal);

    private final String name;

    VitalicSource(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
