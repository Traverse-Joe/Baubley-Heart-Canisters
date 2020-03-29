package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreeperModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer field_78135_a;
   private final ModelRenderer creeperArmor;
   private final ModelRenderer field_78134_c;
   private final ModelRenderer field_78131_d;
   private final ModelRenderer field_78132_e;
   private final ModelRenderer field_78129_f;
   private final ModelRenderer field_78130_g;

   public CreeperModel() {
      this(0.0F);
   }

   public CreeperModel(float p_i46366_1_) {
      int i = 6;
      this.field_78135_a = new ModelRenderer(this, 0, 0);
      this.field_78135_a.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46366_1_);
      this.field_78135_a.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.creeperArmor = new ModelRenderer(this, 32, 0);
      this.creeperArmor.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46366_1_ + 0.5F);
      this.creeperArmor.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.field_78134_c = new ModelRenderer(this, 16, 16);
      this.field_78134_c.func_228301_a_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_i46366_1_);
      this.field_78134_c.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.field_78131_d = new ModelRenderer(this, 0, 16);
      this.field_78131_d.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
      this.field_78131_d.setRotationPoint(-2.0F, 18.0F, 4.0F);
      this.field_78132_e = new ModelRenderer(this, 0, 16);
      this.field_78132_e.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
      this.field_78132_e.setRotationPoint(2.0F, 18.0F, 4.0F);
      this.field_78129_f = new ModelRenderer(this, 0, 16);
      this.field_78129_f.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
      this.field_78129_f.setRotationPoint(-2.0F, 18.0F, -4.0F);
      this.field_78130_g = new ModelRenderer(this, 0, 16);
      this.field_78130_g.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
      this.field_78130_g.setRotationPoint(2.0F, 18.0F, -4.0F);
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.field_78135_a, this.field_78134_c, this.field_78131_d, this.field_78132_e, this.field_78129_f, this.field_78130_g);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      this.field_78135_a.rotateAngleY = p_225597_5_ * ((float)Math.PI / 180F);
      this.field_78135_a.rotateAngleX = p_225597_6_ * ((float)Math.PI / 180F);
      this.field_78131_d.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
      this.field_78132_e.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
      this.field_78129_f.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_;
      this.field_78130_g.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
   }
}