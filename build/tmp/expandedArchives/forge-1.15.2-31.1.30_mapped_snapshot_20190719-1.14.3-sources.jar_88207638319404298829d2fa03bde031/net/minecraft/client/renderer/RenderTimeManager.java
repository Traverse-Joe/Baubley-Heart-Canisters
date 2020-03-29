package net.minecraft.client.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderTimeManager {
   private final long[] field_228729_a_;
   private int field_228730_b_;
   private int field_228731_c_;

   public RenderTimeManager(int p_i225998_1_) {
      this.field_228729_a_ = new long[p_i225998_1_];
   }

   public long func_228732_a_(long p_228732_1_) {
      if (this.field_228730_b_ < this.field_228729_a_.length) {
         ++this.field_228730_b_;
      }

      this.field_228729_a_[this.field_228731_c_] = p_228732_1_;
      this.field_228731_c_ = (this.field_228731_c_ + 1) % this.field_228729_a_.length;
      long i = Long.MAX_VALUE;
      long j = Long.MIN_VALUE;
      long k = 0L;

      for(int l = 0; l < this.field_228730_b_; ++l) {
         long i1 = this.field_228729_a_[l];
         k += i1;
         i = Math.min(i, i1);
         j = Math.max(j, i1);
      }

      if (this.field_228730_b_ > 2) {
         k = k - (i + j);
         return k / (long)(this.field_228730_b_ - 2);
      } else {
         return k > 0L ? (long)this.field_228730_b_ / k : 0L;
      }
   }
}