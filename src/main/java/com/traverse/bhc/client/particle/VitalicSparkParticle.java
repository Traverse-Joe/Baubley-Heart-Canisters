package com.traverse.bhc.client.particle;

import com.traverse.bhc.common.particle.VitalicSparkOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3fc;

public class VitalicSparkParticle extends SingleQuadParticle {

    private final SpriteSet sprites;
    private final float initialScale;
    private final float baseR;
    private final float baseG;
    private final float baseB;
    private final float endR;
    private final float endG;
    private final float endB;
    private final float wanderStrength;

    VitalicSparkParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, VitalicSparkOptions options, SpriteSet sprites) {
        super(level, x, y, z, dx, dy, dz, sprites.first());
        this.sprites = sprites;
        this.lifetime = Math.max(1, options.lifetime());
        this.initialScale = Math.max(0.01F, options.scale());
        this.quadSize = initialScale;
        Vector3fc c = options.color();
        this.baseR = Mth.clamp(c.x(), 0F, 1F);
        this.baseG = Mth.clamp(c.y(), 0F, 1F);
        this.baseB = Mth.clamp(c.z(), 0F, 1F);
        this.endR = baseR * 0.35F;
        this.endG = baseG * 0.35F;
        this.endB = baseB * 0.35F;
        this.rCol = baseR;
        this.gCol = baseG;
        this.bCol = baseB;
        this.alpha = 1.0F;
        this.hasPhysics = false;
        this.friction = 0.86F;
        this.xd = dx;
        this.yd = dy;
        this.zd = dz;
        this.wanderStrength = 0.0008F + random.nextFloat() * 0.0012F;
        setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        this.xo = x;
        this.yo = y;
        this.zo = z;
        if (age++ >= lifetime) {
            remove();
            return;
        }

        xd += (random.nextFloat() - 0.5F) * wanderStrength;
        yd += (random.nextFloat() - 0.5F) * wanderStrength;
        zd += (random.nextFloat() - 0.5F) * wanderStrength;

        move(xd, yd, zd);
        xd *= friction;
        yd *= friction;
        zd *= friction;

        float t = age / (float) lifetime;
        rCol = Mth.lerp(t, baseR, endR);
        gCol = Mth.lerp(t, baseG, endG);
        bCol = Mth.lerp(t, baseB, endB);
        alpha = 1.0F - t * 0.7F;

        setSpriteFromAge(sprites);
    }

    @Override
    public float getQuadSize(float partialTicks) {
        float t = (age + partialTicks) / (float) lifetime;
        float curve = 1.0F - t;
        return initialScale * Mth.clamp(curve, 0F, 1F);
    }

    @Override
    public int getLightCoords(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    protected SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Override
    public @NotNull ParticleRenderType getGroup() {
        return ParticleRenderType.SINGLE_QUADS;
    }

    public static final class Provider implements ParticleProvider<VitalicSparkOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(VitalicSparkOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz, RandomSource random) {
            return new VitalicSparkParticle(level, x, y, z, dx, dy, dz, options, sprites);
        }
    }
}
