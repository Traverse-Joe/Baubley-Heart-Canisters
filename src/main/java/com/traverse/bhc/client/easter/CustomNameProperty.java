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

import java.util.List;
import java.util.Locale;

public record CustomNameProperty() implements SelectItemModelProperty<String> {
	public static final SelectItemModelProperty.Type<CustomNameProperty, String> TYPE = SelectItemModelProperty.Type.create(
			MapCodec.unit(new CustomNameProperty()), PrimitiveCodec.STRING
	);

	private static final List<String> KEYWORDS = List.of("beautiful eyes", "traverse", "jamiscus");

	@Override
	public String get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, @NotNull ItemDisplayContext displayContext) {
		if (!stack.has(DataComponents.CUSTOM_NAME)) {
			return "";
		}
		String name = stack.get(DataComponents.CUSTOM_NAME).getString().toLowerCase(Locale.ROOT);
		for (String keyword : KEYWORDS) {
			if (name.contains(keyword)) {
				return keyword;
			}
		}
		return name;
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
