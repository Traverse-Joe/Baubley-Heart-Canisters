package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatRenderer extends MobRenderer<CatEntity, CatModel<CatEntity>> {
   public CatRenderer(EntityRendererManager p_i50973_1_) {
      super(p_i50973_1_, new CatModel<>(0.0F), 0.4F);
      this.addLayer(new CatCollarLayer(this));
   }

   public ResourceLocation getEntityTexture(CatEntity entity) {
      return entity.getCatTypeName();
   }

   protected void func_225620_a_(CatEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      super.func_225620_a_(p_225620_1_, p_225620_2_, p_225620_3_);
      p_225620_2_.func_227862_a_(0.8F, 0.8F, 0.8F);
   }

   protected void func_225621_a_(CatEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
      float f = p_225621_1_.func_213408_v(p_225621_5_);
      if (f > 0.0F) {
         p_225621_2_.func_227861_a_((double)(0.4F * f), (double)(0.15F * f), (double)(0.1F * f));
         p_225621_2_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(MathHelper.func_219805_h(f, 0.0F, 90.0F)));
         BlockPos blockpos = new BlockPos(p_225621_1_);

         for(PlayerEntity playerentity : p_225621_1_.world.getEntitiesWithinAABB(PlayerEntity.class, (new AxisAlignedBB(blockpos)).grow(2.0D, 2.0D, 2.0D))) {
            if (playerentity.isSleeping()) {
               p_225621_2_.func_227861_a_((double)(0.15F * f), 0.0D, 0.0D);
               break;
            }
         }
      }

   }
}