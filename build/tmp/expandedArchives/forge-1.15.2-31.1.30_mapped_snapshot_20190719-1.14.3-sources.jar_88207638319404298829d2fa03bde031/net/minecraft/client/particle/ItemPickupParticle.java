package net.minecraft.client.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemPickupParticle extends Particle {
   private final RenderTypeBuffers field_228340_a_;
   private final Entity item;
   private final Entity target;
   private int age;
   private final EntityRendererManager renderManager;

   public ItemPickupParticle(EntityRendererManager p_i225963_1_, RenderTypeBuffers p_i225963_2_, World p_i225963_3_, Entity p_i225963_4_, Entity p_i225963_5_) {
      this(p_i225963_1_, p_i225963_2_, p_i225963_3_, p_i225963_4_, p_i225963_5_, p_i225963_4_.getMotion());
   }

   private ItemPickupParticle(EntityRendererManager p_i225964_1_, RenderTypeBuffers p_i225964_2_, World p_i225964_3_, Entity p_i225964_4_, Entity p_i225964_5_, Vec3d p_i225964_6_) {
      super(p_i225964_3_, p_i225964_4_.func_226277_ct_(), p_i225964_4_.func_226278_cu_(), p_i225964_4_.func_226281_cx_(), p_i225964_6_.x, p_i225964_6_.y, p_i225964_6_.z);
      this.field_228340_a_ = p_i225964_2_;
      this.item = p_i225964_4_;
      this.target = p_i225964_5_;
      this.renderManager = p_i225964_1_;
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.CUSTOM;
   }

   public void func_225606_a_(IVertexBuilder p_225606_1_, ActiveRenderInfo p_225606_2_, float p_225606_3_) {
      float f = ((float)this.age + p_225606_3_) / 3.0F;
      f = f * f;
      double d0 = MathHelper.lerp((double)p_225606_3_, this.target.lastTickPosX, this.target.func_226277_ct_());
      double d1 = MathHelper.lerp((double)p_225606_3_, this.target.lastTickPosY, this.target.func_226278_cu_()) + 0.5D;
      double d2 = MathHelper.lerp((double)p_225606_3_, this.target.lastTickPosZ, this.target.func_226281_cx_());
      double d3 = MathHelper.lerp((double)f, this.item.func_226277_ct_(), d0);
      double d4 = MathHelper.lerp((double)f, this.item.func_226278_cu_(), d1);
      double d5 = MathHelper.lerp((double)f, this.item.func_226281_cx_(), d2);
      IRenderTypeBuffer.Impl irendertypebuffer$impl = this.field_228340_a_.func_228487_b_();
      Vec3d vec3d = p_225606_2_.getProjectedView();
      this.renderManager.func_229084_a_(this.item, d3 - vec3d.getX(), d4 - vec3d.getY(), d5 - vec3d.getZ(), this.item.rotationYaw, p_225606_3_, new MatrixStack(), irendertypebuffer$impl, this.renderManager.func_229085_a_(this.item, p_225606_3_));
      irendertypebuffer$impl.func_228461_a_();
   }

   public void tick() {
      ++this.age;
      if (this.age == 3) {
         this.setExpired();
      }

   }
}