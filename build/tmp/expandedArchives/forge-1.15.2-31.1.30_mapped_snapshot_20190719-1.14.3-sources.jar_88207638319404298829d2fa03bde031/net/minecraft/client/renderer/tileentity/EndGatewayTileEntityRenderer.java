package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndGatewayTileEntityRenderer extends EndPortalTileEntityRenderer<EndGatewayTileEntity> {
   private static final ResourceLocation END_GATEWAY_BEAM_TEXTURE = new ResourceLocation("textures/entity/end_gateway_beam.png");

   public EndGatewayTileEntityRenderer(TileEntityRendererDispatcher p_i226018_1_) {
      super(p_i226018_1_);
   }

   public void func_225616_a_(EndGatewayTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      if (p_225616_1_.isSpawning() || p_225616_1_.isCoolingDown()) {
         float f = p_225616_1_.isSpawning() ? p_225616_1_.getSpawnPercent(p_225616_2_) : p_225616_1_.getCooldownPercent(p_225616_2_);
         double d0 = p_225616_1_.isSpawning() ? 256.0D : 50.0D;
         f = MathHelper.sin(f * (float)Math.PI);
         int i = MathHelper.floor((double)f * d0);
         float[] afloat = p_225616_1_.isSpawning() ? DyeColor.MAGENTA.getColorComponentValues() : DyeColor.PURPLE.getColorComponentValues();
         long j = p_225616_1_.getWorld().getGameTime();
         BeaconTileEntityRenderer.func_228842_a_(p_225616_3_, p_225616_4_, END_GATEWAY_BEAM_TEXTURE, p_225616_2_, f, j, 0, i, afloat, 0.15F, 0.175F);
         BeaconTileEntityRenderer.func_228842_a_(p_225616_3_, p_225616_4_, END_GATEWAY_BEAM_TEXTURE, p_225616_2_, f, j, 0, -i, afloat, 0.15F, 0.175F);
      }

      super.func_225616_a_(p_225616_1_, p_225616_2_, p_225616_3_, p_225616_4_, p_225616_5_, p_225616_6_);
   }

   protected int getPasses(double p_191286_1_) {
      return super.getPasses(p_191286_1_) + 1;
   }

   protected float getOffset() {
      return 1.0F;
   }
}