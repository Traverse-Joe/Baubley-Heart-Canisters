package com.traverse.bhc.common.init;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.container.SoulHeartAmuletContainer;
import com.traverse.bhc.common.items.*;
import com.traverse.bhc.common.items.tools.ItemBladeOfVitality;
import com.traverse.bhc.common.recipes.HeartAmuletRecipe;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryHandler {

    public static final DeferredRegister <Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPESERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BaubleyHeartCanisters.MODID);

    //Items
    public static final RegistryObject<Item> RED_CANISTER = ITEMS.register("red_heart_canister", () -> new BaseHeartCanister(HeartType.RED));
    public static final RegistryObject<Item> YELLOW_CANISTER = ITEMS.register("yellow_heart_canister", () -> new BaseHeartCanister(HeartType.YELLOW));
    public static final RegistryObject<Item> GREEN_CANISTER = ITEMS.register("green_heart_canister", () -> new BaseHeartCanister(HeartType.GREEN));
    public static final RegistryObject<Item> BLUE_CANISTER = ITEMS.register("blue_heart_canister", () -> new BaseHeartCanister(HeartType.BLUE));
    public static final RegistryObject<Item> SOUL_CANISTER = ITEMS.register("soul_heart_canister", () -> new BaseHeartCanister(HeartType.SOUL));

    public static final RegistryObject<Item> RED_HEART = ITEMS.register("red_heart", () -> new ItemHeart(HeartType.RED));
    public static final RegistryObject<Item> YELLOW_HEART = ITEMS.register("yellow_heart", () -> new ItemHeart(HeartType.YELLOW));
    public static final RegistryObject<Item> GREEN_HEART = ITEMS.register("green_heart", () -> new ItemHeart(HeartType.GREEN));
    public static final RegistryObject<Item> BLUE_HEART = ITEMS.register("blue_heart", () -> new ItemHeart(HeartType.BLUE));
    public static final RegistryObject<Item> CANISTER = ITEMS.register("canister", BaseItem::new);
    public static final RegistryObject<Item> BLADE_OF_VITALITIY = ITEMS.register("blade_of_vitality", ItemBladeOfVitality::new);

    public static final RegistryObject<Item> WITHER_BONE = ITEMS.register("wither_bone", BaseItem::new);
    public static final RegistryObject<Item> RELIC_APPLE = ITEMS.register("relic_apple", ItemRelicApple::new);
    public static final RegistryObject<Item> HEART_AMULET = ITEMS.register("heart_amulet", ItemHeartAmulet::new);
    public static final RegistryObject<Item> SOUL_HEART_AMULET = ITEMS.register("soul_heart_amulet", ItemSoulHeartAmulet::new);
    public static final RegistryObject<Item> SOUL_HEART_CRYSTAL = ITEMS.register("soul_heart_crystal", BaseItem::new);

    public static final RegistryObject<Item> RED_HEART_MELTED = ITEMS.register("red_heart_melted", BaseItem::new);
    public static final RegistryObject<Item> YELLOW_HEART_MELTED = ITEMS.register("yellow_heart_melted", BaseItem::new);
    public static final RegistryObject<Item> GREEN_HEART_MELTED = ITEMS.register("green_heart_melted", BaseItem::new);
    public static final RegistryObject<Item> BLUE_HEART_MELTED = ITEMS.register("blue_heart_melted", BaseItem::new);
    //Container
    public static final RegistryObject<MenuType<HeartAmuletContainer>> HEART_AMUlET_CONTAINER = CONTAINERS.register("heart_amulet_container",  () -> IForgeMenuType.create((windowId, inv, data) -> new HeartAmuletContainer(windowId, inv, data.readItem())));
    public static final RegistryObject<MenuType<SoulHeartAmuletContainer>> SOUL_HEART_AMUlET_CONTAINER = CONTAINERS.register("soul_heart_amulet_container",  () -> IForgeMenuType.create((windowId, inv, data) -> new SoulHeartAmuletContainer(windowId, inv, data.readItem())));
    public static final RegistryObject<MenuType<BladeOfVitalityContainer>> BLADE_OF_VITALITY_CONTAINER = CONTAINERS.register("blade_of_vitality_container",  () -> IForgeMenuType.create((windowId, inv, data) -> new BladeOfVitalityContainer(windowId, inv, data.readItem())));

    //Recipe Serializer
    public static final RegistryObject<RecipeSerializer<HeartAmuletRecipe>> HEART_AMULET_RECIPE_SERIALIZER = RECIPESERIALIZER.register(HeartAmuletRecipe.BHCSerializer.NAME.getPath(),HeartAmuletRecipe.BHCSerializer::new);
}
