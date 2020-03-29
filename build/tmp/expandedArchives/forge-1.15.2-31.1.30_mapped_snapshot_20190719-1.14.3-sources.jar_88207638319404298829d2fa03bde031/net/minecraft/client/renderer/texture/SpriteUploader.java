package net.minecraft.client.renderer.texture;

import java.util.stream.Stream;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SpriteUploader extends ReloadListener<AtlasTexture.SheetData> implements AutoCloseable {
   private final AtlasTexture textureAtlas;
   private final String field_229298_b_;

   public SpriteUploader(TextureManager p_i50905_1_, ResourceLocation atlasTextureLocation, String p_i50905_3_) {
      this.field_229298_b_ = p_i50905_3_;
      this.textureAtlas = new AtlasTexture(atlasTextureLocation);
      p_i50905_1_.func_229263_a_(this.textureAtlas.func_229223_g_(), this.textureAtlas);
   }

   protected abstract Stream<ResourceLocation> func_225640_a_();

   /**
    * Gets a sprite associated with the passed resource location.
    */
   protected TextureAtlasSprite getSprite(ResourceLocation p_215282_1_) {
      return this.textureAtlas.getSprite(this.func_229299_b_(p_215282_1_));
   }

   private ResourceLocation func_229299_b_(ResourceLocation p_229299_1_) {
      return new ResourceLocation(p_229299_1_.getNamespace(), this.field_229298_b_ + "/" + p_229299_1_.getPath());
   }

   /**
    * Performs any reloading that can be done off-thread, such as file IO
    */
   protected AtlasTexture.SheetData prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {
      profilerIn.startTick();
      profilerIn.startSection("stitching");
      AtlasTexture.SheetData atlastexture$sheetdata = this.textureAtlas.func_229220_a_(resourceManagerIn, this.func_225640_a_().map(this::func_229299_b_), profilerIn, 0);
      profilerIn.endSection();
      profilerIn.endTick();
      return atlastexture$sheetdata;
   }

   protected void apply(AtlasTexture.SheetData splashList, IResourceManager resourceManagerIn, IProfiler profilerIn) {
      profilerIn.startTick();
      profilerIn.startSection("upload");
      this.textureAtlas.upload(splashList);
      profilerIn.endSection();
      profilerIn.endTick();
   }

   public void close() {
      this.textureAtlas.clear();
   }
}