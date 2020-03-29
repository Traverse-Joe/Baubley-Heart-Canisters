package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class TexturedParticle extends Particle {
   protected float particleScale = 0.1F * (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;

   protected TexturedParticle(World p_i51011_1_, double p_i51011_2_, double p_i51011_4_, double p_i51011_6_) {
      super(p_i51011_1_, p_i51011_2_, p_i51011_4_, p_i51011_6_);
   }

   protected TexturedParticle(World p_i51012_1_, double p_i51012_2_, double p_i51012_4_, double p_i51012_6_, double p_i51012_8_, double p_i51012_10_, double p_i51012_12_) {
      super(p_i51012_1_, p_i51012_2_, p_i51012_4_, p_i51012_6_, p_i51012_8_, p_i51012_10_, p_i51012_12_);
   }

   public void func_225606_a_(IVertexBuilder p_225606_1_, ActiveRenderInfo p_225606_2_, float p_225606_3_) {
      Vec3d vec3d = p_225606_2_.getProjectedView();
      float f = (float)(MathHelper.lerp((double)p_225606_3_, this.prevPosX, this.posX) - vec3d.getX());
      float f1 = (float)(MathHelper.lerp((double)p_225606_3_, this.prevPosY, this.posY) - vec3d.getY());
      float f2 = (float)(MathHelper.lerp((double)p_225606_3_, this.prevPosZ, this.posZ) - vec3d.getZ());
      Quaternion quaternion;
      if (this.particleAngle == 0.0F) {
         quaternion = p_225606_2_.func_227995_f_();
      } else {
         quaternion = new Quaternion(p_225606_2_.func_227995_f_());
         float f3 = MathHelper.lerp(p_225606_3_, this.prevParticleAngle, this.particleAngle);
         quaternion.multiply(Vector3f.field_229183_f_.func_229193_c_(f3));
      }

      Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
      vector3f1.func_214905_a(quaternion);
      Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
      float f4 = this.getScale(p_225606_3_);

      for(int i = 0; i < 4; ++i) {
         Vector3f vector3f = avector3f[i];
         vector3f.func_214905_a(quaternion);
         vector3f.mul(f4);
         vector3f.add(f, f1, f2);
      }

      float f7 = this.getMinU();
      float f8 = this.getMaxU();
      float f5 = this.getMinV();
      float f6 = this.getMaxV();
      int j = this.getBrightnessForRender(p_225606_3_);
      p_225606_1_.func_225582_a_((double)avector3f[0].getX(), (double)avector3f[0].getY(), (double)avector3f[0].getZ()).func_225583_a_(f8, f6).func_227885_a_(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).func_227886_a_(j).endVertex();
      p_225606_1_.func_225582_a_((double)avector3f[1].getX(), (double)avector3f[1].getY(), (double)avector3f[1].getZ()).func_225583_a_(f8, f5).func_227885_a_(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).func_227886_a_(j).endVertex();
      p_225606_1_.func_225582_a_((double)avector3f[2].getX(), (double)avector3f[2].getY(), (double)avector3f[2].getZ()).func_225583_a_(f7, f5).func_227885_a_(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).func_227886_a_(j).endVertex();
      p_225606_1_.func_225582_a_((double)avector3f[3].getX(), (double)avector3f[3].getY(), (double)avector3f[3].getZ()).func_225583_a_(f7, f6).func_227885_a_(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).func_227886_a_(j).endVertex();
   }

   public float getScale(float p_217561_1_) {
      return this.particleScale;
   }

   public Particle multipleParticleScaleBy(float scale) {
      this.particleScale *= scale;
      return super.multipleParticleScaleBy(scale);
   }

   protected abstract float getMinU();

   protected abstract float getMaxU();

   protected abstract float getMinV();

   protected abstract float getMaxV();
}