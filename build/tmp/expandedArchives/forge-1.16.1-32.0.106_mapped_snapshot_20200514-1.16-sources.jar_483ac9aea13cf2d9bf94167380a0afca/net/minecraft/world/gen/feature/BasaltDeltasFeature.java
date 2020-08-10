package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.block.BlockState;

public class BasaltDeltasFeature implements IFeatureConfig {
   public static final Codec<BasaltDeltasFeature> field_236495_a_ = RecordCodecBuilder.create((p_236502_0_) -> {
      return p_236502_0_.group(BlockState.field_235877_b_.fieldOf("contents").forGetter((p_236506_0_) -> {
         return p_236506_0_.field_236496_b_;
      }), BlockState.field_235877_b_.fieldOf("rim").forGetter((p_236505_0_) -> {
         return p_236505_0_.field_236497_c_;
      }), Codec.INT.fieldOf("minimum_radius").forGetter((p_236504_0_) -> {
         return p_236504_0_.field_236498_d_;
      }), Codec.INT.fieldOf("maximum_radius").forGetter((p_236503_0_) -> {
         return p_236503_0_.field_236499_e_;
      }), Codec.INT.fieldOf("maximum_rim").forGetter((p_236501_0_) -> {
         return p_236501_0_.field_236500_f_;
      })).apply(p_236502_0_, BasaltDeltasFeature::new);
   });
   public final BlockState field_236496_b_;
   public final BlockState field_236497_c_;
   public final int field_236498_d_;
   public final int field_236499_e_;
   public final int field_236500_f_;

   public BasaltDeltasFeature(BlockState p_i232009_1_, BlockState p_i232009_2_, int p_i232009_3_, int p_i232009_4_, int p_i232009_5_) {
      this.field_236496_b_ = p_i232009_1_;
      this.field_236497_c_ = p_i232009_2_;
      this.field_236498_d_ = p_i232009_3_;
      this.field_236499_e_ = p_i232009_4_;
      this.field_236500_f_ = p_i232009_5_;
   }

   public static class Builder {
      Optional<BlockState> field_236507_a_ = Optional.empty();
      Optional<BlockState> field_236508_b_ = Optional.empty();
      int field_236509_c_;
      int field_236510_d_;
      int field_236511_e_;

      public BasaltDeltasFeature.Builder func_236513_a_(int p_236513_1_, int p_236513_2_) {
         this.field_236509_c_ = p_236513_1_;
         this.field_236510_d_ = p_236513_2_;
         return this;
      }

      public BasaltDeltasFeature.Builder func_236514_a_(BlockState p_236514_1_) {
         this.field_236507_a_ = Optional.of(p_236514_1_);
         return this;
      }

      public BasaltDeltasFeature.Builder func_236515_a_(BlockState p_236515_1_, int p_236515_2_) {
         this.field_236508_b_ = Optional.of(p_236515_1_);
         this.field_236511_e_ = p_236515_2_;
         return this;
      }

      public BasaltDeltasFeature func_236512_a_() {
         if (!this.field_236507_a_.isPresent()) {
            throw new IllegalArgumentException("Missing contents");
         } else if (!this.field_236508_b_.isPresent()) {
            throw new IllegalArgumentException("Missing rim");
         } else if (this.field_236509_c_ > this.field_236510_d_) {
            throw new IllegalArgumentException("Minimum radius cannot be greater than maximum radius");
         } else {
            return new BasaltDeltasFeature(this.field_236507_a_.get(), this.field_236508_b_.get(), this.field_236509_c_, this.field_236510_d_, this.field_236511_e_);
         }
      }
   }
}