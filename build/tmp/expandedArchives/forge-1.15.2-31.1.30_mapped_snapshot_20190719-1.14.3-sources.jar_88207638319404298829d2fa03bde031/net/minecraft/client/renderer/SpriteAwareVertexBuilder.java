package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpriteAwareVertexBuilder implements IVertexBuilder {
   private final IVertexBuilder field_228787_a_;
   private final TextureAtlasSprite field_228788_b_;

   public SpriteAwareVertexBuilder(IVertexBuilder p_i225999_1_, TextureAtlasSprite p_i225999_2_) {
      this.field_228787_a_ = p_i225999_1_;
      this.field_228788_b_ = p_i225999_2_;
   }

   public IVertexBuilder func_225582_a_(double p_225582_1_, double p_225582_3_, double p_225582_5_) {
      return this.field_228787_a_.func_225582_a_(p_225582_1_, p_225582_3_, p_225582_5_);
   }

   public IVertexBuilder func_225586_a_(int p_225586_1_, int p_225586_2_, int p_225586_3_, int p_225586_4_) {
      return this.field_228787_a_.func_225586_a_(p_225586_1_, p_225586_2_, p_225586_3_, p_225586_4_);
   }

   public IVertexBuilder func_225583_a_(float p_225583_1_, float p_225583_2_) {
      return this.field_228787_a_.func_225583_a_(this.field_228788_b_.getInterpolatedU((double)(p_225583_1_ * 16.0F)), this.field_228788_b_.getInterpolatedV((double)(p_225583_2_ * 16.0F)));
   }

   public IVertexBuilder func_225585_a_(int p_225585_1_, int p_225585_2_) {
      return this.field_228787_a_.func_225585_a_(p_225585_1_, p_225585_2_);
   }

   public IVertexBuilder func_225587_b_(int p_225587_1_, int p_225587_2_) {
      return this.field_228787_a_.func_225587_b_(p_225587_1_, p_225587_2_);
   }

   public IVertexBuilder func_225584_a_(float p_225584_1_, float p_225584_2_, float p_225584_3_) {
      return this.field_228787_a_.func_225584_a_(p_225584_1_, p_225584_2_, p_225584_3_);
   }

   public void endVertex() {
      this.field_228787_a_.endVertex();
   }

   public void func_225588_a_(float p_225588_1_, float p_225588_2_, float p_225588_3_, float p_225588_4_, float p_225588_5_, float p_225588_6_, float p_225588_7_, float p_225588_8_, float p_225588_9_, int p_225588_10_, int p_225588_11_, float p_225588_12_, float p_225588_13_, float p_225588_14_) {
      this.field_228787_a_.func_225588_a_(p_225588_1_, p_225588_2_, p_225588_3_, p_225588_4_, p_225588_5_, p_225588_6_, p_225588_7_, this.field_228788_b_.getInterpolatedU((double)(p_225588_8_ * 16.0F)), this.field_228788_b_.getInterpolatedV((double)(p_225588_9_ * 16.0F)), p_225588_10_, p_225588_11_, p_225588_12_, p_225588_13_, p_225588_14_);
   }
}