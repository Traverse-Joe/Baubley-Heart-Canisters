package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.crash.CrashReport;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ChunkRenderDispatcher {
   private static final Logger LOGGER = LogManager.getLogger();
   private final PriorityQueue<ChunkRenderDispatcher.ChunkRender.ChunkRenderTask> field_228885_b_ = Queues.newPriorityQueue();
   private final Queue<RegionRenderCacheBuilder> field_228886_c_;
   private final Queue<Runnable> field_228887_d_ = Queues.newConcurrentLinkedQueue();
   private volatile int field_228888_e_;
   private volatile int field_228889_f_;
   private final RegionRenderCacheBuilder field_228890_g_;
   private final DelegatedTaskExecutor<Runnable> field_228891_h_;
   private final Executor field_228892_i_;
   private World field_228893_j_;
   private final WorldRenderer field_228894_k_;
   private Vec3d field_217672_l = Vec3d.ZERO;

   public ChunkRenderDispatcher(World p_i226020_1_, WorldRenderer p_i226020_2_, Executor p_i226020_3_, boolean p_i226020_4_, RegionRenderCacheBuilder p_i226020_5_) {
      this(p_i226020_1_, p_i226020_2_, p_i226020_3_, p_i226020_4_, p_i226020_5_, -1);
   }
   public ChunkRenderDispatcher(World p_i226020_1_, WorldRenderer p_i226020_2_, Executor p_i226020_3_, boolean p_i226020_4_, RegionRenderCacheBuilder p_i226020_5_, int countRenderBuilders) {
      this.field_228893_j_ = p_i226020_1_;
      this.field_228894_k_ = p_i226020_2_;
      int i = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3D) / (RenderType.func_228661_n_().stream().mapToInt(RenderType::func_228662_o_).sum() * 4) - 1);
      int j = Runtime.getRuntime().availableProcessors();
      int k = p_i226020_4_ ? j : Math.min(j, 4);
      int l = countRenderBuilders < 0 ? Math.max(1, Math.min(k, i)) : countRenderBuilders;
      this.field_228890_g_ = p_i226020_5_;
      List<RegionRenderCacheBuilder> list = Lists.newArrayListWithExpectedSize(l);

      try {
         for(int i1 = 0; i1 < l; ++i1) {
            list.add(new RegionRenderCacheBuilder());
         }
      } catch (OutOfMemoryError var14) {
         LOGGER.warn("Allocated only {}/{} buffers", list.size(), l);
         int j1 = Math.min(list.size() * 2 / 3, list.size() - 1);

         for(int k1 = 0; k1 < j1; ++k1) {
            list.remove(list.size() - 1);
         }

         System.gc();
      }

      this.field_228886_c_ = Queues.newArrayDeque(list);
      this.field_228889_f_ = this.field_228886_c_.size();
      this.field_228892_i_ = p_i226020_3_;
      this.field_228891_h_ = DelegatedTaskExecutor.create(p_i226020_3_, "Chunk Renderer");
      this.field_228891_h_.enqueue(this::func_228909_h_);
   }

   public void func_228895_a_(World p_228895_1_) {
      this.field_228893_j_ = p_228895_1_;
   }

   private void func_228909_h_() {
      if (!this.field_228886_c_.isEmpty()) {
         ChunkRenderDispatcher.ChunkRender.ChunkRenderTask chunkrenderdispatcher$chunkrender$chunkrendertask = this.field_228885_b_.poll();
         if (chunkrenderdispatcher$chunkrender$chunkrendertask != null) {
            RegionRenderCacheBuilder regionrendercachebuilder = this.field_228886_c_.poll();
            this.field_228888_e_ = this.field_228885_b_.size();
            this.field_228889_f_ = this.field_228886_c_.size();
            CompletableFuture.runAsync(() -> {
            }, this.field_228892_i_).thenCompose((p_228901_2_) -> {
               return chunkrenderdispatcher$chunkrender$chunkrendertask.func_225618_a_(regionrendercachebuilder);
            }).whenComplete((p_228898_2_, p_228898_3_) -> {
               if (p_228898_3_ != null) {
                  CrashReport crashreport = CrashReport.makeCrashReport(p_228898_3_, "Batching chunks");
                  Minecraft.getInstance().crashed(Minecraft.getInstance().addGraphicsAndWorldToCrashReport(crashreport));
               } else {
                  this.field_228891_h_.enqueue(() -> {
                     if (p_228898_2_ == ChunkRenderDispatcher.ChunkTaskResult.SUCCESSFUL) {
                        regionrendercachebuilder.func_228365_a_();
                     } else {
                        regionrendercachebuilder.func_228367_b_();
                     }

                     this.field_228886_c_.add(regionrendercachebuilder);
                     this.field_228889_f_ = this.field_228886_c_.size();
                     this.func_228909_h_();
                  });
               }
            });
         }
      }
   }

   public String getDebugInfo() {
      return String.format("pC: %03d, pU: %02d, aB: %02d", this.field_228888_e_, this.field_228887_d_.size(), this.field_228889_f_);
   }

   public void func_217669_a(Vec3d p_217669_1_) {
      this.field_217672_l = p_217669_1_;
   }

   public Vec3d func_217671_b() {
      return this.field_217672_l;
   }

   public boolean func_228908_d_() {
      boolean flag;
      Runnable runnable;
      for(flag = false; (runnable = this.field_228887_d_.poll()) != null; flag = true) {
         runnable.run();
      }

      return flag;
   }

   public void func_228902_a_(ChunkRenderDispatcher.ChunkRender p_228902_1_) {
      p_228902_1_.func_228936_k_();
   }

   public void stopChunkUpdates() {
      this.clearChunkUpdates();
   }

   public void func_228900_a_(ChunkRenderDispatcher.ChunkRender.ChunkRenderTask p_228900_1_) {
      this.field_228891_h_.enqueue(() -> {
         this.field_228885_b_.offer(p_228900_1_);
         this.field_228888_e_ = this.field_228885_b_.size();
         this.func_228909_h_();
      });
   }

   public CompletableFuture<Void> func_228896_a_(BufferBuilder p_228896_1_, VertexBuffer p_228896_2_) {
      return CompletableFuture.runAsync(() -> {
      }, this.field_228887_d_::add).thenCompose((p_228897_3_) -> {
         return this.func_228904_b_(p_228896_1_, p_228896_2_);
      });
   }

   private CompletableFuture<Void> func_228904_b_(BufferBuilder p_228904_1_, VertexBuffer p_228904_2_) {
      return p_228904_2_.func_227878_b_(p_228904_1_);
   }

   private void clearChunkUpdates() {
      while(!this.field_228885_b_.isEmpty()) {
         ChunkRenderDispatcher.ChunkRender.ChunkRenderTask chunkrenderdispatcher$chunkrender$chunkrendertask = this.field_228885_b_.poll();
         if (chunkrenderdispatcher$chunkrender$chunkrendertask != null) {
            chunkrenderdispatcher$chunkrender$chunkrendertask.func_225617_a_();
         }
      }

      this.field_228888_e_ = 0;
   }

   public boolean hasNoChunkUpdates() {
      return this.field_228888_e_ == 0 && this.field_228887_d_.isEmpty();
   }

   public void stopWorkerThreads() {
      this.clearChunkUpdates();
      this.field_228891_h_.close();
      this.field_228886_c_.clear();
   }

   @OnlyIn(Dist.CLIENT)
   public class ChunkRender implements net.minecraftforge.client.extensions.IForgeRenderChunk {
      public final AtomicReference<ChunkRenderDispatcher.CompiledChunk> compiledChunk = new AtomicReference<>(ChunkRenderDispatcher.CompiledChunk.DUMMY);
      @Nullable
      private ChunkRenderDispatcher.ChunkRender.RebuildTask field_228921_d_;
      @Nullable
      private ChunkRenderDispatcher.ChunkRender.SortTransparencyTask field_228922_e_;
      private final Set<TileEntity> setTileEntities = Sets.newHashSet();
      private final Map<RenderType, VertexBuffer> vertexBuffers = RenderType.func_228661_n_().stream().collect(Collectors.toMap((p_228934_0_) -> {
         return p_228934_0_;
      }, (p_228933_0_) -> {
         return new VertexBuffer(DefaultVertexFormats.BLOCK);
      }));
      public AxisAlignedBB boundingBox;
      private int frameIndex = -1;
      private boolean needsUpdate = true;
      private final BlockPos.Mutable position = new BlockPos.Mutable(-1, -1, -1);
      private final BlockPos.Mutable[] mapEnumFacing = Util.make(new BlockPos.Mutable[6], (p_228932_0_) -> {
         for(int i = 0; i < p_228932_0_.length; ++i) {
            p_228932_0_[i] = new BlockPos.Mutable();
         }

      });
      private boolean needsImmediateUpdate;

      private boolean func_228930_a_(BlockPos p_228930_1_) {
         return ChunkRenderDispatcher.this.field_228893_j_.getChunk(p_228930_1_.getX() >> 4, p_228930_1_.getZ() >> 4, ChunkStatus.FULL, false) != null;
      }

      public boolean shouldStayLoaded() {
         int i = 24;
         if (!(this.getDistanceSq() > 576.0D)) {
            return true;
         } else {
            return this.func_228930_a_(this.mapEnumFacing[Direction.WEST.ordinal()]) && this.func_228930_a_(this.mapEnumFacing[Direction.NORTH.ordinal()]) && this.func_228930_a_(this.mapEnumFacing[Direction.EAST.ordinal()]) && this.func_228930_a_(this.mapEnumFacing[Direction.SOUTH.ordinal()]);
         }
      }

      public boolean setFrameIndex(int frameIndexIn) {
         if (this.frameIndex == frameIndexIn) {
            return false;
         } else {
            this.frameIndex = frameIndexIn;
            return true;
         }
      }

      public VertexBuffer func_228924_a_(RenderType p_228924_1_) {
         return this.vertexBuffers.get(p_228924_1_);
      }

      /**
       * Sets the RenderChunk base position
       */
      public void setPosition(int x, int y, int z) {
         if (x != this.position.getX() || y != this.position.getY() || z != this.position.getZ()) {
            this.stopCompileTask();
            this.position.setPos(x, y, z);
            this.boundingBox = new AxisAlignedBB((double)x, (double)y, (double)z, (double)(x + 16), (double)(y + 16), (double)(z + 16));

            for(Direction direction : Direction.values()) {
               this.mapEnumFacing[direction.ordinal()].setPos(this.position).move(direction, 16);
            }

         }
      }

      protected double getDistanceSq() {
         ActiveRenderInfo activerenderinfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
         double d0 = this.boundingBox.minX + 8.0D - activerenderinfo.getProjectedView().x;
         double d1 = this.boundingBox.minY + 8.0D - activerenderinfo.getProjectedView().y;
         double d2 = this.boundingBox.minZ + 8.0D - activerenderinfo.getProjectedView().z;
         return d0 * d0 + d1 * d1 + d2 * d2;
      }

      private void func_228923_a_(BufferBuilder p_228923_1_) {
         p_228923_1_.begin(7, DefaultVertexFormats.BLOCK);
      }

      public ChunkRenderDispatcher.CompiledChunk getCompiledChunk() {
         return this.compiledChunk.get();
      }

      private void stopCompileTask() {
         this.func_228935_i_();
         this.compiledChunk.set(ChunkRenderDispatcher.CompiledChunk.DUMMY);
         this.needsUpdate = true;
      }

      public void deleteGlResources() {
         this.stopCompileTask();
         this.vertexBuffers.values().forEach(VertexBuffer::close);
      }

      public BlockPos getPosition() {
         return this.position;
      }

      public void setNeedsUpdate(boolean immediate) {
         boolean flag = this.needsUpdate;
         this.needsUpdate = true;
         this.needsImmediateUpdate = immediate | (flag && this.needsImmediateUpdate);
      }

      public void clearNeedsUpdate() {
         this.needsUpdate = false;
         this.needsImmediateUpdate = false;
      }

      public boolean needsUpdate() {
         return this.needsUpdate;
      }

      public boolean needsImmediateUpdate() {
         return this.needsUpdate && this.needsImmediateUpdate;
      }

      public BlockPos getBlockPosOffset16(Direction facing) {
         return this.mapEnumFacing[facing.ordinal()];
      }

      public boolean func_228925_a_(RenderType p_228925_1_, ChunkRenderDispatcher p_228925_2_) {
         ChunkRenderDispatcher.CompiledChunk chunkrenderdispatcher$compiledchunk = this.getCompiledChunk();
         if (this.field_228922_e_ != null) {
            this.field_228922_e_.func_225617_a_();
         }

         if (!chunkrenderdispatcher$compiledchunk.layersStarted.contains(p_228925_1_)) {
            return false;
         } else {
            this.field_228922_e_ = new ChunkRenderDispatcher.ChunkRender.SortTransparencyTask(new net.minecraft.util.math.ChunkPos(getPosition()), this.getDistanceSq(), chunkrenderdispatcher$compiledchunk);
            p_228925_2_.func_228900_a_(this.field_228922_e_);
            return true;
         }
      }

      protected void func_228935_i_() {
         if (this.field_228921_d_ != null) {
            this.field_228921_d_.func_225617_a_();
            this.field_228921_d_ = null;
         }

         if (this.field_228922_e_ != null) {
            this.field_228922_e_.func_225617_a_();
            this.field_228922_e_ = null;
         }

      }

      public ChunkRenderDispatcher.ChunkRender.ChunkRenderTask makeCompileTaskChunk() {
         this.func_228935_i_();
         BlockPos blockpos = this.position.toImmutable();
         int i = 1;
         ChunkRenderCache chunkrendercache = createRegionRenderCache(ChunkRenderDispatcher.this.field_228893_j_, blockpos.add(-1, -1, -1), blockpos.add(16, 16, 16), 1);
         this.field_228921_d_ = new ChunkRenderDispatcher.ChunkRender.RebuildTask(new net.minecraft.util.math.ChunkPos(getPosition()), this.getDistanceSq(), chunkrendercache);
         return this.field_228921_d_;
      }

      public void func_228929_a_(ChunkRenderDispatcher p_228929_1_) {
         ChunkRenderDispatcher.ChunkRender.ChunkRenderTask chunkrenderdispatcher$chunkrender$chunkrendertask = this.makeCompileTaskChunk();
         p_228929_1_.func_228900_a_(chunkrenderdispatcher$chunkrender$chunkrendertask);
      }

      private void func_228931_a_(Set<TileEntity> p_228931_1_) {
         Set<TileEntity> set = Sets.newHashSet(p_228931_1_);
         Set<TileEntity> set1 = Sets.newHashSet(this.setTileEntities);
         set.removeAll(this.setTileEntities);
         set1.removeAll(p_228931_1_);
         this.setTileEntities.clear();
         this.setTileEntities.addAll(p_228931_1_);
         ChunkRenderDispatcher.this.field_228894_k_.updateTileEntities(set1, set);
      }

      public void func_228936_k_() {
         ChunkRenderDispatcher.ChunkRender.ChunkRenderTask chunkrenderdispatcher$chunkrender$chunkrendertask = this.makeCompileTaskChunk();
         chunkrenderdispatcher$chunkrender$chunkrendertask.func_225618_a_(ChunkRenderDispatcher.this.field_228890_g_);
      }

      @OnlyIn(Dist.CLIENT)
      abstract class ChunkRenderTask implements Comparable<ChunkRenderDispatcher.ChunkRender.ChunkRenderTask> {
         protected final double distanceSq;
         protected final AtomicBoolean finished = new AtomicBoolean(false);
         protected java.util.Map<net.minecraft.util.math.BlockPos, net.minecraftforge.client.model.data.IModelData> modelData;

         public ChunkRenderTask(double p_i226023_2_) {
            this(null, p_i226023_2_);
         }

         public ChunkRenderTask(@Nullable net.minecraft.util.math.ChunkPos pos, double p_i226023_2_) {
            this.distanceSq = p_i226023_2_;
            if (pos == null) {
                this.modelData = java.util.Collections.emptyMap();
            } else {
                this.modelData = net.minecraftforge.client.model.ModelDataManager.getModelData(net.minecraft.client.Minecraft.getInstance().world, pos);
            }
         }

         public abstract CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> func_225618_a_(RegionRenderCacheBuilder p_225618_1_);

         public abstract void func_225617_a_();

         public int compareTo(ChunkRenderDispatcher.ChunkRender.ChunkRenderTask p_compareTo_1_) {
            return Doubles.compare(this.distanceSq, p_compareTo_1_.distanceSq);
         }

         public net.minecraftforge.client.model.data.IModelData getModelData(net.minecraft.util.math.BlockPos pos) {
            return modelData.getOrDefault(pos, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
         }
      }

      @OnlyIn(Dist.CLIENT)
      class RebuildTask extends ChunkRenderDispatcher.ChunkRender.ChunkRenderTask {
         @Nullable
         protected ChunkRenderCache field_228938_d_;

         @Deprecated
         public RebuildTask(double p_i226024_2_, @Nullable ChunkRenderCache p_i226024_4_) {
            this(null, p_i226024_2_, p_i226024_4_);
         }

         public RebuildTask(@Nullable net.minecraft.util.math.ChunkPos pos, double p_i226024_2_, @Nullable ChunkRenderCache p_i226024_4_) {
            super(pos, p_i226024_2_);
            this.field_228938_d_ = p_i226024_4_;
         }

         public CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> func_225618_a_(RegionRenderCacheBuilder p_225618_1_) {
            if (this.finished.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (!ChunkRender.this.shouldStayLoaded()) {
               this.field_228938_d_ = null;
               ChunkRender.this.setNeedsUpdate(false);
               this.finished.set(true);
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (this.finished.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else {
               Vec3d vec3d = ChunkRenderDispatcher.this.func_217671_b();
               float f = (float)vec3d.x;
               float f1 = (float)vec3d.y;
               float f2 = (float)vec3d.z;
               ChunkRenderDispatcher.CompiledChunk chunkrenderdispatcher$compiledchunk = new ChunkRenderDispatcher.CompiledChunk();
               Set<TileEntity> set = this.func_228940_a_(f, f1, f2, chunkrenderdispatcher$compiledchunk, p_225618_1_);
               ChunkRender.this.func_228931_a_(set);
               if (this.finished.get()) {
                  return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
               } else {
                  List<CompletableFuture<Void>> list = Lists.newArrayList();
                  chunkrenderdispatcher$compiledchunk.layersStarted.forEach((p_228943_3_) -> {
                     list.add(ChunkRenderDispatcher.this.func_228896_a_(p_225618_1_.func_228366_a_(p_228943_3_), ChunkRender.this.func_228924_a_(p_228943_3_)));
                  });
                  return Util.gather(list).handle((p_228941_2_, p_228941_3_) -> {
                     if (p_228941_3_ != null && !(p_228941_3_ instanceof CancellationException) && !(p_228941_3_ instanceof InterruptedException)) {
                        Minecraft.getInstance().crashed(CrashReport.makeCrashReport(p_228941_3_, "Rendering chunk"));
                     }

                     if (this.finished.get()) {
                        return ChunkRenderDispatcher.ChunkTaskResult.CANCELLED;
                     } else {
                        ChunkRender.this.compiledChunk.set(chunkrenderdispatcher$compiledchunk);
                        return ChunkRenderDispatcher.ChunkTaskResult.SUCCESSFUL;
                     }
                  });
               }
            }
         }

         private Set<TileEntity> func_228940_a_(float p_228940_1_, float p_228940_2_, float p_228940_3_, ChunkRenderDispatcher.CompiledChunk p_228940_4_, RegionRenderCacheBuilder p_228940_5_) {
            int i = 1;
            BlockPos blockpos = ChunkRender.this.position.toImmutable();
            BlockPos blockpos1 = blockpos.add(15, 15, 15);
            VisGraph visgraph = new VisGraph();
            Set<TileEntity> set = Sets.newHashSet();
            ChunkRenderCache chunkrendercache = this.field_228938_d_;
            this.field_228938_d_ = null;
            MatrixStack matrixstack = new MatrixStack();
            if (chunkrendercache != null) {
               BlockModelRenderer.enableCache();
               Random random = new Random();
               BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();

               for(BlockPos blockpos2 : BlockPos.getAllInBoxMutable(blockpos, blockpos1)) {
                  BlockState blockstate = chunkrendercache.getBlockState(blockpos2);
                  Block block = blockstate.getBlock();
                  if (blockstate.isOpaqueCube(chunkrendercache, blockpos2)) {
                     visgraph.setOpaqueCube(blockpos2);
                  }

                  if (blockstate.hasTileEntity()) {
                     TileEntity tileentity = chunkrendercache.getTileEntity(blockpos2, Chunk.CreateEntityType.CHECK);
                     if (tileentity != null) {
                        this.func_228942_a_(p_228940_4_, set, tileentity);
                     }
                  }

                  IFluidState ifluidstate = chunkrendercache.getFluidState(blockpos2);
                  net.minecraftforge.client.model.data.IModelData modelData = getModelData(blockpos2);
                  for (RenderType rendertype : RenderType.func_228661_n_()) {
                      net.minecraftforge.client.ForgeHooksClient.setRenderLayer(rendertype);
                  if (!ifluidstate.isEmpty() && RenderTypeLookup.canRenderInLayer(ifluidstate, rendertype)) {
                     BufferBuilder bufferbuilder = p_228940_5_.func_228366_a_(rendertype);
                     if (p_228940_4_.layersStarted.add(rendertype)) {
                        ChunkRender.this.func_228923_a_(bufferbuilder);
                     }

                     if (blockrendererdispatcher.func_228794_a_(blockpos2, chunkrendercache, bufferbuilder, ifluidstate)) {
                        p_228940_4_.empty = false;
                        p_228940_4_.layersUsed.add(rendertype);
                     }
                  }

                  if (blockstate.getRenderType() != BlockRenderType.INVISIBLE && RenderTypeLookup.canRenderInLayer(blockstate, rendertype)) {
                     RenderType rendertype1 = rendertype;
                     BufferBuilder bufferbuilder2 = p_228940_5_.func_228366_a_(rendertype1);
                     if (p_228940_4_.layersStarted.add(rendertype1)) {
                        ChunkRender.this.func_228923_a_(bufferbuilder2);
                     }

                     matrixstack.func_227860_a_();
                     matrixstack.func_227861_a_((double)(blockpos2.getX() & 15), (double)(blockpos2.getY() & 15), (double)(blockpos2.getZ() & 15));
                     if (blockrendererdispatcher.renderModel(blockstate, blockpos2, chunkrendercache, matrixstack, bufferbuilder2, true, random, modelData)) {
                        p_228940_4_.empty = false;
                        p_228940_4_.layersUsed.add(rendertype1);
                     }

                     matrixstack.func_227865_b_();
                  }
               }
               }
               net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);

               if (p_228940_4_.layersUsed.contains(RenderType.func_228645_f_())) {
                  BufferBuilder bufferbuilder1 = p_228940_5_.func_228366_a_(RenderType.func_228645_f_());
                  bufferbuilder1.sortVertexData(p_228940_1_ - (float)blockpos.getX(), p_228940_2_ - (float)blockpos.getY(), p_228940_3_ - (float)blockpos.getZ());
                  p_228940_4_.state = bufferbuilder1.getVertexState();
               }

               p_228940_4_.layersStarted.stream().map(p_228940_5_::func_228366_a_).forEach(BufferBuilder::finishDrawing);
               BlockModelRenderer.disableCache();
            }

            p_228940_4_.setVisibility = visgraph.computeVisibility();
            return set;
         }

         private <E extends TileEntity> void func_228942_a_(ChunkRenderDispatcher.CompiledChunk p_228942_1_, Set<TileEntity> p_228942_2_, E p_228942_3_) {
            TileEntityRenderer<E> tileentityrenderer = TileEntityRendererDispatcher.instance.getRenderer(p_228942_3_);
            if (tileentityrenderer != null) {
               if (tileentityrenderer.isGlobalRenderer(p_228942_3_)) {
                  p_228942_2_.add(p_228942_3_);
               }
               else p_228942_1_.tileEntities.add(p_228942_3_); //FORGE: Fix MC-112730
            }

         }

         public void func_225617_a_() {
            this.field_228938_d_ = null;
            if (this.finished.compareAndSet(false, true)) {
               ChunkRender.this.setNeedsUpdate(false);
            }

         }
      }

      @OnlyIn(Dist.CLIENT)
      class SortTransparencyTask extends ChunkRenderDispatcher.ChunkRender.ChunkRenderTask {
         private final ChunkRenderDispatcher.CompiledChunk field_228945_e_;

         @Deprecated
         public SortTransparencyTask(double p_i226025_2_, ChunkRenderDispatcher.CompiledChunk p_i226025_4_) {
            this(null, p_i226025_2_, p_i226025_4_);
         }

         public SortTransparencyTask(@Nullable net.minecraft.util.math.ChunkPos pos, double p_i226025_2_, ChunkRenderDispatcher.CompiledChunk p_i226025_4_) {
            super(pos, p_i226025_2_);
            this.field_228945_e_ = p_i226025_4_;
         }

         public CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> func_225618_a_(RegionRenderCacheBuilder p_225618_1_) {
            if (this.finished.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (!ChunkRender.this.shouldStayLoaded()) {
               this.finished.set(true);
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (this.finished.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else {
               Vec3d vec3d = ChunkRenderDispatcher.this.func_217671_b();
               float f = (float)vec3d.x;
               float f1 = (float)vec3d.y;
               float f2 = (float)vec3d.z;
               BufferBuilder.State bufferbuilder$state = this.field_228945_e_.state;
               if (bufferbuilder$state != null && this.field_228945_e_.layersUsed.contains(RenderType.func_228645_f_())) {
                  BufferBuilder bufferbuilder = p_225618_1_.func_228366_a_(RenderType.func_228645_f_());
                  ChunkRender.this.func_228923_a_(bufferbuilder);
                  bufferbuilder.setVertexState(bufferbuilder$state);
                  bufferbuilder.sortVertexData(f - (float)ChunkRender.this.position.getX(), f1 - (float)ChunkRender.this.position.getY(), f2 - (float)ChunkRender.this.position.getZ());
                  this.field_228945_e_.state = bufferbuilder.getVertexState();
                  bufferbuilder.finishDrawing();
                  if (this.finished.get()) {
                     return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
                  } else {
                     CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> completablefuture = ChunkRenderDispatcher.this.func_228896_a_(p_225618_1_.func_228366_a_(RenderType.func_228645_f_()), ChunkRender.this.func_228924_a_(RenderType.func_228645_f_())).thenApply((p_228947_0_) -> {
                        return ChunkRenderDispatcher.ChunkTaskResult.CANCELLED;
                     });
                     return completablefuture.handle((p_228946_1_, p_228946_2_) -> {
                        if (p_228946_2_ != null && !(p_228946_2_ instanceof CancellationException) && !(p_228946_2_ instanceof InterruptedException)) {
                           Minecraft.getInstance().crashed(CrashReport.makeCrashReport(p_228946_2_, "Rendering chunk"));
                        }

                        return this.finished.get() ? ChunkRenderDispatcher.ChunkTaskResult.CANCELLED : ChunkRenderDispatcher.ChunkTaskResult.SUCCESSFUL;
                     });
                  }
               } else {
                  return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
               }
            }
         }

         public void func_225617_a_() {
            this.finished.set(true);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   static enum ChunkTaskResult {
      SUCCESSFUL,
      CANCELLED;
   }

   @OnlyIn(Dist.CLIENT)
   public static class CompiledChunk {
      public static final ChunkRenderDispatcher.CompiledChunk DUMMY = new ChunkRenderDispatcher.CompiledChunk() {
         public boolean isVisible(Direction facing, Direction facing2) {
            return false;
         }
      };
      private final Set<RenderType> layersUsed = new ObjectArraySet<>();
      private final Set<RenderType> layersStarted = new ObjectArraySet<>();
      private boolean empty = true;
      private final List<TileEntity> tileEntities = Lists.newArrayList();
      private SetVisibility setVisibility = new SetVisibility();
      @Nullable
      private BufferBuilder.State state;

      public boolean isEmpty() {
         return this.empty;
      }

      public boolean func_228912_a_(RenderType p_228912_1_) {
         return !this.layersUsed.contains(p_228912_1_);
      }

      public List<TileEntity> getTileEntities() {
         return this.tileEntities;
      }

      public boolean isVisible(Direction facing, Direction facing2) {
         return this.setVisibility.isVisible(facing, facing2);
      }
   }
}