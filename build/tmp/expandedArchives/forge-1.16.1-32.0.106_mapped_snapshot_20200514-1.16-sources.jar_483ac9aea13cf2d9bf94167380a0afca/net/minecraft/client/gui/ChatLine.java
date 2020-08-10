package net.minecraft.client.gui;

import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChatLine {
   private final int updateCounterCreated;
   private final ITextProperties lineString;
   private final int chatLineID;

   public ChatLine(int p_i232239_1_, ITextProperties p_i232239_2_, int p_i232239_3_) {
      this.lineString = p_i232239_2_;
      this.updateCounterCreated = p_i232239_1_;
      this.chatLineID = p_i232239_3_;
   }

   public ITextProperties func_238169_a_() {
      return this.lineString;
   }

   public int getUpdatedCounter() {
      return this.updateCounterCreated;
   }

   public int getChatLineID() {
      return this.chatLineID;
   }
}