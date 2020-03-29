package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Random;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FallingBlockRenderer extends EntityRenderer<FallingBlockEntity> {
   public FallingBlockRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
      this.shadowSize = 0.5F;
   }

   public void func_225623_a_(FallingBlockEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      BlockState blockstate = p_225623_1_.getBlockState();
      if (blockstate.getRenderType() == BlockRenderType.MODEL) {
         World world = p_225623_1_.getWorldObj();
         if (blockstate != world.getBlockState(new BlockPos(p_225623_1_)) && blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
            p_225623_4_.func_227860_a_();
            BlockPos blockpos = new BlockPos(p_225623_1_.func_226277_ct_(), p_225623_1_.getBoundingBox().maxY, p_225623_1_.func_226281_cx_());
            p_225623_4_.func_227861_a_(-0.5D, 0.0D, -0.5D);
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.func_228661_n_()) {
               if (RenderTypeLookup.canRenderInLayer(blockstate, type)) {
                  net.minecraftforge.client.ForgeHooksClient.setRenderLayer(type);
                  blockrendererdispatcher.getBlockModelRenderer().func_228802_a_(world, blockrendererdispatcher.getModelForState(blockstate), blockstate, blockpos, p_225623_4_, p_225623_5_.getBuffer(type), false, new Random(), blockstate.getPositionRandom(p_225623_1_.getOrigin()), OverlayTexture.field_229196_a_);
               }
            }
            net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
            p_225623_4_.func_227865_b_();
            super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
         }
      }
   }

   public ResourceLocation getEntityTexture(FallingBlockEntity entity) {
      return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
   }
}