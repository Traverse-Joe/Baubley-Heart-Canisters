package net.minecraft.client.renderer.color;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColorCache {
   private final ThreadLocal<ColorCache.Entry> field_228066_a_ = ThreadLocal.withInitial(() -> {
      return new ColorCache.Entry();
   });
   private final Long2ObjectLinkedOpenHashMap<int[]> field_228067_b_ = new Long2ObjectLinkedOpenHashMap<>(256, 0.25F);
   private final ReentrantReadWriteLock field_228068_c_ = new ReentrantReadWriteLock();

   public int func_228071_a_(BlockPos p_228071_1_, IntSupplier p_228071_2_) {
      int i = p_228071_1_.getX() >> 4;
      int j = p_228071_1_.getZ() >> 4;
      ColorCache.Entry colorcache$entry = this.field_228066_a_.get();
      if (colorcache$entry.field_228074_a_ != i || colorcache$entry.field_228075_b_ != j) {
         colorcache$entry.field_228074_a_ = i;
         colorcache$entry.field_228075_b_ = j;
         colorcache$entry.field_228076_c_ = this.func_228073_b_(i, j);
      }

      int k = p_228071_1_.getX() & 15;
      int l = p_228071_1_.getZ() & 15;
      int i1 = l << 4 | k;
      int j1 = colorcache$entry.field_228076_c_[i1];
      if (j1 != -1) {
         return j1;
      } else {
         int k1 = p_228071_2_.getAsInt();
         colorcache$entry.field_228076_c_[i1] = k1;
         return k1;
      }
   }

   public void func_228070_a_(int p_228070_1_, int p_228070_2_) {
      try {
         this.field_228068_c_.writeLock().lock();

         for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
               long k = ChunkPos.asLong(p_228070_1_ + i, p_228070_2_ + j);
               this.field_228067_b_.remove(k);
            }
         }
      } finally {
         this.field_228068_c_.writeLock().unlock();
      }

   }

   public void func_228069_a_() {
      try {
         this.field_228068_c_.writeLock().lock();
         this.field_228067_b_.clear();
      } finally {
         this.field_228068_c_.writeLock().unlock();
      }

   }

   private int[] func_228073_b_(int p_228073_1_, int p_228073_2_) {
      long i = ChunkPos.asLong(p_228073_1_, p_228073_2_);
      this.field_228068_c_.readLock().lock();

      int[] aint;
      try {
         aint = this.field_228067_b_.get(i);
      } finally {
         this.field_228068_c_.readLock().unlock();
      }

      if (aint != null) {
         return aint;
      } else {
         int[] aint1 = new int[256];
         Arrays.fill(aint1, -1);

         try {
            this.field_228068_c_.writeLock().lock();
            if (this.field_228067_b_.size() >= 256) {
               this.field_228067_b_.removeFirst();
            }

            this.field_228067_b_.put(i, aint1);
         } finally {
            this.field_228068_c_.writeLock().unlock();
         }

         return aint1;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Entry {
      public int field_228074_a_ = Integer.MIN_VALUE;
      public int field_228075_b_ = Integer.MIN_VALUE;
      public int[] field_228076_c_;

      private Entry() {
      }
   }
}