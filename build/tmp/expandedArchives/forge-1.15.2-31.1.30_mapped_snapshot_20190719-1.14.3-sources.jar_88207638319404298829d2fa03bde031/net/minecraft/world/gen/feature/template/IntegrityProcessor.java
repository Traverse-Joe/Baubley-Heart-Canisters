package net.minecraft.world.gen.feature.template;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class IntegrityProcessor extends StructureProcessor {
   private final float integrity;

   public IntegrityProcessor(float integrity) {
      this.integrity = integrity;
   }

   public IntegrityProcessor(Dynamic<?> p_i51333_1_) {
      this(p_i51333_1_.get("integrity").asFloat(1.0F));
   }

   @Nullable
   public Template.BlockInfo process(IWorldReader p_215194_1_, BlockPos p_215194_2_, Template.BlockInfo p_215194_3_, Template.BlockInfo p_215194_4_, PlacementSettings p_215194_5_) {
      Random random = p_215194_5_.getRandom(p_215194_4_.pos);
      return !(this.integrity >= 1.0F) && !(random.nextFloat() <= this.integrity) ? null : p_215194_4_;
   }

   protected IStructureProcessorType getType() {
      return IStructureProcessorType.BLOCK_ROT;
   }

   protected <T> Dynamic<T> serialize0(DynamicOps<T> ops) {
      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("integrity"), ops.createFloat(this.integrity))));
   }
}