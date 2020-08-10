package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.server.IDynamicRegistries;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DebugChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BiomeGeneratorTypeScreens {
   public static final BiomeGeneratorTypeScreens field_239066_a_ = new BiomeGeneratorTypeScreens("default") {
      protected ChunkGenerator func_230484_a_(long p_230484_1_) {
         return new NoiseChunkGenerator(new OverworldBiomeProvider(p_230484_1_, false, false), p_230484_1_, DimensionSettings.Preset.field_236122_b_.func_236137_b_());
      }
   };
   private static final BiomeGeneratorTypeScreens field_239070_e_ = new BiomeGeneratorTypeScreens("flat") {
      protected ChunkGenerator func_230484_a_(long p_230484_1_) {
         return new FlatChunkGenerator(FlatGenerationSettings.getDefaultFlatGenerator());
      }
   };
   private static final BiomeGeneratorTypeScreens field_239071_f_ = new BiomeGeneratorTypeScreens("large_biomes") {
      protected ChunkGenerator func_230484_a_(long p_230484_1_) {
         return new NoiseChunkGenerator(new OverworldBiomeProvider(p_230484_1_, false, true), p_230484_1_, DimensionSettings.Preset.field_236122_b_.func_236137_b_());
      }
   };
   public static final BiomeGeneratorTypeScreens field_239067_b_ = new BiomeGeneratorTypeScreens("amplified") {
      protected ChunkGenerator func_230484_a_(long p_230484_1_) {
         return new NoiseChunkGenerator(new OverworldBiomeProvider(p_230484_1_, false, false), p_230484_1_, DimensionSettings.Preset.field_236123_c_.func_236137_b_());
      }
   };
   private static final BiomeGeneratorTypeScreens field_239072_g_ = new BiomeGeneratorTypeScreens("single_biome_surface") {
      protected ChunkGenerator func_230484_a_(long p_230484_1_) {
         return new NoiseChunkGenerator(new SingleBiomeProvider(Biomes.PLAINS), p_230484_1_, DimensionSettings.Preset.field_236122_b_.func_236137_b_());
      }
   };
   private static final BiomeGeneratorTypeScreens field_239073_h_ = new BiomeGeneratorTypeScreens("single_biome_caves") {
      public DimensionGeneratorSettings func_241220_a_(IDynamicRegistries.Impl p_241220_1_, long p_241220_2_, boolean p_241220_4_, boolean p_241220_5_) {
         return new DimensionGeneratorSettings(p_241220_2_, p_241220_4_, p_241220_5_, DimensionGeneratorSettings.func_241520_a_(DimensionType.func_236022_a_(p_241220_2_), DimensionType::func_241507_b_, this.func_230484_a_(p_241220_2_)));
      }

      protected ChunkGenerator func_230484_a_(long p_230484_1_) {
         return new NoiseChunkGenerator(new SingleBiomeProvider(Biomes.PLAINS), p_230484_1_, DimensionSettings.Preset.field_236126_f_.func_236137_b_());
      }
   };
   private static final BiomeGeneratorTypeScreens field_239074_i_ = new BiomeGeneratorTypeScreens("single_biome_floating_islands") {
      protected ChunkGenerator func_230484_a_(long p_230484_1_) {
         return new NoiseChunkGenerator(new SingleBiomeProvider(Biomes.PLAINS), p_230484_1_, DimensionSettings.Preset.field_236127_g_.func_236137_b_());
      }
   };
   private static final BiomeGeneratorTypeScreens field_239075_j_ = new BiomeGeneratorTypeScreens("debug_all_block_states") {
      protected ChunkGenerator func_230484_a_(long p_230484_1_) {
         return DebugChunkGenerator.field_236065_d_;
      }
   };
   protected static final List<BiomeGeneratorTypeScreens> field_239068_c_ = Lists.newArrayList(field_239066_a_, field_239070_e_, field_239071_f_, field_239067_b_, field_239072_g_, field_239073_h_, field_239074_i_, field_239075_j_);
   protected static final Map<Optional<BiomeGeneratorTypeScreens>, BiomeGeneratorTypeScreens.IFactory> field_239069_d_ = ImmutableMap.of(Optional.of(field_239070_e_), (p_239089_0_, p_239089_1_) -> {
      ChunkGenerator chunkgenerator = p_239089_1_.func_236225_f_();
      return new CreateFlatWorldScreen(p_239089_0_, (p_239083_2_) -> {
         p_239089_0_.field_238934_c_.func_239043_a_(new DimensionGeneratorSettings(p_239089_1_.func_236221_b_(), p_239089_1_.func_236222_c_(), p_239089_1_.func_236223_d_(), DimensionGeneratorSettings.func_236216_a_(p_239089_1_.func_236224_e_(), new FlatChunkGenerator(p_239083_2_))));
      }, chunkgenerator instanceof FlatChunkGenerator ? ((FlatChunkGenerator)chunkgenerator).func_236073_g_() : FlatGenerationSettings.getDefaultFlatGenerator());
   }, Optional.of(field_239072_g_), (p_239087_0_, p_239087_1_) -> {
      return new CreateBuffetWorldScreen(p_239087_0_, (p_239088_2_) -> {
         p_239087_0_.field_238934_c_.func_239043_a_(func_239080_a_(p_239087_1_, field_239072_g_, p_239088_2_));
      }, func_239084_b_(p_239087_1_));
   }, Optional.of(field_239073_h_), (p_239085_0_, p_239085_1_) -> {
      return new CreateBuffetWorldScreen(p_239085_0_, (p_239086_2_) -> {
         p_239085_0_.field_238934_c_.func_239043_a_(func_239080_a_(p_239085_1_, field_239073_h_, p_239086_2_));
      }, func_239084_b_(p_239085_1_));
   }, Optional.of(field_239074_i_), (p_239081_0_, p_239081_1_) -> {
      return new CreateBuffetWorldScreen(p_239081_0_, (p_239082_2_) -> {
         p_239081_0_.field_238934_c_.func_239043_a_(func_239080_a_(p_239081_1_, field_239074_i_, p_239082_2_));
      }, func_239084_b_(p_239081_1_));
   });
   private final ITextComponent field_239076_k_;

   private BiomeGeneratorTypeScreens(String p_i232324_1_) {
      this.field_239076_k_ = new TranslationTextComponent("generator." + p_i232324_1_);
   }

   private static DimensionGeneratorSettings func_239080_a_(DimensionGeneratorSettings p_239080_0_, BiomeGeneratorTypeScreens p_239080_1_, Biome p_239080_2_) {
      BiomeProvider biomeprovider = new SingleBiomeProvider(p_239080_2_);
      DimensionSettings dimensionsettings;
      if (p_239080_1_ == field_239073_h_) {
         dimensionsettings = DimensionSettings.Preset.field_236126_f_.func_236137_b_();
      } else if (p_239080_1_ == field_239074_i_) {
         dimensionsettings = DimensionSettings.Preset.field_236127_g_.func_236137_b_();
      } else {
         dimensionsettings = DimensionSettings.Preset.field_236122_b_.func_236137_b_();
      }

      return new DimensionGeneratorSettings(p_239080_0_.func_236221_b_(), p_239080_0_.func_236222_c_(), p_239080_0_.func_236223_d_(), DimensionGeneratorSettings.func_236216_a_(p_239080_0_.func_236224_e_(), new NoiseChunkGenerator(biomeprovider, p_239080_0_.func_236221_b_(), dimensionsettings)));
   }

   private static Biome func_239084_b_(DimensionGeneratorSettings p_239084_0_) {
      return p_239084_0_.func_236225_f_().getBiomeProvider().func_235203_c_().stream().findFirst().orElse(Biomes.PLAINS);
   }

   public static Optional<BiomeGeneratorTypeScreens> func_239079_a_(DimensionGeneratorSettings p_239079_0_) {
      ChunkGenerator chunkgenerator = p_239079_0_.func_236225_f_();
      if (chunkgenerator instanceof FlatChunkGenerator) {
         return Optional.of(field_239070_e_);
      } else {
         return chunkgenerator instanceof DebugChunkGenerator ? Optional.of(field_239075_j_) : Optional.empty();
      }
   }

   public ITextComponent func_239077_a_() {
      return this.field_239076_k_;
   }

   public DimensionGeneratorSettings func_241220_a_(IDynamicRegistries.Impl p_241220_1_, long p_241220_2_, boolean p_241220_4_, boolean p_241220_5_) {
      return new DimensionGeneratorSettings(p_241220_2_, p_241220_4_, p_241220_5_, DimensionGeneratorSettings.func_236216_a_(DimensionType.func_236022_a_(p_241220_2_), this.func_230484_a_(p_241220_2_)));
   }

   protected abstract ChunkGenerator func_230484_a_(long p_230484_1_);

   @OnlyIn(Dist.CLIENT)
   public interface IFactory {
      Screen createEditScreen(CreateWorldScreen p_createEditScreen_1_, DimensionGeneratorSettings p_createEditScreen_2_);
   }
}