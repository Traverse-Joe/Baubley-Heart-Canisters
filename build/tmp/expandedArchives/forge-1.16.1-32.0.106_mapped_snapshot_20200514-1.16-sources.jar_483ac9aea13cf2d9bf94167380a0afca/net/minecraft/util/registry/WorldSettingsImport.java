package net.minecraft.util.registry;

import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.IDynamicRegistries;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DelegatingDynamicOps;
import net.minecraft.util.datafix.codec.RangeCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldSettingsImport<T> extends DelegatingDynamicOps<T> {
   private static final Logger field_240870_b_ = LogManager.getLogger();
   private final IResourceManager field_240871_c_;
   private final IDynamicRegistries field_240872_d_;
   private final Map<RegistryKey<? extends Registry<?>>, WorldSettingsImport.ResultMap<?>> field_240873_e_ = Maps.newIdentityHashMap();

   public static <T> WorldSettingsImport<T> func_240876_a_(DynamicOps<T> p_240876_0_, IResourceManager p_240876_1_, IDynamicRegistries p_240876_2_) {
      return new WorldSettingsImport<>(p_240876_0_, p_240876_1_, p_240876_2_);
   }

   private WorldSettingsImport(DynamicOps<T> p_i232589_1_, IResourceManager p_i232589_2_, IDynamicRegistries p_i232589_3_) {
      super(p_i232589_1_);
      this.field_240871_c_ = p_i232589_2_;
      this.field_240872_d_ = p_i232589_3_;
   }

   protected <E> DataResult<Pair<Supplier<E>, T>> func_241802_a_(T p_241802_1_, RegistryKey<Registry<E>> p_241802_2_, MapCodec<E> p_241802_3_) {
      Optional<MutableRegistry<E>> optional = this.field_240872_d_.func_230521_a_(p_241802_2_);
      if (!optional.isPresent()) {
         return DataResult.error("Unknown registry: " + p_241802_2_);
      } else {
         MutableRegistry<E> mutableregistry = optional.get();
         DataResult<Pair<ResourceLocation, T>> dataresult = ResourceLocation.field_240908_a_.decode(this.field_240857_a_, p_241802_1_);
         if (!dataresult.result().isPresent()) {
            return RangeCodec.func_241293_a_(p_241802_2_, p_241802_3_).codec().decode(this.field_240857_a_, p_241802_1_).map((p_240874_1_) -> {
               return p_240874_1_.mapFirst((p_240891_1_) -> {
                  mutableregistry.register(p_240891_1_.getFirst(), p_240891_1_.getSecond());
                  mutableregistry.func_239662_d_(p_240891_1_.getFirst());
                  return p_240891_1_::getSecond;
               });
            });
         } else {
            Pair<ResourceLocation, T> pair = dataresult.result().get();
            ResourceLocation resourcelocation = pair.getFirst();
            return this.func_241805_a_(p_241802_2_, mutableregistry, p_241802_3_, resourcelocation).map((p_240875_1_) -> {
               return Pair.of(p_240875_1_, pair.getSecond());
            });
         }
      }
   }

   public <E> DataResult<SimpleRegistry<E>> func_241797_a_(SimpleRegistry<E> p_241797_1_, RegistryKey<Registry<E>> p_241797_2_, MapCodec<E> p_241797_3_) {
      ResourceLocation resourcelocation = p_241797_2_.func_240901_a_();
      Collection<ResourceLocation> collection = this.field_240871_c_.func_230231_a_(resourcelocation, (p_240883_0_) -> {
         return p_240883_0_.endsWith(".json");
      });
      DataResult<SimpleRegistry<E>> dataresult = DataResult.success(p_241797_1_, Lifecycle.stable());

      for(ResourceLocation resourcelocation1 : collection) {
         String s = resourcelocation1.getPath();
         if (!s.endsWith(".json")) {
            field_240870_b_.warn("Skipping resource {} since it is not a json file", (Object)resourcelocation1);
         } else if (!s.startsWith(resourcelocation.getPath() + "/")) {
            field_240870_b_.warn("Skipping resource {} since it does not have a registry name prefix", (Object)resourcelocation1);
         } else {
            String s1 = s.substring(0, s.length() - ".json".length()).substring(resourcelocation.getPath().length() + 1);
            int i = s1.indexOf(47);
            if (i < 0) {
               field_240870_b_.warn("Skipping resource {} since it does not have a namespace", (Object)resourcelocation1);
            } else {
               String s2 = s1.substring(0, i);
               String s3 = s1.substring(i + 1);
               ResourceLocation resourcelocation2 = new ResourceLocation(s2, s3);
               dataresult = dataresult.flatMap((p_240885_4_) -> {
                  return this.func_241805_a_(p_241797_2_, p_240885_4_, p_241797_3_, resourcelocation2).map((p_240877_1_) -> {
                     return p_240885_4_;
                  });
               });
            }
         }
      }

      return dataresult.setPartial(p_241797_1_);
   }

   private <E> DataResult<Supplier<E>> func_241805_a_(RegistryKey<Registry<E>> p_241805_1_, MutableRegistry<E> p_241805_2_, MapCodec<E> p_241805_3_, ResourceLocation p_241805_4_) {
      RegistryKey<E> registrykey = RegistryKey.func_240903_a_(p_241805_1_, p_241805_4_);
      E e = p_241805_2_.func_230516_a_(registrykey);
      if (e != null) {
         return DataResult.success(() -> {
            return e;
         }, Lifecycle.stable());
      } else {
         WorldSettingsImport.ResultMap<E> resultmap = this.func_240884_a_(p_241805_1_);
         DataResult<Supplier<E>> dataresult = resultmap.field_240893_a_.get(registrykey);
         if (dataresult != null) {
            return dataresult;
         } else {
            Supplier<E> supplier = Suppliers.memoize(() -> {
               E e1 = p_241805_2_.func_230516_a_(registrykey);
               if (e1 == null) {
                  throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + registrykey);
               } else {
                  return e1;
               }
            });
            resultmap.field_240893_a_.put(registrykey, DataResult.success(supplier));
            DataResult<E> dataresult1 = this.func_241806_a_(p_241805_1_, registrykey, p_241805_3_);
            dataresult1.result().ifPresent((p_240880_2_) -> {
               p_241805_2_.register(registrykey, p_240880_2_);
            });
            DataResult<Supplier<E>> dataresult2 = dataresult1.map((p_240881_0_) -> {
               return () -> {
                  return p_240881_0_;
               };
            });
            resultmap.field_240893_a_.put(registrykey, dataresult2);
            return dataresult2;
         }
      }
   }

   private <E> DataResult<E> func_241806_a_(RegistryKey<Registry<E>> p_241806_1_, RegistryKey<E> p_241806_2_, MapCodec<E> p_241806_3_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_241806_1_.func_240901_a_().getNamespace(), p_241806_1_.func_240901_a_().getPath() + "/" + p_241806_2_.func_240901_a_().getNamespace() + "/" + p_241806_2_.func_240901_a_().getPath() + ".json");

      try (
         IResource iresource = this.field_240871_c_.getResource(resourcelocation);
         Reader reader = new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8);
      ) {
         JsonParser jsonparser = new JsonParser();
         JsonElement jsonelement = jsonparser.parse(reader);
         return p_241806_3_.codec().parse(new WorldSettingsImport<>(JsonOps.INSTANCE, this.field_240871_c_, this.field_240872_d_), jsonelement);
      } catch (JsonIOException | JsonSyntaxException | IOException ioexception) {
         return DataResult.error("Failed to parse file: " + ioexception.getMessage());
      }
   }

   private <E> WorldSettingsImport.ResultMap<E> func_240884_a_(RegistryKey<Registry<E>> p_240884_1_) {
      return (WorldSettingsImport.ResultMap<E>)this.field_240873_e_.computeIfAbsent(p_240884_1_, (p_240889_0_) -> {
         return new WorldSettingsImport.ResultMap();
      });
   }

   static final class ResultMap<E> {
      private final Map<RegistryKey<E>, DataResult<Supplier<E>>> field_240893_a_ = Maps.newIdentityHashMap();

      private ResultMap() {
      }
   }
}