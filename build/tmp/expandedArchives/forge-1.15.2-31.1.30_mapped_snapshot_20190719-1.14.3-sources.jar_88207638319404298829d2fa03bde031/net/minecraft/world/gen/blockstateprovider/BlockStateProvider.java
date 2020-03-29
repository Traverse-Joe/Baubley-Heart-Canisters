package net.minecraft.world.gen.blockstateprovider;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.IDynamicSerializable;
import net.minecraft.util.math.BlockPos;

public abstract class BlockStateProvider implements IDynamicSerializable {
   protected final BlockStateProviderType<?> field_227393_a_;

   protected BlockStateProvider(BlockStateProviderType<?> p_i225854_1_) {
      this.field_227393_a_ = p_i225854_1_;
   }

   public abstract BlockState func_225574_a_(Random p_225574_1_, BlockPos p_225574_2_);
}