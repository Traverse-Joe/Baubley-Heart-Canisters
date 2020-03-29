package net.minecraft.world.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ITickList;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.TickPriority;

public class ServerTickList<T> implements ITickList<T> {
   protected final Predicate<T> filter;
   private final Function<T, ResourceLocation> serializer;
   private final Function<ResourceLocation, T> deserializer;
   private final Set<NextTickListEntry<T>> pendingTickListEntriesHashSet = Sets.newHashSet();
   private final TreeSet<NextTickListEntry<T>> pendingTickListEntriesTreeSet = Sets.newTreeSet(NextTickListEntry.func_223192_a());
   private final ServerWorld world;
   private final Queue<NextTickListEntry<T>> pendingTickListEntriesThisTick = Queues.newArrayDeque();
   private final List<NextTickListEntry<T>> field_223189_h = Lists.newArrayList();
   private final Consumer<NextTickListEntry<T>> tickFunction;

   public ServerTickList(ServerWorld worldIn, Predicate<T> filter, Function<T, ResourceLocation> serializerIn, Function<ResourceLocation, T> deserializerIn, Consumer<NextTickListEntry<T>> tickFunctionIn) {
      this.filter = filter;
      this.serializer = serializerIn;
      this.deserializer = deserializerIn;
      this.world = worldIn;
      this.tickFunction = tickFunctionIn;
   }

   public void tick() {
      int i = this.pendingTickListEntriesTreeSet.size();
      if (i != this.pendingTickListEntriesHashSet.size()) {
         throw new IllegalStateException("TickNextTick list out of synch");
      } else {
         if (i > 65536) {
            i = 65536;
         }

         ServerChunkProvider serverchunkprovider = this.world.getChunkProvider();
         Iterator<NextTickListEntry<T>> iterator = this.pendingTickListEntriesTreeSet.iterator();
         this.world.getProfiler().startSection("cleaning");

         while(i > 0 && iterator.hasNext()) {
            NextTickListEntry<T> nextticklistentry = iterator.next();
            if (nextticklistentry.scheduledTime > this.world.getGameTime()) {
               break;
            }

            if (serverchunkprovider.canTick(nextticklistentry.position)) {
               iterator.remove();
               this.pendingTickListEntriesHashSet.remove(nextticklistentry);
               this.pendingTickListEntriesThisTick.add(nextticklistentry);
               --i;
            }
         }

         this.world.getProfiler().endStartSection("ticking");

         NextTickListEntry<T> nextticklistentry1;
         while((nextticklistentry1 = this.pendingTickListEntriesThisTick.poll()) != null) {
            if (serverchunkprovider.canTick(nextticklistentry1.position)) {
               try {
                  this.field_223189_h.add(nextticklistentry1);
                  this.tickFunction.accept(nextticklistentry1);
               } catch (Throwable throwable) {
                  CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while ticking");
                  CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being ticked");
                  CrashReportCategory.addBlockInfo(crashreportcategory, nextticklistentry1.position, (BlockState)null);
                  throw new ReportedException(crashreport);
               }
            } else {
               this.scheduleTick(nextticklistentry1.position, nextticklistentry1.getTarget(), 0);
            }
         }

         this.world.getProfiler().endSection();
         this.field_223189_h.clear();
         this.pendingTickListEntriesThisTick.clear();
      }
   }

   /**
    * Checks if this position/item is scheduled to be updated this tick
    */
   public boolean isTickPending(BlockPos pos, T obj) {
      return this.pendingTickListEntriesThisTick.contains(new NextTickListEntry(pos, obj));
   }

   public void func_219497_a(Stream<NextTickListEntry<T>> p_219497_1_) {
      p_219497_1_.forEach(this::func_219504_a);
   }

   public List<NextTickListEntry<T>> func_223188_a(ChunkPos p_223188_1_, boolean p_223188_2_, boolean p_223188_3_) {
      int i = (p_223188_1_.x << 4) - 2;
      int j = i + 16 + 2;
      int k = (p_223188_1_.z << 4) - 2;
      int l = k + 16 + 2;
      return this.getPending(new MutableBoundingBox(i, 0, k, j, 256, l), p_223188_2_, p_223188_3_);
   }

