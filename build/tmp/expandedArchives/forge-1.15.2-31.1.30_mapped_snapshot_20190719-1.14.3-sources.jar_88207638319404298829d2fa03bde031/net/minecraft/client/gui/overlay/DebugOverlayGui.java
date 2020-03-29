package net.minecraft.client.gui.overlay;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.PlatformDescriptors;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.DataFixUtils;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.state.IProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DebugOverlayGui extends AbstractGui {
   private static final Map<Heightmap.Type, String> field_212923_a = Util.make(new EnumMap<>(Heightmap.Type.class), (p_212918_0_) -> {
      p_212918_0_.put(Heightmap.Type.WORLD_SURFACE_WG, "SW");
      p_212918_0_.put(Heightmap.Type.WORLD_SURFACE, "S");
      p_212918_0_.put(Heightmap.Type.OCEAN_FLOOR_WG, "OW");
      p_212918_0_.put(Heightmap.Type.OCEAN_FLOOR, "O");
      p_212918_0_.put(Heightmap.Type.MOTION_BLOCKING, "M");
      p_212918_0_.put(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, "ML");
   });
   private final Minecraft mc;
   private final FontRenderer fontRenderer;
   protected RayTraceResult rayTraceBlock;
   protected RayTraceResult rayTraceFluid;
   @Nullable
   private ChunkPos field_212924_f;
   @Nullable
   private Chunk field_212925_g;
   @Nullable
   private CompletableFuture<Chunk> field_212926_h;

   public DebugOverlayGui(Minecraft mc) {
      this.mc = mc;
      this.fontRenderer = mc.fontRenderer;
   }

   public void func_212921_a() {
      this.field_212926_h = null;
      this.field_212925_g = null;
   }

   public void render() {
      this.mc.getProfiler().startSection("debug");
      RenderSystem.pushMatrix();
      Entity entity = this.mc.getRenderViewEntity();
      this.rayTraceBlock = entity.func_213324_a(20.0D, 0.0F, false);
      this.rayTraceFluid = entity.func_213324_a(20.0D, 0.0F, true);
      this.func_230024_c_();
      this.func_230025_d_();
      RenderSystem.popMatrix();
      if (this.mc.gameSettings.showLagometer) {
         int i = this.mc.func_228018_at_().getScaledWidth();
         this.func_230023_a_(this.mc.getFrameTimer(), 0, i / 2, true);
         IntegratedServer integratedserver = this.mc.getIntegratedServer();
         if (integratedserver != null) {
            this.func_230023_a_(integratedserver.getFrameTimer(), i - Math.min(i / 2, 240), i / 2, false);
         }
      }

      this.mc.getProfiler().endSection();
   }

   protected void func_230024_c_() {
      List<String> list = this.call();
      list.add("");
      boolean flag = this.mc.getIntegratedServer() != null;
      list.add("Debug: Pie [shift]: " + (this.mc.gameSettings.showDebugProfilerChart ? "visible" : "hidden") + (flag ? " FPS + TPS" : " FPS") + " [alt]: " + (this.mc.gameSettings.showLagometer ? "visible" : "hidden"));
      list.add("For help: press F3 + Q");

      for(int i = 0; i < list.size(); ++i) {
         String s = list.get(i);
         if (!Strings.isNullOrEmpty(s)) {
            int j = 9;
            int k = this.fontRenderer.getStringWidth(s);
            int l = 2;
            int i1 = 2 + j * i;
            fill(1, i1 - 1, 2 + k + 1, i1 + j - 1, -1873784752);
            this.fontRenderer.drawString(s, 2.0F, (float)i1, 14737632);
         }
      }

   }

   protected void func_230025_d_() {
      List<String> list = this.getDebugInfoRight();

      for(int i = 0; i < list.size(); ++i) {
         String s = list.get(i);
         if (!Strings.isNullOrEmpty(s)) {
            int j = 9;
            int k = this.fontRenderer.getStringWidth(s);
            int l = this.mc.func_228018_at_().getScaledWidth() - 2 - k;
            int i1 = 2 + j * i;
            fill(l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
            this.fontRenderer.drawString(s, (float)l, (float)i1, 14737632);
         }
      }

   }

   protected List<String> call() {
      IntegratedServer integratedserver = this.mc.getIntegratedServer();
      NetworkManager networkmanager = this.mc.getConnection().getNetworkManager();
      float f = networkmanager.getPacketsSent();
      float f1 = networkmanager.getPacketsReceived();
      String s;
      if (integratedserver != null) {
         s = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", integratedserver.getTickTime(), f, f1);
      } else {
         s = String.format("\"%s\" server, %.0f tx, %.0f rx", this.mc.player.getServerBrand(), f, f1);
      }

      BlockPos blockpos = new BlockPos(this.mc.getRenderViewEntity());
      if (this.mc.isReducedDebug()) {
         return Lists.newArrayList("Minecraft " + SharedConstants.getVersion().getName() + " (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, s, this.mc.worldRenderer.getDebugInfoRenders(), this.mc.worldRenderer.getDebugInfoEntities(), "P: " + this.mc.particles.getStatistics() + ". T: " + this.mc.world.func_217425_f(), this.mc.world.getProviderName(), "", String.format("Chunk-relative: %d %d %d", blockpos.getX() & 15, blockpos.getY() & 15, blockpos.getZ() & 15));
      } else {
         Entity entity = this.mc.getRenderViewEntity();
         Direction direction = entity.getHorizontalFacing();
         String s1;
         switch(direction) {
         case NORTH:
            s1 = "Towards negative Z";
            break;
         case SOUTH:
            s1 = "Towards positive Z";
            break;
         case WEST:
            s1 = "Towards negative X";
            break;
         case EAST:
            s1 = "Towards positive X";
            break;
         default:
            s1 = "Invalid";
         }

         ChunkPos chunkpos = new ChunkPos(blockpos);
         if (!Objects.equals(this.field_212924_f, chunkpos)) {
            this.field_212924_f = chunkpos;
            this.func_212921_a();
         }

         World world = this.func_212922_g();
         LongSet longset = (LongSet)(world instanceof ServerWorld ? ((ServerWorld)world).getForcedChunks() : LongSets.EMPTY_SET);
         List<String> list = Lists.newArrayList("Minecraft " + SharedConstants.getVersion().getName() + " (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType()) + ")", this.mc.debug, s, this.mc.worldRenderer.getDebugInfoRenders(), this.mc.worldRenderer.getDebugInfoEntities(), "P: " + this.mc.particles.getStatistics() + ". T: " + this.mc.world.func_217425_f(), this.mc.world.getProviderName());
         String s2 = this.func_223101_g();
         if (s2 != null) {
            list.add(s2);
         }

         list.add(DimensionType.getKey(this.mc.world.dimension.getType()).toString() + " FC: " + Integer.toString(longset.size()));
         list.add("");
         list.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", this.mc.getRenderViewEntity().func_226277_ct_(), this.mc.getRenderViewEntity().func_226278_cu_(), this.mc.getRenderViewEntity().func_226281_cx_()));
         list.add(String.format("Block: %d %d %d", blockpos.getX(), blockpos.getY(), blockpos.getZ()));
         list.add(String.format("Chunk: %d %d %d in %d %d %d", blockpos.getX() & 15, blockpos.getY() & 15, blockpos.getZ() & 15, blockpos.getX() >> 4, blockpos.getY() >> 4, blockpos.getZ() >> 4));
         list.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, s1, MathHelper.wrapDegrees(entity.rotationYaw), MathHelper.wrapDegrees(entity.rotationPitch)));
         if (this.mc.world != null) {
            if (this.mc.world.isBlockLoaded(blockpos)) {
               Chunk chunk = this.func_212916_i();
               if (chunk.isEmpty()) {
                  list.add("Waiting for chunk...");
               } else {
                  int i = this.mc.world.getChunkProvider().getLightManager().func_227470_b_(blockpos, 0);
                  int j = this.mc.world.func_226658_a_(LightType.SKY, blockpos);
                  int k = this.mc.world.func_226658_a_(LightType.BLOCK, blockpos);
                  list.add("Client Light: " + i + " (" + j + " sky, " + k + " block)");
                  Chunk chunk1 = this.func_212919_h();
                  if (chunk1 != null) {
                     WorldLightManager worldlightmanager = world.getChunkProvider().getLightManager();
                     list.add("Server Light: (" + worldlightmanager.getLightEngine(LightType.SKY).getLightFor(blockpos) + " sky, " + worldlightmanager.getLightEngine(LightType.BLOCK).getLightFor(blockpos) + " block)");
                  } else {
                     list.add("Server Light: (?? sky, ?? block)");
                  }

                  StringBuilder stringbuilder = new StringBuilder("CH");

                  for(Heightmap.Type heightmap$type : Heightmap.Type.values()) {
                     if (heightmap$type.func_222681_b()) {
                        stringbuilder.append(" ").append(field_212923_a.get(heightmap$type)).append(": ").append(chunk.getTopBlockY(heightmap$type, blockpos.getX(), blockpos.getZ()));
                     }
                  }

                  list.add(stringbuilder.toString());
                  stringbuilder.setLength(0);
                  stringbuilder.append("SH");

                  for(Heightmap.Type heightmap$type1 : Heightmap.Type.values()) {
                     if (heightmap$type1.func_222683_c()) {
                        stringbuilder.append(" ").append(field_212923_a.get(heightmap$type1)).append(": ");
                        if (chunk1 != null) {
                           stringbuilder.append(chunk1.getTopBlockY(heightmap$type1, blockpos.getX(), blockpos.getZ()));
                        } else {
                           stringbuilder.append("??");
                        }
                     }
                  }

                  list.add(stringbuilder.toString());
                  if (blockpos.getY() >= 0 && blockpos.getY() < 256) {
                     list.add("Biome: " + Registry.BIOME.getKey(this.mc.world.func_226691_t_(blockpos)));
                     long l = 0L;
                     float f2 = 0.0F;
                     if (chunk1 != null) {
                        f2 = world.getCurrentMoonPhaseFactor();
                        l = chunk1.getInhabitedTime();
                     }

                     DifficultyInstance difficultyinstance = new DifficultyInstance(world.getDifficulty(), world.getDayTime(), l, f2);
                     list.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f (Day %d)", difficultyinstance.getAdditionalDifficulty(), difficultyinstance.getClampedAdditionalDifficulty(), this.mc.world.getDayTime() / 24000L));
                  }
               }
            } else {
               list.add("Outside of world...");
            }
         } else {
            list.add("Outside of world...");
         }

         ShaderGroup shadergroup = this.mc.gameRenderer.getShaderGroup();
         if (shadergroup != null) {
            list.add("Shader: " + shadergroup.getShaderGroupName());
         }

         if (this.rayTraceBlock.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos1 = ((BlockRayTraceResult)this.rayTraceBlock).getPos();
            list.add(String.format("Looking at block: %d %d %d", blockpos1.getX(), blockpos1.getY(), blockpos1.getZ()));
         }

         if (this.rayTraceFluid.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos2 = ((BlockRayTraceResult)this.rayTraceFluid).getPos();
            list.add(String.format("Looking at liquid: %d %d %d", blockpos2.getX(), blockpos2.getY(), blockpos2.getZ()));
         }

         list.add(this.mc.getSoundHandler().getDebugString());
         return list;
      }
   }

   @Nullable
   private String func_223101_g() {
      IntegratedServer integratedserver = this.mc.getIntegratedServer();
      if (integratedserver != null) {
         ServerWorld serverworld = integratedserver.getWorld(this.mc.world.getDimension().getType());
         if (serverworld != null) {
            return serverworld.getProviderName();
         }
      }

      return null;
   }

   private World func_212922_g() {
      return DataFixUtils.orElse(Optional.ofNullable(this.mc.getIntegratedServer()).map((p_212917_1_) -> {
         return p_212917_1_.getWorld(this.mc.world.dimension.getType());
      }), this.mc.world);
   }

   @Nullable
   private Chunk func_212919_h() {
      if (this.field_212926_h == null) {
         IntegratedServer integratedserver = this.mc.getIntegratedServer();
         if (integratedserver != null) {
            ServerWorld serverworld = integratedserver.getWorld(this.mc.world.dimension.getType());
            if (serverworld != null) {
               this.field_212926_h = serverworld.getChunkProvider().func_217232_b(this.field_212924_f.x, this.field_212924_f.z, ChunkStatus.FULL, false).thenApply((p_222802_0_) -> {
                  return p_222802_0_.map((p_222803_0_) -> {
                     return (Chunk)p_222803_0_;
                  }, (p_222801_0_) -> {
                     return null;
                  });
               });
            }
         }

         if (this.field_212926_h == null) {
            this.field_212926_h = CompletableFuture.completedFuture(this.func_212916_i());
         }
      }

      return this.field_212926_h.getNow((Chunk)null);
   }

   private Chunk func_212916_i() {
      if (this.field_212925_g == null) {
         this.field_212925_g = this.mc.world.getChunk(this.field_212924_f.x, this.field_212924_f.z);
      }

      return this.field_212925_g;
   }

   protected List<String> getDebugInfoRight() {
      long i = Runtime.getRuntime().maxMemory();
      long j = Runtime.getRuntime().totalMemory();
      long k = Runtime.getRuntime().freeMemory();
      long l = j - k;
      List<String> list = Lists.newArrayList(String.format("Java: %s %dbit", System.getProperty("java.version"), this.mc.isJava64bit() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", l * 100L / i, bytesToMb(l), bytesToMb(i)), String.format("Allocated: % 2d%% %03dMB", j * 100L / i, bytesToMb(j)), "", String.format("CPU: %s", PlatformDescriptors.func_227775_b_()), "", String.format("Display: %dx%d (%s)", Minecraft.getInstance().func_228018_at_().getFramebufferWidth(), Minecraft.getInstance().func_228018_at_().getFramebufferHeight(), PlatformDescriptors.func_227774_a_()), PlatformDescriptors.func_227776_c_(), PlatformDescriptors.func_227777_d_());
      if (this.mc.isReducedDebug()) {
         return list;
      } else {
         if (this.rayTraceBlock.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockRayTraceResult)this.rayTraceBlock).getPos();
            BlockState blockstate = this.mc.world.getBlockState(blockpos);
            list.add("");
            list.add(TextFormatting.UNDERLINE + "Targeted Block");
            list.add(String.valueOf((Object)Registry.BLOCK.getKey(blockstate.getBlock())));

            for(Entry<IProperty<?>, Comparable<?>> entry : blockstate.getValues().entrySet()) {
               list.add(this.getPropertyString(entry));
            }

            for(ResourceLocation resourcelocation : blockstate.getBlock().getTags()) {
               list.add("#" + resourcelocation);
            }
         }

         if (this.rayTraceFluid.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos1 = ((BlockRayTraceResult)this.rayTraceFluid).getPos();
            IFluidState ifluidstate = this.mc.world.getFluidState(blockpos1);
            list.add("");
            list.add(TextFormatting.UNDERLINE + "Targeted Fluid");
            list.add(String.valueOf((Object)Registry.FLUID.getKey(ifluidstate.getFluid())));

            for(Entry<IProperty<?>, Comparable<?>> entry1 : ifluidstate.getValues().entrySet()) {
               list.add(this.getPropertyString(entry1));
            }

            for(ResourceLocation resourcelocation1 : ifluidstate.getFluid().getTags()) {
               list.add("#" + resourcelocation1);
            }
         }

         Entity entity = this.mc.pointedEntity;
         if (entity != null) {
            list.add("");
            list.add(TextFormatting.UNDERLINE + "Targeted Entity");
            list.add(String.valueOf((Object)Registry.ENTITY_TYPE.getKey(entity.getType())));
            entity.getType().getTags().forEach(t -> list.add("#" + t));
         }

         return list;
      }
   }

   private String getPropertyString(Entry<IProperty<?>, Comparable<?>> entryIn) {
      IProperty<?> iproperty = entryIn.getKey();
      Comparable<?> comparable = entryIn.getValue();
      String s = Util.getValueName(iproperty, comparable);
      if (Boolean.TRUE.equals(comparable)) {
         s = TextFormatting.GREEN + s;
      } else if (Boolean.FALSE.equals(comparable)) {
         s = TextFormatting.RED + s;
      }

      return iproperty.getName() + ": " + s;
   }

   private void func_230023_a_(FrameTimer p_230023_1_, int p_230023_2_, int p_230023_3_, boolean p_230023_4_) {
      RenderSystem.disableDepthTest();
      int i = p_230023_1_.getLastIndex();
      int j = p_230023_1_.getIndex();
      long[] along = p_230023_1_.getFrames();
      int l = p_230023_2_;
      int i1 = Math.max(0, along.length - p_230023_3_);
      int j1 = along.length - i1;
      int lvt_8_1_ = p_230023_1_.parseIndex(i + i1);
      long k1 = 0L;
      int l1 = Integer.MAX_VALUE;
      int i2 = Integer.MIN_VALUE;

      for(int j2 = 0; j2 < j1; ++j2) {
         int k2 = (int)(along[p_230023_1_.parseIndex(lvt_8_1_ + j2)] / 1000000L);
         l1 = Math.min(l1, k2);
         i2 = Math.max(i2, k2);
         k1 += (long)k2;
      }

      int k4 = this.mc.func_228018_at_().getScaledHeight();
      fill(p_230023_2_, k4 - 60, p_230023_2_ + j1, k4, -1873784752);
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);

      for(Matrix4f matrix4f = TransformationMatrix.func_227983_a_().func_227988_c_(); lvt_8_1_ != j; lvt_8_1_ = p_230023_1_.parseIndex(lvt_8_1_ + 1)) {
         int l2 = p_230023_1_.func_219792_a(along[lvt_8_1_], p_230023_4_ ? 30 : 60, p_230023_4_ ? 60 : 20);
         int i3 = p_230023_4_ ? 100 : 60;
         int j3 = this.getFrameColor(MathHelper.clamp(l2, 0, i3), 0, i3 / 2, i3);
         int k3 = j3 >> 24 & 255;
         int l3 = j3 >> 16 & 255;
         int i4 = j3 >> 8 & 255;
         int j4 = j3 & 255;
         bufferbuilder.func_227888_a_(matrix4f, (float)(l + 1), (float)k4, 0.0F).func_225586_a_(l3, i4, j4, k3).endVertex();
         bufferbuilder.func_227888_a_(matrix4f, (float)l, (float)k4, 0.0F).func_225586_a_(l3, i4, j4, k3).endVertex();
         bufferbuilder.func_227888_a_(matrix4f, (float)l, (float)(k4 - l2 + 1), 0.0F).func_225586_a_(l3, i4, j4, k3).endVertex();
         bufferbuilder.func_227888_a_(matrix4f, (float)(l + 1), (float)(k4 - l2 + 1), 0.0F).func_225586_a_(l3, i4, j4, k3).endVertex();
         ++l;
      }

      bufferbuilder.finishDrawing();
      WorldVertexBufferUploader.draw(bufferbuilder);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      if (p_230023_4_) {
         fill(p_230023_2_ + 1, k4 - 30 + 1, p_230023_2_ + 14, k4 - 30 + 10, -1873784752);
         this.fontRenderer.drawString("60 FPS", (float)(p_230023_2_ + 2), (float)(k4 - 30 + 2), 14737632);
         this.hLine(p_230023_2_, p_230023_2_ + j1 - 1, k4 - 30, -1);
         fill(p_230023_2_ + 1, k4 - 60 + 1, p_230023_2_ + 14, k4 - 60 + 10, -1873784752);
         this.fontRenderer.drawString("30 FPS", (float)(p_230023_2_ + 2), (float)(k4 - 60 + 2), 14737632);
         this.hLine(p_230023_2_, p_230023_2_ + j1 - 1, k4 - 60, -1);
      } else {
         fill(p_230023_2_ + 1, k4 - 60 + 1, p_230023_2_ + 14, k4 - 60 + 10, -1873784752);
         this.fontRenderer.drawString("20 TPS", (float)(p_230023_2_ + 2), (float)(k4 - 60 + 2), 14737632);
         this.hLine(p_230023_2_, p_230023_2_ + j1 - 1, k4 - 60, -1);
      }

      this.hLine(p_230023_2_, p_230023_2_ + j1 - 1, k4 - 1, -1);
      this.vLine(p_230023_2_, k4 - 60, k4, -1);
      this.vLine(p_230023_2_ + j1 - 1, k4 - 60, k4, -1);
      if (p_230023_4_ && this.mc.gameSettings.framerateLimit > 0 && this.mc.gameSettings.framerateLimit <= 250) {
         this.hLine(p_230023_2_, p_230023_2_ + j1 - 1, k4 - 1 - (int)(1800.0D / (double)this.mc.gameSettings.framerateLimit), -16711681);
      }

      String s = l1 + " ms min";
      String s1 = k1 / (long)j1 + " ms avg";
      String s2 = i2 + " ms max";
      this.fontRenderer.drawStringWithShadow(s, (float)(p_230023_2_ + 2), (float)(k4 - 60 - 9), 14737632);
      this.fontRenderer.drawStringWithShadow(s1, (float)(p_230023_2_ + j1 / 2 - this.fontRenderer.getStringWidth(s1) / 2), (float)(k4 - 60 - 9), 14737632);
      this.fontRenderer.drawStringWithShadow(s2, (float)(p_230023_2_ + j1 - this.fontRenderer.getStringWidth(s2)), (float)(k4 - 60 - 9), 14737632);
      RenderSystem.enableDepthTest();
   }

   private int getFrameColor(int height, int heightMin, int heightMid, int heightMax) {
      return height < heightMid ? this.blendColors(-16711936, -256, (float)height / (float)heightMid) : this.blendColors(-256, -65536, (float)(height - heightMid) / (float)(heightMax - heightMid));
   }

   private int blendColors(int col1, int col2, float factor) {
      int i = col1 >> 24 & 255;
      int j = col1 >> 16 & 255;
      int k = col1 >> 8 & 255;
      int l = col1 & 255;
      int i1 = col2 >> 24 & 255;
      int j1 = col2 >> 16 & 255;
      int k1 = col2 >> 8 & 255;
      int l1 = col2 & 255;
      int i2 = MathHelper.clamp((int)MathHelper.lerp(factor, (float)i, (float)i1), 0, 255);
      int j2 = MathHelper.clamp((int)MathHelper.lerp(factor, (float)j, (float)j1), 0, 255);
      int k2 = MathHelper.clamp((int)MathHelper.lerp(factor, (float)k, (float)k1), 0, 255);
      int l2 = MathHelper.clamp((int)MathHelper.lerp(factor, (float)l, (float)l1), 0, 255);
      return i2 << 24 | j2 << 16 | k2 << 8 | l2;
   }

   private static long bytesToMb(long bytes) {
      return bytes / 1024L / 1024L;
   }
}