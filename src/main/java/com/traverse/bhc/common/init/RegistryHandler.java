package com.traverse.bhc.common.init;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.blocks.BuddingCrystalBlock;
import com.traverse.bhc.common.blocks.VitalicBudBlock;
import com.traverse.bhc.common.blocks.VitalicBudSize;
import com.traverse.bhc.common.blocks.entity.BuddingCrystalBlockEntity;
import com.traverse.bhc.common.config.ConfigHandler;
import com.traverse.bhc.common.container.BladeOfVitalityContainer;
import com.traverse.bhc.common.container.HeartAmuletContainer;
import com.traverse.bhc.common.container.SoulHeartAmuletContainer;
import com.traverse.bhc.common.container.VigorBowContainer;
import com.traverse.bhc.common.container.base.SoulContainerMenu;
import com.traverse.bhc.common.entity.VitalicOrb;
import com.traverse.bhc.common.items.BaseHeartCanister;
import com.traverse.bhc.common.items.BaseItem;
import com.traverse.bhc.common.items.ItemHeart;
import com.traverse.bhc.common.items.ItemHeartAmulet;
import com.traverse.bhc.common.items.ItemHeartPatch;
import com.traverse.bhc.common.items.ItemHeartPulseBelt;
import com.traverse.bhc.common.items.ItemRelicApple;
import com.traverse.bhc.common.items.ItemSoulHeartAmulet;
import com.traverse.bhc.common.items.ItemSoulHeartCrystal;
import com.traverse.bhc.common.items.tools.ItemBladeOfVitality;
import com.traverse.bhc.common.items.tools.ItemVigorBow;
import com.traverse.bhc.common.recipes.HeartAmuletRecipe;
import com.traverse.bhc.common.util.HeartType;
import com.traverse.bhc.common.util.VitalicCharge;
import com.traverse.bhc.common.util.VitalicSource;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;
import java.util.EnumMap;


