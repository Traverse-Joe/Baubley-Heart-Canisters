package com.traverse.bhc.common.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.traverse.bhc.common.init.RegistryHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public record VitalicSparkOptions(Vector3fc color, float scale, int lifetime) implements ParticleOptions {

    public static final MapCodec<VitalicSparkOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(VitalicSparkOptions::color),
            Codec.FLOAT.fieldOf("scale").forGetter(VitalicSparkOptions::scale),
            Codec.INT.fieldOf("lifetime").forGetter(VitalicSparkOptions::lifetime)
    ).apply(i, VitalicSparkOptions::new));

    public static final StreamCodec<ByteBuf, VitalicSparkOptions> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, VitalicSparkOptions::color,
            ByteBufCodecs.FLOAT, VitalicSparkOptions::scale,
            ByteBufCodecs.VAR_INT, VitalicSparkOptions::lifetime,
            VitalicSparkOptions::new
    );

    public VitalicSparkOptions(int rgb, float scale, int lifetime) {
        this(unpack(rgb), scale, lifetime);
    }

    private static Vector3fc unpack(int rgb) {
        return new Vector3f(((rgb >> 16) & 0xFF) / 255F, ((rgb >> 8) & 0xFF) / 255F, (rgb & 0xFF) / 255F);
    }

    @Override
    public ParticleType<VitalicSparkOptions> getType() {
        return RegistryHandler.VITALIC_SPARK.get();
    }
}
