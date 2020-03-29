package net.minecraft.client.multiplayer;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientChunkProvider extends AbstractChunkProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Chunk empty;
   private final WorldLightManager lightManager;
   private volatile ClientChunkProvider.ChunkArray array;
   private final ClientWorld world;

   public ClientChunkProvider(ClientWorld p_i51057_1_, int viewDistance) {
      this.world = p_i51057_1_;
      this.empty = new EmptyChunk(p_i51057_1_, new ChunkPos(0, 0));
      this.lightManager = new WorldLightManager(this, true, p_i51057_1_.getDimension().hasSkyLight());
      this.array = new ClientChunkProvider.ChunkArray(adjustViewDistance(viewDistance));
   }

   public WorldLightManager getLightManager() {
      return this.lightManager;
   }

   private static boolean isValid(@Nullable Chunk p_217249_0_, int p_217249_1_, int p_217249_2_) {
      if (p_217249_0_ == null) {
         return false;
      } else {
         ChunkPos chunkpos = p_217249_0_.getPos();
         return chunkpos.x == p_217249_1_ && chunkpos.z == p_217249_2_;
      }
   }

   /**
    * Unload chunk from ChunkProviderClient's hashmap. Called in response to a Packet50PreChunk with its mode field set
    * to false
    */
   public void unloadChunk(int x, int z) {
      if (this.array.inView(x, z)) {
         int i = this.array.getIndex(x, z);
         Chunk chunk = this.array.get(i);
         if (isValid(chunk, x, z)) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkEvent.Unload(chunk));
            this.array.unload(i, chunk, (Chunk)null);
         }

      }
   }

   @Nullable
   public Chunk getChunk(int chunkX, int chunkZ, ChunkStatus requiredStatus, boolean load) {
      if (this.array.inView(chunkX, chunkZ)) {
         Chunk chunk = this.array.get(this.array.getIndex(chunkX, chunkZ));
         if (isValid(chunk, chunkX, chunkZ)) {
            return chunk;
         }
      }

      return load ? this.empty : null;
   }

   public IBlockReader getWorld() {
      return this.world;
   }

   @Nullable
   public Chunk func_228313_a_(int p_228313_1_, int p_228313_2_, @Nullable BiomeContainer p_228313_3_, PacketBuffer p_228313_4_, CompoundNBT p_228313_5_, int p_228313_6_) {
      if (!this.array.inView(p_228313_1_, p_228313_2_)) {
         LOGGER.warn("Ignoring chunk since it's not in the view range: {}, {}", p_228313_1_, p_228313_2_);
         return null;
      } else {
         int i = this.array.getIndex(p_228313_1_, p_228313_2_);
         Chunk chunk = this.array.chunks.get(i);
         if (!isValid(chunk, p_228313_1_, p_228313_2_)) {
            if (p_228313_3_ == null) {
               LOGGER.warn("Ignoring chunk since we don't have complete data: {}, {}", p_228313_1_, p_228313_2_);
               return null;
            }

            chunk = new Chunk(this.world, new ChunkPos(p_228313_1_, p_228313_2_), p_228313_3_);
            chunk.func_227073_a_(p_228313_3_, p_228313_4_, p_228313_5_, p_228313_6_);
            this.array.replace(i, chunk);
         } else {
            chunk.func_227073_a_(p_228313_3_, p_228313_4_, p_228313_5_, p_228313_6_);
         }

         ChunkSection[] achunksection = chunk.getSections();
         WorldLightManager worldlightmanager = this.getLightManager();
         worldlightmanager.func_215571_a(new ChunkPos(p_228313_1_, p_228313_2_), true);

         for(int j = 0; j < achunksection.length; ++j) {
            ChunkSection chunksection = achunksection[j];
            worldlightmanager.updateSectionStatus(SectionPos.of(p_228313_1_, j, p_228313_2_), ChunkSection.isEmpty(chunksection));
         }

         this.world.func_228323_e_(p_228313_1_, p_228313_2_);
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkEvent.Load(chunk));
         return chunk;
      }
   }

   public void tick(BooleanSupplier hasTimeLeft) {
   }

   public void setCenter(int p_217251_1_, int p_217251_2_) {
      this.array.centerX = p_217251_1_;
      this.array.centerZ = p_217251_2_;
   }

   public void setViewDistance(int p_217248_1_) {
      int i = this.array.viewDistance;
      int j = adjustViewDistance(p_217248_1_);
      if (i != j) {
         ClientChunkProvider.ChunkArray clientchunkprovider$chunkarray = new ClientChunkProvider.ChunkArray(j);
         clientchunkprovider$chunkarray.centerX = this.array.centerX;
         clientchunkprovider$chunkarray.centerZ = this.array.centerZ;

         for(int k = 0; k < this.array.chunks.length(); ++k) {
            Chunk chunk = this.array.chunks.get(k);
            if (chunk != null) {
               ChunkPos chunkpos = chunk.getPos();
               if (clientchunkprovider$chunkarray.inView(chunkpos.x, chunkpos.z)) {
                  clientchunkprovider$chunkarray.replace(clientchunkprovider$chunkarray.getIndex(chunkpos.x, chunkpos.z), chunk);
               }
            }
         }

         this.array = clientchunkprovider$chunkarray;
      }

   }

   private static int adjustViewDistance(int p_217254_0_) {
      return Math.max(2, p_217254_0_) + 3;
   }

   /**
    * Converts the instance data to a readable string.
    */
   public String makeString() {
      return "Client Chunk Cache: " + this.array.chunks.length() + ", " + this.func_217252_g();
   }

   public int func_217252_g() {
      return this.array.loaded;
   }

   public void markLightChanged(LightType type, SectionPos pos) {
      Minecraft.getInstance().worldRenderer.markForRerender(pos.getSectionX(), pos.getSectionY(), pos.getSectionZ());
   }

   public boolean canTick(BlockPos pos) {
      return this.chunkExists(pos.getX() >> 4, pos.getZ() >> 4);
   }

   public boolean isChunkLoaded(ChunkPos pos) {
      return this.chunkExists(pos.x, pos.z);
   }

   public boolean isChunkLoaded(Entity entityIn) {
      return this.chunkExists(MathHelper.floor(entityIn.func_226277_ct_()) >> 4, MathHelper.floor(entityIn.func_226281_cx_()) >> 4);
   }

   @OnlyIn(Dist.CLIENT)
   final class ChunkArray {
      private final AtomicReferenceArray<Chunk> chunks;
      private final int viewDistance;
      private final int sideLength;
      private volatile int centerX;
      private volatile int centerZ;
      private int loaded;

      private ChunkArray(int p_i50568_2_) {
         this.viewDistance = p_i50568_2_;
         this.sideLength = p_i50568_2_ * 2 + 1;
         this.chunks = new AtomicReferenceArray<>(this.sideLength * this.sideLength);
      }

      private int getIndex(int x, int z) {
         return Math.floorMod(z, this.sideLength) * this.sideLength + Math.floorMod(x, this.sideLength);
      }

      protected void replace(int p_217181_1_, @Nullable Chunk p_217181_2_) {
         Chunk chunk = this.chunks.getAndSet(p_217181_1_, p_217181_2_);
         if (chunk != null) {
            --this.loaded;
            ClientChunkProvider.this.world.onChunkUnloaded(chunk);
         }

         if (p_217181_2_ != null) {
            ++this.loaded;
         }

      }

      protected Chunk unload(int p_217190_1_, Chunk p_217190_2_, @Nullable Chunk p_217190_3_) {
         if (this.chunks.compareAndSet(p_217190_1_, p_217190_2_, p_217190_3_) && p_217190_3_ == null) {
            --this.loaded;
         }

         ClientChunkProvider.this.world.onChunkUnloaded(p_217190_2_);
         return p_217190_2_;
      }

      private boolean inView(int p_217183_1_, int p_217183_2_) {
         return Math.abs(p_217183_1_ - this.centerX) <= this.viewDistance && Math.abs(p_217183_2_ - this.centerZ) <= this.viewDistance;
      }

      @Nullable
      protected Chunk get(int p_217192_1_) {
         return this.chunks.get(p_217192_1_);
      }
   }
}