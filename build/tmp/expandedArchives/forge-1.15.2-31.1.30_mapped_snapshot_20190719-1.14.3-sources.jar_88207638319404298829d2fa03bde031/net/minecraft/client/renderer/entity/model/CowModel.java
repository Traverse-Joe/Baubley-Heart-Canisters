package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CowModel<T extends Entity> extends QuadrupedModel<T> {
   public CowModel() {
      super(12, 0.0F, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
      this.headModel = new ModelRenderer(this, 0, 0);
      this.headModel.func_228301_a_(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, 0.0F);
      this.headModel.setRotationPoint(0.0F, 4.0F, -8.0F);
      this.headModel.setTextureOffset(22, 0).func_228301_a_(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F);
      this.headModel.setTextureOffset(22, 0).func_228301_a_(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F);
      this.field_78148_b = new ModelRenderer(this, 18, 4);
      this.field_78148_b.func_228301_a_(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, 0.0F);
      this.field_78148_b.setRotationPoint(0.0F, 5.0F, 2.0F);
      this.field_78148_b.setTextureOffset(52, 0).func_228300_a_(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F);
      --this.field_78149_c.rotationPointX;
      ++this.field_78146_d.rotationPointX;
      this.field_78149_c.rotationPointZ += 0.0F;
      this.field_78146_d.rotationPointZ += 0.0F;
      --this.field_78147_e.rotationPointX;
      ++this.field_78144_f.rotationPointX;
      --this.field_78147_e.rotationPointZ;
      --this.field_78144_f.rotationPointZ;
   }

   public ModelRenderer getHead() {
      return this.headModel;
   }
}