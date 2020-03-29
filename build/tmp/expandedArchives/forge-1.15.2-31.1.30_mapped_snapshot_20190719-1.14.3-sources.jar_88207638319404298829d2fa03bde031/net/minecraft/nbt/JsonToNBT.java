package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.util.text.TranslationTextComponent;

public class JsonToNBT {
   public static final SimpleCommandExceptionType ERROR_TRAILING_DATA = new SimpleCommandExceptionType(new TranslationTextComponent("argument.nbt.trailing"));
   public static final SimpleCommandExceptionType ERROR_EXPECTED_KEY = new SimpleCommandExceptionType(new TranslationTextComponent("argument.nbt.expected.key"));
   public static final SimpleCommandExceptionType ERROR_EXPECTED_VALUE = new SimpleCommandExceptionType(new TranslationTextComponent("argument.nbt.expected.value"));
   public static final Dynamic2CommandExceptionType ERROR_INSERT_MIXED_LIST = new Dynamic2CommandExceptionType((p_208775_0_, p_208775_1_) -> {
      return new TranslationTextComponent("argument.nbt.list.mixed", p_208775_0_, p_208775_1_);
   });
   public static final Dynamic2CommandExceptionType ERROR_INSERT_MIXED_ARRAY = new Dynamic2CommandExceptionType((p_208774_0_, p_208774_1_) -> {
      return new TranslationTextComponent("argument.nbt.array.mixed", p_208774_0_, p_208774_1_);
   });
   public static final DynamicCommandExceptionType ERROR_INVALID_ARRAY = new DynamicCommandExceptionType((p_208773_0_) -> {
      return new TranslationTextComponent("argument.nbt.array.invalid", p_208773_0_);
   });
   private static final Pattern DOUBLE_PATTERN_NOSUFFIX = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
   private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
   private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
   private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
   private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
   private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
   private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
   private final StringReader reader;

   public static CompoundNBT getTagFromJson(String jsonString) throws CommandSyntaxException {
      return (new JsonToNBT(new StringReader(jsonString))).readSingleStruct();
   }

   @VisibleForTesting
   CompoundNBT readSingleStruct() throws CommandSyntaxException {
      CompoundNBT compoundnbt = this.readStruct();
      this.reader.skipWhitespace();
      if (this.reader.canRead()) {
         throw ERROR_TRAILING_DATA.createWithContext(this.reader);
      } else {
         return compoundnbt;
      }
   }

   public JsonToNBT(StringReader p_i47948_1_) {
      this.reader = p_i47948_1_;
   }

