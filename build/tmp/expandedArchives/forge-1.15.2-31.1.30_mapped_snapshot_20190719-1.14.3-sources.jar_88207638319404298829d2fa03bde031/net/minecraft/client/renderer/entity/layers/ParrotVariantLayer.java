package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.model.ParrotModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParrotVariantLayer<T extends PlayerEntity> extends LayerRenderer<T, PlayerModel<T>> {
   private final ParrotModel field_215346_a = new ParrotModel();

   public ParrotVariantLayer(IEntityRenderer<T, PlayerModel<T>> p_i50929_1_) {
      super(p_i50929_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      this.func_229136_a_(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_9_, p_225628_10_, true);
      this.func_229136_a_(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_9_, p_225628_10_, false);
   }

   private void func_229136_a_(MatrixStack p_229136_1_, IRenderTypeBuffer p_229136_2_, int p_229136_3_, T p_229136_4_, float p_229136_5_, float p_229136_6_, float p_229136_7_, float p_229136_8_, boolean p_229136_9_) {
      CompoundNBT compoundnbt = p_229136_9_ ? p_229136_4_.getLeftShoulderEntity() : p_229136_4_.getRightShoulderEntity();
      EntityType.byKey(compoundnbt.getString("id")).filter((p_215344_0_) -> {
         return p_215344_0_ == EntityType.PARROT;
      }).ifPresent((p_229137_11_) -> {
         p_229136_1_.func_227860_a_();
         p_229136_1_.func_227861_a_(p_229136_9_ ? (double)0.4F : (double)-0.4F, p_229136_4_.isCrouching() ? (double)-1.3F : -1.5D, 0.0D);
         IVertexBuilder ivertexbuilder = p_229136_2_.getBuffer(this.field_215346_a.func_228282_a_(ParrotRenderer.PARROT_TEXTURES[compoundnbt.getInt("Variant")]));
         this.field_215346_a.func_228284_a_(p_229136_1_, ivertexbuilder, p_229136_3_, OverlayTexture.field_229196_a_, p_229136_5_, p_229136_6_, p_229136_7_, p_229136_8_, p_229136_4_.ticksExisted);
         p_229136_1_.func_227865_b_();
      });
   }
}