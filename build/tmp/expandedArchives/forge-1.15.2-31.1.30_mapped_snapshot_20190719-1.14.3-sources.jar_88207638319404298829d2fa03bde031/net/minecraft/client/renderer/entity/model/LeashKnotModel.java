package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LeashKnotModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer knotRenderer;

   public LeashKnotModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.knotRenderer = new ModelRenderer(this, 0, 0);
      this.knotRenderer.func_228301_a_(-3.0F, -6.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F);
      this.knotRenderer.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.knotRenderer);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.knotRenderer.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.knotRenderer.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
   }
}