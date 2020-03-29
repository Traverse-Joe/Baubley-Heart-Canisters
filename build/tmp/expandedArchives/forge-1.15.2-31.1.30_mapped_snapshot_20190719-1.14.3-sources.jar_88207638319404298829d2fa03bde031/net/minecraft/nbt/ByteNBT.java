package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ByteNBT extends NumberNBT {
   public static final INBTType<ByteNBT> field_229668_a_ = new INBTType<ByteNBT>() {
      public ByteNBT func_225649_b_(DataInput p_225649_1_, int p_225649_2_, NBTSizeTracker p_225649_3_) throws IOException {
         p_225649_3_.read(72L);
         return ByteNBT.func_229671_a_(p_225649_1_.readByte());
      }

      public String func_225648_a_() {
         return "BYTE";
      }

      public String func_225650_b_() {
         return "TAG_Byte";
      }

      public boolean func_225651_c_() {
         return true;
      }
   };
   public static final ByteNBT field_229669_b_ = func_229671_a_((byte)0);
   public static final ByteNBT field_229670_c_ = func_229671_a_((byte)1);
   private final byte data;

   private ByteNBT(byte data) {
      this.data = data;
   }

   public static ByteNBT func_229671_a_(byte p_229671_0_) {
      return ByteNBT.Cache.field_229673_a_[128 + p_229671_0_];
   }

   public static ByteNBT func_229672_a_(boolean p_229672_0_) {
      return p_229672_0_ ? field_229670_c_ : field_229669_b_;
   }

   /**
    * Write the actual data contents of the tag, implemented in NBT extension classes
    */
   public void write(DataOutput output) throws IOException {
      output.writeByte(this.data);
   }

   /**
    * Gets the type byte for the tag.
    */
   public byte getId() {
      return 1;
   }

   public INBTType<ByteNBT> func_225647_b_() {
      return field_229668_a_;
   }

   public String toString() {
      return this.data + "b";
   }

   /**
    * Creates a clone of the tag.
    */
   public ByteNBT copy() {
      return this;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         return p_equals_1_ instanceof ByteNBT && this.data == ((ByteNBT)p_equals_1_).data;
      }
   }

   public int hashCode() {
      return this.data;
   }

   public ITextComponent toFormattedComponent(String indentation, int indentDepth) {
      ITextComponent itextcomponent = (new StringTextComponent("b")).applyTextStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      return (new StringTextComponent(String.valueOf((int)this.data))).appendSibling(itextcomponent).applyTextStyle(SYNTAX_HIGHLIGHTING_NUMBER);
   }

   public long getLong() {
      return (long)this.data;
   }

   public int getInt() {
      return this.data;
   }

   public short getShort() {
      return (short)this.data;
   }

   public byte getByte() {
      return this.data;
   }

   public double getDouble() {
      return (double)this.data;
   }

   public float getFloat() {
      return (float)this.data;
   }

   public Number getAsNumber() {
      return this.data;
   }

   static class Cache {
      private static final ByteNBT[] field_229673_a_ = new ByteNBT[256];

      static {
         for(int i = 0; i < field_229673_a_.length; ++i) {
            field_229673_a_[i] = new ByteNBT((byte)(i - 128));
         }

      }
   }
}