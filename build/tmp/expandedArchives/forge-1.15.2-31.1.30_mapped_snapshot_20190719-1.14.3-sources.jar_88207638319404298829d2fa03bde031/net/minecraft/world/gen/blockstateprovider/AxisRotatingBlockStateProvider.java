package net.minecraft.world.gen.blockstateprovider;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class AxisRotatingBlockStateProvider extends BlockStateProvider {
   private final Block field_227404_b_;

   public AxisRotatingBlockStateProvider(Block p_i225858_1_) {
      super(BlockStateProviderType.field_227394_a_);
      this.field_227404_b_ = p_i225858_1_;
   }

   public <T> AxisRotatingBlockStateProvider(Dynamic<T> p_i225859_1_) {
      this(BlockState.deserialize(p_i225859_1_.get("state").orElseEmptyMap()).getBlock());
   }

   public BlockState func_225574_a_(Random p_225574_1_, BlockPos p_225574_2_) {
      Direction.Axis direction$axis = Direction.Axis.random(p_225574_1_);
      return this.field_227404_b_.getDefaultState().with(RotatedPillarBlock.AXIS, direction$axis);
   }

   public <T> T serialize(DynamicOps<T> p_218175_1_) {
      Builder<T, T> builder = ImmutableMap.builder();
      builder.put(p_218175_1_.createString("type"), p_218175_1_.createString(Registry.field_229387_t_.getKey(this.field_227393_a_).toString())).put(p_218175_1_.createString("state"), BlockState.serialize(p_218175_1_, this.field_227404_b_.getDefaultState()).getValue());
      return (new Dynamic<>(p_218175_1_, p_218175_1_.createMap(builder.build()))).getValue();
   }
}