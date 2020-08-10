package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class ConfiguredRandomFeatureList<FC extends IFeatureConfig> {
   public static final Codec<ConfiguredRandomFeatureList<?>> field_236430_a_ = RecordCodecBuilder.create((p_236433_0_) -> {
      return p_236433_0_.group(ConfiguredFeature.field_236264_b_.fieldOf("feature").forGetter((p_236434_0_) -> {
         return p_236434_0_.feature;
      }), Codec.FLOAT.fieldOf("chance").forGetter((p_236432_0_) -> {
         return p_236432_0_.chance;
      })).apply(p_236433_0_, ConfiguredRandomFeatureList::new);
   });
   public final ConfiguredFeature<FC, ?> feature;
   public final float chance;

   public ConfiguredRandomFeatureList(ConfiguredFeature<FC, ?> p_i225822_1_, float p_i225822_2_) {
      this.feature = p_i225822_1_;
      this.chance = p_i225822_2_;
   }

   public boolean func_236431_a_(ISeedReader p_236431_1_, StructureManager p_236431_2_, ChunkGenerator p_236431_3_, Random p_236431_4_, BlockPos p_236431_5_) {
      return this.feature.func_236265_a_(p_236431_1_, p_236431_2_, p_236431_3_, p_236431_4_, p_236431_5_);
   }
}