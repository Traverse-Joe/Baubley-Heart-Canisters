package net.minecraft.tags;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class NetworkTagManager implements IFutureReloadListener {
   protected NetworkTagCollection<Block> blocks = new NetworkTagCollection<>(Registry.BLOCK, "tags/blocks", "block");
   protected NetworkTagCollection<Item> items = new NetworkTagCollection<>(Registry.ITEM, "tags/items", "item");
   protected NetworkTagCollection<Fluid> fluids = new NetworkTagCollection<>(Registry.FLUID, "tags/fluids", "fluid");
   protected NetworkTagCollection<EntityType<?>> entityTypes = new NetworkTagCollection<>(Registry.ENTITY_TYPE, "tags/entity_types", "entity_type");

   public NetworkTagCollection<Block> getBlocks() {
      return this.blocks;
   }

   public NetworkTagCollection<Item> getItems() {
      return this.items;
   }

   public NetworkTagCollection<Fluid> getFluids() {
      return this.fluids;
   }

   public NetworkTagCollection<EntityType<?>> getEntityTypes() {
      return this.entityTypes;
   }

   public void write(PacketBuffer buffer) {
      this.blocks.write(buffer);
      this.items.write(buffer);
      this.fluids.write(buffer);
      this.entityTypes.write(buffer);
   }

   /*
    * Reads a list of Network Tag Collections from the packet, but supports missing/empty tags.
    * If the tag is missing, it will either return an empty tag when requested, or a tag with default values.
    */
   public static NetworkTagManager readSafe(PacketBuffer buffer) {
      return read(buffer, net.minecraftforge.common.util.ForgeNetworkTagManager.create());
   }

   public static NetworkTagManager read(PacketBuffer buffer) {
      return read(buffer, new NetworkTagManager());
   }

   private static NetworkTagManager read(PacketBuffer buffer, NetworkTagManager networktagmanager) {
      networktagmanager.getBlocks().read(buffer);
      networktagmanager.getItems().read(buffer);
      networktagmanager.getFluids().read(buffer);
      networktagmanager.getEntityTypes().read(buffer);
      return networktagmanager;
   }

   public CompletableFuture<Void> reload(IFutureReloadListener.IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
      CompletableFuture<Map<ResourceLocation, ITag.Builder>> completablefuture = this.blocks.reload(resourceManager, backgroundExecutor);
      CompletableFuture<Map<ResourceLocation, ITag.Builder>> completablefuture1 = this.items.reload(resourceManager, backgroundExecutor);
      CompletableFuture<Map<ResourceLocation, ITag.Builder>> completablefuture2 = this.fluids.reload(resourceManager, backgroundExecutor);
      CompletableFuture<Map<ResourceLocation, ITag.Builder>> completablefuture3 = this.entityTypes.reload(resourceManager, backgroundExecutor);
      return CompletableFuture.allOf(completablefuture, completablefuture1, completablefuture2, completablefuture3).thenCompose(stage::markCompleteAwaitingOthers).thenAcceptAsync((p_232979_5_) -> {
         this.blocks.registerAll(completablefuture.join());
         this.items.registerAll(completablefuture1.join());
         this.fluids.registerAll(completablefuture2.join());
         this.entityTypes.registerAll(completablefuture3.join());
         TagCollectionManager.func_232924_a_(this.blocks, this.items, this.fluids, this.entityTypes);
         Multimap<String, ResourceLocation> multimap = HashMultimap.create();
         multimap.putAll("blocks", BlockTags.func_232892_b_(this.blocks));
         multimap.putAll("items", ItemTags.func_232917_b_(this.items));
         multimap.putAll("fluids", FluidTags.func_232901_b_(this.fluids));
         multimap.putAll("entity_types", EntityTypeTags.func_232897_b_(this.entityTypes));
         if (!multimap.isEmpty()) {
            throw new IllegalStateException("Missing required tags: " + (String)multimap.entries().stream().map((p_232978_0_) -> {
               return (String)p_232978_0_.getKey() + ":" + p_232978_0_.getValue();
            }).sorted().collect(Collectors.joining(",")));
         }
      }, gameExecutor);
   }

   public void func_232980_f_() {
      BlockTags.setCollection(this.blocks);
      ItemTags.setCollection(this.items);
      FluidTags.setCollection(this.fluids);
      EntityTypeTags.setCollection(this.entityTypes);
      Blocks.func_235419_a_();
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.TagsUpdatedEvent(this));
   }
}