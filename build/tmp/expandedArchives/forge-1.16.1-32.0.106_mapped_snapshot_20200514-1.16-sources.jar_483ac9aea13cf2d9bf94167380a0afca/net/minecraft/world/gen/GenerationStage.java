package net.minecraft.world.gen;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.IStringSerializable;

public class GenerationStage {
   public static enum Carving implements IStringSerializable {
      AIR("air"),
      LIQUID("liquid");

      public static final Codec<GenerationStage.Carving> field_236074_c_ = IStringSerializable.func_233023_a_(GenerationStage.Carving::values, GenerationStage.Carving::func_236075_a_);
      private static final Map<String, GenerationStage.Carving> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(GenerationStage.Carving::getName, (p_222672_0_) -> {
         return p_222672_0_;
      }));
      private final String name;

      private Carving(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      @Nullable
      public static GenerationStage.Carving func_236075_a_(String p_236075_0_) {
         return BY_NAME.get(p_236075_0_);
      }

      public String func_176610_l() {
         return this.name;
      }
   }

   public static enum Decoration implements IStringSerializable {
      RAW_GENERATION("raw_generation"),
      LAKES("lakes"),
      LOCAL_MODIFICATIONS("local_modifications"),
      UNDERGROUND_STRUCTURES("underground_structures"),
      SURFACE_STRUCTURES("surface_structures"),
      STRONGHOLDS("strongholds"),
      UNDERGROUND_ORES("underground_ores"),
      UNDERGROUND_DECORATION("underground_decoration"),
      VEGETAL_DECORATION("vegetal_decoration"),
      TOP_LAYER_MODIFICATION("top_layer_modification");

      public static final Codec<GenerationStage.Decoration> field_236076_k_ = IStringSerializable.func_233023_a_(GenerationStage.Decoration::values, GenerationStage.Decoration::func_236077_a_);
      private static final Map<String, GenerationStage.Decoration> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(GenerationStage.Decoration::getName, (p_222675_0_) -> {
         return p_222675_0_;
      }));
      private final String name;

      private Decoration(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public static GenerationStage.Decoration func_236077_a_(String p_236077_0_) {
         return BY_NAME.get(p_236077_0_);
      }

      public String func_176610_l() {
         return this.name;
      }
   }
}