   protected String readKey() throws CommandSyntaxException {
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
      } else {
         return this.reader.readString();
      }
   }

   protected INBT readTypedValue() throws CommandSyntaxException {
      this.reader.skipWhitespace();
      int i = this.reader.getCursor();
      if (StringReader.isQuotedStringStart(this.reader.peek())) {
         return StringNBT.func_229705_a_(this.reader.readQuotedString());
      } else {
         String s = this.reader.readUnquotedString();
         if (s.isEmpty()) {
            this.reader.setCursor(i);
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
         } else {
            return this.type(s);
         }
      }
   }

   private INBT type(String stringIn) {
      try {
         if (FLOAT_PATTERN.matcher(stringIn).matches()) {
            return FloatNBT.func_229689_a_(Float.parseFloat(stringIn.substring(0, stringIn.length() - 1)));
         }

         if (BYTE_PATTERN.matcher(stringIn).matches()) {
            return ByteNBT.func_229671_a_(Byte.parseByte(stringIn.substring(0, stringIn.length() - 1)));
         }

         if (LONG_PATTERN.matcher(stringIn).matches()) {
            return LongNBT.func_229698_a_(Long.parseLong(stringIn.substring(0, stringIn.length() - 1)));
         }

         if (SHORT_PATTERN.matcher(stringIn).matches()) {
            return ShortNBT.func_229701_a_(Short.parseShort(stringIn.substring(0, stringIn.length() - 1)));
         }

         if (INT_PATTERN.matcher(stringIn).matches()) {
            return IntNBT.func_229692_a_(Integer.parseInt(stringIn));
         }

         if (DOUBLE_PATTERN.matcher(stringIn).matches()) {
            return DoubleNBT.func_229684_a_(Double.parseDouble(stringIn.substring(0, stringIn.length() - 1)));
         }

         if (DOUBLE_PATTERN_NOSUFFIX.matcher(stringIn).matches()) {
            return DoubleNBT.func_229684_a_(Double.parseDouble(stringIn));
         }

         if ("true".equalsIgnoreCase(stringIn)) {
            return ByteNBT.field_229670_c_;
         }

         if ("false".equalsIgnoreCase(stringIn)) {
            return ByteNBT.field_229669_b_;
         }
      } catch (NumberFormatException var3) {
         ;
      }

      return StringNBT.func_229705_a_(stringIn);
   }

   public INBT readValue() throws CommandSyntaxException {
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
      } else {
         char c0 = this.reader.peek();
         if (c0 == '{') {
            return this.readStruct();
         } else {
            return c0 == '[' ? this.readList() : this.readTypedValue();
         }
      }
   }

   protected INBT readList() throws CommandSyntaxException {
      return this.reader.canRead(3) && !StringReader.isQuotedStringStart(this.reader.peek(1)) && this.reader.peek(2) == ';' ? this.readArrayTag() : this.readListTag();
   }

   public CompoundNBT readStruct() throws CommandSyntaxException {
      this.expect('{');
      CompoundNBT compoundnbt = new CompoundNBT();
      this.reader.skipWhitespace();

      while(this.reader.canRead() && this.reader.peek() != '}') {
         int i = this.reader.getCursor();
         String s = this.readKey();
         if (s.isEmpty()) {
            this.reader.setCursor(i);
            throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
         }

         this.expect(':');
         compoundnbt.put(s, this.readValue());
         if (!this.hasElementSeparator()) {
            break;
         }

         if (!this.reader.canRead()) {
            throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
         }
      }

      this.expect('}');
      return compoundnbt;
   }

   private INBT readListTag() throws CommandSyntaxException {
      this.expect('[');
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
      } else {
         ListNBT listnbt = new ListNBT();
         INBTType<?> inbttype = null;

         while(this.reader.peek() != ']') {
            int i = this.reader.getCursor();
            INBT inbt = this.readValue();
            INBTType<?> inbttype1 = inbt.func_225647_b_();
            if (inbttype == null) {
               inbttype = inbttype1;
            } else if (inbttype1 != inbttype) {
               this.reader.setCursor(i);
               throw ERROR_INSERT_MIXED_LIST.createWithContext(this.reader, inbttype1.func_225650_b_(), inbttype.func_225650_b_());
            }

            listnbt.add(inbt);
            if (!this.hasElementSeparator()) {
               break;
            }

            if (!this.reader.canRead()) {
               throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
            }
         }

         this.expect(']');
         return listnbt;
      }
   }

   private INBT readArrayTag() throws CommandSyntaxException {
      this.expect('[');
      int i = this.reader.getCursor();
      char c0 = this.reader.read();
      this.reader.read();
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
      } else if (c0 == 'B') {
         return new ByteArrayNBT(this.func_229706_a_(ByteArrayNBT.field_229667_a_, ByteNBT.field_229668_a_));
      } else if (c0 == 'L') {
         return new LongArrayNBT(this.func_229706_a_(LongArrayNBT.field_229696_a_, LongNBT.field_229697_a_));
      } else if (c0 == 'I') {
         return new IntArrayNBT(this.func_229706_a_(IntArrayNBT.field_229690_a_, IntNBT.field_229691_a_));
      } else {
         this.reader.setCursor(i);
         throw ERROR_INVALID_ARRAY.createWithContext(this.reader, String.valueOf(c0));
      }
   }

   private <T extends Number> List<T> func_229706_a_(INBTType<?> p_229706_1_, INBTType<?> p_229706_2_) throws CommandSyntaxException {
      List<T> list = Lists.newArrayList();

      while(true) {
         if (this.reader.peek() != ']') {
            int i = this.reader.getCursor();
            INBT inbt = this.readValue();
            INBTType<?> inbttype = inbt.func_225647_b_();
            if (inbttype != p_229706_2_) {
               this.reader.setCursor(i);
               throw ERROR_INSERT_MIXED_ARRAY.createWithContext(this.reader, inbttype.func_225650_b_(), p_229706_1_.func_225650_b_());
            }

            if (p_229706_2_ == ByteNBT.field_229668_a_) {
               list.add((T)(Byte)((NumberNBT)inbt).getByte());
            } else if (p_229706_2_ == LongNBT.field_229697_a_) {
               list.add((T)(Long)((NumberNBT)inbt).getLong());
            } else {
               list.add((T)(Integer)((NumberNBT)inbt).getInt());
            }

            if (this.hasElementSeparator()) {
               if (!this.reader.canRead()) {
                  throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
               }
               continue;
            }
         }

         this.expect(']');
         return list;
      }
   }

   private boolean hasElementSeparator() {
      this.reader.skipWhitespace();
      if (this.reader.canRead() && this.reader.peek() == ',') {
         this.reader.skip();
         this.reader.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   private void expect(char expected) throws CommandSyntaxException {
      this.reader.skipWhitespace();
      this.reader.expect(expected);
   }
}