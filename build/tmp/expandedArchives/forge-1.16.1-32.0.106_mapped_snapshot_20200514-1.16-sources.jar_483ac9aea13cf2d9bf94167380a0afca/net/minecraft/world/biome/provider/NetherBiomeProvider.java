package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.MaxMinNoiseMixer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NetherBiomeProvider extends BiomeProvider {
   public static final MapCodec<NetherBiomeProvider> field_235262_e_ = RecordCodecBuilder.mapCodec((p_235279_0_) -> {
      return p_235279_0_.group(Codec.LONG.fieldOf("seed").forGetter((p_235286_0_) -> {
         return p_235286_0_.field_235270_m_;
      }), RecordCodecBuilder.<Pair<Biome.Attributes, Biome>>create((p_235282_0_) -> {
         return p_235282_0_.group(Biome.Attributes.field_235104_a_.fieldOf("parameters").forGetter(Pair::getFirst), Registry.BIOME.fieldOf("biome").forGetter(Pair::getSecond)).apply(p_235282_0_, Pair::of);
      }).listOf().fieldOf("biomes").forGetter((p_235284_0_) -> {
         return p_235284_0_.field_235268_k_;
      })).apply(p_235279_0_, NetherBiomeProvider::new);
   });
   public static final Codec<NetherBiomeProvider> field_235263_f_ = Codec.mapEither(NetherBiomeProvider.Preset.field_235287_a_, field_235262_e_).xmap((p_235277_0_) -> {
      return p_235277_0_.map((p_235278_0_) -> {
         return p_235278_0_.getFirst().func_235292_a_(p_235278_0_.getSecond());
      }, Function.identity());
   }, (p_235275_0_) -> {
      return p_235275_0_.field_235271_n_.<Either<Pair<Preset, Long>, NetherBiomeProvider>>map((p_235276_1_) -> {
         return Either.left(Pair.of(p_235276_1_, p_235275_0_.field_235270_m_));
      }).orElseGet(() -> {
         return Either.right(p_235275_0_);
      });
   }).codec();
   private final MaxMinNoiseMixer field_235264_g_;
   private final MaxMinNoiseMixer field_235265_h_;
   private final MaxMinNoiseMixer field_235266_i_;
   private final MaxMinNoiseMixer field_235267_j_;
   private final List<Pair<Biome.Attributes, Biome>> field_235268_k_;
   private final boolean field_235269_l_;
   private final long field_235270_m_;
   private final Optional<NetherBiomeProvider.Preset> field_235271_n_;

   private NetherBiomeProvider(long p_i231639_1_, List<Pair<Biome.Attributes, Biome>> p_i231639_3_) {
      this(p_i231639_1_, p_i231639_3_, Optional.empty());
   }

   public NetherBiomeProvider(long p_i231640_1_, List<Pair<Biome.Attributes, Biome>> p_i231640_3_, Optional<NetherBiomeProvider.Preset> p_i231640_4_) {
      super(p_i231640_3_.stream().map(Pair::getSecond).collect(Collectors.toList()));
      this.field_235270_m_ = p_i231640_1_;
      this.field_235271_n_ = p_i231640_4_;
      IntStream intstream = IntStream.rangeClosed(-7, -6);
      IntStream intstream1 = IntStream.rangeClosed(-7, -6);
      IntStream intstream2 = IntStream.rangeClosed(-7, -6);
      IntStream intstream3 = IntStream.rangeClosed(-7, -6);
      this.field_235264_g_ = new MaxMinNoiseMixer(new SharedSeedRandom(p_i231640_1_), intstream);
      this.field_235265_h_ = new MaxMinNoiseMixer(new SharedSeedRandom(p_i231640_1_ + 1L), intstream1);
      this.field_235266_i_ = new MaxMinNoiseMixer(new SharedSeedRandom(p_i231640_1_ + 2L), intstream2);
      this.field_235267_j_ = new MaxMinNoiseMixer(new SharedSeedRandom(p_i231640_1_ + 3L), intstream3);
      this.field_235268_k_ = p_i231640_3_;
      this.field_235269_l_ = false;
   }

   private static NetherBiomeProvider func_235285_d_(long p_235285_0_) {
      ImmutableList<Biome> immutablelist = ImmutableList.of(Biomes.field_235254_j_, Biomes.field_235252_ay_, Biomes.field_235253_az_, Biomes.field_235250_aA_, Biomes.field_235251_aB_);
      return new NetherBiomeProvider(p_235285_0_, immutablelist.stream().flatMap((p_235273_0_) -> {
         return p_235273_0_.func_235055_B_().map((p_235274_1_) -> {
            return Pair.of(p_235274_1_, p_235273_0_);
         });
      }).collect(ImmutableList.toImmutableList()), Optional.of(NetherBiomeProvider.Preset.field_235288_b_));
   }

   protected Codec<? extends BiomeProvider> func_230319_a_() {
      return field_235263_f_;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeProvider func_230320_a_(long p_230320_1_) {
      return new NetherBiomeProvider(p_230320_1_, this.field_235268_k_, this.field_235271_n_);
   }

   public Biome getNoiseBiome(int x, int y, int z) {
      int i = this.field_235269_l_ ? y : 0;
      Biome.Attributes biome$attributes = new Biome.Attributes((float)this.field_235264_g_.func_237211_a_((double)x, (double)i, (double)z), (float)this.field_235265_h_.func_237211_a_((double)x, (double)i, (double)z), (float)this.field_235266_i_.func_237211_a_((double)x, (double)i, (double)z), (float)this.field_235267_j_.func_237211_a_((double)x, (double)i, (double)z), 0.0F);
      return this.field_235268_k_.stream().min(Comparator.comparing((p_235272_1_) -> {
         return p_235272_1_.getFirst().func_235110_a_(biome$attributes);
      })).map(Pair::getSecond).orElse(Biomes.THE_VOID);
   }

   public boolean func_235280_b_(long p_235280_1_) {
      return this.field_235270_m_ == p_235280_1_ && Objects.equals(this.field_235271_n_, Optional.of(NetherBiomeProvider.Preset.field_235288_b_));
   }

   public static class Preset {
      private static final Map<ResourceLocation, NetherBiomeProvider.Preset> field_235289_c_ = Maps.newHashMap();
      public static final MapCodec<Pair<NetherBiomeProvider.Preset, Long>> field_235287_a_ = Codec.mapPair(ResourceLocation.field_240908_a_.flatXmap((p_235294_0_) -> {
         return Optional.ofNullable(field_235289_c_.get(p_235294_0_)).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown preset: " + p_235294_0_);
         });
      }, (p_235293_0_) -> {
         return DataResult.success(p_235293_0_.field_235290_d_);
      }).fieldOf("preset"), Codec.LONG.fieldOf("seed")).stable();
      public static final NetherBiomeProvider.Preset field_235288_b_ = new NetherBiomeProvider.Preset(new ResourceLocation("nether"), (p_235295_0_) -> {
         return NetherBiomeProvider.func_235285_d_(p_235295_0_);
      });
      private final ResourceLocation field_235290_d_;
      private final LongFunction<NetherBiomeProvider> field_235291_e_;

      public Preset(ResourceLocation p_i231641_1_, LongFunction<NetherBiomeProvider> p_i231641_2_) {
         this.field_235290_d_ = p_i231641_1_;
         this.field_235291_e_ = p_i231641_2_;
         field_235289_c_.put(p_i231641_1_, this);
      }

      public NetherBiomeProvider func_235292_a_(long p_235292_1_) {
         return this.field_235291_e_.apply(p_235292_1_);
      }
   }
}