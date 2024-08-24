package com.traverse.bhc.datagen;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.data.BHCItemTags;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.CompletableFuture;

public class BHCItemTagsProvider extends ItemTagsProvider {

    public BHCItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, BaubleyHeartCanisters.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BHCItemTags.HEARTS).add(RegistryHandler.RED_HEART.get(), RegistryHandler.YELLOW_HEART.get(), RegistryHandler.GREEN_HEART.get(), RegistryHandler.BLUE_HEART.get());
        tag(BHCItemTags.HEART_AMULETS).add(RegistryHandler.HEART_AMULET.get(), RegistryHandler.SOUL_HEART_AMULET.get());
        tag(BHCItemTags.WITHER_BONES).add(RegistryHandler.WITHER_BONE.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "heart_amulet"))).addTag(BHCItemTags.HEART_AMULETS);
    }
}
