package net.minecraft.world.gen;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.BitArray;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Heightmap {
   private static final Predicate<BlockState> field_222691_a = (p_222688_0_) -> {
      return !p_222688_0_.isAir();
   };
   private static final Predicate<BlockState> field_222692_b = (p_222689_0_) -> {
      return p_222689_0_.getMaterial().blocksMovement();
   };
   private final BitArray data = new BitArray(9, 256);
   private final Predicate<BlockState> field_222693_d;
   private final IChunk chunk;

   public Heightmap(IChunk chunkIn, Heightmap.Type type) {
      this.field_222693_d = type.func_222684_d();
      this.chunk = chunkIn;
   }

   public static void func_222690_a(IChunk p_222690_0_, Set<Heightmap.Type> p_222690_1_) {
      int i = p_222690_1_.size();
      ObjectList<Heightmap> objectlist = new ObjectArrayList<>(i);
      ObjectListIterator<Heightmap> objectlistiterator = objectlist.iterator();
      int j = p_222690_0_.getTopFilledSegment() + 16;

      try (BlockPos.PooledMutable blockpos$pooledmutable = BlockPos.PooledMutable.retain()) {
         for(int k = 0; k < 16; ++k) {
            for(int l = 0; l < 16; ++l) {
               for(Heightmap.Type heightmap$type : p_222690_1_) {
                  objectlist.add(p_222690_0_.func_217303_b(heightmap$type));
               }

               for(int i1 = j - 1; i1 >= 0; --i1) {
                  blockpos$pooledmutable.setPos(k, i1, l);
                  BlockState blockstate = p_222690_0_.getBlockState(blockpos$pooledmutable);
                  if (blockstate.getBlock() != Blocks.AIR) {
                     while(objectlistiterator.hasNext()) {
                        Heightmap heightmap = objectlistiterator.next();
                        if (heightmap.field_222693_d.test(blockstate)) {
                           heightmap.set(k, l, i1 + 1);
                           objectlistiterator.remove();
                        }
                     }

                     if (objectlist.isEmpty()) {
                        break;
                     }

                     objectlistiterator.back(i);
                  }
               }
            }
         }
      }

   }

   public boolean update(int p_202270_1_, int p_202270_2_, int p_202270_3_, BlockState p_202270_4_) {
      int i = this.getHeight(p_202270_1_, p_202270_3_);
      if (p_202270_2_ <= i - 2) {
         return false;
      } else {
         if (this.field_222693_d.test(p_202270_4_)) {
            if (p_202270_2_ >= i) {
               this.set(p_202270_1_, p_202270_3_, p_202270_2_ + 1);
               return true;
            }
         } else if (i - 1 == p_202270_2_) {
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for(int j = p_202270_2_ - 1; j >= 0; --j) {
               blockpos$mutable.setPos(p_202270_1_, j, p_202270_3_);
               if (this.field_222693_d.test(this.chunk.getBlockState(blockpos$mutable))) {
                  this.set(p_202270_1_, p_202270_3_, j + 1);
                  return true;
               }
            }

            this.set(p_202270_1_, p_202270_3_, 0);
            return true;
         }

         return false;
      }
   }

   public int getHeight(int x, int z) {
      return this.getHeight(getDataArrayIndex(x, z));
   }

   private int getHeight(int dataArrayIndex) {
      return this.data.getAt(dataArrayIndex);
   }

   private void set(int x, int z, int value) {
      this.data.setAt(getDataArrayIndex(x, z), value);
   }

   public void setDataArray(long[] dataIn) {
      System.arraycopy(dataIn, 0, this.data.getBackingLongArray(), 0, dataIn.length);
   }

   public long[] getDataArray() {
      return this.data.getBackingLongArray();
   }

   private static int getDataArrayIndex(int x, int z) {
      return x + z * 16;
   }

   public static enum Type {
      WORLD_SURFACE_WG("WORLD_SURFACE_WG", Heightmap.Usage.WORLDGEN, Heightmap.field_222691_a),
      WORLD_SURFACE("WORLD_SURFACE", Heightmap.Usage.CLIENT, Heightmap.field_222691_a),
      OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", Heightmap.Usage.WORLDGEN, Heightmap.field_222692_b),
      OCEAN_FLOOR("OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD, Heightmap.field_222692_b),
      MOTION_BLOCKING("MOTION_BLOCKING", Heightmap.Usage.CLIENT, (p_222680_0_) -> {
         return p_222680_0_.getMaterial().blocksMovement() || !p_222680_0_.getFluidState().isEmpty();
      }),
      MOTION_BLOCKING_NO_LEAVES("MOTION_BLOCKING_NO_LEAVES", Heightmap.Usage.LIVE_WORLD, (p_222682_0_) -> {
         return (p_222682_0_.getMaterial().blocksMovement() || !p_222682_0_.getFluidState().isEmpty()) && !(p_222682_0_.getBlock() instanceof LeavesBlock);
      });

      private final String id;
      private final Heightmap.Usage usage;
      private final Predicate<BlockState> field_222685_i;
      private static final Map<String, Heightmap.Type> field_203503_g = Util.make(Maps.newHashMap(), (p_222679_0_) -> {
         for(Heightmap.Type heightmap$type : values()) {
            p_222679_0_.put(heightmap$type.id, heightmap$type);
         }

      });

      private Type(String p_i50821_3_, Heightmap.Usage p_i50821_4_, Predicate<BlockState> p_i50821_5_) {
         this.id = p_i50821_3_;
         this.usage = p_i50821_4_;
         this.field_222685_i = p_i50821_5_;
      }

      public String getId() {
         return this.id;
      }

      public boolean func_222681_b() {
         return this.usage == Heightmap.Usage.CLIENT;
      }

      @OnlyIn(Dist.CLIENT)
      public boolean func_222683_c() {
         return this.usage != Heightmap.Usage.WORLDGEN;
      }

      public static Heightmap.Type func_203501_a(String p_203501_0_) {
         return field_203503_g.get(p_203501_0_);
      }

      public Predicate<BlockState> func_222684_d() {
         return this.field_222685_i;
      }
   }

   public static enum Usage {
      WORLDGEN,
      LIVE_WORLD,
      CLIENT;
   }
}