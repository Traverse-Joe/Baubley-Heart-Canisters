package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DragonFireballRenderer extends EntityRenderer<DragonFireballEntity> {
   private static final ResourceLocation DRAGON_FIREBALL_TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");
   private static final RenderType field_229044_e_ = RenderType.func_228640_c_(DRAGON_FIREBALL_TEXTURE);

   public DragonFireballRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
   }

   protected int func_225624_a_(DragonFireballEntity p_225624_1_, float p_225624_2_) {
      return 15;
   }

   public void func_225623_a_(DragonFireballEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      p_225623_4_.func_227860_a_();
      p_225623_4_.func_227862_a_(2.0F, 2.0F, 2.0F);
      p_225623_4_.func_227863_a_(this.renderManager.func_229098_b_());
      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F));
      MatrixStack.Entry matrixstack$entry = p_225623_4_.func_227866_c_();
      Matrix4f matrix4f = matrixstack$entry.func_227870_a_();
      Matrix3f matrix3f = matrixstack$entry.func_227872_b_();
      IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(field_229044_e_);
      func_229045_a_(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 0.0F, 0, 0, 1);
      func_229045_a_(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 1.0F, 0, 1, 1);
      func_229045_a_(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 1.0F, 1, 1, 0);
      func_229045_a_(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 0.0F, 1, 0, 0);
      p_225623_4_.func_227865_b_();
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
   }

   private static void func_229045_a_(IVertexBuilder p_229045_0_, Matrix4f p_229045_1_, Matrix3f p_229045_2_, int p_229045_3_, float p_229045_4_, int p_229045_5_, int p_229045_6_, int p_229045_7_) {
      p_229045_0_.func_227888_a_(p_229045_1_, p_229045_4_ - 0.5F, (float)p_229045_5_ - 0.25F, 0.0F).func_225586_a_(255, 255, 255, 255).func_225583_a_((float)p_229045_6_, (float)p_229045_7_).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(p_229045_3_).func_227887_a_(p_229045_2_, 0.0F, 1.0F, 0.0F).endVertex();
   }

   public ResourceLocation getEntityTexture(DragonFireballEntity entity) {
      return DRAGON_FIREBALL_TEXTURE;
   }
}