package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ColumnConfig implements IFeatureConfig {
   public static final Codec<ColumnConfig> field_236465_a_ = RecordCodecBuilder.create((p_236471_0_) -> {
      return p_236471_0_.group(Codec.INT.fieldOf("minimum_reach").forGetter((p_236474_0_) -> {
         return p_236474_0_.field_236466_b_;
      }), Codec.INT.fieldOf("maximum_reach").forGetter((p_236473_0_) -> {
         return p_236473_0_.field_236467_c_;
      }), Codec.INT.fieldOf("minimum_height").forGetter((p_236472_0_) -> {
         return p_236472_0_.field_236468_d_;
      }), Codec.INT.fieldOf("maximum_height").forGetter((p_236470_0_) -> {
         return p_236470_0_.field_236469_e_;
      })).apply(p_236471_0_, ColumnConfig::new);
   });
   public final int field_236466_b_;
   public final int field_236467_c_;
   public final int field_236468_d_;
   public final int field_236469_e_;

   public ColumnConfig(int p_i232008_1_, int p_i232008_2_, int p_i232008_3_, int p_i232008_4_) {
      this.field_236466_b_ = p_i232008_1_;
      this.field_236467_c_ = p_i232008_2_;
      this.field_236468_d_ = p_i232008_3_;
      this.field_236469_e_ = p_i232008_4_;
   }

   public static class Builder {
      private int field_236475_a_;
      private int field_236476_b_;
      private int field_236477_c_;
      private int field_236478_d_;

      public ColumnConfig.Builder func_236480_a_(int p_236480_1_) {
         this.field_236475_a_ = p_236480_1_;
         this.field_236476_b_ = p_236480_1_;
         return this;
      }

      public ColumnConfig.Builder func_236481_a_(int p_236481_1_, int p_236481_2_) {
         this.field_236475_a_ = p_236481_1_;
         this.field_236476_b_ = p_236481_2_;
         return this;
      }

      public ColumnConfig.Builder func_236482_b_(int p_236482_1_, int p_236482_2_) {
         this.field_236477_c_ = p_236482_1_;
         this.field_236478_d_ = p_236482_2_;
         return this;
      }

      public ColumnConfig func_236479_a_() {
         if (this.field_236477_c_ < 1) {
            throw new IllegalArgumentException("Minimum height cannot be less than 1");
         } else if (this.field_236475_a_ < 0) {
            throw new IllegalArgumentException("Minimum reach cannot be negative");
         } else if (this.field_236475_a_ <= this.field_236476_b_ && this.field_236477_c_ <= this.field_236478_d_) {
            return new ColumnConfig(this.field_236475_a_, this.field_236476_b_, this.field_236477_c_, this.field_236478_d_);
         } else {
            throw new IllegalArgumentException("Minimum reach/height cannot be greater than maximum width/height");
         }
      }
   }
}