   public List<NextTickListEntry<T>> getPending(MutableBoundingBox p_205366_1_, boolean remove, boolean p_205366_3_) {
      List<NextTickListEntry<T>> list = this.func_223187_a((List<NextTickListEntry<T>>)null, this.pendingTickListEntriesTreeSet, p_205366_1_, remove);
      if (remove && list != null) {
         this.pendingTickListEntriesHashSet.removeAll(list);
      }

      list = this.func_223187_a(list, this.pendingTickListEntriesThisTick, p_205366_1_, remove);
      if (!p_205366_3_) {
         list = this.func_223187_a(list, this.field_223189_h, p_205366_1_, remove);
      }

      return list == null ? Collections.emptyList() : list;
   }

   @Nullable
   private List<NextTickListEntry<T>> func_223187_a(@Nullable List<NextTickListEntry<T>> p_223187_1_, Collection<NextTickListEntry<T>> p_223187_2_, MutableBoundingBox p_223187_3_, boolean p_223187_4_) {
      Iterator<NextTickListEntry<T>> iterator = p_223187_2_.iterator();

      while(iterator.hasNext()) {
         NextTickListEntry<T> nextticklistentry = iterator.next();
         BlockPos blockpos = nextticklistentry.position;
         if (blockpos.getX() >= p_223187_3_.minX && blockpos.getX() < p_223187_3_.maxX && blockpos.getZ() >= p_223187_3_.minZ && blockpos.getZ() < p_223187_3_.maxZ) {
            if (p_223187_4_) {
               iterator.remove();
            }

            if (p_223187_1_ == null) {
               p_223187_1_ = Lists.newArrayList();
            }

            p_223187_1_.add(nextticklistentry);
         }
      }

      return p_223187_1_;
   }

   public void copyTicks(MutableBoundingBox area, BlockPos offset) {
      for(NextTickListEntry<T> nextticklistentry : this.getPending(area, false, false)) {
         if (area.isVecInside(nextticklistentry.position)) {
            BlockPos blockpos = nextticklistentry.position.add(offset);
            T t = nextticklistentry.getTarget();
            this.func_219504_a(new NextTickListEntry<>(blockpos, t, nextticklistentry.scheduledTime, nextticklistentry.priority));
         }
      }

   }

   public ListNBT func_219503_a(ChunkPos p_219503_1_) {
      List<NextTickListEntry<T>> list = this.func_223188_a(p_219503_1_, false, true);
      return func_219502_a(this.serializer, list, this.world.getGameTime());
   }

   public static <T> ListNBT func_219502_a(Function<T, ResourceLocation> p_219502_0_, Iterable<NextTickListEntry<T>> p_219502_1_, long p_219502_2_) {
      ListNBT listnbt = new ListNBT();

      for(NextTickListEntry<T> nextticklistentry : p_219502_1_) {
         CompoundNBT compoundnbt = new CompoundNBT();
         compoundnbt.putString("i", p_219502_0_.apply(nextticklistentry.getTarget()).toString());
         compoundnbt.putInt("x", nextticklistentry.position.getX());
         compoundnbt.putInt("y", nextticklistentry.position.getY());
         compoundnbt.putInt("z", nextticklistentry.position.getZ());
         compoundnbt.putInt("t", (int)(nextticklistentry.scheduledTime - p_219502_2_));
         compoundnbt.putInt("p", nextticklistentry.priority.getPriority());
         listnbt.add(compoundnbt);
      }

      return listnbt;
   }

   public boolean isTickScheduled(BlockPos pos, T itemIn) {
      return this.pendingTickListEntriesHashSet.contains(new NextTickListEntry(pos, itemIn));
   }

   public void scheduleTick(BlockPos pos, T itemIn, int scheduledTime, TickPriority priority) {
      if (!this.filter.test(itemIn)) {
         this.func_219504_a(new NextTickListEntry<>(pos, itemIn, (long)scheduledTime + this.world.getGameTime(), priority));
      }

   }

   private void func_219504_a(NextTickListEntry<T> p_219504_1_) {
      if (!this.pendingTickListEntriesHashSet.contains(p_219504_1_)) {
         this.pendingTickListEntriesHashSet.add(p_219504_1_);
         this.pendingTickListEntriesTreeSet.add(p_219504_1_);
      }

   }

   public int func_225420_a() {
      return this.pendingTickListEntriesHashSet.size();
   }
}