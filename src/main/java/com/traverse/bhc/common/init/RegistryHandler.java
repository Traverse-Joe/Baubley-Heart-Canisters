package com.traverse.bhc.common.init;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.items.*;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister <Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BaubleyHeartCanisters.MODID);

    //Items
    public static final RegistryObject<Item> RED_HEART = ITEMS.register("red_heart", () -> new ItemHeart(HeartType.RED));
    public static final RegistryObject<Item> YELLOW_HEART = ITEMS.register("yellow_heart", () -> new ItemHeart(HeartType.YELLOW));
    public static final RegistryObject<Item> GREEN_HEART = ITEMS.register("green_heart", () -> new ItemHeart(HeartType.GREEN));
    public static final RegistryObject<Item> BLUE_HEART = ITEMS.register("blue_heart", () -> new ItemHeart(HeartType.BLUE));
    public static final RegistryObject<Item> CANISTER = ITEMS.register("canister", BaseItem::new);
    public static final RegistryObject<Item> RED_CANISTER = ITEMS.register("red_heart_canister", () -> new BaseHeartCanister(HeartType.RED));
    public static final RegistryObject<Item> YELLOW_CANISTER = ITEMS.register("yellow_heart_canister", () -> new BaseHeartCanister(HeartType.YELLOW));
    public static final RegistryObject<Item> GREEN_CANISTER = ITEMS.register("green_heart_canister", () -> new BaseHeartCanister(HeartType.GREEN));
    public static final RegistryObject<Item> BLUE_CANISTER = ITEMS.register("blue_heart_canister", () -> new BaseHeartCanister(HeartType.BLUE));
    public static final RegistryObject<Item> WITHER_BONE = ITEMS.register("wither_bone", BaseItem::new);
    public static final RegistryObject<Item> RELIC_APPLE = ITEMS.register("relic_apple", ItemRelicApple::new);
    public static final RegistryObject<Item> HEART_AMULET = ITEMS.register("heart_amulet", ItemHeartAmulet::new);

    //Container
    public static final RegistryObject<ContainerType<HeartAmuletContainer>> HEART_AMUlET_CONTAINER = CONTAINERS.register("heart_amulet_container",  () -> IForgeContainerType.create((windowId, inv, data) -> new HeartAmuletContainer(windowId, inv, data.readItemStack())));
}
