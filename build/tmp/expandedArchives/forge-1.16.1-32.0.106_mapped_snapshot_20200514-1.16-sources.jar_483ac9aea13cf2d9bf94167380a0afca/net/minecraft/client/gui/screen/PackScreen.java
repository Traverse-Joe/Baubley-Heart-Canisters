package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ResourcePackList;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public abstract class PackScreen extends Screen {
   private static final Logger field_238883_a_ = LogManager.getLogger();
   private static final ITextComponent field_238884_b_ = (new TranslationTextComponent("pack.dropInfo")).func_240699_a_(TextFormatting.DARK_GRAY);
   private static final ITextComponent field_238885_c_ = new TranslationTextComponent("pack.folderInfo");
   private final PackLoadingManager<?> field_238887_q_;
   private final Screen field_238888_r_;
   private boolean field_238890_t_;
   private ResourcePackList field_238891_u_;
   private ResourcePackList field_238892_v_;
   private final File field_241817_w_;
   private Button field_238894_x_;

   public PackScreen(Screen p_i241254_1_, TranslationTextComponent p_i241254_2_, Function<Runnable, PackLoadingManager<?>> p_i241254_3_, File p_i241254_4_) {
      super(p_i241254_2_);
      this.field_238888_r_ = p_i241254_1_;
      this.field_238887_q_ = p_i241254_3_.apply(this::func_238904_g_);
      this.field_241817_w_ = p_i241254_4_;
   }

   public void func_231164_f_() {
      if (this.field_238890_t_) {
         this.field_238890_t_ = false;
         this.field_238887_q_.func_241618_c_();
      }

   }

   public void func_231175_as__() {
      this.field_238890_t_ = true;
      this.field_230706_i_.displayGuiScreen(this.field_238888_r_);
   }

   protected void func_231160_c_() {
      this.field_238894_x_ = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, this.field_230709_l_ - 48, 150, 20, DialogTexts.field_240632_c_, (p_238903_1_) -> {
         this.func_231175_as__();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 154, this.field_230709_l_ - 48, 150, 20, new TranslationTextComponent("pack.openFolder"), (p_238896_1_) -> {
         Util.getOSType().openFile(this.field_241817_w_);
      }, (p_238897_1_, p_238897_2_, p_238897_3_, p_238897_4_) -> {
         this.func_238652_a_(p_238897_2_, field_238885_c_, p_238897_3_, p_238897_4_);
      }));
      this.field_238891_u_ = new ResourcePackList(this.field_230706_i_, 200, this.field_230709_l_, new TranslationTextComponent("pack.available.title"));
      this.field_238891_u_.func_230959_g_(this.field_230708_k_ / 2 - 4 - 200);
      this.field_230705_e_.add(this.field_238891_u_);
      this.field_238892_v_ = new ResourcePackList(this.field_230706_i_, 200, this.field_230709_l_, new TranslationTextComponent("pack.selected.title"));
      this.field_238892_v_.func_230959_g_(this.field_230708_k_ / 2 + 4);
      this.field_230705_e_.add(this.field_238892_v_);
      this.func_238904_g_();
   }

   private void func_238904_g_() {
      this.func_238899_a_(this.field_238892_v_, this.field_238887_q_.func_238869_b_());
      this.func_238899_a_(this.field_238891_u_, this.field_238887_q_.func_238865_a_());
      this.field_238894_x_.field_230693_o_ = !this.field_238892_v_.func_231039_at__().isEmpty();
   }

   private void func_238899_a_(ResourcePackList p_238899_1_, Stream<PackLoadingManager.IPack> p_238899_2_) {
      p_238899_1_.func_231039_at__().clear();
      p_238899_2_.filter(PackLoadingManager.IPack::notHidden).forEach((p_238898_2_) -> {
         p_238899_1_.func_231039_at__().add(new ResourcePackList.ResourcePackEntry(this.field_230706_i_, p_238899_1_, this, p_238898_2_));
      });
   }

   private void func_238906_l_() {
      this.field_238887_q_.func_241619_d_();
      this.func_238904_g_();
      this.field_238890_t_ = true;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_231165_f_(0);
      this.field_238891_u_.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.field_238892_v_.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 8, 16777215);
      this.func_238472_a_(p_230430_1_, this.field_230712_o_, field_238884_b_, this.field_230708_k_ / 2, 20, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   protected static void func_238895_a_(Minecraft p_238895_0_, List<Path> p_238895_1_, Path p_238895_2_) {
      MutableBoolean mutableboolean = new MutableBoolean();
      p_238895_1_.forEach((p_238901_2_) -> {
         try (Stream<Path> stream = Files.walk(p_238901_2_)) {
            stream.forEach((p_238900_3_) -> {
               try {
                  Util.func_240984_a_(p_238901_2_.getParent(), p_238895_2_, p_238900_3_);
               } catch (IOException ioexception1) {
                  field_238883_a_.warn("Failed to copy datapack file  from {} to {}", p_238900_3_, p_238895_2_, ioexception1);
                  mutableboolean.setTrue();
               }

            });
         } catch (IOException ioexception) {
            field_238883_a_.warn("Failed to copy datapack file from {} to {}", p_238901_2_, p_238895_2_);
            mutableboolean.setTrue();
         }

      });
      if (mutableboolean.isTrue()) {
         SystemToast.func_238539_c_(p_238895_0_, p_238895_2_.toString());
      }

   }

   public void func_230476_a_(List<Path> p_230476_1_) {
      String s = p_230476_1_.stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining(", "));
      this.field_230706_i_.displayGuiScreen(new ConfirmScreen((p_238902_2_) -> {
         if (p_238902_2_) {
            func_238895_a_(this.field_230706_i_, p_230476_1_, this.field_241817_w_.toPath());
            this.func_238906_l_();
         }

         this.field_230706_i_.displayGuiScreen(this);
      }, new TranslationTextComponent("pack.dropConfirm"), new StringTextComponent(s)));
   }
}