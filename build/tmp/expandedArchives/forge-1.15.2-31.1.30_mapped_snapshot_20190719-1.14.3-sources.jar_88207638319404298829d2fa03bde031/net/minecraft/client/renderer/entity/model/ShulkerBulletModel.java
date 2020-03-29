package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerBulletModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer renderer;

   public ShulkerBulletModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.renderer = new ModelRenderer(this);
      this.renderer.setTextureOffset(0, 0).func_228301_a_(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F, 0.0F);
      this.renderer.setTextureOffset(0, 10).func_228301_a_(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F);
      this.renderer.setTextureOffset(20, 0).func_228301_a_(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F);
      this.renderer.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.renderer);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.renderer.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.renderer.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
   }
}