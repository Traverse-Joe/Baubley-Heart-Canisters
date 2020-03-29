package net.minecraft.client.renderer.model;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Material {
   private final ResourceLocation field_229307_a_;
   private final ResourceLocation field_229308_b_;
   @Nullable
   private RenderType field_229309_c_;

   public Material(ResourceLocation p_i226055_1_, ResourceLocation p_i226055_2_) {
      this.field_229307_a_ = p_i226055_1_;
      this.field_229308_b_ = p_i226055_2_;
   }

   public ResourceLocation func_229310_a_() {
      return this.field_229307_a_;
   }

   public ResourceLocation func_229313_b_() {
      return this.field_229308_b_;
   }

   public TextureAtlasSprite func_229314_c_() {
      return Minecraft.getInstance().func_228015_a_(this.func_229310_a_()).apply(this.func_229313_b_());
   }

   public RenderType func_229312_a_(Function<ResourceLocation, RenderType> p_229312_1_) {
      if (this.field_229309_c_ == null) {
         this.field_229309_c_ = p_229312_1_.apply(this.field_229307_a_);
      }

      return this.field_229309_c_;
   }

   public IVertexBuilder func_229311_a_(IRenderTypeBuffer p_229311_1_, Function<ResourceLocation, RenderType> p_229311_2_) {
      return this.func_229314_c_().func_229230_a_(p_229311_1_.getBuffer(this.func_229312_a_(p_229311_2_)));
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         Material material = (Material)p_equals_1_;
         return this.field_229307_a_.equals(material.field_229307_a_) && this.field_229308_b_.equals(material.field_229308_b_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(this.field_229307_a_, this.field_229308_b_);
   }

   public String toString() {
      return "Material{atlasLocation=" + this.field_229307_a_ + ", texture=" + this.field_229308_b_ + '}';
   }
}