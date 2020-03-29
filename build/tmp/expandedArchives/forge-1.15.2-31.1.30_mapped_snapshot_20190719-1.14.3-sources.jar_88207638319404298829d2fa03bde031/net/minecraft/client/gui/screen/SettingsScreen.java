package net.minecraft.client.gui.screen;

import net.minecraft.client.GameSettings;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SettingsScreen extends Screen {
   protected final Screen field_228182_a_;
   protected final GameSettings field_228183_b_;

   public SettingsScreen(Screen p_i225930_1_, GameSettings p_i225930_2_, ITextComponent p_i225930_3_) {
      super(p_i225930_3_);
      this.field_228182_a_ = p_i225930_1_;
      this.field_228183_b_ = p_i225930_2_;
   }

   public void removed() {
      this.minecraft.gameSettings.saveOptions();
   }

   public void onClose() {
      this.minecraft.displayGuiScreen(this.field_228182_a_);
   }
}