package com.traverse.bhc.datagen;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BHCBlockTagsProvider extends BlockTagsProvider {

    public BHCBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BaubleyHeartCanisters.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}
