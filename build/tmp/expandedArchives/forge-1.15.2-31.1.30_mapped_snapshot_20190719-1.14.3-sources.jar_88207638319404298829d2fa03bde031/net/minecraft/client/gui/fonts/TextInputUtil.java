package net.minecraft.client.gui.fonts;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextInputUtil {
   private final Minecraft field_216900_a;
   private final FontRenderer field_216901_b;
   private final Supplier<String> field_216902_c;
   private final Consumer<String> field_216903_d;
   private final int field_216904_e;
   private int field_216905_f;
   private int field_216906_g;

   public TextInputUtil(Minecraft p_i51124_1_, Supplier<String> p_i51124_2_, Consumer<String> p_i51124_3_, int p_i51124_4_) {
      this.field_216900_a = p_i51124_1_;
      this.field_216901_b = p_i51124_1_.fontRenderer;
      this.field_216902_c = p_i51124_2_;
      this.field_216903_d = p_i51124_3_;
      this.field_216904_e = p_i51124_4_;
      this.func_216899_b();
   }

   public boolean func_216894_a(char p_216894_1_) {
      if (SharedConstants.isAllowedCharacter(p_216894_1_)) {
         this.func_216892_a(Character.toString(p_216894_1_));
      }

      return true;
   }

   private void func_216892_a(String p_216892_1_) {
      if (this.field_216906_g != this.field_216905_f) {
         this.func_216893_f();
      }

      String s = this.field_216902_c.get();
      this.field_216905_f = MathHelper.clamp(this.field_216905_f, 0, s.length());
      String s1 = (new StringBuilder(s)).insert(this.field_216905_f, p_216892_1_).toString();
      if (this.field_216901_b.getStringWidth(s1) <= this.field_216904_e) {
         this.field_216903_d.accept(s1);
         this.field_216906_g = this.field_216905_f = Math.min(s1.length(), this.field_216905_f + p_216892_1_.length());
      }

   }

   public boolean func_216897_a(int p_216897_1_) {
      String s = this.field_216902_c.get();
      if (Screen.isSelectAll(p_216897_1_)) {
         this.field_216906_g = 0;
         this.field_216905_f = s.length();
         return true;
      } else if (Screen.isCopy(p_216897_1_)) {
         this.field_216900_a.keyboardListener.setClipboardString(this.func_216895_e());
         return true;
      } else if (Screen.isPaste(p_216897_1_)) {
         this.func_216892_a(SharedConstants.filterAllowedCharacters(TextFormatting.getTextWithoutFormattingCodes(this.field_216900_a.keyboardListener.getClipboardString().replaceAll("\\r", ""))));
         this.field_216906_g = this.field_216905_f;
         return true;
      } else if (Screen.isCut(p_216897_1_)) {
         this.field_216900_a.keyboardListener.setClipboardString(this.func_216895_e());
         this.func_216893_f();
         return true;
      } else if (p_216897_1_ == 259) {
         if (!s.isEmpty()) {
            if (this.field_216906_g != this.field_216905_f) {
               this.func_216893_f();
            } else if (this.field_216905_f > 0) {
               s = (new StringBuilder(s)).deleteCharAt(Math.max(0, this.field_216905_f - 1)).toString();
               this.field_216906_g = this.field_216905_f = Math.max(0, this.field_216905_f - 1);
               this.field_216903_d.accept(s);
            }
         }

         return true;
      } else if (p_216897_1_ == 261) {
         if (!s.isEmpty()) {
            if (this.field_216906_g != this.field_216905_f) {
               this.func_216893_f();
            } else if (this.field_216905_f < s.length()) {
               s = (new StringBuilder(s)).deleteCharAt(Math.max(0, this.field_216905_f)).toString();
               this.field_216903_d.accept(s);
            }
         }

         return true;
      } else if (p_216897_1_ == 263) {
         int j = this.field_216901_b.getBidiFlag() ? 1 : -1;
         if (Screen.hasControlDown()) {
            this.field_216905_f = this.field_216901_b.func_216863_a(s, j, this.field_216905_f, true);
         } else {
            this.field_216905_f = Math.max(0, Math.min(s.length(), this.field_216905_f + j));
         }

         if (!Screen.hasShiftDown()) {
            this.field_216906_g = this.field_216905_f;
         }

         return true;
      } else if (p_216897_1_ == 262) {
         int i = this.field_216901_b.getBidiFlag() ? -1 : 1;
         if (Screen.hasControlDown()) {
            this.field_216905_f = this.field_216901_b.func_216863_a(s, i, this.field_216905_f, true);
         } else {
            this.field_216905_f = Math.max(0, Math.min(s.length(), this.field_216905_f + i));
         }

         if (!Screen.hasShiftDown()) {
            this.field_216906_g = this.field_216905_f;
         }

         return true;
      } else if (p_216897_1_ == 268) {
         this.field_216905_f = 0;
         if (!Screen.hasShiftDown()) {
            this.field_216906_g = this.field_216905_f;
         }

         return true;
      } else if (p_216897_1_ == 269) {
         this.field_216905_f = this.field_216902_c.get().length();
         if (!Screen.hasShiftDown()) {
            this.field_216906_g = this.field_216905_f;
         }

         return true;
      } else {
         return false;
      }
   }

   private String func_216895_e() {
      String s = this.field_216902_c.get();
      int i = Math.min(this.field_216905_f, this.field_216906_g);
      int j = Math.max(this.field_216905_f, this.field_216906_g);
      return s.substring(i, j);
   }

   private void func_216893_f() {
      if (this.field_216906_g != this.field_216905_f) {
         String s = this.field_216902_c.get();
         int i = Math.min(this.field_216905_f, this.field_216906_g);
         int j = Math.max(this.field_216905_f, this.field_216906_g);
         String s1 = s.substring(0, i) + s.substring(j);
         this.field_216905_f = i;
         this.field_216906_g = this.field_216905_f;
         this.field_216903_d.accept(s1);
      }
   }

   public void func_216899_b() {
      this.field_216906_g = this.field_216905_f = this.field_216902_c.get().length();
   }

   public int func_216896_c() {
      return this.field_216905_f;
   }

   public int func_216898_d() {
      return this.field_216906_g;
   }
}