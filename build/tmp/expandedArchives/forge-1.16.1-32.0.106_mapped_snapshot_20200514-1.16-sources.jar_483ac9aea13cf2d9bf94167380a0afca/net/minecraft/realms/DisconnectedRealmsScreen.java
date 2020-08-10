package net.minecraft.realms;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisconnectedRealmsScreen extends RealmsScreen {
   private final String field_230713_a_;
   private final ITextComponent field_230714_b_;
   @Nullable
   private List<ITextProperties> field_230715_c_;
   private final Screen field_230716_p_;
   private int field_230717_q_;

   public DisconnectedRealmsScreen(Screen p_i232499_1_, String p_i232499_2_, ITextComponent p_i232499_3_) {
      this.field_230716_p_ = p_i232499_1_;
      this.field_230713_a_ = I18n.format(p_i232499_2_);
      this.field_230714_b_ = p_i232499_3_;
   }

   public void func_231160_c_() {
      Minecraft minecraft = Minecraft.getInstance();
      minecraft.setConnectedToRealms(false);
      minecraft.getPackFinder().clearResourcePack();
      RealmsNarratorHelper.func_239550_a_(this.field_230713_a_ + ": " + this.field_230714_b_.getString());
      this.field_230715_c_ = this.field_230712_o_.func_238425_b_(this.field_230714_b_, this.field_230708_k_ - 50);
      this.field_230717_q_ = this.field_230715_c_.size() * 9;
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 2 + this.field_230717_q_ / 2 + 9, 200, 20, DialogTexts.field_240637_h_, (p_239547_2_) -> {
         minecraft.displayGuiScreen(this.field_230716_p_);
      }));
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         Minecraft.getInstance().displayGuiScreen(this.field_230716_p_);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.func_238471_a_(p_230430_1_, this.field_230712_o_, this.field_230713_a_, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - this.field_230717_q_ / 2 - 9 * 2, 11184810);
      int i = this.field_230709_l_ / 2 - this.field_230717_q_ / 2;
      if (this.field_230715_c_ != null) {
         for(ITextProperties itextproperties : this.field_230715_c_) {
            this.func_238472_a_(p_230430_1_, this.field_230712_o_, itextproperties, this.field_230708_k_ / 2, i, 16777215);
            i += 9;
         }
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}