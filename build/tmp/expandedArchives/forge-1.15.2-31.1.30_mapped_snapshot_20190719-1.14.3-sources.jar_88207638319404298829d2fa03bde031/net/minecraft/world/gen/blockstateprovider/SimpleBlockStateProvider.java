package net.minecraft.world.gen.blockstateprovider;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class SimpleBlockStateProvider extends BlockStateProvider {
   private final BlockState field_227405_b_;

   public SimpleBlockStateProvider(BlockState p_i225860_1_) {
      super(BlockStateProviderType.field_227394_a_);
      this.field_227405_b_ = p_i225860_1_;
   }

   public <T> SimpleBlockStateProvider(Dynamic<T> p_i225861_1_) {
      this(BlockState.deserialize(p_i225861_1_.get("state").orElseEmptyMap()));
   }

   public BlockState func_225574_a_(Random p_225574_1_, BlockPos p_225574_2_) {
      return this.field_227405_b_;
   }

   public <T> T serialize(DynamicOps<T> p_218175_1_) {
      Builder<T, T> builder = ImmutableMap.builder();
      builder.put(p_218175_1_.createString("type"), p_218175_1_.createString(Registry.field_229387_t_.getKey(this.field_227393_a_).toString())).put(p_218175_1_.createString("state"), BlockState.serialize(p_218175_1_, this.field_227405_b_).getValue());
      return (new Dynamic<>(p_218175_1_, p_218175_1_.createMap(builder.build()))).getValue();
   }
}