package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextPropertiesManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderComponentsUtil {
   private static final ITextProperties field_238502_a_ = ITextProperties.func_240652_a_(" ");

   private static String func_238504_a_(String p_238504_0_) {
      return Minecraft.getInstance().gameSettings.chatColor ? p_238504_0_ : TextFormatting.getTextWithoutFormattingCodes(p_238504_0_);
   }

   public static List<ITextProperties> func_238505_a_(ITextProperties p_238505_0_, int p_238505_1_, FontRenderer p_238505_2_) {
      TextPropertiesManager textpropertiesmanager = new TextPropertiesManager();
      p_238505_0_.func_230439_a_((p_238503_1_, p_238503_2_) -> {
         textpropertiesmanager.func_238155_a_(ITextProperties.func_240653_a_(func_238504_a_(p_238503_2_), p_238503_1_));
         return Optional.empty();
      }, Style.field_240709_b_);
      List<ITextProperties> list = p_238505_2_.func_238420_b_().func_241570_a_(textpropertiesmanager.func_238156_b_(), p_238505_1_, Style.field_240709_b_, field_238502_a_);
      return (List<ITextProperties>)(list.isEmpty() ? Lists.newArrayList(ITextProperties.field_240651_c_) : list);
   }
}