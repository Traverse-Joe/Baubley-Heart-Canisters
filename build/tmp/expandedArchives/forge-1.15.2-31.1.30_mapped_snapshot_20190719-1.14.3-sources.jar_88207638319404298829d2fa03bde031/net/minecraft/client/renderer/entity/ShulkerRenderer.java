package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.layers.ShulkerColorLayer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerRenderer extends MobRenderer<ShulkerEntity, ShulkerModel<ShulkerEntity>> {
   public static final ResourceLocation field_204402_a = new ResourceLocation("textures/" + Atlases.field_228748_g_.func_229313_b_().getPath() + ".png");
   public static final ResourceLocation[] SHULKER_ENDERGOLEM_TEXTURE = Atlases.field_228749_h_.stream().map((p_229125_0_) -> {
      return new ResourceLocation("textures/" + p_229125_0_.func_229313_b_().getPath() + ".png");
   }).toArray((p_229124_0_) -> {
      return new ResourceLocation[p_229124_0_];
   });

   public ShulkerRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new ShulkerModel<>(), 0.0F);
      this.addLayer(new ShulkerColorLayer(this));
   }

   public Vec3d func_225627_b_(ShulkerEntity p_225627_1_, float p_225627_2_) {
      int i = p_225627_1_.getClientTeleportInterp();
      if (i > 0 && p_225627_1_.isAttachedToBlock()) {
         BlockPos blockpos = p_225627_1_.getAttachmentPos();
         BlockPos blockpos1 = p_225627_1_.getOldAttachPos();
         double d0 = (double)((float)i - p_225627_2_) / 6.0D;
         d0 = d0 * d0;
         double d1 = (double)(blockpos.getX() - blockpos1.getX()) * d0;
         double d2 = (double)(blockpos.getY() - blockpos1.getY()) * d0;
         double d3 = (double)(blockpos.getZ() - blockpos1.getZ()) * d0;
         return new Vec3d(-d1, -d2, -d3);
      } else {
         return super.func_225627_b_(p_225627_1_, p_225627_2_);
      }
   }

   public boolean func_225626_a_(ShulkerEntity p_225626_1_, ClippingHelperImpl p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
      if (super.func_225626_a_(p_225626_1_, p_225626_2_, p_225626_3_, p_225626_5_, p_225626_7_)) {
         return true;
      } else {
         if (p_225626_1_.getClientTeleportInterp() > 0 && p_225626_1_.isAttachedToBlock()) {
            Vec3d vec3d = new Vec3d(p_225626_1_.getAttachmentPos());
            Vec3d vec3d1 = new Vec3d(p_225626_1_.getOldAttachPos());
            if (p_225626_2_.func_228957_a_(new AxisAlignedBB(vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y, vec3d.z))) {
               return true;
            }
         }

         return false;
      }
   }

   public ResourceLocation getEntityTexture(ShulkerEntity entity) {
      return entity.getColor() == null ? field_204402_a : SHULKER_ENDERGOLEM_TEXTURE[entity.getColor().getId()];
   }

   protected void func_225621_a_(ShulkerEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
      p_225621_2_.func_227861_a_(0.0D, 0.5D, 0.0D);
      p_225621_2_.func_227863_a_(p_225621_1_.getAttachmentFacing().getOpposite().func_229384_a_());
      p_225621_2_.func_227861_a_(0.0D, -0.5D, 0.0D);
   }

   protected void func_225620_a_(ShulkerEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      float f = 0.999F;
      p_225620_2_.func_227862_a_(0.999F, 0.999F, 0.999F);
   }
}