package com.traverse.bhc.datagen;

import com.traverse.bhc.client.easter.CustomNameProperty;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.tools.ItemBladeOfVitality;
import com.traverse.bhc.common.items.tools.ItemVigorBow;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BHCModelProvider extends ModelProvider {
	public BHCModelProvider(PackOutput output) {
		super(output, BaubleyHeartCanisters.MODID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		for (DeferredHolder<Item, ? extends Item> registryObject : RegistryHandler.ITEMS.getEntries()) {
			if (registryObject.get() instanceof ItemBladeOfVitality) {
				generateBladeOfVitality(itemModels);
			} else if(registryObject.get() instanceof ItemVigorBow) {
				generateVigorBow(itemModels);
			} else {
				itemModels.generateFlatItem(registryObject.get(), ModelTemplates.FLAT_ITEM);
			}
		}
	}

	private void generateBladeOfVitality(ItemModelGenerators itemModels) {
		ItemBladeOfVitality bladeOfVitality = RegistryHandler.BLADE_OF_VITALITY.get();
		ItemModel.Unbaked base = ItemModelUtils.plainModel(itemModels.createFlatItemModel(bladeOfVitality, "", ModelTemplates.FLAT_HANDHELD_ITEM));
		ItemModel.Unbaked lashBlade = ItemModelUtils.plainModel(createFlatItemModel(itemModels, "lash_blade", ModelTemplates.FLAT_HANDHELD_ITEM));
		ItemModel.Unbaked traverseBlade = ItemModelUtils.plainModel(createFlatItemModel(itemModels, "traverse_blade", ModelTemplates.FLAT_HANDHELD_ITEM));
		ItemModel.Unbaked jamiscusBlade = ItemModelUtils.plainModel(createFlatItemModel(itemModels, "jamiscus_blade", ModelTemplates.FLAT_HANDHELD_ITEM));
		itemModels.itemModelOutput
				.accept(
						bladeOfVitality,
						ItemModelUtils.conditional(
								ItemModelUtils.hasComponent(DataComponents.CUSTOM_NAME),
								ItemModelUtils.select(
										new CustomNameProperty(),
										base,
										ItemModelUtils.when("beautiful eyes", lashBlade),
										ItemModelUtils.when("traverse", traverseBlade),
										ItemModelUtils.when("jamiscus", jamiscusBlade)
								),
								base
						)
				);
	}

	public ResourceLocation createFlatItemModel(ItemModelGenerators itemModels, String name, ModelTemplate modelTemplate) {
		return modelTemplate.create(
				ResourceLocation.fromNamespaceAndPath(BaubleyHeartCanisters.MODID, "item/" + name),
				TextureMapping.layer0(ResourceLocation.fromNamespaceAndPath(BaubleyHeartCanisters.MODID, "item/" + name)),
				itemModels.modelOutput
		);
	}

	public void generateVigorBow(ItemModelGenerators itemModels) {
		ItemVigorBow vigorBow = RegistryHandler.VIGOR_BOW.get();
		ItemModel.Unbaked base = ItemModelUtils.plainModel(itemModels.createFlatItemModel(vigorBow, "", ModelTemplates.BOW));
		ItemModel.Unbaked pulling0 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(vigorBow, "_pulling_0", ModelTemplates.BOW));
		ItemModel.Unbaked pulling1 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(vigorBow, "_pulling_1", ModelTemplates.BOW));
		ItemModel.Unbaked pulling2 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(vigorBow, "_pulling_2", ModelTemplates.BOW));
		itemModels.itemModelOutput
				.accept(
						vigorBow,
						ItemModelUtils.conditional(
								ItemModelUtils.isUsingItem(),
								ItemModelUtils.rangeSelect(
										new UseDuration(false),
										0.05F,
										pulling0,
										ItemModelUtils.override(pulling1, 0.65F),
										ItemModelUtils.override(pulling2, 0.9F)
								),
								base
						)
				);
	}
}
