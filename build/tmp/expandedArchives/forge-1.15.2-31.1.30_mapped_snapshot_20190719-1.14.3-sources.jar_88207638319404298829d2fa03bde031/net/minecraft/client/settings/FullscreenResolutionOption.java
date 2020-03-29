package net.minecraft.client.settings;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.VideoMode;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FullscreenResolutionOption extends SliderPercentageOption {
   public FullscreenResolutionOption(MainWindow p_i51744_1_) {
      this(p_i51744_1_, p_i51744_1_.func_224796_s());
   }

   private FullscreenResolutionOption(MainWindow p_i51745_1_, @Nullable Monitor p_i51745_2_) {
      super("options.fullscreen.resolution", -1.0D, p_i51745_2_ != null ? (double)(p_i51745_2_.getVideoModeCount() - 1) : -1.0D, 1.0F, (p_225306_2_) -> {
         if (p_i51745_2_ == null) {
            return -1.0D;
         } else {
            Optional<VideoMode> optional = p_i51745_1_.getVideoMode();
            return optional.map((p_225304_1_) -> {
               return (double)p_i51745_2_.func_224794_a(p_225304_1_);
            }).orElse(-1.0D);
         }
      }, (p_225303_2_, p_225303_3_) -> {
         if (p_i51745_2_ != null) {
            if (p_225303_3_ == -1.0D) {
               p_i51745_1_.func_224797_a(Optional.empty());
            } else {
               p_i51745_1_.func_224797_a(Optional.of(p_i51745_2_.getVideoModeFromIndex(p_225303_3_.intValue())));
            }

         }
      }, (p_225305_1_, p_225305_2_) -> {
         if (p_i51745_2_ == null) {
            return I18n.format("options.fullscreen.unavailable");
         } else {
            double d0 = p_225305_2_.get(p_225305_1_);
            String s = p_225305_2_.getDisplayString();
            return d0 == -1.0D ? s + I18n.format("options.fullscreen.current") : p_i51745_2_.getVideoModeFromIndex((int)d0).toString();
         }
      });
   }
}