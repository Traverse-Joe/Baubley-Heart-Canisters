package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.client.renderer.entity.model.AbstractTropicalFishModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.TropicalFishAModel;
import net.minecraft.client.renderer.entity.model.TropicalFishBModel;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TropicalFishRenderer extends MobRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
   private final TropicalFishAModel<TropicalFishEntity> modelB = new TropicalFishAModel<>(0.0F);
   private final TropicalFishBModel<TropicalFishEntity> modelA = new TropicalFishBModel<>(0.0F);

   public TropicalFishRenderer(EntityRendererManager p_i48889_1_) {
      super(p_i48889_1_, new TropicalFishAModel<>(0.0F), 0.15F);
      this.addLayer(new TropicalFishPatternLayer(this));
   }

   public ResourceLocation getEntityTexture(TropicalFishEntity entity) {
      return entity.getBodyTexture();
   }

   public void func_225623_a_(TropicalFishEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      AbstractTropicalFishModel<TropicalFishEntity> abstracttropicalfishmodel = (AbstractTropicalFishModel<TropicalFishEntity>)(p_225623_1_.getSize() == 0 ? this.modelB : this.modelA);
      this.entityModel = abstracttropicalfishmodel;
      float[] afloat = p_225623_1_.func_204219_dC();
      abstracttropicalfishmodel.func_228257_a_(afloat[0], afloat[1], afloat[2]);
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
      abstracttropicalfishmodel.func_228257_a_(1.0F, 1.0F, 1.0F);
   }

   protected void func_225621_a_(TropicalFishEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
      float f = 4.3F * MathHelper.sin(0.6F * p_225621_3_);
      p_225621_2_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f));
      if (!p_225621_1_.isInWater()) {
         p_225621_2_.func_227861_a_((double)0.2F, (double)0.1F, 0.0D);
         p_225621_2_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
      }

   }
}