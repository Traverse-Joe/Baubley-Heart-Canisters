package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.tileentity.BellTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BellTileEntityRenderer extends TileEntityRenderer<BellTileEntity> {
   public static final Material field_217653_c = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/bell/bell_body"));
   private final ModelRenderer field_228848_c_ = new ModelRenderer(32, 32, 0, 0);

   public BellTileEntityRenderer(TileEntityRendererDispatcher p_i226005_1_) {
      super(p_i226005_1_);
      this.field_228848_c_.func_228300_a_(-3.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F);
      this.field_228848_c_.setRotationPoint(8.0F, 12.0F, 8.0F);
      ModelRenderer modelrenderer = new ModelRenderer(32, 32, 0, 13);
      modelrenderer.func_228300_a_(4.0F, 4.0F, 4.0F, 8.0F, 2.0F, 8.0F);
      modelrenderer.setRotationPoint(-8.0F, -12.0F, -8.0F);
      this.field_228848_c_.addChild(modelrenderer);
   }

   public void func_225616_a_(BellTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      float f = (float)p_225616_1_.field_213943_a + p_225616_2_;
      float f1 = 0.0F;
      float f2 = 0.0F;
      if (p_225616_1_.field_213944_b) {
         float f3 = MathHelper.sin(f / (float)Math.PI) / (4.0F + f / 3.0F);
         if (p_225616_1_.field_213945_c == Direction.NORTH) {
            f1 = -f3;
         } else if (p_225616_1_.field_213945_c == Direction.SOUTH) {
            f1 = f3;
         } else if (p_225616_1_.field_213945_c == Direction.EAST) {
            f2 = -f3;
         } else if (p_225616_1_.field_213945_c == Direction.WEST) {
            f2 = f3;
         }
      }

      this.field_228848_c_.rotateAngleX = f1;
      this.field_228848_c_.rotateAngleZ = f2;
      IVertexBuilder ivertexbuilder = field_217653_c.func_229311_a_(p_225616_4_, RenderType::func_228634_a_);
      this.field_228848_c_.func_228308_a_(p_225616_3_, ivertexbuilder, p_225616_5_, p_225616_6_);
   }
}