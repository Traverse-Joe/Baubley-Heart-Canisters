package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class NetworkTagCollection<T> extends TagCollection<T> {
   private final Registry<T> registry;

   public NetworkTagCollection(Registry<T> p_i49817_1_, String p_i49817_2_, String p_i49817_3_) {
      super(p_i49817_1_::getValue, p_i49817_2_, p_i49817_3_);
      this.registry = p_i49817_1_;
   }

   public void write(PacketBuffer buf) {
      Map<ResourceLocation, ITag<T>> map = this.getTagMap();
      buf.writeVarInt(map.size());

      for(Entry<ResourceLocation, ITag<T>> entry : map.entrySet()) {
         buf.writeResourceLocation(entry.getKey());
         buf.writeVarInt(entry.getValue().func_230236_b_().size());

         for(T t : entry.getValue().func_230236_b_()) {
            buf.writeVarInt(this.registry.getId(t));
         }
      }

   }

   public void read(PacketBuffer buf) {
      Map<ResourceLocation, ITag<T>> map = Maps.newHashMap();
      int i = buf.readVarInt();

      for(int j = 0; j < i; ++j) {
         ResourceLocation resourcelocation = buf.readResourceLocation();
         int k = buf.readVarInt();
         Builder<T> builder = ImmutableSet.builder();

         for(int l = 0; l < k; ++l) {
            builder.add(this.registry.getByValue(buf.readVarInt()));
         }

         map.put(resourcelocation, ITag.func_232946_a_(builder.build()));
      }

      this.toImmutable(map);
   }
}