package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HorseArmorChestsModel<T extends AbstractChestedHorseEntity> extends HorseModel<T> {
   private final ModelRenderer field_199057_c = new ModelRenderer(this, 26, 21);
   private final ModelRenderer field_199058_d;

   public HorseArmorChestsModel(float p_i51068_1_) {
      super(p_i51068_1_);
      this.field_199057_c.func_228300_a_(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
      this.field_199058_d = new ModelRenderer(this, 26, 21);
      this.field_199058_d.func_228300_a_(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
      this.field_199057_c.rotateAngleY = (-(float)Math.PI / 2F);
      this.field_199058_d.rotateAngleY = ((float)Math.PI / 2F);
      this.field_199057_c.setRotationPoint(6.0F, -8.0F, 0.0F);
      this.field_199058_d.setRotationPoint(-6.0F, -8.0F, 0.0F);
      this.field_217127_a.addChild(this.field_199057_c);
      this.field_217127_a.addChild(this.field_199058_d);
   }

   protected void func_199047_a(ModelRenderer p_199047_1_) {
      ModelRenderer modelrenderer = new ModelRenderer(this, 0, 12);
      modelrenderer.func_228300_a_(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
      modelrenderer.setRotationPoint(1.25F, -10.0F, 4.0F);
      ModelRenderer modelrenderer1 = new ModelRenderer(this, 0, 12);
      modelrenderer1.func_228300_a_(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
      modelrenderer1.setRotationPoint(-1.25F, -10.0F, 4.0F);
      modelrenderer.rotateAngleX = 0.2617994F;
      modelrenderer.rotateAngleZ = 0.2617994F;
      modelrenderer1.rotateAngleX = 0.2617994F;
      modelrenderer1.rotateAngleZ = -0.2617994F;
      p_199047_1_.addChild(modelrenderer);
      p_199047_1_.addChild(modelrenderer1);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      super.func_225597_a_(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
      if (p_225597_1_.hasChest()) {
         this.field_199057_c.showModel = true;
         this.field_199058_d.showModel = true;
      } else {
         this.field_199057_c.showModel = false;
         this.field_199058_d.showModel = false;
      }

   }
}