package net.minecraft.client.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.client.renderer.entity.model.GuardianModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MobAppearanceParticle extends Particle {
   private final Model field_228342_a_ = new GuardianModel();
   private final RenderType field_228341_A_ = RenderType.func_228644_e_(ElderGuardianRenderer.GUARDIAN_ELDER_TEXTURE);

   private MobAppearanceParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn);
      this.particleGravity = 0.0F;
      this.maxAge = 30;
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.CUSTOM;
   }

   public void func_225606_a_(IVertexBuilder p_225606_1_, ActiveRenderInfo p_225606_2_, float p_225606_3_) {
      float f = ((float)this.age + p_225606_3_) / (float)this.maxAge;
      float f1 = 0.05F + 0.5F * MathHelper.sin(f * (float)Math.PI);
      MatrixStack matrixstack = new MatrixStack();
      matrixstack.func_227863_a_(p_225606_2_.func_227995_f_());
      matrixstack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(150.0F * f - 60.0F));
      matrixstack.func_227862_a_(-1.0F, -1.0F, 1.0F);
      matrixstack.func_227861_a_(0.0D, (double)-1.101F, 1.5D);
      IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().func_228019_au_().func_228487_b_();
      IVertexBuilder ivertexbuilder = irendertypebuffer$impl.getBuffer(this.field_228341_A_);
      this.field_228342_a_.func_225598_a_(matrixstack, ivertexbuilder, 15728880, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, f1);
      irendertypebuffer$impl.func_228461_a_();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new MobAppearanceParticle(worldIn, x, y, z);
      }
   }
}