package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeightMapDebugRenderer implements DebugRenderer.IDebugRenderer {
   private final Minecraft minecraft;

   public HeightMapDebugRenderer(Minecraft minecraftIn) {
      this.minecraft = minecraftIn;
   }

   public void func_225619_a_(MatrixStack p_225619_1_, IRenderTypeBuffer p_225619_2_, double p_225619_3_, double p_225619_5_, double p_225619_7_) {
      IWorld iworld = this.minecraft.world;
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      BlockPos blockpos = new BlockPos(p_225619_3_, 0.0D, p_225619_7_);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);

      for(BlockPos blockpos1 : BlockPos.getAllInBoxMutable(blockpos.add(-40, 0, -40), blockpos.add(40, 0, 40))) {
         int i = iworld.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
         if (iworld.getBlockState(blockpos1.add(0, i, 0).down()).isAir()) {
            WorldRenderer.addChainedFilledBoxVertices(bufferbuilder, (double)((float)blockpos1.getX() + 0.25F) - p_225619_3_, (double)i - p_225619_5_, (double)((float)blockpos1.getZ() + 0.25F) - p_225619_7_, (double)((float)blockpos1.getX() + 0.75F) - p_225619_3_, (double)i + 0.09375D - p_225619_5_, (double)((float)blockpos1.getZ() + 0.75F) - p_225619_7_, 0.0F, 0.0F, 1.0F, 0.5F);
         } else {
            WorldRenderer.addChainedFilledBoxVertices(bufferbuilder, (double)((float)blockpos1.getX() + 0.25F) - p_225619_3_, (double)i - p_225619_5_, (double)((float)blockpos1.getZ() + 0.25F) - p_225619_7_, (double)((float)blockpos1.getX() + 0.75F) - p_225619_3_, (double)i + 0.09375D - p_225619_5_, (double)((float)blockpos1.getZ() + 0.75F) - p_225619_7_, 0.0F, 1.0F, 0.0F, 0.5F);
         }
      }

      tessellator.draw();
      RenderSystem.enableTexture();
      RenderSystem.popMatrix();
   }
}