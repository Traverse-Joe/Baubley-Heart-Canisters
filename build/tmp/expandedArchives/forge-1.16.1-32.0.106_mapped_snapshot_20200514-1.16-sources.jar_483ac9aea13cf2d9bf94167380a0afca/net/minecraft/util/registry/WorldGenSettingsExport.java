package net.minecraft.util.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.server.IDynamicRegistries;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DelegatingDynamicOps;
import net.minecraft.util.datafix.codec.RangeCodec;

public class WorldGenSettingsExport<T> extends DelegatingDynamicOps<T> {
   private final IDynamicRegistries field_240895_b_;

   public static <T> WorldGenSettingsExport<T> func_240896_a_(DynamicOps<T> p_240896_0_, IDynamicRegistries p_240896_1_) {
      return new WorldGenSettingsExport<>(p_240896_0_, p_240896_1_);
   }

   private WorldGenSettingsExport(DynamicOps<T> p_i232591_1_, IDynamicRegistries p_i232591_2_) {
      super(p_i232591_1_);
      this.field_240895_b_ = p_i232591_2_;
   }

   protected <E> DataResult<T> func_241811_a_(E p_241811_1_, T p_241811_2_, RegistryKey<Registry<E>> p_241811_3_, MapCodec<E> p_241811_4_) {
      Optional<MutableRegistry<E>> optional = this.field_240895_b_.func_230521_a_(p_241811_3_);
      if (optional.isPresent()) {
         MutableRegistry<E> mutableregistry = optional.get();
         Optional<RegistryKey<E>> optional1 = mutableregistry.func_230519_c_(p_241811_1_);
         if (optional1.isPresent()) {
            RegistryKey<E> registrykey = optional1.get();
            if (mutableregistry.func_239660_c_(registrykey)) {
               return RangeCodec.func_241293_a_(p_241811_3_, p_241811_4_).codec().encode(Pair.of(registrykey, p_241811_1_), this.field_240857_a_, p_241811_2_);
            }

            return ResourceLocation.field_240908_a_.encode(registrykey.func_240901_a_(), this.field_240857_a_, p_241811_2_);
         }
      }

      return p_241811_4_.codec().encode(p_241811_1_, this.field_240857_a_, p_241811_2_);
   }
}