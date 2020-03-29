package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractTropicalFishModel<E extends Entity> extends SegmentedModel<E> {
   private float field_228254_a_ = 1.0F;
   private float field_228255_b_ = 1.0F;
   private float field_228256_f_ = 1.0F;

   public void func_228257_a_(float p_228257_1_, float p_228257_2_, float p_228257_3_) {
      this.field_228254_a_ = p_228257_1_;
      this.field_228255_b_ = p_228257_2_;
      this.field_228256_f_ = p_228257_3_;
   }

   public void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
      super.func_225598_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, this.field_228254_a_ * p_225598_5_, this.field_228255_b_ * p_225598_6_, this.field_228256_f_ * p_225598_7_, p_225598_8_);
   }
}