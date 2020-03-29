package net.minecraft.client.renderer.vertex;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VertexFormat {
   private final ImmutableList<VertexFormatElement> elements;
   private final IntList offsets = new IntArrayList();
   private final int vertexSize;

   public VertexFormat(ImmutableList<VertexFormatElement> p_i225911_1_) {
      this.elements = p_i225911_1_;
      int i = 0;

      for(VertexFormatElement vertexformatelement : p_i225911_1_) {
         this.offsets.add(i);
         i += vertexformatelement.getSize();
      }

      this.vertexSize = i;
   }

   public String toString() {
      return "format: " + this.elements.size() + " elements: " + (String)this.elements.stream().map(Object::toString).collect(Collectors.joining(" "));
   }

   public int getIntegerSize() {
      return this.getSize() / 4;
   }

   public int getSize() {
      return this.vertexSize;
   }

   public ImmutableList<VertexFormatElement> func_227894_c_() {
      return this.elements;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         VertexFormat vertexformat = (VertexFormat)p_equals_1_;
         return this.vertexSize != vertexformat.vertexSize ? false : this.elements.equals(vertexformat.elements);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.elements.hashCode();
   }

   public void func_227892_a_(long p_227892_1_) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            this.func_227892_a_(p_227892_1_);
         });
      } else {
         int i = this.getSize();
         List<VertexFormatElement> list = this.func_227894_c_();

         for(int j = 0; j < list.size(); ++j) {
            list.get(j).func_227897_a_(p_227892_1_ + (long)this.offsets.getInt(j), i);
         }

      }
   }

   public void func_227895_d_() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::func_227895_d_);
      } else {
         for(VertexFormatElement vertexformatelement : this.func_227894_c_()) {
            vertexformatelement.func_227898_g_();
         }

      }
   }

   // Forge start
   public int getOffset(int index) { return offsets.getInt(index); }
   public boolean hasPosition() { return elements.stream().anyMatch(e -> e.isPositionElement()); }
   public boolean hasNormal() { return elements.stream().anyMatch(e -> e.getUsage() == VertexFormatElement.Usage.NORMAL); }
   public boolean hasColor() { return elements.stream().anyMatch(e -> e.getUsage() == VertexFormatElement.Usage.COLOR); }
   public boolean hasUV(int which) { return elements.stream().anyMatch(e -> e.getUsage() == VertexFormatElement.Usage.UV && e.getIndex() == which); }
}