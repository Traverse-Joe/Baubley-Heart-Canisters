package net.minecraft.world.gen;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.codec.RangeCodec;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public final class DimensionSettings {
   public static final Codec<DimensionSettings> field_236097_a_ = RecordCodecBuilder.create((p_236112_0_) -> {
      return p_236112_0_.group(DimensionStructuresSettings.field_236190_a_.fieldOf("structures").forGetter(DimensionSettings::func_236108_a_), NoiseSettings.field_236156_a_.fieldOf("noise").forGetter(DimensionSettings::func_236113_b_), BlockState.field_235877_b_.fieldOf("default_block").forGetter(DimensionSettings::func_236115_c_), BlockState.field_235877_b_.fieldOf("default_fluid").forGetter(DimensionSettings::func_236116_d_), RangeCodec.func_232989_a_(-20, 276).fieldOf("bedrock_roof_position").forGetter(DimensionSettings::func_236117_e_), RangeCodec.func_232989_a_(-20, 276).fieldOf("bedrock_floor_position").forGetter(DimensionSettings::func_236118_f_), RangeCodec.func_232989_a_(0, 255).fieldOf("sea_level").forGetter(DimensionSettings::func_236119_g_), Codec.BOOL.fieldOf("disable_mob_generation").forGetter(DimensionSettings::func_236120_h_)).apply(p_236112_0_, DimensionSettings::new);
   });
   public static final Codec<DimensionSettings> field_236098_b_ = Codec.either(DimensionSettings.Preset.field_236121_a_, field_236097_a_).xmap((p_236111_0_) -> {
      return p_236111_0_.map(DimensionSettings.Preset::func_236137_b_, Function.identity());
   }, (p_236110_0_) -> {
      return p_236110_0_.field_236107_k_.<Either<Preset, DimensionSettings>>map(Either::left).orElseGet(() -> {
         return Either.right(p_236110_0_);
      });
   });
   private final DimensionStructuresSettings field_236099_c_;
   private final NoiseSettings field_236100_d_;
   private final BlockState field_236101_e_;
   private final BlockState field_236102_f_;
   private final int field_236103_g_;
   private final int field_236104_h_;
   private final int field_236105_i_;
   private final boolean field_236106_j_;
   private final Optional<DimensionSettings.Preset> field_236107_k_;

   private DimensionSettings(DimensionStructuresSettings p_i231905_1_, NoiseSettings p_i231905_2_, BlockState p_i231905_3_, BlockState p_i231905_4_, int p_i231905_5_, int p_i231905_6_, int p_i231905_7_, boolean p_i231905_8_) {
      this(p_i231905_1_, p_i231905_2_, p_i231905_3_, p_i231905_4_, p_i231905_5_, p_i231905_6_, p_i231905_7_, p_i231905_8_, Optional.empty());
   }

   private DimensionSettings(DimensionStructuresSettings p_i231906_1_, NoiseSettings p_i231906_2_, BlockState p_i231906_3_, BlockState p_i231906_4_, int p_i231906_5_, int p_i231906_6_, int p_i231906_7_, boolean p_i231906_8_, Optional<DimensionSettings.Preset> p_i231906_9_) {
      this.field_236099_c_ = p_i231906_1_;
      this.field_236100_d_ = p_i231906_2_;
      this.field_236101_e_ = p_i231906_3_;
      this.field_236102_f_ = p_i231906_4_;
      this.field_236103_g_ = p_i231906_5_;
      this.field_236104_h_ = p_i231906_6_;
      this.field_236105_i_ = p_i231906_7_;
      this.field_236106_j_ = p_i231906_8_;
      this.field_236107_k_ = p_i231906_9_;
   }

   public DimensionStructuresSettings func_236108_a_() {
      return this.field_236099_c_;
   }

   public NoiseSettings func_236113_b_() {
      return this.field_236100_d_;
   }

   public BlockState func_236115_c_() {
      return this.field_236101_e_;
   }

   public BlockState func_236116_d_() {
      return this.field_236102_f_;
   }

   public int func_236117_e_() {
      return this.field_236103_g_;
   }

   public int func_236118_f_() {
      return this.field_236104_h_;
   }

   public int func_236119_g_() {
      return this.field_236105_i_;
   }

   @Deprecated
   protected boolean func_236120_h_() {
      return this.field_236106_j_;
   }

   public boolean func_236109_a_(DimensionSettings.Preset p_236109_1_) {
      return Objects.equals(this.field_236107_k_, Optional.of(p_236109_1_));
   }

   public static class Preset {
      private static final Map<ResourceLocation, DimensionSettings.Preset> field_236128_h_ = Maps.newHashMap();
      public static final Codec<DimensionSettings.Preset> field_236121_a_ = ResourceLocation.field_240908_a_.flatXmap((p_236136_0_) -> {
         return Optional.ofNullable(field_236128_h_.get(p_236136_0_)).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown preset: " + p_236136_0_);
         });
      }, (p_236144_0_) -> {
         return DataResult.success(p_236144_0_.field_236130_j_);
      }).stable();
      public static final DimensionSettings.Preset field_236122_b_ = new DimensionSettings.Preset("overworld", (p_236143_0_) -> {
         return func_236135_a_(new DimensionStructuresSettings(true), false, p_236143_0_);
      });
      public static final DimensionSettings.Preset field_236123_c_ = new DimensionSettings.Preset("amplified", (p_236142_0_) -> {
         return func_236135_a_(new DimensionStructuresSettings(true), true, p_236142_0_);
      });
      public static final DimensionSettings.Preset field_236124_d_ = new DimensionSettings.Preset("nether", (p_236141_0_) -> {
         return func_236133_a_(new DimensionStructuresSettings(false), Blocks.NETHERRACK.getDefaultState(), Blocks.LAVA.getDefaultState(), p_236141_0_);
      });
      public static final DimensionSettings.Preset field_236125_e_ = new DimensionSettings.Preset("end", (p_236140_0_) -> {
         return func_236134_a_(new DimensionStructuresSettings(false), Blocks.END_STONE.getDefaultState(), Blocks.AIR.getDefaultState(), p_236140_0_, true, true);
      });
      public static final DimensionSettings.Preset field_236126_f_ = new DimensionSettings.Preset("caves", (p_236138_0_) -> {
         return func_236133_a_(new DimensionStructuresSettings(false), Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), p_236138_0_);
      });
      public static final DimensionSettings.Preset field_236127_g_ = new DimensionSettings.Preset("floating_islands", (p_236132_0_) -> {
         return func_236134_a_(new DimensionStructuresSettings(false), Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), p_236132_0_, false, false);
      });
      private final ITextComponent field_236129_i_;
      private final ResourceLocation field_236130_j_;
      private final DimensionSettings field_236131_k_;

      public Preset(String p_i231908_1_, Function<DimensionSettings.Preset, DimensionSettings> p_i231908_2_) {
         this.field_236130_j_ = new ResourceLocation(p_i231908_1_);
         this.field_236129_i_ = new TranslationTextComponent("generator.noise." + p_i231908_1_);
         this.field_236131_k_ = p_i231908_2_.apply(this);
         field_236128_h_.put(this.field_236130_j_, this);
      }

      public DimensionSettings func_236137_b_() {
         return this.field_236131_k_;
      }

      private static DimensionSettings func_236134_a_(DimensionStructuresSettings p_236134_0_, BlockState p_236134_1_, BlockState p_236134_2_, DimensionSettings.Preset p_236134_3_, boolean p_236134_4_, boolean p_236134_5_) {
         return new DimensionSettings(p_236134_0_, new NoiseSettings(128, new ScalingSettings(2.0D, 1.0D, 80.0D, 160.0D), new SlideSettings(-3000, 64, -46), new SlideSettings(-30, 7, 1), 2, 1, 0.0D, 0.0D, true, false, p_236134_5_, false), p_236134_1_, p_236134_2_, -10, -10, 0, p_236134_4_, Optional.of(p_236134_3_));
      }

      private static DimensionSettings func_236133_a_(DimensionStructuresSettings p_236133_0_, BlockState p_236133_1_, BlockState p_236133_2_, DimensionSettings.Preset p_236133_3_) {
         Map<Structure<?>, StructureSeparationSettings> map = Maps.newHashMap(DimensionStructuresSettings.field_236191_b_);
         map.put(Structure.field_236372_h_, new StructureSeparationSettings(25, 10, 34222645));
         return new DimensionSettings(new DimensionStructuresSettings(Optional.ofNullable(p_236133_0_.func_236199_b_()), map), new NoiseSettings(128, new ScalingSettings(1.0D, 3.0D, 80.0D, 60.0D), new SlideSettings(120, 3, 0), new SlideSettings(320, 4, -1), 1, 2, 0.0D, 0.019921875D, false, false, false, false), p_236133_1_, p_236133_2_, 0, 0, 32, false, Optional.of(p_236133_3_));
      }

      private static DimensionSettings func_236135_a_(DimensionStructuresSettings p_236135_0_, boolean p_236135_1_, DimensionSettings.Preset p_236135_2_) {
         double d0 = 0.9999999814507745D;
         return new DimensionSettings(p_236135_0_, new NoiseSettings(256, new ScalingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D), new SlideSettings(-10, 3, 0), new SlideSettings(-30, 0, 0), 1, 2, 1.0D, -0.46875D, true, true, false, p_236135_1_), Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), -10, 0, 63, false, Optional.of(p_236135_2_));
      }
   }
}