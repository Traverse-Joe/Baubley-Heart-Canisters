package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TNTRenderer extends EntityRenderer<TNTEntity> {
   public TNTRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
      this.shadowSize = 0.5F;
   }

   public void func_225623_a_(TNTEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      p_225623_4_.func_227860_a_();
      p_225623_4_.func_227861_a_(0.0D, 0.5D, 0.0D);
      if ((float)p_225623_1_.getFuse() - p_225623_3_ + 1.0F < 10.0F) {
         float f = 1.0F - ((float)p_225623_1_.getFuse() - p_225623_3_ + 1.0F) / 10.0F;
         f = MathHelper.clamp(f, 0.0F, 1.0F);
         f = f * f;
         f = f * f;
         float f1 = 1.0F + f * 0.3F;
         p_225623_4_.func_227862_a_(f1, f1, f1);
      }

      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-90.0F));
      p_225623_4_.func_227861_a_(-0.5D, -0.5D, 0.5D);
      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(90.0F));
      TNTMinecartRenderer.func_229127_a_(Blocks.TNT.getDefaultState(), p_225623_4_, p_225623_5_, p_225623_6_, p_225623_1_.getFuse() / 5 % 2 == 0);
      p_225623_4_.func_227865_b_();
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
   }

   public ResourceLocation getEntityTexture(TNTEntity entity) {
      return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
   }
}