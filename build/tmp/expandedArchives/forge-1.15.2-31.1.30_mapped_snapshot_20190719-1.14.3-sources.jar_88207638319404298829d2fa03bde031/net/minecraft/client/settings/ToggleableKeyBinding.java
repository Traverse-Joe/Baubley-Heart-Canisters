package net.minecraft.client.settings;

import java.util.function.BooleanSupplier;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ToggleableKeyBinding extends KeyBinding {
   private final BooleanSupplier field_228053_a_;

   public ToggleableKeyBinding(String p_i225917_1_, int p_i225917_2_, String p_i225917_3_, BooleanSupplier p_i225917_4_) {
      super(p_i225917_1_, InputMappings.Type.KEYSYM, p_i225917_2_, p_i225917_3_);
      this.field_228053_a_ = p_i225917_4_;
   }

   public void func_225593_a_(boolean p_225593_1_) {
      if (this.field_228053_a_.getAsBoolean()) {
         if (p_225593_1_) {
            super.func_225593_a_(!this.isKeyDown());
         }
      } else {
         super.func_225593_a_(p_225593_1_);
      }

   }
}