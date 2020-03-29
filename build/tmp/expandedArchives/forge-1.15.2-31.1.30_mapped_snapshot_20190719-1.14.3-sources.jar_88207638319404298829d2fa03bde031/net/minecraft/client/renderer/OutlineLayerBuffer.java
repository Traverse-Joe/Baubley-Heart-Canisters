package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.DefaultColorVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import java.util.Optional;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OutlineLayerBuffer implements IRenderTypeBuffer {
   private final IRenderTypeBuffer.Impl field_228465_a_;
   private final IRenderTypeBuffer.Impl field_228466_b_ = IRenderTypeBuffer.func_228455_a_(new BufferBuilder(256));
   private int field_228467_c_ = 255;
   private int field_228468_d_ = 255;
   private int field_228469_e_ = 255;
   private int field_228470_f_ = 255;

   public OutlineLayerBuffer(IRenderTypeBuffer.Impl p_i225970_1_) {
      this.field_228465_a_ = p_i225970_1_;
   }

   public IVertexBuilder getBuffer(RenderType p_getBuffer_1_) {
      if (p_getBuffer_1_.func_230041_s_()) {
         IVertexBuilder ivertexbuilder2 = this.field_228466_b_.getBuffer(p_getBuffer_1_);
         return new OutlineLayerBuffer.ColoredOutline(ivertexbuilder2, this.field_228467_c_, this.field_228468_d_, this.field_228469_e_, this.field_228470_f_);
      } else {
         IVertexBuilder ivertexbuilder = this.field_228465_a_.getBuffer(p_getBuffer_1_);
         Optional<RenderType> optional = p_getBuffer_1_.func_225612_r_();
         if (optional.isPresent()) {
            IVertexBuilder ivertexbuilder1 = this.field_228466_b_.getBuffer(optional.get());
            OutlineLayerBuffer.ColoredOutline outlinelayerbuffer$coloredoutline = new OutlineLayerBuffer.ColoredOutline(ivertexbuilder1, this.field_228467_c_, this.field_228468_d_, this.field_228469_e_, this.field_228470_f_);
            return VertexBuilderUtils.func_227915_a_(outlinelayerbuffer$coloredoutline, ivertexbuilder);
         } else {
            return ivertexbuilder;
         }
      }
   }

   public void func_228472_a_(int p_228472_1_, int p_228472_2_, int p_228472_3_, int p_228472_4_) {
      this.field_228467_c_ = p_228472_1_;
      this.field_228468_d_ = p_228472_2_;
      this.field_228469_e_ = p_228472_3_;
      this.field_228470_f_ = p_228472_4_;
   }

   public void func_228471_a_() {
      this.field_228466_b_.func_228461_a_();
   }

   @OnlyIn(Dist.CLIENT)
   static class ColoredOutline extends DefaultColorVertexBuilder {
      private final IVertexBuilder field_228473_g_;
      private double field_228474_h_;
      private double field_228475_i_;
      private double field_228476_j_;
      private float field_228477_k_;
      private float field_228478_l_;

      private ColoredOutline(IVertexBuilder p_i225971_1_, int p_i225971_2_, int p_i225971_3_, int p_i225971_4_, int p_i225971_5_) {
         this.field_228473_g_ = p_i225971_1_;
         super.func_225611_b_(p_i225971_2_, p_i225971_3_, p_i225971_4_, p_i225971_5_);
      }

      public void func_225611_b_(int p_225611_1_, int p_225611_2_, int p_225611_3_, int p_225611_4_) {
      }

      public IVertexBuilder func_225582_a_(double p_225582_1_, double p_225582_3_, double p_225582_5_) {
         this.field_228474_h_ = p_225582_1_;
         this.field_228475_i_ = p_225582_3_;
         this.field_228476_j_ = p_225582_5_;
         return this;
      }

      public IVertexBuilder func_225586_a_(int p_225586_1_, int p_225586_2_, int p_225586_3_, int p_225586_4_) {
         return this;
      }

      public IVertexBuilder func_225583_a_(float p_225583_1_, float p_225583_2_) {
         this.field_228477_k_ = p_225583_1_;
         this.field_228478_l_ = p_225583_2_;
         return this;
      }

      public IVertexBuilder func_225585_a_(int p_225585_1_, int p_225585_2_) {
         return this;
      }

      public IVertexBuilder func_225587_b_(int p_225587_1_, int p_225587_2_) {
         return this;
      }

      public IVertexBuilder func_225584_a_(float p_225584_1_, float p_225584_2_, float p_225584_3_) {
         return this;
      }

      public void func_225588_a_(float p_225588_1_, float p_225588_2_, float p_225588_3_, float p_225588_4_, float p_225588_5_, float p_225588_6_, float p_225588_7_, float p_225588_8_, float p_225588_9_, int p_225588_10_, int p_225588_11_, float p_225588_12_, float p_225588_13_, float p_225588_14_) {
         this.field_228473_g_.func_225582_a_((double)p_225588_1_, (double)p_225588_2_, (double)p_225588_3_).func_225586_a_(this.field_227855_b_, this.field_227856_c_, this.field_227857_d_, this.field_227858_e_).func_225583_a_(p_225588_8_, p_225588_9_).endVertex();
      }

      public void endVertex() {
         this.field_228473_g_.func_225582_a_(this.field_228474_h_, this.field_228475_i_, this.field_228476_j_).func_225586_a_(this.field_227855_b_, this.field_227856_c_, this.field_227857_d_, this.field_227858_e_).func_225583_a_(this.field_228477_k_, this.field_228478_l_).endVertex();
      }
   }
}