package net.minecraft.client.gui.chat;

import com.mojang.text2speech.Narrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class NarratorChatListener implements IChatListener {
   public static final ITextComponent field_216868_a = new StringTextComponent("");
   private static final Logger LOGGER = LogManager.getLogger();
   public static final NarratorChatListener INSTANCE = new NarratorChatListener();
   private final Narrator narrator = Narrator.getNarrator();

   /**
    * Called whenever this listener receives a chat message, if this listener is registered to the given type in {@link
    * net.minecraft.client.gui.GuiIngame#chatListeners chatListeners}
    */
   public void say(ChatType chatTypeIn, ITextComponent message) {
      NarratorStatus narratorstatus = func_223131_d();
      if (narratorstatus != NarratorStatus.OFF && this.narrator.active()) {
         if (narratorstatus == NarratorStatus.ALL || narratorstatus == NarratorStatus.CHAT && chatTypeIn == ChatType.CHAT || narratorstatus == NarratorStatus.SYSTEM && chatTypeIn == ChatType.SYSTEM) {
            ITextComponent itextcomponent;
            if (message instanceof TranslationTextComponent && "chat.type.text".equals(((TranslationTextComponent)message).getKey())) {
               itextcomponent = new TranslationTextComponent("chat.type.text.narrate", ((TranslationTextComponent)message).getFormatArgs());
            } else {
               itextcomponent = message;
            }

            this.func_216866_a(chatTypeIn.func_218690_b(), itextcomponent.getString());
         }

      }
   }

   public void func_216864_a(String p_216864_1_) {
      NarratorStatus narratorstatus = func_223131_d();
      if (this.narrator.active() && narratorstatus != NarratorStatus.OFF && narratorstatus != NarratorStatus.CHAT && !p_216864_1_.isEmpty()) {
         this.narrator.clear();
         this.func_216866_a(true, p_216864_1_);
      }

   }

   private static NarratorStatus func_223131_d() {
      return Minecraft.getInstance().gameSettings.narrator;
   }

   private void func_216866_a(boolean p_216866_1_, String p_216866_2_) {
      if (SharedConstants.developmentMode) {
         LOGGER.debug("Narrating: {}", (Object)p_216866_2_);
      }

      this.narrator.say(p_216866_2_, p_216866_1_);
   }

   public void func_216865_a(NarratorStatus p_216865_1_) {
      this.clear();
      this.narrator.say((new TranslationTextComponent("options.narrator")).getString() + " : " + (new TranslationTextComponent(p_216865_1_.func_216824_b())).getString(), true);
      ToastGui toastgui = Minecraft.getInstance().getToastGui();
      if (this.narrator.active()) {
         if (p_216865_1_ == NarratorStatus.OFF) {
            SystemToast.addOrUpdate(toastgui, SystemToast.Type.NARRATOR_TOGGLE, new TranslationTextComponent("narrator.toast.disabled"), (ITextComponent)null);
         } else {
            SystemToast.addOrUpdate(toastgui, SystemToast.Type.NARRATOR_TOGGLE, new TranslationTextComponent("narrator.toast.enabled"), new TranslationTextComponent(p_216865_1_.func_216824_b()));
         }
      } else {
         SystemToast.addOrUpdate(toastgui, SystemToast.Type.NARRATOR_TOGGLE, new TranslationTextComponent("narrator.toast.disabled"), new TranslationTextComponent("options.narrator.notavailable"));
      }

   }

   public boolean isActive() {
      return this.narrator.active();
   }

   public void clear() {
      if (func_223131_d() != NarratorStatus.OFF && this.narrator.active()) {
         this.narrator.clear();
      }
   }

   public void func_216867_c() {
      this.narrator.destroy();
   }
}