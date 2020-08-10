package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChatOptionsScreen extends SettingsScreen {
   private static final AbstractOption[] CHAT_OPTIONS = new AbstractOption[]{AbstractOption.CHAT_VISIBILITY, AbstractOption.CHAT_COLOR, AbstractOption.CHAT_LINKS, AbstractOption.CHAT_LINKS_PROMPT, AbstractOption.CHAT_OPACITY, AbstractOption.ACCESSIBILITY_TEXT_BACKGROUND_OPACITY, AbstractOption.CHAT_SCALE, AbstractOption.field_238235_g_, AbstractOption.CHAT_WIDTH, AbstractOption.CHAT_HEIGHT_FOCUSED, AbstractOption.CHAT_HEIGHT_UNFOCUSED, AbstractOption.NARRATOR, AbstractOption.AUTO_SUGGEST_COMMANDS, AbstractOption.REDUCED_DEBUG_INFO};
   private Widget narratorButton;

   public ChatOptionsScreen(Screen parentScreenIn, GameSettings gameSettingsIn) {
      super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("options.chat.title"));
   }

   protected void func_231160_c_() {
      int i = 0;

      for(AbstractOption abstractoption : CHAT_OPTIONS) {
         int j = this.field_230708_k_ / 2 - 155 + i % 2 * 160;
         int k = this.field_230709_l_ / 6 + 24 * (i >> 1);
         Widget widget = this.func_230480_a_(abstractoption.createWidget(this.field_230706_i_.gameSettings, j, k, 150));
         if (abstractoption == AbstractOption.NARRATOR) {
            this.narratorButton = widget;
            widget.field_230693_o_ = NarratorChatListener.INSTANCE.isActive();
         }

         ++i;
      }

      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 6 + 24 * (i + 1) / 2, 200, 20, DialogTexts.field_240632_c_, (p_212990_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 20, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public void updateNarratorButton() {
      this.narratorButton.func_238482_a_(AbstractOption.NARRATOR.func_238157_c_(this.gameSettings));
   }
}