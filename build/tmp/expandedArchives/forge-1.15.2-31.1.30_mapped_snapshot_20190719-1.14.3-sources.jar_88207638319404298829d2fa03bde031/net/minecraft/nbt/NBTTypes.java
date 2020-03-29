package net.minecraft.nbt;

public class NBTTypes {
   private static final INBTType<?>[] field_229709_a_ = new INBTType[]{EndNBT.field_229685_a_, ByteNBT.field_229668_a_, ShortNBT.field_229700_a_, IntNBT.field_229691_a_, LongNBT.field_229697_a_, FloatNBT.field_229688_b_, DoubleNBT.field_229683_b_, ByteArrayNBT.field_229667_a_, StringNBT.field_229703_a_, ListNBT.field_229694_a_, CompoundNBT.field_229675_a_, IntArrayNBT.field_229690_a_, LongArrayNBT.field_229696_a_};

   public static INBTType<?> func_229710_a_(int p_229710_0_) {
      return p_229710_0_ >= 0 && p_229710_0_ < field_229709_a_.length ? field_229709_a_[p_229710_0_] : INBTType.func_229707_a_(p_229710_0_);
   }
}