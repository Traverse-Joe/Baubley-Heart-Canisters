package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PreloadedTexture extends SimpleTexture {
   @Nullable
   private CompletableFuture<SimpleTexture.TextureData> field_215252_g;

   public PreloadedTexture(IResourceManager p_i50911_1_, ResourceLocation p_i50911_2_, Executor p_i50911_3_) {
      super(p_i50911_2_);
      this.field_215252_g = CompletableFuture.supplyAsync(() -> {
         return SimpleTexture.TextureData.func_217799_a(p_i50911_1_, p_i50911_2_);
      }, p_i50911_3_);
   }

   protected SimpleTexture.TextureData func_215246_b(IResourceManager resourceManager) {
      if (this.field_215252_g != null) {
         SimpleTexture.TextureData simpletexture$texturedata = this.field_215252_g.join();
         this.field_215252_g = null;
         return simpletexture$texturedata;
      } else {
         return SimpleTexture.TextureData.func_217799_a(resourceManager, this.textureLocation);
      }
   }

   public CompletableFuture<Void> func_215248_a() {
      return this.field_215252_g == null ? CompletableFuture.completedFuture((Void)null) : this.field_215252_g.thenApply((p_215247_0_) -> {
         return null;
      });
   }

   public void func_215244_a(TextureManager p_215244_1_, IResourceManager p_215244_2_, ResourceLocation p_215244_3_, Executor p_215244_4_) {
      this.field_215252_g = CompletableFuture.supplyAsync(() -> {
         return SimpleTexture.TextureData.func_217799_a(p_215244_2_, this.textureLocation);
      }, Util.getServerExecutor());
      this.field_215252_g.thenRunAsync(() -> {
         p_215244_1_.func_229263_a_(this.textureLocation, this);
      }, func_229205_a_(p_215244_4_));
   }

   private static Executor func_229205_a_(Executor p_229205_0_) {
      return (p_229206_1_) -> {
         p_229205_0_.execute(() -> {
            RenderSystem.recordRenderCall(p_229206_1_::run);
         });
      };
   }
}