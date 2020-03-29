package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CheckboxButton extends AbstractButton {
   private static final ResourceLocation field_212944_b = new ResourceLocation("textures/gui/checkbox.png");
   boolean field_212943_a;

   public CheckboxButton(int p_i51140_1_, int p_i51140_2_, int p_i51140_3_, int p_i51140_4_, String p_i51140_5_, boolean p_i51140_6_) {
      super(p_i51140_1_, p_i51140_2_, p_i51140_3_, p_i51140_4_, p_i51140_5_);
      this.field_212943_a = p_i51140_6_;
   }

   public void onPress() {
      this.field_212943_a = !this.field_212943_a;
   }

   public boolean func_212942_a() {
      return this.field_212943_a;
   }

   public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
      Minecraft minecraft = Minecraft.getInstance();
      minecraft.getTextureManager().bindTexture(field_212944_b);
      RenderSystem.enableDepthTest();
      FontRenderer fontrenderer = minecraft.fontRenderer;
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      blit(this.x, this.y, 0.0F, this.field_212943_a ? 20.0F : 0.0F, 20, this.height, 32, 64);
      this.renderBg(minecraft, p_renderButton_1_, p_renderButton_2_);
      int i = 14737632;
      this.drawString(fontrenderer, this.getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 14737632 | MathHelper.ceil(this.alpha * 255.0F) << 24);
   }
}