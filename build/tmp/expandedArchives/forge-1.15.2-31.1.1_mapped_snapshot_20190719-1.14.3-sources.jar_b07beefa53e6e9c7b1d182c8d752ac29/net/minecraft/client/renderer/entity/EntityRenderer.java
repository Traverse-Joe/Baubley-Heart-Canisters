package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class EntityRenderer<T extends Entity> {
   protected final EntityRendererManager renderManager;
   protected float shadowSize;
   protected float shadowOpaque = 1.0F;

   protected EntityRenderer(EntityRendererManager renderManager) {
      this.renderManager = renderManager;
   }

   public final int func_229100_c_(T p_229100_1_, float p_229100_2_) {
      return LightTexture.func_228451_a_(this.func_225624_a_(p_229100_1_, p_229100_2_), p_229100_1_.world.func_226658_a_(LightType.SKY, new BlockPos(p_229100_1_.getEyePosition(p_229100_2_))));
   }

   protected int func_225624_a_(T p_225624_1_, float p_225624_2_) {
      return p_225624_1_.isBurning() ? 15 : p_225624_1_.world.func_226658_a_(LightType.BLOCK, new BlockPos(p_225624_1_.getEyePosition(p_225624_2_)));
   }

   public boolean func_225626_a_(T p_225626_1_, ClippingHelperImpl p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
      if (!p_225626_1_.isInRangeToRender3d(p_225626_3_, p_225626_5_, p_225626_7_)) {
         return false;
      } else if (p_225626_1_.ignoreFrustumCheck) {
         return true;
      } else {
         AxisAlignedBB axisalignedbb = p_225626_1_.getRenderBoundingBox().grow(0.5D);
         if (axisalignedbb.hasNaN() || axisalignedbb.getAverageEdgeLength() == 0.0D) {
            axisalignedbb = new AxisAlignedBB(p_225626_1_.func_226277_ct_() - 2.0D, p_225626_1_.func_226278_cu_() - 2.0D, p_225626_1_.func_226281_cx_() - 2.0D, p_225626_1_.func_226277_ct_() + 2.0D, p_225626_1_.func_226278_cu_() + 2.0D, p_225626_1_.func_226281_cx_() + 2.0D);
         }

         return p_225626_2_.func_228957_a_(axisalignedbb);
      }
   }

   public Vec3d func_225627_b_(T p_225627_1_, float p_225627_2_) {
      return Vec3d.ZERO;
   }

   public void func_225623_a_(T p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(p_225623_1_,p_225623_1_.getDisplayName().getFormattedText(), p_225623_4_, p_225623_5_);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
      if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.canRenderName(p_225623_1_))) {
         this.func_225629_a_(p_225623_1_, renderNameplateEvent.getContent(), p_225623_4_, p_225623_5_, p_225623_6_);
      }
   }

   protected boolean canRenderName(T entity) {
      return entity.getAlwaysRenderNameTagForRender() && entity.hasCustomName();
   }

   public abstract ResourceLocation getEntityTexture(T entity);

   /**
    * Returns the font renderer from the set render manager
    */
   public FontRenderer getFontRendererFromRenderManager() {
      return this.renderManager.getFontRenderer();
   }

   protected void func_225629_a_(T p_225629_1_, String p_225629_2_, MatrixStack p_225629_3_, IRenderTypeBuffer p_225629_4_, int p_225629_5_) {
      double d0 = this.renderManager.func_229099_b_(p_225629_1_);
      if (!(d0 > 4096.0D)) {
         boolean flag = !p_225629_1_.func_226273_bm_();
         float f = p_225629_1_.getHeight() + 0.5F;
         int i = "deadmau5".equals(p_225629_2_) ? -10 : 0;
         p_225629_3_.func_227860_a_();
         p_225629_3_.func_227861_a_(0.0D, (double)f, 0.0D);
         p_225629_3_.func_227863_a_(this.renderManager.func_229098_b_());
         p_225629_3_.func_227862_a_(-0.025F, -0.025F, 0.025F);
         Matrix4f matrix4f = p_225629_3_.func_227866_c_().func_227870_a_();
         float f1 = Minecraft.getInstance().gameSettings.func_216840_a(0.25F);
         int j = (int)(f1 * 255.0F) << 24;
         FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
         float f2 = (float)(-fontrenderer.getStringWidth(p_225629_2_) / 2);
         fontrenderer.func_228079_a_(p_225629_2_, f2, (float)i, 553648127, false, matrix4f, p_225629_4_, flag, j, p_225629_5_);
         if (flag) {
            fontrenderer.func_228079_a_(p_225629_2_, f2, (float)i, -1, false, matrix4f, p_225629_4_, false, 0, p_225629_5_);
         }

         p_225629_3_.func_227865_b_();
      }
   }

   public EntityRendererManager getRenderManager() {
      return this.renderManager;
   }
}