package net.minecraft.world.gen.foliageplacer;

import com.mojang.datafixers.Products.P4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;

public abstract class FoliagePlacer {
   public static final Codec<FoliagePlacer> field_236749_d_ = Registry.FOLIAGE_PLACER_TYPE.dispatch(FoliagePlacer::func_230371_a_, FoliagePlacerType::func_236772_a_);
   protected final int field_227381_a_;
   protected final int field_227382_b_;
   protected final int field_236750_g_;
   protected final int field_236751_h_;

   protected static <P extends FoliagePlacer> P4<Mu<P>, Integer, Integer, Integer, Integer> func_236756_b_(Instance<P> p_236756_0_) {
      return p_236756_0_.group(Codec.INT.fieldOf("radius").forGetter((p_236759_0_) -> {
         return p_236759_0_.field_227381_a_;
      }), Codec.INT.fieldOf("radius_random").forGetter((p_236758_0_) -> {
         return p_236758_0_.field_227382_b_;
      }), Codec.INT.fieldOf("offset").forGetter((p_236757_0_) -> {
         return p_236757_0_.field_236750_g_;
      }), Codec.INT.fieldOf("offset_random").forGetter((p_236754_0_) -> {
         return p_236754_0_.field_236751_h_;
      }));
   }

   public FoliagePlacer(int p_i232034_1_, int p_i232034_2_, int p_i232034_3_, int p_i232034_4_) {
      this.field_227381_a_ = p_i232034_1_;
      this.field_227382_b_ = p_i232034_2_;
      this.field_236750_g_ = p_i232034_3_;
      this.field_236751_h_ = p_i232034_4_;
   }

   protected abstract FoliagePlacerType<?> func_230371_a_();

   public void func_236752_a_(IWorldGenerationReader p_236752_1_, Random p_236752_2_, BaseTreeFeatureConfig p_236752_3_, int p_236752_4_, FoliagePlacer.Foliage p_236752_5_, int p_236752_6_, int p_236752_7_, Set<BlockPos> p_236752_8_, MutableBoundingBox p_236752_9_) {
      this.func_230372_a_(p_236752_1_, p_236752_2_, p_236752_3_, p_236752_4_, p_236752_5_, p_236752_6_, p_236752_7_, p_236752_8_, this.func_236755_a_(p_236752_2_), p_236752_9_);
   }

   protected abstract void func_230372_a_(IWorldGenerationReader p_230372_1_, Random p_230372_2_, BaseTreeFeatureConfig p_230372_3_, int p_230372_4_, FoliagePlacer.Foliage p_230372_5_, int p_230372_6_, int p_230372_7_, Set<BlockPos> p_230372_8_, int p_230372_9_, MutableBoundingBox p_230372_10_);

   public abstract int func_230374_a_(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_);

   public int func_230376_a_(Random p_230376_1_, int p_230376_2_) {
      return this.field_227381_a_ + p_230376_1_.nextInt(this.field_227382_b_ + 1);
   }

   private int func_236755_a_(Random p_236755_1_) {
      return this.field_236750_g_ + p_236755_1_.nextInt(this.field_236751_h_ + 1);
   }

   protected abstract boolean func_230373_a_(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_);

   protected boolean func_230375_b_(Random p_230375_1_, int p_230375_2_, int p_230375_3_, int p_230375_4_, int p_230375_5_, boolean p_230375_6_) {
      int i;
      int j;
      if (p_230375_6_) {
         i = Math.min(Math.abs(p_230375_2_), Math.abs(p_230375_2_ - 1));
         j = Math.min(Math.abs(p_230375_4_), Math.abs(p_230375_4_ - 1));
      } else {
         i = Math.abs(p_230375_2_);
         j = Math.abs(p_230375_4_);
      }

      return this.func_230373_a_(p_230375_1_, i, p_230375_3_, j, p_230375_5_, p_230375_6_);
   }

   protected void func_236753_a_(IWorldGenerationReader p_236753_1_, Random p_236753_2_, BaseTreeFeatureConfig p_236753_3_, BlockPos p_236753_4_, int p_236753_5_, Set<BlockPos> p_236753_6_, int p_236753_7_, boolean p_236753_8_, MutableBoundingBox p_236753_9_) {
      int i = p_236753_8_ ? 1 : 0;
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int j = -p_236753_5_; j <= p_236753_5_ + i; ++j) {
         for(int k = -p_236753_5_; k <= p_236753_5_ + i; ++k) {
            if (!this.func_230375_b_(p_236753_2_, j, p_236753_7_, k, p_236753_5_, p_236753_8_)) {
               blockpos$mutable.func_239621_a_(p_236753_4_, j, p_236753_7_, k);
               if (TreeFeature.func_236404_a_(p_236753_1_, blockpos$mutable)) {
                  p_236753_1_.setBlockState(blockpos$mutable, p_236753_3_.leavesProvider.getBlockState(p_236753_2_, blockpos$mutable), 19);
                  p_236753_9_.expandTo(new MutableBoundingBox(blockpos$mutable, blockpos$mutable));
                  p_236753_6_.add(blockpos$mutable.toImmutable());
               }
            }
         }
      }

   }

   public static final class Foliage {
      private final BlockPos field_236760_a_;
      private final int field_236761_b_;
      private final boolean field_236762_c_;

      public Foliage(BlockPos p_i232035_1_, int p_i232035_2_, boolean p_i232035_3_) {
         this.field_236760_a_ = p_i232035_1_;
         this.field_236761_b_ = p_i232035_2_;
         this.field_236762_c_ = p_i232035_3_;
      }

      public BlockPos func_236763_a_() {
         return this.field_236760_a_;
      }

      public int func_236764_b_() {
         return this.field_236761_b_;
      }

      public boolean func_236765_c_() {
         return this.field_236762_c_;
      }
   }
}