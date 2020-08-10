package net.minecraft.world.gen.foliageplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;

public class SpruceFoliagePlacer extends FoliagePlacer {
   public static final Codec<SpruceFoliagePlacer> field_236790_a_ = RecordCodecBuilder.create((p_236793_0_) -> {
      return func_236756_b_(p_236793_0_).and(p_236793_0_.group(Codec.INT.fieldOf("trunk_height").forGetter((p_236795_0_) -> {
         return p_236795_0_.field_236791_b_;
      }), Codec.INT.fieldOf("trunk_height_random").forGetter((p_236794_0_) -> {
         return p_236794_0_.field_236792_c_;
      }))).apply(p_236793_0_, SpruceFoliagePlacer::new);
   });
   private final int field_236791_b_;
   private final int field_236792_c_;

   public SpruceFoliagePlacer(int p_i232040_1_, int p_i232040_2_, int p_i232040_3_, int p_i232040_4_, int p_i232040_5_, int p_i232040_6_) {
      super(p_i232040_1_, p_i232040_2_, p_i232040_3_, p_i232040_4_);
      this.field_236791_b_ = p_i232040_5_;
      this.field_236792_c_ = p_i232040_6_;
   }

   protected FoliagePlacerType<?> func_230371_a_() {
      return FoliagePlacerType.SPRUCE;
   }

   protected void func_230372_a_(IWorldGenerationReader p_230372_1_, Random p_230372_2_, BaseTreeFeatureConfig p_230372_3_, int p_230372_4_, FoliagePlacer.Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> p_230372_8_, int p_230372_9_, MutableBoundingBox p_230372_10_) {
      BlockPos blockpos = p_230372_5_.func_236763_a_();
      int i = p_230372_2_.nextInt(2);
      int j = 1;
      int k = 0;

      for(int l = p_230372_9_; l >= -p_230372_6_; --l) {
         this.func_236753_a_(p_230372_1_, p_230372_2_, p_230372_3_, blockpos, i, p_230372_8_, l, p_230372_5_.func_236765_c_(), p_230372_10_);
         if (i >= j) {
            i = k;
            k = 1;
            j = Math.min(j + 1, p_230372_7_ + p_230372_5_.func_236764_b_());
         } else {
            ++i;
         }
      }

   }

   public int func_230374_a_(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_) {
      return Math.max(4, p_230374_2_ - this.field_236791_b_ - p_230374_1_.nextInt(this.field_236792_c_ + 1));
   }

   protected boolean func_230373_a_(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
      return p_230373_2_ == p_230373_5_ && p_230373_4_ == p_230373_5_ && p_230373_5_ > 0;
   }
}