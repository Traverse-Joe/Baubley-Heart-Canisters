package net.minecraft.client.renderer.texture;

import java.util.stream.Stream;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PaintingSpriteUploader extends SpriteUploader {
   private static final ResourceLocation field_215287_a = new ResourceLocation("back");

   public PaintingSpriteUploader(TextureManager textureManagerIn) {
      super(textureManagerIn, new ResourceLocation("textures/atlas/paintings.png"), "painting");
   }

   protected Stream<ResourceLocation> func_225640_a_() {
      return Stream.concat(Registry.MOTIVE.keySet().stream(), Stream.of(field_215287_a));
   }

   /**
    * Gets the sprite used for a specific painting type.
    */
   public TextureAtlasSprite getSpriteForPainting(PaintingType paintingTypeIn) {
      return this.getSprite(Registry.MOTIVE.getKey(paintingTypeIn));
   }

   public TextureAtlasSprite func_215286_b() {
      return this.getSprite(field_215287_a);
   }
}