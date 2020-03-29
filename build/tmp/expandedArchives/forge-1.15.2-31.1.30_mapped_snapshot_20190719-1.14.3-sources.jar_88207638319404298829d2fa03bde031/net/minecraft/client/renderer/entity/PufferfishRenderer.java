package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PufferFishBigModel;
import net.minecraft.client.renderer.entity.model.PufferFishMediumModel;
import net.minecraft.client.renderer.entity.model.PufferFishSmallModel;
import net.minecraft.entity.passive.fish.PufferfishEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PufferfishRenderer extends MobRenderer<PufferfishEntity, EntityModel<PufferfishEntity>> {
   private static final ResourceLocation field_203771_a = new ResourceLocation("textures/entity/fish/pufferfish.png");
   private int field_203772_j;
   private final PufferFishSmallModel<PufferfishEntity> field_203773_k = new PufferFishSmallModel<>();
   private final PufferFishMediumModel<PufferfishEntity> field_203774_l = new PufferFishMediumModel<>();
   private final PufferFishBigModel<PufferfishEntity> field_203775_m = new PufferFishBigModel<>();

   public PufferfishRenderer(EntityRendererManager p_i48863_1_) {
      super(p_i48863_1_, new PufferFishBigModel<>(), 0.2F);
      this.field_203772_j = 3;
   }

   public ResourceLocation getEntityTexture(PufferfishEntity entity) {
      return field_203771_a;
   }

   public void func_225623_a_(PufferfishEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      int i = p_225623_1_.getPuffState();
      if (i != this.field_203772_j) {
         if (i == 0) {
            this.entityModel = this.field_203773_k;
         } else if (i == 1) {
            this.entityModel = this.field_203774_l;
         } else {
            this.entityModel = this.field_203775_m;
         }
      }

      this.field_203772_j = i;
      this.shadowSize = 0.1F + 0.1F * (float)i;
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
   }

   protected void func_225621_a_(PufferfishEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      p_225621_2_.func_227861_a_(0.0D, (double)(MathHelper.cos(p_225621_3_ * 0.05F) * 0.08F), 0.0D);
      super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
   }
}