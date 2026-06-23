package com.traverse.bhc.datagen;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BHCBlockTagsProvider extends BlockTagsProvider {

    public BHCBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BaubleyHeartCanisters.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                RegistryHandler.RED_BUDDING_CRYSTAL.get(),
                RegistryHandler.YELLOW_BUDDING_CRYSTAL.get(),
                RegistryHandler.GREEN_BUDDING_CRYSTAL.get(),
                RegistryHandler.BLUE_BUDDING_CRYSTAL.get()
        );
        tag(BlockTags.NEEDS_IRON_TOOL).add(
                RegistryHandler.RED_BUDDING_CRYSTAL.get(),
                RegistryHandler.YELLOW_BUDDING_CRYSTAL.get(),
                RegistryHandler.GREEN_BUDDING_CRYSTAL.get(),
                RegistryHandler.BLUE_BUDDING_CRYSTAL.get()
        );
    }
}
