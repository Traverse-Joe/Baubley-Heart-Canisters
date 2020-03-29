package net.minecraft.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiomeColors {
   public static final ColorResolver GRASS_COLOR = Biome::func_225528_a_;
   public static final ColorResolver FOLIAGE_COLOR = (p_228362_0_, p_228362_1_, p_228362_3_) -> {
      return p_228362_0_.func_225527_a_();
   };
   public static final ColorResolver WATER_COLOR = (p_228360_0_, p_228360_1_, p_228360_3_) -> {
      return p_228360_0_.getWaterColor();
   };

   private static int func_228359_a_(ILightReader p_228359_0_, BlockPos p_228359_1_, ColorResolver p_228359_2_) {
      return p_228359_0_.func_225525_a_(p_228359_1_, p_228359_2_);
   }

   public static int func_228358_a_(ILightReader p_228358_0_, BlockPos p_228358_1_) {
      return func_228359_a_(p_228358_0_, p_228358_1_, GRASS_COLOR);
   }

   public static int func_228361_b_(ILightReader p_228361_0_, BlockPos p_228361_1_) {
      return func_228359_a_(p_228361_0_, p_228361_1_, FOLIAGE_COLOR);
   }

   public static int func_228363_c_(ILightReader p_228363_0_, BlockPos p_228363_1_) {
      return func_228359_a_(p_228363_0_, p_228363_1_, WATER_COLOR);
   }
}