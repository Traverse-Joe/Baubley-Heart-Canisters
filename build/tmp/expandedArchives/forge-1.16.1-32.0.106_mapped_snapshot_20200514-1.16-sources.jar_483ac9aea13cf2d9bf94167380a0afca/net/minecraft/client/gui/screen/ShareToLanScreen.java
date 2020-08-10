package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.HTTPUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShareToLanScreen extends Screen {
   private final Screen lastScreen;
   private Button allowCheatsButton;
   private Button gameModeButton;
   private String gameMode = "survival";
   private boolean allowCheats;

   public ShareToLanScreen(Screen lastScreenIn) {
      super(new TranslationTextComponent("lanServer.title"));
      this.lastScreen = lastScreenIn;
   }

   protected void func_231160_c_() {
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ - 28, 150, 20, new TranslationTextComponent("lanServer.start"), (p_213082_1_) -> {
         this.field_230706_i_.displayGuiScreen((Screen)null);
         int i = HTTPUtil.getSuitableLanPort();
         ITextComponent itextcomponent;
         if (this.field_230706_i_.getIntegratedServer().shareToLAN(GameType.getByName(this.gameMode), this.allowCheats, i)) {
            itextcomponent = new TranslationTextComponent("commands.publish.started", i);
         } else {
            itextcomponent = new TranslationTextComponent("commands.publish.failed");
         }

         this.field_230706_i_.ingameGUI.getChatGUI().printChatMessage(itextcomponent);
         this.field_230706_i_.func_230150_b_();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, this.field_230709_l_ - 28, 150, 20, DialogTexts.field_240633_d_, (p_213085_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.lastScreen);
      }));
      this.gameModeButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, 100, 150, 20, new TranslationTextComponent("selectWorld.gameMode"), (p_213084_1_) -> {
         if ("spectator".equals(this.gameMode)) {
            this.gameMode = "creative";
         } else if ("creative".equals(this.gameMode)) {
            this.gameMode = "adventure";
         } else if ("adventure".equals(this.gameMode)) {
            this.gameMode = "survival";
         } else {
            this.gameMode = "spectator";
         }

         this.updateDisplayNames();
      }));
      this.allowCheatsButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, 100, 150, 20, new TranslationTextComponent("selectWorld.allowCommands"), (p_213083_1_) -> {
         this.allowCheats = !this.allowCheats;
         this.updateDisplayNames();
      }));
      this.updateDisplayNames();
   }

   private void updateDisplayNames() {
      this.gameModeButton.func_238482_a_((new TranslationTextComponent("selectWorld.gameMode")).func_240702_b_(": ").func_230529_a_(new TranslationTextComponent("selectWorld.gameMode." + this.gameMode)));
      this.allowCheatsButton.func_238482_a_((new TranslationTextComponent("selectWorld.allowCommands")).func_240702_b_(" ").func_230529_a_(DialogTexts.func_240638_a_(this.allowCheats)));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 50, 16777215);
      this.func_238471_a_(p_230430_1_, this.field_230712_o_, I18n.format("lanServer.otherPlayers"), this.field_230708_k_ / 2, 82, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}