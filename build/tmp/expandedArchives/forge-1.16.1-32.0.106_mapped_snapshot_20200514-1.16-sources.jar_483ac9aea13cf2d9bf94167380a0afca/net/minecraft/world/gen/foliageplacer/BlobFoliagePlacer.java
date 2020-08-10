package net.minecraft.world.gen.foliageplacer;

import com.mojang.datafixers.Products.P5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;

public class BlobFoliagePlacer extends FoliagePlacer {
   public static final Codec<BlobFoliagePlacer> field_236738_a_ = RecordCodecBuilder.create((p_236742_0_) -> {
      return func_236740_a_(p_236742_0_).apply(p_236742_0_, BlobFoliagePlacer::new);
   });
   protected final int field_236739_b_;

   protected static <P extends BlobFoliagePlacer> P5<Mu<P>, Integer, Integer, Integer, Integer, Integer> func_236740_a_(Instance<P> p_236740_0_) {
      return func_236756_b_(p_236740_0_).and(Codec.INT.fieldOf("height").forGetter((p_236741_0_) -> {
         return p_236741_0_.field_236739_b_;
      }));
   }

   protected BlobFoliagePlacer(int p_i232029_1_, int p_i232029_2_, int p_i232029_3_, int p_i232029_4_, int p_i232029_5_, FoliagePlacerType<?> p_i232029_6_) {
      super(p_i232029_1_, p_i232029_2_, p_i232029_3_, p_i232029_4_);
      this.field_236739_b_ = p_i232029_5_;
   }

   public BlobFoliagePlacer(int p_i232028_1_, int p_i232028_2_, int p_i232028_3_, int p_i232028_4_, int p_i232028_5_) {
      this(p_i232028_1_, p_i232028_2_, p_i232028_3_, p_i232028_4_, p_i232028_5_, FoliagePlacerType.BLOB);
   }

   protected FoliagePlacerType<?> func_230371_a_() {
      return FoliagePlacerType.BLOB;
   }

   protected void func_230372_a_(IWorldGenerationReader p_230372_1_, Random p_230372_2_, BaseTreeFeatureConfig p_230372_3_, int p_230372_4_, FoliagePlacer.Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> p_230372_8_, int p_230372_9_, MutableBoundingBox p_230372_10_) {
      for(int i = p_230372_9_; i >= p_230372_9_ - p_230372_6_; --i) {
         int j = Math.max(p_230372_7_ + p_230372_5_.func_236764_b_() - 1 - i / 2, 0);
         this.func_236753_a_(p_230372_1_, p_230372_2_, p_230372_3_, p_230372_5_.func_236763_a_(), j, p_230372_8_, i, p_230372_5_.func_236765_c_(), p_230372_10_);
      }

   }

   public int func_230374_a_(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_) {
      return this.field_236739_b_;
   }

   protected boolean func_230373_a_(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
      return p_230373_2_ == p_230373_5_ && p_230373_4_ == p_230373_5_ && (p_230373_1_.nextInt(2) == 0 || p_230373_3_ == 0);
   }
}