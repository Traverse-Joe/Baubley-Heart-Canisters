package net.minecraft.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BeeSound extends TickableSound {
   protected final BeeEntity field_229357_o_;
   private boolean field_229358_p_;

   public BeeSound(BeeEntity p_i226060_1_, SoundEvent p_i226060_2_, SoundCategory p_i226060_3_) {
      super(p_i226060_2_, p_i226060_3_);
      this.field_229357_o_ = p_i226060_1_;
      this.x = (float)p_i226060_1_.func_226277_ct_();
      this.y = (float)p_i226060_1_.func_226278_cu_();
      this.z = (float)p_i226060_1_.func_226281_cx_();
      this.repeat = true;
      this.repeatDelay = 0;
      this.volume = 0.0F;
   }

   public void tick() {
      boolean flag = this.func_225643_p_();
      if (flag && !this.donePlaying) {
         Minecraft.getInstance().getSoundHandler().func_229364_a_(this.func_225642_o_());
         this.field_229358_p_ = true;
      }

      if (!this.field_229357_o_.removed && !this.field_229358_p_) {
         this.x = (float)this.field_229357_o_.func_226277_ct_();
         this.y = (float)this.field_229357_o_.func_226278_cu_();
         this.z = (float)this.field_229357_o_.func_226281_cx_();
         float f = MathHelper.sqrt(Entity.func_213296_b(this.field_229357_o_.getMotion()));
         if ((double)f >= 0.01D) {
            this.pitch = MathHelper.lerp(MathHelper.clamp(f, this.func_229359_s_(), this.func_229360_t_()), this.func_229359_s_(), this.func_229360_t_());
            this.volume = MathHelper.lerp(MathHelper.clamp(f, 0.0F, 0.5F), 0.0F, 1.2F);
         } else {
            this.pitch = 0.0F;
            this.volume = 0.0F;
         }

      } else {
         this.donePlaying = true;
      }
   }

   private float func_229359_s_() {
      return this.field_229357_o_.isChild() ? 1.1F : 0.7F;
   }

   private float func_229360_t_() {
      return this.field_229357_o_.isChild() ? 1.5F : 1.1F;
   }

   public boolean canBeSilent() {
      return true;
   }

   protected abstract TickableSound func_225642_o_();

   protected abstract boolean func_225643_p_();
}