package net.minecraft.util.datafix.codec;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class RangeCodec {
   private static Function<Integer, DataResult<Integer>> func_232992_b_(int p_232992_0_, int p_232992_1_) {
      return (p_232990_2_) -> {
         return p_232990_2_ >= p_232992_0_ && p_232990_2_ <= p_232992_1_ ? DataResult.success(p_232990_2_) : DataResult.error("Value " + p_232990_2_ + " outside of range [" + p_232992_0_ + ":" + p_232992_1_ + "]", p_232990_2_);
      };
   }

   public static Codec<Integer> func_232989_a_(int p_232989_0_, int p_232989_1_) {
      Function<Integer, DataResult<Integer>> function = func_232992_b_(p_232989_0_, p_232989_1_);
      return Codec.INT.flatXmap(function, function);
   }

   private static Function<Double, DataResult<Double>> func_232991_b_(double p_232991_0_, double p_232991_2_) {
      return (p_232988_4_) -> {
         return p_232988_4_ >= p_232991_0_ && p_232988_4_ <= p_232991_2_ ? DataResult.success(p_232988_4_) : DataResult.error("Value " + p_232988_4_ + " outside of range [" + p_232991_0_ + ":" + p_232991_2_ + "]", p_232988_4_);
      };
   }

   public static Codec<Double> func_232987_a_(double p_232987_0_, double p_232987_2_) {
      Function<Double, DataResult<Double>> function = func_232991_b_(p_232987_0_, p_232987_2_);
      return Codec.DOUBLE.flatXmap(function, function);
   }

   public static <T> MapCodec<Pair<RegistryKey<T>, T>> func_241293_a_(RegistryKey<Registry<T>> p_241293_0_, MapCodec<T> p_241293_1_) {
      return Codec.mapPair(ResourceLocation.field_240908_a_.xmap(RegistryKey.func_240902_a_(p_241293_0_), RegistryKey::func_240901_a_).fieldOf("name"), p_241293_1_);
   }

   private static <A> MapCodec<A> func_241290_a_(final MapCodec<A> p_241290_0_, final RangeCodec.ICodecModifier<A> p_241290_1_) {
      return new MapCodec<A>() {
         public <T> Stream<T> keys(DynamicOps<T> p_keys_1_) {
            return p_241290_0_.keys(p_keys_1_);
         }

         public <T> RecordBuilder<T> encode(A p_encode_1_, DynamicOps<T> p_encode_2_, RecordBuilder<T> p_encode_3_) {
            return p_241290_1_.func_241814_a(p_encode_2_, p_encode_1_, p_241290_0_.encode(p_encode_1_, p_encode_2_, p_encode_3_));
         }

         public <T> DataResult<A> decode(DynamicOps<T> p_decode_1_, MapLike<T> p_decode_2_) {
            return p_241290_1_.func_241812_a(p_decode_1_, p_decode_2_, p_241290_0_.decode(p_decode_1_, p_decode_2_));
         }

         public String toString() {
            return p_241290_0_ + "[mapResult " + p_241290_1_ + "]";
         }
      };
   }

   public static <A> MapCodec<A> func_241291_a_(MapCodec<A> p_241291_0_, final Consumer<String> p_241291_1_, final Supplier<? extends A> p_241291_2_) {
      return func_241290_a_(p_241291_0_, new RangeCodec.ICodecModifier<A>() {
         public <T> DataResult<A> func_241812_a(DynamicOps<T> p_241812_1_, MapLike<T> p_241812_2_, DataResult<A> p_241812_3_) {
            return DataResult.success(p_241812_3_.resultOrPartial(p_241291_1_).orElseGet(p_241291_2_));
         }

         public <T> RecordBuilder<T> func_241814_a(DynamicOps<T> p_241814_1_, A p_241814_2_, RecordBuilder<T> p_241814_3_) {
            return p_241814_3_;
         }

         public String toString() {
            return "WithDefault[" + p_241291_2_.get() + "]";
         }
      });
   }

   public static <A> MapCodec<A> func_241292_a_(MapCodec<A> p_241292_0_, final Supplier<A> p_241292_1_) {
      return func_241290_a_(p_241292_0_, new RangeCodec.ICodecModifier<A>() {
         public <T> DataResult<A> func_241812_a(DynamicOps<T> p_241812_1_, MapLike<T> p_241812_2_, DataResult<A> p_241812_3_) {
            return p_241812_3_.setPartial(p_241292_1_);
         }

         public <T> RecordBuilder<T> func_241814_a(DynamicOps<T> p_241814_1_, A p_241814_2_, RecordBuilder<T> p_241814_3_) {
            return p_241814_3_;
         }

         public String toString() {
            return "SetPartial[" + p_241292_1_ + "]";
         }
      });
   }

   interface ICodecModifier<A> {
      <T> DataResult<A> func_241812_a(DynamicOps<T> p_241812_1_, MapLike<T> p_241812_2_, DataResult<A> p_241812_3_);

      <T> RecordBuilder<T> func_241814_a(DynamicOps<T> p_241814_1_, A p_241814_2_, RecordBuilder<T> p_241814_3_);
   }
}