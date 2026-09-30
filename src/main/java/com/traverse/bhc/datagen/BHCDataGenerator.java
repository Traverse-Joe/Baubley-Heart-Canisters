package com.traverse.bhc.datagen;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class BHCDataGenerator {

    public static final RegistrySetBuilder RELOADABLE_BUILDER = new RegistrySetBuilder()
            .add(RecipeProvider.asBootstrap(BHCRecipeProvider::new));

    @SubscribeEvent
    public static void onDataGeneration(GatherDataEvent.Client event) {
        event.createReloadableRegistryObjects(RELOADABLE_BUILDER);
        event.createProvider(BHCBlockTagsProvider::new);
        event.createProvider(BHCItemTagsProvider::new);
        event.createProvider(BHCModelProvider::new);
    }
}
