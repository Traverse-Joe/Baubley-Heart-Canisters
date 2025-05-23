package com.traverse.bhc.common.init;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.container.SoulHeartAmuletContainer;
import com.traverse.bhc.common.container.VigorBowContainer;
import com.traverse.bhc.common.container.base.SoulContainerMenu;
import com.traverse.bhc.common.items.*;
import com.traverse.bhc.common.items.tools.ItemBladeOfVitality;
import com.traverse.bhc.common.items.tools.ItemVigorBow;
import com.traverse.bhc.common.recipes.HeartAmuletRecipe.Serializer;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;


public class RegistryHandler {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPESERIALIZER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BaubleyHeartCanisters.MODID);

    //Items
    public static final DeferredItem<BaseHeartCanister> RED_CANISTER = ITEMS.registerItem("red_heart_canister", (properties) -> new BaseHeartCanister(properties, HeartType.RED));
    public static final DeferredItem<BaseHeartCanister> YELLOW_CANISTER = ITEMS.registerItem("yellow_heart_canister", (properties) -> new BaseHeartCanister(properties, HeartType.YELLOW));
    public static final DeferredItem<BaseHeartCanister> GREEN_CANISTER = ITEMS.registerItem("green_heart_canister", (properties) -> new BaseHeartCanister(properties, HeartType.GREEN));
    public static final DeferredItem<BaseHeartCanister> BLUE_CANISTER = ITEMS.registerItem("blue_heart_canister", (properties) -> new BaseHeartCanister(properties, HeartType.BLUE));
    public static final DeferredItem<BaseHeartCanister> SOUL_CANISTER = ITEMS.registerItem("soul_heart_canister", (properties) -> new BaseHeartCanister(properties, HeartType.SOUL));

    public static final DeferredItem<BaseItem> RED_HEART_MELTED = ITEMS.registerItem("red_heart_melted", BaseItem::new);
    public static final DeferredItem<BaseItem> YELLOW_HEART_MELTED = ITEMS.registerItem("yellow_heart_melted", BaseItem::new);
    public static final DeferredItem<BaseItem> GREEN_HEART_MELTED = ITEMS.registerItem("green_heart_melted", BaseItem::new);
    public static final DeferredItem<BaseItem> BLUE_HEART_MELTED = ITEMS.registerItem("blue_heart_melted", BaseItem::new);

    public static final DeferredItem<ItemHeartPatch> RED_HEART_PATCH = ITEMS.registerItem("red_heart_patch", (properties) -> new ItemHeartPatch(properties, 2, 5 * 20, 20, Color.red.getRGB()));
    public static final DeferredItem<ItemHeartPatch> YELLOW_HEART_PATCH = ITEMS.registerItem("yellow_heart_patch", (properties) -> new ItemHeartPatch(properties, 6, 10 * 20, 25, Color.yellow.getRGB()));
    public static final DeferredItem<ItemHeartPatch> GREEN_HEART_PATCH = ITEMS.registerItem("green_heart_patch", (properties) -> new ItemHeartPatch(properties, 10, 20 * 20, 30, Color.green.getRGB()));
    public static final DeferredItem<ItemHeartPatch> BLUE_HEART_PATCH = ITEMS.registerItem("blue_heart_patch", (properties) -> new ItemHeartPatch(properties, 20, 30 * 20, 50, Color.blue.getRGB()));

    public static final DeferredItem<ItemHeart> RED_HEART = ITEMS.registerItem("red_heart", (properties) -> new ItemHeart(properties, HeartType.RED));
    public static final DeferredItem<ItemHeart> YELLOW_HEART = ITEMS.registerItem("yellow_heart", (properties) -> new ItemHeart(properties, HeartType.YELLOW));
    public static final DeferredItem<ItemHeart> GREEN_HEART = ITEMS.registerItem("green_heart", (properties) -> new ItemHeart(properties, HeartType.GREEN));
    public static final DeferredItem<ItemHeart> BLUE_HEART = ITEMS.registerItem("blue_heart", (properties) -> new ItemHeart(properties, HeartType.BLUE));
    public static final DeferredItem<BaseItem> CANISTER = ITEMS.registerItem("canister", BaseItem::new);
    public static final DeferredItem<ItemBladeOfVitality> BLADE_OF_VITALITY = ITEMS.registerItem("blade_of_vitality", ItemBladeOfVitality::new);
    public static final DeferredItem<ItemVigorBow> VIGOR_BOW = ITEMS.registerItem("vigor_bow", ItemVigorBow::new);

    public static final DeferredItem<BaseItem> WITHER_BONE = ITEMS.registerItem("wither_bone", BaseItem::new);
    public static final DeferredItem<ItemRelicApple> RELIC_APPLE = ITEMS.registerItem("relic_apple", ItemRelicApple::new);
    public static final DeferredItem<ItemHeartAmulet> HEART_AMULET = ITEMS.registerItem("heart_amulet", ItemHeartAmulet::new);
    public static final DeferredItem<ItemSoulHeartAmulet> SOUL_HEART_AMULET = ITEMS.registerItem("soul_heart_amulet", ItemSoulHeartAmulet::new);
    public static final DeferredItem<BaseItem> SOUL_HEART_CRYSTAL = ITEMS.registerItem("soul_heart_crystal", BaseItem::new);

    //Container
    public static final DeferredHolder<MenuType<?>, MenuType<HeartAmuletContainer>> HEART_AMUlET_CONTAINER = CONTAINERS.register("heart_amulet", () -> IMenuTypeExtension.create((windowId, inv, data) -> new HeartAmuletContainer(windowId, inv, SoulContainerMenu.readSlotId(data))));
    public static final DeferredHolder<MenuType<?>, MenuType<SoulHeartAmuletContainer>> SOUL_HEART_AMUlET_CONTAINER = CONTAINERS.register("soul_heart_amulet", () -> IMenuTypeExtension.create((windowId, inv, data) -> new SoulHeartAmuletContainer(windowId, inv, SoulContainerMenu.readSlotId(data))));
    public static final DeferredHolder<MenuType<?>, MenuType<BladeOfVitalityContainer>> BLADE_OF_VITALITY_CONTAINER = CONTAINERS.register("blade_of_vitality", () -> IMenuTypeExtension.create((windowId, inv, data) -> new BladeOfVitalityContainer(windowId, inv, SoulContainerMenu.readSlotId(data))));
    public static final DeferredHolder<MenuType<?>, MenuType<VigorBowContainer>> VIGOR_BOW_CONTAINER = CONTAINERS.register("vigor_bow", () -> IMenuTypeExtension.create((windowId, inv, data) -> new VigorBowContainer(windowId, inv, SoulContainerMenu.readSlotId(data))));

    //Recipe Serializer
    public static final DeferredHolder<RecipeSerializer<?>, Serializer> HEART_AMULET_RECIPE_SERIALIZER = RECIPESERIALIZER.register("amulet_shapeless", Serializer::new);

    //Creative Mod Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BHC_TAB = TAB.register("bhc_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(RegistryHandler.HEART_AMULET.get())).displayItems((params, output) -> RegistryHandler.ITEMS.getEntries().forEach(item -> output.accept(item.get()))).title(Component.translatable("itemGroup.bhcTab")).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> STORED_HEARTS_COMPONENT = DATA_COMPONENT_TYPES.registerComponentType("hearts", builder -> builder.persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding());
}
