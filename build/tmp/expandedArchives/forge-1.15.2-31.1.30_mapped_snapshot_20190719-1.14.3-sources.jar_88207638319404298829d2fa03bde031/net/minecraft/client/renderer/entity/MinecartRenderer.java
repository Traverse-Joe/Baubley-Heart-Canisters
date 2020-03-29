package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.MinecartModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MinecartRenderer<T extends AbstractMinecartEntity> extends EntityRenderer<T> {
   private static final ResourceLocation MINECART_TEXTURES = new ResourceLocation("textures/entity/minecart.png");
   protected final EntityModel<T> field_77013_a = new MinecartModel<>();

   public MinecartRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
      this.shadowSize = 0.7F;
   }

   public void func_225623_a_(T p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
      p_225623_4_.func_227860_a_();
      long i = (long)p_225623_1_.getEntityId() * 493286711L;
      i = i * i * 4392167121L + i * 98761L;
      float f = (((float)(i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float f1 = (((float)(i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float f2 = (((float)(i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      p_225623_4_.func_227861_a_((double)f, (double)f1, (double)f2);
      double d0 = MathHelper.lerp((double)p_225623_3_, p_225623_1_.lastTickPosX, p_225623_1_.func_226277_ct_());
      double d1 = MathHelper.lerp((double)p_225623_3_, p_225623_1_.lastTickPosY, p_225623_1_.func_226278_cu_());
      double d2 = MathHelper.lerp((double)p_225623_3_, p_225623_1_.lastTickPosZ, p_225623_1_.func_226281_cx_());
      double d3 = (double)0.3F;
      Vec3d vec3d = p_225623_1_.getPos(d0, d1, d2);
      float f3 = MathHelper.lerp(p_225623_3_, p_225623_1_.prevRotationPitch, p_225623_1_.rotationPitch);
      if (vec3d != null) {
         Vec3d vec3d1 = p_225623_1_.getPosOffset(d0, d1, d2, (double)0.3F);
         Vec3d vec3d2 = p_225623_1_.getPosOffset(d0, d1, d2, (double)-0.3F);
         if (vec3d1 == null) {
            vec3d1 = vec3d;
         }

         if (vec3d2 == null) {
            vec3d2 = vec3d;
         }

         p_225623_4_.func_227861_a_(vec3d.x - d0, (vec3d1.y + vec3d2.y) / 2.0D - d1, vec3d.z - d2);
         Vec3d vec3d3 = vec3d2.add(-vec3d1.x, -vec3d1.y, -vec3d1.z);
         if (vec3d3.length() != 0.0D) {
            vec3d3 = vec3d3.normalize();
            p_225623_2_ = (float)(Math.atan2(vec3d3.z, vec3d3.x) * 180.0D / Math.PI);
            f3 = (float)(Math.atan(vec3d3.y) * 73.0D);
         }
      }

      p_225623_4_.func_227861_a_(0.0D, 0.375D, 0.0D);
      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F - p_225623_2_));
      p_225623_4_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(-f3));
      float f5 = (float)p_225623_1_.getRollingAmplitude() - p_225623_3_;
      float f6 = p_225623_1_.getDamage() - p_225623_3_;
      if (f6 < 0.0F) {
         f6 = 0.0F;
      }

      if (f5 > 0.0F) {
         p_225623_4_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(MathHelper.sin(f5) * f5 * f6 / 10.0F * (float)p_225623_1_.getRollingDirection()));
      }

      int j = p_225623_1_.getDisplayTileOffset();
      BlockState blockstate = p_225623_1_.getDisplayTile();
      if (blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
         p_225623_4_.func_227860_a_();
         float f4 = 0.75F;
         p_225623_4_.func_227862_a_(0.75F, 0.75F, 0.75F);
         p_225623_4_.func_227861_a_(-0.5D, (double)((float)(j - 8) / 16.0F), 0.5D);
         p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(90.0F));
         this.func_225630_a_(p_225623_1_, p_225623_3_, blockstate, p_225623_4_, p_225623_5_, p_225623_6_);
         p_225623_4_.func_227865_b_();
      }

      p_225623_4_.func_227862_a_(-1.0F, -1.0F, 1.0F);
      this.field_77013_a.func_225597_a_(p_225623_1_, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
      IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(this.field_77013_a.func_228282_a_(this.getEntityTexture(p_225623_1_)));
      this.field_77013_a.func_225598_a_(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_225623_4_.func_227865_b_();
   }

   public ResourceLocation getEntityTexture(T entity) {
      return MINECART_TEXTURES;
   }

   protected void func_225630_a_(T p_225630_1_, float p_225630_2_, BlockState p_225630_3_, MatrixStack p_225630_4_, IRenderTypeBuffer p_225630_5_, int p_225630_6_) {
      Minecraft.getInstance().getBlockRendererDispatcher().func_228791_a_(p_225630_3_, p_225630_4_, p_225630_5_, p_225630_6_, OverlayTexture.field_229196_a_);
   }
}