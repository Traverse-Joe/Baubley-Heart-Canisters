package com.mojang.blaze3d.vertex;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VertexBuilderUtils {
   public static IVertexBuilder func_227915_a_(IVertexBuilder p_227915_0_, IVertexBuilder p_227915_1_) {
      return new VertexBuilderUtils.DelegatingVertexBuilder(p_227915_0_, p_227915_1_);
   }

   @OnlyIn(Dist.CLIENT)
   static class DelegatingVertexBuilder implements IVertexBuilder {
      private final IVertexBuilder field_227916_a_;
      private final IVertexBuilder field_227917_b_;

      public DelegatingVertexBuilder(IVertexBuilder p_i225913_1_, IVertexBuilder p_i225913_2_) {
         if (p_i225913_1_ == p_i225913_2_) {
            throw new IllegalArgumentException("Duplicate delegates");
         } else {
            this.field_227916_a_ = p_i225913_1_;
            this.field_227917_b_ = p_i225913_2_;
         }
      }

      public IVertexBuilder func_225582_a_(double p_225582_1_, double p_225582_3_, double p_225582_5_) {
         this.field_227916_a_.func_225582_a_(p_225582_1_, p_225582_3_, p_225582_5_);
         this.field_227917_b_.func_225582_a_(p_225582_1_, p_225582_3_, p_225582_5_);
         return this;
      }

      public IVertexBuilder func_225586_a_(int p_225586_1_, int p_225586_2_, int p_225586_3_, int p_225586_4_) {
         this.field_227916_a_.func_225586_a_(p_225586_1_, p_225586_2_, p_225586_3_, p_225586_4_);
         this.field_227917_b_.func_225586_a_(p_225586_1_, p_225586_2_, p_225586_3_, p_225586_4_);
         return this;
      }

      public IVertexBuilder func_225583_a_(float p_225583_1_, float p_225583_2_) {
         this.field_227916_a_.func_225583_a_(p_225583_1_, p_225583_2_);
         this.field_227917_b_.func_225583_a_(p_225583_1_, p_225583_2_);
         return this;
      }

      public IVertexBuilder func_225585_a_(int p_225585_1_, int p_225585_2_) {
         this.field_227916_a_.func_225585_a_(p_225585_1_, p_225585_2_);
         this.field_227917_b_.func_225585_a_(p_225585_1_, p_225585_2_);
         return this;
      }

      public IVertexBuilder func_225587_b_(int p_225587_1_, int p_225587_2_) {
         this.field_227916_a_.func_225587_b_(p_225587_1_, p_225587_2_);
         this.field_227917_b_.func_225587_b_(p_225587_1_, p_225587_2_);
         return this;
      }

      public IVertexBuilder func_225584_a_(float p_225584_1_, float p_225584_2_, float p_225584_3_) {
         this.field_227916_a_.func_225584_a_(p_225584_1_, p_225584_2_, p_225584_3_);
         this.field_227917_b_.func_225584_a_(p_225584_1_, p_225584_2_, p_225584_3_);
         return this;
      }

      public void func_225588_a_(float p_225588_1_, float p_225588_2_, float p_225588_3_, float p_225588_4_, float p_225588_5_, float p_225588_6_, float p_225588_7_, float p_225588_8_, float p_225588_9_, int p_225588_10_, int p_225588_11_, float p_225588_12_, float p_225588_13_, float p_225588_14_) {
         this.field_227916_a_.func_225588_a_(p_225588_1_, p_225588_2_, p_225588_3_, p_225588_4_, p_225588_5_, p_225588_6_, p_225588_7_, p_225588_8_, p_225588_9_, p_225588_10_, p_225588_11_, p_225588_12_, p_225588_13_, p_225588_14_);
         this.field_227917_b_.func_225588_a_(p_225588_1_, p_225588_2_, p_225588_3_, p_225588_4_, p_225588_5_, p_225588_6_, p_225588_7_, p_225588_8_, p_225588_9_, p_225588_10_, p_225588_11_, p_225588_12_, p_225588_13_, p_225588_14_);
      }

      public void endVertex() {
         this.field_227916_a_.endVertex();
         this.field_227917_b_.endVertex();
      }
   }
}