public class RegistryHandler {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BaubleyHeartCanisters.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPESERIALIZER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BaubleyHeartCanisters.MODID);

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

    public static final DeferredItem<ItemHeartPatch> RED_HEART_PATCH = ITEMS.registerItem("red_heart_patch", (properties) -> new ItemHeartPatch(properties, ConfigHandler.general.redPatchHealAmount::get, ConfigHandler.general.redPatchCooldown::get, 20, Color.red.getRGB()));
    public static final DeferredItem<ItemHeartPatch> YELLOW_HEART_PATCH = ITEMS.registerItem("yellow_heart_patch", (properties) -> new ItemHeartPatch(properties, ConfigHandler.general.yellowPatchHealAmount::get, ConfigHandler.general.yellowPatchCooldown::get, 25, Color.yellow.getRGB()));
    public static final DeferredItem<ItemHeartPatch> GREEN_HEART_PATCH = ITEMS.registerItem("green_heart_patch", (properties) -> new ItemHeartPatch(properties, ConfigHandler.general.greenPatchHealAmount::get, ConfigHandler.general.greenPatchCooldown::get, 30, Color.green.getRGB()));
    public static final DeferredItem<ItemHeartPatch> BLUE_HEART_PATCH = ITEMS.registerItem("blue_heart_patch", (properties) -> new ItemHeartPatch(properties, ConfigHandler.general.bluePatchHealAmount::get, ConfigHandler.general.bluePatchCooldown::get, 50, Color.blue.getRGB()));

    public static final DeferredItem<ItemHeartPulseBelt> RED_HEARTPULSE_BELT = ITEMS.registerItem("red_heartpulse_belt", (properties) -> new ItemHeartPulseBelt(properties, HeartType.RED));
    public static final DeferredItem<ItemHeartPulseBelt> YELLOW_HEARTPULSE_BELT = ITEMS.registerItem("yellow_heartpulse_belt", (properties) -> new ItemHeartPulseBelt(properties, HeartType.YELLOW));
    public static final DeferredItem<ItemHeartPulseBelt> GREEN_HEARTPULSE_BELT = ITEMS.registerItem("green_heartpulse_belt", (properties) -> new ItemHeartPulseBelt(properties, HeartType.GREEN));
    public static final DeferredItem<ItemHeartPulseBelt> BLUE_HEARTPULSE_BELT = ITEMS.registerItem("blue_heartpulse_belt", (properties) -> new ItemHeartPulseBelt(properties, HeartType.BLUE));

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
    public static final DeferredItem<ItemSoulHeartCrystal> SOUL_HEART_CRYSTAL = ITEMS.registerItem("soul_heart_crystal", ItemSoulHeartCrystal::new);

    //Budding Crystals
    public static final DeferredBlock<BuddingCrystalBlock> RED_BUDDING_CRYSTAL = registerBuddingCrystal("red_budding_crystal", VitalicSource.RED, MapColor.COLOR_RED);
    public static final DeferredBlock<BuddingCrystalBlock> YELLOW_BUDDING_CRYSTAL = registerBuddingCrystal("yellow_budding_crystal", VitalicSource.YELLOW, MapColor.COLOR_YELLOW);
    public static final DeferredBlock<BuddingCrystalBlock> GREEN_BUDDING_CRYSTAL = registerBuddingCrystal("green_budding_crystal", VitalicSource.GREEN, MapColor.COLOR_GREEN);
    public static final DeferredBlock<BuddingCrystalBlock> BLUE_BUDDING_CRYSTAL = registerBuddingCrystal("blue_budding_crystal", VitalicSource.BLUE, MapColor.COLOR_BLUE);
    public static final DeferredItem<?> RED_BUDDING_CRYSTAL_ITEM = ITEMS.registerSimpleBlockItem(RED_BUDDING_CRYSTAL);
    public static final DeferredItem<?> YELLOW_BUDDING_CRYSTAL_ITEM = ITEMS.registerSimpleBlockItem(YELLOW_BUDDING_CRYSTAL);
    public static final DeferredItem<?> GREEN_BUDDING_CRYSTAL_ITEM = ITEMS.registerSimpleBlockItem(GREEN_BUDDING_CRYSTAL);
    public static final DeferredItem<?> BLUE_BUDDING_CRYSTAL_ITEM = ITEMS.registerSimpleBlockItem(BLUE_BUDDING_CRYSTAL);

    //Vitalic Buds (visual only — placed by BuddingCrystalBlockEntity, no BlockItems)
    private static final EnumMap<VitalicSource, EnumMap<VitalicBudSize, DeferredBlock<VitalicBudBlock>>> BUDS = new EnumMap<>(VitalicSource.class);
    static {
        for (VitalicSource source : VitalicSource.values()) {
            EnumMap<VitalicBudSize, DeferredBlock<VitalicBudBlock>> bySize = new EnumMap<>(VitalicBudSize.class);
            for (VitalicBudSize size : VitalicBudSize.values()) {
                bySize.put(size, registerBud(source, size));
            }
            BUDS.put(source, bySize);
        }
    }

    public static DeferredBlock<VitalicBudBlock> getBud(VitalicSource source, VitalicBudSize size) {
        return BUDS.get(source).get(size);
    }

    private static DeferredBlock<BuddingCrystalBlock> registerBuddingCrystal(String name, VitalicSource source, MapColor color) {
        return BLOCKS.registerBlock(name,
                props -> new BuddingCrystalBlock(source, props),
                () -> BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .strength(1.5F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.AMETHYST));
    }

    private static DeferredBlock<VitalicBudBlock> registerBud(VitalicSource source, VitalicBudSize size) {
        String name = source.getSerializedName() + "_" + size.getSerializedName() + "_vitalic_bud";
        return BLOCKS.registerBlock(name,
                props -> new VitalicBudBlock(source, size, props),
                () -> BlockBehaviour.Properties.of()
                        .forceSolidOn()
                        .noOcclusion()
                        .noLootTable()
                        .strength(1.5F)
                        .sound(SoundType.AMETHYST_CLUSTER)
                        .lightLevel(state -> size.lightLevel)
                        .pushReaction(PushReaction.DESTROY));
    }

    //Container
    public static final DeferredHolder<MenuType<?>, MenuType<HeartAmuletContainer>> HEART_AMUlET_CONTAINER = CONTAINERS.register("heart_amulet", () -> IMenuTypeExtension.create((windowId, inv, data) -> new HeartAmuletContainer(windowId, inv, SoulContainerMenu.readSlotId(data))));
    public static final DeferredHolder<MenuType<?>, MenuType<SoulHeartAmuletContainer>> SOUL_HEART_AMUlET_CONTAINER = CONTAINERS.register("soul_heart_amulet", () -> IMenuTypeExtension.create((windowId, inv, data) -> new SoulHeartAmuletContainer(windowId, inv, SoulContainerMenu.readSlotId(data))));
    public static final DeferredHolder<MenuType<?>, MenuType<BladeOfVitalityContainer>> BLADE_OF_VITALITY_CONTAINER = CONTAINERS.register("blade_of_vitality", () -> IMenuTypeExtension.create((windowId, inv, data) -> new BladeOfVitalityContainer(windowId, inv, SoulContainerMenu.readSlotId(data))));
    public static final DeferredHolder<MenuType<?>, MenuType<VigorBowContainer>> VIGOR_BOW_CONTAINER = CONTAINERS.register("vigor_bow", () -> IMenuTypeExtension.create((windowId, inv, data) -> new VigorBowContainer(windowId, inv, SoulContainerMenu.readSlotId(data))));

    //Recipe Serializer
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HeartAmuletRecipe>> HEART_AMULET_RECIPE_SERIALIZER = RECIPESERIALIZER.register("amulet_shapeless", () -> HeartAmuletRecipe.SERIALIZER);

    //Creative Mod Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BHC_TAB = TAB.register("bhc_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(RegistryHandler.HEART_AMULET.get())).displayItems((params, output) -> RegistryHandler.ITEMS.getEntries().forEach(item -> output.accept(item.get()))).title(Component.translatable("itemGroup.bhcTab")).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> STORED_HEARTS_COMPONENT = DATA_COMPONENT_TYPES.registerComponentType("hearts", builder -> builder.persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<VitalicCharge>> VITALIC_CHARGE_COMPONENT = DATA_COMPONENT_TYPES.registerComponentType("vitalic_charge", builder -> builder.persistent(VitalicCharge.CODEC).networkSynchronized(VitalicCharge.STREAM_CODEC).cacheEncoding());

    //Entities
    public static final DeferredHolder<EntityType<?>, EntityType<VitalicOrb>> VITALIC_ORB = ENTITY_TYPES.register("vitalic_orb",
            () -> EntityType.Builder.<VitalicOrb>of(VitalicOrb::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(8)
                    .updateInterval(2)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, BaubleyHeartCanisters.id("vitalic_orb"))));

    //Block Entities
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BuddingCrystalBlockEntity>> BUDDING_CRYSTAL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("budding_crystal",
            () -> new BlockEntityType<>(BuddingCrystalBlockEntity::new,
                    RED_BUDDING_CRYSTAL.get(),
                    YELLOW_BUDDING_CRYSTAL.get(),
                    GREEN_BUDDING_CRYSTAL.get(),
                    BLUE_BUDDING_CRYSTAL.get()
            ));
}
