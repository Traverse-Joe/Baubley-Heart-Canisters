package com.traverse.bhc.client.easter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record CustomNameProperty() implements SelectItemModelProperty<String> {
	public static final SelectItemModelProperty.Type<CustomNameProperty, String> TYPE = SelectItemModelProperty.Type.create(
			MapCodec.unit(new CustomNameProperty()), PrimitiveCodec.STRING
	);

	@Override
	public String get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, @NotNull ItemDisplayContext displayContext) {
		return stack.has(DataComponents.CUSTOM_NAME) ? stack.get(DataComponents.CUSTOM_NAME).getString() : "";
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
