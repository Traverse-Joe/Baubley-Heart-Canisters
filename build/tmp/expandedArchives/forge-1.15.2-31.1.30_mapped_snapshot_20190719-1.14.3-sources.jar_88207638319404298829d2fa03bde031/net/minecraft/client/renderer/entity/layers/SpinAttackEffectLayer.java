package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpinAttackEffectLayer<T extends LivingEntity> extends LayerRenderer<T, PlayerModel<T>> {
   public static final ResourceLocation field_204836_a = new ResourceLocation("textures/entity/trident_riptide.png");
   private final ModelRenderer field_229143_b_ = new ModelRenderer(64, 64, 0, 0);

   public SpinAttackEffectLayer(IEntityRenderer<T, PlayerModel<T>> p_i50920_1_) {
      super(p_i50920_1_);
      this.field_229143_b_.func_228300_a_(-8.0F, -16.0F, -8.0F, 16.0F, 32.0F, 16.0F);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      if (p_225628_4_.isSpinAttacking()) {
         IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.func_228640_c_(field_204836_a));

         for(int i = 0; i < 3; ++i) {
            p_225628_1_.func_227860_a_();
            float f = p_225628_8_ * (float)(-(45 + i * 5));
            p_225628_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f));
            float f1 = 0.75F * (float)i;
            p_225628_1_.func_227862_a_(f1, f1, f1);
            p_225628_1_.func_227861_a_(0.0D, (double)(-0.2F + 0.6F * (float)i), 0.0D);
            this.field_229143_b_.func_228308_a_(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.field_229196_a_);
            p_225628_1_.func_227865_b_();
         }

      }
   }
}