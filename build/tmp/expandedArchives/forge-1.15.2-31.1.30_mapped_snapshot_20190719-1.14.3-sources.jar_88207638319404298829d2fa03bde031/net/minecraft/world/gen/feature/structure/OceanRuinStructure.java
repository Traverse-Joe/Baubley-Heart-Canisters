package net.minecraft.world.gen.feature.structure;

import com.mojang.datafixers.Dynamic;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanRuinStructure extends ScatteredStructure<OceanRuinConfig> {
   public OceanRuinStructure(Function<Dynamic<?>, ? extends OceanRuinConfig> p_i51348_1_) {
      super(p_i51348_1_);
   }

   public String getStructureName() {
      return "Ocean_Ruin";
   }

   public int getSize() {
      return 3;
   }

   protected int getBiomeFeatureDistance(ChunkGenerator<?> chunkGenerator) {
      return chunkGenerator.getSettings().getOceanRuinDistance();
   }

   protected int getBiomeFeatureSeparation(ChunkGenerator<?> chunkGenerator) {
      return chunkGenerator.getSettings().getOceanRuinSeparation();
   }

   public Structure.IStartFactory getStartFactory() {
      return OceanRuinStructure.Start::new;
   }

   protected int getSeedModifier() {
      return 14357621;
   }

   public static class Start extends StructureStart {
      public Start(Structure<?> p_i225875_1_, int p_i225875_2_, int p_i225875_3_, MutableBoundingBox p_i225875_4_, int p_i225875_5_, long p_i225875_6_) {
         super(p_i225875_1_, p_i225875_2_, p_i225875_3_, p_i225875_4_, p_i225875_5_, p_i225875_6_);
      }

      public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
         OceanRuinConfig oceanruinconfig = (OceanRuinConfig)generator.getStructureConfig(biomeIn, Feature.OCEAN_RUIN);
         int i = chunkX * 16;
         int j = chunkZ * 16;
         BlockPos blockpos = new BlockPos(i, 90, j);
         Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
         OceanRuinPieces.func_204041_a(templateManagerIn, blockpos, rotation, this.components, this.rand, oceanruinconfig);
         this.recalculateStructureSize();
      }
   }

   public static enum Type {
      WARM("warm"),
      COLD("cold");

      private static final Map<String, OceanRuinStructure.Type> field_215137_c = Arrays.stream(values()).collect(Collectors.toMap(OceanRuinStructure.Type::func_215135_a, (p_215134_0_) -> {
         return p_215134_0_;
      }));
      private final String field_215138_d;

      private Type(String p_i50621_3_) {
         this.field_215138_d = p_i50621_3_;
      }

      public String func_215135_a() {
         return this.field_215138_d;
      }

      public static OceanRuinStructure.Type func_215136_a(String p_215136_0_) {
         return field_215137_c.get(p_215136_0_);
      }
   }
}