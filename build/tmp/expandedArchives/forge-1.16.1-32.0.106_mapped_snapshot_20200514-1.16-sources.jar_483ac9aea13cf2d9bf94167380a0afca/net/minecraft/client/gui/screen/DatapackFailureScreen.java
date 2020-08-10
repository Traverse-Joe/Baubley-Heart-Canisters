package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DatapackFailureScreen extends Screen {
   private final List<ITextProperties> field_238619_a_ = Lists.newArrayList();
   private final Runnable field_238620_b_;

   public DatapackFailureScreen(Runnable p_i232276_1_) {
      super(new TranslationTextComponent("datapackFailure.title"));
      this.field_238620_b_ = p_i232276_1_;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.field_238619_a_.clear();
      this.field_238619_a_.addAll(this.field_230712_o_.func_238425_b_(this.func_231171_q_(), this.field_230708_k_ - 50));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ / 6 + 96, 150, 20, new TranslationTextComponent("datapackFailure.safeMode"), (p_238622_1_) -> {
         this.field_238620_b_.run();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + 160, this.field_230709_l_ / 6 + 96, 150, 20, new TranslationTextComponent("gui.toTitle"), (p_238621_1_) -> {
         this.field_230706_i_.displayGuiScreen((Screen)null);
      }));
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      int i = 70;

      for(ITextProperties itextproperties : this.field_238619_a_) {
         this.func_238472_a_(p_230430_1_, this.field_230712_o_, itextproperties, this.field_230708_k_ / 2, i, 16777215);
         i += 9;
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public boolean func_231178_ax__() {
      return false;
   }
}