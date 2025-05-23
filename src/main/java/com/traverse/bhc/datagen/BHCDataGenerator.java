package com.traverse.bhc.datagen;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = EventBusSubscriber.Bus.MOD)
public class BHCDataGenerator {

    @SubscribeEvent
    public static void onDataGeneration(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        var blockTags = gen.addProvider(true, new BHCBlockTagsProvider(packOutput, lookupProvider));
        gen.addProvider(true, new BHCItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter()));
        gen.addProvider(true, new BHCRecipeProvider.Runner(packOutput, lookupProvider));
        gen.addProvider(true, new BHCModelProvider(packOutput));
    }
}
