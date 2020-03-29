package net.minecraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OverlayRenderer {
   private static final ResourceLocation field_228733_a_ = new ResourceLocation("textures/misc/underwater.png");

   public static void func_228734_a_(Minecraft p_228734_0_, MatrixStack p_228734_1_) {
      RenderSystem.disableAlphaTest();
      PlayerEntity playerentity = p_228734_0_.player;
      if (!playerentity.noClip) {
         org.apache.commons.lang3.tuple.Pair<BlockState, BlockPos> overlay = getOverlayBlock(playerentity);
         if (overlay != null) {
            if (!net.minecraftforge.event.ForgeEventFactory.renderBlockOverlay(playerentity, p_228734_1_, net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType.BLOCK, overlay.getLeft(), overlay.getRight()))
            func_228735_a_(p_228734_0_, p_228734_0_.getBlockRendererDispatcher().getBlockModelShapes().getTexture(overlay.getLeft(), p_228734_0_.world, overlay.getRight()), p_228734_1_);
         }
      }

      if (!p_228734_0_.player.isSpectator()) {
         if (p_228734_0_.player.areEyesInFluid(FluidTags.WATER)) {
            if (!net.minecraftforge.event.ForgeEventFactory.renderWaterOverlay(playerentity, p_228734_1_))
            func_228736_b_(p_228734_0_, p_228734_1_);
         }

         if (p_228734_0_.player.isBurning()) {
            if (!net.minecraftforge.event.ForgeEventFactory.renderFireOverlay(playerentity, p_228734_1_))
            func_228737_c_(p_228734_0_, p_228734_1_);
         }
      }

      RenderSystem.enableAlphaTest();
   }

   @Nullable
   private static BlockState func_230018_a_(PlayerEntity p_230018_0_) {
      return getOverlayBlock(p_230018_0_).getLeft();
   }

   @Nullable
   private static org.apache.commons.lang3.tuple.Pair<BlockState, BlockPos> getOverlayBlock(PlayerEntity p_230018_0_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int i = 0; i < 8; ++i) {
         double d0 = p_230018_0_.func_226277_ct_() + (double)(((float)((i >> 0) % 2) - 0.5F) * p_230018_0_.getWidth() * 0.8F);
         double d1 = p_230018_0_.func_226280_cw_() + (double)(((float)((i >> 1) % 2) - 0.5F) * 0.1F);
         double d2 = p_230018_0_.func_226281_cx_() + (double)(((float)((i >> 2) % 2) - 0.5F) * p_230018_0_.getWidth() * 0.8F);
         blockpos$mutable.setPos(d0, d1, d2);
         BlockState blockstate = p_230018_0_.world.getBlockState(blockpos$mutable);
         if (blockstate.getRenderType() != BlockRenderType.INVISIBLE && blockstate.causesSuffocation(p_230018_0_.world, blockpos$mutable)) {
            return org.apache.commons.lang3.tuple.Pair.of(blockstate, blockpos$mutable.toImmutable());
         }
      }

      return null;
   }

   private static void func_228735_a_(Minecraft p_228735_0_, TextureAtlasSprite p_228735_1_, MatrixStack p_228735_2_) {
      p_228735_0_.getTextureManager().bindTexture(p_228735_1_.func_229241_m_().func_229223_g_());
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      float f = 0.1F;
      float f1 = -1.0F;
      float f2 = 1.0F;
      float f3 = -1.0F;
      float f4 = 1.0F;
      float f5 = -0.5F;
      float f6 = p_228735_1_.getMinU();
      float f7 = p_228735_1_.getMaxU();
      float f8 = p_228735_1_.getMinV();
      float f9 = p_228735_1_.getMaxV();
      Matrix4f matrix4f = p_228735_2_.func_227866_c_().func_227870_a_();
      bufferbuilder.begin(7, DefaultVertexFormats.field_227851_o_);
      bufferbuilder.func_227888_a_(matrix4f, -1.0F, -1.0F, -0.5F).func_227885_a_(0.1F, 0.1F, 0.1F, 1.0F).func_225583_a_(f7, f9).endVertex();
      bufferbuilder.func_227888_a_(matrix4f, 1.0F, -1.0F, -0.5F).func_227885_a_(0.1F, 0.1F, 0.1F, 1.0F).func_225583_a_(f6, f9).endVertex();
      bufferbuilder.func_227888_a_(matrix4f, 1.0F, 1.0F, -0.5F).func_227885_a_(0.1F, 0.1F, 0.1F, 1.0F).func_225583_a_(f6, f8).endVertex();
      bufferbuilder.func_227888_a_(matrix4f, -1.0F, 1.0F, -0.5F).func_227885_a_(0.1F, 0.1F, 0.1F, 1.0F).func_225583_a_(f7, f8).endVertex();
      bufferbuilder.finishDrawing();
      WorldVertexBufferUploader.draw(bufferbuilder);
   }

   private static void func_228736_b_(Minecraft p_228736_0_, MatrixStack p_228736_1_) {
      p_228736_0_.getTextureManager().bindTexture(field_228733_a_);
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      float f = p_228736_0_.player.getBrightness();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      float f1 = 4.0F;
      float f2 = -1.0F;
      float f3 = 1.0F;
      float f4 = -1.0F;
      float f5 = 1.0F;
      float f6 = -0.5F;
      float f7 = -p_228736_0_.player.rotationYaw / 64.0F;
      float f8 = p_228736_0_.player.rotationPitch / 64.0F;
      Matrix4f matrix4f = p_228736_1_.func_227866_c_().func_227870_a_();
      bufferbuilder.begin(7, DefaultVertexFormats.field_227851_o_);
      bufferbuilder.func_227888_a_(matrix4f, -1.0F, -1.0F, -0.5F).func_227885_a_(f, f, f, 0.1F).func_225583_a_(4.0F + f7, 4.0F + f8).endVertex();
      bufferbuilder.func_227888_a_(matrix4f, 1.0F, -1.0F, -0.5F).func_227885_a_(f, f, f, 0.1F).func_225583_a_(0.0F + f7, 4.0F + f8).endVertex();
      bufferbuilder.func_227888_a_(matrix4f, 1.0F, 1.0F, -0.5F).func_227885_a_(f, f, f, 0.1F).func_225583_a_(0.0F + f7, 0.0F + f8).endVertex();
      bufferbuilder.func_227888_a_(matrix4f, -1.0F, 1.0F, -0.5F).func_227885_a_(f, f, f, 0.1F).func_225583_a_(4.0F + f7, 0.0F + f8).endVertex();
      bufferbuilder.finishDrawing();
      WorldVertexBufferUploader.draw(bufferbuilder);
      RenderSystem.disableBlend();
   }

   private static void func_228737_c_(Minecraft p_228737_0_, MatrixStack p_228737_1_) {
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      RenderSystem.depthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      TextureAtlasSprite textureatlassprite = ModelBakery.LOCATION_FIRE_1.func_229314_c_();
      p_228737_0_.getTextureManager().bindTexture(textureatlassprite.func_229241_m_().func_229223_g_());
      float f = textureatlassprite.getMinU();
      float f1 = textureatlassprite.getMaxU();
      float f2 = (f + f1) / 2.0F;
      float f3 = textureatlassprite.getMinV();
      float f4 = textureatlassprite.getMaxV();
      float f5 = (f3 + f4) / 2.0F;
      float f6 = textureatlassprite.func_229242_p_();
      float f7 = MathHelper.lerp(f6, f, f2);
      float f8 = MathHelper.lerp(f6, f1, f2);
      float f9 = MathHelper.lerp(f6, f3, f5);
      float f10 = MathHelper.lerp(f6, f4, f5);
      float f11 = 1.0F;

      for(int i = 0; i < 2; ++i) {
         p_228737_1_.func_227860_a_();
         float f12 = -0.5F;
         float f13 = 0.5F;
         float f14 = -0.5F;
         float f15 = 0.5F;
         float f16 = -0.5F;
         p_228737_1_.func_227861_a_((double)((float)(-(i * 2 - 1)) * 0.24F), (double)-0.3F, 0.0D);
         p_228737_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)(i * 2 - 1) * 10.0F));
         Matrix4f matrix4f = p_228737_1_.func_227866_c_().func_227870_a_();
         bufferbuilder.begin(7, DefaultVertexFormats.field_227851_o_);
         bufferbuilder.func_227888_a_(matrix4f, -0.5F, -0.5F, -0.5F).func_227885_a_(1.0F, 1.0F, 1.0F, 0.9F).func_225583_a_(f8, f10).endVertex();
         bufferbuilder.func_227888_a_(matrix4f, 0.5F, -0.5F, -0.5F).func_227885_a_(1.0F, 1.0F, 1.0F, 0.9F).func_225583_a_(f7, f10).endVertex();
         bufferbuilder.func_227888_a_(matrix4f, 0.5F, 0.5F, -0.5F).func_227885_a_(1.0F, 1.0F, 1.0F, 0.9F).func_225583_a_(f7, f9).endVertex();
         bufferbuilder.func_227888_a_(matrix4f, -0.5F, 0.5F, -0.5F).func_227885_a_(1.0F, 1.0F, 1.0F, 0.9F).func_225583_a_(f8, f9).endVertex();
         bufferbuilder.finishDrawing();
         WorldVertexBufferUploader.draw(bufferbuilder);
         p_228737_1_.func_227865_b_();
      }

      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.depthFunc(515);
   }
}