package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.FireworkRocketEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FireworkRocketRenderer extends EntityRenderer<FireworkRocketEntity> {
   private final net.minecraft.client.renderer.ItemRenderer field_217761_a;

   public FireworkRocketRenderer(EntityRendererManager p_i50970_1_, net.minecraft.client.renderer.ItemRenderer p_i50970_2_) {
      super(p_i50970_1_);
      this.field_217761_a = p_i50970_2_;
   }

   public void func_225623_a_(FireworkRocketEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      p_225623_4_.func_227860_a_();
      p_225623_4_.func_227863_a_(this.renderManager.func_229098_b_());
      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F));
      if (p_225623_1_.func_213889_i()) {
         p_225623_4_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
         p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F));
         p_225623_4_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
      }

      this.field_217761_a.func_229110_a_(p_225623_1_.getItem(), ItemCameraTransforms.TransformType.GROUND, p_225623_6_, OverlayTexture.field_229196_a_, p_225623_4_, p_225623_5_);
      p_225623_4_.func_227865_b_();
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
   }

   public ResourceLocation getEntityTexture(FireworkRocketEntity entity) {
      return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
   }
}