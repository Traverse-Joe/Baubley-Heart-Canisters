package net.minecraft.client.settings;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BooleanOption extends AbstractOption {
   private final Predicate<GameSettings> getter;
   private final BiConsumer<GameSettings, Boolean> setter;

   public BooleanOption(String p_i51167_1_, Predicate<GameSettings> getter, BiConsumer<GameSettings, Boolean> setter) {
      super(p_i51167_1_);
      this.getter = getter;
      this.setter = setter;
   }

   public void set(GameSettings options, String p_216742_2_) {
      this.set(options, "true".equals(p_216742_2_));
   }

   public void func_216740_a(GameSettings options) {
      this.set(options, !this.get(options));
      options.saveOptions();
   }

   private void set(GameSettings options, boolean p_216744_2_) {
      this.setter.accept(options, p_216744_2_);
   }

   public boolean get(GameSettings options) {
      return this.getter.test(options);
   }

   public Widget createWidget(GameSettings options, int p_216586_2_, int p_216586_3_, int p_216586_4_) {
      return new OptionButton(p_216586_2_, p_216586_3_, p_216586_4_, 20, this, this.func_216743_c(options), (p_216745_2_) -> {
         this.func_216740_a(options);
         p_216745_2_.setMessage(this.func_216743_c(options));
      });
   }

   public String func_216743_c(GameSettings options) {
      return this.getDisplayString() + I18n.format(this.get(options) ? "options.on" : "options.off");
   }
}