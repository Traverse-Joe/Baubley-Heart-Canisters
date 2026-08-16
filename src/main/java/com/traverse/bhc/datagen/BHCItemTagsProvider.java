package com.traverse.bhc.datagen;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.data.BHCItemTags;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import top.theillusivec4.curios.api.CuriosResources;

import java.util.concurrent.CompletableFuture;

public class BHCItemTagsProvider extends ItemTagsProvider {

    public BHCItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BaubleyHeartCanisters.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BHCItemTags.HEARTS).add(RegistryHandler.RED_HEART.getKey(), RegistryHandler.YELLOW_HEART.getKey(), RegistryHandler.GREEN_HEART.getKey(), RegistryHandler.BLUE_HEART.getKey());
        tag(BHCItemTags.HEART_AMULETS).add(RegistryHandler.HEART_AMULET.getKey(), RegistryHandler.SOUL_HEART_AMULET.getKey());
        tag(BHCItemTags.WITHER_BONES).add(RegistryHandler.WITHER_BONE.getKey());
        tag(ItemTags.SWORDS).add(RegistryHandler.BLADE_OF_VITALITY.getKey());
        tag(ItemTags.BOW_ENCHANTABLE).add(RegistryHandler.VIGOR_BOW.getKey());

        // Create heart_belts tag and add belt items
        tag(BHCItemTags.HEART_BELTS).add(
                RegistryHandler.RED_HEARTPULSE_BELT.getKey(),
                RegistryHandler.YELLOW_HEARTPULSE_BELT.getKey(),
                RegistryHandler.GREEN_HEARTPULSE_BELT.getKey(),
                RegistryHandler.BLUE_HEARTPULSE_BELT.getKey()
        );

        // Add items directly to Curios tags
        TagKey<Item> curiosHeartAmuletTag = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "heart_amulet"));
        TagKey<Item> curiosBeltTag = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "belt"));
        
        tag(curiosHeartAmuletTag).addTag(BHCItemTags.HEART_AMULETS);
        tag(curiosBeltTag).add(
                RegistryHandler.RED_HEARTPULSE_BELT.getKey(),
                RegistryHandler.YELLOW_HEARTPULSE_BELT.getKey(),
                RegistryHandler.GREEN_HEARTPULSE_BELT.getKey(),
                RegistryHandler.BLUE_HEARTPULSE_BELT.getKey()
        );
    }
}
