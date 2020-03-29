package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.CodModel;
import net.minecraft.entity.passive.fish.CodEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CodRenderer extends MobRenderer<CodEntity, CodModel<CodEntity>> {
   private static final ResourceLocation COD_LOCATION = new ResourceLocation("textures/entity/fish/cod.png");

   public CodRenderer(EntityRendererManager p_i48864_1_) {
      super(p_i48864_1_, new CodModel<>(), 0.3F);
   }

   public ResourceLocation getEntityTexture(CodEntity entity) {
      return COD_LOCATION;
   }

   protected void func_225621_a_(CodEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
      float f = 4.3F * MathHelper.sin(0.6F * p_225621_3_);
      p_225621_2_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f));
      if (!p_225621_1_.isInWater()) {
         p_225621_2_.func_227861_a_((double)0.1F, (double)0.1F, (double)-0.1F);
         p_225621_2_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
      }

   }
}