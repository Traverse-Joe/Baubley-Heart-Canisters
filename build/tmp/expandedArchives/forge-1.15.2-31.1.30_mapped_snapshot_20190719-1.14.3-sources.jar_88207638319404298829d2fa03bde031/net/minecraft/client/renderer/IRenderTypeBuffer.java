package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IRenderTypeBuffer {
   static IRenderTypeBuffer.Impl func_228455_a_(BufferBuilder p_228455_0_) {
      return func_228456_a_(ImmutableMap.of(), p_228455_0_);
   }

   static IRenderTypeBuffer.Impl func_228456_a_(Map<RenderType, BufferBuilder> p_228456_0_, BufferBuilder p_228456_1_) {
      return new IRenderTypeBuffer.Impl(p_228456_1_, p_228456_0_);
   }

   IVertexBuilder getBuffer(RenderType p_getBuffer_1_);

   @OnlyIn(Dist.CLIENT)
   public static class Impl implements IRenderTypeBuffer {
      protected final BufferBuilder field_228457_a_;
      protected final Map<RenderType, BufferBuilder> field_228458_b_;
      protected Optional<RenderType> field_228459_c_ = Optional.empty();
      protected final Set<BufferBuilder> field_228460_d_ = Sets.newHashSet();

      protected Impl(BufferBuilder p_i225969_1_, Map<RenderType, BufferBuilder> p_i225969_2_) {
         this.field_228457_a_ = p_i225969_1_;
         this.field_228458_b_ = p_i225969_2_;
      }

      public IVertexBuilder getBuffer(RenderType p_getBuffer_1_) {
         Optional<RenderType> optional = p_getBuffer_1_.func_230169_u_();
         BufferBuilder bufferbuilder = this.func_228463_b_(p_getBuffer_1_);
         if (!Objects.equals(this.field_228459_c_, optional)) {
            if (this.field_228459_c_.isPresent()) {
               RenderType rendertype = this.field_228459_c_.get();
               if (!this.field_228458_b_.containsKey(rendertype)) {
                  this.func_228462_a_(rendertype);
               }
            }

            if (this.field_228460_d_.add(bufferbuilder)) {
               bufferbuilder.begin(p_getBuffer_1_.func_228664_q_(), p_getBuffer_1_.func_228663_p_());
            }

            this.field_228459_c_ = optional;
         }

         return bufferbuilder;
      }

      private BufferBuilder func_228463_b_(RenderType p_228463_1_) {
         return this.field_228458_b_.getOrDefault(p_228463_1_, this.field_228457_a_);
      }

      public void func_228461_a_() {
         this.field_228459_c_.ifPresent((p_228464_1_) -> {
            IVertexBuilder ivertexbuilder = this.getBuffer(p_228464_1_);
            if (ivertexbuilder == this.field_228457_a_) {
               this.func_228462_a_(p_228464_1_);
            }

         });

         for(RenderType rendertype : this.field_228458_b_.keySet()) {
            this.func_228462_a_(rendertype);
         }

      }

      public void func_228462_a_(RenderType p_228462_1_) {
         BufferBuilder bufferbuilder = this.func_228463_b_(p_228462_1_);
         boolean flag = Objects.equals(this.field_228459_c_, p_228462_1_.func_230169_u_());
         if (flag || bufferbuilder != this.field_228457_a_) {
            if (this.field_228460_d_.remove(bufferbuilder)) {
               p_228462_1_.func_228631_a_(bufferbuilder, 0, 0, 0);
               if (flag) {
                  this.field_228459_c_ = Optional.empty();
               }

            }
         }
      }
   }
}