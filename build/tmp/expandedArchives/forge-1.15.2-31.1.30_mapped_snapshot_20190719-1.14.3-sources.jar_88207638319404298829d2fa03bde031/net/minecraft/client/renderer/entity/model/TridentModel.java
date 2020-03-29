package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TridentModel extends Model {
   public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/trident.png");
   private final ModelRenderer modelRenderer = new ModelRenderer(32, 32, 0, 6);

   public TridentModel() {
      super(RenderType::func_228634_a_);
      this.modelRenderer.func_228301_a_(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F, 0.0F);
      ModelRenderer modelrenderer = new ModelRenderer(32, 32, 4, 0);
      modelrenderer.func_228300_a_(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F);
      this.modelRenderer.addChild(modelrenderer);
      ModelRenderer modelrenderer1 = new ModelRenderer(32, 32, 4, 3);
      modelrenderer1.func_228300_a_(-2.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F);
      this.modelRenderer.addChild(modelrenderer1);
      ModelRenderer modelrenderer2 = new ModelRenderer(32, 32, 0, 0);
      modelrenderer2.func_228301_a_(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.modelRenderer.addChild(modelrenderer2);
      ModelRenderer modelrenderer3 = new ModelRenderer(32, 32, 4, 3);
      modelrenderer3.mirror = true;
      modelrenderer3.func_228300_a_(1.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F);
      this.modelRenderer.addChild(modelrenderer3);
   }

   public void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
      this.modelRenderer.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
   }
}