package com.traverse.bhc.common.init;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.container.SoulHeartAmuletContainer;
import com.traverse.bhc.common.items.*;
import com.traverse.bhc.common.items.tools.ItemBladeOfVitality;
import com.traverse.bhc.common.recipes.HeartAmuletRecipe;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPESERIALIZER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BaubleyHeartCanisters.MODID);
    public static DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BaubleyHeartCanisters.MODID);

    //Items
    public static final DeferredHolder<Item, BaseHeartCanister> RED_CANISTER = ITEMS.register("red_heart_canister", () -> new BaseHeartCanister(HeartType.RED));
    public static final DeferredHolder<Item, BaseHeartCanister> YELLOW_CANISTER = ITEMS.register("yellow_heart_canister", () -> new BaseHeartCanister(HeartType.YELLOW));
    public static final DeferredHolder<Item, BaseHeartCanister> GREEN_CANISTER = ITEMS.register("green_heart_canister", () -> new BaseHeartCanister(HeartType.GREEN));
    public static final DeferredHolder<Item, BaseHeartCanister> BLUE_CANISTER = ITEMS.register("blue_heart_canister", () -> new BaseHeartCanister(HeartType.BLUE));
    public static final DeferredHolder<Item, BaseHeartCanister> SOUL_CANISTER = ITEMS.register("soul_heart_canister", () -> new BaseHeartCanister(HeartType.SOUL));

    public static final DeferredHolder<Item, BaseItem> RED_HEART_MELTED = ITEMS.register("red_heart_melted", () -> new BaseItem());
    public static final DeferredHolder<Item, BaseItem> YELLOW_HEART_MELTED = ITEMS.register("yellow_heart_melted",  () -> new BaseItem());
    public static final DeferredHolder<Item, BaseItem> GREEN_HEART_MELTED = ITEMS.register("green_heart_melted",  () -> new BaseItem());
    public static final DeferredHolder<Item, BaseItem> BLUE_HEART_MELTED = ITEMS.register("blue_heart_melted",  () -> new BaseItem());

    public static final DeferredHolder<Item, ItemHeartPatch> RED_HEART_PATCH = ITEMS.register("red_heart_patch", () -> new ItemHeartPatch(2, 5*20, 20));
    public static final DeferredHolder<Item, ItemHeartPatch> YELLOW_HEART_PATCH = ITEMS.register("yellow_heart_patch", () -> new ItemHeartPatch(6, 10*20, 25));
    public static final DeferredHolder<Item, ItemHeartPatch> GREEN_HEART_PATCH = ITEMS.register("green_heart_patch", () -> new ItemHeartPatch(10, 20*20, 30));
    public static final DeferredHolder<Item, ItemHeartPatch> BLUE_HEART_PATCH = ITEMS.register("blue_heart_patch", () -> new ItemHeartPatch(20, 30*20, 50));

    public static final DeferredHolder<Item, ItemHeart> RED_HEART = ITEMS.register("red_heart", () -> new ItemHeart(HeartType.RED));
    public static final DeferredHolder<Item, ItemHeart> YELLOW_HEART = ITEMS.register("yellow_heart", () -> new ItemHeart(HeartType.YELLOW));
    public static final DeferredHolder<Item, ItemHeart> GREEN_HEART = ITEMS.register("green_heart", () -> new ItemHeart(HeartType.GREEN));
    public static final DeferredHolder<Item, ItemHeart> BLUE_HEART = ITEMS.register("blue_heart", () -> new ItemHeart(HeartType.BLUE));
    public static final DeferredHolder<Item, BaseItem> CANISTER = ITEMS.register("canister", () -> new BaseItem());
    public static final DeferredHolder<Item, ItemBladeOfVitality> BLADE_OF_VITALITY = ITEMS.register("blade_of_vitality", ItemBladeOfVitality::new);

    public static final DeferredHolder<Item, BaseItem> WITHER_BONE = ITEMS.register("wither_bone", ()-> new BaseItem());
    public static final DeferredHolder<Item, ItemRelicApple> RELIC_APPLE = ITEMS.register("relic_apple", ItemRelicApple::new);
    public static final DeferredHolder<Item, ItemHeartAmulet> HEART_AMULET = ITEMS.register("heart_amulet", ItemHeartAmulet::new);
    public static final DeferredHolder<Item, ItemSoulHeartAmulet> SOUL_HEART_AMULET = ITEMS.register("soul_heart_amulet", ItemSoulHeartAmulet::new);
    public static final DeferredHolder<Item, BaseItem> SOUL_HEART_CRYSTAL = ITEMS.register("soul_heart_crystal", () -> new BaseItem());

    //Container
    public static final Supplier<MenuType<HeartAmuletContainer>> HEART_AMUlET_CONTAINER = CONTAINERS.register("heart_amulet_container", () -> IMenuTypeExtension.create((windowId, inv, data) -> new HeartAmuletContainer(windowId, inv, data.readItem())));
    public static final Supplier<MenuType<SoulHeartAmuletContainer>> SOUL_HEART_AMUlET_CONTAINER = CONTAINERS.register("soul_heart_amulet_container",  () -> IMenuTypeExtension.create((windowId, inv, data) -> new SoulHeartAmuletContainer(windowId, inv, data.readItem())));
    public static final Supplier<MenuType<BladeOfVitalityContainer>> BLADE_OF_VITALITY_CONTAINER = CONTAINERS.register("blade_of_vitality_container",  () -> IMenuTypeExtension.create((windowId, inv, data) -> new BladeOfVitalityContainer(windowId, inv, data.readItem())));

    //Recipe Serializer
     public static final Supplier<RecipeSerializer<HeartAmuletRecipe>> HEART_AMULET_RECIPE_SERIALIZER = RECIPESERIALIZER.register("amulet_shapeless",HeartAmuletRecipe.BHCSerializer::new);

    //Creative Mod Tab
    public static final Supplier<CreativeModeTab> BHC_TAB = TAB.register("bhc_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(RegistryHandler.HEART_AMULET.get()))
            .displayItems((params, output) -> RegistryHandler.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
            .title(Component.translatable("itemGroup.bhcTab"))
            .build());
}
