package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.GravityStructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class JigsawPattern {
   public static final JigsawPattern EMPTY = new JigsawPattern(new ResourceLocation("empty"), new ResourceLocation("empty"), ImmutableList.of(), JigsawPattern.PlacementBehaviour.RIGID);
   public static final JigsawPattern INVALID = new JigsawPattern(new ResourceLocation("invalid"), new ResourceLocation("invalid"), ImmutableList.of(), JigsawPattern.PlacementBehaviour.RIGID);
   private final ResourceLocation field_214951_c;
   private final ImmutableList<Pair<JigsawPiece, Integer>> field_214952_d;
   private final List<JigsawPiece> field_214953_e;
   private final ResourceLocation field_214954_f;
   private final JigsawPattern.PlacementBehaviour field_214955_g;
   private int field_214956_h = Integer.MIN_VALUE;

   public JigsawPattern(ResourceLocation p_i51397_1_, ResourceLocation p_i51397_2_, List<Pair<JigsawPiece, Integer>> p_i51397_3_, JigsawPattern.PlacementBehaviour p_i51397_4_) {
      this.field_214951_c = p_i51397_1_;
      this.field_214952_d = ImmutableList.copyOf(p_i51397_3_);
      this.field_214953_e = Lists.newArrayList();

      for(Pair<JigsawPiece, Integer> pair : p_i51397_3_) {
         for(Integer integer = 0; integer < pair.getSecond(); integer = integer + 1) {
            this.field_214953_e.add(pair.getFirst().setPlacementBehaviour(p_i51397_4_));
         }
      }

      this.field_214954_f = p_i51397_2_;
      this.field_214955_g = p_i51397_4_;
   }

   public int func_214945_a(TemplateManager p_214945_1_) {
      if (this.field_214956_h == Integer.MIN_VALUE) {
         this.field_214956_h = this.field_214953_e.stream().mapToInt((p_214942_1_) -> {
            return p_214942_1_.func_214852_a(p_214945_1_, BlockPos.ZERO, Rotation.NONE).getYSize();
         }).max().orElse(0);
      }

      return this.field_214956_h;
   }

   public ResourceLocation func_214948_a() {
      return this.field_214954_f;
   }

   public JigsawPiece func_214944_a(Random p_214944_1_) {
      return this.field_214953_e.get(p_214944_1_.nextInt(this.field_214953_e.size()));
   }

   public List<JigsawPiece> func_214943_b(Random p_214943_1_) {
      return ImmutableList.copyOf(ObjectArrays.shuffle(this.field_214953_e.toArray(new JigsawPiece[0]), p_214943_1_));
   }

   public ResourceLocation func_214947_b() {
      return this.field_214951_c;
   }

   public int func_214946_c() {
      return this.field_214953_e.size();
   }

   public static enum PlacementBehaviour implements net.minecraftforge.common.IExtensibleEnum {
      TERRAIN_MATCHING("terrain_matching", ImmutableList.of(new GravityStructureProcessor(Heightmap.Type.WORLD_SURFACE_WG, -1))),
      RIGID("rigid", ImmutableList.of());

      private static final Map<String, JigsawPattern.PlacementBehaviour> field_214939_c = Arrays.stream(values()).collect(Collectors.toMap(JigsawPattern.PlacementBehaviour::func_214936_a, (p_214935_0_) -> {
         return p_214935_0_;
      }));
      private final String field_214940_d;
      private final ImmutableList<StructureProcessor> field_214941_e;

      private PlacementBehaviour(String p_i50487_3_, ImmutableList<StructureProcessor> p_i50487_4_) {
         this.field_214940_d = p_i50487_3_;
         this.field_214941_e = p_i50487_4_;
      }

      public String func_214936_a() {
         return this.field_214940_d;
      }

      public static JigsawPattern.PlacementBehaviour func_214938_a(String p_214938_0_) {
         return field_214939_c.get(p_214938_0_);
      }

      public ImmutableList<StructureProcessor> func_214937_b() {
         return this.field_214941_e;
      }
      
      public static PlacementBehaviour create(String enumName, String p_i50487_3_, ImmutableList<StructureProcessor> p_i50487_4_) {
         throw new IllegalStateException("Enum not extended");
      }

      @Override
      @Deprecated
      public void init() {
         field_214939_c.put(func_214936_a(), this);
      }
   }
}