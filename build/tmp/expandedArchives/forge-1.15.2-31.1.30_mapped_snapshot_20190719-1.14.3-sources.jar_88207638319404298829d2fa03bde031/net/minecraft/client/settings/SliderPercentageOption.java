package net.minecraft.client.settings;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SliderPercentageOption extends AbstractOption {
   protected final float stepSize;
   protected final double minValue;
   protected double maxValue;
   private final Function<GameSettings, Double> getter;
   private final BiConsumer<GameSettings, Double> setter;
   private final BiFunction<GameSettings, SliderPercentageOption, String> getDisplayStringFunc;

   public SliderPercentageOption(String translationKey, double minValueIn, double maxValueIn, float stepSizeIn, Function<GameSettings, Double> getter, BiConsumer<GameSettings, Double> setter, BiFunction<GameSettings, SliderPercentageOption, String> getDisplayString) {
      super(translationKey);
      this.minValue = minValueIn;
      this.maxValue = maxValueIn;
      this.stepSize = stepSizeIn;
      this.getter = getter;
      this.setter = setter;
      this.getDisplayStringFunc = getDisplayString;
   }

   public Widget createWidget(GameSettings options, int p_216586_2_, int p_216586_3_, int p_216586_4_) {
      return new OptionSlider(options, p_216586_2_, p_216586_3_, p_216586_4_, 20, this);
   }

   public double func_216726_a(double p_216726_1_) {
      return MathHelper.clamp((this.func_216731_c(p_216726_1_) - this.minValue) / (this.maxValue - this.minValue), 0.0D, 1.0D);
   }

   public double func_216725_b(double p_216725_1_) {
      return this.func_216731_c(MathHelper.lerp(MathHelper.clamp(p_216725_1_, 0.0D, 1.0D), this.minValue, this.maxValue));
   }

   private double func_216731_c(double p_216731_1_) {
      if (this.stepSize > 0.0F) {
         p_216731_1_ = (double)(this.stepSize * (float)Math.round(p_216731_1_ / (double)this.stepSize));
      }

      return MathHelper.clamp(p_216731_1_, this.minValue, this.maxValue);
   }

   public double getMinValue() {
      return this.minValue;
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   public void func_216728_a(float p_216728_1_) {
      this.maxValue = (double)p_216728_1_;
   }

   public void set(GameSettings options, double p_216727_2_) {
      this.setter.accept(options, p_216727_2_);
   }

   public double get(GameSettings options) {
      return this.getter.apply(options);
   }

   public String func_216730_c(GameSettings options) {
      return this.getDisplayStringFunc.apply(options, this);
   }
}