package net.minecraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AccessibilityScreen extends SettingsScreen {
   private static final AbstractOption[] OPTIONS = new AbstractOption[]{AbstractOption.NARRATOR, AbstractOption.SHOW_SUBTITLES, AbstractOption.ACCESSIBILITY_TEXT_BACKGROUND_OPACITY, AbstractOption.ACCESSIBILITY_TEXT_BACKGROUND, AbstractOption.CHAT_OPACITY, AbstractOption.field_238235_g_, AbstractOption.field_238236_h_, AbstractOption.AUTO_JUMP, AbstractOption.SNEAK, AbstractOption.SPRINT};
   private Widget field_212989_d;

   public AccessibilityScreen(Screen p_i51123_1_, GameSettings p_i51123_2_) {
      super(p_i51123_1_, p_i51123_2_, new TranslationTextComponent("options.accessibility.title"));
   }

   protected void func_231160_c_() {
      int i = 0;

      for(AbstractOption abstractoption : OPTIONS) {
         int j = this.field_230708_k_ / 2 - 155 + i % 2 * 160;
         int k = this.field_230709_l_ / 6 + 24 * (i >> 1);
         Widget widget = this.func_230480_a_(abstractoption.createWidget(this.field_230706_i_.gameSettings, j, k, 150));
         if (abstractoption == AbstractOption.NARRATOR) {
            this.field_212989_d = widget;
            widget.field_230693_o_ = NarratorChatListener.INSTANCE.isActive();
         }

         ++i;
      }

      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 6 + 144, 200, 20, DialogTexts.field_240632_c_, (p_212984_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 20, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public void func_212985_a() {
      this.field_212989_d.func_238482_a_(AbstractOption.NARRATOR.func_238157_c_(this.gameSettings));
   }
}