package net.minecraft.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BiomeAmbience {
   public static final Codec<BiomeAmbience> field_235204_a_ = RecordCodecBuilder.create((p_235215_0_) -> {
      return p_235215_0_.group(Codec.INT.fieldOf("fog_color").forGetter((p_235229_0_) -> {
         return p_235229_0_.field_235205_b_;
      }), Codec.INT.fieldOf("water_color").forGetter((p_235227_0_) -> {
         return p_235227_0_.field_235206_c_;
      }), Codec.INT.fieldOf("water_fog_color").forGetter((p_235225_0_) -> {
         return p_235225_0_.field_235207_d_;
      }), ParticleEffectAmbience.field_235041_a_.optionalFieldOf("particle").forGetter((p_235223_0_) -> {
         return p_235223_0_.field_235208_e_;
      }), SoundEvent.field_232678_a_.optionalFieldOf("ambient_sound").forGetter((p_235221_0_) -> {
         return p_235221_0_.field_235209_f_;
      }), MoodSoundAmbience.field_235026_a_.optionalFieldOf("mood_sound").forGetter((p_235219_0_) -> {
         return p_235219_0_.field_235210_g_;
      }), SoundAdditionsAmbience.field_235018_a_.optionalFieldOf("additions_sound").forGetter((p_235217_0_) -> {
         return p_235217_0_.field_235211_h_;
      }), BackgroundMusicSelector.field_232656_a_.optionalFieldOf("music").forGetter((p_235214_0_) -> {
         return p_235214_0_.field_235212_i_;
      })).apply(p_235215_0_, BiomeAmbience::new);
   });
   private final int field_235205_b_;
   private final int field_235206_c_;
   private final int field_235207_d_;
   private final Optional<ParticleEffectAmbience> field_235208_e_;
   private final Optional<SoundEvent> field_235209_f_;
   private final Optional<MoodSoundAmbience> field_235210_g_;
   private final Optional<SoundAdditionsAmbience> field_235211_h_;
   private final Optional<BackgroundMusicSelector> field_235212_i_;

   private BiomeAmbience(int p_i231635_1_, int p_i231635_2_, int p_i231635_3_, Optional<ParticleEffectAmbience> p_i231635_4_, Optional<SoundEvent> p_i231635_5_, Optional<MoodSoundAmbience> p_i231635_6_, Optional<SoundAdditionsAmbience> p_i231635_7_, Optional<BackgroundMusicSelector> p_i231635_8_) {
      this.field_235205_b_ = p_i231635_1_;
      this.field_235206_c_ = p_i231635_2_;
      this.field_235207_d_ = p_i231635_3_;
      this.field_235208_e_ = p_i231635_4_;
      this.field_235209_f_ = p_i231635_5_;
      this.field_235210_g_ = p_i231635_6_;
      this.field_235211_h_ = p_i231635_7_;
      this.field_235212_i_ = p_i231635_8_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_235213_a_() {
      return this.field_235205_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_235216_b_() {
      return this.field_235206_c_;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_235218_c_() {
      return this.field_235207_d_;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<ParticleEffectAmbience> func_235220_d_() {
      return this.field_235208_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundEvent> func_235222_e_() {
      return this.field_235209_f_;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<MoodSoundAmbience> func_235224_f_() {
      return this.field_235210_g_;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundAdditionsAmbience> func_235226_g_() {
      return this.field_235211_h_;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<BackgroundMusicSelector> func_235228_h_() {
      return this.field_235212_i_;
   }

   public static class Builder {
      private OptionalInt field_235230_a_ = OptionalInt.empty();
      private OptionalInt field_235231_b_ = OptionalInt.empty();
      private OptionalInt field_235232_c_ = OptionalInt.empty();
      private Optional<ParticleEffectAmbience> field_235233_d_ = Optional.empty();
      private Optional<SoundEvent> field_235234_e_ = Optional.empty();
      private Optional<MoodSoundAmbience> field_235235_f_ = Optional.empty();
      private Optional<SoundAdditionsAmbience> field_235236_g_ = Optional.empty();
      private Optional<BackgroundMusicSelector> field_235237_h_ = Optional.empty();

      public BiomeAmbience.Builder func_235239_a_(int p_235239_1_) {
         this.field_235230_a_ = OptionalInt.of(p_235239_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235246_b_(int p_235246_1_) {
         this.field_235231_b_ = OptionalInt.of(p_235246_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235248_c_(int p_235248_1_) {
         this.field_235232_c_ = OptionalInt.of(p_235248_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235244_a_(ParticleEffectAmbience p_235244_1_) {
         this.field_235233_d_ = Optional.of(p_235244_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235241_a_(SoundEvent p_235241_1_) {
         this.field_235234_e_ = Optional.of(p_235241_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235243_a_(MoodSoundAmbience p_235243_1_) {
         this.field_235235_f_ = Optional.of(p_235243_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235242_a_(SoundAdditionsAmbience p_235242_1_) {
         this.field_235236_g_ = Optional.of(p_235242_1_);
         return this;
      }

      public BiomeAmbience.Builder func_235240_a_(BackgroundMusicSelector p_235240_1_) {
         this.field_235237_h_ = Optional.of(p_235240_1_);
         return this;
      }

      public BiomeAmbience func_235238_a_() {
         return new BiomeAmbience(this.field_235230_a_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'fog' color.");
         }), this.field_235231_b_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water' color.");
         }), this.field_235232_c_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water fog' color.");
         }), this.field_235233_d_, this.field_235234_e_, this.field_235235_f_, this.field_235236_g_, this.field_235237_h_);
      }
   }
}