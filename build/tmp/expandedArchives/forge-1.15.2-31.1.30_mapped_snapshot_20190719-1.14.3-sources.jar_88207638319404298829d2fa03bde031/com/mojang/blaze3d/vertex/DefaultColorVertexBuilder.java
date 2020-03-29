package com.mojang.blaze3d.vertex;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DefaultColorVertexBuilder implements IVertexBuilder {
   protected boolean field_227854_a_ = false;
   protected int field_227855_b_ = 255;
   protected int field_227856_c_ = 255;
   protected int field_227857_d_ = 255;
   protected int field_227858_e_ = 255;

   public void func_225611_b_(int p_225611_1_, int p_225611_2_, int p_225611_3_, int p_225611_4_) {
      this.field_227855_b_ = p_225611_1_;
      this.field_227856_c_ = p_225611_2_;
      this.field_227857_d_ = p_225611_3_;
      this.field_227858_e_ = p_225611_4_;
      this.field_227854_a_ = true;
   }
}