package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class TintedAgeableModel<E extends Entity> extends AgeableModel<E> {
   private float field_228250_a_ = 1.0F;
   private float field_228251_b_ = 1.0F;
   private float field_228252_f_ = 1.0F;

   public void func_228253_a_(float p_228253_1_, float p_228253_2_, float p_228253_3_) {
      this.field_228250_a_ = p_228253_1_;
      this.field_228251_b_ = p_228253_2_;
      this.field_228252_f_ = p_228253_3_;
   }

   public void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
      super.func_225598_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, this.field_228250_a_ * p_225598_5_, this.field_228251_b_ * p_225598_6_, this.field_228252_f_ * p_225598_7_, p_225598_8_);
   }
}