package net.minecraft.client.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientResourcePackInfo extends ResourcePackInfo {
   @Nullable
   private NativeImage field_195809_a;
   @Nullable
   private ResourceLocation field_195810_b;

   @Deprecated
   public ClientResourcePackInfo(String p_i232485_1_, boolean p_i232485_2_, Supplier<IResourcePack> p_i232485_3_, IResourcePack p_i232485_4_, PackMetadataSection p_i232485_5_, ResourcePackInfo.Priority p_i232485_6_, IPackNameDecorator p_i232485_7_) {
      this(p_i232485_1_, p_i232485_2_, p_i232485_3_, p_i232485_4_, p_i232485_5_, p_i232485_6_, p_i232485_7_, false);
   }

   public ClientResourcePackInfo(String p_i232485_1_, boolean p_i232485_2_, Supplier<IResourcePack> p_i232485_3_, IResourcePack p_i232485_4_, PackMetadataSection p_i232485_5_, ResourcePackInfo.Priority p_i232485_6_, IPackNameDecorator p_i232485_7_, boolean hidden) {
      super(p_i232485_1_, p_i232485_2_, p_i232485_3_, p_i232485_4_, p_i232485_5_, p_i232485_6_, p_i232485_7_, hidden);
      this.field_195809_a = func_239491_a_(p_i232485_4_);
   }

   @Deprecated
   public ClientResourcePackInfo(String p_i232486_1_, boolean p_i232486_2_, Supplier<IResourcePack> p_i232486_3_, ITextComponent p_i232486_4_, ITextComponent p_i232486_5_, PackCompatibility p_i232486_6_, ResourcePackInfo.Priority p_i232486_7_, boolean p_i232486_8_, IPackNameDecorator p_i232486_9_, @Nullable NativeImage p_i232486_10_) {
      this(p_i232486_1_, p_i232486_2_, p_i232486_3_, p_i232486_4_, p_i232486_5_, p_i232486_6_, p_i232486_7_, p_i232486_8_, p_i232486_9_, p_i232486_10_, false);
   }

   public ClientResourcePackInfo(String p_i232486_1_, boolean p_i232486_2_, Supplier<IResourcePack> p_i232486_3_, ITextComponent p_i232486_4_, ITextComponent p_i232486_5_, PackCompatibility p_i232486_6_, ResourcePackInfo.Priority p_i232486_7_, boolean p_i232486_8_, IPackNameDecorator p_i232486_9_, @Nullable NativeImage p_i232486_10_, boolean hidden) {
      super(p_i232486_1_, p_i232486_2_, p_i232486_3_, p_i232486_4_, p_i232486_5_, p_i232486_6_, p_i232486_7_, p_i232486_8_, p_i232486_9_, hidden);
      this.field_195809_a = p_i232486_10_;
   }

   @Nullable
   public static NativeImage func_239491_a_(IResourcePack p_239491_0_) {
      try (InputStream inputstream = p_239491_0_.getRootResourceStream("pack.png")) {
         return NativeImage.read(inputstream);
      } catch (IllegalArgumentException | IOException ioexception) {
         return null;
      }
   }

   public void func_195808_a(TextureManager p_195808_1_) {
      if (this.field_195810_b == null) {
         if (this.field_195809_a == null) {
            this.field_195810_b = new ResourceLocation("textures/misc/unknown_pack.png");
         } else {
            this.field_195810_b = p_195808_1_.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.field_195809_a));
         }
      }

      p_195808_1_.bindTexture(this.field_195810_b);
   }

   public void close() {
      super.close();
      if (this.field_195809_a != null) {
         this.field_195809_a.close();
         this.field_195809_a = null;
      }

   }
}