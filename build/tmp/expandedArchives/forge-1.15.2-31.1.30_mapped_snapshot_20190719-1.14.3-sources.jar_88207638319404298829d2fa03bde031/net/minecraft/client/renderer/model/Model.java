package net.minecraft.client.renderer.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Model implements Consumer<ModelRenderer> {
   protected final Function<ResourceLocation, RenderType> field_228281_q_;
   public int textureWidth = 64;
   public int textureHeight = 32;

   public Model(Function<ResourceLocation, RenderType> p_i225947_1_) {
      this.field_228281_q_ = p_i225947_1_;
   }

   public void accept(ModelRenderer p_accept_1_) {
   }

   public final RenderType func_228282_a_(ResourceLocation p_228282_1_) {
      return this.field_228281_q_.apply(p_228282_1_);
   }

   public abstract void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_);
}