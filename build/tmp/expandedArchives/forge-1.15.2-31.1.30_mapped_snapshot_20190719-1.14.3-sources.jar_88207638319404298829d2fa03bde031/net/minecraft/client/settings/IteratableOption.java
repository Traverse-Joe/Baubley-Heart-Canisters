package net.minecraft.client.settings;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IteratableOption extends AbstractOption {
   private final BiConsumer<GameSettings, Integer> setter;
   private final BiFunction<GameSettings, IteratableOption, String> field_216724_R;

   public IteratableOption(String p_i51164_1_, BiConsumer<GameSettings, Integer> getter, BiFunction<GameSettings, IteratableOption, String> p_i51164_3_) {
      super(p_i51164_1_);
      this.setter = getter;
      this.field_216724_R = p_i51164_3_;
   }

   public void func_216722_a(GameSettings options, int p_216722_2_) {
      this.setter.accept(options, p_216722_2_);
      options.saveOptions();
   }

   public Widget createWidget(GameSettings options, int p_216586_2_, int p_216586_3_, int p_216586_4_) {
      return new OptionButton(p_216586_2_, p_216586_3_, p_216586_4_, 20, this, this.func_216720_c(options), (p_216721_2_) -> {
         this.func_216722_a(options, 1);
         p_216721_2_.setMessage(this.func_216720_c(options));
      });
   }

   public String func_216720_c(GameSettings options) {
      return this.field_216724_R.apply(options, this);
   }
}