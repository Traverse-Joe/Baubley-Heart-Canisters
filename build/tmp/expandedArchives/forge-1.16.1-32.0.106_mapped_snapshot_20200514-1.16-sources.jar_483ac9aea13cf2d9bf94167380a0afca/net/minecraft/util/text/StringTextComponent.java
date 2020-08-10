package net.minecraft.util.text;

import javax.annotation.Nullable;

public class StringTextComponent extends TextComponent {
   public static final ITextComponent field_240750_d_ = new StringTextComponent("");
   private final String text;
   @Nullable
   private LanguageMap field_240751_f_;
   private String field_240752_g_;

   public StringTextComponent(String msg) {
      this.text = msg;
      this.field_240752_g_ = msg;
   }

   /**
    * Gets the text value of this component. This is used to access the {@link #text} property, and only should be used
    * when dealing specifically with instances of {@link TextComponentString} - for other purposes, use {@link
    * #getUnformattedComponentText()}.
    */
   public String getText() {
      return this.text;
   }

   /**
    * Gets the raw content of this component (but not its sibling components), without any formatting codes. For
    * example, this is the raw text in a {@link TextComponentString}, but it's the translated text for a {@link
    * TextComponentTranslation} and it's the score value for a {@link TextComponentScore}.
    */
   public String getUnformattedComponentText() {
      if (this.text.isEmpty()) {
         return this.text;
      } else {
         LanguageMap languagemap = LanguageMap.getInstance();
         if (this.field_240751_f_ != languagemap) {
            this.field_240752_g_ = languagemap.func_230504_a_(this.text, false);
            this.field_240751_f_ = languagemap;
         }

         return this.field_240752_g_;
      }
   }

   public StringTextComponent func_230531_f_() {
      return new StringTextComponent(this.text);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof StringTextComponent)) {
         return false;
      } else {
         StringTextComponent stringtextcomponent = (StringTextComponent)p_equals_1_;
         return this.text.equals(stringtextcomponent.getText()) && super.equals(p_equals_1_);
      }
   }

   public String toString() {
      return "TextComponent{text='" + this.text + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
   }
}