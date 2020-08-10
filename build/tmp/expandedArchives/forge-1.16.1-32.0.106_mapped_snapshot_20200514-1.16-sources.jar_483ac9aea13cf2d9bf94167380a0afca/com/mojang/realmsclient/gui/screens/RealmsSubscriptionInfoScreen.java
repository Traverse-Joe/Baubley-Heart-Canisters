package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.exception.RealmsServiceException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsSubscriptionInfoScreen extends RealmsScreen {
   private static final Logger field_224579_a = LogManager.getLogger();
   private final Screen field_224580_b;
   private final RealmsServer field_224581_c;
   private final Screen field_224582_d;
   private final String field_224586_h;
   private final String field_224587_i;
   private final String field_224588_j;
   private final String field_224589_k;
   private int field_224590_l;
   private String field_224591_m;
   private Subscription.Type field_224592_n;

   public RealmsSubscriptionInfoScreen(Screen p_i232223_1_, RealmsServer p_i232223_2_, Screen p_i232223_3_) {
      this.field_224580_b = p_i232223_1_;
      this.field_224581_c = p_i232223_2_;
      this.field_224582_d = p_i232223_3_;
      this.field_224586_h = I18n.format("mco.configure.world.subscription.title");
      this.field_224587_i = I18n.format("mco.configure.world.subscription.start");
      this.field_224588_j = I18n.format("mco.configure.world.subscription.timeleft");
      this.field_224589_k = I18n.format("mco.configure.world.subscription.recurring.daysleft");
   }

   public void func_231160_c_() {
      this.func_224573_a(this.field_224581_c.field_230582_a_);
      RealmsNarratorHelper.func_239551_a_(this.field_224586_h, this.field_224587_i, this.field_224591_m, this.field_224588_j, this.func_224576_a(this.field_224590_l));
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, func_239562_k_(6), 200, 20, new TranslationTextComponent("mco.configure.world.subscription.extend"), (p_238073_1_) -> {
         String s = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + this.field_224581_c.field_230583_b_ + "&profileId=" + this.field_230706_i_.getSession().getPlayerID();
         this.field_230706_i_.keyboardListener.setClipboardString(s);
         Util.getOSType().openURI(s);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, func_239562_k_(12), 200, 20, DialogTexts.field_240637_h_, (p_238071_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224580_b);
      }));
      if (this.field_224581_c.field_230591_j_) {
         this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, func_239562_k_(10), 200, 20, new TranslationTextComponent("mco.configure.world.delete.button"), (p_238069_1_) -> {
            ITextComponent itextcomponent = new TranslationTextComponent("mco.configure.world.delete.question.line1");
            ITextComponent itextcomponent1 = new TranslationTextComponent("mco.configure.world.delete.question.line2");
            this.field_230706_i_.displayGuiScreen(new RealmsLongConfirmationScreen(this::func_238074_c_, RealmsLongConfirmationScreen.Type.Warning, itextcomponent, itextcomponent1, true));
         }));
      }

   }

   private void func_238074_c_(boolean p_238074_1_) {
      if (p_238074_1_) {
         (new Thread("Realms-delete-realm") {
            public void run() {
               try {
                  RealmsClient realmsclient = RealmsClient.func_224911_a();
                  realmsclient.func_224916_h(RealmsSubscriptionInfoScreen.this.field_224581_c.field_230582_a_);
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsSubscriptionInfoScreen.field_224579_a.error("Couldn't delete world");
                  RealmsSubscriptionInfoScreen.field_224579_a.error(realmsserviceexception);
               }

               RealmsSubscriptionInfoScreen.this.field_230706_i_.execute(() -> {
                  RealmsSubscriptionInfoScreen.this.field_230706_i_.displayGuiScreen(RealmsSubscriptionInfoScreen.this.field_224582_d);
               });
            }
         }).start();
      }

      this.field_230706_i_.displayGuiScreen(this);
   }

   private void func_224573_a(long p_224573_1_) {
      RealmsClient realmsclient = RealmsClient.func_224911_a();

      try {
         Subscription subscription = realmsclient.func_224933_g(p_224573_1_);
         this.field_224590_l = subscription.field_230635_b_;
         this.field_224591_m = this.func_224574_b(subscription.field_230634_a_);
         this.field_224592_n = subscription.field_230636_c_;
      } catch (RealmsServiceException realmsserviceexception) {
         field_224579_a.error("Couldn't get subscription");
         this.field_230706_i_.displayGuiScreen(new RealmsGenericErrorScreen(realmsserviceexception, this.field_224580_b));
      }

   }

   private String func_224574_b(long p_224574_1_) {
      Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
      calendar.setTimeInMillis(p_224574_1_);
      return DateFormat.getDateTimeInstance().format(calendar.getTime());
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224580_b);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      int i = this.field_230708_k_ / 2 - 100;
      this.func_238471_a_(p_230430_1_, this.field_230712_o_, this.field_224586_h, this.field_230708_k_ / 2, 17, 16777215);
      this.field_230712_o_.func_238421_b_(p_230430_1_, this.field_224587_i, (float)i, (float)func_239562_k_(0), 10526880);
      this.field_230712_o_.func_238421_b_(p_230430_1_, this.field_224591_m, (float)i, (float)func_239562_k_(1), 16777215);
      if (this.field_224592_n == Subscription.Type.NORMAL) {
         this.field_230712_o_.func_238421_b_(p_230430_1_, this.field_224588_j, (float)i, (float)func_239562_k_(3), 10526880);
      } else if (this.field_224592_n == Subscription.Type.RECURRING) {
         this.field_230712_o_.func_238421_b_(p_230430_1_, this.field_224589_k, (float)i, (float)func_239562_k_(3), 10526880);
      }

      this.field_230712_o_.func_238421_b_(p_230430_1_, this.func_224576_a(this.field_224590_l), (float)i, (float)func_239562_k_(4), 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   private String func_224576_a(int p_224576_1_) {
      if (p_224576_1_ == -1 && this.field_224581_c.field_230591_j_) {
         return I18n.format("mco.configure.world.subscription.expired");
      } else if (p_224576_1_ <= 1) {
         return I18n.format("mco.configure.world.subscription.less_than_a_day");
      } else {
         int i = p_224576_1_ / 30;
         int j = p_224576_1_ % 30;
         StringBuilder stringbuilder = new StringBuilder();
         if (i > 0) {
            stringbuilder.append(i).append(" ");
            if (i == 1) {
               stringbuilder.append(I18n.format("mco.configure.world.subscription.month").toLowerCase(Locale.ROOT));
            } else {
               stringbuilder.append(I18n.format("mco.configure.world.subscription.months").toLowerCase(Locale.ROOT));
            }
         }

         if (j > 0) {
            if (stringbuilder.length() > 0) {
               stringbuilder.append(", ");
            }

            stringbuilder.append(j).append(" ");
            if (j == 1) {
               stringbuilder.append(I18n.format("mco.configure.world.subscription.day").toLowerCase(Locale.ROOT));
            } else {
               stringbuilder.append(I18n.format("mco.configure.world.subscription.days").toLowerCase(Locale.ROOT));
            }
         }

         return stringbuilder.toString();
      }
   }
}