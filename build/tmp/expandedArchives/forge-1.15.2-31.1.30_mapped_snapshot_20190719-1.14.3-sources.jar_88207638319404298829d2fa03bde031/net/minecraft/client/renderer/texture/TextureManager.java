package net.minecraft.client.renderer.texture;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class TextureManager implements ITickable, AutoCloseable, IFutureReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ResourceLocation RESOURCE_LOCATION_EMPTY = new ResourceLocation("");
   private final Map<ResourceLocation, Texture> mapTextureObjects = Maps.newHashMap();
   private final Set<ITickable> listTickables = Sets.newHashSet();
   private final Map<String, Integer> mapTextureCounters = Maps.newHashMap();
   private final IResourceManager resourceManager;

   public TextureManager(IResourceManager resourceManager) {
      this.resourceManager = resourceManager;
   }

   public void bindTexture(ResourceLocation resource) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            this.func_229269_d_(resource);
         });
      } else {
         this.func_229269_d_(resource);
      }

   }

   private void func_229269_d_(ResourceLocation p_229269_1_) {
      Texture texture = this.mapTextureObjects.get(p_229269_1_);
      if (texture == null) {
         texture = new SimpleTexture(p_229269_1_);
         this.func_229263_a_(p_229269_1_, texture);
      }

      texture.func_229148_d_();
   }

   public void func_229263_a_(ResourceLocation p_229263_1_, Texture p_229263_2_) {
      p_229263_2_ = this.func_230183_b_(p_229263_1_, p_229263_2_);
      Texture texture = this.mapTextureObjects.put(p_229263_1_, p_229263_2_);
      if (texture != p_229263_2_) {
         if (texture != null && texture != MissingTextureSprite.getDynamicTexture()) {
            texture.deleteGlTexture();
            this.listTickables.remove(texture);
         }

         if (p_229263_2_ instanceof ITickable) {
            this.listTickables.add((ITickable)p_229263_2_);
         }
      }

   }

   private Texture func_230183_b_(ResourceLocation p_230183_1_, Texture p_230183_2_) {
      try {
         p_230183_2_.loadTexture(this.resourceManager);
         return p_230183_2_;
      } catch (IOException ioexception) {
         if (p_230183_1_ != RESOURCE_LOCATION_EMPTY) {
            LOGGER.warn("Failed to load texture: {}", p_230183_1_, ioexception);
         }

         return MissingTextureSprite.getDynamicTexture();
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Registering texture");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Resource location being registered");
         crashreportcategory.addDetail("Resource location", p_230183_1_);
         crashreportcategory.addDetail("Texture object class", () -> {
            return p_230183_2_.getClass().getName();
         });
         throw new ReportedException(crashreport);
      }
   }

   @Nullable
   public Texture func_229267_b_(ResourceLocation p_229267_1_) {
      return this.mapTextureObjects.get(p_229267_1_);
   }

   public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture) {
      Integer integer = this.mapTextureCounters.get(name);
      if (integer == null) {
         integer = 1;
      } else {
         integer = integer + 1;
      }

      this.mapTextureCounters.put(name, integer);
      ResourceLocation resourcelocation = new ResourceLocation(String.format("dynamic/%s_%d", name, integer));
      this.func_229263_a_(resourcelocation, texture);
      return resourcelocation;
   }

   public CompletableFuture<Void> loadAsync(ResourceLocation textureLocation, Executor executor) {
      if (!this.mapTextureObjects.containsKey(textureLocation)) {
         PreloadedTexture preloadedtexture = new PreloadedTexture(this.resourceManager, textureLocation, executor);
         this.mapTextureObjects.put(textureLocation, preloadedtexture);
         return preloadedtexture.func_215248_a().thenRunAsync(() -> {
            this.func_229263_a_(textureLocation, preloadedtexture);
         }, TextureManager::func_229262_a_);
      } else {
         return CompletableFuture.completedFuture((Void)null);
      }
   }

   private static void func_229262_a_(Runnable p_229262_0_) {
      Minecraft.getInstance().execute(() -> {
         RenderSystem.recordRenderCall(p_229262_0_::run);
      });
   }

   public void tick() {
      for(ITickable itickable : this.listTickables) {
         itickable.tick();
      }

   }

   public void deleteTexture(ResourceLocation textureLocation) {
      Texture texture = this.func_229267_b_(textureLocation);
      if (texture != null) {
         this.mapTextureObjects.remove(textureLocation); // Forge: fix MC-98707
         TextureUtil.func_225679_a_(texture.getGlTextureId());
      }

   }

   public void close() {
      this.mapTextureObjects.values().forEach(Texture::deleteGlTexture);
      this.mapTextureObjects.clear();
      this.listTickables.clear();
      this.mapTextureCounters.clear();
   }

   public CompletableFuture<Void> reload(IFutureReloadListener.IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
      return CompletableFuture.allOf(MainMenuScreen.loadAsync(this, backgroundExecutor), this.loadAsync(Widget.WIDGETS_LOCATION, backgroundExecutor)).<Void>thenCompose(stage::markCompleteAwaitingOthers).thenAcceptAsync((p_229265_3_) -> {
         MissingTextureSprite.getDynamicTexture();
         RealmsMainScreen.func_227932_a_(this.resourceManager);
         Iterator<Entry<ResourceLocation, Texture>> iterator = this.mapTextureObjects.entrySet().iterator();

         while(iterator.hasNext()) {
            Entry<ResourceLocation, Texture> entry = iterator.next();
            ResourceLocation resourcelocation = entry.getKey();
            Texture texture = entry.getValue();
            if (texture == MissingTextureSprite.getDynamicTexture() && !resourcelocation.equals(MissingTextureSprite.getLocation())) {
               iterator.remove();
            } else {
               texture.func_215244_a(this, resourceManager, resourcelocation, gameExecutor);
            }
         }

      }, (p_229266_0_) -> {
         RenderSystem.recordRenderCall(p_229266_0_::run);
      });
   }
}