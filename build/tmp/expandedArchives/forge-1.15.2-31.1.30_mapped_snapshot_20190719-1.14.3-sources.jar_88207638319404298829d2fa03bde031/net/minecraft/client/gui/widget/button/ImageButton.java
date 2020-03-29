package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ImageButton extends Button {
   private final ResourceLocation resourceLocation;
   private final int xTexStart;
   private final int yTexStart;
   private final int yDiffText;
   private final int field_212935_e;
   private final int field_212936_f;

   public ImageButton(int p_i51134_1_, int p_i51134_2_, int p_i51134_3_, int p_i51134_4_, int p_i51134_5_, int p_i51134_6_, int p_i51134_7_, ResourceLocation p_i51134_8_, Button.IPressable p_i51134_9_) {
      this(p_i51134_1_, p_i51134_2_, p_i51134_3_, p_i51134_4_, p_i51134_5_, p_i51134_6_, p_i51134_7_, p_i51134_8_, 256, 256, p_i51134_9_);
   }

   public ImageButton(int p_i51135_1_, int p_i51135_2_, int p_i51135_3_, int p_i51135_4_, int p_i51135_5_, int p_i51135_6_, int p_i51135_7_, ResourceLocation p_i51135_8_, int p_i51135_9_, int p_i51135_10_, Button.IPressable p_i51135_11_) {
      this(p_i51135_1_, p_i51135_2_, p_i51135_3_, p_i51135_4_, p_i51135_5_, p_i51135_6_, p_i51135_7_, p_i51135_8_, p_i51135_9_, p_i51135_10_, p_i51135_11_, "");
   }

   public ImageButton(int p_i51136_1_, int p_i51136_2_, int p_i51136_3_, int p_i51136_4_, int p_i51136_5_, int p_i51136_6_, int p_i51136_7_, ResourceLocation p_i51136_8_, int p_i51136_9_, int p_i51136_10_, Button.IPressable p_i51136_11_, String p_i51136_12_) {
      super(p_i51136_1_, p_i51136_2_, p_i51136_3_, p_i51136_4_, p_i51136_12_, p_i51136_11_);
      this.field_212935_e = p_i51136_9_;
      this.field_212936_f = p_i51136_10_;
      this.xTexStart = p_i51136_5_;
      this.yTexStart = p_i51136_6_;
      this.yDiffText = p_i51136_7_;
      this.resourceLocation = p_i51136_8_;
   }

   public void setPosition(int p_191746_1_, int p_191746_2_) {
      this.x = p_191746_1_;
      this.y = p_191746_2_;
   }

   public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
      Minecraft minecraft = Minecraft.getInstance();
      minecraft.getTextureManager().bindTexture(this.resourceLocation);
      RenderSystem.disableDepthTest();
      int i = this.yTexStart;
      if (this.isHovered()) {
         i += this.yDiffText;
      }

      blit(this.x, this.y, (float)this.xTexStart, (float)i, this.width, this.height, this.field_212935_e, this.field_212936_f);
      RenderSystem.enableDepthTest();
   }
}