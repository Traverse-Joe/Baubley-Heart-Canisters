package com.traverse.bhc.common.blocks;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum VitalicBudSize implements StringRepresentable {
    SMALL("small", 3.0F, 8.0F, 1),
    MEDIUM("medium", 4.0F, 10.0F, 2),
    LARGE("large", 5.0F, 10.0F, 4),
    CLUSTER("cluster", 7.0F, 10.0F, 5);

    public static final Codec<VitalicBudSize> CODEC = StringRepresentable.fromEnum(VitalicBudSize::values);

    public final String name;
    public final float height;
    public final float width;
    public final int lightLevel;

    VitalicBudSize(String name, float height, float width, int lightLevel) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.lightLevel = lightLevel;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
