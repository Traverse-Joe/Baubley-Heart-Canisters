package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.util.math.MathHelper;

public final class ImprovedNoiseGenerator {
   private final byte[] permutations;
   public final double xCoord;
   public final double yCoord;
   public final double zCoord;

   public ImprovedNoiseGenerator(Random p_i45469_1_) {
      this.xCoord = p_i45469_1_.nextDouble() * 256.0D;
      this.yCoord = p_i45469_1_.nextDouble() * 256.0D;
      this.zCoord = p_i45469_1_.nextDouble() * 256.0D;
      this.permutations = new byte[256];

      for(int i = 0; i < 256; ++i) {
         this.permutations[i] = (byte)i;
      }

      for(int k = 0; k < 256; ++k) {
         int j = p_i45469_1_.nextInt(256 - k);
         byte b0 = this.permutations[k];
         this.permutations[k] = this.permutations[k + j];
         this.permutations[k + j] = b0;
      }

   }

   public double func_215456_a(double p_215456_1_, double p_215456_3_, double p_215456_5_, double p_215456_7_, double p_215456_9_) {
      double d0 = p_215456_1_ + this.xCoord;
      double d1 = p_215456_3_ + this.yCoord;
      double d2 = p_215456_5_ + this.zCoord;
      int i = MathHelper.floor(d0);
      int j = MathHelper.floor(d1);
      int k = MathHelper.floor(d2);
      double d3 = d0 - (double)i;
      double d4 = d1 - (double)j;
      double d5 = d2 - (double)k;
      double d6 = MathHelper.perlinFade(d3);
      double d7 = MathHelper.perlinFade(d4);
      double d8 = MathHelper.perlinFade(d5);
      double d9;
      if (p_215456_7_ != 0.0D) {
         double d10 = Math.min(p_215456_9_, d4);
         d9 = (double)MathHelper.floor(d10 / p_215456_7_) * p_215456_7_;
      } else {
         d9 = 0.0D;
      }

      return this.func_215459_a(i, j, k, d3, d4 - d9, d5, d6, d7, d8);
   }

   private static double func_215457_a(int p_215457_0_, double p_215457_1_, double p_215457_3_, double p_215457_5_) {
      int i = p_215457_0_ & 15;
      return SimplexNoiseGenerator.func_215467_a(SimplexNoiseGenerator.field_215468_a[i], p_215457_1_, p_215457_3_, p_215457_5_);
   }

   private int func_215458_a(int p_215458_1_) {
      return this.permutations[p_215458_1_ & 255] & 255;
   }

   public double func_215459_a(int p_215459_1_, int p_215459_2_, int p_215459_3_, double p_215459_4_, double p_215459_6_, double p_215459_8_, double p_215459_10_, double p_215459_12_, double p_215459_14_) {
      int i = this.func_215458_a(p_215459_1_) + p_215459_2_;
      int j = this.func_215458_a(i) + p_215459_3_;
      int k = this.func_215458_a(i + 1) + p_215459_3_;
      int l = this.func_215458_a(p_215459_1_ + 1) + p_215459_2_;
      int i1 = this.func_215458_a(l) + p_215459_3_;
      int j1 = this.func_215458_a(l + 1) + p_215459_3_;
      double d0 = func_215457_a(this.func_215458_a(j), p_215459_4_, p_215459_6_, p_215459_8_);
      double d1 = func_215457_a(this.func_215458_a(i1), p_215459_4_ - 1.0D, p_215459_6_, p_215459_8_);
      double d2 = func_215457_a(this.func_215458_a(k), p_215459_4_, p_215459_6_ - 1.0D, p_215459_8_);
      double d3 = func_215457_a(this.func_215458_a(j1), p_215459_4_ - 1.0D, p_215459_6_ - 1.0D, p_215459_8_);
      double d4 = func_215457_a(this.func_215458_a(j + 1), p_215459_4_, p_215459_6_, p_215459_8_ - 1.0D);
      double d5 = func_215457_a(this.func_215458_a(i1 + 1), p_215459_4_ - 1.0D, p_215459_6_, p_215459_8_ - 1.0D);
      double d6 = func_215457_a(this.func_215458_a(k + 1), p_215459_4_, p_215459_6_ - 1.0D, p_215459_8_ - 1.0D);
      double d7 = func_215457_a(this.func_215458_a(j1 + 1), p_215459_4_ - 1.0D, p_215459_6_ - 1.0D, p_215459_8_ - 1.0D);
      return MathHelper.lerp3(p_215459_10_, p_215459_12_, p_215459_14_, d0, d1, d2, d3, d4, d5, d6, d7);
   }
}