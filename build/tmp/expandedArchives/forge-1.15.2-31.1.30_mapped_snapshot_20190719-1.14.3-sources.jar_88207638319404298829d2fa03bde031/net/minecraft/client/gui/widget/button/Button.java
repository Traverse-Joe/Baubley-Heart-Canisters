package net.minecraft.client.gui.widget.button;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Button extends AbstractButton {
   protected final Button.IPressable onPress;

   public Button(int widthIn, int heightIn, int p_i51141_3_, int p_i51141_4_, String text, Button.IPressable onPress) {
      super(widthIn, heightIn, p_i51141_3_, p_i51141_4_, text);
      this.onPress = onPress;
   }

   public void onPress() {
      this.onPress.onPress(this);
   }

   @OnlyIn(Dist.CLIENT)
   public interface IPressable {
      void onPress(Button p_onPress_1_);
   }
}