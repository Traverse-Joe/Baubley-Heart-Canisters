package net.minecraft.world;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerTickList;

public class SerializableTickList<T> implements ITickList<T> {
   private final Set<NextTickListEntry<T>> field_219500_a;
   private final Function<T, ResourceLocation> field_219501_b;

   public SerializableTickList(Function<T, ResourceLocation> p_i50010_1_, List<NextTickListEntry<T>> p_i50010_2_) {
      this(p_i50010_1_, Sets.newHashSet(p_i50010_2_));
   }

   private SerializableTickList(Function<T, ResourceLocation> p_i51499_1_, Set<NextTickListEntry<T>> p_i51499_2_) {
      this.field_219500_a = p_i51499_2_;
      this.field_219501_b = p_i51499_1_;
   }

   public boolean isTickScheduled(BlockPos pos, T itemIn) {
      return false;
   }

   public void scheduleTick(BlockPos pos, T itemIn, int scheduledTime, TickPriority priority) {
      this.field_219500_a.add(new NextTickListEntry<>(pos, itemIn, (long)scheduledTime, priority));
   }

   /**
    * Checks if this position/item is scheduled to be updated this tick
    */
   public boolean isTickPending(BlockPos pos, T obj) {
      return false;
   }

   public void func_219497_a(Stream<NextTickListEntry<T>> p_219497_1_) {
      p_219497_1_.forEach(this.field_219500_a::add);
   }

   public Stream<NextTickListEntry<T>> func_219499_a() {
      return this.field_219500_a.stream();
   }

   public ListNBT func_219498_a(long p_219498_1_) {
      return ServerTickList.func_219502_a(this.field_219501_b, this.field_219500_a, p_219498_1_);
   }

   public static <T> SerializableTickList<T> func_222984_a(ListNBT p_222984_0_, Function<T, ResourceLocation> p_222984_1_, Function<ResourceLocation, T> p_222984_2_) {
      Set<NextTickListEntry<T>> set = Sets.newHashSet();

      for(int i = 0; i < p_222984_0_.size(); ++i) {
         CompoundNBT compoundnbt = p_222984_0_.getCompound(i);
         T t = p_222984_2_.apply(new ResourceLocation(compoundnbt.getString("i")));
         if (t != null) {
            set.add(new NextTickListEntry<>(new BlockPos(compoundnbt.getInt("x"), compoundnbt.getInt("y"), compoundnbt.getInt("z")), t, (long)compoundnbt.getInt("t"), TickPriority.getPriority(compoundnbt.getInt("p"))));
         }
      }

      return new SerializableTickList<>(p_222984_1_, set);
   }
}