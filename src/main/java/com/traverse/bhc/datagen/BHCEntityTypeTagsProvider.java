package com.traverse.bhc.datagen;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.data.BHCEntityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BHCEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public BHCEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BaubleyHeartCanisters.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BHCEntityTags.NO_HEARTS);
    }
}
