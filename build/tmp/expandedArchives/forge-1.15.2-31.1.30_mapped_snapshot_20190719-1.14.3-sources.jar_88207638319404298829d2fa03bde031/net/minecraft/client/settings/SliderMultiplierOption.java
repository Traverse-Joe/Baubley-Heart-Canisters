package net.minecraft.client.settings;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.GameSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SliderMultiplierOption extends SliderPercentageOption {
   public SliderMultiplierOption(String p_i51161_1_, double p_i51161_2_, double p_i51161_4_, float p_i51161_6_, Function<GameSettings, Double> p_i51161_7_, BiConsumer<GameSettings, Double> p_i51161_8_, BiFunction<GameSettings, SliderPercentageOption, String> p_i51161_9_) {
      super(p_i51161_1_, p_i51161_2_, p_i51161_4_, p_i51161_6_, p_i51161_7_, p_i51161_8_, p_i51161_9_);
   }

   public double func_216726_a(double p_216726_1_) {
      return Math.log(p_216726_1_ / this.minValue) / Math.log(this.maxValue / this.minValue);
   }

   public double func_216725_b(double p_216725_1_) {
      return this.minValue * Math.pow(Math.E, Math.log(this.maxValue / this.minValue) * p_216725_1_);
   }
}