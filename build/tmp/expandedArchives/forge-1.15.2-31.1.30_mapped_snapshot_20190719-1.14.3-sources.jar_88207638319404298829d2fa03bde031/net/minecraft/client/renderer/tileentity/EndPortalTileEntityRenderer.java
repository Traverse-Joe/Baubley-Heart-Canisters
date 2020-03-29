package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndPortalTileEntityRenderer<T extends EndPortalTileEntity> extends TileEntityRenderer<T> {
   public static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
   public static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
   private static final Random RANDOM = new Random(31100L);
   private static final List<RenderType> field_228881_e_ = IntStream.range(0, 16).mapToObj((p_228882_0_) -> {
      return RenderType.func_228630_a_(p_228882_0_ + 1);
   }).collect(ImmutableList.toImmutableList());

   public EndPortalTileEntityRenderer(TileEntityRendererDispatcher p_i226019_1_) {
      super(p_i226019_1_);
   }

   public void func_225616_a_(T p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      RANDOM.setSeed(31100L);
      double d0 = p_225616_1_.getPos().distanceSq(this.field_228858_b_.renderInfo.getProjectedView(), true);
      int i = this.getPasses(d0);
      float f = this.getOffset();
      Matrix4f matrix4f = p_225616_3_.func_227866_c_().func_227870_a_();
      this.func_228883_a_(p_225616_1_, f, 0.15F, matrix4f, p_225616_4_.getBuffer(field_228881_e_.get(0)));

      for(int j = 1; j < i; ++j) {
         this.func_228883_a_(p_225616_1_, f, 2.0F / (float)(18 - j), matrix4f, p_225616_4_.getBuffer(field_228881_e_.get(j)));
      }

   }

   private void func_228883_a_(T p_228883_1_, float p_228883_2_, float p_228883_3_, Matrix4f p_228883_4_, IVertexBuilder p_228883_5_) {
      float f = (RANDOM.nextFloat() * 0.5F + 0.1F) * p_228883_3_;
      float f1 = (RANDOM.nextFloat() * 0.5F + 0.4F) * p_228883_3_;
      float f2 = (RANDOM.nextFloat() * 0.5F + 0.5F) * p_228883_3_;
      this.func_228884_a_(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, f, f1, f2, Direction.SOUTH);
      this.func_228884_a_(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f, f1, f2, Direction.NORTH);
      this.func_228884_a_(p_228883_1_, p_228883_4_, p_228883_5_, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, f, f1, f2, Direction.EAST);
      this.func_228884_a_(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, f, f1, f2, Direction.WEST);
      this.func_228884_a_(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f, f1, f2, Direction.DOWN);
      this.func_228884_a_(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 1.0F, p_228883_2_, p_228883_2_, 1.0F, 1.0F, 0.0F, 0.0F, f, f1, f2, Direction.UP);
   }

   private void func_228884_a_(T p_228884_1_, Matrix4f p_228884_2_, IVertexBuilder p_228884_3_, float p_228884_4_, float p_228884_5_, float p_228884_6_, float p_228884_7_, float p_228884_8_, float p_228884_9_, float p_228884_10_, float p_228884_11_, float p_228884_12_, float p_228884_13_, float p_228884_14_, Direction p_228884_15_) {
      if (p_228884_1_.shouldRenderFace(p_228884_15_)) {
         p_228884_3_.func_227888_a_(p_228884_2_, p_228884_4_, p_228884_6_, p_228884_8_).func_227885_a_(p_228884_12_, p_228884_13_, p_228884_14_, 1.0F).endVertex();
         p_228884_3_.func_227888_a_(p_228884_2_, p_228884_5_, p_228884_6_, p_228884_9_).func_227885_a_(p_228884_12_, p_228884_13_, p_228884_14_, 1.0F).endVertex();
         p_228884_3_.func_227888_a_(p_228884_2_, p_228884_5_, p_228884_7_, p_228884_10_).func_227885_a_(p_228884_12_, p_228884_13_, p_228884_14_, 1.0F).endVertex();
         p_228884_3_.func_227888_a_(p_228884_2_, p_228884_4_, p_228884_7_, p_228884_11_).func_227885_a_(p_228884_12_, p_228884_13_, p_228884_14_, 1.0F).endVertex();
      }

   }

   protected int getPasses(double p_191286_1_) {
      if (p_191286_1_ > 36864.0D) {
         return 1;
      } else if (p_191286_1_ > 25600.0D) {
         return 3;
      } else if (p_191286_1_ > 16384.0D) {
         return 5;
      } else if (p_191286_1_ > 9216.0D) {
         return 7;
      } else if (p_191286_1_ > 4096.0D) {
         return 9;
      } else if (p_191286_1_ > 1024.0D) {
         return 11;
      } else if (p_191286_1_ > 576.0D) {
         return 13;
      } else {
         return p_191286_1_ > 256.0D ? 14 : 15;
      }
   }

   protected float getOffset() {
      return 0.75F;
   }
}