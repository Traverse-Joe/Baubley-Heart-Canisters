package net.minecraft.util;

public enum ActionResultType {
   SUCCESS,
   CONSUME,
   PASS,
   FAIL;

   public boolean func_226246_a_() {
      return this == SUCCESS || this == CONSUME;
   }

   public boolean func_226247_b_() {
      return this == SUCCESS;
   }
}