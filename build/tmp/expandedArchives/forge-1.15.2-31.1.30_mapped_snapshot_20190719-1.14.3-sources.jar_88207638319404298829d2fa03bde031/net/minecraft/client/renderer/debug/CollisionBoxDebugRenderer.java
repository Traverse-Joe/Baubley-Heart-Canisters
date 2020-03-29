package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CollisionBoxDebugRenderer implements DebugRenderer.IDebugRenderer {
   private final Minecraft minecraft;
   private double lastUpdate = Double.MIN_VALUE;
   private List<VoxelShape> collisionData = Collections.emptyList();

   public CollisionBoxDebugRenderer(Minecraft minecraftIn) {
      this.minecraft = minecraftIn;
   }

   public void func_225619_a_(MatrixStack p_225619_1_, IRenderTypeBuffer p_225619_2_, double p_225619_3_, double p_225619_5_, double p_225619_7_) {
      double d0 = (double)Util.nanoTime();
      if (d0 - this.lastUpdate > 1.0E8D) {
         this.lastUpdate = d0;
         Entity entity = this.minecraft.gameRenderer.getActiveRenderInfo().getRenderViewEntity();
         this.collisionData = entity.world.func_226667_c_(entity, entity.getBoundingBox().grow(6.0D), Collections.emptySet()).collect(Collectors.toList());
      }

      IVertexBuilder ivertexbuilder = p_225619_2_.getBuffer(RenderType.func_228659_m_());

      for(VoxelShape voxelshape : this.collisionData) {
         WorldRenderer.func_228431_a_(p_225619_1_, ivertexbuilder, voxelshape, -p_225619_3_, -p_225619_5_, -p_225619_7_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

   }
}