package com.traverse.bhc.common.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record VitalicCharge(VitalicSource source, int charge) {

    public static final Codec<VitalicCharge> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            VitalicSource.CODEC.fieldOf("source").forGetter(VitalicCharge::source),
            Codec.INT.fieldOf("charge").forGetter(VitalicCharge::charge)
    ).apply(instance, VitalicCharge::new));

    public static final StreamCodec<ByteBuf, VitalicCharge> STREAM_CODEC = StreamCodec.composite(
            VitalicSource.STREAM_CODEC, VitalicCharge::source,
            ByteBufCodecs.VAR_INT, VitalicCharge::charge,
            VitalicCharge::new
    );

    public VitalicCharge withCharge(int newCharge) {
        return new VitalicCharge(source, newCharge);
    }
}
