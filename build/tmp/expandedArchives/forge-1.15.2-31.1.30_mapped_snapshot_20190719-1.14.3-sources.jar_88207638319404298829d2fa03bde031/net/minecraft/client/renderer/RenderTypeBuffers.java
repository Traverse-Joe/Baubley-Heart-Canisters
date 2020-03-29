package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.SortedMap;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderTypeBuffers {
   private final RegionRenderCacheBuilder field_228479_a_ = new RegionRenderCacheBuilder();
   private final SortedMap<RenderType, BufferBuilder> field_228480_b_ = Util.make(new Object2ObjectLinkedOpenHashMap<>(), (p_228485_1_) -> {
      p_228485_1_.put(Atlases.func_228782_g_(), this.field_228479_a_.func_228366_a_(RenderType.func_228639_c_()));
      p_228485_1_.put(Atlases.func_228783_h_(), this.field_228479_a_.func_228366_a_(RenderType.func_228643_e_()));
      p_228485_1_.put(Atlases.func_228768_a_(), this.field_228479_a_.func_228366_a_(RenderType.func_228641_d_()));
      p_228485_1_.put(Atlases.func_228784_i_(), this.field_228479_a_.func_228366_a_(RenderType.func_228645_f_()));
      func_228486_a_(p_228485_1_, Atlases.func_228776_b_());
      func_228486_a_(p_228485_1_, Atlases.func_228778_c_());
      func_228486_a_(p_228485_1_, Atlases.func_228779_d_());
      func_228486_a_(p_228485_1_, Atlases.func_228780_e_());
      func_228486_a_(p_228485_1_, Atlases.func_228781_f_());
      func_228486_a_(p_228485_1_, RenderType.func_228647_g_());
      func_228486_a_(p_228485_1_, RenderType.func_228653_j_());
      func_228486_a_(p_228485_1_, RenderType.func_228655_k_());
      func_228486_a_(p_228485_1_, RenderType.func_228651_i_());
      ModelBakery.field_229320_k_.forEach((p_228488_1_) -> {
         func_228486_a_(p_228485_1_, p_228488_1_);
      });
   });
   private final IRenderTypeBuffer.Impl field_228481_c_ = IRenderTypeBuffer.func_228456_a_(this.field_228480_b_, new BufferBuilder(256));
   private final IRenderTypeBuffer.Impl field_228482_d_ = IRenderTypeBuffer.func_228455_a_(new BufferBuilder(256));
   private final OutlineLayerBuffer field_228483_e_ = new OutlineLayerBuffer(this.field_228481_c_);

   private static void func_228486_a_(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> p_228486_0_, RenderType p_228486_1_) {
      p_228486_0_.put(p_228486_1_, new BufferBuilder(p_228486_1_.func_228662_o_()));
   }

   public RegionRenderCacheBuilder func_228484_a_() {
      return this.field_228479_a_;
   }

   public IRenderTypeBuffer.Impl func_228487_b_() {
      return this.field_228481_c_;
   }

   public IRenderTypeBuffer.Impl func_228489_c_() {
      return this.field_228482_d_;
   }

   public OutlineLayerBuffer func_228490_d_() {
      return this.field_228483_e_;
   }
}