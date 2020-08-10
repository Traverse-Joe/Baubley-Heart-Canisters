package net.minecraft.util.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;

public final class RegistryKeyCodec<E> implements Codec<Supplier<E>> {
   private final RegistryKey<Registry<E>> field_240864_a_;
   private final MapCodec<E> field_240865_b_;

   public static <E> RegistryKeyCodec<E> func_241794_a_(RegistryKey<Registry<E>> p_241794_0_, MapCodec<E> p_241794_1_) {
      return new RegistryKeyCodec<>(p_241794_0_, p_241794_1_);
   }

   private RegistryKeyCodec(RegistryKey<Registry<E>> p_i241275_1_, MapCodec<E> p_i241275_2_) {
      this.field_240864_a_ = p_i241275_1_;
      this.field_240865_b_ = p_i241275_2_;
   }

   public <T> DataResult<T> encode(Supplier<E> p_encode_1_, DynamicOps<T> p_encode_2_, T p_encode_3_) {
      return p_encode_2_ instanceof WorldGenSettingsExport ? ((WorldGenSettingsExport)p_encode_2_).func_241811_a_(p_encode_1_.get(), p_encode_3_, this.field_240864_a_, this.field_240865_b_) : this.field_240865_b_.codec().encode(p_encode_1_.get(), p_encode_2_, p_encode_3_);
   }

   public <T> DataResult<Pair<Supplier<E>, T>> decode(DynamicOps<T> p_decode_1_, T p_decode_2_) {
      return p_decode_1_ instanceof WorldSettingsImport ? ((WorldSettingsImport)p_decode_1_).func_241802_a_(p_decode_2_, this.field_240864_a_, this.field_240865_b_) : this.field_240865_b_.codec().decode(p_decode_1_, p_decode_2_).map((p_240866_0_) -> {
         return p_240866_0_.mapFirst((p_240867_0_) -> {
            return () -> {
               return p_240867_0_;
            };
         });
      });
   }

   public String toString() {
      return "RegistryFileCodec[" + this.field_240864_a_ + " " + this.field_240865_b_ + "]";
   }
}