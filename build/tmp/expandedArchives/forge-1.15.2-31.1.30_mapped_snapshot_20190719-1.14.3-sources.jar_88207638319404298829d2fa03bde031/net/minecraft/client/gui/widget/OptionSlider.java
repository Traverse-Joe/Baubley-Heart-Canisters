package net.minecraft.client.gui.widget;

import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionSlider extends AbstractSlider {
   private final SliderPercentageOption option;

   public OptionSlider(GameSettings settings, int xIn, int yIn, int widthIn, int heightIn, SliderPercentageOption p_i51129_6_) {
      super(settings, xIn, yIn, widthIn, heightIn, (double)((float)p_i51129_6_.func_216726_a(p_i51129_6_.get(settings))));
      this.option = p_i51129_6_;
      this.updateMessage();
   }

   protected void applyValue() {
      this.option.set(this.options, this.option.func_216725_b(this.value));
      this.options.saveOptions();
   }

   protected void updateMessage() {
      this.setMessage(this.option.func_216730_c(this.options));
   }
}