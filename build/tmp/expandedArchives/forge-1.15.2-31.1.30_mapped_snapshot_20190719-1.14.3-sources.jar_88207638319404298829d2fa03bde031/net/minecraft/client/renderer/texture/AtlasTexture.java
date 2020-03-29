package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class AtlasTexture extends Texture implements ITickable {
   private static final Logger LOGGER = LogManager.getLogger();
   @Deprecated
   public static final ResourceLocation LOCATION_BLOCKS_TEXTURE = PlayerContainer.field_226615_c_;
   @Deprecated
   public static final ResourceLocation LOCATION_PARTICLES_TEXTURE = new ResourceLocation("textures/atlas/particles.png");
   private final List<TextureAtlasSprite> listAnimatedSprites = Lists.newArrayList();
   private final Set<ResourceLocation> sprites = Sets.newHashSet();
   private final Map<ResourceLocation, TextureAtlasSprite> mapUploadedSprites = Maps.newHashMap();
   private final ResourceLocation field_229214_j_;
   private final int field_215265_o;

   public AtlasTexture(ResourceLocation p_i226047_1_) {
      this.field_229214_j_ = p_i226047_1_;
      this.field_215265_o = RenderSystem.maxSupportedTextureSize();
   }

   public void loadTexture(IResourceManager manager) throws IOException {
   }

   public void upload(AtlasTexture.SheetData p_215260_1_) {
      this.sprites.clear();
      this.sprites.addAll(p_215260_1_.field_217805_a);
      LOGGER.info("Created: {}x{}x{} {}-atlas", p_215260_1_.width, p_215260_1_.height, p_215260_1_.field_229224_d_, this.field_229214_j_);
      TextureUtil.func_225681_a_(this.getGlTextureId(), p_215260_1_.field_229224_d_, p_215260_1_.width, p_215260_1_.height);
      this.clear();

      for(TextureAtlasSprite textureatlassprite : p_215260_1_.sprites) {
         this.mapUploadedSprites.put(textureatlassprite.getName(), textureatlassprite);

         try {
            textureatlassprite.uploadMipmaps();
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Stitching texture atlas");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Texture being stitched together");
            crashreportcategory.addDetail("Atlas path", this.field_229214_j_);
            crashreportcategory.addDetail("Sprite", textureatlassprite);
            throw new ReportedException(crashreport);
         }

         if (textureatlassprite.hasAnimationMetadata()) {
            this.listAnimatedSprites.add(textureatlassprite);
         }
      }

      net.minecraftforge.client.ForgeHooksClient.onTextureStitchedPost(this);
   }

   public AtlasTexture.SheetData func_229220_a_(IResourceManager p_229220_1_, Stream<ResourceLocation> p_229220_2_, IProfiler p_229220_3_, int p_229220_4_) {
      p_229220_3_.startSection("preparing");
      Set<ResourceLocation> set = p_229220_2_.peek((p_229222_0_) -> {
         if (p_229222_0_ == null) {
            throw new IllegalArgumentException("Location cannot be null!");
         }
      }).collect(Collectors.toSet());
      int i = this.field_215265_o;
      Stitcher stitcher = new Stitcher(i, i, p_229220_4_);
      int j = Integer.MAX_VALUE;
      int k = 1 << p_229220_4_;
      p_229220_3_.endStartSection("extracting_frames");
      net.minecraftforge.client.ForgeHooksClient.onTextureStitchedPre(this, set);

      for(TextureAtlasSprite.Info textureatlassprite$info : this.func_215256_a(p_229220_1_, set)) {
         j = Math.min(j, Math.min(textureatlassprite$info.func_229250_b_(), textureatlassprite$info.func_229252_c_()));
         int l = Math.min(Integer.lowestOneBit(textureatlassprite$info.func_229250_b_()), Integer.lowestOneBit(textureatlassprite$info.func_229252_c_()));
         if (l < k) {
            LOGGER.warn("Texture {} with size {}x{} limits mip level from {} to {}", textureatlassprite$info.func_229248_a_(), textureatlassprite$info.func_229250_b_(), textureatlassprite$info.func_229252_c_(), MathHelper.log2(k), MathHelper.log2(l));
            k = l;
         }

         stitcher.func_229211_a_(textureatlassprite$info);
      }

      int i1 = Math.min(j, k);
      int j1 = MathHelper.log2(i1);
      int k1 = p_229220_4_;
      if (false) // FORGE: do not lower the mipmap level
      if (j1 < p_229220_4_) {
         LOGGER.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.field_229214_j_, p_229220_4_, j1, i1);
         k1 = j1;
      } else {
         k1 = p_229220_4_;
      }

      p_229220_3_.endStartSection("register");
      stitcher.func_229211_a_(MissingTextureSprite.func_229177_b_());
      p_229220_3_.endStartSection("stitching");

      try {
         stitcher.doStitch();
      } catch (StitcherException stitcherexception) {
         CrashReport crashreport = CrashReport.makeCrashReport(stitcherexception, "Stitching");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Stitcher");
         crashreportcategory.addDetail("Sprites", stitcherexception.func_225331_a().stream().map((p_229216_0_) -> {
            return String.format("%s[%dx%d]", p_229216_0_.func_229248_a_(), p_229216_0_.func_229250_b_(), p_229216_0_.func_229252_c_());
         }).collect(Collectors.joining(",")));
         crashreportcategory.addDetail("Max Texture Size", i);
         throw new ReportedException(crashreport);
      }

      p_229220_3_.endStartSection("loading");
      List<TextureAtlasSprite> list = this.func_229217_a_(p_229220_1_, stitcher, k1);
      p_229220_3_.endSection();
      return new AtlasTexture.SheetData(set, stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), k1, list);
   }

   private Collection<TextureAtlasSprite.Info> func_215256_a(IResourceManager p_215256_1_, Set<ResourceLocation> p_215256_2_) {
      List<CompletableFuture<?>> list = Lists.newArrayList();
      ConcurrentLinkedQueue<TextureAtlasSprite.Info> concurrentlinkedqueue = new ConcurrentLinkedQueue<>();

      for(ResourceLocation resourcelocation : p_215256_2_) {
         if (!MissingTextureSprite.getLocation().equals(resourcelocation)) {
            list.add(CompletableFuture.runAsync(() -> {
               ResourceLocation resourcelocation1 = this.getSpritePath(resourcelocation);

               TextureAtlasSprite.Info textureatlassprite$info;
               try (IResource iresource = p_215256_1_.getResource(resourcelocation1)) {
                  PngSizeInfo pngsizeinfo = new PngSizeInfo(iresource.toString(), iresource.getInputStream());
                  AnimationMetadataSection animationmetadatasection = iresource.getMetadata(AnimationMetadataSection.SERIALIZER);
                  if (animationmetadatasection == null) {
                     animationmetadatasection = AnimationMetadataSection.field_229300_b_;
                  }

                  Pair<Integer, Integer> pair = animationmetadatasection.func_225641_a_(pngsizeinfo.width, pngsizeinfo.height);
                  textureatlassprite$info = new TextureAtlasSprite.Info(resourcelocation, pair.getFirst(), pair.getSecond(), animationmetadatasection);
               } catch (RuntimeException runtimeexception) {
                  LOGGER.error("Unable to parse metadata from {} : {}", resourcelocation1, runtimeexception);
                  return;
               } catch (IOException ioexception) {
                  LOGGER.error("Using missing texture, unable to load {} : {}", resourcelocation1, ioexception);
                  return;
               }

               concurrentlinkedqueue.add(textureatlassprite$info);
            }, Util.getServerExecutor()));
         }
      }

      CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
      return concurrentlinkedqueue;
   }

   private List<TextureAtlasSprite> func_229217_a_(IResourceManager p_229217_1_, Stitcher p_229217_2_, int p_229217_3_) {
      ConcurrentLinkedQueue<TextureAtlasSprite> concurrentlinkedqueue = new ConcurrentLinkedQueue<>();
      List<CompletableFuture<?>> list = Lists.newArrayList();
      p_229217_2_.func_229209_a_((p_229215_5_, p_229215_6_, p_229215_7_, p_229215_8_, p_229215_9_) -> {
         if (p_229215_5_ == MissingTextureSprite.func_229177_b_()) {
            MissingTextureSprite missingtexturesprite = MissingTextureSprite.func_229176_a_(this, p_229217_3_, p_229215_6_, p_229215_7_, p_229215_8_, p_229215_9_);
            concurrentlinkedqueue.add(missingtexturesprite);
         } else {
            list.add(CompletableFuture.runAsync(() -> {
               TextureAtlasSprite textureatlassprite = this.func_229218_a_(p_229217_1_, p_229215_5_, p_229215_6_, p_229215_7_, p_229217_3_, p_229215_8_, p_229215_9_);
               if (textureatlassprite != null) {
                  concurrentlinkedqueue.add(textureatlassprite);
               }

            }, Util.getServerExecutor()));
         }

      });
      CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
      return Lists.newArrayList(concurrentlinkedqueue);
   }

   @Nullable
   private TextureAtlasSprite func_229218_a_(IResourceManager p_229218_1_, TextureAtlasSprite.Info p_229218_2_, int p_229218_3_, int p_229218_4_, int p_229218_5_, int p_229218_6_, int p_229218_7_) {
      ResourceLocation resourcelocation = this.getSpritePath(p_229218_2_.func_229248_a_());

      try (IResource iresource = p_229218_1_.getResource(resourcelocation)) {
         NativeImage nativeimage = NativeImage.read(iresource.getInputStream());
         TextureAtlasSprite textureatlassprite = new TextureAtlasSprite(this, p_229218_2_, p_229218_5_, p_229218_3_, p_229218_4_, p_229218_6_, p_229218_7_, nativeimage);
         return textureatlassprite;
      } catch (RuntimeException runtimeexception) {
         LOGGER.error("Unable to parse metadata from {}", resourcelocation, runtimeexception);
         return null;
      } catch (IOException ioexception) {
         LOGGER.error("Using missing texture, unable to load {}", resourcelocation, ioexception);
         return null;
      }
   }

   private ResourceLocation getSpritePath(ResourceLocation location) {
      return new ResourceLocation(location.getNamespace(), String.format("textures/%s%s", location.getPath(), ".png"));
   }

   public void updateAnimations() {
      this.func_229148_d_();

      for(TextureAtlasSprite textureatlassprite : this.listAnimatedSprites) {
         textureatlassprite.updateAnimation();
      }

   }

   public void tick() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::updateAnimations);
      } else {
         this.updateAnimations();
      }

   }

   public TextureAtlasSprite getSprite(ResourceLocation location) {
      TextureAtlasSprite textureatlassprite = this.mapUploadedSprites.get(location);
      return textureatlassprite == null ? this.mapUploadedSprites.get(MissingTextureSprite.getLocation()) : textureatlassprite;
   }

   public void clear() {
      for(TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values()) {
         textureatlassprite.close();
      }

      this.mapUploadedSprites.clear();
      this.listAnimatedSprites.clear();
   }

   public ResourceLocation func_229223_g_() {
      return this.field_229214_j_;
   }

   public void func_229221_b_(AtlasTexture.SheetData p_229221_1_) {
      this.setBlurMipmapDirect(false, p_229221_1_.field_229224_d_ > 0);
   }

   @OnlyIn(Dist.CLIENT)
   public static class SheetData {
      final Set<ResourceLocation> field_217805_a;
      final int width;
      final int height;
      final int field_229224_d_;
      final List<TextureAtlasSprite> sprites;

      public SheetData(Set<ResourceLocation> p_i226048_1_, int p_i226048_2_, int p_i226048_3_, int p_i226048_4_, List<TextureAtlasSprite> p_i226048_5_) {
         this.field_217805_a = p_i226048_1_;
         this.width = p_i226048_2_;
         this.height = p_i226048_3_;
         this.field_229224_d_ = p_i226048_4_;
         this.sprites = p_i226048_5_;
      }
   }
}