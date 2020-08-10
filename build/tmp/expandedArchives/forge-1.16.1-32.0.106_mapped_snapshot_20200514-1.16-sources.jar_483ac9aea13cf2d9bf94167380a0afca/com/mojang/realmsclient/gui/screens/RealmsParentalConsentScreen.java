package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsParentalConsentScreen extends RealmsScreen {
   private final Screen field_224260_a;

   public RealmsParentalConsentScreen(Screen p_i232210_1_) {
      this.field_224260_a = p_i232210_1_;
   }

   public void func_231160_c_() {
      RealmsNarratorHelper.func_239550_a_(I18n.format("mco.account.privacyinfo"));
      ITextComponent itextcomponent = new TranslationTextComponent("mco.account.update");
      ITextComponent itextcomponent1 = DialogTexts.field_240637_h_;
      int i = Math.max(this.field_230712_o_.func_238414_a_(itextcomponent), this.field_230712_o_.func_238414_a_(itextcomponent1)) + 30;
      ITextComponent itextcomponent2 = new TranslationTextComponent("mco.account.privacy.info");
      int j = (int)((double)this.field_230712_o_.func_238414_a_(itextcomponent2) * 1.2D);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - j / 2, func_239562_k_(11), j, 20, itextcomponent2, (p_237862_0_) -> {
         Util.getOSType().openURI("https://minecraft.net/privacy/gdpr/");
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - (i + 5), func_239562_k_(13), i, 20, itextcomponent, (p_237861_0_) -> {
         Util.getOSType().openURI("https://minecraft.net/update-account");
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, func_239562_k_(13), i, 20, itextcomponent1, (p_237860_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224260_a);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      List<ITextProperties> list = this.field_230706_i_.fontRenderer.func_238425_b_(new TranslationTextComponent("mco.account.privacyinfo"), (int)Math.round((double)this.field_230708_k_ * 0.9D));
      int i = 15;

      for(ITextProperties itextproperties : list) {
         this.func_238472_a_(p_230430_1_, this.field_230712_o_, itextproperties, this.field_230708_k_ / 2, i, 16777215);
         i += 15;
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}