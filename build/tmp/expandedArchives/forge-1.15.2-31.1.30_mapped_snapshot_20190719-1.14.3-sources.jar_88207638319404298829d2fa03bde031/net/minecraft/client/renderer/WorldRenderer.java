package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ILightReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class WorldRenderer implements AutoCloseable, IResourceManagerReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("textures/environment/moon_phases.png");
   private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation CLOUDS_TEXTURES = new ResourceLocation("textures/environment/clouds.png");
   private static final ResourceLocation END_SKY_TEXTURES = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation FORCEFIELD_TEXTURES = new ResourceLocation("textures/misc/forcefield.png");
   private static final ResourceLocation field_228413_h_ = new ResourceLocation("textures/environment/rain.png");
   private static final ResourceLocation field_228414_i_ = new ResourceLocation("textures/environment/snow.png");
   public static final Direction[] FACINGS = Direction.values();
   private final Minecraft mc;
   private final TextureManager textureManager;
   private final EntityRendererManager renderManager;
   private final RenderTypeBuffers field_228415_m_;
   private ClientWorld world;
   private Set<ChunkRenderDispatcher.ChunkRender> chunksToUpdate = Sets.newLinkedHashSet();
   private final ObjectList<WorldRenderer.LocalRenderInformationContainer> renderInfos = new ObjectArrayList<>(69696);
   private final Set<TileEntity> setTileEntities = Sets.newHashSet();
   private ViewFrustum viewFrustum;
   private final VertexFormat vertexBufferFormat = DefaultVertexFormats.POSITION;
   @Nullable
   private VertexBuffer starVBO;
   @Nullable
   private VertexBuffer skyVBO;
   @Nullable
   private VertexBuffer sky2VBO;
   private boolean cloudsNeedUpdate = true;
   @Nullable
   private VertexBuffer cloudsVBO;
   private RenderTimeManager field_228416_y_ = new RenderTimeManager(100);
   private int ticks;
   private final Int2ObjectMap<DestroyBlockProgress> damagedBlocks = new Int2ObjectOpenHashMap<>();
   private final Long2ObjectMap<SortedSet<DestroyBlockProgress>> field_228407_B_ = new Long2ObjectOpenHashMap<>();
   private final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
   private Framebuffer entityOutlineFramebuffer;
   private ShaderGroup entityOutlineShader;
   private double frustumUpdatePosX = Double.MIN_VALUE;
   private double frustumUpdatePosY = Double.MIN_VALUE;
   private double frustumUpdatePosZ = Double.MIN_VALUE;
   private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
   private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
   private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
   private double lastViewEntityX = Double.MIN_VALUE;
   private double lastViewEntityY = Double.MIN_VALUE;
   private double lastViewEntityZ = Double.MIN_VALUE;
   private double lastViewEntityPitch = Double.MIN_VALUE;
   private double lastViewEntityYaw = Double.MIN_VALUE;
   private int cloudsCheckX = Integer.MIN_VALUE;
   private int cloudsCheckY = Integer.MIN_VALUE;
   private int cloudsCheckZ = Integer.MIN_VALUE;
   private Vec3d cloudsCheckColor = Vec3d.ZERO;
   private CloudOption cloudOption;
   private ChunkRenderDispatcher renderDispatcher;
   private final VertexFormat field_228408_W_ = DefaultVertexFormats.BLOCK;
   private int renderDistanceChunks = -1;
   private int countEntitiesRendered;
   private int countEntitiesHidden;
   private boolean debugFixTerrainFrustum;
   @Nullable
   private ClippingHelperImpl debugFixedClippingHelper;
   private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
   private final Vector3d debugTerrainFrustumPosition = new Vector3d(0.0D, 0.0D, 0.0D);
   private double prevRenderSortX;
   private double prevRenderSortY;
   private double prevRenderSortZ;
   private boolean displayListEntitiesDirty = true;
   private int field_228409_ai_;
   private int field_228410_aj_;
   private final float[] field_228411_ak_ = new float[1024];
   private final float[] field_228412_al_ = new float[1024];

   public WorldRenderer(Minecraft p_i225967_1_, RenderTypeBuffers p_i225967_2_) {
      this.mc = p_i225967_1_;
      this.renderManager = p_i225967_1_.getRenderManager();
      this.field_228415_m_ = p_i225967_2_;
      this.textureManager = p_i225967_1_.getTextureManager();

      for(int i = 0; i < 32; ++i) {
         for(int j = 0; j < 32; ++j) {
            float f = (float)(j - 16);
            float f1 = (float)(i - 16);
            float f2 = MathHelper.sqrt(f * f + f1 * f1);
            this.field_228411_ak_[i << 5 | j] = -f1 / f2;
            this.field_228412_al_[i << 5 | j] = f / f2;
         }
      }

      this.generateStars();
      this.generateSky();
      this.generateSky2();
   }

   private void func_228438_a_(LightTexture p_228438_1_, float p_228438_2_, double p_228438_3_, double p_228438_5_, double p_228438_7_) {
      net.minecraftforge.client.IRenderHandler renderer = this.mc.world.getDimension().getWeatherRenderer();
      if (renderer != null) {
         renderer.render(this.ticks, p_228438_2_, this.mc.world, this.mc);
         return;
      }
      float f = this.mc.world.getRainStrength(p_228438_2_);
      if (!(f <= 0.0F)) {
         p_228438_1_.enableLightmap();
         World world = this.mc.world;
         int i = MathHelper.floor(p_228438_3_);
         int j = MathHelper.floor(p_228438_5_);
         int k = MathHelper.floor(p_228438_7_);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         RenderSystem.disableCull();
         RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.defaultAlphaFunc();
         int l = 5;
         if (this.mc.gameSettings.fancyGraphics) {
            l = 10;
         }

         int i1 = -1;
         float f1 = (float)this.ticks + p_228438_2_;
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

         for(int j1 = k - l; j1 <= k + l; ++j1) {
            for(int k1 = i - l; k1 <= i + l; ++k1) {
               int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
               double d0 = (double)this.field_228411_ak_[l1] * 0.5D;
               double d1 = (double)this.field_228412_al_[l1] * 0.5D;
               blockpos$mutable.setPos(k1, 0, j1);
               Biome biome = world.func_226691_t_(blockpos$mutable);
               if (biome.getPrecipitation() != Biome.RainType.NONE) {
                  int i2 = world.getHeight(Heightmap.Type.MOTION_BLOCKING, blockpos$mutable).getY();
                  int j2 = j - l;
                  int k2 = j + l;
                  if (j2 < i2) {
                     j2 = i2;
                  }

                  if (k2 < i2) {
                     k2 = i2;
                  }

                  int l2 = i2;
                  if (i2 < j) {
                     l2 = j;
                  }

                  if (j2 != k2) {
                     Random random = new Random((long)(k1 * k1 * 3121 + k1 * 45238971 ^ j1 * j1 * 418711 + j1 * 13761));
                     blockpos$mutable.setPos(k1, j2, j1);
                     float f2 = biome.func_225486_c(blockpos$mutable);
                     if (f2 >= 0.15F) {
                        if (i1 != 0) {
                           if (i1 >= 0) {
                              tessellator.draw();
                           }

                           i1 = 0;
                           this.mc.getTextureManager().bindTexture(field_228413_h_);
                           bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }

                        int i3 = this.ticks + k1 * k1 * 3121 + k1 * 45238971 + j1 * j1 * 418711 + j1 * 13761 & 31;
                        float f3 = -((float)i3 + p_228438_2_) / 32.0F * (3.0F + random.nextFloat());
                        double d2 = (double)((float)k1 + 0.5F) - p_228438_3_;
                        double d4 = (double)((float)j1 + 0.5F) - p_228438_7_;
                        float f4 = MathHelper.sqrt(d2 * d2 + d4 * d4) / (float)l;
                        float f5 = ((1.0F - f4 * f4) * 0.5F + 0.5F) * f;
                        blockpos$mutable.setPos(k1, l2, j1);
                        int j3 = func_228421_a_(world, blockpos$mutable);
                        bufferbuilder.func_225582_a_((double)k1 - p_228438_3_ - d0 + 0.5D, (double)k2 - p_228438_5_, (double)j1 - p_228438_7_ - d1 + 0.5D).func_225583_a_(0.0F, (float)j2 * 0.25F + f3).func_227885_a_(1.0F, 1.0F, 1.0F, f5).func_227886_a_(j3).endVertex();
                        bufferbuilder.func_225582_a_((double)k1 - p_228438_3_ + d0 + 0.5D, (double)k2 - p_228438_5_, (double)j1 - p_228438_7_ + d1 + 0.5D).func_225583_a_(1.0F, (float)j2 * 0.25F + f3).func_227885_a_(1.0F, 1.0F, 1.0F, f5).func_227886_a_(j3).endVertex();
                        bufferbuilder.func_225582_a_((double)k1 - p_228438_3_ + d0 + 0.5D, (double)j2 - p_228438_5_, (double)j1 - p_228438_7_ + d1 + 0.5D).func_225583_a_(1.0F, (float)k2 * 0.25F + f3).func_227885_a_(1.0F, 1.0F, 1.0F, f5).func_227886_a_(j3).endVertex();
                        bufferbuilder.func_225582_a_((double)k1 - p_228438_3_ - d0 + 0.5D, (double)j2 - p_228438_5_, (double)j1 - p_228438_7_ - d1 + 0.5D).func_225583_a_(0.0F, (float)k2 * 0.25F + f3).func_227885_a_(1.0F, 1.0F, 1.0F, f5).func_227886_a_(j3).endVertex();
                     } else {
                        if (i1 != 1) {
                           if (i1 >= 0) {
                              tessellator.draw();
                           }

                           i1 = 1;
                           this.mc.getTextureManager().bindTexture(field_228414_i_);
                           bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }

                        float f6 = -((float)(this.ticks & 511) + p_228438_2_) / 512.0F;
                        float f7 = (float)(random.nextDouble() + (double)f1 * 0.01D * (double)((float)random.nextGaussian()));
                        float f8 = (float)(random.nextDouble() + (double)(f1 * (float)random.nextGaussian()) * 0.001D);
                        double d3 = (double)((float)k1 + 0.5F) - p_228438_3_;
                        double d5 = (double)((float)j1 + 0.5F) - p_228438_7_;
                        float f9 = MathHelper.sqrt(d3 * d3 + d5 * d5) / (float)l;
                        float f10 = ((1.0F - f9 * f9) * 0.3F + 0.5F) * f;
                        blockpos$mutable.setPos(k1, l2, j1);
                        int k3 = func_228421_a_(world, blockpos$mutable);
                        int l3 = k3 >> 16 & '\uffff';
                        int i4 = (k3 & '\uffff') * 3;
                        int j4 = (l3 * 3 + 240) / 4;
                        int k4 = (i4 * 3 + 240) / 4;
                        bufferbuilder.func_225582_a_((double)k1 - p_228438_3_ - d0 + 0.5D, (double)k2 - p_228438_5_, (double)j1 - p_228438_7_ - d1 + 0.5D).func_225583_a_(0.0F + f7, (float)j2 * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).endVertex();
                        bufferbuilder.func_225582_a_((double)k1 - p_228438_3_ + d0 + 0.5D, (double)k2 - p_228438_5_, (double)j1 - p_228438_7_ + d1 + 0.5D).func_225583_a_(1.0F + f7, (float)j2 * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).endVertex();
                        bufferbuilder.func_225582_a_((double)k1 - p_228438_3_ + d0 + 0.5D, (double)j2 - p_228438_5_, (double)j1 - p_228438_7_ + d1 + 0.5D).func_225583_a_(1.0F + f7, (float)k2 * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).endVertex();
                        bufferbuilder.func_225582_a_((double)k1 - p_228438_3_ - d0 + 0.5D, (double)j2 - p_228438_5_, (double)j1 - p_228438_7_ - d1 + 0.5D).func_225583_a_(0.0F + f7, (float)k2 * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).endVertex();
                     }
                  }
               }
            }
         }

         if (i1 >= 0) {
            tessellator.draw();
         }

         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.defaultAlphaFunc();
         p_228438_1_.disableLightmap();
      }
   }

   public void func_228436_a_(ActiveRenderInfo p_228436_1_) {
      float f = this.mc.world.getRainStrength(1.0F);
      if (!this.mc.gameSettings.fancyGraphics) {
         f /= 2.0F;
      }

      if (f != 0.0F) {
         Random random = new Random((long)this.ticks * 312987231L);
         IWorldReader iworldreader = this.mc.world;
         BlockPos blockpos = new BlockPos(p_228436_1_.getProjectedView());
         int i = 10;
         double d0 = 0.0D;
         double d1 = 0.0D;
         double d2 = 0.0D;
         int j = 0;
         int k = (int)(100.0F * f * f);
         if (this.mc.gameSettings.particles == ParticleStatus.DECREASED) {
            k >>= 1;
         } else if (this.mc.gameSettings.particles == ParticleStatus.MINIMAL) {
            k = 0;
         }

         for(int l = 0; l < k; ++l) {
            BlockPos blockpos1 = iworldreader.getHeight(Heightmap.Type.MOTION_BLOCKING, blockpos.add(random.nextInt(10) - random.nextInt(10), 0, random.nextInt(10) - random.nextInt(10)));
            Biome biome = iworldreader.func_226691_t_(blockpos1);
            BlockPos blockpos2 = blockpos1.down();
            if (blockpos1.getY() <= blockpos.getY() + 10 && blockpos1.getY() >= blockpos.getY() - 10 && biome.getPrecipitation() == Biome.RainType.RAIN && biome.func_225486_c(blockpos1) >= 0.15F) {
               double d3 = random.nextDouble();
               double d4 = random.nextDouble();
               BlockState blockstate = iworldreader.getBlockState(blockpos2);
               IFluidState ifluidstate = iworldreader.getFluidState(blockpos1);
               VoxelShape voxelshape = blockstate.getCollisionShape(iworldreader, blockpos2);
               double d7 = voxelshape.max(Direction.Axis.Y, d3, d4);
               double d8 = (double)ifluidstate.func_215679_a(iworldreader, blockpos1);
               double d5;
               double d6;
               if (d7 >= d8) {
                  d5 = d7;
                  d6 = voxelshape.min(Direction.Axis.Y, d3, d4);
               } else {
                  d5 = 0.0D;
                  d6 = 0.0D;
               }

               if (d5 > -Double.MAX_VALUE) {
                  if (!ifluidstate.isTagged(FluidTags.LAVA) && blockstate.getBlock() != Blocks.MAGMA_BLOCK && (blockstate.getBlock() != Blocks.CAMPFIRE || !blockstate.get(CampfireBlock.LIT))) {
                     ++j;
                     if (random.nextInt(j) == 0) {
                        d0 = (double)blockpos2.getX() + d3;
                        d1 = (double)((float)blockpos2.getY() + 0.1F) + d5 - 1.0D;
                        d2 = (double)blockpos2.getZ() + d4;
                     }

                     this.mc.world.addParticle(ParticleTypes.RAIN, (double)blockpos2.getX() + d3, (double)((float)blockpos2.getY() + 0.1F) + d5, (double)blockpos2.getZ() + d4, 0.0D, 0.0D, 0.0D);
                  } else {
                     this.mc.world.addParticle(ParticleTypes.SMOKE, (double)blockpos1.getX() + d3, (double)((float)blockpos1.getY() + 0.1F) - d6, (double)blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D);
                  }
               }
            }
         }

         if (j > 0 && random.nextInt(3) < this.field_228410_aj_++) {
            this.field_228410_aj_ = 0;
            if (d1 > (double)(blockpos.getY() + 1) && iworldreader.getHeight(Heightmap.Type.MOTION_BLOCKING, blockpos).getY() > MathHelper.floor((float)blockpos.getY())) {
               this.mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
            } else {
               this.mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
            }
         }

      }
   }

   public void close() {
      if (this.entityOutlineShader != null) {
         this.entityOutlineShader.close();
      }

   }

   public void onResourceManagerReload(IResourceManager resourceManager) {
      this.textureManager.bindTexture(FORCEFIELD_TEXTURES);
      RenderSystem.texParameter(3553, 10242, 10497);
      RenderSystem.texParameter(3553, 10243, 10497);
      RenderSystem.bindTexture(0);
      this.makeEntityOutlineShader();
   }

   /**
    * Creates the entity outline shader to be stored in RenderGlobal.entityOutlineShader
    */
   public void makeEntityOutlineShader() {
      if (this.entityOutlineShader != null) {
         this.entityOutlineShader.close();
      }

      ResourceLocation resourcelocation = new ResourceLocation("shaders/post/entity_outline.json");

      try {
         this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocation);
         this.entityOutlineShader.createBindFramebuffers(this.mc.func_228018_at_().getFramebufferWidth(), this.mc.func_228018_at_().getFramebufferHeight());
         this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
      } catch (IOException ioexception) {
         LOGGER.warn("Failed to load shader: {}", resourcelocation, ioexception);
         this.entityOutlineShader = null;
         this.entityOutlineFramebuffer = null;
      } catch (JsonSyntaxException jsonsyntaxexception) {
         LOGGER.warn("Failed to load shader: {}", resourcelocation, jsonsyntaxexception);
         this.entityOutlineShader = null;
         this.entityOutlineFramebuffer = null;
      }

   }

   public void renderEntityOutlineFramebuffer() {
      if (this.isRenderEntityOutlines()) {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
         this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.func_228018_at_().getFramebufferWidth(), this.mc.func_228018_at_().getFramebufferHeight(), false);
         RenderSystem.disableBlend();
      }

   }

   protected boolean isRenderEntityOutlines() {
      return this.entityOutlineFramebuffer != null && this.entityOutlineShader != null && this.mc.player != null;
   }

   private void generateSky2() {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      if (this.sky2VBO != null) {
         this.sky2VBO.close();
      }

      this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
      this.renderSky(bufferbuilder, -16.0F, true);
      bufferbuilder.finishDrawing();
      this.sky2VBO.func_227875_a_(bufferbuilder);
   }

   private void generateSky() {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      if (this.skyVBO != null) {
         this.skyVBO.close();
      }

      this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
      this.renderSky(bufferbuilder, 16.0F, false);
      bufferbuilder.finishDrawing();
      this.skyVBO.func_227875_a_(bufferbuilder);
   }

   private void renderSky(BufferBuilder bufferBuilderIn, float posY, boolean reverseX) {
      int i = 64;
      int j = 6;
      bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);

      for(int k = -384; k <= 384; k += 64) {
         for(int l = -384; l <= 384; l += 64) {
            float f = (float)k;
            float f1 = (float)(k + 64);
            if (reverseX) {
               f1 = (float)k;
               f = (float)(k + 64);
            }

            bufferBuilderIn.func_225582_a_((double)f, (double)posY, (double)l).endVertex();
            bufferBuilderIn.func_225582_a_((double)f1, (double)posY, (double)l).endVertex();
            bufferBuilderIn.func_225582_a_((double)f1, (double)posY, (double)(l + 64)).endVertex();
            bufferBuilderIn.func_225582_a_((double)f, (double)posY, (double)(l + 64)).endVertex();
         }
      }

   }

   private void generateStars() {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      if (this.starVBO != null) {
         this.starVBO.close();
      }

      this.starVBO = new VertexBuffer(this.vertexBufferFormat);
      this.renderStars(bufferbuilder);
      bufferbuilder.finishDrawing();
      this.starVBO.func_227875_a_(bufferbuilder);
   }

   private void renderStars(BufferBuilder bufferBuilderIn) {
      Random random = new Random(10842L);
      bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);

      for(int i = 0; i < 1500; ++i) {
         double d0 = (double)(random.nextFloat() * 2.0F - 1.0F);
         double d1 = (double)(random.nextFloat() * 2.0F - 1.0F);
         double d2 = (double)(random.nextFloat() * 2.0F - 1.0F);
         double d3 = (double)(0.15F + random.nextFloat() * 0.1F);
         double d4 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d4 < 1.0D && d4 > 0.01D) {
            d4 = 1.0D / Math.sqrt(d4);
            d0 = d0 * d4;
            d1 = d1 * d4;
            d2 = d2 * d4;
            double d5 = d0 * 100.0D;
            double d6 = d1 * 100.0D;
            double d7 = d2 * 100.0D;
            double d8 = Math.atan2(d0, d2);
            double d9 = Math.sin(d8);
            double d10 = Math.cos(d8);
            double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
            double d12 = Math.sin(d11);
            double d13 = Math.cos(d11);
            double d14 = random.nextDouble() * Math.PI * 2.0D;
            double d15 = Math.sin(d14);
            double d16 = Math.cos(d14);

            for(int j = 0; j < 4; ++j) {
               double d17 = 0.0D;
               double d18 = (double)((j & 2) - 1) * d3;
               double d19 = (double)((j + 1 & 2) - 1) * d3;
               double d20 = 0.0D;
               double d21 = d18 * d16 - d19 * d15;
               double d22 = d19 * d16 + d18 * d15;
               double d23 = d21 * d12 + 0.0D * d13;
               double d24 = 0.0D * d12 - d21 * d13;
               double d25 = d24 * d9 - d22 * d10;
               double d26 = d22 * d9 + d24 * d10;
               bufferBuilderIn.func_225582_a_(d5 + d25, d6 + d23, d7 + d26).endVertex();
            }
         }
      }

   }

   /**
    * set null to clear
    */
   public void setWorldAndLoadRenderers(@Nullable ClientWorld worldClientIn) {
      this.frustumUpdatePosX = Double.MIN_VALUE;
      this.frustumUpdatePosY = Double.MIN_VALUE;
      this.frustumUpdatePosZ = Double.MIN_VALUE;
      this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
      this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
      this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
      this.renderManager.setWorld(worldClientIn);
      this.world = worldClientIn;
      if (worldClientIn != null) {
         this.loadRenderers();
      } else {
         this.chunksToUpdate.clear();
         this.renderInfos.clear();
         if (this.viewFrustum != null) {
            this.viewFrustum.deleteGlResources();
            this.viewFrustum = null;
         }

         if (this.renderDispatcher != null) {
            this.renderDispatcher.stopWorkerThreads();
         }

         this.renderDispatcher = null;
         this.setTileEntities.clear();
      }

   }

   /**
    * Loads all the renderers and sets up the basic settings usage
    */
   public void loadRenderers() {
      if (this.world != null) {
         this.world.func_228327_h_();
         if (this.renderDispatcher == null) {
            this.renderDispatcher = new ChunkRenderDispatcher(this.world, this, Util.getServerExecutor(), this.mc.isJava64bit(), this.field_228415_m_.func_228484_a_());
         } else {
            this.renderDispatcher.func_228895_a_(this.world);
         }

         this.displayListEntitiesDirty = true;
         this.cloudsNeedUpdate = true;
         RenderTypeLookup.func_228393_a_(this.mc.gameSettings.fancyGraphics);
         this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
         if (this.viewFrustum != null) {
            this.viewFrustum.deleteGlResources();
         }

         this.stopChunkUpdates();
         synchronized(this.setTileEntities) {
            this.setTileEntities.clear();
         }

         this.viewFrustum = new ViewFrustum(this.renderDispatcher, this.world, this.mc.gameSettings.renderDistanceChunks, this);
         if (this.world != null) {
            Entity entity = this.mc.getRenderViewEntity();
            if (entity != null) {
               this.viewFrustum.updateChunkPositions(entity.func_226277_ct_(), entity.func_226281_cx_());
            }
         }

      }
   }

   protected void stopChunkUpdates() {
      this.chunksToUpdate.clear();
      this.renderDispatcher.stopChunkUpdates();
   }

   public void createBindEntityOutlineFbs(int width, int height) {
      this.setDisplayListEntitiesDirty();
      if (this.entityOutlineShader != null) {
         this.entityOutlineShader.createBindFramebuffers(width, height);
      }

   }

   /**
    * Gets the render info for use on the Debug screen
    */
   public String getDebugInfoRenders() {
      int i = this.viewFrustum.renderChunks.length;
      int j = this.getRenderedChunks();
      return String.format("C: %d/%d %sD: %d, %s", j, i, this.mc.renderChunksMany ? "(s) " : "", this.renderDistanceChunks, this.renderDispatcher == null ? "null" : this.renderDispatcher.getDebugInfo());
   }

   protected int getRenderedChunks() {
      int i = 0;

      for(WorldRenderer.LocalRenderInformationContainer worldrenderer$localrenderinformationcontainer : this.renderInfos) {
         if (!worldrenderer$localrenderinformationcontainer.renderChunk.getCompiledChunk().isEmpty()) {
            ++i;
         }
      }

      return i;
   }

   /**
    * Gets the entities info for use on the Debug screen
    */
   public String getDebugInfoEntities() {
      return "E: " + this.countEntitiesRendered + "/" + this.world.func_217425_f() + ", B: " + this.countEntitiesHidden;
   }

   private void func_228437_a_(ActiveRenderInfo p_228437_1_, ClippingHelperImpl p_228437_2_, boolean p_228437_3_, int p_228437_4_, boolean p_228437_5_) {
      Vec3d vec3d = p_228437_1_.getProjectedView();
      if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
         this.loadRenderers();
      }

      this.world.getProfiler().startSection("camera");
      double d0 = this.mc.player.func_226277_ct_() - this.frustumUpdatePosX;
      double d1 = this.mc.player.func_226278_cu_() - this.frustumUpdatePosY;
      double d2 = this.mc.player.func_226281_cx_() - this.frustumUpdatePosZ;
      if (this.frustumUpdatePosChunkX != this.mc.player.chunkCoordX || this.frustumUpdatePosChunkY != this.mc.player.chunkCoordY || this.frustumUpdatePosChunkZ != this.mc.player.chunkCoordZ || d0 * d0 + d1 * d1 + d2 * d2 > 16.0D) {
         this.frustumUpdatePosX = this.mc.player.func_226277_ct_();
         this.frustumUpdatePosY = this.mc.player.func_226278_cu_();
         this.frustumUpdatePosZ = this.mc.player.func_226281_cx_();
         this.frustumUpdatePosChunkX = this.mc.player.chunkCoordX;
         this.frustumUpdatePosChunkY = this.mc.player.chunkCoordY;
         this.frustumUpdatePosChunkZ = this.mc.player.chunkCoordZ;
         this.viewFrustum.updateChunkPositions(this.mc.player.func_226277_ct_(), this.mc.player.func_226281_cx_());
      }

      this.renderDispatcher.func_217669_a(vec3d);
      this.world.getProfiler().endStartSection("cull");
      this.mc.getProfiler().endStartSection("culling");
      BlockPos blockpos = p_228437_1_.getBlockPos();
      ChunkRenderDispatcher.ChunkRender chunkrenderdispatcher$chunkrender = this.viewFrustum.getRenderChunk(blockpos);
      int i = 16;
      BlockPos blockpos1 = new BlockPos(MathHelper.floor(vec3d.x / 16.0D) * 16, MathHelper.floor(vec3d.y / 16.0D) * 16, MathHelper.floor(vec3d.z / 16.0D) * 16);
      float f = p_228437_1_.getPitch();
      float f1 = p_228437_1_.getYaw();
      this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || vec3d.x != this.lastViewEntityX || vec3d.y != this.lastViewEntityY || vec3d.z != this.lastViewEntityZ || (double)f != this.lastViewEntityPitch || (double)f1 != this.lastViewEntityYaw;
      this.lastViewEntityX = vec3d.x;
      this.lastViewEntityY = vec3d.y;
      this.lastViewEntityZ = vec3d.z;
      this.lastViewEntityPitch = (double)f;
      this.lastViewEntityYaw = (double)f1;
      this.mc.getProfiler().endStartSection("update");
      if (!p_228437_3_ && this.displayListEntitiesDirty) {
         this.displayListEntitiesDirty = false;
         this.renderInfos.clear();
         Queue<WorldRenderer.LocalRenderInformationContainer> queue = Queues.newArrayDeque();
         Entity.setRenderDistanceWeight(MathHelper.clamp((double)this.mc.gameSettings.renderDistanceChunks / 8.0D, 1.0D, 2.5D));
         boolean flag = this.mc.renderChunksMany;
         if (chunkrenderdispatcher$chunkrender != null) {
            boolean flag1 = false;
            WorldRenderer.LocalRenderInformationContainer worldrenderer$localrenderinformationcontainer3 = new WorldRenderer.LocalRenderInformationContainer(chunkrenderdispatcher$chunkrender, (Direction)null, 0);
            Set<Direction> set1 = this.getVisibleFacings(blockpos);
            if (set1.size() == 1) {
               Vector3f vector3f = p_228437_1_.func_227996_l_();
               Direction direction1 = Direction.getFacingFromVector(vector3f.getX(), vector3f.getY(), vector3f.getZ()).getOpposite();
               set1.remove(direction1);
            }

            if (set1.isEmpty()) {
               flag1 = true;
            }

            if (flag1 && !p_228437_5_) {
               this.renderInfos.add(worldrenderer$localrenderinformationcontainer3);
            } else {
               if (p_228437_5_ && this.world.getBlockState(blockpos).isOpaqueCube(this.world, blockpos)) {
                  flag = false;
               }

               chunkrenderdispatcher$chunkrender.setFrameIndex(p_228437_4_);
               queue.add(worldrenderer$localrenderinformationcontainer3);
            }
         } else {
            int j = blockpos.getY() > 0 ? 248 : 8;
            int k = MathHelper.floor(vec3d.x / 16.0D) * 16;
            int l = MathHelper.floor(vec3d.z / 16.0D) * 16;
            List<WorldRenderer.LocalRenderInformationContainer> list = Lists.newArrayList();

            for(int i1 = -this.renderDistanceChunks; i1 <= this.renderDistanceChunks; ++i1) {
               for(int j1 = -this.renderDistanceChunks; j1 <= this.renderDistanceChunks; ++j1) {
                  ChunkRenderDispatcher.ChunkRender chunkrenderdispatcher$chunkrender1 = this.viewFrustum.getRenderChunk(new BlockPos(k + (i1 << 4) + 8, j, l + (j1 << 4) + 8));
                  if (chunkrenderdispatcher$chunkrender1 != null && p_228437_2_.func_228957_a_(chunkrenderdispatcher$chunkrender1.boundingBox)) {
                     chunkrenderdispatcher$chunkrender1.setFrameIndex(p_228437_4_);
                     list.add(new WorldRenderer.LocalRenderInformationContainer(chunkrenderdispatcher$chunkrender1, (Direction)null, 0));
                  }
               }
            }

            list.sort(Comparator.comparingDouble((p_230016_1_) -> {
               return blockpos.distanceSq(p_230016_1_.renderChunk.getPosition().add(8, 8, 8));
            }));
            queue.addAll(list);
         }

         this.mc.getProfiler().startSection("iteration");

         while(!queue.isEmpty()) {
            WorldRenderer.LocalRenderInformationContainer worldrenderer$localrenderinformationcontainer1 = queue.poll();
            ChunkRenderDispatcher.ChunkRender chunkrenderdispatcher$chunkrender3 = worldrenderer$localrenderinformationcontainer1.renderChunk;
            Direction direction = worldrenderer$localrenderinformationcontainer1.facing;
            this.renderInfos.add(worldrenderer$localrenderinformationcontainer1);

            for(Direction direction2 : FACINGS) {
               ChunkRenderDispatcher.ChunkRender chunkrenderdispatcher$chunkrender2 = this.getRenderChunkOffset(blockpos1, chunkrenderdispatcher$chunkrender3, direction2);
               if ((!flag || !worldrenderer$localrenderinformationcontainer1.hasDirection(direction2.getOpposite())) && (!flag || direction == null || chunkrenderdispatcher$chunkrender3.getCompiledChunk().isVisible(direction.getOpposite(), direction2)) && chunkrenderdispatcher$chunkrender2 != null && chunkrenderdispatcher$chunkrender2.shouldStayLoaded() && chunkrenderdispatcher$chunkrender2.setFrameIndex(p_228437_4_) && p_228437_2_.func_228957_a_(chunkrenderdispatcher$chunkrender2.boundingBox)) {
                  WorldRenderer.LocalRenderInformationContainer worldrenderer$localrenderinformationcontainer = new WorldRenderer.LocalRenderInformationContainer(chunkrenderdispatcher$chunkrender2, direction2, worldrenderer$localrenderinformationcontainer1.counter + 1);
                  worldrenderer$localrenderinformationcontainer.setDirection(worldrenderer$localrenderinformationcontainer1.setFacing, direction2);
                  queue.add(worldrenderer$localrenderinformationcontainer);
               }
            }
         }

         this.mc.getProfiler().endSection();
      }

      this.mc.getProfiler().endStartSection("rebuildNear");
      Set<ChunkRenderDispatcher.ChunkRender> set = this.chunksToUpdate;
      this.chunksToUpdate = Sets.newLinkedHashSet();

      for(WorldRenderer.LocalRenderInformationContainer worldrenderer$localrenderinformationcontainer2 : this.renderInfos) {
         ChunkRenderDispatcher.ChunkRender chunkrenderdispatcher$chunkrender4 = worldrenderer$localrenderinformationcontainer2.renderChunk;
         if (chunkrenderdispatcher$chunkrender4.needsUpdate() || set.contains(chunkrenderdispatcher$chunkrender4)) {
            this.displayListEntitiesDirty = true;
            BlockPos blockpos2 = chunkrenderdispatcher$chunkrender4.getPosition().add(8, 8, 8);
            boolean flag2 = blockpos2.distanceSq(blockpos) < 768.0D;
            if (net.minecraftforge.common.ForgeConfig.CLIENT.alwaysSetupTerrainOffThread.get() || !chunkrenderdispatcher$chunkrender4.needsImmediateUpdate() && !flag2) {
               this.chunksToUpdate.add(chunkrenderdispatcher$chunkrender4);
            } else {
               this.mc.getProfiler().startSection("build near");
               this.renderDispatcher.func_228902_a_(chunkrenderdispatcher$chunkrender4);
               chunkrenderdispatcher$chunkrender4.clearNeedsUpdate();
               this.mc.getProfiler().endSection();
            }
         }
      }

      this.chunksToUpdate.addAll(set);
      this.mc.getProfiler().endSection();
   }

   private Set<Direction> getVisibleFacings(BlockPos pos) {
      VisGraph visgraph = new VisGraph();
      BlockPos blockpos = new BlockPos(pos.getX() >> 4 << 4, pos.getY() >> 4 << 4, pos.getZ() >> 4 << 4);
      Chunk chunk = this.world.getChunkAt(blockpos);

      for(BlockPos blockpos1 : BlockPos.getAllInBoxMutable(blockpos, blockpos.add(15, 15, 15))) {
         if (chunk.getBlockState(blockpos1).isOpaqueCube(this.world, blockpos1)) {
            visgraph.setOpaqueCube(blockpos1);
         }
      }

      return visgraph.getVisibleFacings(pos);
   }

   /**
    * Returns RenderChunk offset from given RenderChunk in given direction, or null if it can't be seen by player at
    * given BlockPos.
    */
   @Nullable
   private ChunkRenderDispatcher.ChunkRender getRenderChunkOffset(BlockPos playerPos, ChunkRenderDispatcher.ChunkRender renderChunkBase, Direction facing) {
      BlockPos blockpos = renderChunkBase.getBlockPosOffset16(facing);
      if (MathHelper.abs(playerPos.getX() - blockpos.getX()) > this.renderDistanceChunks * 16) {
         return null;
      } else if (blockpos.getY() >= 0 && blockpos.getY() < 256) {
         return MathHelper.abs(playerPos.getZ() - blockpos.getZ()) > this.renderDistanceChunks * 16 ? null : this.viewFrustum.getRenderChunk(blockpos);
      } else {
         return null;
      }
   }

   private void func_228419_a_(Matrix4f p_228419_1_, Matrix4f p_228419_2_, double p_228419_3_, double p_228419_5_, double p_228419_7_, ClippingHelperImpl p_228419_9_) {
      this.debugFixedClippingHelper = p_228419_9_;
      Matrix4f matrix4f = p_228419_2_.func_226601_d_();
      matrix4f.func_226595_a_(p_228419_1_);
      matrix4f.func_226600_c_();
      this.debugTerrainFrustumPosition.x = p_228419_3_;
      this.debugTerrainFrustumPosition.y = p_228419_5_;
      this.debugTerrainFrustumPosition.z = p_228419_7_;
      this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
      this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
      this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

      for(int i = 0; i < 8; ++i) {
         this.debugTerrainMatrix[i].func_229372_a_(matrix4f);
         this.debugTerrainMatrix[i].func_229375_f_();
      }

   }

   public void func_228426_a_(MatrixStack p_228426_1_, float p_228426_2_, long p_228426_3_, boolean p_228426_5_, ActiveRenderInfo p_228426_6_, GameRenderer p_228426_7_, LightTexture p_228426_8_, Matrix4f p_228426_9_) {
      TileEntityRendererDispatcher.instance.func_217665_a(this.world, this.mc.getTextureManager(), this.mc.fontRenderer, p_228426_6_, this.mc.objectMouseOver);
      this.renderManager.func_229088_a_(this.world, p_228426_6_, this.mc.pointedEntity);
      IProfiler iprofiler = this.world.getProfiler();
      iprofiler.endStartSection("light_updates");
      this.mc.world.getChunkProvider().getLightManager().tick(Integer.MAX_VALUE, true, true);
      Vec3d vec3d = p_228426_6_.getProjectedView();
      double d0 = vec3d.getX();
      double d1 = vec3d.getY();
      double d2 = vec3d.getZ();
      Matrix4f matrix4f = p_228426_1_.func_227866_c_().func_227870_a_();
      iprofiler.endStartSection("culling");
      boolean flag = this.debugFixedClippingHelper != null;
      ClippingHelperImpl clippinghelperimpl;
      if (flag) {
         clippinghelperimpl = this.debugFixedClippingHelper;
         clippinghelperimpl.func_228952_a_(this.debugTerrainFrustumPosition.x, this.debugTerrainFrustumPosition.y, this.debugTerrainFrustumPosition.z);
      } else {
         clippinghelperimpl = new ClippingHelperImpl(matrix4f, p_228426_9_);
         clippinghelperimpl.func_228952_a_(d0, d1, d2);
      }

      this.mc.getProfiler().endStartSection("captureFrustum");
      if (this.debugFixTerrainFrustum) {
         this.func_228419_a_(matrix4f, p_228426_9_, vec3d.x, vec3d.y, vec3d.z, flag ? new ClippingHelperImpl(matrix4f, p_228426_9_) : clippinghelperimpl);
         this.debugFixTerrainFrustum = false;
      }

      iprofiler.endStartSection("clear");
      FogRenderer.func_228371_a_(p_228426_6_, p_228426_2_, this.mc.world, this.mc.gameSettings.renderDistanceChunks, p_228426_7_.getBossColorModifier(p_228426_2_));
      RenderSystem.clear(16640, Minecraft.IS_RUNNING_ON_MAC);
      float f = p_228426_7_.getFarPlaneDistance();
      boolean flag1 = this.mc.world.dimension.doesXZShowFog(MathHelper.floor(d0), MathHelper.floor(d1)) || this.mc.ingameGUI.getBossOverlay().shouldCreateFog();
      if (this.mc.gameSettings.renderDistanceChunks >= 4) {
         FogRenderer.setupFog(p_228426_6_, FogRenderer.FogType.FOG_SKY, f, flag1, p_228426_2_);
         iprofiler.endStartSection("sky");
         this.func_228424_a_(p_228426_1_, p_228426_2_);
      }

      iprofiler.endStartSection("fog");
      FogRenderer.setupFog(p_228426_6_, FogRenderer.FogType.FOG_TERRAIN, Math.max(f - 16.0F, 32.0F), flag1, p_228426_2_);
      iprofiler.endStartSection("terrain_setup");
      this.func_228437_a_(p_228426_6_, clippinghelperimpl, flag, this.field_228409_ai_++, this.mc.player.isSpectator());
      iprofiler.endStartSection("updatechunks");
      int i = 30;
      int j = this.mc.gameSettings.framerateLimit;
      long k = 33333333L;
      long l;
      if ((double)j == AbstractOption.FRAMERATE_LIMIT.getMaxValue()) {
         l = 0L;
      } else {
         l = (long)(1000000000 / j);
      }

      long i1 = Util.nanoTime() - p_228426_3_;
      long j1 = this.field_228416_y_.func_228732_a_(i1);
      long k1 = j1 * 3L / 2L;
      long l1 = MathHelper.func_226163_a_(k1, l, 33333333L);
      this.updateChunks(p_228426_3_ + l1);
      iprofiler.endStartSection("terrain");
      this.func_228441_a_(RenderType.func_228639_c_(), p_228426_1_, d0, d1, d2);
      this.mc.getModelManager().func_229356_a_(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, this.mc.gameSettings.mipmapLevels > 0); // FORGE: fix flickering leaves when mods mess up the blurMipmap settings
      this.func_228441_a_(RenderType.func_228641_d_(), p_228426_1_, d0, d1, d2);
      this.mc.getModelManager().func_229356_a_(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
      this.func_228441_a_(RenderType.func_228643_e_(), p_228426_1_, d0, d1, d2);
      RenderHelper.func_227781_a_(p_228426_1_.func_227866_c_().func_227870_a_());
      iprofiler.endStartSection("entities");
      iprofiler.startSection("prepare");
      this.countEntitiesRendered = 0;
      this.countEntitiesHidden = 0;
      iprofiler.endStartSection("entities");
      if (this.isRenderEntityOutlines()) {
         this.entityOutlineFramebuffer.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
         this.mc.getFramebuffer().bindFramebuffer(false);
      }

      boolean flag2 = false;
      IRenderTypeBuffer.Impl irendertypebuffer$impl = this.field_228415_m_.func_228487_b_();

      for(Entity entity : this.world.getAllEntities()) {
         if ((this.renderManager.func_229086_a_(entity, clippinghelperimpl, d0, d1, d2) || entity.isRidingOrBeingRiddenBy(this.mc.player)) && (entity != p_228426_6_.getRenderViewEntity() || p_228426_6_.isThirdPerson() || p_228426_6_.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity)p_228426_6_.getRenderViewEntity()).isSleeping()) && (!(entity instanceof ClientPlayerEntity) || p_228426_6_.getRenderViewEntity() == entity)) {
            ++this.countEntitiesRendered;
            if (entity.ticksExisted == 0) {
               entity.lastTickPosX = entity.func_226277_ct_();
               entity.lastTickPosY = entity.func_226278_cu_();
               entity.lastTickPosZ = entity.func_226281_cx_();
            }

            IRenderTypeBuffer irendertypebuffer;
            if (this.isRenderEntityOutlines() && entity.func_225510_bt_()) {
               flag2 = true;
               OutlineLayerBuffer outlinelayerbuffer = this.field_228415_m_.func_228490_d_();
               irendertypebuffer = outlinelayerbuffer;
               int i2 = entity.func_226263_P_();
               int j2 = 255;
               int k2 = i2 >> 16 & 255;
               int l2 = i2 >> 8 & 255;
               int i3 = i2 & 255;
               outlinelayerbuffer.func_228472_a_(k2, l2, i3, 255);
            } else {
               irendertypebuffer = irendertypebuffer$impl;
            }

            this.func_228418_a_(entity, d0, d1, d2, p_228426_2_, p_228426_1_, irendertypebuffer);
         }
      }

      this.func_228423_a_(p_228426_1_);
      irendertypebuffer$impl.func_228462_a_(RenderType.func_228634_a_(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
      irendertypebuffer$impl.func_228462_a_(RenderType.func_228638_b_(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
      irendertypebuffer$impl.func_228462_a_(RenderType.func_228640_c_(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
      irendertypebuffer$impl.func_228462_a_(RenderType.func_228646_f_(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
      iprofiler.endStartSection("blockentities");

      for(WorldRenderer.LocalRenderInformationContainer worldrenderer$localrenderinformationcontainer : this.renderInfos) {
         List<TileEntity> list = worldrenderer$localrenderinformationcontainer.renderChunk.getCompiledChunk().getTileEntities();
         if (!list.isEmpty()) {
            for(TileEntity tileentity1 : list) {
               if(!clippinghelperimpl.func_228957_a_(tileentity1.getRenderBoundingBox())) continue;
               BlockPos blockpos3 = tileentity1.getPos();
               IRenderTypeBuffer irendertypebuffer1 = irendertypebuffer$impl;
               p_228426_1_.func_227860_a_();
               p_228426_1_.func_227861_a_((double)blockpos3.getX() - d0, (double)blockpos3.getY() - d1, (double)blockpos3.getZ() - d2);
               SortedSet<DestroyBlockProgress> sortedset = this.field_228407_B_.get(blockpos3.toLong());
               if (sortedset != null && !sortedset.isEmpty()) {
                  int k3 = sortedset.last().getPartialBlockDamage();
                  if (k3 >= 0) {
                     IVertexBuilder ivertexbuilder = new MatrixApplyingVertexBuilder(this.field_228415_m_.func_228489_c_().getBuffer(ModelBakery.field_229320_k_.get(k3)), p_228426_1_.func_227866_c_());
                     irendertypebuffer1 = (p_230014_2_) -> {
                        IVertexBuilder ivertexbuilder3 = irendertypebuffer$impl.getBuffer(p_230014_2_);
                        return p_230014_2_.func_228665_s_() ? VertexBuilderUtils.func_227915_a_(ivertexbuilder, ivertexbuilder3) : ivertexbuilder3;
                     };
                  }
               }

               TileEntityRendererDispatcher.instance.func_228850_a_(tileentity1, p_228426_2_, p_228426_1_, irendertypebuffer1);
               p_228426_1_.func_227865_b_();
            }
         }
      }

      synchronized(this.setTileEntities) {
         for(TileEntity tileentity : this.setTileEntities) {
            if(!clippinghelperimpl.func_228957_a_(tileentity.getRenderBoundingBox())) continue;
            BlockPos blockpos2 = tileentity.getPos();
            p_228426_1_.func_227860_a_();
            p_228426_1_.func_227861_a_((double)blockpos2.getX() - d0, (double)blockpos2.getY() - d1, (double)blockpos2.getZ() - d2);
            TileEntityRendererDispatcher.instance.func_228850_a_(tileentity, p_228426_2_, p_228426_1_, irendertypebuffer$impl);
            p_228426_1_.func_227865_b_();
         }
      }

      this.func_228423_a_(p_228426_1_);
      irendertypebuffer$impl.func_228462_a_(RenderType.func_228639_c_());
      irendertypebuffer$impl.func_228462_a_(Atlases.func_228782_g_());
      irendertypebuffer$impl.func_228462_a_(Atlases.func_228783_h_());
      irendertypebuffer$impl.func_228462_a_(Atlases.func_228778_c_());
      irendertypebuffer$impl.func_228462_a_(Atlases.func_228779_d_());
      irendertypebuffer$impl.func_228462_a_(Atlases.func_228780_e_());
      irendertypebuffer$impl.func_228462_a_(Atlases.func_228781_f_());
      this.field_228415_m_.func_228490_d_().func_228471_a_();
      if (flag2) {
         this.entityOutlineShader.render(p_228426_2_);
         this.mc.getFramebuffer().bindFramebuffer(false);
      }

      iprofiler.endStartSection("destroyProgress");

      for(Entry<SortedSet<DestroyBlockProgress>> entry : this.field_228407_B_.long2ObjectEntrySet()) {
         BlockPos blockpos1 = BlockPos.fromLong(entry.getLongKey());
         double d3 = (double)blockpos1.getX() - d0;
         double d4 = (double)blockpos1.getY() - d1;
         double d5 = (double)blockpos1.getZ() - d2;
         if (!(d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D)) {
            SortedSet<DestroyBlockProgress> sortedset1 = entry.getValue();
            if (sortedset1 != null && !sortedset1.isEmpty()) {
               int j3 = sortedset1.last().getPartialBlockDamage();
               p_228426_1_.func_227860_a_();
               p_228426_1_.func_227861_a_((double)blockpos1.getX() - d0, (double)blockpos1.getY() - d1, (double)blockpos1.getZ() - d2);
               IVertexBuilder ivertexbuilder1 = new MatrixApplyingVertexBuilder(this.field_228415_m_.func_228489_c_().getBuffer(ModelBakery.field_229320_k_.get(j3)), p_228426_1_.func_227866_c_());
               this.mc.getBlockRendererDispatcher().func_228792_a_(this.world.getBlockState(blockpos1), blockpos1, this.world, p_228426_1_, ivertexbuilder1);
               p_228426_1_.func_227865_b_();
            }
         }
      }

      this.func_228423_a_(p_228426_1_);
      iprofiler.endSection();
      RayTraceResult raytraceresult = this.mc.objectMouseOver;
      if (p_228426_5_ && raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
         iprofiler.endStartSection("outline");
         BlockPos blockpos = ((BlockRayTraceResult)raytraceresult).getPos();
         BlockState blockstate = this.world.getBlockState(blockpos);
         if (!net.minecraftforge.client.ForgeHooksClient.onDrawBlockHighlight(this, p_228426_6_, mc.objectMouseOver, p_228426_2_, p_228426_1_, irendertypebuffer$impl))
         if (!blockstate.isAir(this.world, blockpos) && this.world.getWorldBorder().contains(blockpos)) {
            IVertexBuilder ivertexbuilder2 = irendertypebuffer$impl.getBuffer(RenderType.func_228659_m_());
            this.func_228429_a_(p_228426_1_, ivertexbuilder2, p_228426_6_.getRenderViewEntity(), d0, d1, d2, blockpos, blockstate);
         }
      }

      RenderSystem.pushMatrix();
      RenderSystem.multMatrix(p_228426_1_.func_227866_c_().func_227870_a_());
      this.mc.debugRenderer.func_229019_a_(p_228426_1_, irendertypebuffer$impl, d0, d1, d2);
      this.func_228447_c_(p_228426_6_);
      RenderSystem.popMatrix();
      irendertypebuffer$impl.func_228462_a_(Atlases.func_228784_i_());
      irendertypebuffer$impl.func_228462_a_(Atlases.func_228768_a_());
      irendertypebuffer$impl.func_228462_a_(Atlases.func_228776_b_());
      irendertypebuffer$impl.func_228462_a_(RenderType.func_228653_j_());
      irendertypebuffer$impl.func_228462_a_(RenderType.func_228655_k_());
      irendertypebuffer$impl.func_228462_a_(RenderType.func_228651_i_());
      this.field_228415_m_.func_228489_c_().func_228461_a_();
      irendertypebuffer$impl.func_228462_a_(RenderType.func_228659_m_());
      irendertypebuffer$impl.func_228461_a_();
      iprofiler.endStartSection("translucent");
      this.func_228441_a_(RenderType.func_228645_f_(), p_228426_1_, d0, d1, d2);
      iprofiler.endStartSection("particles");
      this.mc.particles.func_228345_a_(p_228426_1_, irendertypebuffer$impl, p_228426_8_, p_228426_6_, p_228426_2_);
      RenderSystem.pushMatrix();
      RenderSystem.multMatrix(p_228426_1_.func_227866_c_().func_227870_a_());
      iprofiler.endStartSection("cloudsLayers");
      if (this.mc.gameSettings.getCloudOption() != CloudOption.OFF) {
         iprofiler.endStartSection("clouds");
         this.func_228425_a_(p_228426_1_, p_228426_2_, d0, d1, d2);
      }

      RenderSystem.depthMask(false);
      iprofiler.endStartSection("weather");
      this.func_228438_a_(p_228426_8_, p_228426_2_, d0, d1, d2);
      RenderSystem.depthMask(true);
      this.func_228446_b_(p_228426_6_);
      RenderSystem.shadeModel(7424);
      RenderSystem.depthMask(true);
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
      FogRenderer.func_228370_a_();
   }

   private void func_228423_a_(MatrixStack p_228423_1_) {
      if (!p_228423_1_.func_227867_d_()) {
         throw new IllegalStateException("Pose stack not empty");
      }
   }

   private void func_228418_a_(Entity p_228418_1_, double p_228418_2_, double p_228418_4_, double p_228418_6_, float p_228418_8_, MatrixStack p_228418_9_, IRenderTypeBuffer p_228418_10_) {
      double d0 = MathHelper.lerp((double)p_228418_8_, p_228418_1_.lastTickPosX, p_228418_1_.func_226277_ct_());
      double d1 = MathHelper.lerp((double)p_228418_8_, p_228418_1_.lastTickPosY, p_228418_1_.func_226278_cu_());
      double d2 = MathHelper.lerp((double)p_228418_8_, p_228418_1_.lastTickPosZ, p_228418_1_.func_226281_cx_());
      float f = MathHelper.lerp(p_228418_8_, p_228418_1_.prevRotationYaw, p_228418_1_.rotationYaw);
      this.renderManager.func_229084_a_(p_228418_1_, d0 - p_228418_2_, d1 - p_228418_4_, d2 - p_228418_6_, f, p_228418_8_, p_228418_9_, p_228418_10_, this.renderManager.func_229085_a_(p_228418_1_, p_228418_8_));
   }

   private void func_228441_a_(RenderType p_228441_1_, MatrixStack p_228441_2_, double p_228441_3_, double p_228441_5_, double p_228441_7_) {
      p_228441_1_.func_228547_a_();
      if (p_228441_1_ == RenderType.func_228645_f_()) {
         this.mc.getProfiler().startSection("translucent_sort");
         double d0 = p_228441_3_ - this.prevRenderSortX;
         double d1 = p_228441_5_ - this.prevRenderSortY;
         double d2 = p_228441_7_ - this.prevRenderSortZ;
         if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0D) {
            this.prevRenderSortX = p_228441_3_;
            this.prevRenderSortY = p_228441_5_;
            this.prevRenderSortZ = p_228441_7_;
            int i = 0;

            for(WorldRenderer.LocalRenderInformationContainer worldrenderer$localrenderinformationcontainer : this.renderInfos) {
               if (i < 15 && worldrenderer$localrenderinformationcontainer.renderChunk.func_228925_a_(p_228441_1_, this.renderDispatcher)) {
                  ++i;
               }
            }
         }

         this.mc.getProfiler().endSection();
      }

      this.mc.getProfiler().startSection("filterempty");
      this.mc.getProfiler().endStartSection(() -> {
         return "render_" + p_228441_1_;
      });
      boolean flag = p_228441_1_ != RenderType.func_228645_f_();
      ObjectListIterator<WorldRenderer.LocalRenderInformationContainer> objectlistiterator = this.renderInfos.listIterator(flag ? 0 : this.renderInfos.size());

      while(true) {
         if (flag) {
            if (!objectlistiterator.hasNext()) {
               break;
            }
         } else if (!objectlistiterator.hasPrevious()) {
            break;
         }

         WorldRenderer.LocalRenderInformationContainer worldrenderer$localrenderinformationcontainer1 = flag ? objectlistiterator.next() : objectlistiterator.previous();
         ChunkRenderDispatcher.ChunkRender chunkrenderdispatcher$chunkrender = worldrenderer$localrenderinformationcontainer1.renderChunk;
         if (!chunkrenderdispatcher$chunkrender.getCompiledChunk().func_228912_a_(p_228441_1_)) {
            VertexBuffer vertexbuffer = chunkrenderdispatcher$chunkrender.func_228924_a_(p_228441_1_);
            p_228441_2_.func_227860_a_();
            BlockPos blockpos = chunkrenderdispatcher$chunkrender.getPosition();
            p_228441_2_.func_227861_a_((double)blockpos.getX() - p_228441_3_, (double)blockpos.getY() - p_228441_5_, (double)blockpos.getZ() - p_228441_7_);
            vertexbuffer.bindBuffer();
            this.field_228408_W_.func_227892_a_(0L);
            vertexbuffer.func_227874_a_(p_228441_2_.func_227866_c_().func_227870_a_(), 7);
            p_228441_2_.func_227865_b_();
         }
      }

      VertexBuffer.unbindBuffer();
      RenderSystem.clearCurrentColor();
      this.field_228408_W_.func_227895_d_();
      this.mc.getProfiler().endSection();
      p_228441_1_.func_228549_b_();
   }

   private void func_228446_b_(ActiveRenderInfo p_228446_1_) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      if (this.mc.field_228004_B_ || this.mc.field_228005_C_) {
         double d0 = p_228446_1_.getProjectedView().getX();
         double d1 = p_228446_1_.getProjectedView().getY();
         double d2 = p_228446_1_.getProjectedView().getZ();
         RenderSystem.depthMask(true);
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableTexture();

         for(WorldRenderer.LocalRenderInformationContainer worldrenderer$localrenderinformationcontainer : this.renderInfos) {
            ChunkRenderDispatcher.ChunkRender chunkrenderdispatcher$chunkrender = worldrenderer$localrenderinformationcontainer.renderChunk;
            RenderSystem.pushMatrix();
            BlockPos blockpos = chunkrenderdispatcher$chunkrender.getPosition();
            RenderSystem.translated((double)blockpos.getX() - d0, (double)blockpos.getY() - d1, (double)blockpos.getZ() - d2);
            if (this.mc.field_228004_B_) {
               bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
               RenderSystem.lineWidth(10.0F);
               int i = worldrenderer$localrenderinformationcontainer.counter == 0 ? 0 : MathHelper.hsvToRGB((float)worldrenderer$localrenderinformationcontainer.counter / 50.0F, 0.9F, 0.9F);
               int j = i >> 16 & 255;
               int k = i >> 8 & 255;
               int l = i & 255;
               Direction direction = worldrenderer$localrenderinformationcontainer.facing;
               if (direction != null) {
                  bufferbuilder.func_225582_a_(8.0D, 8.0D, 8.0D).func_225586_a_(j, k, l, 255).endVertex();
                  bufferbuilder.func_225582_a_((double)(8 - 16 * direction.getXOffset()), (double)(8 - 16 * direction.getYOffset()), (double)(8 - 16 * direction.getZOffset())).func_225586_a_(j, k, l, 255).endVertex();
               }

               tessellator.draw();
               RenderSystem.lineWidth(1.0F);
            }

            if (this.mc.field_228005_C_ && !chunkrenderdispatcher$chunkrender.getCompiledChunk().isEmpty()) {
               bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
               RenderSystem.lineWidth(10.0F);
               int i1 = 0;

               for(Direction direction2 : Direction.values()) {
                  for(Direction direction1 : Direction.values()) {
                     boolean flag = chunkrenderdispatcher$chunkrender.getCompiledChunk().isVisible(direction2, direction1);
                     if (!flag) {
                        ++i1;
                        bufferbuilder.func_225582_a_((double)(8 + 8 * direction2.getXOffset()), (double)(8 + 8 * direction2.getYOffset()), (double)(8 + 8 * direction2.getZOffset())).func_225586_a_(1, 0, 0, 1).endVertex();
                        bufferbuilder.func_225582_a_((double)(8 + 8 * direction1.getXOffset()), (double)(8 + 8 * direction1.getYOffset()), (double)(8 + 8 * direction1.getZOffset())).func_225586_a_(1, 0, 0, 1).endVertex();
                     }
                  }
               }

               tessellator.draw();
               RenderSystem.lineWidth(1.0F);
               if (i1 > 0) {
                  bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
                  float f = 0.5F;
                  float f1 = 0.2F;
                  bufferbuilder.func_225582_a_(0.5D, 15.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 15.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 15.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 15.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 0.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 0.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 0.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 0.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 15.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 15.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 0.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 0.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 0.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 0.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 15.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 15.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 0.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 0.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 15.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 15.5D, 0.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 15.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 15.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(15.5D, 0.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  bufferbuilder.func_225582_a_(0.5D, 0.5D, 15.5D).func_227885_a_(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  tessellator.draw();
               }
            }

            RenderSystem.popMatrix();
         }

         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
         RenderSystem.enableTexture();
      }

      if (this.debugFixedClippingHelper != null) {
         RenderSystem.disableCull();
         RenderSystem.disableTexture();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.lineWidth(10.0F);
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(this.debugTerrainFrustumPosition.x - p_228446_1_.getProjectedView().x), (float)(this.debugTerrainFrustumPosition.y - p_228446_1_.getProjectedView().y), (float)(this.debugTerrainFrustumPosition.z - p_228446_1_.getProjectedView().z));
         RenderSystem.depthMask(true);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         this.func_228434_a_(bufferbuilder, 0, 1, 2, 3, 0, 1, 1);
         this.func_228434_a_(bufferbuilder, 4, 5, 6, 7, 1, 0, 0);
         this.func_228434_a_(bufferbuilder, 0, 1, 5, 4, 1, 1, 0);
         this.func_228434_a_(bufferbuilder, 2, 3, 7, 6, 0, 0, 1);
         this.func_228434_a_(bufferbuilder, 0, 4, 7, 3, 0, 1, 0);
         this.func_228434_a_(bufferbuilder, 1, 5, 6, 2, 1, 0, 1);
         tessellator.draw();
         RenderSystem.depthMask(false);
         bufferbuilder.begin(1, DefaultVertexFormats.POSITION);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_228433_a_(bufferbuilder, 0);
         this.func_228433_a_(bufferbuilder, 1);
         this.func_228433_a_(bufferbuilder, 1);
         this.func_228433_a_(bufferbuilder, 2);
         this.func_228433_a_(bufferbuilder, 2);
         this.func_228433_a_(bufferbuilder, 3);
         this.func_228433_a_(bufferbuilder, 3);
         this.func_228433_a_(bufferbuilder, 0);
         this.func_228433_a_(bufferbuilder, 4);
         this.func_228433_a_(bufferbuilder, 5);
         this.func_228433_a_(bufferbuilder, 5);
         this.func_228433_a_(bufferbuilder, 6);
         this.func_228433_a_(bufferbuilder, 6);
         this.func_228433_a_(bufferbuilder, 7);
         this.func_228433_a_(bufferbuilder, 7);
         this.func_228433_a_(bufferbuilder, 4);
         this.func_228433_a_(bufferbuilder, 0);
         this.func_228433_a_(bufferbuilder, 4);
         this.func_228433_a_(bufferbuilder, 1);
         this.func_228433_a_(bufferbuilder, 5);
         this.func_228433_a_(bufferbuilder, 2);
         this.func_228433_a_(bufferbuilder, 6);
         this.func_228433_a_(bufferbuilder, 3);
         this.func_228433_a_(bufferbuilder, 7);
         tessellator.draw();
         RenderSystem.popMatrix();
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
         RenderSystem.enableTexture();
         RenderSystem.lineWidth(1.0F);
      }

   }

   private void func_228433_a_(IVertexBuilder p_228433_1_, int p_228433_2_) {
      p_228433_1_.func_225582_a_((double)this.debugTerrainMatrix[p_228433_2_].getX(), (double)this.debugTerrainMatrix[p_228433_2_].getY(), (double)this.debugTerrainMatrix[p_228433_2_].getZ()).endVertex();
   }

   private void func_228434_a_(IVertexBuilder p_228434_1_, int p_228434_2_, int p_228434_3_, int p_228434_4_, int p_228434_5_, int p_228434_6_, int p_228434_7_, int p_228434_8_) {
      float f = 0.25F;
      p_228434_1_.func_225582_a_((double)this.debugTerrainMatrix[p_228434_2_].getX(), (double)this.debugTerrainMatrix[p_228434_2_].getY(), (double)this.debugTerrainMatrix[p_228434_2_].getZ()).func_227885_a_((float)p_228434_6_, (float)p_228434_7_, (float)p_228434_8_, 0.25F).endVertex();
      p_228434_1_.func_225582_a_((double)this.debugTerrainMatrix[p_228434_3_].getX(), (double)this.debugTerrainMatrix[p_228434_3_].getY(), (double)this.debugTerrainMatrix[p_228434_3_].getZ()).func_227885_a_((float)p_228434_6_, (float)p_228434_7_, (float)p_228434_8_, 0.25F).endVertex();
      p_228434_1_.func_225582_a_((double)this.debugTerrainMatrix[p_228434_4_].getX(), (double)this.debugTerrainMatrix[p_228434_4_].getY(), (double)this.debugTerrainMatrix[p_228434_4_].getZ()).func_227885_a_((float)p_228434_6_, (float)p_228434_7_, (float)p_228434_8_, 0.25F).endVertex();
      p_228434_1_.func_225582_a_((double)this.debugTerrainMatrix[p_228434_5_].getX(), (double)this.debugTerrainMatrix[p_228434_5_].getY(), (double)this.debugTerrainMatrix[p_228434_5_].getZ()).func_227885_a_((float)p_228434_6_, (float)p_228434_7_, (float)p_228434_8_, 0.25F).endVertex();
   }

   public void tick() {
      ++this.ticks;
      if (this.ticks % 20 == 0) {
         Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();

         while(iterator.hasNext()) {
            DestroyBlockProgress destroyblockprogress = iterator.next();
            int i = destroyblockprogress.getCreationCloudUpdateTick();
            if (this.ticks - i > 400) {
               iterator.remove();
               this.func_228442_a_(destroyblockprogress);
            }
         }

      }
   }

   private void func_228442_a_(DestroyBlockProgress p_228442_1_) {
      long i = p_228442_1_.getPosition().toLong();
      Set<DestroyBlockProgress> set = this.field_228407_B_.get(i);
      set.remove(p_228442_1_);
      if (set.isEmpty()) {
         this.field_228407_B_.remove(i);
      }

   }

   private void func_228444_b_(MatrixStack p_228444_1_) {
      RenderSystem.disableAlphaTest();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.depthMask(false);
      this.textureManager.bindTexture(END_SKY_TEXTURES);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();

      for(int i = 0; i < 6; ++i) {
         p_228444_1_.func_227860_a_();
         if (i == 1) {
            p_228444_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
         }

         if (i == 2) {
            p_228444_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-90.0F));
         }

         if (i == 3) {
            p_228444_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180.0F));
         }

         if (i == 4) {
            p_228444_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
         }

         if (i == 5) {
            p_228444_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(-90.0F));
         }

         Matrix4f matrix4f = p_228444_1_.func_227866_c_().func_227870_a_();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         bufferbuilder.func_227888_a_(matrix4f, -100.0F, -100.0F, -100.0F).func_225583_a_(0.0F, 0.0F).func_225586_a_(40, 40, 40, 255).endVertex();
         bufferbuilder.func_227888_a_(matrix4f, -100.0F, -100.0F, 100.0F).func_225583_a_(0.0F, 16.0F).func_225586_a_(40, 40, 40, 255).endVertex();
         bufferbuilder.func_227888_a_(matrix4f, 100.0F, -100.0F, 100.0F).func_225583_a_(16.0F, 16.0F).func_225586_a_(40, 40, 40, 255).endVertex();
         bufferbuilder.func_227888_a_(matrix4f, 100.0F, -100.0F, -100.0F).func_225583_a_(16.0F, 0.0F).func_225586_a_(40, 40, 40, 255).endVertex();
         tessellator.draw();
         p_228444_1_.func_227865_b_();
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.enableAlphaTest();
   }

   public void func_228424_a_(MatrixStack p_228424_1_, float p_228424_2_) {
      net.minecraftforge.client.IRenderHandler renderer = this.world.getDimension().getSkyRenderer();
      if (renderer != null) {
         renderer.render(this.ticks, p_228424_2_, this.world, this.mc);
         return;
      }
      if (this.mc.world.dimension.getType() == DimensionType.THE_END) {
         this.func_228444_b_(p_228424_1_);
      } else if (this.mc.world.dimension.isSurfaceWorld()) {
         RenderSystem.disableTexture();
         Vec3d vec3d = this.world.func_228318_a_(this.mc.gameRenderer.getActiveRenderInfo().getBlockPos(), p_228424_2_);
         float f = (float)vec3d.x;
         float f1 = (float)vec3d.y;
         float f2 = (float)vec3d.z;
         FogRenderer.func_228373_b_();
         BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
         RenderSystem.depthMask(false);
         RenderSystem.enableFog();
         RenderSystem.color3f(f, f1, f2);
         this.skyVBO.bindBuffer();
         this.vertexBufferFormat.func_227892_a_(0L);
         this.skyVBO.func_227874_a_(p_228424_1_.func_227866_c_().func_227870_a_(), 7);
         VertexBuffer.unbindBuffer();
         this.vertexBufferFormat.func_227895_d_();
         RenderSystem.disableFog();
         RenderSystem.disableAlphaTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         float[] afloat = this.world.dimension.calcSunriseSunsetColors(this.world.getCelestialAngle(p_228424_2_), p_228424_2_);
         if (afloat != null) {
            RenderSystem.disableTexture();
            RenderSystem.shadeModel(7425);
            p_228424_1_.func_227860_a_();
            p_228424_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
            float f3 = MathHelper.sin(this.world.getCelestialAngleRadians(p_228424_2_)) < 0.0F ? 180.0F : 0.0F;
            p_228424_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(f3));
            p_228424_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
            float f4 = afloat[0];
            float f5 = afloat[1];
            float f6 = afloat[2];
            Matrix4f matrix4f = p_228424_1_.func_227866_c_().func_227870_a_();
            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.func_227888_a_(matrix4f, 0.0F, 100.0F, 0.0F).func_227885_a_(f4, f5, f6, afloat[3]).endVertex();
            int i = 16;

            for(int j = 0; j <= 16; ++j) {
               float f7 = (float)j * ((float)Math.PI * 2F) / 16.0F;
               float f8 = MathHelper.sin(f7);
               float f9 = MathHelper.cos(f7);
               bufferbuilder.func_227888_a_(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).func_227885_a_(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
            }

            bufferbuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferbuilder);
            p_228424_1_.func_227865_b_();
            RenderSystem.shadeModel(7424);
         }

         RenderSystem.enableTexture();
         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         p_228424_1_.func_227860_a_();
         float f11 = 1.0F - this.world.getRainStrength(p_228424_2_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, f11);
         p_228424_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-90.0F));
         p_228424_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(this.world.getCelestialAngle(p_228424_2_) * 360.0F));
         Matrix4f matrix4f1 = p_228424_1_.func_227866_c_().func_227870_a_();
         float f12 = 30.0F;
         this.textureManager.bindTexture(SUN_TEXTURES);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.func_227888_a_(matrix4f1, -f12, 100.0F, -f12).func_225583_a_(0.0F, 0.0F).endVertex();
         bufferbuilder.func_227888_a_(matrix4f1, f12, 100.0F, -f12).func_225583_a_(1.0F, 0.0F).endVertex();
         bufferbuilder.func_227888_a_(matrix4f1, f12, 100.0F, f12).func_225583_a_(1.0F, 1.0F).endVertex();
         bufferbuilder.func_227888_a_(matrix4f1, -f12, 100.0F, f12).func_225583_a_(0.0F, 1.0F).endVertex();
         bufferbuilder.finishDrawing();
         WorldVertexBufferUploader.draw(bufferbuilder);
         f12 = 20.0F;
         this.textureManager.bindTexture(MOON_PHASES_TEXTURES);
         int k = this.world.getMoonPhase();
         int l = k % 4;
         int i1 = k / 4 % 2;
         float f13 = (float)(l + 0) / 4.0F;
         float f14 = (float)(i1 + 0) / 2.0F;
         float f15 = (float)(l + 1) / 4.0F;
         float f16 = (float)(i1 + 1) / 2.0F;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.func_227888_a_(matrix4f1, -f12, -100.0F, f12).func_225583_a_(f15, f16).endVertex();
         bufferbuilder.func_227888_a_(matrix4f1, f12, -100.0F, f12).func_225583_a_(f13, f16).endVertex();
         bufferbuilder.func_227888_a_(matrix4f1, f12, -100.0F, -f12).func_225583_a_(f13, f14).endVertex();
         bufferbuilder.func_227888_a_(matrix4f1, -f12, -100.0F, -f12).func_225583_a_(f15, f14).endVertex();
         bufferbuilder.finishDrawing();
         WorldVertexBufferUploader.draw(bufferbuilder);
         RenderSystem.disableTexture();
         float f10 = this.world.func_228330_j_(p_228424_2_) * f11;
         if (f10 > 0.0F) {
            RenderSystem.color4f(f10, f10, f10, f10);
            this.starVBO.bindBuffer();
            this.vertexBufferFormat.func_227892_a_(0L);
            this.starVBO.func_227874_a_(p_228424_1_.func_227866_c_().func_227870_a_(), 7);
            VertexBuffer.unbindBuffer();
            this.vertexBufferFormat.func_227895_d_();
         }

         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
         RenderSystem.enableAlphaTest();
         RenderSystem.enableFog();
         p_228424_1_.func_227865_b_();
         RenderSystem.disableTexture();
         RenderSystem.color3f(0.0F, 0.0F, 0.0F);
         double d0 = this.mc.player.getEyePosition(p_228424_2_).y - this.world.func_228331_m_();
         if (d0 < 0.0D) {
            p_228424_1_.func_227860_a_();
            p_228424_1_.func_227861_a_(0.0D, 12.0D, 0.0D);
            this.sky2VBO.bindBuffer();
            this.vertexBufferFormat.func_227892_a_(0L);
            this.sky2VBO.func_227874_a_(p_228424_1_.func_227866_c_().func_227870_a_(), 7);
            VertexBuffer.unbindBuffer();
            this.vertexBufferFormat.func_227895_d_();
            p_228424_1_.func_227865_b_();
         }

         if (this.world.dimension.isSkyColored()) {
            RenderSystem.color3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
         } else {
            RenderSystem.color3f(f, f1, f2);
         }

         RenderSystem.enableTexture();
         RenderSystem.depthMask(true);
         RenderSystem.disableFog();
      }
   }

   public void func_228425_a_(MatrixStack p_228425_1_, float p_228425_2_, double p_228425_3_, double p_228425_5_, double p_228425_7_) {
      if (this.mc.world.dimension.isSurfaceWorld()) {
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.enableAlphaTest();
         RenderSystem.enableDepthTest();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.defaultBlendFunc();
         RenderSystem.enableFog();
         float f = 12.0F;
         float f1 = 4.0F;
         double d0 = 2.0E-4D;
         double d1 = (double)(((float)this.ticks + p_228425_2_) * 0.03F);
         double d2 = (p_228425_3_ + d1) / 12.0D;
         double d3 = (double)(this.world.dimension.getCloudHeight() - (float)p_228425_5_ + 0.33F);
         double d4 = p_228425_7_ / 12.0D + (double)0.33F;
         d2 = d2 - (double)(MathHelper.floor(d2 / 2048.0D) * 2048);
         d4 = d4 - (double)(MathHelper.floor(d4 / 2048.0D) * 2048);
         float f2 = (float)(d2 - (double)MathHelper.floor(d2));
         float f3 = (float)(d3 / 4.0D - (double)MathHelper.floor(d3 / 4.0D)) * 4.0F;
         float f4 = (float)(d4 - (double)MathHelper.floor(d4));
         Vec3d vec3d = this.world.func_228328_h_(p_228425_2_);
         int i = (int)Math.floor(d2);
         int j = (int)Math.floor(d3 / 4.0D);
         int k = (int)Math.floor(d4);
         if (i != this.cloudsCheckX || j != this.cloudsCheckY || k != this.cloudsCheckZ || this.mc.gameSettings.getCloudOption() != this.cloudOption || this.cloudsCheckColor.squareDistanceTo(vec3d) > 2.0E-4D) {
            this.cloudsCheckX = i;
            this.cloudsCheckY = j;
            this.cloudsCheckZ = k;
            this.cloudsCheckColor = vec3d;
            this.cloudOption = this.mc.gameSettings.getCloudOption();
            this.cloudsNeedUpdate = true;
         }

         if (this.cloudsNeedUpdate) {
            this.cloudsNeedUpdate = false;
            BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
            if (this.cloudsVBO != null) {
               this.cloudsVBO.close();
            }

            this.cloudsVBO = new VertexBuffer(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
            this.drawClouds(bufferbuilder, d2, d3, d4, vec3d);
            bufferbuilder.finishDrawing();
            this.cloudsVBO.func_227875_a_(bufferbuilder);
         }

         this.textureManager.bindTexture(CLOUDS_TEXTURES);
         p_228425_1_.func_227860_a_();
         p_228425_1_.func_227862_a_(12.0F, 1.0F, 12.0F);
         p_228425_1_.func_227861_a_((double)(-f2), (double)f3, (double)(-f4));
         if (this.cloudsVBO != null) {
            this.cloudsVBO.bindBuffer();
            DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.func_227892_a_(0L);
            int i1 = this.cloudOption == CloudOption.FANCY ? 0 : 1;

            for(int l = i1; l < 2; ++l) {
               if (l == 0) {
                  RenderSystem.colorMask(false, false, false, false);
               } else {
                  RenderSystem.colorMask(true, true, true, true);
               }

               this.cloudsVBO.func_227874_a_(p_228425_1_.func_227866_c_().func_227870_a_(), 7);
            }

            VertexBuffer.unbindBuffer();
            DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.func_227895_d_();
         }

         p_228425_1_.func_227865_b_();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableAlphaTest();
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.disableFog();
      }
   }

   private void drawClouds(BufferBuilder bufferIn, double cloudsX, double cloudsY, double cloudsZ, Vec3d cloudsColor) {
      float f = 4.0F;
      float f1 = 0.00390625F;
      int i = 8;
      int j = 4;
      float f2 = 9.765625E-4F;
      float f3 = (float)MathHelper.floor(cloudsX) * 0.00390625F;
      float f4 = (float)MathHelper.floor(cloudsZ) * 0.00390625F;
      float f5 = (float)cloudsColor.x;
      float f6 = (float)cloudsColor.y;
      float f7 = (float)cloudsColor.z;
      float f8 = f5 * 0.9F;
      float f9 = f6 * 0.9F;
      float f10 = f7 * 0.9F;
      float f11 = f5 * 0.7F;
      float f12 = f6 * 0.7F;
      float f13 = f7 * 0.7F;
      float f14 = f5 * 0.8F;
      float f15 = f6 * 0.8F;
      float f16 = f7 * 0.8F;
      bufferIn.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
      float f17 = (float)Math.floor(cloudsY / 4.0D) * 4.0F;
      if (this.cloudOption == CloudOption.FANCY) {
         for(int k = -3; k <= 4; ++k) {
            for(int l = -3; l <= 4; ++l) {
               float f18 = (float)(k * 8);
               float f19 = (float)(l * 8);
               if (f17 > -5.0F) {
                  bufferIn.func_225582_a_((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).func_225583_a_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).func_227885_a_(f11, f12, f13, 0.8F).func_225584_a_(0.0F, -1.0F, 0.0F).endVertex();
                  bufferIn.func_225582_a_((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).func_225583_a_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).func_227885_a_(f11, f12, f13, 0.8F).func_225584_a_(0.0F, -1.0F, 0.0F).endVertex();
                  bufferIn.func_225582_a_((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).func_225583_a_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).func_227885_a_(f11, f12, f13, 0.8F).func_225584_a_(0.0F, -1.0F, 0.0F).endVertex();
                  bufferIn.func_225582_a_((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).func_225583_a_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).func_227885_a_(f11, f12, f13, 0.8F).func_225584_a_(0.0F, -1.0F, 0.0F).endVertex();
               }

               if (f17 <= 5.0F) {
                  bufferIn.func_225582_a_((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).func_225583_a_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).func_227885_a_(f5, f6, f7, 0.8F).func_225584_a_(0.0F, 1.0F, 0.0F).endVertex();
                  bufferIn.func_225582_a_((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).func_225583_a_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).func_227885_a_(f5, f6, f7, 0.8F).func_225584_a_(0.0F, 1.0F, 0.0F).endVertex();
                  bufferIn.func_225582_a_((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).func_225583_a_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).func_227885_a_(f5, f6, f7, 0.8F).func_225584_a_(0.0F, 1.0F, 0.0F).endVertex();
                  bufferIn.func_225582_a_((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).func_225583_a_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).func_227885_a_(f5, f6, f7, 0.8F).func_225584_a_(0.0F, 1.0F, 0.0F).endVertex();
               }

               if (k > -1) {
                  for(int i1 = 0; i1 < 8; ++i1) {
                     bufferIn.func_225582_a_((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).func_225583_a_((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).func_227885_a_(f8, f9, f10, 0.8F).func_225584_a_(-1.0F, 0.0F, 0.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).func_225583_a_((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).func_227885_a_(f8, f9, f10, 0.8F).func_225584_a_(-1.0F, 0.0F, 0.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).func_225583_a_((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).func_227885_a_(f8, f9, f10, 0.8F).func_225584_a_(-1.0F, 0.0F, 0.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).func_225583_a_((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).func_227885_a_(f8, f9, f10, 0.8F).func_225584_a_(-1.0F, 0.0F, 0.0F).endVertex();
                  }
               }

               if (k <= 1) {
                  for(int j2 = 0; j2 < 8; ++j2) {
                     bufferIn.func_225582_a_((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).func_225583_a_((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).func_227885_a_(f8, f9, f10, 0.8F).func_225584_a_(1.0F, 0.0F, 0.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).func_225583_a_((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).func_227885_a_(f8, f9, f10, 0.8F).func_225584_a_(1.0F, 0.0F, 0.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).func_225583_a_((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).func_227885_a_(f8, f9, f10, 0.8F).func_225584_a_(1.0F, 0.0F, 0.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).func_225583_a_((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).func_227885_a_(f8, f9, f10, 0.8F).func_225584_a_(1.0F, 0.0F, 0.0F).endVertex();
                  }
               }

               if (l > -1) {
                  for(int k2 = 0; k2 < 8; ++k2) {
                     bufferIn.func_225582_a_((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).func_225583_a_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).func_227885_a_(f14, f15, f16, 0.8F).func_225584_a_(0.0F, 0.0F, -1.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).func_225583_a_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).func_227885_a_(f14, f15, f16, 0.8F).func_225584_a_(0.0F, 0.0F, -1.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).func_225583_a_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).func_227885_a_(f14, f15, f16, 0.8F).func_225584_a_(0.0F, 0.0F, -1.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).func_225583_a_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).func_227885_a_(f14, f15, f16, 0.8F).func_225584_a_(0.0F, 0.0F, -1.0F).endVertex();
                  }
               }

               if (l <= 1) {
                  for(int l2 = 0; l2 < 8; ++l2) {
                     bufferIn.func_225582_a_((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).func_225583_a_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).func_227885_a_(f14, f15, f16, 0.8F).func_225584_a_(0.0F, 0.0F, 1.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).func_225583_a_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).func_227885_a_(f14, f15, f16, 0.8F).func_225584_a_(0.0F, 0.0F, 1.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).func_225583_a_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).func_227885_a_(f14, f15, f16, 0.8F).func_225584_a_(0.0F, 0.0F, 1.0F).endVertex();
                     bufferIn.func_225582_a_((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).func_225583_a_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).func_227885_a_(f14, f15, f16, 0.8F).func_225584_a_(0.0F, 0.0F, 1.0F).endVertex();
                  }
               }
            }
         }
      } else {
         int j1 = 1;
         int k1 = 32;

         for(int l1 = -32; l1 < 32; l1 += 32) {
            for(int i2 = -32; i2 < 32; i2 += 32) {
               bufferIn.func_225582_a_((double)(l1 + 0), (double)f17, (double)(i2 + 32)).func_225583_a_((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).func_227885_a_(f5, f6, f7, 0.8F).func_225584_a_(0.0F, -1.0F, 0.0F).endVertex();
               bufferIn.func_225582_a_((double)(l1 + 32), (double)f17, (double)(i2 + 32)).func_225583_a_((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).func_227885_a_(f5, f6, f7, 0.8F).func_225584_a_(0.0F, -1.0F, 0.0F).endVertex();
               bufferIn.func_225582_a_((double)(l1 + 32), (double)f17, (double)(i2 + 0)).func_225583_a_((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).func_227885_a_(f5, f6, f7, 0.8F).func_225584_a_(0.0F, -1.0F, 0.0F).endVertex();
               bufferIn.func_225582_a_((double)(l1 + 0), (double)f17, (double)(i2 + 0)).func_225583_a_((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).func_227885_a_(f5, f6, f7, 0.8F).func_225584_a_(0.0F, -1.0F, 0.0F).endVertex();
            }
         }
      }

   }

   private void updateChunks(long finishTimeNano) {
      this.displayListEntitiesDirty |= this.renderDispatcher.func_228908_d_();
      long i = Util.nanoTime();
      int j = 0;
      if (!this.chunksToUpdate.isEmpty()) {
         Iterator<ChunkRenderDispatcher.ChunkRender> iterator = this.chunksToUpdate.iterator();

         while(iterator.hasNext()) {
            ChunkRenderDispatcher.ChunkRender chunkrenderdispatcher$chunkrender = iterator.next();
            if (chunkrenderdispatcher$chunkrender.needsImmediateUpdate()) {
               this.renderDispatcher.func_228902_a_(chunkrenderdispatcher$chunkrender);
            } else {
               chunkrenderdispatcher$chunkrender.func_228929_a_(this.renderDispatcher);
            }

            chunkrenderdispatcher$chunkrender.clearNeedsUpdate();
            iterator.remove();
            ++j;
            long k = Util.nanoTime();
            long l = k - i;
            long i1 = l / (long)j;
            long j1 = finishTimeNano - k;
            if (j1 < i1) {
               break;
            }
         }
      }

   }

   private void func_228447_c_(ActiveRenderInfo p_228447_1_) {
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      WorldBorder worldborder = this.world.getWorldBorder();
      double d0 = (double)(this.mc.gameSettings.renderDistanceChunks * 16);
      if (!(p_228447_1_.getProjectedView().x < worldborder.maxX() - d0) || !(p_228447_1_.getProjectedView().x > worldborder.minX() + d0) || !(p_228447_1_.getProjectedView().z < worldborder.maxZ() - d0) || !(p_228447_1_.getProjectedView().z > worldborder.minZ() + d0)) {
         double d1 = 1.0D - worldborder.getClosestDistance(p_228447_1_.getProjectedView().x, p_228447_1_.getProjectedView().z) / d0;
         d1 = Math.pow(d1, 4.0D);
         double d2 = p_228447_1_.getProjectedView().x;
         double d3 = p_228447_1_.getProjectedView().y;
         double d4 = p_228447_1_.getProjectedView().z;
         RenderSystem.enableBlend();
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         this.textureManager.bindTexture(FORCEFIELD_TEXTURES);
         RenderSystem.depthMask(false);
         RenderSystem.pushMatrix();
         int i = worldborder.getStatus().getColor();
         float f = (float)(i >> 16 & 255) / 255.0F;
         float f1 = (float)(i >> 8 & 255) / 255.0F;
         float f2 = (float)(i & 255) / 255.0F;
         RenderSystem.color4f(f, f1, f2, (float)d1);
         RenderSystem.polygonOffset(-3.0F, -3.0F);
         RenderSystem.enablePolygonOffset();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.enableAlphaTest();
         RenderSystem.disableCull();
         float f3 = (float)(Util.milliTime() % 3000L) / 3000.0F;
         float f4 = 0.0F;
         float f5 = 0.0F;
         float f6 = 128.0F;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         double d5 = Math.max((double)MathHelper.floor(d4 - d0), worldborder.minZ());
         double d6 = Math.min((double)MathHelper.ceil(d4 + d0), worldborder.maxZ());
         if (d2 > worldborder.maxX() - d0) {
            float f7 = 0.0F;

            for(double d7 = d5; d7 < d6; f7 += 0.5F) {
               double d8 = Math.min(1.0D, d6 - d7);
               float f8 = (float)d8 * 0.5F;
               this.func_228422_a_(bufferbuilder, d2, d3, d4, worldborder.maxX(), 256, d7, f3 + f7, f3 + 0.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, worldborder.maxX(), 256, d7 + d8, f3 + f8 + f7, f3 + 0.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, worldborder.maxX(), 0, d7 + d8, f3 + f8 + f7, f3 + 128.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, worldborder.maxX(), 0, d7, f3 + f7, f3 + 128.0F);
               ++d7;
            }
         }

         if (d2 < worldborder.minX() + d0) {
            float f9 = 0.0F;

            for(double d9 = d5; d9 < d6; f9 += 0.5F) {
               double d12 = Math.min(1.0D, d6 - d9);
               float f12 = (float)d12 * 0.5F;
               this.func_228422_a_(bufferbuilder, d2, d3, d4, worldborder.minX(), 256, d9, f3 + f9, f3 + 0.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, worldborder.minX(), 256, d9 + d12, f3 + f12 + f9, f3 + 0.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, worldborder.minX(), 0, d9 + d12, f3 + f12 + f9, f3 + 128.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, worldborder.minX(), 0, d9, f3 + f9, f3 + 128.0F);
               ++d9;
            }
         }

         d5 = Math.max((double)MathHelper.floor(d2 - d0), worldborder.minX());
         d6 = Math.min((double)MathHelper.ceil(d2 + d0), worldborder.maxX());
         if (d4 > worldborder.maxZ() - d0) {
            float f10 = 0.0F;

            for(double d10 = d5; d10 < d6; f10 += 0.5F) {
               double d13 = Math.min(1.0D, d6 - d10);
               float f13 = (float)d13 * 0.5F;
               this.func_228422_a_(bufferbuilder, d2, d3, d4, d10, 256, worldborder.maxZ(), f3 + f10, f3 + 0.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, d10 + d13, 256, worldborder.maxZ(), f3 + f13 + f10, f3 + 0.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, d10 + d13, 0, worldborder.maxZ(), f3 + f13 + f10, f3 + 128.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, d10, 0, worldborder.maxZ(), f3 + f10, f3 + 128.0F);
               ++d10;
            }
         }

         if (d4 < worldborder.minZ() + d0) {
            float f11 = 0.0F;

            for(double d11 = d5; d11 < d6; f11 += 0.5F) {
               double d14 = Math.min(1.0D, d6 - d11);
               float f14 = (float)d14 * 0.5F;
               this.func_228422_a_(bufferbuilder, d2, d3, d4, d11, 256, worldborder.minZ(), f3 + f11, f3 + 0.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, d11 + d14, 256, worldborder.minZ(), f3 + f14 + f11, f3 + 0.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, d11 + d14, 0, worldborder.minZ(), f3 + f14 + f11, f3 + 128.0F);
               this.func_228422_a_(bufferbuilder, d2, d3, d4, d11, 0, worldborder.minZ(), f3 + f11, f3 + 128.0F);
               ++d11;
            }
         }

         bufferbuilder.finishDrawing();
         WorldVertexBufferUploader.draw(bufferbuilder);
         RenderSystem.enableCull();
         RenderSystem.disableAlphaTest();
         RenderSystem.polygonOffset(0.0F, 0.0F);
         RenderSystem.disablePolygonOffset();
         RenderSystem.enableAlphaTest();
         RenderSystem.disableBlend();
         RenderSystem.popMatrix();
         RenderSystem.depthMask(true);
      }
   }

   private void func_228422_a_(BufferBuilder p_228422_1_, double p_228422_2_, double p_228422_4_, double p_228422_6_, double p_228422_8_, int p_228422_10_, double p_228422_11_, float p_228422_13_, float p_228422_14_) {
      p_228422_1_.func_225582_a_(p_228422_8_ - p_228422_2_, (double)p_228422_10_ - p_228422_4_, p_228422_11_ - p_228422_6_).func_225583_a_(p_228422_13_, p_228422_14_).endVertex();
   }

   private void func_228429_a_(MatrixStack p_228429_1_, IVertexBuilder p_228429_2_, Entity p_228429_3_, double p_228429_4_, double p_228429_6_, double p_228429_8_, BlockPos p_228429_10_, BlockState p_228429_11_) {
      func_228445_b_(p_228429_1_, p_228429_2_, p_228429_11_.getShape(this.world, p_228429_10_, ISelectionContext.forEntity(p_228429_3_)), (double)p_228429_10_.getX() - p_228429_4_, (double)p_228429_10_.getY() - p_228429_6_, (double)p_228429_10_.getZ() - p_228429_8_, 0.0F, 0.0F, 0.0F, 0.4F);
   }

   public static void func_228431_a_(MatrixStack p_228431_0_, IVertexBuilder p_228431_1_, VoxelShape p_228431_2_, double p_228431_3_, double p_228431_5_, double p_228431_7_, float p_228431_9_, float p_228431_10_, float p_228431_11_, float p_228431_12_) {
      List<AxisAlignedBB> list = p_228431_2_.toBoundingBoxList();
      int i = MathHelper.ceil((double)list.size() / 3.0D);

      for(int j = 0; j < list.size(); ++j) {
         AxisAlignedBB axisalignedbb = list.get(j);
         float f = ((float)j % (float)i + 1.0F) / (float)i;
         float f1 = (float)(j / i);
         float f2 = f * (float)(f1 == 0.0F ? 1 : 0);
         float f3 = f * (float)(f1 == 1.0F ? 1 : 0);
         float f4 = f * (float)(f1 == 2.0F ? 1 : 0);
         func_228445_b_(p_228431_0_, p_228431_1_, VoxelShapes.create(axisalignedbb.offset(0.0D, 0.0D, 0.0D)), p_228431_3_, p_228431_5_, p_228431_7_, f2, f3, f4, 1.0F);
      }

   }

   private static void func_228445_b_(MatrixStack p_228445_0_, IVertexBuilder p_228445_1_, VoxelShape p_228445_2_, double p_228445_3_, double p_228445_5_, double p_228445_7_, float p_228445_9_, float p_228445_10_, float p_228445_11_, float p_228445_12_) {
      Matrix4f matrix4f = p_228445_0_.func_227866_c_().func_227870_a_();
      p_228445_2_.forEachEdge((p_230013_12_, p_230013_14_, p_230013_16_, p_230013_18_, p_230013_20_, p_230013_22_) -> {
         p_228445_1_.func_227888_a_(matrix4f, (float)(p_230013_12_ + p_228445_3_), (float)(p_230013_14_ + p_228445_5_), (float)(p_230013_16_ + p_228445_7_)).func_227885_a_(p_228445_9_, p_228445_10_, p_228445_11_, p_228445_12_).endVertex();
         p_228445_1_.func_227888_a_(matrix4f, (float)(p_230013_18_ + p_228445_3_), (float)(p_230013_20_ + p_228445_5_), (float)(p_230013_22_ + p_228445_7_)).func_227885_a_(p_228445_9_, p_228445_10_, p_228445_11_, p_228445_12_).endVertex();
      });
   }

   public static void func_228432_a_(IVertexBuilder p_228432_0_, double p_228432_1_, double p_228432_3_, double p_228432_5_, double p_228432_7_, double p_228432_9_, double p_228432_11_, float p_228432_13_, float p_228432_14_, float p_228432_15_, float p_228432_16_) {
      func_228428_a_(new MatrixStack(), p_228432_0_, p_228432_1_, p_228432_3_, p_228432_5_, p_228432_7_, p_228432_9_, p_228432_11_, p_228432_13_, p_228432_14_, p_228432_15_, p_228432_16_, p_228432_13_, p_228432_14_, p_228432_15_);
   }

   public static void func_228430_a_(MatrixStack p_228430_0_, IVertexBuilder p_228430_1_, AxisAlignedBB p_228430_2_, float p_228430_3_, float p_228430_4_, float p_228430_5_, float p_228430_6_) {
      func_228428_a_(p_228430_0_, p_228430_1_, p_228430_2_.minX, p_228430_2_.minY, p_228430_2_.minZ, p_228430_2_.maxX, p_228430_2_.maxY, p_228430_2_.maxZ, p_228430_3_, p_228430_4_, p_228430_5_, p_228430_6_, p_228430_3_, p_228430_4_, p_228430_5_);
   }

   public static void func_228427_a_(MatrixStack p_228427_0_, IVertexBuilder p_228427_1_, double p_228427_2_, double p_228427_4_, double p_228427_6_, double p_228427_8_, double p_228427_10_, double p_228427_12_, float p_228427_14_, float p_228427_15_, float p_228427_16_, float p_228427_17_) {
      func_228428_a_(p_228427_0_, p_228427_1_, p_228427_2_, p_228427_4_, p_228427_6_, p_228427_8_, p_228427_10_, p_228427_12_, p_228427_14_, p_228427_15_, p_228427_16_, p_228427_17_, p_228427_14_, p_228427_15_, p_228427_16_);
   }

   public static void func_228428_a_(MatrixStack p_228428_0_, IVertexBuilder p_228428_1_, double p_228428_2_, double p_228428_4_, double p_228428_6_, double p_228428_8_, double p_228428_10_, double p_228428_12_, float p_228428_14_, float p_228428_15_, float p_228428_16_, float p_228428_17_, float p_228428_18_, float p_228428_19_, float p_228428_20_) {
      Matrix4f matrix4f = p_228428_0_.func_227866_c_().func_227870_a_();
      float f = (float)p_228428_2_;
      float f1 = (float)p_228428_4_;
      float f2 = (float)p_228428_6_;
      float f3 = (float)p_228428_8_;
      float f4 = (float)p_228428_10_;
      float f5 = (float)p_228428_12_;
      p_228428_1_.func_227888_a_(matrix4f, f, f1, f2).func_227885_a_(p_228428_14_, p_228428_19_, p_228428_20_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f1, f2).func_227885_a_(p_228428_14_, p_228428_19_, p_228428_20_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f1, f2).func_227885_a_(p_228428_18_, p_228428_15_, p_228428_20_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f4, f2).func_227885_a_(p_228428_18_, p_228428_15_, p_228428_20_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f1, f2).func_227885_a_(p_228428_18_, p_228428_19_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f1, f5).func_227885_a_(p_228428_18_, p_228428_19_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f1, f2).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f4, f2).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f4, f2).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f4, f2).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f4, f2).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f4, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f4, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f1, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f1, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f1, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f1, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f1, f2).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f, f4, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f4, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f1, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f4, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f4, f2).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
      p_228428_1_.func_227888_a_(matrix4f, f3, f4, f5).func_227885_a_(p_228428_14_, p_228428_15_, p_228428_16_, p_228428_17_).endVertex();
   }

   public static void addChainedFilledBoxVertices(BufferBuilder builder, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
      builder.func_225582_a_(x1, y1, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y1, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y1, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y1, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y2, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y2, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y2, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y1, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y2, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y1, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y1, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y1, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y2, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y2, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y2, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y1, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y2, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y1, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y1, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y1, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y1, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y1, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y1, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y2, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y2, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x1, y2, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y2, z1).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y2, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y2, z2).func_227885_a_(red, green, blue, alpha).endVertex();
      builder.func_225582_a_(x2, y2, z2).func_227885_a_(red, green, blue, alpha).endVertex();
   }

   public void notifyBlockUpdate(IBlockReader worldIn, BlockPos pos, BlockState oldState, BlockState newState, int flags) {
      this.func_215324_a(pos, (flags & 8) != 0);
   }

   private void func_215324_a(BlockPos p_215324_1_, boolean p_215324_2_) {
      for(int i = p_215324_1_.getZ() - 1; i <= p_215324_1_.getZ() + 1; ++i) {
         for(int j = p_215324_1_.getX() - 1; j <= p_215324_1_.getX() + 1; ++j) {
            for(int k = p_215324_1_.getY() - 1; k <= p_215324_1_.getY() + 1; ++k) {
               this.markForRerender(j >> 4, k >> 4, i >> 4, p_215324_2_);
            }
         }
      }

   }

   /**
    * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing.
    */
   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
      for(int i = z1 - 1; i <= z2 + 1; ++i) {
         for(int j = x1 - 1; j <= x2 + 1; ++j) {
            for(int k = y1 - 1; k <= y2 + 1; ++k) {
               this.markForRerender(j >> 4, k >> 4, i >> 4);
            }
         }
      }

   }

   public void func_224746_a(BlockPos p_224746_1_, BlockState p_224746_2_, BlockState p_224746_3_) {
      if (this.mc.getModelManager().func_224742_a(p_224746_2_, p_224746_3_)) {
         this.markBlockRangeForRenderUpdate(p_224746_1_.getX(), p_224746_1_.getY(), p_224746_1_.getZ(), p_224746_1_.getX(), p_224746_1_.getY(), p_224746_1_.getZ());
      }

   }

   public void markSurroundingsForRerender(int sectionX, int sectionY, int sectionZ) {
      for(int i = sectionZ - 1; i <= sectionZ + 1; ++i) {
         for(int j = sectionX - 1; j <= sectionX + 1; ++j) {
            for(int k = sectionY - 1; k <= sectionY + 1; ++k) {
               this.markForRerender(j, k, i);
            }
         }
      }

   }

   public void markForRerender(int sectionX, int sectionY, int sectionZ) {
      this.markForRerender(sectionX, sectionY, sectionZ, false);
   }

   private void markForRerender(int sectionX, int sectionY, int sectionZ, boolean rerenderOnMainThread) {
      this.viewFrustum.markForRerender(sectionX, sectionY, sectionZ, rerenderOnMainThread);
   }

   public void playRecord(@Nullable SoundEvent soundIn, BlockPos pos) {
      ISound isound = this.mapSoundPositions.get(pos);
      if (isound != null) {
         this.mc.getSoundHandler().stop(isound);
         this.mapSoundPositions.remove(pos);
      }

      if (soundIn != null) {
         MusicDiscItem musicdiscitem = MusicDiscItem.getBySound(soundIn);
         if (musicdiscitem != null) {
            this.mc.ingameGUI.setRecordPlayingMessage(musicdiscitem.getRecordDescription().getFormattedText());
         }

         ISound simplesound = SimpleSound.record(soundIn, (float)pos.getX(), (float)pos.getY(), (float)pos.getZ());
         this.mapSoundPositions.put(pos, simplesound);
         this.mc.getSoundHandler().play(simplesound);
      }

      this.setPartying(this.world, pos, soundIn != null);
   }

   /**
    * Called when a record starts or stops playing. Used to make parrots start or stop partying.
    */
   private void setPartying(World worldIn, BlockPos pos, boolean isPartying) {
      for(LivingEntity livingentity : worldIn.getEntitiesWithinAABB(LivingEntity.class, (new AxisAlignedBB(pos)).grow(3.0D))) {
         livingentity.setPartying(pos, isPartying);
      }

   }

   public void addParticle(IParticleData particleData, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.addParticle(particleData, alwaysRender, false, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void addParticle(IParticleData particleData, boolean ignoreRange, boolean minimizeLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      try {
         this.addParticleUnchecked(particleData, ignoreRange, minimizeLevel, x, y, z, xSpeed, ySpeed, zSpeed);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being added");
         crashreportcategory.addDetail("ID", Registry.PARTICLE_TYPE.getKey(particleData.getType()));
         crashreportcategory.addDetail("Parameters", particleData.getParameters());
         crashreportcategory.addDetail("Position", () -> {
            return CrashReportCategory.getCoordinateInfo(x, y, z);
         });
         throw new ReportedException(crashreport);
      }
   }

   private <T extends IParticleData> void addParticleUnchecked(T particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.addParticle(particleData, particleData.getType().getAlwaysShow(), x, y, z, xSpeed, ySpeed, zSpeed);
   }

   @Nullable
   private Particle addParticleUnchecked(IParticleData particleData, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      return this.addParticleUnchecked(particleData, alwaysRender, false, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   @Nullable
   private Particle addParticleUnchecked(IParticleData particleData, boolean alwaysRender, boolean minimizeLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      ActiveRenderInfo activerenderinfo = this.mc.gameRenderer.getActiveRenderInfo();
      if (this.mc != null && activerenderinfo.isValid() && this.mc.particles != null) {
         ParticleStatus particlestatus = this.func_215327_a(minimizeLevel);
         if (alwaysRender) {
            return this.mc.particles.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
         } else if (activerenderinfo.getProjectedView().squareDistanceTo(x, y, z) > 1024.0D) {
            return null;
         } else {
            return particlestatus == ParticleStatus.MINIMAL ? null : this.mc.particles.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
         }
      } else {
         return null;
      }
   }

   private ParticleStatus func_215327_a(boolean p_215327_1_) {
      ParticleStatus particlestatus = this.mc.gameSettings.particles;
      if (p_215327_1_ && particlestatus == ParticleStatus.MINIMAL && this.world.rand.nextInt(10) == 0) {
         particlestatus = ParticleStatus.DECREASED;
      }

      if (particlestatus == ParticleStatus.DECREASED && this.world.rand.nextInt(3) == 0) {
         particlestatus = ParticleStatus.MINIMAL;
      }

      return particlestatus;
   }

   /**
    * Deletes all display lists
    */
   public void deleteAllDisplayLists() {
   }

   public void broadcastSound(int soundID, BlockPos pos, int data) {
      switch(soundID) {
      case 1023:
      case 1028:
      case 1038:
         ActiveRenderInfo activerenderinfo = this.mc.gameRenderer.getActiveRenderInfo();
         if (activerenderinfo.isValid()) {
            double d0 = (double)pos.getX() - activerenderinfo.getProjectedView().x;
            double d1 = (double)pos.getY() - activerenderinfo.getProjectedView().y;
            double d2 = (double)pos.getZ() - activerenderinfo.getProjectedView().z;
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            double d4 = activerenderinfo.getProjectedView().x;
            double d5 = activerenderinfo.getProjectedView().y;
            double d6 = activerenderinfo.getProjectedView().z;
            if (d3 > 0.0D) {
               d4 += d0 / d3 * 2.0D;
               d5 += d1 / d3 * 2.0D;
               d6 += d2 / d3 * 2.0D;
            }

            if (soundID == 1023) {
               this.world.playSound(d4, d5, d6, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
            } else if (soundID == 1038) {
               this.world.playSound(d4, d5, d6, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
            } else {
               this.world.playSound(d4, d5, d6, SoundEvents.ENTITY_ENDER_DRAGON_DEATH, SoundCategory.HOSTILE, 5.0F, 1.0F, false);
            }
         }
      default:
      }
   }

   public void playEvent(PlayerEntity player, int type, BlockPos blockPosIn, int data) {
      Random random = this.world.rand;
      switch(type) {
      case 1000:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 1001:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
         break;
      case 1002:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
         break;
      case 1003:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
         break;
      case 1004:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_FIREWORK_ROCKET_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
         break;
      case 1005:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1006:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1007:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1008:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_FENCE_GATE_OPEN, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1009:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);
         break;
      case 1010:
         if (Item.getItemById(data) instanceof MusicDiscItem) {
            this.playRecord(((MusicDiscItem)Item.getItemById(data)).getSound(), blockPosIn);
         } else {
            this.playRecord((SoundEvent)null, blockPosIn);
         }
         break;
      case 1011:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1012:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1013:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1014:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1015:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_GHAST_WARN, SoundCategory.HOSTILE, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1016:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.HOSTILE, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1017:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.HOSTILE, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1018:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1019:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1020:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1021:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1022:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1024:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1025:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.NEUTRAL, 0.05F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1026:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1027:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1029:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1030:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1031:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1032:
         this.mc.getSoundHandler().play(SimpleSound.master(SoundEvents.BLOCK_PORTAL_TRAVEL, random.nextFloat() * 0.4F + 0.8F));
         break;
      case 1033:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 1034:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 1035:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 1036:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1037:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1039:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_PHANTOM_BITE, SoundCategory.HOSTILE, 0.3F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1040:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, SoundCategory.NEUTRAL, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1041:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_HUSK_CONVERTED_TO_ZOMBIE, SoundCategory.NEUTRAL, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1042:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1043:
         this.world.playSound(blockPosIn, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1500:
         ComposterBlock.func_220292_a(this.world, blockPosIn, data > 0);
         break;
      case 1501:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (this.world.getRandom().nextFloat() - this.world.getRandom().nextFloat()) * 0.8F, false);

         for(int k1 = 0; k1 < 8; ++k1) {
            this.world.addParticle(ParticleTypes.LARGE_SMOKE, (double)blockPosIn.getX() + Math.random(), (double)blockPosIn.getY() + 1.2D, (double)blockPosIn.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
         }
         break;
      case 1502:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.5F, 2.6F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.8F, false);

         for(int j1 = 0; j1 < 5; ++j1) {
            double d16 = (double)blockPosIn.getX() + random.nextDouble() * 0.6D + 0.2D;
            double d22 = (double)blockPosIn.getY() + random.nextDouble() * 0.6D + 0.2D;
            double d27 = (double)blockPosIn.getZ() + random.nextDouble() * 0.6D + 0.2D;
            this.world.addParticle(ParticleTypes.SMOKE, d16, d22, d27, 0.0D, 0.0D, 0.0D);
         }
         break;
      case 1503:
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

         for(int i1 = 0; i1 < 16; ++i1) {
            double d15 = (double)((float)blockPosIn.getX() + (5.0F + random.nextFloat() * 6.0F) / 16.0F);
            double d21 = (double)((float)blockPosIn.getY() + 0.8125F);
            double d26 = (double)((float)blockPosIn.getZ() + (5.0F + random.nextFloat() * 6.0F) / 16.0F);
            double d29 = 0.0D;
            double d30 = 0.0D;
            double d5 = 0.0D;
            this.world.addParticle(ParticleTypes.SMOKE, d15, d21, d26, 0.0D, 0.0D, 0.0D);
         }
         break;
      case 2000:
         Direction direction = Direction.byIndex(data);
         int l = direction.getXOffset();
         int l1 = direction.getYOffset();
         int i2 = direction.getZOffset();
         double d20 = (double)blockPosIn.getX() + (double)l * 0.6D + 0.5D;
         double d25 = (double)blockPosIn.getY() + (double)l1 * 0.6D + 0.5D;
         double d28 = (double)blockPosIn.getZ() + (double)i2 * 0.6D + 0.5D;

         for(int i3 = 0; i3 < 10; ++i3) {
            double d31 = random.nextDouble() * 0.2D + 0.01D;
            double d32 = d20 + (double)l * 0.01D + (random.nextDouble() - 0.5D) * (double)i2 * 0.5D;
            double d33 = d25 + (double)l1 * 0.01D + (random.nextDouble() - 0.5D) * (double)l1 * 0.5D;
            double d34 = d28 + (double)i2 * 0.01D + (random.nextDouble() - 0.5D) * (double)l * 0.5D;
            double d35 = (double)l * d31 + random.nextGaussian() * 0.01D;
            double d36 = (double)l1 * d31 + random.nextGaussian() * 0.01D;
            double d10 = (double)i2 * d31 + random.nextGaussian() * 0.01D;
            this.addParticleUnchecked(ParticleTypes.SMOKE, d32, d33, d34, d35, d36, d10);
         }
         break;
      case 2001:
         BlockState blockstate = Block.getStateById(data);
         if (!blockstate.isAir(this.world, blockPosIn)) {
            SoundType soundtype = blockstate.getSoundType(this.world, blockPosIn, null);
            this.world.playSound(blockPosIn, soundtype.getBreakSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
         }

         this.mc.particles.addBlockDestroyEffects(blockPosIn, blockstate);
         break;
      case 2002:
      case 2007:
         double d11 = (double)blockPosIn.getX();
         double d14 = (double)blockPosIn.getY();
         double d19 = (double)blockPosIn.getZ();

         for(int k2 = 0; k2 < 8; ++k2) {
            this.addParticleUnchecked(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)), d11, d14, d19, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);
         }

         float f4 = (float)(data >> 16 & 255) / 255.0F;
         float f2 = (float)(data >> 8 & 255) / 255.0F;
         float f5 = (float)(data >> 0 & 255) / 255.0F;
         IParticleData iparticledata = type == 2007 ? ParticleTypes.INSTANT_EFFECT : ParticleTypes.EFFECT;

         for(int l2 = 0; l2 < 100; ++l2) {
            double d4 = random.nextDouble() * 4.0D;
            double d6 = random.nextDouble() * Math.PI * 2.0D;
            double d7 = Math.cos(d6) * d4;
            double d8 = 0.01D + random.nextDouble() * 0.5D;
            double d9 = Math.sin(d6) * d4;
            Particle particle1 = this.addParticleUnchecked(iparticledata, iparticledata.getType().getAlwaysShow(), d11 + d7 * 0.1D, d14 + 0.3D, d19 + d9 * 0.1D, d7, d8, d9);
            if (particle1 != null) {
               float f3 = 0.75F + random.nextFloat() * 0.25F;
               particle1.setColor(f4 * f3, f2 * f3, f5 * f3);
               particle1.multiplyVelocity((float)d4);
            }
         }

         this.world.playSound(blockPosIn, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2003:
         double d0 = (double)blockPosIn.getX() + 0.5D;
         double d13 = (double)blockPosIn.getY();
         double d18 = (double)blockPosIn.getZ() + 0.5D;

         for(int j2 = 0; j2 < 8; ++j2) {
            this.addParticleUnchecked(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.ENDER_EYE)), d0, d13, d18, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);
         }

         for(double d24 = 0.0D; d24 < (Math.PI * 2D); d24 += 0.15707963267948966D) {
            this.addParticleUnchecked(ParticleTypes.PORTAL, d0 + Math.cos(d24) * 5.0D, d13 - 0.4D, d18 + Math.sin(d24) * 5.0D, Math.cos(d24) * -5.0D, 0.0D, Math.sin(d24) * -5.0D);
            this.addParticleUnchecked(ParticleTypes.PORTAL, d0 + Math.cos(d24) * 5.0D, d13 - 0.4D, d18 + Math.sin(d24) * 5.0D, Math.cos(d24) * -7.0D, 0.0D, Math.sin(d24) * -7.0D);
         }
         break;
      case 2004:
         for(int k = 0; k < 20; ++k) {
            double d12 = (double)blockPosIn.getX() + 0.5D + ((double)this.world.rand.nextFloat() - 0.5D) * 2.0D;
            double d17 = (double)blockPosIn.getY() + 0.5D + ((double)this.world.rand.nextFloat() - 0.5D) * 2.0D;
            double d23 = (double)blockPosIn.getZ() + 0.5D + ((double)this.world.rand.nextFloat() - 0.5D) * 2.0D;
            this.world.addParticle(ParticleTypes.SMOKE, d12, d17, d23, 0.0D, 0.0D, 0.0D);
            this.world.addParticle(ParticleTypes.FLAME, d12, d17, d23, 0.0D, 0.0D, 0.0D);
         }
         break;
      case 2005:
         BoneMealItem.spawnBonemealParticles(this.world, blockPosIn, data);
         break;
      case 2006:
         for(int j = 0; j < 200; ++j) {
            float f = random.nextFloat() * 4.0F;
            float f1 = random.nextFloat() * ((float)Math.PI * 2F);
            double d1 = (double)(MathHelper.cos(f1) * f);
            double d2 = 0.01D + random.nextDouble() * 0.5D;
            double d3 = (double)(MathHelper.sin(f1) * f);
            Particle particle = this.addParticleUnchecked(ParticleTypes.DRAGON_BREATH, false, (double)blockPosIn.getX() + d1 * 0.1D, (double)blockPosIn.getY() + 0.3D, (double)blockPosIn.getZ() + d3 * 0.1D, d1, d2, d3);
            if (particle != null) {
               particle.multiplyVelocity(f);
            }
         }

         this.world.playSound(blockPosIn, SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2008:
         this.world.addParticle(ParticleTypes.EXPLOSION, (double)blockPosIn.getX() + 0.5D, (double)blockPosIn.getY() + 0.5D, (double)blockPosIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
         break;
      case 2009:
         for(int i = 0; i < 8; ++i) {
            this.world.addParticle(ParticleTypes.CLOUD, (double)blockPosIn.getX() + Math.random(), (double)blockPosIn.getY() + 1.2D, (double)blockPosIn.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
         }
         break;
      case 3000:
         this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, true, (double)blockPosIn.getX() + 0.5D, (double)blockPosIn.getY() + 0.5D, (double)blockPosIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
         this.world.playSound(blockPosIn, SoundEvents.BLOCK_END_GATEWAY_SPAWN, SoundCategory.BLOCKS, 10.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
         break;
      case 3001:
         this.world.playSound(blockPosIn, SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.HOSTILE, 64.0F, 0.8F + this.world.rand.nextFloat() * 0.3F, false);
      }

   }

   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
      if (progress >= 0 && progress < 10) {
         DestroyBlockProgress destroyblockprogress1 = this.damagedBlocks.get(breakerId);
         if (destroyblockprogress1 != null) {
            this.func_228442_a_(destroyblockprogress1);
         }

         if (destroyblockprogress1 == null || destroyblockprogress1.getPosition().getX() != pos.getX() || destroyblockprogress1.getPosition().getY() != pos.getY() || destroyblockprogress1.getPosition().getZ() != pos.getZ()) {
            destroyblockprogress1 = new DestroyBlockProgress(breakerId, pos);
            this.damagedBlocks.put(breakerId, destroyblockprogress1);
         }

         destroyblockprogress1.setPartialBlockDamage(progress);
         destroyblockprogress1.setCloudUpdateTick(this.ticks);
         this.field_228407_B_.computeIfAbsent(destroyblockprogress1.getPosition().toLong(), (p_230017_0_) -> {
            return Sets.newTreeSet();
         }).add(destroyblockprogress1);
      } else {
         DestroyBlockProgress destroyblockprogress = this.damagedBlocks.remove(breakerId);
         if (destroyblockprogress != null) {
            this.func_228442_a_(destroyblockprogress);
         }
      }

   }

   public boolean hasNoChunkUpdates() {
      return this.chunksToUpdate.isEmpty() && this.renderDispatcher.hasNoChunkUpdates();
   }

   public void setDisplayListEntitiesDirty() {
      this.displayListEntitiesDirty = true;
      this.cloudsNeedUpdate = true;
   }

   public void updateTileEntities(Collection<TileEntity> tileEntitiesToRemove, Collection<TileEntity> tileEntitiesToAdd) {
      synchronized(this.setTileEntities) {
         this.setTileEntities.removeAll(tileEntitiesToRemove);
         this.setTileEntities.addAll(tileEntitiesToAdd);
      }
   }

   public static int func_228421_a_(ILightReader p_228421_0_, BlockPos p_228421_1_) {
      return func_228420_a_(p_228421_0_, p_228421_0_.getBlockState(p_228421_1_), p_228421_1_);
   }

   public static int func_228420_a_(ILightReader p_228420_0_, BlockState p_228420_1_, BlockPos p_228420_2_) {
      if (p_228420_1_.func_227035_k_()) {
         return 15728880;
      } else {
         int i = p_228420_0_.func_226658_a_(LightType.SKY, p_228420_2_);
         int j = p_228420_0_.func_226658_a_(LightType.BLOCK, p_228420_2_);
         int k = p_228420_1_.getLightValue(p_228420_0_, p_228420_2_);
         if (j < k) {
            j = k;
         }

         return i << 20 | j << 4;
      }
   }

   public Framebuffer func_228448_p_() {
      return this.entityOutlineFramebuffer;
   }

   @Override
   public net.minecraftforge.resource.IResourceType getResourceType() {
      return net.minecraftforge.resource.VanillaResourceType.MODELS;
   }

   @OnlyIn(Dist.CLIENT)
   class LocalRenderInformationContainer {
      private final ChunkRenderDispatcher.ChunkRender renderChunk;
      private final Direction facing;
      private byte setFacing;
      private final int counter;

      private LocalRenderInformationContainer(ChunkRenderDispatcher.ChunkRender renderChunkIn, @Nullable Direction facingIn, int counterIn) {
         this.renderChunk = renderChunkIn;
         this.facing = facingIn;
         this.counter = counterIn;
      }

      public void setDirection(byte dir, Direction facingIn) {
         this.setFacing = (byte)(this.setFacing | dir | 1 << facingIn.ordinal());
      }

      public boolean hasDirection(Direction facingIn) {
         return (this.setFacing & 1 << facingIn.ordinal()) > 0;
      }
   }
}