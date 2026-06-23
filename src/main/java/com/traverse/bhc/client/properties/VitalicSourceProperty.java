package com.traverse.bhc.client.properties;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.util.VitalicCharge;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record VitalicSourceProperty() implements SelectItemModelProperty<String> {
    public static final SelectItemModelProperty.Type<VitalicSourceProperty, String> TYPE = SelectItemModelProperty.Type.create(
            MapCodec.unit(new VitalicSourceProperty()), PrimitiveCodec.STRING
    );

    @Override
    public String get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, @NotNull ItemDisplayContext displayContext) {
        VitalicCharge data = stack.get(RegistryHandler.VITALIC_CHARGE_COMPONENT.get());
        return data == null ? "" : data.source().getSerializedName();
    }

    @Override
    public Codec<String> valueCodec() {
        return PrimitiveCodec.STRING;
    }

    @Override
    public Type<? extends SelectItemModelProperty<String>, String> type() {
        return TYPE;
    }
}
