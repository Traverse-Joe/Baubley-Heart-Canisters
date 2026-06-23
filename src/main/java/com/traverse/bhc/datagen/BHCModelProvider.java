package com.traverse.bhc.datagen;

import com.traverse.bhc.client.easter.CustomNameProperty;
import com.traverse.bhc.client.properties.VitalicSourceProperty;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.blocks.BuddingCrystalBlock;
import com.traverse.bhc.common.blocks.VitalicBudBlock;
import com.traverse.bhc.common.init.RegistryHandler;
import com.traverse.bhc.common.items.ItemSoulHeartCrystal;
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
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class BHCModelProvider extends ModelProvider {
	public BHCModelProvider(PackOutput output) {
		super(output, BaubleyHeartCanisters.MODID);
	}

	@Override
	protected Stream<? extends Holder<Block>> getKnownBlocks() {
		return super.getKnownBlocks().filter(holder -> {
			Block b = holder.value();
			return !(b instanceof BuddingCrystalBlock) && !(b instanceof VitalicBudBlock);
		});
	}

	@Override
	protected Stream<? extends Holder<Item>> getKnownItems() {
		return super.getKnownItems().filter(holder -> {
			if (!(holder.value() instanceof BlockItem bi)) return true;
			Block b = bi.getBlock();
			return !(b instanceof BuddingCrystalBlock) && !(b instanceof VitalicBudBlock);
		});
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		for (DeferredHolder<Item, ? extends Item> registryObject : RegistryHandler.ITEMS.getEntries()) {
			Item item = registryObject.get();
			if (item instanceof BlockItem) continue;
			if (item instanceof ItemBladeOfVitality) {
				generateBladeOfVitality(itemModels);
			} else if(item instanceof ItemVigorBow) {
				generateVigorBow(itemModels);
			} else if (item instanceof ItemSoulHeartCrystal) {
				generateSoulHeartCrystal(itemModels);
			} else {
				itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
			}
		}
	}

	private void generateBladeOfVitality(ItemModelGenerators itemModels) {
		Item bladeOfVitality = RegistryHandler.BLADE_OF_VITALITY.asItem();
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

	private void generateSoulHeartCrystal(ItemModelGenerators itemModels) {
		Item crystal = RegistryHandler.SOUL_HEART_CRYSTAL.asItem();
		ItemModel.Unbaked base = ItemModelUtils.plainModel(itemModels.createFlatItemModel(crystal, "", ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked red = ItemModelUtils.plainModel(createFlatItemModel(itemModels, "soul_heart_crystal_red", ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked yellow = ItemModelUtils.plainModel(createFlatItemModel(itemModels, "soul_heart_crystal_yellow", ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked green = ItemModelUtils.plainModel(createFlatItemModel(itemModels, "soul_heart_crystal_green", ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked blue = ItemModelUtils.plainModel(createFlatItemModel(itemModels, "soul_heart_crystal_blue", ModelTemplates.FLAT_ITEM));
		itemModels.itemModelOutput
				.accept(
						crystal,
						ItemModelUtils.conditional(
								ItemModelUtils.hasComponent(RegistryHandler.VITALIC_CHARGE_COMPONENT.get()),
								ItemModelUtils.select(
										new VitalicSourceProperty(),
										base,
										ItemModelUtils.when("red", red),
										ItemModelUtils.when("yellow", yellow),
										ItemModelUtils.when("green", green),
										ItemModelUtils.when("blue", blue)
								),
								base
						)
				);
	}

	public Identifier createFlatItemModel(ItemModelGenerators itemModels, String name, ModelTemplate modelTemplate) {
		return modelTemplate.create(
				Identifier.fromNamespaceAndPath(BaubleyHeartCanisters.MODID, "item/" + name),
				TextureMapping.layer0(new Material(Identifier.fromNamespaceAndPath(BaubleyHeartCanisters.MODID, "item/" + name))),
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
