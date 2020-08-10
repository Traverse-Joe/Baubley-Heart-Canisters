package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

public class ThreeLayerFeature extends AbstractFeatureSizeType {
   public static final Codec<ThreeLayerFeature> field_236716_c_ = RecordCodecBuilder.create((p_236722_0_) -> {
      return p_236722_0_.group(Codec.INT.fieldOf("limit").withDefault(1).forGetter((p_236727_0_) -> {
         return p_236727_0_.field_236717_d_;
      }), Codec.INT.fieldOf("upper_limit").withDefault(1).forGetter((p_236726_0_) -> {
         return p_236726_0_.field_236718_e_;
      }), Codec.INT.fieldOf("lower_size").withDefault(0).forGetter((p_236725_0_) -> {
         return p_236725_0_.field_236719_f_;
      }), Codec.INT.fieldOf("middle_size").withDefault(1).forGetter((p_236724_0_) -> {
         return p_236724_0_.field_236720_g_;
      }), Codec.INT.fieldOf("upper_size").withDefault(1).forGetter((p_236723_0_) -> {
         return p_236723_0_.field_236721_h_;
      }), func_236706_a_()).apply(p_236722_0_, ThreeLayerFeature::new);
   });
   private final int field_236717_d_;
   private final int field_236718_e_;
   private final int field_236719_f_;
   private final int field_236720_g_;
   private final int field_236721_h_;

   public ThreeLayerFeature(int p_i232024_1_, int p_i232024_2_, int p_i232024_3_, int p_i232024_4_, int p_i232024_5_, OptionalInt p_i232024_6_) {
      super(p_i232024_6_);
      this.field_236717_d_ = p_i232024_1_;
      this.field_236718_e_ = p_i232024_2_;
      this.field_236719_f_ = p_i232024_3_;
      this.field_236720_g_ = p_i232024_4_;
      this.field_236721_h_ = p_i232024_5_;
   }

   protected FeatureSizeType<?> func_230370_b_() {
      return FeatureSizeType.field_236712_b_;
   }

   public int func_230369_a_(int p_230369_1_, int p_230369_2_) {
      if (p_230369_2_ < this.field_236717_d_) {
         return this.field_236719_f_;
      } else {
         return p_230369_2_ >= p_230369_1_ - this.field_236718_e_ ? this.field_236721_h_ : this.field_236720_g_;
      }
   }
}