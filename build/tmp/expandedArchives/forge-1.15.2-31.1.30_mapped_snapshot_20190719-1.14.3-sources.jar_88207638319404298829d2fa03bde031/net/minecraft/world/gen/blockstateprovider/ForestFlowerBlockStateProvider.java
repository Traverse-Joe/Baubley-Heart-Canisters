package net.minecraft.world.gen.blockstateprovider;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ForestFlowerBlockStateProvider extends BlockStateProvider {
   private static final BlockState[] field_227401_b_ = new BlockState[]{Blocks.DANDELION.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.ALLIUM.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.ORANGE_TULIP.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(), Blocks.CORNFLOWER.getDefaultState(), Blocks.LILY_OF_THE_VALLEY.getDefaultState()};

   public ForestFlowerBlockStateProvider() {
      super(BlockStateProviderType.field_227397_d_);
   }

   public <T> ForestFlowerBlockStateProvider(Dynamic<T> p_i225856_1_) {
      this();
   }

   public BlockState func_225574_a_(Random p_225574_1_, BlockPos p_225574_2_) {
      double d0 = MathHelper.clamp((1.0D + Biome.INFO_NOISE.func_215464_a((double)p_225574_2_.getX() / 48.0D, (double)p_225574_2_.getZ() / 48.0D, false)) / 2.0D, 0.0D, 0.9999D);
      return field_227401_b_[(int)(d0 * (double)field_227401_b_.length)];
   }

   public <T> T serialize(DynamicOps<T> p_218175_1_) {
      Builder<T, T> builder = ImmutableMap.builder();
      builder.put(p_218175_1_.createString("type"), p_218175_1_.createString(Registry.field_229387_t_.getKey(this.field_227393_a_).toString()));
      return (new Dynamic<>(p_218175_1_, p_218175_1_.createMap(builder.build()))).getValue();
   }
}