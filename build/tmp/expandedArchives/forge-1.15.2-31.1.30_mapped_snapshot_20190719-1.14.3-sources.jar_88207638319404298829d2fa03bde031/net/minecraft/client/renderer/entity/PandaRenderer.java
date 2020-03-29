package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Map;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.layers.PandaHeldItemLayer;
import net.minecraft.client.renderer.entity.model.PandaModel;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PandaRenderer extends MobRenderer<PandaEntity, PandaModel<PandaEntity>> {
   private static final Map<PandaEntity.Type, ResourceLocation> field_217777_a = Util.make(Maps.newEnumMap(PandaEntity.Type.class), (p_217776_0_) -> {
      p_217776_0_.put(PandaEntity.Type.NORMAL, new ResourceLocation("textures/entity/panda/panda.png"));
      p_217776_0_.put(PandaEntity.Type.LAZY, new ResourceLocation("textures/entity/panda/lazy_panda.png"));
      p_217776_0_.put(PandaEntity.Type.WORRIED, new ResourceLocation("textures/entity/panda/worried_panda.png"));
      p_217776_0_.put(PandaEntity.Type.PLAYFUL, new ResourceLocation("textures/entity/panda/playful_panda.png"));
      p_217776_0_.put(PandaEntity.Type.BROWN, new ResourceLocation("textures/entity/panda/brown_panda.png"));
      p_217776_0_.put(PandaEntity.Type.WEAK, new ResourceLocation("textures/entity/panda/weak_panda.png"));
      p_217776_0_.put(PandaEntity.Type.AGGRESSIVE, new ResourceLocation("textures/entity/panda/aggressive_panda.png"));
   });

   public PandaRenderer(EntityRendererManager p_i50960_1_) {
      super(p_i50960_1_, new PandaModel<>(9, 0.0F), 0.9F);
      this.addLayer(new PandaHeldItemLayer(this));
   }

   public ResourceLocation getEntityTexture(PandaEntity entity) {
      return field_217777_a.getOrDefault(entity.func_213590_ei(), field_217777_a.get(PandaEntity.Type.NORMAL));
   }

   protected void func_225621_a_(PandaEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
      if (p_225621_1_.field_213608_bz > 0) {
         int i = p_225621_1_.field_213608_bz;
         int j = i + 1;
         float f = 7.0F;
         float f1 = p_225621_1_.isChild() ? 0.3F : 0.8F;
         if (i < 8) {
            float f3 = (float)(90 * i) / 7.0F;
            float f4 = (float)(90 * j) / 7.0F;
            float f2 = this.func_217775_a(f3, f4, j, p_225621_5_, 8.0F);
            p_225621_2_.func_227861_a_(0.0D, (double)((f1 + 0.2F) * (f2 / 90.0F)), 0.0D);
            p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-f2));
         } else if (i < 16) {
            float f13 = ((float)i - 8.0F) / 7.0F;
            float f16 = 90.0F + 90.0F * f13;
            float f5 = 90.0F + 90.0F * ((float)j - 8.0F) / 7.0F;
            float f10 = this.func_217775_a(f16, f5, j, p_225621_5_, 16.0F);
            p_225621_2_.func_227861_a_(0.0D, (double)(f1 + 0.2F + (f1 - 0.2F) * (f10 - 90.0F) / 90.0F), 0.0D);
            p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-f10));
         } else if ((float)i < 24.0F) {
            float f14 = ((float)i - 16.0F) / 7.0F;
            float f17 = 180.0F + 90.0F * f14;
            float f19 = 180.0F + 90.0F * ((float)j - 16.0F) / 7.0F;
            float f11 = this.func_217775_a(f17, f19, j, p_225621_5_, 24.0F);
            p_225621_2_.func_227861_a_(0.0D, (double)(f1 + f1 * (270.0F - f11) / 90.0F), 0.0D);
            p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-f11));
         } else if (i < 32) {
            float f15 = ((float)i - 24.0F) / 7.0F;
            float f18 = 270.0F + 90.0F * f15;
            float f20 = 270.0F + 90.0F * ((float)j - 24.0F) / 7.0F;
            float f12 = this.func_217775_a(f18, f20, j, p_225621_5_, 32.0F);
            p_225621_2_.func_227861_a_(0.0D, (double)(f1 * ((360.0F - f12) / 90.0F)), 0.0D);
            p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-f12));
         }
      }

      float f6 = p_225621_1_.func_213561_v(p_225621_5_);
      if (f6 > 0.0F) {
         p_225621_2_.func_227861_a_(0.0D, (double)(0.8F * f6), 0.0D);
         p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(MathHelper.lerp(f6, p_225621_1_.rotationPitch, p_225621_1_.rotationPitch + 90.0F)));
         p_225621_2_.func_227861_a_(0.0D, (double)(-1.0F * f6), 0.0D);
         if (p_225621_1_.func_213566_eo()) {
            float f7 = (float)(Math.cos((double)p_225621_1_.ticksExisted * 1.25D) * Math.PI * (double)0.05F);
            p_225621_2_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f7));
            if (p_225621_1_.isChild()) {
               p_225621_2_.func_227861_a_(0.0D, (double)0.8F, (double)0.55F);
            }
         }
      }

      float f8 = p_225621_1_.func_213583_w(p_225621_5_);
      if (f8 > 0.0F) {
         float f9 = p_225621_1_.isChild() ? 0.5F : 1.3F;
         p_225621_2_.func_227861_a_(0.0D, (double)(f9 * f8), 0.0D);
         p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(MathHelper.lerp(f8, p_225621_1_.rotationPitch, p_225621_1_.rotationPitch + 180.0F)));
      }

   }

   private float func_217775_a(float p_217775_1_, float p_217775_2_, int p_217775_3_, float p_217775_4_, float p_217775_5_) {
      return (float)p_217775_3_ < p_217775_5_ ? MathHelper.lerp(p_217775_4_, p_217775_1_, p_217775_2_) : p_217775_1_;
   }
}