package net.minecraft.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ILightReader extends IBlockReader {
   WorldLightManager func_225524_e_();

   @OnlyIn(Dist.CLIENT)
   int func_225525_a_(BlockPos p_225525_1_, ColorResolver p_225525_2_);

   default int func_226658_a_(LightType p_226658_1_, BlockPos p_226658_2_) {
      return this.func_225524_e_().getLightEngine(p_226658_1_).getLightFor(p_226658_2_);
   }

   default int func_226659_b_(BlockPos p_226659_1_, int p_226659_2_) {
      return this.func_225524_e_().func_227470_b_(p_226659_1_, p_226659_2_);
   }

   default boolean func_226660_f_(BlockPos p_226660_1_) {
      return this.func_226658_a_(LightType.SKY, p_226660_1_) >= this.getMaxLightLevel();
   }
}