package net.minecraft.client.renderer;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.resources.SimpleResource;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class GameRenderer implements AutoCloseable, IResourceManagerReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft mc;
   private final IResourceManager resourceManager;
   private final Random random = new Random();
   private float farPlaneDistance;
   public final FirstPersonRenderer itemRenderer;
   private final MapItemRenderer mapItemRenderer;
   private final RenderTypeBuffers field_228374_i_;
   private int rendererUpdateCount;
   private float fovModifierHand;
   private float fovModifierHandPrev;
   private float bossColorModifier;
   private float bossColorModifierPrev;
   private boolean renderHand = true;
   private boolean drawBlockOutline = true;
   private long timeWorldIcon;
   private long prevFrameTime = Util.milliTime();
   private final LightTexture lightmapTexture;
   private final OverlayTexture field_228375_t_ = new OverlayTexture();
   private boolean debugView;
   private float cameraZoom = 1.0F;
   private float field_228376_w_;
   private float field_228377_x_;
   @Nullable
   private ItemStack itemActivationItem;
   private int itemActivationTicks;
   private float itemActivationOffX;
   private float itemActivationOffY;
   @Nullable
   private ShaderGroup shaderGroup;
   private static final ResourceLocation[] SHADERS_TEXTURES = new ResourceLocation[]{new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json")};
   public static final int SHADER_COUNT = SHADERS_TEXTURES.length;
   private int shaderIndex = SHADER_COUNT;
   private boolean useShader;
   private final ActiveRenderInfo activeRender = new ActiveRenderInfo();

   public GameRenderer(Minecraft p_i225966_1_, IResourceManager p_i225966_2_, RenderTypeBuffers p_i225966_3_) {
      this.mc = p_i225966_1_;
      this.resourceManager = p_i225966_2_;
      this.itemRenderer = p_i225966_1_.getFirstPersonRenderer();
      this.mapItemRenderer = new MapItemRenderer(p_i225966_1_.getTextureManager());
      this.lightmapTexture = new LightTexture(this, p_i225966_1_);
      this.field_228374_i_ = p_i225966_3_;
      this.shaderGroup = null;
   }

   public void close() {
      this.lightmapTexture.close();
      this.mapItemRenderer.close();
      this.field_228375_t_.close();
      this.stopUseShader();
   }

   public void stopUseShader() {
      if (this.shaderGroup != null) {
         this.shaderGroup.close();
      }

      this.shaderGroup = null;
      this.shaderIndex = SHADER_COUNT;
   }

   public void switchUseShader() {
      this.useShader = !this.useShader;
   }

   /**
    * What shader to use when spectating this entity
    */
   public void loadEntityShader(@Nullable Entity entityIn) {
      if (this.shaderGroup != null) {
         this.shaderGroup.close();
      }

      this.shaderGroup = null;
      if (entityIn instanceof CreeperEntity) {
         this.loadShader(new ResourceLocation("shaders/post/creeper.json"));
      } else if (entityIn instanceof SpiderEntity) {
         this.loadShader(new ResourceLocation("shaders/post/spider.json"));
      } else if (entityIn instanceof EndermanEntity) {
         this.loadShader(new ResourceLocation("shaders/post/invert.json"));
      } else {
         net.minecraftforge.client.ForgeHooksClient.loadEntityShader(entityIn, this);
      }

   }

   public void loadShader(ResourceLocation resourceLocationIn) {
      if (this.shaderGroup != null) {
         this.shaderGroup.close();
      }

      try {
         this.shaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocationIn);
         this.shaderGroup.createBindFramebuffers(this.mc.func_228018_at_().getFramebufferWidth(), this.mc.func_228018_at_().getFramebufferHeight());
         this.useShader = true;
      } catch (IOException ioexception) {
         LOGGER.warn("Failed to load shader: {}", resourceLocationIn, ioexception);
         this.shaderIndex = SHADER_COUNT;
         this.useShader = false;
      } catch (JsonSyntaxException jsonsyntaxexception) {
         LOGGER.warn("Failed to load shader: {}", resourceLocationIn, jsonsyntaxexception);
         this.shaderIndex = SHADER_COUNT;
         this.useShader = false;
      }

   }

   public void onResourceManagerReload(IResourceManager resourceManager) {
      if (this.shaderGroup != null) {
         this.shaderGroup.close();
      }

      this.shaderGroup = null;
      if (this.shaderIndex == SHADER_COUNT) {
         this.loadEntityShader(this.mc.getRenderViewEntity());
      } else {
         this.loadShader(SHADERS_TEXTURES[this.shaderIndex]);
      }

   }

   /**
    * Updates the entity renderer
    */
   public void tick() {
      this.updateFovModifierHand();
      this.lightmapTexture.tick();
      if (this.mc.getRenderViewEntity() == null) {
         this.mc.setRenderViewEntity(this.mc.player);
      }

      this.activeRender.interpolateHeight();
      ++this.rendererUpdateCount;
      this.itemRenderer.tick();
      this.mc.worldRenderer.func_228436_a_(this.activeRender);
      this.bossColorModifierPrev = this.bossColorModifier;
      if (this.mc.ingameGUI.getBossOverlay().shouldDarkenSky()) {
         this.bossColorModifier += 0.05F;
         if (this.bossColorModifier > 1.0F) {
            this.bossColorModifier = 1.0F;
         }
      } else if (this.bossColorModifier > 0.0F) {
         this.bossColorModifier -= 0.0125F;
      }

      if (this.itemActivationTicks > 0) {
         --this.itemActivationTicks;
         if (this.itemActivationTicks == 0) {
            this.itemActivationItem = null;
         }
      }

   }

   @Nullable
   public ShaderGroup getShaderGroup() {
      return this.shaderGroup;
   }

   public void updateShaderGroupSize(int width, int height) {
      if (this.shaderGroup != null) {
         this.shaderGroup.createBindFramebuffers(width, height);
      }

      this.mc.worldRenderer.createBindEntityOutlineFbs(width, height);
   }

   /**
    * Gets the block or object that is being moused over.
    */
   public void getMouseOver(float partialTicks) {
      Entity entity = this.mc.getRenderViewEntity();
      if (entity != null) {
         if (this.mc.world != null) {
            this.mc.getProfiler().startSection("pick");
            this.mc.pointedEntity = null;
            double d0 = (double)this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = entity.func_213324_a(d0, partialTicks, false);
            Vec3d vec3d = entity.getEyePosition(partialTicks);
            boolean flag = false;
            int i = 3;
            double d1 = d0;
            if (this.mc.playerController.extendedReach()) {
               d1 = 6.0D;
               d0 = d1;
            } else {
               if (d0 > 3.0D) {
                  flag = true;
               }

               d0 = d0;
            }

            d1 = d1 * d1;
            if (this.mc.objectMouseOver != null) {
               d1 = this.mc.objectMouseOver.getHitVec().squareDistanceTo(vec3d);
            }

            Vec3d vec3d1 = entity.getLook(1.0F);
            Vec3d vec3d2 = vec3d.add(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0);
            float f = 1.0F;
            AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vec3d1.scale(d0)).grow(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityraytraceresult = ProjectileHelper.func_221273_a(entity, vec3d, vec3d2, axisalignedbb, (p_215312_0_) -> {
               return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
            }, d1);
            if (entityraytraceresult != null) {
               Entity entity1 = entityraytraceresult.getEntity();
               Vec3d vec3d3 = entityraytraceresult.getHitVec();
               double d2 = vec3d.squareDistanceTo(vec3d3);
               if (flag && d2 > 9.0D) {
                  this.mc.objectMouseOver = BlockRayTraceResult.createMiss(vec3d3, Direction.getFacingFromVector(vec3d1.x, vec3d1.y, vec3d1.z), new BlockPos(vec3d3));
               } else if (d2 < d1 || this.mc.objectMouseOver == null) {
                  this.mc.objectMouseOver = entityraytraceresult;
                  if (entity1 instanceof LivingEntity || entity1 instanceof ItemFrameEntity) {
                     this.mc.pointedEntity = entity1;
                  }
               }
            }

            this.mc.getProfiler().endSection();
         }
      }
   }

   /**
    * Update FOV modifier hand
    */
   private void updateFovModifierHand() {
      float f = 1.0F;
      if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayerEntity) {
         AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity)this.mc.getRenderViewEntity();
         f = abstractclientplayerentity.getFovModifier();
      }

      this.fovModifierHandPrev = this.fovModifierHand;
      this.fovModifierHand += (f - this.fovModifierHand) * 0.5F;
      if (this.fovModifierHand > 1.5F) {
         this.fovModifierHand = 1.5F;
      }

      if (this.fovModifierHand < 0.1F) {
         this.fovModifierHand = 0.1F;
      }

   }

   private double getFOVModifier(ActiveRenderInfo p_215311_1_, float p_215311_2_, boolean p_215311_3_) {
      if (this.debugView) {
         return 90.0D;
      } else {
         double d0 = 70.0D;
         if (p_215311_3_) {
            d0 = this.mc.gameSettings.fov;
            d0 = d0 * (double)MathHelper.lerp(p_215311_2_, this.fovModifierHandPrev, this.fovModifierHand);
         }

         if (p_215311_1_.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity)p_215311_1_.getRenderViewEntity()).getHealth() <= 0.0F) {
            float f = Math.min((float)((LivingEntity)p_215311_1_.getRenderViewEntity()).deathTime + p_215311_2_, 20.0F);
            d0 /= (double)((1.0F - 500.0F / (f + 500.0F)) * 2.0F + 1.0F);
         }

         IFluidState ifluidstate = p_215311_1_.getFluidState();
         if (!ifluidstate.isEmpty()) {
            d0 = d0 * 60.0D / 70.0D;
         }

         return net.minecraftforge.client.ForgeHooksClient.getFOVModifier(this, p_215311_1_, p_215311_2_, d0);
      }
   }

   private void func_228380_a_(MatrixStack p_228380_1_, float p_228380_2_) {
      if (this.mc.getRenderViewEntity() instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)this.mc.getRenderViewEntity();
         float f = (float)livingentity.hurtTime - p_228380_2_;
         if (livingentity.getHealth() <= 0.0F) {
            float f1 = Math.min((float)livingentity.deathTime + p_228380_2_, 20.0F);
            p_228380_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(40.0F - 8000.0F / (f1 + 200.0F)));
         }

         if (f < 0.0F) {
            return;
         }

         f = f / (float)livingentity.maxHurtTime;
         f = MathHelper.sin(f * f * f * f * (float)Math.PI);
         float f2 = livingentity.attackedAtYaw;
         p_228380_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-f2));
         p_228380_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(-f * 14.0F));
         p_228380_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f2));
      }

   }

   private void func_228383_b_(MatrixStack p_228383_1_, float p_228383_2_) {
      if (this.mc.getRenderViewEntity() instanceof PlayerEntity) {
         PlayerEntity playerentity = (PlayerEntity)this.mc.getRenderViewEntity();
         float f = playerentity.distanceWalkedModified - playerentity.prevDistanceWalkedModified;
         float f1 = -(playerentity.distanceWalkedModified + f * p_228383_2_);
         float f2 = MathHelper.lerp(p_228383_2_, playerentity.prevCameraYaw, playerentity.cameraYaw);
         p_228383_1_.func_227861_a_((double)(MathHelper.sin(f1 * (float)Math.PI) * f2 * 0.5F), (double)(-Math.abs(MathHelper.cos(f1 * (float)Math.PI) * f2)), 0.0D);
         p_228383_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(MathHelper.sin(f1 * (float)Math.PI) * f2 * 3.0F));
         p_228383_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(Math.abs(MathHelper.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F));
      }
   }

   private void func_228381_a_(MatrixStack p_228381_1_, ActiveRenderInfo p_228381_2_, float p_228381_3_) {
      if (!this.debugView) {
         this.func_228379_a_(this.func_228382_a_(p_228381_2_, p_228381_3_, false));
         MatrixStack.Entry matrixstack$entry = p_228381_1_.func_227866_c_();
         matrixstack$entry.func_227870_a_().func_226591_a_();
         matrixstack$entry.func_227872_b_().func_226119_c_();
         p_228381_1_.func_227860_a_();
         this.func_228380_a_(p_228381_1_, p_228381_3_);
         if (this.mc.gameSettings.viewBobbing) {
            this.func_228383_b_(p_228381_1_, p_228381_3_);
         }

         boolean flag = this.mc.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity)this.mc.getRenderViewEntity()).isSleeping();
         if (this.mc.gameSettings.thirdPersonView == 0 && !flag && !this.mc.gameSettings.hideGUI && this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
            this.lightmapTexture.enableLightmap();
            this.itemRenderer.func_228396_a_(p_228381_3_, p_228381_1_, this.field_228374_i_.func_228487_b_(), this.mc.player, this.mc.getRenderManager().func_229085_a_(this.mc.player, p_228381_3_));
            this.lightmapTexture.disableLightmap();
         }

         p_228381_1_.func_227865_b_();
         if (this.mc.gameSettings.thirdPersonView == 0 && !flag) {
            OverlayRenderer.func_228734_a_(this.mc, p_228381_1_);
            this.func_228380_a_(p_228381_1_, p_228381_3_);
         }

         if (this.mc.gameSettings.viewBobbing) {
            this.func_228383_b_(p_228381_1_, p_228381_3_);
         }

      }
   }

   public void func_228379_a_(Matrix4f p_228379_1_) {
      RenderSystem.matrixMode(5889);
      RenderSystem.loadIdentity();
      RenderSystem.multMatrix(p_228379_1_);
      RenderSystem.matrixMode(5888);
   }

   public Matrix4f func_228382_a_(ActiveRenderInfo p_228382_1_, float p_228382_2_, boolean p_228382_3_) {
      MatrixStack matrixstack = new MatrixStack();
      matrixstack.func_227866_c_().func_227870_a_().func_226591_a_();
      if (this.cameraZoom != 1.0F) {
         matrixstack.func_227861_a_((double)this.field_228376_w_, (double)(-this.field_228377_x_), 0.0D);
         matrixstack.func_227862_a_(this.cameraZoom, this.cameraZoom, 1.0F);
      }

      matrixstack.func_227866_c_().func_227870_a_().func_226595_a_(Matrix4f.perspective(this.getFOVModifier(p_228382_1_, p_228382_2_, p_228382_3_), (float)this.mc.func_228018_at_().getFramebufferWidth() / (float)this.mc.func_228018_at_().getFramebufferHeight(), 0.05F, this.farPlaneDistance * 4.0F));
      return matrixstack.func_227866_c_().func_227870_a_();
   }

   public static float getNightVisionBrightness(LivingEntity p_180438_0_, float entitylivingbaseIn) {
      int i = p_180438_0_.getActivePotionEffect(Effects.NIGHT_VISION).getDuration();
      return i > 200 ? 1.0F : 0.7F + MathHelper.sin(((float)i - entitylivingbaseIn) * (float)Math.PI * 0.2F) * 0.3F;
   }

   public void updateCameraAndRender(float partialTicks, long nanoTime, boolean renderWorldIn) {
      if (!this.mc.isGameFocused() && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !this.mc.mouseHelper.isRightDown())) {
         if (Util.milliTime() - this.prevFrameTime > 500L) {
            this.mc.displayInGameMenu(false);
         }
      } else {
         this.prevFrameTime = Util.milliTime();
      }

      if (!this.mc.skipRenderWorld) {
         int i = (int)(this.mc.mouseHelper.getMouseX() * (double)this.mc.func_228018_at_().getScaledWidth() / (double)this.mc.func_228018_at_().getWidth());
         int j = (int)(this.mc.mouseHelper.getMouseY() * (double)this.mc.func_228018_at_().getScaledHeight() / (double)this.mc.func_228018_at_().getHeight());
         MatrixStack matrixstack = new MatrixStack();
         RenderSystem.viewport(0, 0, this.mc.func_228018_at_().getFramebufferWidth(), this.mc.func_228018_at_().getFramebufferHeight());
         if (renderWorldIn && this.mc.world != null) {
            this.mc.getProfiler().startSection("level");
            this.func_228378_a_(partialTicks, nanoTime, matrixstack);
            if (this.mc.isSingleplayer() && this.timeWorldIcon < Util.milliTime() - 1000L) {
               this.timeWorldIcon = Util.milliTime();
               if (!this.mc.getIntegratedServer().isWorldIconSet()) {
                  this.createWorldIcon();
               }
            }

            this.mc.worldRenderer.renderEntityOutlineFramebuffer();
            if (this.shaderGroup != null && this.useShader) {
               RenderSystem.disableBlend();
               RenderSystem.disableDepthTest();
               RenderSystem.disableAlphaTest();
               RenderSystem.enableTexture();
               RenderSystem.matrixMode(5890);
               RenderSystem.pushMatrix();
               RenderSystem.loadIdentity();
               this.shaderGroup.render(partialTicks);
               RenderSystem.popMatrix();
            }

            this.mc.getFramebuffer().bindFramebuffer(true);
         }

         MainWindow mainwindow = this.mc.func_228018_at_();
         RenderSystem.clear(256, Minecraft.IS_RUNNING_ON_MAC);
         RenderSystem.matrixMode(5889);
         RenderSystem.loadIdentity();
         RenderSystem.ortho(0.0D, (double)mainwindow.getFramebufferWidth() / mainwindow.getGuiScaleFactor(), (double)mainwindow.getFramebufferHeight() / mainwindow.getGuiScaleFactor(), 0.0D, 1000.0D, 3000.0D);
         RenderSystem.matrixMode(5888);
         RenderSystem.loadIdentity();
         RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
         RenderHelper.func_227784_d_();
         if (renderWorldIn && this.mc.world != null) {
            this.mc.getProfiler().endStartSection("gui");
            if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
               RenderSystem.defaultAlphaFunc();
               this.renderItemActivation(this.mc.func_228018_at_().getScaledWidth(), this.mc.func_228018_at_().getScaledHeight(), partialTicks);
               this.mc.ingameGUI.renderGameOverlay(partialTicks);
               RenderSystem.clear(256, Minecraft.IS_RUNNING_ON_MAC);
            }

            this.mc.getProfiler().endSection();
         }

         if (this.mc.loadingGui != null) {
            try {
               this.mc.loadingGui.render(i, j, this.mc.getTickLength());
            } catch (Throwable throwable1) {
               CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Rendering overlay");
               CrashReportCategory crashreportcategory = crashreport.makeCategory("Overlay render details");
               crashreportcategory.addDetail("Overlay name", () -> {
                  return this.mc.loadingGui.getClass().getCanonicalName();
               });
               throw new ReportedException(crashreport);
            }
         } else if (this.mc.currentScreen != null) {
            try {
               net.minecraftforge.client.ForgeHooksClient.drawScreen(this.mc.currentScreen, i, j, this.mc.getTickLength());
            } catch (Throwable throwable) {
               CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Rendering screen");
               CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Screen render details");
               crashreportcategory1.addDetail("Screen name", () -> {
                  return this.mc.currentScreen.getClass().getCanonicalName();
               });
               crashreportcategory1.addDetail("Mouse location", () -> {
                  return String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%f, %f)", i, j, this.mc.mouseHelper.getMouseX(), this.mc.mouseHelper.getMouseY());
               });
               crashreportcategory1.addDetail("Screen size", () -> {
                  return String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f", this.mc.func_228018_at_().getScaledWidth(), this.mc.func_228018_at_().getScaledHeight(), this.mc.func_228018_at_().getFramebufferWidth(), this.mc.func_228018_at_().getFramebufferHeight(), this.mc.func_228018_at_().getGuiScaleFactor());
               });
               throw new ReportedException(crashreport1);
            }
         }

      }
   }

   private void createWorldIcon() {
      if (this.mc.worldRenderer.getRenderedChunks() > 10 && this.mc.worldRenderer.hasNoChunkUpdates() && !this.mc.getIntegratedServer().isWorldIconSet()) {
         NativeImage nativeimage = ScreenShotHelper.createScreenshot(this.mc.func_228018_at_().getFramebufferWidth(), this.mc.func_228018_at_().getFramebufferHeight(), this.mc.getFramebuffer());
         SimpleResource.RESOURCE_IO_EXECUTOR.execute(() -> {
            int i = nativeimage.getWidth();
            int j = nativeimage.getHeight();
            int k = 0;
            int l = 0;
            if (i > j) {
               k = (i - j) / 2;
               i = j;
            } else {
               l = (j - i) / 2;
               j = i;
            }

            try (NativeImage nativeimage1 = new NativeImage(64, 64, false)) {
               nativeimage.resizeSubRectTo(k, l, i, j, nativeimage1);
               nativeimage1.write(this.mc.getIntegratedServer().getWorldIconFile());
            } catch (IOException ioexception) {
               LOGGER.warn("Couldn't save auto screenshot", (Throwable)ioexception);
            } finally {
               nativeimage.close();
            }

         });
      }

   }

   private boolean isDrawBlockOutline() {
      if (!this.drawBlockOutline) {
         return false;
      } else {
         Entity entity = this.mc.getRenderViewEntity();
         boolean flag = entity instanceof PlayerEntity && !this.mc.gameSettings.hideGUI;
         if (flag && !((PlayerEntity)entity).abilities.allowEdit) {
            ItemStack itemstack = ((LivingEntity)entity).getHeldItemMainhand();
            RayTraceResult raytraceresult = this.mc.objectMouseOver;
            if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
               BlockPos blockpos = ((BlockRayTraceResult)raytraceresult).getPos();
               BlockState blockstate = this.mc.world.getBlockState(blockpos);
               if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
                  flag = blockstate.getContainer(this.mc.world, blockpos) != null;
               } else {
                  CachedBlockInfo cachedblockinfo = new CachedBlockInfo(this.mc.world, blockpos, false);
                  flag = !itemstack.isEmpty() && (itemstack.canDestroy(this.mc.world.getTags(), cachedblockinfo) || itemstack.canPlaceOn(this.mc.world.getTags(), cachedblockinfo));
               }
            }
         }

         return flag;
      }
   }

   public void func_228378_a_(float p_228378_1_, long p_228378_2_, MatrixStack p_228378_4_) {
      this.lightmapTexture.updateLightmap(p_228378_1_);
      if (this.mc.getRenderViewEntity() == null) {
         this.mc.setRenderViewEntity(this.mc.player);
      }

      this.getMouseOver(p_228378_1_);
      this.mc.getProfiler().startSection("center");
      boolean flag = this.isDrawBlockOutline();
      this.mc.getProfiler().endStartSection("camera");
      ActiveRenderInfo activerenderinfo = this.activeRender;
      this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * 16);
      MatrixStack matrixstack = new MatrixStack();
      matrixstack.func_227866_c_().func_227870_a_().func_226595_a_(this.func_228382_a_(activerenderinfo, p_228378_1_, true));
      this.func_228380_a_(matrixstack, p_228378_1_);
      if (this.mc.gameSettings.viewBobbing) {
         this.func_228383_b_(matrixstack, p_228378_1_);
      }

      float f = MathHelper.lerp(p_228378_1_, this.mc.player.prevTimeInPortal, this.mc.player.timeInPortal);
      if (f > 0.0F) {
         int i = 20;
         if (this.mc.player.isPotionActive(Effects.NAUSEA)) {
            i = 7;
         }

         float f1 = 5.0F / (f * f + 5.0F) - f * 0.04F;
         f1 = f1 * f1;
         Vector3f vector3f = new Vector3f(0.0F, MathHelper.SQRT_2 / 2.0F, MathHelper.SQRT_2 / 2.0F);
         matrixstack.func_227863_a_(vector3f.func_229187_a_(((float)this.rendererUpdateCount + p_228378_1_) * (float)i));
         matrixstack.func_227862_a_(1.0F / f1, 1.0F, 1.0F);
         float f2 = -((float)this.rendererUpdateCount + p_228378_1_) * (float)i;
         matrixstack.func_227863_a_(vector3f.func_229187_a_(f2));
      }

      Matrix4f matrix4f = matrixstack.func_227866_c_().func_227870_a_();
      this.func_228379_a_(matrix4f);
      activerenderinfo.update(this.mc.world, (Entity)(this.mc.getRenderViewEntity() == null ? this.mc.player : this.mc.getRenderViewEntity()), this.mc.gameSettings.thirdPersonView > 0, this.mc.gameSettings.thirdPersonView == 2, p_228378_1_);

      net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup cameraSetup = net.minecraftforge.client.ForgeHooksClient.onCameraSetup(this, activerenderinfo, p_228378_1_);
      activerenderinfo.setAnglesInternal(cameraSetup.getYaw(), cameraSetup.getPitch());
      p_228378_4_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(cameraSetup.getRoll()));

      p_228378_4_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(activerenderinfo.getPitch()));
      p_228378_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(activerenderinfo.getYaw() + 180.0F));
      this.mc.worldRenderer.func_228426_a_(p_228378_4_, p_228378_1_, p_228378_2_, flag, activerenderinfo, this, this.lightmapTexture, matrix4f);
      this.mc.getProfiler().endStartSection("forge_render_last");
      net.minecraftforge.client.ForgeHooksClient.dispatchRenderLast(this.mc.worldRenderer, p_228378_4_, p_228378_2_);
      this.mc.getProfiler().endStartSection("hand");
      if (this.renderHand) {
         RenderSystem.clear(256, Minecraft.IS_RUNNING_ON_MAC);
         this.func_228381_a_(p_228378_4_, activerenderinfo, p_228378_1_);
      }

      this.mc.getProfiler().endSection();
   }

   public void resetData() {
      this.itemActivationItem = null;
      this.mapItemRenderer.clearLoadedMaps();
      this.activeRender.clear();
   }

   public MapItemRenderer getMapItemRenderer() {
      return this.mapItemRenderer;
   }

   public void displayItemActivation(ItemStack stack) {
      this.itemActivationItem = stack;
      this.itemActivationTicks = 40;
      this.itemActivationOffX = this.random.nextFloat() * 2.0F - 1.0F;
      this.itemActivationOffY = this.random.nextFloat() * 2.0F - 1.0F;
   }

   private void renderItemActivation(int widthsp, int heightScaled, float partialTicks) {
      if (this.itemActivationItem != null && this.itemActivationTicks > 0) {
         int i = 40 - this.itemActivationTicks;
         float f = ((float)i + partialTicks) / 40.0F;
         float f1 = f * f;
         float f2 = f * f1;
         float f3 = 10.25F * f2 * f1 - 24.95F * f1 * f1 + 25.5F * f2 - 13.8F * f1 + 4.0F * f;
         float f4 = f3 * (float)Math.PI;
         float f5 = this.itemActivationOffX * (float)(widthsp / 4);
         float f6 = this.itemActivationOffY * (float)(heightScaled / 4);
         RenderSystem.enableAlphaTest();
         RenderSystem.pushMatrix();
         RenderSystem.pushLightingAttributes();
         RenderSystem.enableDepthTest();
         RenderSystem.disableCull();
         MatrixStack matrixstack = new MatrixStack();
         matrixstack.func_227860_a_();
         matrixstack.func_227861_a_((double)((float)(widthsp / 2) + f5 * MathHelper.abs(MathHelper.sin(f4 * 2.0F))), (double)((float)(heightScaled / 2) + f6 * MathHelper.abs(MathHelper.sin(f4 * 2.0F))), -50.0D);
         float f7 = 50.0F + 175.0F * MathHelper.sin(f4);
         matrixstack.func_227862_a_(f7, -f7, f7);
         matrixstack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(900.0F * MathHelper.abs(MathHelper.sin(f4))));
         matrixstack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(6.0F * MathHelper.cos(f * 8.0F)));
         matrixstack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(6.0F * MathHelper.cos(f * 8.0F)));
         IRenderTypeBuffer.Impl irendertypebuffer$impl = this.field_228374_i_.func_228487_b_();
         this.mc.getItemRenderer().func_229110_a_(this.itemActivationItem, ItemCameraTransforms.TransformType.FIXED, 15728880, OverlayTexture.field_229196_a_, matrixstack, irendertypebuffer$impl);
         matrixstack.func_227865_b_();
         irendertypebuffer$impl.func_228461_a_();
         RenderSystem.popAttributes();
         RenderSystem.popMatrix();
         RenderSystem.enableCull();
         RenderSystem.disableDepthTest();
      }
   }

   public float getBossColorModifier(float partialTicks) {
      return MathHelper.lerp(partialTicks, this.bossColorModifierPrev, this.bossColorModifier);
   }

   public float getFarPlaneDistance() {
      return this.farPlaneDistance;
   }

   public ActiveRenderInfo getActiveRenderInfo() {
      return this.activeRender;
   }

   public LightTexture func_228384_l_() {
      return this.lightmapTexture;
   }

   public OverlayTexture func_228385_m_() {
      return this.field_228375_t_;
   }

   @Override
   public net.minecraftforge.resource.IResourceType getResourceType() {
      return net.minecraftforge.resource.VanillaResourceType.SHADERS;
   }
}