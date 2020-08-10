package net.minecraft.tags;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagCollection<T> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new Gson();
   private static final int JSON_EXTENSION_LENGTH = ".json".length();
   private final ITag<T> field_232970_d_ = Tag.func_241284_a_();
   private volatile BiMap<ResourceLocation, ITag<T>> tagMap = HashBiMap.create();
   private final Function<ResourceLocation, Optional<T>> resourceLocationToItem;
   private final String resourceLocationPrefix;
   private final String itemTypeName;

   public TagCollection(Function<ResourceLocation, Optional<T>> p_i231436_1_, String p_i231436_2_, String p_i231436_3_) {
      this.resourceLocationToItem = p_i231436_1_;
      this.resourceLocationPrefix = p_i231436_2_;
      this.itemTypeName = p_i231436_3_;
   }

   @Nullable
   public ITag<T> get(ResourceLocation resourceLocationIn) {
      return this.tagMap.get(resourceLocationIn);
   }

   public ITag<T> getOrCreate(ResourceLocation resourceLocationIn) {
      return this.tagMap.getOrDefault(resourceLocationIn, this.field_232970_d_);
   }

   @Nullable
   public ResourceLocation func_232973_a_(ITag<T> p_232973_1_) {
      return p_232973_1_ instanceof ITag.INamedTag ? ((ITag.INamedTag)p_232973_1_).func_230234_a_() : this.tagMap.inverse().get(p_232973_1_);
   }

   public ResourceLocation func_232975_b_(ITag<T> p_232975_1_) {
      ResourceLocation resourcelocation = this.func_232973_a_(p_232975_1_);
      if (resourcelocation == null) {
         throw new IllegalStateException("Unrecognized tag");
      } else {
         return resourcelocation;
      }
   }

   public Collection<ResourceLocation> getRegisteredTags() {
      return this.tagMap.keySet();
   }

   public Collection<ResourceLocation> getOwningTags(T itemIn) {
      List<ResourceLocation> list = Lists.newArrayList();

      for(Entry<ResourceLocation, ITag<T>> entry : this.tagMap.entrySet()) {
         if (entry.getValue().func_230235_a_(itemIn)) {
            list.add(entry.getKey());
         }
      }

      return list;
   }

   public CompletableFuture<Map<ResourceLocation, ITag.Builder>> reload(IResourceManager p_219781_1_, Executor p_219781_2_) {
      return CompletableFuture.supplyAsync(() -> {
         Map<ResourceLocation, ITag.Builder> map = Maps.newHashMap();

         for(ResourceLocation resourcelocation : p_219781_1_.getAllResourceLocations(this.resourceLocationPrefix, (p_199916_0_) -> {
            return p_199916_0_.endsWith(".json");
         })) {
            String s = resourcelocation.getPath();
            ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), s.substring(this.resourceLocationPrefix.length() + 1, s.length() - JSON_EXTENSION_LENGTH));

            try {
               for(IResource iresource : p_219781_1_.getAllResources(resourcelocation)) {
                  try (
                     InputStream inputstream = iresource.getInputStream();
                     Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
                  ) {
                     JsonObject jsonobject = JSONUtils.fromJson(GSON, reader, JsonObject.class);
                     if (jsonobject == null) {
                        LOGGER.error("Couldn't load {} tag list {} from {} in data pack {} as it is empty or null", this.itemTypeName, resourcelocation1, resourcelocation, iresource.getPackName());
                     } else {
                        map.computeIfAbsent(resourcelocation1, (p_232977_0_) -> {
                           return ITag.Builder.create();
                        }).func_232956_a_(jsonobject, iresource.getPackName());
                     }
                  } catch (RuntimeException | IOException ioexception) {
                     LOGGER.error("Couldn't read {} tag list {} from {} in data pack {}", this.itemTypeName, resourcelocation1, resourcelocation, iresource.getPackName(), ioexception);
                  } finally {
                     IOUtils.closeQuietly((Closeable)iresource);
                  }
               }
            } catch (IOException ioexception1) {
               LOGGER.error("Couldn't read {} tag list {} from {}", this.itemTypeName, resourcelocation1, resourcelocation, ioexception1);
            }
         }

         return map;
      }, p_219781_2_);
   }

   public void registerAll(Map<ResourceLocation, ITag.Builder> p_219779_1_) {
      Map<ResourceLocation, ITag<T>> map = Maps.newHashMap();
      Function<ResourceLocation, ITag<T>> function = map::get;
      Function<ResourceLocation, T> function1 = (p_232976_1_) -> {
         return this.resourceLocationToItem.apply(p_232976_1_).orElse((T)null);
      };

      while(!p_219779_1_.isEmpty()) {
         boolean flag = false;
         Iterator<Entry<ResourceLocation, ITag.Builder>> iterator = p_219779_1_.entrySet().iterator();

         while(iterator.hasNext()) {
            Entry<ResourceLocation, ITag.Builder> entry = iterator.next();
            Optional<ITag<T>> optional = entry.getValue().func_232959_a_(function, function1);
            if (optional.isPresent()) {
               map.put(entry.getKey(), optional.get());
               iterator.remove();
               flag = true;
            }
         }

         if (!flag) {
            break;
         }
      }

      p_219779_1_.forEach((p_223505_3_, p_223505_4_) -> {
         LOGGER.error("Couldn't load {} tag {} as it is missing following references: {}", this.itemTypeName, p_223505_3_, p_223505_4_.func_232963_b_(function, function1).map(Objects::toString).collect(Collectors.joining(",")));
      });
      this.toImmutable(map);
   }

   protected void toImmutable(Map<ResourceLocation, ITag<T>> p_223507_1_) {
      this.tagMap = ImmutableBiMap.copyOf(p_223507_1_);
   }

   public Map<ResourceLocation, ITag<T>> getTagMap() {
      return this.tagMap;
   }

   public Function<ResourceLocation, Optional<T>> getEntryLookup() {
      return this.resourceLocationToItem;
   }
}